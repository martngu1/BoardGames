package no.ntnu.idatg2003.mappe10.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {

  private Dice testDice2D;
  private Dice testDice4D;

  @BeforeEach
  void setUp() {
    testDice2D = new Dice(2);
    testDice4D = new Dice(4);
  }

  @Test
  void testRollForCorrectSumOfDice() {
    int roll2D = testDice2D.roll();
    int sum2D = testDice2D.getDie(0) + testDice2D.getDie(1);

    int roll4D = testDice4D.roll();
    int sum4D = testDice4D.getDie(0) + testDice4D.getDie(1) + testDice4D.getDie(2)
          + testDice4D.getDie(3);


    assertEquals(sum2D, roll2D);
    assertEquals(sum4D, roll4D);
  }
}