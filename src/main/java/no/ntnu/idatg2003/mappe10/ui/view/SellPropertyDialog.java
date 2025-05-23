package no.ntnu.idatg2003.mappe10.ui.view;

import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import no.ntnu.idatg2003.mappe10.model.player.Player;
import no.ntnu.idatg2003.mappe10.model.tile.MonopolyTile;
import no.ntnu.idatg2003.mappe10.model.property.Property;
import no.ntnu.idatg2003.mappe10.model.tile.Tile;

import java.util.HashMap;
import java.util.Map;

public class SellPropertyDialog extends Stage {

    private ScaleTransition scale1 = new ScaleTransition();
    private ScaleTransition scale2 = new ScaleTransition();
    private SequentialTransition anim = new SequentialTransition(scale1, scale2);
    private final double ANIMATION_DURATION = 0.33;

    private ComboBox<String> propertyComboBox;
    private Button sellBtn;
    private Button cancelBtn;
    private Label sellPriceLabel;
    private Player player;
    private Map<String, Property> propertyMap = new HashMap<>();


    public SellPropertyDialog(Player player, Runnable onSell, Runnable onFailure) {
        this.player = player;
        this.initStyle(StageStyle.TRANSPARENT);
        this.initModality(Modality.APPLICATION_MODAL);
        StackPane root = new StackPane();
        setupAnimation(root);
        System.out.println("SellPropertyDialog: " + player.getName() + " is trying to sell a property. DEBUG");

        Label title = new Label("Select properties to sell");

        propertyComboBox = new ComboBox<>();
        propertyComboBox.setPromptText("Choose a property to sell");

        populatePropertyComboBox();

        sellPriceLabel = new Label("Select a property to see selling price");

        propertyComboBox.setOnAction(e -> updatePriceLabel());

        sellBtn = new Button("Sell Property");
        sellBtn.setDisable(true);
        sellBtn.setOnAction(e -> {
            String selectedPropertyName = propertyComboBox.getValue();
            if (selectedPropertyName != null) {
                Property selectedProperty = propertyMap.get(selectedPropertyName);
                if (selectedProperty != null) {
                    // Sell the property for half its cost
                    int sellPrice = selectedProperty.getPrice() / 2;
                    player.setBalance(player.getBalance() + sellPrice);
                    selectedProperty.setOwner(null); // Remove ownership

                    System.out.println(player.getName() + " sold " + selectedProperty.getName() +
                            " for $" + sellPrice);

                    onSell.run();
                    this.close();
                }
            }
        });

        cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> {
            onFailure.run();
            this.close();
        });

        VBox content = new VBox(20, title, propertyComboBox, sellBtn, cancelBtn);
        content.setAlignment(Pos.CENTER);
        root.getChildren().add(content);
        root.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95); -fx-padding: 20px; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Scene scene = new Scene(root, 400, 300);
        scene.setFill(Color.TRANSPARENT);

        this.setScene(scene);

    }

    private void populatePropertyComboBox() {
        int totalTiles = player.getGame().getBoard().getNumberOfTiles();

        for (int tileId = 1; tileId <= totalTiles; tileId++) {
            Tile tile = player.getGame().getBoard().getTile(tileId);

            MonopolyTile monopolyTile = tile.getMonopolyTile();
            if (monopolyTile != null) {
                Property property = monopolyTile.getProperty();
                if (property != null && property.getOwner() == player) {
                    String propertyDisplayName = property.getName() + " (Cost: $" + property.getPrice()/2 + ")";
                    propertyComboBox.getItems().add(propertyDisplayName);
                    propertyMap.put(propertyDisplayName, property);
                }
            }
        }

        // If no properties are owned, show a message
        if (propertyComboBox.getItems().isEmpty()) {
            propertyComboBox.getItems().add("No properties owned");
            propertyComboBox.setValue("No properties owned");
            propertyComboBox.setDisable(true);
            sellBtn.setDisable(true);
            sellPriceLabel.setText("You don't own any properties to sell");
        }
    }

    private void updatePriceLabel() {
        String selectedPropertyName = propertyComboBox.getValue();
        if (selectedPropertyName != null && !selectedPropertyName.equals("No properties owned")) {
            Property selectedProperty = propertyMap.get(selectedPropertyName);
            if (selectedProperty != null) {
                int sellPrice = selectedProperty.getPrice() / 2;
                sellPriceLabel.setText("Selling price: $" + sellPrice + " (50% of original cost)");
                sellBtn.setDisable(false);
            }
        } else {
            sellPriceLabel.setText("Select a property to see selling price");
            sellBtn.setDisable(true);
        }
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
