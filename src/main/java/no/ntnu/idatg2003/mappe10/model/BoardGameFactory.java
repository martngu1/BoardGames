package no.ntnu.idatg2003.mappe10.model;

import no.ntnu.idatg2003.mappe10.model.filehandler.JSONFileHandler;



public class BoardGameFactory {

    private JSONFileHandler jsonFileHandler;
    private Board board;

    public BoardGameFactory() {
        this.jsonFileHandler = new JSONFileHandler();
    }

    public Board createBoard1() {

        BoardGame boardGame = new BoardGame();
        boardGame.createBoard(60);
        boardGame.createDice(1);

        // Add ladders to the board
        board.getTile(3).setLandAction(new LadderAction(10, "Climb to tile 10"));
        board.getTile(6).setLandAction(new LadderAction(14, "Climb to tile 14"));
        board.getTile(20).setLandAction(new LadderAction(38, "Climb to tile 38"));

        return boardGame.getBoard();
    }
}
