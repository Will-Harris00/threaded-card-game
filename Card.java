/**
 * Handles the card generation and distribution for the game.
 *
 * @author 014485
 * @author 054530
 * @version 1.0
 */

public class Card {
    private int holder;
    private int value;

    /**
     * @return The player ID who owns the card.
     */
    public int getHolder() {
        return this.holder;
    }

    /**
     * @param owner The owner of the card to this player ID.
     */
    public void setHolder(int owner) {
        this.holder = owner;
    }

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
