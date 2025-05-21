package no.ntnu.idatg2003.mappe10.ui.controller;

import javafx.stage.Stage;
import no.ntnu.idatg2003.mappe10.ui.view.GameSetupView;
import no.ntnu.idatg2003.mappe10.ui.view.PlayerSetupView;
import no.ntnu.idatg2003.mappe10.ui.view.StartPageView;

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
    public void doBack(Stage stage) {
        try {
          StartPageView startPageView = new StartPageView();
          startPageView.start(stage);
        } catch (Exception ex) {
        ex.printStackTrace();
        }
    }
}

