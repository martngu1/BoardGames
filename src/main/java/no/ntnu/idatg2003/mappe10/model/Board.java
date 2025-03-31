package no.ntnu.idatg2003.mappe10.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a map with all the tiles used for the board.
 */

public class Board {

    private Map<Integer, Tile> tilesList = new HashMap<>();

    /**
     * Adds tiles to the Map tilesList with their own distinct tileID.
     * tileID : Tile
     */
    public void addTile(Tile tile) {
        tilesList.put(tile.getTileId(), tile);
    }

    public Tile getTile(int tileId){
    return null;
    }
    public Map<Integer, Tile> getTilesList(){
        return tilesList;
    }
    public void setTilesList(Map<Integer, Tile> tilesList){
        this.tilesList = tilesList;
    }


}
