package no.ntnu.idatg2003.mappe10;

import javafx.application.Application;
import javafx.stage.Stage;
import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.ui.view.BoardGameView;
import no.ntnu.idatg2003.mappe10.ui.view.StartPage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        StartPage startPage = new StartPage();
        startPage.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}