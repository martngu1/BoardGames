package no.ntnu.idatg2003.mappe10.ui.controller;

import no.ntnu.idatg2003.mappe10.model.board.BoardGameObserver;
import no.ntnu.idatg2003.mappe10.model.coordinate.Coordinate;
import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;
import no.ntnu.idatg2003.mappe10.ui.view.BoardGameView;

import java.util.*;

public class BoardGameController {
  BoardGameView boardGameView;
  BoardGame boardGame;
  Queue<String> playerQueue;

  /**
   * Creates a new BoardGameController with the given BoardGameView.
   *
   * @param view the view to control
   */
  public BoardGameController(BoardGameView view) {
    this.boardGameView = view;
    this.boardGame = new BoardGame();
    this.playerQueue = new ArrayDeque<>();
  }

  public void initBoardGame(String selectedBoard) {
    if (selectedBoard.equals("Ladder Game")) {
      initLadderGame();
    }
  }

  public void initLadderGame() {
    boardGame = boardGame.getFactory().createLadderGame();
  }

  public void registerObserver(BoardGameObserver observer) {
    boardGame.registerObserver(observer);
  }

  public void addPlayerToQueue(String playerName) {
    if (playerQueue.contains(playerName)) {
      System.out.println("Player: " + playerName + " is already in the queue. Aborting...");
      return;
    }
    playerQueue.add(playerName);
  }

  public void playTurn() {
    boardGame.setCurrentPlayer(playerQueue.poll());
    boardGame.play();
    playerQueue.add(boardGame.getCurrentPlayer().getName());
    System.out.println("Current player: " + boardGame.getCurrentPlayer().getName());
  }

  public void placePlayerOnStartTile() {
    boardGame.placeAllPlayersOnTile(boardGame.getBoard().getTile(1));
  }

  public double getTileWidth(double canvasWidth) {
    return canvasWidth / boardGame.getBoard().getNumberOfColumns();
  }

  public double getTileHeight(double canvasHeight) {
    return canvasHeight / boardGame.getBoard().getNumberOfRows();
  }

  public int getNumberOfTiles() {
    return boardGame.getBoard().getNumberOfTiles();
  }

  public Coordinate getCanvasCoords(int tileId, double maxWidth, double maxHeight) {
    Coordinate rc = boardGame.getBoard().getTile(tileId).getBoardCoords();
    Coordinate maxXY = new Coordinate(maxWidth, maxHeight);
    return boardGame.transformBoardToCanvas(rc, maxXY);
  }

  public Iterator<Player> getPlayerListIterator() {
    return boardGame.getPlayerListIterator();
  }

  /**
   * Adds a new player to the game with the given name.
   *
   * @param playerName the name of the player
   */
  public void addPlayer(String playerName, String playingPiece) {
    new Player(playerName, playingPiece, boardGame);
  }
}