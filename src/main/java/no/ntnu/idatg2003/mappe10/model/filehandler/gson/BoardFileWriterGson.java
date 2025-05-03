package no.ntnu.idatg2003.mappe10.model.filehandler.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import no.ntnu.idatg2003.mappe10.model.Board;
import no.ntnu.idatg2003.mappe10.model.LadderAction;
import no.ntnu.idatg2003.mappe10.model.Tile;
import no.ntnu.idatg2003.mappe10.model.filehandler.BoardFileWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * This class is responsible for writing a board to a file in JSON format.
 * Uses Gson library for serialization.
 * It implements the BoardFileWriter interface.
 */
public class BoardFileWriterGson implements BoardFileWriter {

    /**
     * Writes the given board to a file path in JSON format.
     * If the file already exists, it will be overwritten, if not, it will be created.
     *
     * @param filePath the path to the file where the board will be written
     * @param board    the board to write
     */
    @Override
    public void writeBoard(String filePath, Board board){
        JsonObject boardJson = new JsonObject();
        JsonArray tilesJson = new JsonArray();

        for (Tile tile : board.getTilesList().values()) {
            JsonObject tileJson = serializeTileObject(tile);
            tilesJson.add(tileJson);
        }
        boardJson.add("tiles", tilesJson);

        try (Writer writer = new FileWriter(filePath)){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(boardJson, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Serializes a Tile object to a JSON object.
     *
     * @param tile the Tile object to serialize
     * @return the serialized JSON object as JsonObject
     */
    private JsonObject serializeTileObject(Tile tile) {
        JsonObject tileJson = new JsonObject();
        tileJson.addProperty("tileId", tile.getTileId());
        tileJson.addProperty("nextTile", tile.getNextTileId());

        if (tile.getLandAction() != null){
            JsonObject actionJson = new JsonObject();
            String actionType = tile.getLandAction().getClass().getSimpleName();
            actionJson.addProperty("type", actionType);
            switch (actionType){
                case "LadderAction":
                    LadderAction ladderAction = (LadderAction) tile.getLandAction();
                    actionJson.addProperty("destinationTileID", ladderAction.getDestinationTileId());
                    actionJson.addProperty("description", ladderAction.getDescription());
                    break;
                case "OtherAction":
                    break;
            }
            tileJson.add("action", actionJson);
        }

     return tileJson;
    }
}
