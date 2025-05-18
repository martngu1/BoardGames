package no.ntnu.idatg2003.mappe10.model.board;

import no.ntnu.idatg2003.mappe10.model.tile.Tile;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a map with all the tiles used for the board.
 */
public class Board {
    private Map<Integer, Tile> tilesList;
    private int numberOfRows;
    private int numberOfColumns;
    private int numberOfTiles;

    /**
     * Constructor for the Board class.
     *
     * @param numberOfTiles
     * @param numberOfRows
     * @param numberOfColumns
     */
    public Board(int numberOfTiles, int numberOfRows, int numberOfColumns) {
        this.tilesList = new HashMap<>();
        initBoard(numberOfTiles);
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.numberOfTiles = numberOfTiles;
    }

    /**
     * Initializes the board with the given number of tiles.
     * The tiles are initialized in a reverse order,
     * so that the first tile is the last tile that is added to the board.
     * E.g. 100 tiles are added in the order 100, 99, 98, ..., 1.
     *
     * @param numberOfTiles the number of tiles to initialize
     */
    public void initBoard(int numberOfTiles) {
        Tile dummy = null;
        for (int i = numberOfTiles; i >= 1; i--) {
            Tile tile = new Tile(i);
            tile.setNextTile(dummy);
            addTile(tile);
            dummy = tile;
        }
    }

    /**
     * Adds tiles to the Map tilesList with their own distinct tileID.
     * tileID : Tile
     *
     * @param tile the tile to add
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
    public Tile getTile(int tileId) {
        return tilesList.get(tileId);
    }

    /**
     * Returns the number of tiles in the board.
     *
     * @return the number of tiles in the board
     */
    public int getNumberOfTiles() {
        return numberOfTiles;
    }

    /**
     * Returns the Map with all the tiles.
     *
     * @return the Map with all the tiles
     */
    public Map<Integer, Tile> getTilesList() {
        return tilesList;
    }

    /**
     * Returns the number of rows in the board.
     *
     * @return the number of rows in the board
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * Returns the number of columns in the board.
     *
     * @return the number of columns in the board
     */
    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    /**
     * Sets the Tile list Map as the given Map.
     *
     * @param tilesList the Map with all the tiles
     */
    public void setTilesList(Map<Integer, Tile> tilesList) {
        this.tilesList = tilesList;
    }


    /**
     * Returns the last tile of the board
     *
     * @return the last tile of the board
     */
    public Tile getLastTile() {
        return getTile(tilesList.size());
    }

    /**
     * Returns the first tile of the board
     *
     * @return the first tile of the board
     */
    public Tile getFirstTile() {
        return getTile(1);
    }
}
