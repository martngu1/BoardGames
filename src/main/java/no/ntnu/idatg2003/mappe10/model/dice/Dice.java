package no.ntnu.idatg2003.mappe10.model.dice;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a set of n individual die.
 * The dice can be rolled, and the sum of the values of the dice can be retrieved.
 */
public class Dice {

  private List<Die> diceList;

  /**
   * Creates a set of n dice.
   *
   * @param numberOfDice the number of dice to create
   */
  public Dice(int numberOfDice) {
    diceList = new ArrayList<>();
    for (int i = 0; i < numberOfDice; i++) {
      diceList.add(new Die());
    }
  }

  /**
   * Rolls all the dice and returns the sum of the values.
   *
   * @return the sum of the values of the dice as an int
   */
  public int roll() {
    int sum = 0;
    for (Die die : diceList) {
      sum += die.roll();
    }

    return sum;
  }

  /**
   * Returns the value of a specific die. The dice are numbered from 0 to n.
   *
   * @param dieNumber the number of the die to get the value of
   * @return the value of the die as an int
   */
  public int getDie(int dieNumber) {
    return diceList.get(dieNumber).getValue();
  }
  public int getNumberOfDice() {
    return diceList.size();
  }

}
