package no.ntnu.idatg2003.mappe10.model.tile.tileaction;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;

/**
 * Represents an action that requires the player to pay a tax.
 * The action is represented by a tax amount and a description.
 */
public class TaxAction implements TileAction{

    private final int taxAmount;
    private String description;

    public TaxAction(int taxAmount, String description) {
        this.taxAmount = taxAmount;
        this.description = description;
    }

    /**
     * Handles the action of paying tax.
     *
     */
    @Override
    public void performAction(Player player, BoardGame game) {
        int totalSellValue = game.getTotalSellValue(player);
        // Check if the player has enough balance to pay the taxæ
        if (player.canAfford(taxAmount)) {
            player.subtractFromBalance(taxAmount);
        } else if (player.getBalance() + totalSellValue < taxAmount) {
            System.out.println(player.getName() + " cannot afford the tax and is bankrupt.");
            int restBalance = player.getBalance();
            player.subtractFromBalance(restBalance + 1); // Set balance to -1 to indicate bankruptcy
            game.notifyBalanceUpdate(player);
            game.removePlayer(player);
        } else {
            System.out.println(player.getName() + " can sell properties to pay the tax.");
            game.notifyOfferToSellProperty(player, taxAmount);
        }
    }

    /**
     * Gets the description of the tax action.
     *
     * @return the description of the tax action
     */
    @Override
    public String getDescription() {
        return description;
    }



}
