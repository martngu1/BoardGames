package no.ntnu.idatg2003.mappe10.model.player;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.tile.Tile;

/**
 * Represents a player in a board game. A player has a name and a current tile.
 * TODO: Implement exception handling for invalid inputs.
 */
public class Player {

  private String name;
  private String playingPiece;
  private Tile currentTile;


  /**
   * Creates a new player with the given name and adds the player to the board game.
   *
   * @param name the name of the player
   * @param game the game the player is added to
   */
  public Player(String name, String playingPiece, BoardGame game) {
    this.name = name;
    this.playingPiece = playingPiece;
    game.addPlayer(this);
  }

  /**
   * Place the player on the given tile.
   */
  public void placeOnTile(Tile tile) {
    this.currentTile = tile;
  }

  /**
   * Move the player to the next tile.
   */
  public void move() {
    if (currentTile.getNextTile() == null) {
      this.placeOnTile(currentTile);
      return;
    }
    this.placeOnTile(currentTile.getNextTile());
  }

  /**
   * Returns the current tile the player is standing on.
   *
   * @return the current Tile object the player is standing on
   */
  public Tile getCurrentTile() {
    return currentTile;
  }

  /**
   * Returns the name of the player.
   *
   * @return the name of the player
   */
  public String getName(){
    return name;
  }

  /**
   * Get the playing piece of the player.
   *
   * @return the playing piece of the player as a String
   */
  public String getPlayingPiece(){
    return playingPiece;
  }
    /**
     * Set the playing piece of the player.
     *
     * @param playingPiece the new playing piece of the player
     */
  public void setPlayingPiece(String playingPiece){
    this.playingPiece = playingPiece;
  }

}

