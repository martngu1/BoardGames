package no.ntnu.idatg2003.mappe10.ui.controller;

import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import no.ntnu.idatg2003.mappe10.ui.view.Alerts;
import no.ntnu.idatg2003.mappe10.ui.view.BoardGameView;
import no.ntnu.idatg2003.mappe10.ui.view.GameSetupView;
import no.ntnu.idatg2003.mappe10.ui.view.PlayerSetupView;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PlayerSetupController {

    private PlayerSetupView playerSetupView;
    private final HashMap<String, String> playersAndPieces = new HashMap<>();

    public PlayerSetupController(PlayerSetupView playerSetupView) {
        this.playerSetupView = playerSetupView;
    }

    public void doContinue(int playerCount, VBox root, List<ComboBox<String>> pieceComboBoxes, String selectedBoard) {
        try {
            // Validate player names and selected pieces
            playersAndPieces.clear();

            for (int i = 0; i < playerCount; i++) {
                HBox playerBox = (HBox) root.getChildren().get(i + 1); // +1 accounts for topSection
                TextField nameField = (TextField) playerBox.getChildren().get(1);
                ComboBox<String> pieceBox = pieceComboBoxes.get(i);

                String playerName = nameField.getText();
                String selectedPiece = pieceBox.getValue();

                if (playerName.isEmpty() || selectedPiece == null) {
                    Alerts alerts = new Alerts();
                    alerts.showAlert("Invalid Input", "Please enter a name and select a piece for all players.",
                            "All players must have a name and a selected piece.");
                    return;
                }

                playersAndPieces.put(playerName, selectedPiece);
            }

            // Create BoardGameView and add players
            BoardGameView boardGameView = new BoardGameView();
            boardGameView.start(selectedBoard, playersAndPieces);
            Stage currentStage = (Stage) root.getScene().getWindow();
            currentStage.close(); // Close the player setup window
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void getPlayingPieces(ComboBox<String> pieceComboBox, ImageView pieceImageView) {
        String selected = pieceComboBox.getValue();
        if (selected != null) {
            try {
                String path = "/playingPieces/" + selected + ".png";
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
                pieceImageView.setImage(image);
            } catch (Exception ex) {
                pieceImageView.setImage(null);
                System.err.println("Could not load image for: " + selected);
            }
        }
    }
    public void doBack(Stage stage) {
        try {
            GameSetupView gameSetupView = new GameSetupView();
            gameSetupView.start(stage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
