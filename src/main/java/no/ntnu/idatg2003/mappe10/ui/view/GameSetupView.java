package no.ntnu.idatg2003.mappe10.ui.view;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import no.ntnu.idatg2003.mappe10.ui.controller.GameSetupController;
import no.ntnu.idatg2003.mappe10.ui.controller.SoundController;

import java.io.InputStream;
import java.util.*;

public class GameSetupView {
  private SoundController soundController;
  private GameSetupController controller;

  private final List<String> allPieces = List.of("Apple", "Bee", "Computer", "Magic 8 Ball", "Trumpet");
  private final ObservableList<PlayerPiece> playerList = FXCollections.observableArrayList();
  private TableView<PlayerPiece> playerTable;

  private final VBox playerVbox = new VBox();
  private Button backBtn;
  private Button continueBtn;
  private String selectedBoard;


  public GameSetupView() {
    this.controller = new GameSetupController(this);
    this.soundController = SoundController.getInstance();
  }


  public void getPlayerSetupScene(Stage stage) {
    stage.setTitle("Board Game Setup");

    BorderPane root = new BorderPane();
    Label titleLabel = new Label("Choose Game and Players");
    titleLabel.setAlignment(Pos.CENTER);
    titleLabel.setPadding(new Insets(20));
    root.setTop(titleLabel);

    playerVbox.setSpacing(10);

    setupTableView();

    playerVbox.getChildren().add(createPlayerSetupBox());
    playerVbox.getChildren().add(playerTable);

    continueBtn = new Button("Continue");
    continueBtn.setOnAction(e -> doContinue(stage));

    backBtn = new Button("Back");
    backBtn.setOnAction(e -> {
      soundController.playButtonSound();
      controller.doBack(stage);
    });

    HBox bottomBox = new HBox();
    bottomBox.setPadding(new Insets(10));
    bottomBox.setSpacing(10);
    bottomBox.setAlignment(Pos.BASELINE_RIGHT);
    bottomBox.getChildren().addAll(backBtn, continueBtn);

    VBox leftBox = new VBox();
    leftBox.setPadding(new Insets(10));
    leftBox.setSpacing(10);
    leftBox.getChildren().addAll(playerVbox);

    root.setBottom(bottomBox);
    root.setLeft(leftBox);
    root.setRight(getBoardSetup());

    Scene scene = new Scene(root, 800, 600);
    scene.getStylesheets().add((getClass().getResource("/css/style.css")).toExternalForm());
    stage.setScene(scene);
  }

  private void doContinue(Stage stage) {
    soundController.playButtonSound();
    if (playerList.isEmpty() || selectedBoard == null) {
      doShowPlayerAndBoardAlert();
      return;
    }
    playerList.forEach(player -> controller.addPlayerPiece(player.getName(), player.getPieceName()));
    controller.doContinue(stage, selectedBoard);
  }

  private void setupTableView() {
    playerTable = new TableView<>();
    playerTable.setPlaceholder(new Label("No players added yet"));
    playerTable.setItems(playerList);

    TableColumn<PlayerPiece, String> playerNameCol = new TableColumn<>("Player Name");
    playerNameCol.setCellValueFactory(cellData ->
          new SimpleStringProperty(cellData.getValue().getName()));

    // Inner class PlayerPiece -> Lambda solution for setting up the table columns. Solution proposed by ChatGPT.
    TableColumn<PlayerPiece, ImageView> playerPieceCol = new TableColumn<>("Player Piece");
    playerPieceCol.setCellValueFactory(cellData ->
          new SimpleObjectProperty<>(cellData.getValue().getPiece()));

    playerTable.getColumns().add(playerNameCol);
    playerTable.getColumns().add(playerPieceCol);
    playerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
  }

