package no.ntnu.idatg2003.mappe10.model;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

  private Player testPlayer;
  private BoardGame boardGame;

  @BeforeEach
  void setUp() {
    boardGame = new BoardGame();
    boardGame.createBoard(4); // Creates a board with n tiles
    boardGame.createPlayerList();
    testPlayer = new Player("TestPlayer", boardGame);
    /*
    Tiles:
    -----------------
    | 1 | 2 | 3 | 4 |
    -----------------
    */
  }

  @Test
  void moveCorrectAmountOfTiles() {
    testPlayer.placeOnTile(boardGame.getBoard().getTile(1));
    testPlayer.move(3);
    assertEquals(4, testPlayer.getCurrentTile().getTileId());
  }

  @Test
  void movePositiveTestNoOutOfBound() {
    testPlayer.placeOnTile(boardGame.getBoard().getTile(2));
    testPlayer.move(4);
    assertEquals(4, testPlayer.getCurrentTile().getTileId()); //Tile4 is the last tile
  }
}