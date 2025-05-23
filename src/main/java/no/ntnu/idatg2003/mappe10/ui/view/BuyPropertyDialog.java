package no.ntnu.idatg2003.mappe10.ui.view;

import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import no.ntnu.idatg2003.mappe10.model.property.Property;
import no.ntnu.idatg2003.mappe10.model.player.Player;

public class BuyPropertyDialog extends Stage {

    private ScaleTransition scale1 = new ScaleTransition();
    private ScaleTransition scale2 = new ScaleTransition();
    private SequentialTransition anim = new SequentialTransition(scale1, scale2);
    private final double ANIMATION_DURATION = 0.33;

    private Button buyBtn;
    private Button declineBtn;

    public BuyPropertyDialog(Player player, Property property, Runnable onBuy, Runnable onDecline) {
        this.initStyle(StageStyle.TRANSPARENT);
        this.initModality(Modality.APPLICATION_MODAL);
        StackPane root = new StackPane();
        setupAnimation(root);

        Label infoLabel = new Label("Do you wish to buy " + property.getName() + "?\n\n" +
                "Player balance: " + player.getBalance() + "\n" +
                "Price cost: " + property.getPrice() + "\n" +
                "Rent: " + property.getRent());

        buyBtn = new Button("Accept Offer");
        buyBtn.setPrefWidth(100);
        buyBtn.setOnAction(e -> {
            onBuy.run();
            this.close();
        });

        declineBtn = new Button("Decline Offer");
        declineBtn.setPrefWidth(100);
        declineBtn.setOnAction(e -> {
            onDecline.run();
            this.close();
        });

        VBox content = new VBox(20, infoLabel, buyBtn, declineBtn);
        content.setAlignment(Pos.CENTER);
        root.getChildren().add(content);
        root.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95); -fx-padding: 20px; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Scene scene = new Scene(root, 400, 300);
        scene.setFill(Color.TRANSPARENT);

        this.setScene(scene);
    }

    private void setupAnimation(Node node) {
        scale1.setFromX(0.01);
        scale1.setFromY(0.01);
        scale1.setToY(1.0);
        scale1.setDuration(Duration.seconds(ANIMATION_DURATION));
        scale1.setNode(node);

        scale2.setFromX(0.01);
        scale2.setToX(1.0);
        scale2.setDuration(Duration.seconds(ANIMATION_DURATION));
        scale2.setNode(node);
    }

    public void showDialog() {
        this.show();
        anim.play();
    }
}
