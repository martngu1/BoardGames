package no.ntnu.idatg2003.mappe10.model;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JSONFileHandler {
    public void saveBoard(String filePath, Board board){
        JsonObject boardJson = new JsonObject();
        JsonArray tilesJson = new JsonArray();

        for (Tile tile : board.getTilesList().values()){
            JsonObject tileJson = new JsonObject();
            tileJson.addProperty("tileId", tile.getTileId());
            tileJson.addProperty("nextTile", tile.getNextTileId());
        }
    }
    public void loadGame(){
        System.out.println("Game loaded from JSON");
    }
}
