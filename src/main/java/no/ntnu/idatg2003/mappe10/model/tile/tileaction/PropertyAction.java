package no.ntnu.idatg2003.mappe10.model.tile.tileaction;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.engine.MonopolyGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;
import no.ntnu.idatg2003.mappe10.model.property.CruiseDock;
import no.ntnu.idatg2003.mappe10.model.tile.MonopolyTile;
import no.ntnu.idatg2003.mappe10.model.property.Property;
import no.ntnu.idatg2003.mappe10.model.tile.Tile;

/**
 * Represents an action that involves properties in the game.
 * The action can be buying a property or paying rent.
 */
public class PropertyAction implements TileAction {
    private String description;

    public PropertyAction(String description) {
        this.description = description;
    }

    /**
     * Gets the description of the action.
     *
     * @return the description of the action
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Performs the action of buying a property or paying rent.
     *
     * @param player the player who is performing the action
     * @param game   the game instance
     */
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
    /**
     * Handles the rent payment process for the player.
     *
     * @param player the player who is paying rent
     * @param owner  the owner of the property
     * @param rent   the amount of rent to be paid
     * @param game   the game instance
     */
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
                player.subtractFromBalance(restBalance + 1); // Set balance to -1 to indicate bankruptcy
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

