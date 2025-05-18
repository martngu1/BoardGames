package no.ntnu.idatg2003.mappe10.ui.controller;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;




public class SoundController {
    private MediaPlayer backgroundMusicPlayer;
    private final String bgMusicPath = "/sounds/background_music.mp3";
    private MediaPlayer mediaPlayer;
    private static SoundController instance;

    public static SoundController getInstance() {
        if (instance == null) {
            instance = new SoundController();
        }
        return instance;
    }

    public void playDiceRollSound() {
        try{
            String path = "/sounds/dice_roll.mp3";
            mediaPlayer = new MediaPlayer(new Media(getClass().getResource(path).toString()));

            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }

    }
    public void playButtonSound() {
        try{
            String path = "/sounds/button_press.mp3";
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

    public void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
    }
}


