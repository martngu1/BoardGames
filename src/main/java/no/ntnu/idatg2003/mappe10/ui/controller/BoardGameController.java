package no.ntnu.idatg2003.mappe10.ui.controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import no.ntnu.idatg2003.mappe10.model.coordinate.Coordinate;
import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;
import no.ntnu.idatg2003.mappe10.model.tile.Tile;
import no.ntnu.idatg2003.mappe10.ui.view.BoardGameView;

public class BoardGameController {
  BoardGameView boardGameView;
  BoardGame boardGame;

  /**
   * Creates a new BoardGameController with the given BoardGameView.
   *
   * @param view the view to control
   */
  public BoardGameController(BoardGameView view) {
    this.boardGameView = view;
    this.boardGame = new BoardGame();
  }

  public void initBoardGame(String selectedBoard) {
    if (selectedBoard.equals("Ladder Game")) {
    initLadderGame();
    }
  }

  public void initLadderGame() {
    boardGame = boardGame.getFactory().createLadderGame();
    boardGame.placeAllPlayersOnTile(boardGame.getBoard().getTile(1));
  }

  /**
   * Adds a new player to the game with the given name.
   *
   * @param playerName the name of the player
   */
  public void addPlayer(String playerName) {
    new Player(playerName, boardGame);
  }

  public void drawCurrentBoard(Canvas canvas) {
    double width = canvas.getWidth();
    double height = canvas.getHeight();

    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.clearRect(0, 0, width, height);

    gc.setFill(Color.LIGHTGRAY);
    gc.fillRect(0,0, width, height);

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
    }
  }
}