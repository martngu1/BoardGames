package no.ntnu.idatg2003.mappe10.model.player;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.tile.Tile;



/**
 * Represents a player in a board game. A player has a name and a current tile.
 * TODO: Implement exception handling for invalid inputs.
 */
public class Player {

  private String name;
  private int balance;
  private String playingPiece;
  private Tile currentTile;
  private BoardGame game;
  private int turnsToSkip;


  /**
   * Creates a new player with the given name and adds the player to the board game.
   *
   * @param name the name of the player
   * @param game the game the player is added to
   */
  public Player(String name, String playingPiece, BoardGame game) {
    this.name = name;
    this.playingPiece = playingPiece;
    this.game = game;
    game.addPlayer(this);
    this.turnsToSkip = 0;
    this.balance = 1500;
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
    Tile oldTile = currentTile;
    Tile nextTile = currentTile.getNextTile();

    if (nextTile == null) {
      this.placeOnTile(currentTile);
      return;
    }

    this.placeOnTile(nextTile);

    if (nextTile.getTileId() == 1 || nextTile.getTileId() < oldTile.getTileId()) {
      game.onPassStartTile(this);
    }
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

    /**
     * Returns if the player should skip their turn.
     *
     * @return true if the player should skip their turn, false otherwise
     */
  public boolean shouldSkipTurn() {
    return turnsToSkip > 0;
  }
    /**
     * Adds the given number of turns to skip to the player.
     *
     */
  public void setTurnsToSkip(int count) {
    for (int i = 0; i < count; i++) {
      turnsToSkip++;
    }
  }
    /**
     * Sets the number of turns to skip for the player.
     *
     * @param count the number of turns to skip
     */
  public void skipPrisonTurn(int count) {
    turnsToSkip = count;
  }
    /**
     * Decrements the number of turns to skip for the player.
     *
     */
  public void decrementSkipTurns() {
    if (turnsToSkip > 0) {
      turnsToSkip--;
    }
  }
    /**
     * Gets the balance of the player.
     *
     * @return the balance of the player as an int
     */
  public int getBalance() {
    return balance;
  }
    /**
     * Sets the balance of the player.
     *
     * @param balance the new balance of the player
     */
  public void setBalance(int balance) {
      this.balance = balance;
  }
    /**
     * Adds the given amount to the player's balance.
     *
     * @param amount the amount to add to the balance
     */
  public void addToBalance(int amount) {
      this.balance += amount;
  }
    /**
     * Subtracts the given amount from the player's balance.
     *
     * @param amount the amount to subtract from the balance
     */
  public void subtractFromBalance(int amount) {
      this.balance -= amount;
  }
    /**
     * Checks if the player can afford the given amount.
     *
     * @param amount the amount to check
     * @return true if the player can afford the amount, false otherwise
     */
  public boolean canAfford(int amount) {
    return balance >= amount;
  }
    /**
     * Returns the game the player is playing.
     *
     * @return the game the player is playing
     */
  public BoardGame getGame() {
    return game;
  }
}

