package no.ntnu.idatg2003.mappe10.model.engine;

import no.ntnu.idatg2003.mappe10.model.board.Board;
import no.ntnu.idatg2003.mappe10.model.board.BoardGameFactory;
import no.ntnu.idatg2003.mappe10.model.coordinate.Coordinate;
import no.ntnu.idatg2003.mappe10.model.dice.Dice;
import no.ntnu.idatg2003.mappe10.model.filehandler.BoardFileWriter;
import no.ntnu.idatg2003.mappe10.model.filehandler.CSVFileHandler;
import no.ntnu.idatg2003.mappe10.model.filehandler.gson.BoardFileWriterGson;
import no.ntnu.idatg2003.mappe10.model.player.Player;
import no.ntnu.idatg2003.mappe10.model.tile.MonopolyTile;
import no.ntnu.idatg2003.mappe10.model.property.Property;
import no.ntnu.idatg2003.mappe10.model.tile.Tile;
import no.ntnu.idatg2003.mappe10.model.board.BoardGameObserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoardGame {
  private Board board;
  private Coordinate boardMax;
  private Player currentPlayer;
  private Player winner;
  private List<Player> playerList;
  private Dice dice;
  private List<BoardGameObserver> observers;
  private BoardGameFactory boardGameFactory;

  /**
   * Creates a new BoardGame object.
   * Note that a player list must be created after the object is constructed.
   */
  public BoardGame() {
    this.observers = new ArrayList<>();
    this.boardGameFactory = new BoardGameFactory();
    this.playerList = new ArrayList<>();
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
   * Adds a player to the board game.
   *
   * @param player the player to add
   */
  public void addPlayer(Player player) {
    playerList.add(player);
  }

  /**
   * Creates a new board with the given number of tiles, numberOfRows and numberOfColumns.
   * Old board is overwritten.
   */
  public void createBoard(int numberOfTiles, int numberOfRows, int numberOfColumns) {
    board = new Board(numberOfTiles, numberOfRows, numberOfColumns);
    boardMax = new Coordinate(numberOfRows - 1.0, numberOfColumns - 1.0);
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
   * Roll the dice and move the number of steps rolled for the current player.
   */
  public void doTurn() {
    currentPlayer.move();
    notifyObservers();
  }

    /**
     * Returns the sum of the values of all the rolled dice.
     *
     * @return the sum of the values of the rolled dice as an int
     */
  public int rollDice() {
    return dice.roll();
  }

    /**
     * Returns the value of a specific die.
     *
     * @param dieNumber the number of the die to get the value of
     * @return the value of the die as an int
     */
  public int getDieValue(int dieNumber) {
    return dice.getDie(dieNumber);
  }

    /**
     * Returns the number of dice in the game.
     *
     * @return the number of dice in the game as an int
     */
  public int getDiceAmount() {
    return dice.getNumberOfDice();
  }

    /**
     * Sets the current player to the player with the given name.
     *
     */
  public void setCurrentPlayer(String playerName) {
    playerList.stream()
          .filter(player -> player.getName().equals(playerName))
          .findFirst()
          .ifPresent(player -> currentPlayer = player);
  }

  public Tile getTileById(int tileId) {
    return board.getTile(tileId);
  }

  public void performLandAction() {
    Tile currentTile = currentPlayer.getCurrentTile();
    if (currentTile.getLandAction() != null) {
      currentTile.getLandAction().performAction(currentPlayer, this);

      String description = currentTile.getLandAction().getDescription();
      if (description != null) {
        notifyTileActionPerformed(currentPlayer.getName(), description);
      }

      notifyObservers();
    }
  }

  /**
   * Play one round of the game for the CL version. Prints out the current player and the tile the player is on.
   */
  public boolean playCL() {
    for (Player player : playerList) {
      currentPlayer = player;
      int diceRoll = dice.roll();
      for (int i = 0; i < diceRoll; i++) {
        currentPlayer.move();
      }
      if (playerWon()) {
        winner = currentPlayer;
        System.out.println("The winner is: " + getWinner().getName());
        return false;
      }
      System.out.println("Player: " + currentPlayer.getName()
            + " on tile " + currentPlayer.getCurrentTile().getTileId());
    }
    return true;
  }

  public Player getCurrentPlayer() {
    return currentPlayer;
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
   * Sets the winner of the game as the given Player.
   */
  public void setWinner(Player player) {
    winner = player;
    notifyGameOver();
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
   * Return the factory for the board game.
   *
   * @return the factory for the board game
   */
  public BoardGameFactory getFactory() {
    return boardGameFactory;
  }

    /**
     * Saves the board to a file in the specified format.
     *
     */
  public void saveBoard(String filePath, String formatType) {
    BoardFileWriter writer;
      if (formatType.equalsIgnoreCase("json")) {
          writer = new BoardFileWriterGson();
      } else {
          throw new IllegalArgumentException("Unsupported format type: " + formatType);
      }
    writer.writeBoard(filePath, this.board);
  }

    /**
     * Saves the players to a file in CSV format.
     *
     */
  public void savePlayers(String filePath) {
    CSVFileHandler csvFileHandler = new CSVFileHandler();
    csvFileHandler.savePlayers(filePath, playerList);
  }

    /**
     * Restarts the game by resetting the winner and current player.
     *
     */
  public void restartGame() {
    winner = null;
    currentPlayer = null;
  }

  /**
   * Returns the transformed coordinates from the board (r, c) to the canvas (x, y).
   *
   * @param rc the (r, c) coordinates to transform to (x, y)
   * @return the (x, y) coordinates corresponding to the given (r, c) coordinates
   */
  public Coordinate transformBoardToCanvas(Coordinate rc, Coordinate canvasMax) {
    return new Coordinate(
          canvasMax.getX0() / boardMax.getX1() * rc.getX1(),
          canvasMax.getX1() - canvasMax.getX1() / boardMax.getX0() * rc.getX0()
    );
  }
  /**
   * Starts the pass tile action for the given player.
   *
   */
  public void onPassStartTile(Player player){
    // Empty implementation. Override in subclasses if needed.
  }

    /**
     * Returns the total sell value of all properties owned by the given player.
     *
     * @param player the player to calculate the total sell value for
     * @return the total sell value of all properties owned by the player
     */
  public int getTotalSellValue(Player player){
    int totalValue = 0;
    int amountOfTiles = getBoard().getNumberOfTiles();
    for (int tileId = 1; tileId <= amountOfTiles; tileId++) {
      Tile tile = getBoard().getTile(tileId);

      MonopolyTile monopolyTile = tile.getMonopolyTile();
      if (monopolyTile != null) {
        Property property = monopolyTile.getProperty();
        if (property.getOwner() == player) {
          // Sell value of property is the property price divided by 2
          totalValue += property.getPrice() / 2;
        }
      }
    }
    return totalValue;
  }

  /**
   * Notifies all observers about the offer to buy a property.
   *
   */
  public void notifyOfferToBuyProperty(Player player, Property property) {
    for (BoardGameObserver observer : observers) {
      observer.onOfferToBuyProperty(player, property);
    }
  }
    /**
     * Notifies all observers about the offer to sell a property.
     *
     */
  public void notifyOfferToSellProperty(Player player, int amount) {
    for (BoardGameObserver observer : observers) {
      observer.onOfferToSellProperty(player, amount);
    }
  }

    /**
     * Notifies all observers about the balance update for a player.
     *
     */
  public void notifyBalanceUpdate(Player player) {
    for (BoardGameObserver observer : observers) {
      observer.onBalanceUpdate(player);
    }
  }

  /**
   * Removes a player from the game.
   *
   */
  public void removePlayer(Player player) {
    player.setBalance(0);
    playerList.remove(player);

    // Remove ownership of properties
    int amountOfTiles = getBoard().getNumberOfTiles();
    for (int tileId = 1; tileId <= amountOfTiles; tileId++) {
      Tile tile = getBoard().getTile(tileId);
      MonopolyTile monopolyTile = tile.getMonopolyTile();
      if (monopolyTile != null) {
        Property property = monopolyTile.getProperty();
        if (property.getOwner() == player) {
          property.setOwner(null);
        }
      }
    }

    notifyObservers();

    if (playerList.size() == 1) {
      Player lastPlayer = playerList.get(0);
      setWinner(lastPlayer); // Triggers notifyGameOver
    }
  }

  /**
   * Return the iterator for the player list.
   *
   * @return the iterator for the player list
   */
  public Iterator<Player> getPlayerListIterator() {
    return playerList.iterator();
  }
  /**
   * Registers an observer to receive updates.
   *
   */

  public void registerObserver(BoardGameObserver observer) {
    observers.add(observer);
  }

  public void removeObserver(BoardGameObserver observer) {
    observers.remove(observer);
  }
    /**
     * Returns the player with the given name.
     *
     * @param playerName the name of the player to find
     * @return the player with the given name, or null if not found
     */
  public Player getPlayerByName(String playerName) {
    return playerList.stream()
          .filter(player -> player.getName().equals(playerName))
          .findFirst()
          .orElse(null);
  }

  /**
   * Notifies all observers about a change to the game
   *
   */
  private void notifyObservers() {
    for (BoardGameObserver observer : observers) {
      observer.updatePosition();
    }
  }

    /**
     * Notifies all observers about a tile action performed by the current player.
     *
     * @param name the name of the player who performed the action
     * @param description a description of the action
     */
  public void notifyTileActionPerformed(String name, String description) {
    for (BoardGameObserver observer : observers) {
      observer.onTileAction(name, description);
    }
  }

    /**
     * Notifies all observers about the game over event.
     *
     */
  public void notifyGameOver() {
    for (BoardGameObserver observer : observers) {
      observer.onGameOver(winner.getName());
    }
  }
}




