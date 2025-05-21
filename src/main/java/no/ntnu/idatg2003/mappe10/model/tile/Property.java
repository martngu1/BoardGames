package no.ntnu.idatg2003.mappe10.model.tile;

import no.ntnu.idatg2003.mappe10.model.player.Player;

public class Property {
    private final String name;
    private final Country country;
    private Player owner;
    public Property(String name, Country country) {
        this.name = name;
        this.country = country;
        this.owner = null;
        country.addProperty(this);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return country.getPrice();
    }

    public int getRent() {
        return country.getRent();
    }
    public Player getOwner() {
        return owner;
    }
    public void setOwner(Player owner) {
        this.owner = owner;
    }
    public boolean isOwned() {
        return owner != null;
    }
    public Country getCountry() {
        return country;
    }
}
