package no.ntnu.idatg2003.mappe10.ui.controller;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;



public class SoundController {
    private MediaPlayer backgroundMusicPlayer;
    private final String basePath = "/sounds/";
    private final String bgMusicPath = basePath + "background_music.mp3";
    private MediaPlayer mediaPlayer;
    private static SoundController instance;

    public static SoundController getInstance() {
        if (instance == null) {
            instance = new SoundController();
        }
        return instance;
    }

    public void playDiceRollSound() {
        try {
            String path = basePath + "dice_roll.mp3";
            mediaPlayer = new MediaPlayer(new Media(getClass().getResource(path).toString()));

            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }

    }

    public void playButtonSound() {
        try {
            String path = basePath + "button_press.mp3";
            mediaPlayer = new MediaPlayer(new Media(getClass().getResource(path).toString()));

            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }

    public void playBackgroundMusic() {
        try {
            if (backgroundMusicPlayer == null) {
                Media bgMusic = new Media(getClass().getResource(bgMusicPath).toString());
                backgroundMusicPlayer = new MediaPlayer(bgMusic);
                backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                backgroundMusicPlayer.setVolume(0.2);
                backgroundMusicPlayer.play();
            } else {
                System.out.println("Background music is already playing.");
            }
        } catch (Exception e) {
            System.out.println("Error playing background music: " + e.getMessage());
        }
    }

    public void quitSound() {
        try {
            String path = basePath + "quit_sound.mp3";
            mediaPlayer = new MediaPlayer(new Media(getClass().getResource(path).toString()));

            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }

    public void playWinSound() {
        try {
            String path = basePath + "win_sound.mp3";
            mediaPlayer = new MediaPlayer(new Media(getClass().getResource(path).toString()));
            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
    }
    public void startBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.play();
        }
    }

    public double getVolume() {
        if (backgroundMusicPlayer != null) {
            return backgroundMusicPlayer.getVolume();
        }
        return 0.0;
    }

    public void setVolume(double v) {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(v);
        }
    }
}







