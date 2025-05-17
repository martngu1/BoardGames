package no.ntnu.idatg2003.mappe10.ui.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import no.ntnu.idatg2003.mappe10.model.board.Board;
import no.ntnu.idatg2003.mappe10.model.board.BoardGameFactory;
import no.ntnu.idatg2003.mappe10.ui.controller.BoardGameController;
import no.ntnu.idatg2003.mappe10.ui.controller.GameSetupController;

public class GameSetupView {
    private GameSetupController controller;

    public GameSetupView() {
        this.controller = new GameSetupController(this);
    }

    public void start(Stage stage) {
        stage.setTitle("Game Setup");

        Label playersLabel = new Label("Number of Players:");
        Spinner<Integer> playerSpinner = new Spinner<>(1, 5, 2);
        VBox leftBox = new VBox(10, playersLabel, playerSpinner);
        leftBox.setAlignment(Pos.CENTER_LEFT);
        leftBox.setPadding(new Insets(20));

        Label boardLabel = new Label("Select Board:");
        ToggleGroup boardToggleGroup = new ToggleGroup();

        RadioButton board1Radio = new RadioButton("Ladder Game");
        board1Radio.setToggleGroup(boardToggleGroup);
        board1Radio.setSelected(true);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            StartPageView startPageView = new StartPageView();
            startPageView.start(stage);
        });


        // Add more boards senere
        VBox boardOptions = new VBox(10, board1Radio);
        boardOptions.setPadding(new Insets(10, 0, 10, 0));

        Button continueButton = new Button("Continue");
        continueButton.setOnAction(e -> {
            // Maybe use single textfield to get player names. Hard to do with spinner.
            int amountOfPlayers = playerSpinner.getValue();
            RadioButton selectedRadio = (RadioButton) boardToggleGroup.getSelectedToggle();
            controller.doContinue(selectedRadio.getText(), stage, amountOfPlayers);
        });

        VBox rightBox = new VBox(10, boardLabel, boardOptions, continueButton, backButton);
        rightBox.setAlignment(Pos.CENTER_LEFT);
        rightBox.setPadding(new Insets(20));

        HBox layout = new HBox(40, leftBox, rightBox);
        layout.setPadding(new Insets(30));

        Scene scene = new Scene(layout, 450, 250);
        stage.setScene(scene);
        stage.show();
    }

}
