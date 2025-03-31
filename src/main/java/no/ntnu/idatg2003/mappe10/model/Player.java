package no.ntnu.idatg2003.mappe10.model;

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
  public Player(String name, BoardGame game) {
    this.name = name;
    game.addPlayer(this);
  }

  /**
   * Place the player on the given tile.
   */
  public void placeOnTile(Tile tile) {
    this.currentTile = tile;
  }

  /**
   * Moves the player a given number of steps on the board.
   *
   * @param steps the number of steps to move
   */
  public void move(int steps) {
    Tile tileDummy = currentTile;
    for (int i=0; i < steps; i++) {
      tileDummy = tileDummy.getNextTile();
    }
    this.placeOnTile(tileDummy);
  }

  /**
   * Returns the current tile the player is standing on.
   *
   * @return the current Tile object the player is standing on
   */
  public Tile getCurrentTile() {
    return currentTile;
  }
  public String getName(){
    return name;
  }

  public String getPlayingPiece(){
    return playingPiece;
  }

}

