package no.ntnu.idatg2003.mappe10.ui.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class StartPage {

    public void start(Stage primaryStage) {
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

        // Button actions
        startGameBtn.setOnAction(e -> {
            GameSetupView setupView = new GameSetupView();
            setupView.start(new Stage());
            primaryStage.close();
        });

        optionsBtn.setOnAction(e -> {
            System.out.println("fikse senere");
        });

        exitBtn.setOnAction(e -> primaryStage.close());

        // Layout
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(50));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(title, startGameBtn, optionsBtn, exitBtn);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
