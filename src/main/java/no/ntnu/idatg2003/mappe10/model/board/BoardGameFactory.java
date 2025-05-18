package no.ntnu.idatg2003.mappe10.model.board;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.filehandler.gson.BoardFileReaderGson;
import no.ntnu.idatg2003.mappe10.model.tile.LadderAction;
import no.ntnu.idatg2003.mappe10.ui.view.BoardGameView;


public class BoardGameFactory {

    private BoardFileReaderGson gsonReader;

    public BoardGameFactory() {
        this.gsonReader = new BoardFileReaderGson();
    }

    public BoardGame createLadderGame() {
        BoardGame boardGame = new BoardGame();
        boardGame.createDice(2);
        boardGame.createBoard(90, 9, 10);

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
