package no.ntnu.idatg2003.mappe10.ui.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import no.ntnu.idatg2003.mappe10.model.board.Board;
import no.ntnu.idatg2003.mappe10.model.board.BoardGameFactory;

public class GameSetupView {

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

        // Add more boards senere
        VBox boardOptions = new VBox(10, board1Radio);
        boardOptions.setPadding(new Insets(10, 0, 10, 0));

        Button continueButton = new Button("Continue");
        continueButton.setOnAction(e -> {
            int playerCount = playerSpinner.getValue();
            RadioButton selectedRadio = (RadioButton) boardToggleGroup.getSelectedToggle();
            String selectedBoard = selectedRadio.getText();

            Board board = null;
            BoardGameFactory factory = new BoardGameFactory();
            if (selectedBoard.equals("Ladder Game")) {
                board = factory.createLadderGame().getBoard();
            }

            if (board != null) {
                BoardGameView boardGameView = new BoardGameView();
                boardGameView.setBoard(board);
                boardGameView.setPlayerCount(playerCount);

                try {
                    boardGameView.start(new Stage());
                    stage.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        VBox rightBox = new VBox(10, boardLabel, boardOptions, continueButton);
        rightBox.setAlignment(Pos.CENTER_LEFT);
        rightBox.setPadding(new Insets(20));

        HBox layout = new HBox(40, leftBox, rightBox);
        layout.setPadding(new Insets(30));

        Scene scene = new Scene(layout, 450, 250);
        stage.setScene(scene);
        stage.show();
    }

}
