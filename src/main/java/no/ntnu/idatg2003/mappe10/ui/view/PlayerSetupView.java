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
import no.ntnu.idatg2003.mappe10.ui.controller.PlayerSetupController;
import no.ntnu.idatg2003.mappe10.ui.controller.SoundController;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PlayerSetupView {
    private SoundController soundController;
    private PlayerSetupController controller;

    private final List<String> availablePieces = List.of("Apple", "Bee", "Computer", "Magic 8 Ball", "Trumpet");
    private final List<ComboBox<String>> pieceComboBoxes = new ArrayList<>();
    private final HashMap<String, String> playersAndPieces = new HashMap<>();

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
            playersAndPieces.clear();

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

                playersAndPieces.put(playerName, selectedPiece);
            }

            // Create BoardGameView and add players
            BoardGameView boardGameView = new BoardGameView();
            boardGameView.start(selectedBoard, playersAndPieces);
            Stage currentStage = (Stage) root.getScene().getWindow();
            currentStage.close(); // Close the player setup window
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            soundController.playButtonSound();
            GameSetupView gameSetupView = new GameSetupView();
            gameSetupView.start(stage);
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

        playerBox.getChildren().addAll(playerLabel, nameField, pieceComboBox, pieceImageView);
        return playerBox;
    }

}