  private HBox createPlayerSetupBox() {
    HBox playerBox = new HBox(10);
    playerBox.setAlignment(Pos.CENTER);

    TextField nameField = new TextField();
    nameField.setPromptText("Enter name");

    ImageView pieceImageView = new ImageView();
    pieceImageView.setFitWidth(50);
    pieceImageView.setFitHeight(50);
    pieceImageView.setPreserveRatio(true);
    InputStream imgStream = getClass().getResourceAsStream("/playingPieces/iconPlaceHolder.png");
    pieceImageView.setImage(new Image(imgStream));

    ComboBox<String> pieceComboBox = createComboBox();

    Button addBtn = new Button();
    addBtn.getStyleClass().add("add-button");
    addBtn.setText("➕");
    addBtn.setOnAction(e -> {
      soundController.playButtonSound();
      if (checkPlayerInputs(nameField.getText(), pieceComboBox.getValue())) {
        doShowInvalidInputAlert();
        return;
      }
      PlayerPiece newPlayer = new PlayerPiece(nameField.getText(), pieceComboBox.getValue());
      playerList.add(newPlayer);
      nameField.clear();
      pieceComboBox.getSelectionModel().clearSelection();
      InputStream img = getClass().getResourceAsStream("/playingPieces/iconPlaceHolder.png");
      pieceImageView.setImage(new Image(img)); // Set to placeholder image if io not found.
    });

    pieceComboBox.setOnAction(e -> {
      String selectedPiece = pieceComboBox.getValue();
      InputStream io = getClass().getResourceAsStream("/playingPieces/" + selectedPiece + ".png");
      if (io == null) {
        return;
      }
      Image img = new Image(io);
      pieceImageView.setImage(img);
    });

    playerBox.getChildren().addAll(nameField, pieceComboBox, pieceImageView, addBtn);
    return playerBox;
  }

  private boolean checkPlayerInputs(String name, String piece) {
    return name.isBlank() || piece == null || nameOrPieceUnavailable(name, piece);
  }

  private boolean nameOrPieceUnavailable(String name, String piece) {
    return playerList.stream()
          .anyMatch(playerPiece ->
                playerPiece.getName().equalsIgnoreCase(name) || playerPiece.getPieceName().equalsIgnoreCase(piece));
  }

  public void doShowInvalidInputAlert() {
    Alerts alerts = new Alerts();
    alerts.showAlert("Invalid Input", "Invalid input",
          "Name or Piece may be unavailable or is empty. Please try again.");
  }

  public void doShowPlayerAndBoardAlert() {
    Alerts alerts = new Alerts();
    alerts.showAlert("Missing Input", "Missing Players or Board",
          "No players found or No board selected.");
  }

  private ComboBox<String> createComboBox() {
    ComboBox<String> comboBox = new ComboBox<>();
    comboBox.getItems().addAll(allPieces);
    comboBox.setPromptText("Select a piece");

    return comboBox;
  }

  public Pane getBoardSetup() {
    Label boardLabel = new Label("Select Board:");
    ToggleGroup boardToggleGroup = new ToggleGroup();

    RadioButton board1Radio = new RadioButton("Ladder Game");
    board1Radio.setToggleGroup(boardToggleGroup);

    RadioButton board2Radio = new RadioButton("The Lost Diamond");
    board2Radio.setToggleGroup(boardToggleGroup);

    RadioButton board3Radio = new RadioButton("Monopoly Game");
    board3Radio.setToggleGroup(boardToggleGroup);

    boardToggleGroup.selectedToggleProperty().addListener(e -> {
      continueBtn.setDisable(boardToggleGroup.getSelectedToggle() == null);
      selectedBoard = ((RadioButton) boardToggleGroup.getSelectedToggle()).getText();
    });

    VBox boardOptions = new VBox(10, board1Radio, board2Radio, board3Radio);
    boardOptions.setPadding(new Insets(10, 0, 10, 0));

    VBox boardSetup = new VBox(10, boardLabel, boardOptions);
    boardSetup.setAlignment(Pos.CENTER_RIGHT);
    boardSetup.setPadding(new Insets(20));

    return boardSetup;
  }


  private class PlayerPiece {
    private String name;
    private ImageView piece;
    private String pieceName;

    public PlayerPiece(String name, String pieceName) {
      this.name = name;
      this.pieceName = pieceName;
      Image img = new Image(getClass().getResourceAsStream("/playingPieces/" + pieceName + ".png"));
      this.piece = new ImageView(img);
      this.piece.setFitWidth(23);
      this.piece.setFitHeight(23);
    }

    public String getName() {
      return name;
    }

    public ImageView getPiece() {
      return piece;
    }

    public String getPieceName() {
      return pieceName;
    }
  }
}




