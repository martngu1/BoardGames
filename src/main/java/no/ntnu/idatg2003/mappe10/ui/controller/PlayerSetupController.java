package no.ntnu.idatg2003.mappe10.ui.controller;

import javafx.stage.Stage;
import no.ntnu.idatg2003.mappe10.ui.view.BoardGameView;
import no.ntnu.idatg2003.mappe10.ui.view.PlayerSetupView;

public class PlayerSetupController {

    private PlayerSetupView playerSetupView;

    public PlayerSetupController(PlayerSetupView playerSetupView) {
        this.playerSetupView = playerSetupView;
    }

    public void doContinue(int playerCount, String selectedBoard, Stage stage) {
        try {
            BoardGameView boardGameView = new BoardGameView();
            stage.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
