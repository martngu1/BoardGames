package no.ntnu.idatg2003.mappe10.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a map with all the tiles used for the board.
 */

public class Board {

    private Map<Integer, Tile> tilesList;

    public Board() {
        tilesList = new HashMap<>();
    }

    /**
     * Adds tiles to the Map tilesList with their own distinct tileID.
     * tileID : Tile
     */
    public void addTile(Tile tile) {
        tilesList.put(tile.getTileId(), tile);
    }

    /**
     * Returns the tile with the given tileID.
     *
     * @param tileId the id of the tile
     * @return the tile with the given tileID
     */
    public Tile getTile(int tileId){
    return tilesList.get(tileId);
    }

}
