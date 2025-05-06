package no.ntnu.idatg2003.mappe10.model.board;

import no.ntnu.idatg2003.mappe10.model.tile.LadderAction;
import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.filehandler.gson.BoardFileReaderGson;

import java.util.List;


public class BoardGameFactory {

    private BoardFileReaderGson gsonReader;

    public BoardGameFactory() {
        this.gsonReader = new BoardFileReaderGson();
    }

    public Board createBoard1() {
        Board board = new Board(60,10, 10);

        // Add ladders to the board
        board.getTile(3).setLandAction(new LadderAction(10, "Climb to tile 10"));
        board.getTile(6).setLandAction(new LadderAction(14, "Climb to tile 14"));
        board.getTile(20).setLandAction(new LadderAction(38, "Climb to tile 38"));

        return board;
    }
}
