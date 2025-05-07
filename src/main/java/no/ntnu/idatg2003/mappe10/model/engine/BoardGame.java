package no.ntnu.idatg2003.mappe10.model.engine;

import no.ntnu.idatg2003.mappe10.model.board.Board;
import no.ntnu.idatg2003.mappe10.model.dice.Dice;
import no.ntnu.idatg2003.mappe10.model.player.Player;
import no.ntnu.idatg2003.mappe10.model.tile.Tile;
import no.ntnu.idatg2003.mappe10.model.board.BoardGameObserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoardGame {
  private Board board;
  private Player currentPlayer;
  private Player winner;
  private List<Player> playerList;
  private Dice dice;
  private final List<BoardGameObserver> observers = new ArrayList<>();

  /**
   * Initializes the game with the given number of dice and players.
   *
   * @param numberOfDice  the number of dice to use
   * @param numberOfTiles the number of tiles to use
   * @param rows          the number of rows in the board
   * @param columns       the number of columns in the board
   */
  public void initializeGame(int numberOfDice, int numberOfTiles, int rows, int columns) {
    createDice(numberOfDice);
    createPlayerList();
    createBoard(numberOfTiles, rows, columns);
  }

  /**
   * Places all players on the given tile.
   *
   * @param tile the tile object to place the players on
   */
  public void placeAllPlayersOnTile(Tile tile) {
    playerList.forEach(player -> player.placeOnTile(tile));
  }

  /**
   * Creates a new player list. Old player list is overwritten.
   */
  public void createPlayerList() {
    playerList = new ArrayList<>();
  }

  /**
   * Adds a player to the board game.
   *
   * @param player the player to add
   */
  public void addPlayer(Player player) {
    playerList.add(player);
  }

  /**
   * Creates a new board with the given number of tiles, rows and columns.
   * Old board is overwritten.
   */
  public void createBoard(int numberOfTiles, int rows, int columns) {
    board = new Board(numberOfTiles, rows, columns);
  }

  /**
   * Creates a new dice with the given number of sides.
   *
   * @param numberOfDice the number of dice to create
   */
  public void createDice(int numberOfDice) {
    dice = new Dice(numberOfDice);
  }

  /**
   * Plays the game. Each player in the player list rolls the dice and moves the number of steps rolled.
   */
  public boolean play() {
    for (Player player : playerList) {
      currentPlayer = player;
      int diceRoll = dice.roll();
      currentPlayer.move(diceRoll);
      notifyObservers(currentPlayer.getName(), currentPlayer.getCurrentTile().getTileId());
      if (playerWon()) {
        winner = currentPlayer;
        return false;
      }
    }
    return true;
  }

  /**
   * Play one round of the game for the CL version. Prints out the current player and the tile the player is on.
   */
  public boolean playCL() {
    for (Player player : playerList) {
      currentPlayer = player;
      int diceRoll = dice.roll();
      currentPlayer.move(diceRoll);
      if (playerWon()) {
        winner = currentPlayer;
        System.out.println("The winner is: " + getWinner().getName());
        return false;
      }
      System.out.println("Player: " + currentPlayer.getName() +
          " on tile " + currentPlayer.getCurrentTile().getTileId());
    }
    return true;
  }

  /**
   * Returns true if the current player has won the game, false if not.
   *
   * @return true if the current player has won the game
   */
  private boolean playerWon() {
    return currentPlayer.getCurrentTile().getTileId() == board.getLastTile().getTileId();
  }

  /**
   * Returns the player that has won the game.
   *
   * @return the player that has won the game
   */
  public Player getWinner() {
    return winner;
  }

  /**
   * Set the board of the BoardGame to a given Board object.
   *
   * @param board the Board object to load
   */
  public void loadBoard(Board board) {
    this.board = board;
  }

  /**
   * Returns the board of the board game.
   *
   * @return the board
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Return the iterator for the player list.
   *
   * @return the iterator for the player list
   */
  public Iterator<Player> getPlayerListIterator() {
    return playerList.iterator();
  }

  public void registerObserver(BoardGameObserver observer) {
    observers.add(observer);
  }

  public void removeObserver(BoardGameObserver observer) {
    observers.remove(observer);
  }

  private void notifyObservers(String playerName, int newPosition) {
    for (BoardGameObserver observer : observers) {
      observer.updatePosition(playerName, newPosition);
    }
  }
}




