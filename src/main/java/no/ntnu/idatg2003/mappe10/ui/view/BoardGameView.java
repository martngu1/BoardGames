package no.ntnu.idatg2003.mappe10.ui.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BoardGameView extends Application {
  private static final int WINDOW_WIDTH = 800;
  private static final int WINDOW_HEIGHT = 600;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void init() {

  }

  @Override
  public void start(Stage primaryStage) {
    BorderPane root = new BorderPane();
    root.setTop(createTopMenuBar());
    root.setCenter(createBoardElements());

    Scene scene = new Scene(root);

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
    stackPane.getChildren().addAll(loadBoard(), labelAndButton);

    return stackPane;
  }

  private Canvas loadBoard() {
    Canvas canvas = new Canvas();

    return canvas;
  }


  private MenuBar createTopMenuBar() {
    // Menu options
    Menu loadMenu = new Menu("Load");
    Menu saveMenu = new Menu("Save");
    Menu settingsMenu = new Menu("Settings");

    // Menu items
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


}
