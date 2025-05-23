package no.ntnu.idatg2003.mappe10.model.board;

import no.ntnu.idatg2003.mappe10.model.tile.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;
    private final int numberOfTiles = 10;
    private final int rows = 2;
    private final int cols = 5;

    @BeforeEach
    void setUp() {
        board = new Board(numberOfTiles, rows, cols);
    }

    @Test
    void testBoardInitialization() {
        assertEquals(numberOfTiles, board.getNumberOfTiles());
        assertEquals(rows, board.getNumberOfRows());
        assertEquals(cols, board.getNumberOfColumns());
        assertEquals(numberOfTiles, board.getTilesList().size());
    }

    @Test
    void testGetTileReturnsCorrectTile() {
        Tile tile = board.getTile(5);
        assertNotNull(tile);
        assertEquals(5, tile.getTileId());
    }

    @Test
    void testAddTileAddsTileToMap() {
        Tile newTile = new Tile(11);
        board.addTile(newTile);
        assertEquals(newTile, board.getTile(11));
    }

    @Test
    void testGetFirstTileReturnsCorrectTile() {
        Tile first = board.getFirstTile();
        assertNotNull(first);
        assertEquals(1, first.getTileId());
    }

    @Test
    void testGetLastTileReturnsCorrectTile() {
        Tile last = board.getLastTile();
        assertNotNull(last);
        assertEquals(numberOfTiles, last.getTileId());
    }

    @Test
    void testSetTilesListOverridesTileMap() {
        Map<Integer, Tile> newMap = Map.of(
                99, new Tile(99),
                100, new Tile(100)
        );
        board.setTilesList(newMap);
        assertEquals(2, board.getTilesList().size());
        assertNotNull(board.getTile(99));
        assertNotNull(board.getTile(100));
    }

    @Test
    void testGetTileInvalidIdReturnsNull() {
        assertNull(board.getTile(-1));
        assertNull(board.getTile(0));
        assertNull(board.getTile(999));
    }


}
