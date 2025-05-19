package no.ntnu.idatg2003.mappe10.ui.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.ntnu.idatg2003.mappe10.model.board.Board;
import no.ntnu.idatg2003.mappe10.model.board.BoardGameFactory;
import no.ntnu.idatg2003.mappe10.ui.controller.PlayerSetupController;
import no.ntnu.idatg2003.mappe10.ui.controller.SoundController;
import org.w3c.dom.Text;

import java.util.*;

public class PlayerSetupView {
    private SoundController soundController;
    private PlayerSetupController controller;
    private final List<String> availablePieces = List.of("Apple", "Bee", "Computer", "Magic 8 Ball", "Trumpet");
    private final List<ComboBox<String>> pieceComboBoxes = new ArrayList<>();
    private final Map<ComboBox<String>, String> selectedPieces = new HashMap<>();


    public PlayerSetupView() {
        this.controller = new PlayerSetupController(this);
        this.soundController = SoundController.getInstance();
    }


    public void getPlayerSetupScene(Stage stage, int playerCount, String selectedBoard) {
        stage.setTitle("Choose Playing Piece");

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Choose Your Playing Piece");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        root.getChildren().add(titleLabel);

        Button continueButton = new Button("Continue");
        continueButton.setOnAction(e -> {
            soundController.playButtonSound();
            controller.doContinue(playerCount, root, pieceComboBoxes, selectedBoard);
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            soundController.playButtonSound();
            controller.doBack(stage);
        });

        VBox topSection = new VBox(10, backButton, continueButton, titleLabel);
        topSection.setAlignment(Pos.TOP_LEFT);

        root.getChildren().add(topSection);

        for (int i = 0; i < playerCount; i++) {
            HBox box = createPlayerSetupBox(i);
            root.getChildren().add(box);
        }

        ScrollPane scrollPane = new ScrollPane(root);

        Scene scene = new Scene(scrollPane, 800, 600);
        scene.getStylesheets().add((getClass().getResource("/css/style.css")).toExternalForm());
        stage.setScene(scene);
    }

    private HBox createPlayerSetupBox(int playerNumber) {
        HBox playerBox = new HBox(10);
        playerBox.setAlignment(Pos.CENTER);

        Label playerLabel = new Label("Player " + (playerNumber + 1) + ":");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter name");

        ComboBox<String> pieceComboBox = new ComboBox<>();
        pieceComboBoxes.add(pieceComboBox);
        pieceComboBox.getItems().addAll(availablePieces);
        pieceComboBox.setPromptText("Select a piece");

        ImageView pieceImageView = new ImageView();
        pieceImageView.setFitWidth(50);
        pieceImageView.setFitHeight(50);
        pieceImageView.setPreserveRatio(true);

        pieceComboBox.setOnAction(e -> {
            String newSelection = pieceComboBox.getValue();
            String oldSelection = selectedPieces.get(pieceComboBox);

            if (oldSelection != null) {
                for (ComboBox<String> otherBox : pieceComboBoxes) {
                    if (otherBox != pieceComboBox && !otherBox.getItems().contains(oldSelection)) {
                        otherBox.getItems().add(oldSelection);
                        FXCollections.sort(otherBox.getItems());
                    }
                }
            }

            selectedPieces.put(pieceComboBox, newSelection);

                // Remove new selection from all other combo boxes
                for (ComboBox<String> otherBox : pieceComboBoxes) {
                    if (otherBox != pieceComboBox) {
                        otherBox.getItems().remove(newSelection);
                    }
                }

            controller.getPlayingPieces(pieceComboBox, pieceImageView);
            soundController.playButtonSound();
        });

        playerBox.getChildren().addAll(playerLabel, nameField, pieceComboBox, pieceImageView);
        return playerBox;
    }

}




