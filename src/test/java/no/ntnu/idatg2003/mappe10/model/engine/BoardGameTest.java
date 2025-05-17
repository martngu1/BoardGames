package no.ntnu.idatg2003.mappe10.model.engine;

import no.ntnu.idatg2003.mappe10.model.coordinate.Coordinate;
import no.ntnu.idatg2003.mappe10.model.player.Player;
import no.ntnu.idatg2003.mappe10.model.tile.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameTest {

  private BoardGame boardGame;

  @BeforeEach
  void setUp() {
    boardGame = new BoardGame();
  }

  @Test
  void placeAllPlayersOnTilePositiveTest() {
    Tile tile = new Tile(0);
    new Player("TestPlayer1", "",boardGame);
    new Player("TestPlayer2", "",boardGame);
    new Player("TestPlayer3", "",boardGame);

    boardGame.placeAllPlayersOnTile(tile);

    Iterator<Player> playerListIterator = boardGame.getPlayerListIterator();
    while (playerListIterator.hasNext()) {
      Player player = playerListIterator.next();
      assertEquals(tile, player.getCurrentTile());
    }
  }

  @Test
  void transformBoardToCanvasPositiveTestCorrectCoords() {
    Coordinate boardCoords = new Coordinate(1, 1);
    Coordinate canvasMaxCoords = new Coordinate(6, 6);
    boardGame.createBoard(1, 5, 5);

    Coordinate canvasCoords = boardGame.transformBoardToCanvas(boardCoords, canvasMaxCoords);

    assertEquals(1.5, canvasCoords.getX0()); // Expected x = 6/4*1 = 1.5
    assertEquals(4.5, canvasCoords.getX1()); // Expected y = 6-(6/4)*1 = 4.5

  }
}