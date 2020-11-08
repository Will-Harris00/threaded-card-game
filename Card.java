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
     * @return Returns the player ID who owns the card.
     */
    public int getHolder() {
        return this.holder;
    }

    /**
     * @param owner Sets the owner of the card to this player ID.
     */
    public void setHolder(int owner) {
        this.holder = owner;
    }

    /**
     * @return Returns the value of the card.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * @param val Sets the value of this card to this integer.
     */
    public void setValue(int val) {
        this.value = val;
    }
}
