package src;

/**
 * Handles the creation and checking of card objects.
 *
 * @author Isaac Cheng
 * @author William Harris
 * @version 1.0
 */

public class Card {
    private int value;

    /**
     * @return The value of the card.
     */
    public int getValue() {
        return this.value;
    }


    /**
     * @param val The value of this card to this integer.
     */
    public void setValue(int val) {
        this.value = val;
    }
}
