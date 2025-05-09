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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class AddPlayerView {

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Add New Player");
        primaryStage.initModality(Modality.APPLICATION_MODAL);

        // Title label
        Label titleLabel = new Label("Add a New Player");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Name input
        Label nameLabel = new Label("Player Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter player name");

        // Playing piece selection
        Label pieceLabel = new Label("Choose Playing Piece:");
        ComboBox<String> pieceComboBox = new ComboBox<>();
        pieceComboBox.getItems().addAll("Knight", "Wizard", "Elf", "Orc", "Robot");
        pieceComboBox.setPromptText("Select piece");

        ImageView pieceImageView = new ImageView();
        pieceImageView.setFitWidth(100);
        pieceImageView.setFitHeight(100);
        pieceImageView.setPreserveRatio(true);

        pieceComboBox.setOnAction(e -> {
            String selectedPiece = pieceComboBox.getValue();
            if (selectedPiece != null) {
                try {
                    String path = "/playingPieces/" + selectedPiece + ".jpg";
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
                    pieceImageView.setImage(image);
                } catch (Exception ex) {
                    pieceImageView.setImage(null);
                    System.err.println("Could not load image for: " + selectedPiece);
                }
            }
        });

        // Submit button
        Button addButton = new Button("Add Player");
        addButton.setPrefWidth(150);

        // Layout
        VBox formLayout = new VBox(12);
        formLayout.setPadding(new Insets(25));
        formLayout.setAlignment(Pos.CENTER);
        formLayout.getChildren().addAll(
                titleLabel,
                nameLabel, nameField,
                pieceLabel, pieceComboBox,
                pieceImageView,
                addButton
        );

        // Scene setup
        Scene scene = new Scene(formLayout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}

