package no.ntnu.idatg2003.mappe10.ui.controller;

import javafx.stage.Stage;
import no.ntnu.idatg2003.mappe10.model.board.Board;
import no.ntnu.idatg2003.mappe10.model.board.BoardGameFactory;
import no.ntnu.idatg2003.mappe10.ui.view.BoardGameView;
import no.ntnu.idatg2003.mappe10.ui.view.GameSetupView;
import no.ntnu.idatg2003.mappe10.ui.view.PlayerSetupView;

public class GameSetupController {
  private GameSetupView gameSetupView;

  public GameSetupController(GameSetupView view) {
    this.gameSetupView = view;
  }

  public void doContinue(String selectedBoard, Stage stage, int playerCount) {
    try {
      PlayerSetupView playerSetupView = new PlayerSetupView();
      playerSetupView.getPlayerSetupScene(stage, playerCount, selectedBoard);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}

