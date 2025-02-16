package no.ntnu.idatg2003.mappe10.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

  private Player testPlayer;
  private Tile testTile1;
  private Tile testTile2;
  private Tile testTile3;
  private Tile testTile4;

  @BeforeEach
  void setUp() {
    BoardGame boardGame = new BoardGame();
    testPlayer = new Player("TestPlayer", boardGame); // BoardGame object is not needed for this test
    testTile1 = new Tile(1);
    testTile2 = new Tile(2);
    testTile3 = new Tile(3);
    testTile4 = new Tile(4);

    testTile1.setNextTile(testTile2);
    testTile2.setNextTile(testTile3);
    testTile3.setNextTile(testTile4);
  }

  @Test
  void moveCorrectAmountOfTiles() {
    testPlayer.placeOnTile(testTile1);
    testPlayer.move(3);
    assertEquals(testTile4.getTileId(), testPlayer.getCurrentTile().getTileId());
  }

}