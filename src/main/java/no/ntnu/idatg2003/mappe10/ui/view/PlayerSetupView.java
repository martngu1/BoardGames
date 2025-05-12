package no.ntnu.idatg2003.mappe10.ui.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class PlayerSetupView {

    private final List<String> availablePieces = List.of("trumpet", "monkey", "pikachu");

    public Scene getPlayerSetupScene(Stage stage, int playerCount) {
        stage.setTitle("Choose Playing Piece");

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Choose Your Playing Piece");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        root.getChildren().add(titleLabel);

        Button continueButton = new Button("Continue");
        continueButton.setOnAction(e -> {
            // Create boardgameview, make sure all players have selected a piece
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            GameSetupView gameSetupView = new GameSetupView();
            Scene gameSetupScene = gameSetupView.getGameSetupScene(stage);
            stage.setScene(gameSetupScene);
        });

        VBox topSection = new VBox(10, backButton, continueButton, titleLabel);
        topSection.setAlignment(Pos.TOP_LEFT);

        root.getChildren().add(topSection);

        for (int i = 0; i < playerCount; i++) {
            Label playerLabel = new Label("Player " + (i + 1) + ":");


            // Maybe factor out to new method and call inside the loop
            ComboBox<String> pieceComboBox = new ComboBox<>();
            pieceComboBox.getItems().addAll(availablePieces);
            pieceComboBox.setPromptText("Select a piece");

            ImageView pieceImageView = new ImageView();
            pieceImageView.setFitWidth(50);
            pieceImageView.setFitHeight(50);
            pieceImageView.setPreserveRatio(true);

            pieceComboBox.setOnAction(e -> {
                String selected = pieceComboBox.getValue();
                if (selected != null) {
                    try {
                        String path = "/playingPieces/" + selected + ".jpg";
                        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
                        pieceImageView.setImage(image);
                    } catch (Exception ex) {
                        pieceImageView.setImage(null);
                        System.err.println("Could not load image for: " + selected);
                    }
                }
            });


            HBox box = new HBox(20, playerLabel, pieceComboBox, pieceImageView);
            box.setAlignment(Pos.CENTER_LEFT);

            root.getChildren().add(box);
        }

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        return scene;
    }
}

