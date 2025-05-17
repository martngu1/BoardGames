package no.ntnu.idatg2003.mappe10.ui.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import no.ntnu.idatg2003.mappe10.model.player.PlayingPiece;
import no.ntnu.idatg2003.mappe10.ui.controller.BoardGameController;

public class BoardGameView {
  private int playerCount;
  private static final int WINDOW_WIDTH = 1000;
  private static final int WINDOW_HEIGHT = 700;
  private Canvas canvas;
  private BoardGameController controller;

  public BoardGameView() {
    controller = new BoardGameController(this);
    canvas = new ResizableCanvas();
    // Redraw canvas when size changes.
    canvas.widthProperty().addListener(evt -> drawBoard());
    canvas.heightProperty().addListener(evt -> drawBoard());
  }

  /**
   * Draws the board.
   */
  private void drawBoard() {
    controller.drawCurrentBoard(canvas);
  }


  public void start(String selectedBoard) {
    controller.initBoardGame(selectedBoard);

    BorderPane root = new BorderPane();
    root.setTop(createTopMenuBar());
    root.setCenter(createBoardElements());

    Scene scene = new Scene(root);

    Stage primaryStage = new Stage();
    primaryStage.setScene(scene);
    primaryStage.setTitle("Board Game");
    primaryStage.setWidth(WINDOW_WIDTH);
    primaryStage.setHeight(WINDOW_HEIGHT);
    primaryStage.show();
  }

  private StackPane createBoardElements() {
    // Create buttons to roll the dice
    Button rollButton1 = new Button("Roll Die");
    Button rollButton2 = new Button("Roll All Dice");
    rollButton1.setScaleShape(true);
    rollButton2.setScaleShape(true);
    double buttonWidth = 150;
    rollButton1.setMinWidth(buttonWidth);
    rollButton2.setMinWidth(buttonWidth);

    VBox buttonBox = new VBox(10);
    buttonBox.setAlignment(Pos.BOTTOM_CENTER);
    buttonBox.getChildren().addAll(rollButton1, rollButton2);
    buttonBox.setStyle("-fx-padding: 13px;");

    // Create a label to display current player
    Label label = new Label("Current Player: ");
    label.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
    label.borderProperty().set(
        new Border(
            new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(0, 3, 3, 3)
            )
        )
    );

    // Combine the label and button box into single layer
    BorderPane labelAndButton = new BorderPane();
    labelAndButton.setTop(label);
    labelAndButton.setBottom(buttonBox);

    // Create a stack pane to hold the board and the label/button. Board is "behind" the label/buttons.
    StackPane stackPane = new StackPane();
    stackPane.getChildren().addAll(canvas, labelAndButton);

    // Binds the canvas size to the stack pane size
    canvas.widthProperty().bind(stackPane.widthProperty().subtract(100));
    canvas.heightProperty().bind(stackPane.heightProperty().subtract(100));

    return stackPane;
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

  public void setPlayerCount(int playerCount) {
    this.playerCount = playerCount;
  }

  public void addPlayer(String playerName, String playingPiece) {
    controller.addPlayer(playerName, playingPiece);
  }

  /**
   * A resizable canvas that redraws itself when the size changes.
   * This class extends the Canvas class and overrides the isResizable,
   * prefWidth, and prefHeight methods to allow for resizing.
   *
   * <p>This class is based on the example provided by Dirk Lemmermann:
   * <a href="https://dlemmermann.wordpress.com/2014/04/10/javafx-tip-1-resizable-canvas/">Resizable Canvas</a></p>
   *
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
