package no.ntnu.idatg2003.mappe10.ui.view;

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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerSetupView {

    private final List<String> availablePieces = List.of("Apple", "Bee", "Computer", "Magic 8 Ball", "Trumpet");
    private final List<ComboBox<String>> pieceComboBoxes = new ArrayList<>();
    private final List<String> selectedPieces = new ArrayList<>();
    private final List<String> selectedNames = new ArrayList<>();


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
            selectedPieces.clear();
            selectedNames.clear();

            for (int i = 0; i < playerCount; i++) {
                HBox playerBox = (HBox) root.getChildren().get(i + 1); // +1 accounts for topSection
                TextField nameField = (TextField) playerBox.getChildren().get(1);
                ComboBox<String> pieceBox = pieceComboBoxes.get(i);

                String playerName = nameField.getText();
                String selectedPiece = pieceBox.getValue();

                if (playerName.isEmpty() || selectedPiece == null) {
                    System.out.println("Please enter name and select a piece for all players.");
                    return;
                }
                System.out.println("Player " + (i + 1) + ": " + playerName + " with piece: " + selectedPiece);

                selectedNames.add(playerName);
                selectedPieces.add(selectedPiece);
            }

            // Create BoardGameView and add players
            BoardGameView boardGameView = new BoardGameView();
            boardGameView.setPlayerCount(playerCount);

            for (int i = 0; i < playerCount; i++) {
                boardGameView.addPlayer(selectedNames.get(i), selectedPieces.get(i));
            }

            boardGameView.start(selectedBoard);
            Stage currentStage = (Stage) root.getScene().getWindow();
            currentStage.close(); // Close the player setup window
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            GameSetupView gameSetupView = new GameSetupView();
            gameSetupView.start(stage);
        });

        VBox topSection = new VBox(10, backButton, continueButton, titleLabel); 
        topSection.setAlignment(Pos.TOP_LEFT);

        root.getChildren().add(topSection);

        for (int i = 0; i < playerCount; i++) {
            Label playerLabel = new Label("Player " + (i + 1) + ":");

            // Add name input field for each player
            TextField nameField = new TextField();
            nameField.setPromptText("Enter player name");

            // Maybe factor out to new method and call inside the loop
            ComboBox<String> pieceComboBox = new ComboBox<>();
            pieceComboBox.getItems().addAll(availablePieces);
            pieceComboBox.setPromptText("Select a piece");

            pieceComboBoxes.add(pieceComboBox);

            ImageView pieceImageView = new ImageView();
            pieceImageView.setFitWidth(50);
            pieceImageView.setFitHeight(50);
            pieceImageView.setPreserveRatio(true);

            pieceComboBox.setOnAction(e -> {
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
            });


            HBox box = new HBox(20, playerLabel, nameField, pieceComboBox, pieceImageView);
            box.setAlignment(Pos.CENTER_LEFT);

            root.getChildren().add(box);
        }

        ScrollPane scrollPane = new ScrollPane(root);

        Scene scene = new Scene(scrollPane, 800, 600);
        stage.setScene(scene);
    }


}

