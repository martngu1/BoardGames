package no.ntnu.idatg2003.mappe10.ui.controller;

import javafx.stage.Stage;
import no.ntnu.idatg2003.mappe10.ui.view.GameSetupView;
import no.ntnu.idatg2003.mappe10.ui.view.StartPageView;

public class StartPageController {
  private StartPageView startPageView;

  public StartPageController(StartPageView startPageView) {
    this.startPageView = startPageView;
  }

  public void doStartGame(Stage primaryStage) {
    GameSetupView setupView = new GameSetupView();
    setupView.start(new Stage());
    primaryStage.close();
  }
}
