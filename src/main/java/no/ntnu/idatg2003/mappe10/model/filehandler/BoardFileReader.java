package no.ntnu.idatg2003.mappe10.model.filehandler;

import no.ntnu.idatg2003.mappe10.model.Board;

public interface BoardFileReader {
    Board readBoard(String path);
}
