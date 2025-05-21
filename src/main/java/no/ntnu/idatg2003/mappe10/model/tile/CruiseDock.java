package no.ntnu.idatg2003.mappe10.model.tile;

import no.ntnu.idatg2003.mappe10.model.engine.MonopolyGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;

public class CruiseDock extends Property{
    private String name;
    private int price;
    private int baseRent;

    public CruiseDock(String name, int price, int baseRent) {
        super(name, null);
        this.price = price;
        this.baseRent = baseRent;
    }

    @Override
    public int getPrice() {
        return price;
    }
    public int getDockRent(Player player, MonopolyGame game) {
        int ownedCount = game.countCruiseDocksOwnedByPlayer(player);
        return baseRent * ownedCount;
    }

    @Override
    public int getRent() {
        return baseRent;
    }

}
