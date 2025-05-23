package no.ntnu.idatg2003.mappe10.model.tile;

import no.ntnu.idatg2003.mappe10.model.tile.Property;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

  public String getName() {
    return name;
  }

  public int getPrice() {
    return price;
  }

  public int getRent() {
    return rent;
  }

  public void addProperty(Property property) {
    properties.add(property);
  }

  public Property getProperty(int index) {
    if (index < 0 || index >= properties.size()) {
      throw new IndexOutOfBoundsException("Invalid property index: " + index);
    }
    return properties.get(index);
  }

  public Iterator<Property> getProperties() {
    return properties.iterator();
  }

}
