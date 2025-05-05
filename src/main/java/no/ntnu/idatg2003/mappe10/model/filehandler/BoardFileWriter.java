package no.ntnu.idatg2003.mappe10.model.filehandler;

import no.ntnu.idatg2003.mappe10.model.board.Board;

public interface BoardFileWriter {
    void writeBoard(String path, Board board);
}
