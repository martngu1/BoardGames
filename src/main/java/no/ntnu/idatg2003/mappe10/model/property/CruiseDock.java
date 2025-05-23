package no.ntnu.idatg2003.mappe10.model.property;

import no.ntnu.idatg2003.mappe10.model.engine.MonopolyGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;
import no.ntnu.idatg2003.mappe10.model.property.Property;

/**
 * Represents a cruise dock in the game.
 * The cruise dock has a name, price and rent.
 */
public class CruiseDock extends Property {
    private int price;
    private int baseRent;

    public CruiseDock(String name, int price, int baseRent) {
        super(name, null);
        this.price = price;
        this.baseRent = baseRent;
    }

    /**
     * Gets the price of the cruise dock.
     *
     * @return the price of the cruise dock
     */
    @Override
    public int getPrice() {
        return price;
    }
    /**
     * Gets the rent of the cruise dock.
     *
     * @param player the player who landed on the tile
     * @param game   the game instance
     * @return the rent of the cruise dock
     */
    public int getDockRent(Player player, MonopolyGame game) {
        int ownedCount = game.countCruiseDocksOwnedByPlayer(player);
        return baseRent * ownedCount;
    }

    /**
     * Gets the base rent of the cruise dock.
     *
     * @return the base rent of the cruise dock
     */
    @Override
    public int getRent() {
        return baseRent;
    }

}
