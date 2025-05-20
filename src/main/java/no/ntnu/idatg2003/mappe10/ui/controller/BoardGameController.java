package no.ntnu.idatg2003.mappe10.ui.controller;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import no.ntnu.idatg2003.mappe10.model.board.BoardGameObserver;
import no.ntnu.idatg2003.mappe10.model.coordinate.Coordinate;
import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;
import no.ntnu.idatg2003.mappe10.model.tile.Tile;
import no.ntnu.idatg2003.mappe10.model.tile.tileaction.LadderAction;
import no.ntnu.idatg2003.mappe10.model.tile.tileaction.PrisonAction;
import no.ntnu.idatg2003.mappe10.model.tile.tileaction.TileAction;
import no.ntnu.idatg2003.mappe10.ui.view.BoardGameView;
import no.ntnu.idatg2003.mappe10.ui.view.renderer.LadderGameRenderer;
import no.ntnu.idatg2003.mappe10.ui.view.renderer.Renderer;

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

  public Renderer initBoardGame(String selectedBoard, Canvas canvas) {
    roller = new Roller();
    if (selectedBoard.equals("Ladder Game")) {
      initLadderGame();
      return new LadderGameRenderer(this, canvas);
    }
    return null;
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
      // ADD LOGGER
      // boardGameView.addToLog("Player: " + playerName + " is already in the queue. Aborting...");
      return;
    }
    playerQueue.add(playerName);
  }

  public void playTurn() {
    if (roller == null){
      roller = new Roller();
    }

    boardGameView.setRollButtonEnabled(false);
    roller.start(() -> {
      String currentPlayerName = playerQueue.poll();
      boardGame.setCurrentPlayer(currentPlayerName);

      Player currentPlayer = boardGame.getCurrentPlayer();
      int diceValue = boardGame.rollDice();
      boardGameView.addToLog(currentPlayer.getName() + " rolled " + boardGame.getDieValue(0)
              + " and " + boardGame.getDieValue(1) + " for a total of " + diceValue);
      if (currentPlayer.shouldSkipTurn()) {
        if (rolledDouble()) {
          boardGameView.addToLog(currentPlayer.getName() + " rolled doubles and is released from prison!");
          play(currentPlayer, diceValue);
          currentPlayer.decrementSkipTurns();
        } else {
          boardGameView.addToLog(currentPlayer.getName() + " is in prison and skips this turn.");
          currentPlayer.decrementSkipTurns();
          displayDiceResults();
          playerQueue.add(currentPlayer.getName());
        }
      } else {
        play(currentPlayer, diceValue); // Regular turn
      }

      boardGameView.setCurrentPlayerLabel(playerQueue.peek());
    });

  }


  public boolean rolledDouble() {
    List<Integer> diceResults = new ArrayList<>();
    for (int i = 0; i < boardGame.getDiceAmount(); i++) {
      diceResults.add(boardGame.getDieValue(i));
    }
    // Check if all dice have the same value
    return diceResults.stream().distinct().count() == 1;
  }
  public void play(Player currentPlayer, int diceValue) {
    animatePlayerMove(diceValue, () -> {
      boardGame.performLandAction();
      playerQueue.add(currentPlayer.getName());
      boardGameView.setCurrentPlayerLabel(playerQueue.peek());
      boardGameView.setRollButtonEnabled(true);
    });
  }

  public void animatePlayerMove(int diceRoll, Runnable onFinished) {
    displayDiceResults();
    Timeline timeline = new Timeline();
    for (int i = 0; i < diceRoll; i++) {
      timeline.getKeyFrames().add(
            new KeyFrame(Duration.seconds(0.3*i), e -> boardGame.doTurn())
      );
    }
    timeline.setOnFinished(e -> {
      if (onFinished != null) onFinished.run();
    });
    timeline.play();
  }

  public void displayDiceResults() {
    List <Integer> diceResults = new ArrayList<>();
    for (int i = 0; i < boardGame.getDiceAmount(); i++) {
      int diceResult = boardGame.getDieValue(i);
      diceResults.add(diceResult);
    }
      boardGameView.showDiceResults(diceResults, boardGame.getDiceAmount());
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

  public int getDestinationTileId(int tileId) {
    LadderAction ladderAction = (LadderAction) boardGame.getTileById(tileId).getLandAction();
    return ladderAction.getDestinationTileId();
  }

  public Coordinate getCanvasCoords(int tileId, double maxWidth, double maxHeight) {
    Coordinate rc = boardGame.getBoard().getTile(tileId).getBoardCoords();
    Coordinate maxXY = new Coordinate(maxWidth, maxHeight);
    return boardGame.transformBoardToCanvas(rc, maxXY);
  }

  public String checkIfTileAction(int tileId) {
    TileAction action = boardGame.getTileById(tileId).getLandAction();
    if (action == null){
      return "";
    }
    if (action instanceof LadderAction) {
      return "Ladder";
    } else if (action instanceof PrisonAction) {
      return "Prison";
    }

    return "null";
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
