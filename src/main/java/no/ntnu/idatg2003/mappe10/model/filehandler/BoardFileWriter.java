package no.ntnu.idatg2003.mappe10.model.filehandler;

import no.ntnu.idatg2003.mappe10.model.board.Board;
import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.engine.GameType;

public interface BoardFileWriter {
    void writeBoard(String path, BoardGame boardGame);
}
