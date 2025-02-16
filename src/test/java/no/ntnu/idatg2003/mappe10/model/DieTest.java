package no.ntnu.idatg2003.mappe10.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DieTest {

    private Die testDie;

    @BeforeEach
    void setUp() {
        testDie = new Die();
    }

    @Test
    void testRollForCorrectBoundary() {
        for (int i = 0; i < 6000; i++) {
            int roll = testDie.roll();
            assertTrue(roll >= 1 && roll <= 6);
        }
    }

    @Test
    void getValueTestForCorrectValue() {
        int roll = testDie.roll();
        assertEquals(roll, testDie.getValue());
    }
}