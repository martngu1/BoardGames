package no.ntnu.idatg2003.mappe10.model.tile.tileaction;

import no.ntnu.idatg2003.mappe10.model.board.BoardGameObserver;
import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.engine.MonopolyGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;
import no.ntnu.idatg2003.mappe10.model.tile.CruiseDock;
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
                if (property instanceof CruiseDock) {
                    CruiseDock dock = (CruiseDock) property;
                    if (game instanceof MonopolyGame monopolyGame){
                        int rent = dock.getDockRent(dock.getOwner(), monopolyGame);
                        handleRentPayment(player, dock.getOwner(), rent, monopolyGame);
                    }
                } else {
                    int rent = property.getRent();
                    if (game instanceof MonopolyGame monopolyGame){
                        handleRentPayment(player, property.getOwner(), rent, monopolyGame);
                    } else {
                        System.out.println("This game is not a Monopoly game.");
                    }
                }
            } else {
                System.out.println(player.getName() + " already owns " + property.getName());
            }
        } else {
            System.out.println("This tile is not a property.");
        }
    }
    private void handleRentPayment(Player player, Player owner, int rent, MonopolyGame game){
        if (player.canAfford(rent)) {
            player.subtractFromBalance(rent);
            owner.addToBalance(rent);
            System.out.println(player.getName() + " paid " + rent + " rent to " + owner.getName());
        } else {
            int totalSellValue = game.getTotalSellValue(player);
            System.out.println("Player cannot afford rent: " + rent);
            System.out.println("Total sell value of properties: " + totalSellValue);

            if (player.getBalance() + totalSellValue < rent) {
                System.out.println(player.getName() + " cannot afford the rent and is bankrupt.");
                int restBalance = player.getBalance();
                player.subtractFromBalance(restBalance);
                owner.addToBalance(restBalance);
                game.notifyBalanceUpdate(player);
                game.removePlayer(player);
            } else {
                System.out.println(player.getName() + " can sell properties to pay the rent.");
                System.out.println("DEBUG: Calling notifyOfferToSellProperty for " + player.getName());
                game.notifyOfferToSellProperty(player, rent);
            }
        }
    }

}

