package no.ntnu.idatg2003.mappe10.model.board;

import no.ntnu.idatg2003.mappe10.model.tile.Country;
import no.ntnu.idatg2003.mappe10.model.tile.CruiseDock;
import no.ntnu.idatg2003.mappe10.model.tile.Property;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CountryFactory {
    private final List<Country> countries = new ArrayList<>();
    private final List<CruiseDock> cruiseDocks = new ArrayList<>();

    private final List<Country> totalAddedCountries = new ArrayList<>();
    private final List<CruiseDock> totalAddedCruiseDocks = new ArrayList<>();

    public CountryFactory(){
        createCountries();
        createCruiseDocks();
    }

    public Iterator<Country> getTotalAddedCountries() {
        return totalAddedCountries.stream().distinct().iterator();
    }

    public Iterator<CruiseDock> getTotalAddedCruiseDocks() {
        return totalAddedCruiseDocks.stream().distinct().iterator();
    }

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
    public void createCruiseDocks(){
        cruiseDocks.add(new CruiseDock("Yokohoma Harbour Terminal", 200, 50));
        cruiseDocks.add(new CruiseDock("Jade Lotus Dock", 200, 50));
        cruiseDocks.add(new CruiseDock("Marina Bay Cruise Center", 200, 50));
        cruiseDocks.add(new CruiseDock("Dragon Pearl Port", 200, 50));
    }
    public CruiseDock getCruiseDockByName(String name) {
        for (CruiseDock cruiseDock : cruiseDocks) {
            if (cruiseDock.getName().equals(name)) {
                totalAddedCruiseDocks.add(cruiseDock);
                return cruiseDock;
            }
        }
        return null;
    }

    private Country createCountry(String name, int price, int rent,List<String> cityNames) {
        Country country = new Country(name, price, rent);
        for (String city : cityNames) {
            Property property = new Property(city, country);
            country.addProperty(property);
        }
        return country;
    }
    public Country getCountryByName(String name) {
        for (Country country : countries) {
            if (country.getName().equals(name)) {
                totalAddedCountries.add(country);
                return country;
            }
        }
        return null;
    }

    public Property getPropertyByCountryAndIndex(String countryName, int cityIndex) {
        Country country = getCountryByName(countryName);
        if (country == null) {
            throw new IllegalArgumentException("Country not found: " + countryName);
        }
        return country.getProperty(cityIndex);
    }


}
