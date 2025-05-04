package no.ntnu.idatg2003.mappe10.model.filehandler.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import no.ntnu.idatg2003.mappe10.model.board.Board;
import no.ntnu.idatg2003.mappe10.model.actions.LadderAction;
import no.ntnu.idatg2003.mappe10.model.tile.Tile;
import no.ntnu.idatg2003.mappe10.model.tile.TileAction;
import no.ntnu.idatg2003.mappe10.model.filehandler.BoardFileReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for reading a board from a file in JSON format.
 * Uses Gson library for deserialization.
 * It implements the BoardFileReader interface.
 */
public class BoardFileReaderGson implements BoardFileReader {

    /**
     * Reads a board from a file path in JSON format.
     * If the file does not exist, it will throw a FileNotFoundException.
     *
     * @param filePath the path to the file where the board will be read from
     * @return the Board object read from the file
     */
    @Override
    public Board readBoard(String filePath){
        try (Reader reader = new FileReader(filePath)) {
            JsonObject boardJson = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray tilesJson = boardJson.getAsJsonArray("tiles");
            Map<Integer, Tile> tilesList = new HashMap<>();

            tilesJson.forEach(tileElement -> {
                Tile tileObject = deserializeJsonTileObject(tileElement, tilesList);
                tilesList.put(tileObject.getTileId(), tileObject);
            });

            Board board = new Board();
            board.setTilesList(tilesList);

            return board;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deserializes a JSON element to a Tile object.
     *
     * @param tileElement the JSON element to deserialize
     * @param tilesList   the map of tiles to set the next tile
     * @return the deserialized Tile object
     */
    private Tile deserializeJsonTileObject(JsonElement tileElement, Map<Integer, Tile> tilesList) {
        JsonObject tileJson = tileElement.getAsJsonObject();
        int tileId = tileJson.get("tileId").getAsInt();
        int nextTileId = tileJson.get("nextTile").getAsInt();

        Tile tile = new Tile(tileId);
        tile.setNextTile(tilesList.get(nextTileId));
        if (tileJson.has("action")){
            JsonObject actionJson = tileJson.getAsJsonObject("action");
            String actionType = actionJson.get("type").getAsString();
            int destinationTileId = actionJson.get("destinationTileID").getAsInt();
            String description = actionJson.get("description").getAsString();
            TileAction action = null;
            switch (actionType){
                case "LadderAction":
                    action = new LadderAction(destinationTileId, description);
                    break;
                case "WinAction":
                    break;
            }
            tile.setLandAction(action);
        }
        return tile;
    }
}
