package no.ntnu.idatg2003.mappe10.ui.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import no.ntnu.idatg2003.mappe10.ui.controller.SoundController;
import no.ntnu.idatg2003.mappe10.ui.controller.StartPageController;

import static java.lang.Thread.sleep;


public class StartPageView extends Application {
    private SoundController soundController;
    private StartPageController controller;
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        soundController = SoundController.getInstance();
        controller = new StartPageController(this);

        primaryStage.setTitle("Board Game");

        // Game Title
        Label title = new Label("Board Game");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        // Buttons
        Button startGameBtn = new Button("Start Game");
        Button optionsBtn = new Button("Options");
        Button exitBtn = new Button("Exit");

        startGameBtn.setPrefWidth(200);
        optionsBtn.setPrefWidth(200);
        exitBtn.setPrefWidth(200);

        soundController.playBackgroundMusic();

        // Button actions
        startGameBtn.setOnAction(e -> {
            soundController.playButtonSound();
            controller.doStartGame(primaryStage);
        });

        optionsBtn.setOnAction(e -> {
            soundController.playButtonSound();
            controller.doOptions(primaryStage);
        });

        exitBtn.setOnAction(e -> {
            primaryStage.close();

        });

        // Layout
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(50));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(title, startGameBtn, optionsBtn, exitBtn);

        Scene scene = new Scene(layout, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
