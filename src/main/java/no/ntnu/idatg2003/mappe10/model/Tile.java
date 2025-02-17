package no.ntnu.idatg2003.mappe10.model;

/**
 * Represents a tile on the board. Each tile has a unique id and a reference to the next tile. The
 * tile can also have an action that is performed when a player lands on it.
 */
public class Tile {

  private Tile nextTile;
  private final int tileId;
  private TileAction landAction;

  /**
   * Creates a new tile with the given id.
   * The next tile is set to null, and the land action is set to null.
   * The next tile and land action can be set with the setNextTile and setLandAction method.
   *
   * @param tileId the id of the tile
   */
  public Tile(int tileId) {
    this.tileId = tileId;
    this.nextTile = null;
    this.landAction = null;
  }

  /**
   * Performs the action of the tile when a player lands on it. If the tile has no action (null),
   * nothing happens.
   *
   * @param player the player that lands on the tile
   */
  public void landPlayer(Player player) {
    if (landAction != null) {
      landAction.performAction(player);
    }
  }

  /**
   * Places the player on the next tile of this tile.
   *
   * @param player the player to place on the next tile
   */
  public void leavePlayer(Player player) {
    player.placeOnTile(nextTile);
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
}
