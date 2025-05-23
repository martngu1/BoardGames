package no.ntnu.idatg2003.mappe10.model.property;

import no.ntnu.idatg2003.mappe10.model.engine.MonopolyGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {

    private Property property;
    private Player player;
    private MonopolyGame game;

    @BeforeEach
    void setUp() {
        game = new MonopolyGame();
        Country testCountry = new Country("Norway", 500, 75);
        property = new Property("Oslo Harbor", testCountry);
        player = new Player("TestPlayer", "boat", game);
    }

    @Test
    void testGetName() {
        assertEquals("Oslo Harbor", property.getName());
    }

    @Test
    void testGetPriceFromCountry() {
        assertEquals(500, property.getPrice());
    }

    @Test
    void testGetRentFromCountry() {
        assertEquals(75, property.getRent());
    }

    @Test
    void testSetAndGetOwner() {
        property.setOwner(player);
        assertEquals(player, property.getOwner());
    }

    @Test
    void testIsOwnedTrueAndFalse() {
        assertFalse(property.isOwned());
        property.setOwner(player);
        assertTrue(property.isOwned());
    }

    @Test
    void testGetCountry() {
        assertNotNull(property.getCountry());
        assertEquals("Norway", property.getCountry().getName());
    }

    @Test
    void testGetPropertyByNameMatches() {
        assertEquals(property, property.getPropertyByName("Oslo Harbor"));
    }

    @Test
    void testGetPropertyByNameNoMatch() {
        assertNull(property.getPropertyByName("Bergen Bay"));
    }
}
