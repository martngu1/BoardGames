package no.ntnu.idatg2003.mappe10.model.tile.tileaction;

import no.ntnu.idatg2003.mappe10.model.board.BoardGameObserver;
import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;
import no.ntnu.idatg2003.mappe10.model.tile.MonopolyTile;
import no.ntnu.idatg2003.mappe10.model.tile.Property;
import no.ntnu.idatg2003.mappe10.model.tile.Tile;
import no.ntnu.idatg2003.mappe10.ui.view.BoardGameView;
import no.ntnu.idatg2003.mappe10.ui.view.renderer.MonopolyGameRenderer;

import java.awt.*;

public class PropertyAction implements TileAction {
    private String description;

    public PropertyAction(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void performAction(Player player, BoardGame game) {

        Tile currentTile = player.getCurrentTile();
        MonopolyTile monopolyTile = currentTile.getMonopolyTile();

        if (monopolyTile != null) {
            // Get the property associated with the tile
            Property property = monopolyTile.getProperty();

            // Check if property is owned or not
            if (!property.isOwned()) {
                if (player.getBalance() >= property.getPrice()) {
                    // Tell the controller a property is available to buy
                    game.notifyOfferToBuyProperty(player, property);
                } else {
                    System.out.println(player.getName() + " does not have enough money to buy " + property.getName());
                }
            } else if (property.getOwner() != player) {
                player.subtractFromBalance(property.getRent());
                property.getOwner().addToBalance(property.getRent());

                System.out.println(player.getName() + " paid " + property.getRent() + " rent to " + property.getOwner().getName());
            } else {
                System.out.println(player.getName() + " already owns " + property.getName());
            }
        } else {
            System.out.println("This tile is not a property.");
        }
    }

}

