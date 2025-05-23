package no.ntnu.idatg2003.mappe10.model;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

  private Player testPlayer;
  private BoardGame boardGame;

  @BeforeEach
  void setUp() {
    boardGame = new BoardGame();
    boardGame.createBoard(4, 1, 4);
    testPlayer = new Player("TestPlayer","playingpiece", boardGame);
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
    for (int i = 0; i < 4; i++) {
      testPlayer.move();
    }
    assertEquals(4, testPlayer.getCurrentTile().getTileId());
  }

  @Test
  void movePositiveTestNoOutOfBound() {
    testPlayer.placeOnTile(boardGame.getBoard().getTile(2));
    for (int i = 0; i < 3; i++) {
      testPlayer.move();
    }
    assertEquals(4, testPlayer.getCurrentTile().getTileId()); //Tile4 is the last tile
  }
  @Test
  void testPlayerInitialization() {
    assertEquals("TestPlayer", testPlayer.getName());
    assertEquals("playingpiece", testPlayer.getPlayingPiece());
    assertEquals(1500, testPlayer.getBalance());
    assertEquals(boardGame, testPlayer.getGame());
  }

  @Test
  void testSetPlayingPiece() {
    testPlayer.setPlayingPiece("hat");
    assertEquals("hat", testPlayer.getPlayingPiece());
  }

  @Test
  void testSkipTurnWhenTurnsToSkipIsPositive() {
    testPlayer.setTurnsToSkip(2);
    assertTrue(testPlayer.shouldSkipTurn());
  }

  @Test
  void testSkipTurnWhenTurnsToSkipIsZero() {
    assertFalse(testPlayer.shouldSkipTurn());
  }

  @Test
  void testDecrementSkipTurns() {
    testPlayer.setTurnsToSkip(2);
    testPlayer.decrementSkipTurns(); // 1 left
    assertTrue(testPlayer.shouldSkipTurn());

    testPlayer.decrementSkipTurns(); // 0 left
    assertFalse(testPlayer.shouldSkipTurn());
  }

  @Test
  void testSkipPrisonTurnSetsCorrectSkipCount() {
    testPlayer.skipPrisonTurn(3);
    assertTrue(testPlayer.shouldSkipTurn());
    testPlayer.decrementSkipTurns();
    testPlayer.decrementSkipTurns();
    testPlayer.decrementSkipTurns();
    assertFalse(testPlayer.shouldSkipTurn());
  }

  @Test
  void testAddToBalance() {
    testPlayer.addToBalance(250);
    assertEquals(1750, testPlayer.getBalance());
  }

  @Test
  void testSubtractFromBalance() {
    testPlayer.subtractFromBalance(500);
    assertEquals(1000, testPlayer.getBalance());
  }

  @Test
  void testCanAffordTrue() {
    assertTrue(testPlayer.canAfford(1000));
  }

  @Test
  void testCanAffordFalse() {
    assertFalse(testPlayer.canAfford(1600));
  }

  @Test
  void testGetFirstTile() {
    Player player = new Player("player", "apple", boardGame);
    player.placeOnTile(boardGame.getBoard().getFirstTile());
    assertEquals(1, player.getCurrentTile().getTileId());
  }

}