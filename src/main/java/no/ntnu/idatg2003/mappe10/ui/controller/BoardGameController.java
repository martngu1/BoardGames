package no.ntnu.idatg2003.mappe10.ui.controller;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import no.ntnu.idatg2003.mappe10.model.board.BoardGameObserver;
import no.ntnu.idatg2003.mappe10.model.coordinate.Coordinate;
import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;
import no.ntnu.idatg2003.mappe10.model.tile.Tile;
import no.ntnu.idatg2003.mappe10.ui.view.BoardGameView;

import java.io.InputStream;
import java.util.*;

public class BoardGameController {
  private BoardGameView boardGameView;
  private BoardGame boardGame;
  private Queue<String> playerQueue;
  private Roller roller;


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
    roller = new Roller();
    if (selectedBoard.equals("Ladder Game")) {
      initLadderGame();
    }
  }

  public void arrangePlayerTurns() {
    // Sets the current player to the first player in the queue but does not remove them from the queue
    boardGame.setCurrentPlayer(playerQueue.peek());
    boardGameView.setCurrentPlayerLabel(boardGame.getCurrentPlayer().getName());
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
    if (roller == null){
      roller = new Roller();
    }

    roller.start(() -> {
      boardGame.setCurrentPlayer(playerQueue.poll());
      boardGame.play();
      displayDiceResults();
      playerQueue.add(boardGame.getCurrentPlayer().getName());
      boardGameView.setCurrentPlayerLabel(playerQueue.peek());
      System.out.println("Current player: " + boardGame.getCurrentPlayer().getName());
    });
  }

  public void displayDiceResults() {
    List <Integer> diceResults = new ArrayList<>();
    for (int i = 0; i < boardGame.getDiceAmount(); i++) {
      int diceResult = boardGame.getDieValue(i);
      diceResults.add(diceResult);
    }
      boardGameView.showDiceResults(diceResults, boardGame.getDiceAmount());
  }


  public String getCurrentPlayerName() {
    return boardGame.getCurrentPlayer().getName();
  }

  public void placePlayerOnStartTile() {
    boardGame.placeAllPlayersOnTile(boardGame.getBoard().getTile(1));
  }

  /**
   * Adds a new player to the game with the given name.
   *
   * @param playerName the name of the player
   */
  public void addPlayer(String playerName, String playingPiece) {
    new Player(playerName, playingPiece, boardGame);
  }

  public void drawCurrentBoard(Canvas canvas) {
    double width = canvas.getWidth();
    double height = canvas.getHeight();

    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.clearRect(0, 0, width, height);

    gc.setFill(Color.LIGHTGRAY);
    gc.fillRect(0, 0, width, height);

    double tileWidth = width / boardGame.getBoard().getNumberOfColumns();
    double tileHeight = height / boardGame.getBoard().getNumberOfRows();

    double offsetWidth = width - tileWidth;
    double offsetHeight = height - tileHeight;

    // Draw the board
    for (Tile tile : boardGame.getBoard().getTilesList().values()) {
      Coordinate rc = tile.getBoardCoords();
      Coordinate maxXY = new Coordinate(offsetWidth, offsetHeight);

      Coordinate canvasCoord = boardGame.transformBoardToCanvas(rc, maxXY);

      double x = canvasCoord.getX0();
      double y = canvasCoord.getX1();

      gc.setStroke(Color.BLACK);
      gc.strokeRect(x, y, tileWidth, tileHeight);
      gc.strokeText(String.valueOf(tile.getTileId()), x + tileWidth / 2, y + tileHeight / 2);
      // Draw the player on the tile
      drawPlayers(tile, gc, x, y, tileWidth, tileHeight);
    }
  }


  private void drawPlayers(Tile tile, GraphicsContext gc, double x, double y, double tileWidth, double tileHeight) {
    Iterator<Player> playerIterator = boardGame.getPlayerListIterator();
    while (playerIterator.hasNext()) {
      Player player = playerIterator.next();
      if (player.getCurrentTile().getTileId() == tile.getTileId()) {
        String imagePath = "/playingPieces/" + player.getPlayingPiece() + ".png";
        InputStream inputStream = getClass().getResourceAsStream(imagePath);
        if (inputStream == null) {
          System.out.println("Image not found: " + imagePath);
          return;
        }
        Image image = new Image(inputStream);
        gc.drawImage(image, x, y, tileWidth / 2, tileHeight / 2);
      }
    }
  }


  /**
   * Animation for rolling the dice
   *
   * <p>This class is based on the example provided by Mark Goadrich:
   * <a href="https://www.youtube.com/watch?v=WZmKAMSMTPo">Pig Dice Game using JavaFX - 6: Animating the Die Roll</a></p>
   */
  private class Roller extends AnimationTimer {
    private long startTime;
    private long lastUpdate;
    private Runnable onFinished;

    public void start(Runnable onFinished) {
      this.onFinished = onFinished;
      startTime = System.nanoTime();
      lastUpdate = startTime;
      boardGameView.setDiceBoxVisible(true);
      super.start();
    }

    @Override
    public void handle(long now) {
      if (now - startTime > 1_000_000_000L) { // 1 second duration
        stop();
        if (onFinished != null) onFinished.run(); // Do real roll after animation ends
        return;
      }

      if (now - lastUpdate > 100_000_000L) { // update every 100ms
        int diceAmount = boardGame.getDiceAmount();
        List <Integer> diceResults = new ArrayList<>();
        for (int i = 0; i < diceAmount; i++) {
          diceResults.add(1 + (int) (Math.random() * 6));
        }
          boardGameView.showDiceResults(diceResults,boardGame.getDiceAmount() );
          lastUpdate = now;
        }
      }
    }
  }
