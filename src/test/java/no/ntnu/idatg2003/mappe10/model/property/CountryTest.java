package no.ntnu.idatg2003.mappe10.model.property;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {

    private Country country;

    @BeforeEach
    void setUp() {
        country = new Country("Norway", 200, 50);
    }

    @Test
    void testCountryInitialization() {
        assertEquals("Norway", country.getName());
        assertEquals(200, country.getPrice());
        assertEquals(50, country.getRent());
    }

    @Test
    void testAddAndGetProperty() {
        Property property = new Property("Oslo", country); // Replace with correct constructor
        country.addProperty(property);

        Property retrieved = country.getProperty(0);
        assertNotNull(retrieved);
        assertEquals("Oslo", retrieved.getName());
        assertEquals(200, retrieved.getPrice());
        assertEquals(50, retrieved.getRent());
    }

    @Test
    void testGetPropertyInvalidIndexNegative() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            country.getProperty(-1);
        });
        assertTrue(exception.getMessage().contains("Invalid property index"));
    }
}
