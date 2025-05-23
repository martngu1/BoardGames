package no.ntnu.idatg2003.mappe10.ui.controller;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import no.ntnu.idatg2003.mappe10.exceptions.BoardGameInitException;
import no.ntnu.idatg2003.mappe10.exceptions.BoardWriteException;
import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.engine.GameType;
import no.ntnu.idatg2003.mappe10.model.filehandler.BoardFileReader;
import no.ntnu.idatg2003.mappe10.model.filehandler.CSVFileHandler;
import no.ntnu.idatg2003.mappe10.model.filehandler.gson.BoardFileReaderGson;
import no.ntnu.idatg2003.mappe10.ui.view.BoardGameView;
import no.ntnu.idatg2003.mappe10.ui.view.GameSetupView;
import no.ntnu.idatg2003.mappe10.ui.view.StartPageView;

import java.io.File;
import java.util.HashMap;

public class GameSetupController {

  private GameSetupView gameSetupView;
  private final HashMap<String, String> playersAndPieces = new HashMap<>();
  private String loadBoardGamePath;

  public GameSetupController(GameSetupView gameSetupView) {
    this.gameSetupView = gameSetupView;
  }

  public void addPlayerPiece(String playerName, String piece) {
    playersAndPieces.put(playerName, piece);
  }

  public void doContinue(Stage currentStage, GameType selectedBoard) {
    try {
      // Create BoardGameView and add players
      BoardGameView boardGameView = new BoardGameView();
      boardGameView.start(selectedBoard, playersAndPieces, loadBoardGamePath);
      currentStage.close(); // Close the player setup window
    } catch (BoardGameInitException e) {
      gameSetupView.doShowInvalidFileActionAlert();
    } catch (Exception e) {
      gameSetupView.doShowInvalidInputAlert();
    }
  }

  public void doLoadPlayers() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Load Players");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    File file = fileChooser.showOpenDialog(null);

    if (file != null) {
      try {
        gameSetupView.clearPlayerList();
        CSVFileHandler csvFileHandler = new CSVFileHandler();
        HashMap<String, String> playerPiece =
              (HashMap<String, String>) csvFileHandler.loadPlayers(file.getAbsolutePath());

        playerPiece.keySet().forEach(playerName -> {
          gameSetupView.addPlayerToPlayerList(playerName, playerPiece.get(playerName));
        });
      } catch (Exception e) {
        gameSetupView.doShowInvalidFileActionAlert();
      }
    }
  }

  public void doLoadBoard(Stage stage) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Load Players");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
    File file = fileChooser.showOpenDialog(null);

    if (file != null) {
      try {
        loadBoardGamePath = file.getAbsolutePath();
        BoardGameView boardGameView = new BoardGameView();
        boardGameView.start(GameType.FROMFILE, playersAndPieces, loadBoardGamePath);
        stage.close();
      } catch (Exception e) {
        gameSetupView.doShowInvalidFileActionAlert();
        throw new BoardGameInitException("Error loading board game", e);

      }
    }
  }

  public void doBack(Stage stage) {
    try {
      StartPageView startPageView = new StartPageView();
      startPageView.start(stage);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}

