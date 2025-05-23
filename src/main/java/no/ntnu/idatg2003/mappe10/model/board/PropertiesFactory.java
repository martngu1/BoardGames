package no.ntnu.idatg2003.mappe10.model.board;

import no.ntnu.idatg2003.mappe10.model.property.Country;
import no.ntnu.idatg2003.mappe10.model.property.CruiseDock;
import no.ntnu.idatg2003.mappe10.model.property.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * CountryFactory is a factory class that creates instances of Country and CruiseDock.
 * It initializes a list of countries and cruise docks with predefined values.
 */
public class PropertiesFactory {
    private final List<Country> countries = new ArrayList<>();
    private final List<CruiseDock> cruiseDocks = new ArrayList<>();

    public PropertiesFactory(){
        createCountries();
        createCruiseDocks();
    }


    /**
     * Creates a list of countries with predefined values.
     * Each country has a name, price, rent, and a list of city names.
     * The countries are added to the countries list.
     */
    public void createCountries() {
        countries.add(createCountry("Mongolia", 100, 25, List.of("Ulaanbaatar", "Erdenet", "Darkhan")));
        countries.add(createCountry("Philippines", 160, 40, List.of("Manila", "Cebu", "Davao")));
        countries.add(createCountry("Vietnam", 220, 55, List.of("Hanoi", "Ho Chi Minh City", "Da Nang")));
        countries.add(createCountry("Thailand", 300, 75, List.of("Bangkok", "Chiang Mai", "Phuket")));
        countries.add(createCountry("Indonesia", 400, 100, List.of("Jakarta", "Bali", "Surabaya")));
        countries.add(createCountry("China", 550, 150, List.of("Beijing", "Shanghai", "Guangzhou")));
        countries.add(createCountry("South Korea", 700, 205, List.of("Seoul", "Busan", "Incheon")));
        countries.add(createCountry("Japan", 850, 245, List.of("Tokyo", "Osaka", "Kyoto")));
    }
    /**
     * Creates a list of cruise docks with predefined values.
     * Each cruise dock has a name, price, and rent.
     * The cruise docks are added to the cruiseDocks list.
     */
    public void createCruiseDocks(){
        cruiseDocks.add(new CruiseDock("Yokohoma Harbour Terminal", 200, 50));
        cruiseDocks.add(new CruiseDock("Jade Lotus Dock", 200, 50));
        cruiseDocks.add(new CruiseDock("Marina Bay Cruise Center", 200, 50));
        cruiseDocks.add(new CruiseDock("Dragon Pearl Port", 200, 50));
    }

    /**
     * Returns a cruise dock by its name.
     *
     * @return the cruise dock with the specified name, or null if not found
     */
    public CruiseDock getCruiseDockByName(String name) {
        for (CruiseDock cruiseDock : cruiseDocks) {
            if (cruiseDock.getName().equals(name)) {
                return cruiseDock;
            }
        }
        return null;
    }

    /**
     * Creates a country with the specified name, price, rent, and list of city names.
     *
     * @return the created country
     */
    private Country createCountry(String name, int price, int rent,List<String> cityNames) {
        Country country = new Country(name, price, rent);
        for (String city : cityNames) {
            Property property = new Property(city, country);
            country.addProperty(property);
        }
        return country;
    }
    /**
     * Gets the country by its name.
     *
     * @return the country with the specified name, or null if not found
     */
    public Country getCountryByName(String name) {
        for (Country country : countries) {
            if (country.getName().equals(name)) {
                return country;
            }
        }
        return null;
    }
    /**
     * Gets the property by its country name and index.
     *
     * @return the property with the specified country name and index
     */
    public Property getPropertyByCountryAndIndex(String countryName, int cityIndex) {
        Country country = getCountryByName(countryName);
        if (country == null) {
            throw new IllegalArgumentException("Country not found: " + countryName);
        }
        return new Property(country.getProperty(cityIndex).getName(), country);
    }


}
