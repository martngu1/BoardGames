package no.ntnu.idatg2003.mappe10.model.filehandler;

import no.ntnu.idatg2003.mappe10.model.board.Board;
import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;

public interface BoardFileReader {
    BoardType readBoard(String path);
}
