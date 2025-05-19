package no.ntnu.idatg2003.mappe10.ui.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import no.ntnu.idatg2003.mappe10.ui.controller.SoundController;

public class OptionsView{

    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;

    private SoundController soundController;

    public void start(Stage stage){

        this.soundController = SoundController.getInstance();

        stage.setTitle("Options");
        stage.show();

        // Create UI elements for options
        Slider volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setMinorTickCount(2);

        volumeSlider.setValue(soundController.getVolume() * 100); // Set initial volume from SoundController
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            soundController.setVolume(newVal.doubleValue() / 100);
        });

        ToggleButton musicToggle = new ToggleButton("🔊 Music On");
        musicToggle.setSelected(true); // Music starts ON
        soundController.playBackgroundMusic();

        musicToggle.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (!isSelected) {
                musicToggle.setText("Music Off");
                soundController.stopBackgroundMusic();
            } else {
                musicToggle.setText("Music On");
                soundController.startBackgroundMusic();
            }
        });
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            soundController.playButtonSound();
            StartPageView startPageView = new StartPageView();
            startPageView.start(stage);
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(backButton,volumeSlider, musicToggle);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        stage.setScene(new Scene(layout, WINDOW_WIDTH, WINDOW_HEIGHT));
        layout.getStylesheets().add((getClass().getResource("/css/style.css")).toExternalForm());
    }
}
