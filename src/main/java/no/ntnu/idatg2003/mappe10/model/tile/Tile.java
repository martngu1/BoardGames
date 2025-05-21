package no.ntnu.idatg2003.mappe10.model.tile;

import no.ntnu.idatg2003.mappe10.model.coordinate.Coordinate;
import no.ntnu.idatg2003.mappe10.model.tile.tileaction.TileAction;

/**
 * Represents a tile on the board. Each tile has a unique id and a reference to the next tile. The
 * tile can also have an action that is performed when a player lands on it.
 */
public class Tile {

    private Tile nextTile;
    private final int tileId;
    private TileAction landAction;
    private Coordinate boardCoords;
    private MonopolyTile monopolyTile;

    /**
     * Creates a new tile with the given id.
     * The next tile is set to null, and the land action is set to null.
     * The coordinates of the tile are pre-set to (0, 0).
     * The next tile, land action and xy coordinates can be set with the setNextTile, setLandAction and set-X/Y-Coordinate method.
     *
     * @param tileId the id of the tile
     */
    public Tile(int tileId) {
        this.tileId = tileId;
        this.nextTile = null;
        this.landAction = null;
        this.boardCoords = null;
    }

    /**
     * Connects this tile with the next tile.
     *
     * @param nextTile the next tile
     */
    public void setNextTile(Tile nextTile) {
        this.nextTile = nextTile;
    }

    /**
     * Sets the coordinates of the tile.
     *
     * @param r the row coordinates of the tile
     */
    public void setCoordinate(int r, int c) {
        this.boardCoords = new Coordinate(r, c);
    }

    /**
     * Returns the coordinates of the tile.
     *
     * @return the Coordinate object of the tile
     */
    public Coordinate getBoardCoords() {
        return this.boardCoords;
    }

    /**
     * Sets the action of the tile when a player lands on it.
     *
     * @param landAction the action to perform when a player lands on the tile
     */
    public void setLandAction(TileAction landAction) {
        this.landAction = landAction;
    }

    /**
     * Returns the id of the tile.
     *
     * @return the id of the tile
     */
    public int getTileId() {
        return this.tileId;
    }

    /**
     * Returns the next tile.
     *
     * @return the next Tile object
     */
    public Tile getNextTile() {
        return this.nextTile;
    }

    /**
     * Returns the TileAction of the tile.
     *
     * @return the TileAction of the tile
     */
    public TileAction getLandAction() {
        return landAction;
    }
    public void setMonopolyTile(MonopolyTile monopolyTile) {
        this.monopolyTile = monopolyTile;
    }

    public MonopolyTile getMonopolyTile() {
        return monopolyTile;
    }

}

