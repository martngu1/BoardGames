package no.ntnu.idatg2003.mappe10.model.property;

import no.ntnu.idatg2003.mappe10.model.player.Player;

/**
 * Represents a property in the game.
 * The property has a name, price, rent and a country.
 */
public class Property {
    private final String name;
    private final Country country;
    private Player owner;
    public Property(String name, Country country) {
        this.name = name;
        this.country = country;
        this.owner = null;
        if (country != null) {
            country.addProperty(this);
        }
    }

    /**
     * Returns the name of the property.
     *
     * @return the name of the property
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the property.
     *
     * @return the price of the property
     */
    public int getPrice() {

        return country.getPrice();
    }

    /**
     * Returns the rent of the property.
     *
     * @return the rent of the property
     */
    public int getRent() {
        return country.getRent();
    }
    /**
     * Returns the owner of the property.
     *
     * @return the owner of the property
     */
    public Player getOwner() {
        return owner;
    }
    /**
     * Sets the owner of the property.
     *
     * @param owner the owner of the property
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }
    /**
     * Checks if the property is owned.
     *
     * @return true if the property is owned, false otherwise
     */
    public boolean isOwned() {
        return owner != null;
    }
    /**
     * Returns the country of the property.
     *
     * @return the country of the property
     */
    public Country getCountry() {
        return country;
    }
    /**
     * Returns the property by its name.
     *
     * @param name the name of the property
     * @return the property with the given name, or null if not found
     */
    public Property getPropertyByName(String name) {
        if (this.name.equals(name)) {
            return this;
        }
        return null;
    }
}
