package no.ntnu.idatg2003.mappe10.ui.controller;

import javafx.stage.Stage;
import no.ntnu.idatg2003.mappe10.model.board.Board;
import no.ntnu.idatg2003.mappe10.model.board.BoardGameFactory;
import no.ntnu.idatg2003.mappe10.ui.view.BoardGameView;
import no.ntnu.idatg2003.mappe10.ui.view.GameSetupView;

public class GameSetupController {
  private GameSetupView gameSetupView;

  public GameSetupController(GameSetupView view) {
    this.gameSetupView = view;
  }

  public void doContinue(String selectedBoard, Stage stage) {
    try {
      BoardGameView boardGameView = new BoardGameView();
      boardGameView.start(selectedBoard);
      stage.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}

