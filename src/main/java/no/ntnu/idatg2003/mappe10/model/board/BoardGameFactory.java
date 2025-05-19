package no.ntnu.idatg2003.mappe10.model.board;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.filehandler.gson.BoardFileReaderGson;
import no.ntnu.idatg2003.mappe10.model.tile.tileaction.LadderAction;
import no.ntnu.idatg2003.mappe10.model.tile.tileaction.PrisonAction;


/**
 * The BoardGameFactory class is responsible for creating instances of the
 * BoardGame class with specific configurations. It sets up the board, players,
 * and actions for the game.
 */
public class BoardGameFactory {

    private BoardFileReaderGson gsonReader;

    public BoardGameFactory() {
        this.gsonReader = new BoardFileReaderGson();
    }

    /**
     * Creates a BoardGame instance with a ladder game configuration.
     * The game has 90 tiles, 9 rows, and 10 columns. Some tiles are configured
     * with ladder actions.
     *
     * @return a configured BoardGame instance based on the ladder game
     */
    public BoardGame createLadderGame() {
        BoardGame boardGame = new BoardGame();
        boardGame.createDice(2);
        boardGame.createBoard(90, 9, 10);

        // Set up ladder actions on specific tiles
        boardGame.getBoard().getTile(3).setLandAction(new LadderAction(17, "Climb to tile 17" ));
        boardGame.getBoard().getTile(11).setLandAction(new LadderAction(25, "Climb to tile 25" ));
        boardGame.getBoard().getTile(29).setLandAction(new LadderAction(9, "Fall to tile 9" ));
        boardGame.getBoard().getTile(41).setLandAction(new LadderAction(21, "Fall to tile 21" ));
        boardGame.getBoard().getTile(46).setLandAction(new LadderAction(63, "Climb to tile 63" ));
        boardGame.getBoard().getTile(56).setLandAction(new LadderAction(33, "Fall to tile 33" ));
        boardGame.getBoard().getTile(72).setLandAction(new LadderAction(26, "Climb to tile 26" ));
        boardGame.getBoard().getTile(87).setLandAction(new LadderAction(55, "Fall to tile 55" ));

        // Set up prison action on board

        for (int i : new int[]{10, 32, 77}) {
            boardGame.getBoard().getTile(i).setLandAction(new PrisonAction("has been jailed for 1 turn" ));
        }

        // Set tile coords for canvas
        int rows = boardGame.getBoard().getNumberOfRows();
        int columns = boardGame.getBoard().getNumberOfColumns();
        int numberOfTiles = boardGame.getBoard().getLastTile().getTileId();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                // if row is even, tileId = r * columns + c + 1.
                // if row is odd, tileId = r * columns + (columns - c)
                // Help from ChatGPT to find the formula.
                int tileId = (r % 2 == 0) ? r * columns + c + 1 : r * columns + (columns - c);
                if (tileId > numberOfTiles) {
                    break;
                }
                boardGame.getBoard().getTile(tileId).setCoordinate(r, c);
            }
        }

        return boardGame;
    }
}
