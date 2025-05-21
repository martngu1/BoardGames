package no.ntnu.idatg2003.mappe10.model.tile.tileaction;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;
import no.ntnu.idatg2003.mappe10.model.tile.MonopolyTile;
import no.ntnu.idatg2003.mappe10.model.tile.Property;
import no.ntnu.idatg2003.mappe10.model.tile.Tile;

import java.awt.*;

public class PropertyAction implements TileAction{
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

        if (currentTile instanceof MonopolyTile monopolyTile) {
            // Get the property associated with the tile
            Property property = monopolyTile.getProperty();

            // Check if property is owned
            if (!property.isOwned()) {
                if (player.getBalance() >= property.getPrice()) {
                    // TO DO: make it so that the player has a choice to buy the property or not
                    player.subtractFromBalance(property.getPrice());
                    property.setOwner(player);
                    System.out.println(player.getName() + " bought " + property.getName() + " for " + property.getPrice());
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
