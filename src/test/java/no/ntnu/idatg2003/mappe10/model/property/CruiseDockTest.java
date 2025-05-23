package no.ntnu.idatg2003.mappe10.model.property;

import no.ntnu.idatg2003.mappe10.model.engine.MonopolyGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CruiseDockTest {

    private CruiseDock cruiseDock;
    private MonopolyGame game;
    private Player player;

    @BeforeEach
    void setUp() {
        cruiseDock = new CruiseDock("Harbor A", 200, 50);
        game = new MonopolyGame();
        player = new Player("TestPlayer", "ship", game);
    }

    @Test
    void getPriceReturnsCorrectValue() {
        assertEquals(200, cruiseDock.getPrice());
    }

    @Test
    void getBaseRentReturnsCorrectValue() {
        assertEquals(50, cruiseDock.getRent());
    }


}
