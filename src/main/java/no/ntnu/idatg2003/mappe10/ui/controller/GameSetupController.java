package no.ntnu.idatg2003.mappe10.ui.controller;

import javafx.stage.Stage;
import no.ntnu.idatg2003.mappe10.ui.view.BoardGameView;
import no.ntnu.idatg2003.mappe10.ui.view.GameSetupView;
import no.ntnu.idatg2003.mappe10.ui.view.StartPageView;

import java.util.HashMap;

public class GameSetupController {

    private GameSetupView gameSetupView;
    private final HashMap<String, String> playersAndPieces = new HashMap<>();

  public GameSetupController(GameSetupView gameSetupView) {
    this.gameSetupView = gameSetupView;
  }

  public void addPlayerPiece(String playerName, String piece) {
    if (playerName == null || playerName.isEmpty() || piece == null || piece.isEmpty()) {
      return;
    }
    playersAndPieces.put(playerName, piece);
  }

  public void doContinue(Stage currentStage, String selectedBoard) {
    try {
      // Create BoardGameView and add players
      BoardGameView boardGameView = new BoardGameView();
      boardGameView.start(selectedBoard, playersAndPieces);
      currentStage.close(); // Close the player setup window
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
