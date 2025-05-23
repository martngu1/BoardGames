package no.ntnu.idatg2003.mappe10.model.filehandler;

import no.ntnu.idatg2003.mappe10.model.board.Board;
import no.ntnu.idatg2003.mappe10.model.engine.GameType;

public class BoardType {
  private GameType gameType;
  private Board board;

  public BoardType(GameType gameType, Board board) {
    this.gameType = gameType;
    this.board = board;
  }

    public GameType getGameType() {
      return gameType;
    }

    public Board getBoard() {
      return board;
    }
}
