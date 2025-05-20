package no.ntnu.idatg2003.mappe10.model.filehandler.gson;

import com.google.gson.*;
import no.ntnu.idatg2003.mappe10.model.board.Board;
import no.ntnu.idatg2003.mappe10.model.tile.tileaction.LadderAction;
import no.ntnu.idatg2003.mappe10.model.tile.Tile;
import no.ntnu.idatg2003.mappe10.model.filehandler.BoardFileWriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
    public void writeBoard(String filePath, Board board) {
        JsonObject boardJson = new JsonObject();
        JsonArray tilesJson = new JsonArray();
        board.getTilesList().values().forEach(tile -> {
            JsonObject tileJson = serializeTileObject(tile);
            tilesJson.add(tileJson);
        });
        JsonElement numberOfRows = new JsonPrimitive(board.getNumberOfRows());
        JsonElement numberOfColumns = new JsonPrimitive(board.getNumberOfColumns());

        boardJson.add("numberOfRows", numberOfRows);
        boardJson.add("numberOfColumns", numberOfColumns);
        boardJson.add("tiles", tilesJson);

        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath))) {
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

        if (tile.getNextTile() == null) {
            tileJson.addProperty("nextTile", "null");
        } else {
            tileJson.addProperty("nextTile", tile.getNextTile().getTileId());
        }

        if (tile.getBoardCoords() != null) {
            tileJson.addProperty("row", tile.getBoardCoords().getX0());
            tileJson.addProperty("column", tile.getBoardCoords().getX1());
        } else {
            tileJson.addProperty("row", "null");
            tileJson.addProperty("column", "null");
        }

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
