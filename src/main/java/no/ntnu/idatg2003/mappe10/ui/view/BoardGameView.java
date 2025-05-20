package no.ntnu.idatg2003.mappe10.ui.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import no.ntnu.idatg2003.mappe10.model.board.BoardGameObserver;
import no.ntnu.idatg2003.mappe10.model.player.Player;
import no.ntnu.idatg2003.mappe10.ui.controller.BoardGameController;
import no.ntnu.idatg2003.mappe10.ui.controller.SoundController;
import no.ntnu.idatg2003.mappe10.ui.view.renderer.Renderer;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class BoardGameView implements BoardGameObserver {
  private static final double WINDOW_WIDTH = 1000;
  private static final double WINDOW_HEIGHT = 700;
  private Canvas canvas;
  private BoardGameController controller;
  private SoundController soundController;
  private Label currentPlayerLabel;
  private HBox diceBox;
  private Renderer gameRenderer;
  private TextArea logTextArea;
  private Button rollButton1;

  public BoardGameView() {
    soundController = new SoundController();
    controller = new BoardGameController(this);
    canvas = new ResizableCanvas();
    currentPlayerLabel = new Label();
    logTextArea = new TextArea();
    rollButton1 = new Button("Roll Dice");
    gameRenderer = null;
  }

  /**
   * Initializes the board game with the selected board and players.
   *
   * @param selectedBoard    the name of the selected board
   * @param playersAndPieces a map of player names and their corresponding pieces
   */
  public void initBoardGame(String selectedBoard, Map<String, String> playersAndPieces) {
    // Initialize the board game with the selected board and players
    gameRenderer = controller.initBoardGame(selectedBoard, canvas);
    playersAndPieces.keySet().forEach(playerName -> {
      String playingPiece = playersAndPieces.get(playerName);
      controller.addPlayer(playerName, playingPiece);
      controller.addPlayerToQueue(playerName);
    });
    controller.placePlayerOnStartTile();
    controller.arrangePlayerTurns();
  }

  /**
   * Draws the board.
   */
  public void start(String selectedBoard, Map<String, String> playersAndPieces) {
    initBoardGame(selectedBoard, playersAndPieces);
    controller.registerObserver(this);

    BorderPane root = new BorderPane();
    root.setTop(createTopMenuBar());
    root.setCenter(createBoardElements());
    root.setRight(createDiceAndLogBox());

    Scene scene = new Scene(root);

    Stage primaryStage = new Stage();
    primaryStage.setScene(scene);
    primaryStage.setTitle("Board Game");
    primaryStage.setWidth(WINDOW_WIDTH);
    primaryStage.setHeight(WINDOW_HEIGHT);
    primaryStage.show();
  }

  private VBox createBoardElements() {

    // Combine the label and button box into single layer
    BorderPane labelAndButton = new BorderPane();
    labelAndButton.setTop(currentPlayerLabel);

    // Create a stack pane to hold the board and the label/button. Board is "behind" the label/buttons.
    VBox leftBox = new VBox();
    leftBox.getChildren().addAll(canvas, labelAndButton);

    // Binds the canvas size to the stack pane size
    canvas.widthProperty().bind(leftBox.widthProperty());
    canvas.heightProperty().bind(leftBox.heightProperty());

    canvas.widthProperty().addListener(evt -> gameRenderer.drawBoard());
    canvas.heightProperty().addListener(evt -> gameRenderer.drawBoard());


    return leftBox;
  }

  public VBox createDiceAndLogBox() {
    VBox rollingBox = createRollingBox();
    VBox logBox = createLogBox();

    VBox rightBox = new VBox(5);
    rightBox.setBorder(
          new Border(
                new BorderStroke(
                      Color.BLACK,
                      BorderStrokeStyle.SOLID,
                      null,
                      new BorderWidths(0, 3, 3, 3)
                )
          )
    );

    // Set preferred sizes here if needed
    rollingBox.setPrefHeight(300);  // You can tweak this
    logBox.setPrefHeight(200);      // And this too

    rightBox.getChildren().addAll(rollingBox, logBox);
    return rightBox;
  }

  private VBox createRollingBox() {
    currentPlayerLabel.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
    currentPlayerLabel.setBorder(
          new Border(
                new BorderStroke(
                      Color.BLACK,
                      BorderStrokeStyle.SOLID,
                      null,
                      new BorderWidths(0, 3, 3, 3)
                )
          )
    );
    currentPlayerLabel.setAlignment(Pos.CENTER);

    diceBox = new HBox(10);
    diceBox.setAlignment(Pos.CENTER);
    diceBox.setVisible(false);

    rollButton1.setOnAction(e -> {
      soundController.playDiceRollSound();
      controller.playTurn();

    });
    rollButton1.setScaleShape(true);
    rollButton1.setMinWidth(150);

    VBox playerListBox = new VBox(5);
    playerListBox.setAlignment(Pos.CENTER_LEFT);
    playerListBox.setStyle("-fx-padding: 10px;");

    controller.getPlayerListIterator().forEachRemaining(player -> {
      // Create a row for each player
      HBox playerRow = new HBox(8);
      playerRow.setAlignment(Pos.CENTER_LEFT);

      // Load playing piece image from the players playing piece
      InputStream imgStream = getClass().getResourceAsStream("/playingPieces/" + player.getPlayingPiece() + ".png");
      ImageView pieceView = new ImageView();
      if (imgStream != null) {
        Image pieceImage = new Image(imgStream);
        pieceView.setImage(pieceImage);
        pieceView.setFitWidth(24);
        pieceView.setFitHeight(24);
      }

      Label nameLabel = new Label(player.getName());
      nameLabel.setStyle("-fx-font-size: 14px;");

      playerRow.getChildren().addAll(pieceView, nameLabel);
      playerListBox.getChildren().add(playerRow);
    });

    VBox rollingBox = new VBox(10);
    rollingBox.setAlignment(Pos.TOP_CENTER);
    rollingBox.setStyle("-fx-padding: 10px;");
    rollingBox.getChildren().addAll(currentPlayerLabel, rollButton1, diceBox, playerListBox);

    return rollingBox;
  }

  private VBox createLogBox() {
    VBox logBox = new VBox(10);
    logBox.setAlignment(Pos.BOTTOM_CENTER);
    logBox.setStyle("-fx-padding: 10px;");

    Label logLabel = new Label("Game Log");
    logLabel.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
    logLabel.setAlignment(Pos.CENTER);

    logTextArea.setEditable(false);
    logTextArea.setWrapText(true);
    logTextArea.setPrefWidth(250);
    VBox.setVgrow(logBox, Priority.ALWAYS); // Allow the log box to grow
    logTextArea.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
    logTextArea.setBorder(
          new Border(
                new BorderStroke(
                      Color.BLACK,
                      BorderStrokeStyle.SOLID,
                      null,
                      new BorderWidths(0, 3, 3, 3)
                )
          )
    );

    logBox.getChildren().addAll(logLabel, logTextArea);
    return logBox;
  }


  public void showDiceResults(List<Integer> diceResults, int amountOfDice) {
    diceBox.setVisible(true);
    diceBox.getChildren().clear(); // clear the old image

    for (int i = 0; i < amountOfDice; i++) {
      int rollResult = diceResults.get(i);
      String imagePath = "/diceImages/dice_" + rollResult + ".png";

      try {
        ImageView diceImage = new ImageView(getClass().getResource(imagePath).toExternalForm());
        diceImage.setFitWidth(50);
        diceImage.setFitHeight(50);
        diceImage.setPreserveRatio(true);
        diceBox.getChildren().add(diceImage);
      } catch (NullPointerException e) {
        System.out.println("Error loading image: " + imagePath);
      }
    }
  }

  private MenuBar createTopMenuBar() {
    // Create Menu options
    Menu loadMenu = new Menu("Load");
    Menu saveMenu = new Menu("Save");
    Menu settingsMenu = new Menu("Settings");

    // Create Menu items
    MenuItem loadBoard = new MenuItem("New Board");
    MenuItem loadPlayers = new MenuItem("Players");
    MenuItem saveBoard = new MenuItem("Board");
    MenuItem savePlayers = new MenuItem("Players");
    MenuItem addPlayer = new MenuItem("Add Player");

    loadMenu.getItems().addAll(loadBoard, loadPlayers);
    saveMenu.getItems().addAll(saveBoard, savePlayers);
    settingsMenu.getItems().addAll(addPlayer);

    MenuBar menuBar = new MenuBar();
    menuBar.getMenus().addAll(loadMenu, saveMenu, settingsMenu);

    return menuBar;
  }

  public void setRollButtonEnabled(boolean enabled) {
    rollButton1.setDisable(!enabled);
  }

  @Override
  public void updatePosition() {
    // Update the current position of the player in canvas.
    gameRenderer.drawBoard();
  }

  @Override
  public void onTileAction(String name, String actionDescription) {
    String logMessage = name + " " + actionDescription;
    addToLog(logMessage);
  }

  @Override
  public void onGameOver(String name) {
    CustomDialog gameOverDialog = new CustomDialog(WINDOW_HEIGHT/2, WINDOW_WIDTH/2);
    gameOverDialog.setExitBtnAction(() -> {
      gameOverDialog.closeDialog();
      Stage stage = (Stage) canvas.getScene().getWindow();
      stage.close();
      new StartPageView().start(new Stage());
    });
    gameOverDialog.setRestartBtnAction(() -> {
      gameOverDialog.closeDialog();
      controller.restartGame();
    });
    gameOverDialog.setWinner(name);
    gameOverDialog.showDialog();
    soundController.playWinSound();
  }

  public void setCurrentPlayerLabel(String playerName) {
    currentPlayerLabel.setText("Current Players turn: " + playerName);
  }

  public void addToLog(String logMessage) {
    logTextArea.appendText(logMessage + "\n");
  }

  /**
   * A resizable canvas that redraws itself when the size changes.
   * This class extends the Canvas class and overrides the isResizable,
   * prefWidth, and prefHeight methods to allow for resizing.
   *
   * <p>This class is based on the example provided by Dirk Lemmermann:
   * <a href="https://dlemmermann.wordpress.com/2014/04/10/javafx-tip-1-resizable-canvas/">Resizable Canvas</a></p>
   */
  static class ResizableCanvas extends Canvas {

    /**
     * Overrides the isResizable method to be true. This allows the canvas to be resized.
     *
     * @return true to indicate that the canvas is resizable
     */
    @Override
    public boolean isResizable() {
      return true;
    }


    /**
     * Overrides the prefWidth method to return 0. This is to let the parent determine the width.
     *
     * @param width the preferred width
     * @return 0 to let the parent determine the height
     */
    @Override
    public double prefWidth(double width) {
      return 0;
    }

    /**
     * Overrides the prefHeight method to return 0. This is to let the parent determine the height.
     *
     * @param height the preferred height
     * @return 0 to let the parent determine the height
     */
    @Override
    public double prefHeight(double height) {
      return 0;
    }
  }

}
