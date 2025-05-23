package no.ntnu.idatg2003.mappe10.model.property;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a country in the game.
 * The country has a name, price, rent and a list of properties.
 */
public class Country {
    private final String name;
    private final int price;
    private final int rent;
    private final List<Property> properties;

    /**
     * Creates a new country with the given name, price, rent and color.
     *
     * @param name  the name of the country
     * @param price the price of the country
     * @param rent  the rent of the country
     */
    public Country(String name, int price, int rent) {
        this.name = name;
        this.price = price;
        this.rent = rent;
        this.properties = new ArrayList<>();
    }

    /**
     * Returns the name of the country.
     *
     * @return the name of the country
     */
    public String getName() {
        return name;
    }
    /**
     * Returns the price of the country.
     *
     * @return the price of the country
     */
    public int getPrice() {
        return price;
    }
    /**
     * Returns the rent of the country.
     *
     * @return the rent of the country
     */
    public int getRent() {
        return rent;
    }
    /**
     * Adds a property to the country.
     *
     */
   public void addProperty(Property property) {
        properties.add(property);
   }
    /**
     * Gets a property from the country by index.
     *
     * @return the property at the given index
     */
    public Property getProperty(int index) {
        if (index < 0 || index >= properties.size()) {
            throw new IndexOutOfBoundsException("Invalid property index: " + index);
        }
        return properties.get(index);
    }


}
