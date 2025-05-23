package no.ntnu.idatg2003.mappe10.model.board;

import no.ntnu.idatg2003.mappe10.model.player.Player;
import no.ntnu.idatg2003.mappe10.model.property.Property;

/**
 * Interface for the observer pattern in the board game.
 * This interface defines the methods that observers
 * must implement to receive updates
 */
public interface BoardGameObserver {
    void updatePosition();
    void onTileAction(String player, String actionDescription);
    void onGameOver(String name);
    void onOfferToBuyProperty(Player player, Property property);
    void onOfferToSellProperty(Player player, int rent);
    void onBalanceUpdate(Player player);
}
