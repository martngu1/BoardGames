package no.ntnu.idatg2003.mappe10.model.filehandler;
import com.google.gson.*;
import no.ntnu.idatg2003.mappe10.model.Board;
import no.ntnu.idatg2003.mappe10.model.LadderAction;
import no.ntnu.idatg2003.mappe10.model.Tile;
import no.ntnu.idatg2003.mappe10.model.TileAction;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class JSONFileHandler {
    public void saveBoard(String filePath, Board board){
        JsonObject boardJson = new JsonObject();
        JsonArray tilesJson = new JsonArray();

        for (Tile tile : board.getTilesList().values()){
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
    public Board loadBoard(String filePath){
        try (Reader reader = new FileReader(filePath)) {
            JsonObject boardJson = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray tilesJson = boardJson.getAsJsonArray("tiles");
            Map<Integer, Tile> tilesList = new HashMap<>();
            for (JsonElement tileElement : tilesJson){
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
                tilesList.put(tileId, tile);
            }
            Board board = new Board();
            board.setTilesList(tilesList);
            return board;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
