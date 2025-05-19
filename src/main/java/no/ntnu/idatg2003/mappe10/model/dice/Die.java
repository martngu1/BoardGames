package no.ntnu.idatg2003.mappe10.model.dice;
import java.util.Random;

/**
 * Represents a die with 6 sides.
 * The die can be rolled, and the value of the last roll can be retrieved.
 */
public class Die {

    private final Random random = new Random();
    private int lastRolledValue;

    /**
     * Rolls the die and returns the value as an int.
     * The value is also stored, and can be retrieved with the getValue() method.
     * Die values are between 1 and 6.
     *
     * @return the int value of the die
     */
    public int roll() {
        // Generate a random number between 1 and 6
        lastRolledValue = random.nextInt(1, 7);
        return lastRolledValue;
    }

    /**
     * Returns the int value of the last rolled die.
     *
     * @return int value of the last rolled die
     */
    public int getValue(){
        return lastRolledValue;
    }


}

