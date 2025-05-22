package no.ntnu.idatg2003.mappe10.model.board;

import no.ntnu.idatg2003.mappe10.model.player.Player;
import no.ntnu.idatg2003.mappe10.model.tile.Property;

public interface BoardGameObserver {
    void updatePosition();
    void onTileAction(String player, String actionDescription);
    void onGameOver(String name);
    void onOfferToBuyProperty(Player player, Property property);
    void onOfferToSellProperty(Player player, int rent);
    void onBalanceUpdate(Player player);
}
