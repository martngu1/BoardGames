package no.ntnu.idatg2003.mappe10.ui.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import no.ntnu.idatg2003.mappe10.model.player.Player;

import java.io.InputStream;
import java.util.Objects;

public class CustomDialog extends Stage {

  private Button restartBtn;
  private Button exitBtn;
  private Label winnerLabel;


  public CustomDialog(double width, double height) {
    this.initStyle(StageStyle.TRANSPARENT);
    this.initModality(Modality.APPLICATION_MODAL);
    StackPane root = new StackPane();

    double btnWidth = 100;
    double btnHeight = 50;
    restartBtn = new Button("Restart");
    restartBtn.setPrefWidth(btnWidth);
    restartBtn.setPrefHeight(btnHeight);

    exitBtn = new Button("Exit");
    exitBtn.setPrefWidth(btnWidth);
    exitBtn.setPrefHeight(btnHeight);

    winnerLabel = new Label("Winner: Player 1");

    VBox buttonBox = new VBox();
    buttonBox.getStyleClass().add("win-dialog");
    winnerLabel.getStyleClass().add("win-label");
    restartBtn.getStyleClass().add("win-button");
    exitBtn.getStyleClass().add("win-button");

    buttonBox.getChildren().addAll(winnerLabel, getTrophyImage(), restartBtn, exitBtn);
    buttonBox.setSpacing(20);
    buttonBox.setAlignment(Pos.CENTER);

    root.getChildren().addAll(buttonBox);
    root.setStyle("-fx-background-color: transparent;");

    Scene scene = new Scene(root, width, height);
    scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/winDialog.css")).toExternalForm());
    scene.setFill(Color.TRANSPARENT);

    this.setScene(scene);
  }

  public void setRestartBtnAction(Runnable action) {
    restartBtn.setOnAction(e -> {
      action.run();
      this.close();
    });
  }

  public void setExitBtnAction(Runnable action) {
    exitBtn.setOnAction(e -> {
      action.run();
      this.close();
    });
  }

  public void setWinner(String name) {
    winnerLabel.setText("Winner: " + name);
  }

  private ImageView getTrophyImage() {
    ImageView trophyView = new ImageView();
    InputStream is = getClass().getResourceAsStream("/otherImg/trophy.png");
    if (is != null) {
      Image trophy = new Image(is);
      trophyView.setImage(trophy);
    }
    trophyView.preserveRatioProperty().set(true);
    trophyView.setFitHeight(300);
    trophyView.setFitWidth(300);

    return trophyView;
  }
}
