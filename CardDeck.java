import java.util.ArrayList;

/**
 * Handles changes to the decks of cards as the game progresses.
 *
 * @author 014485
 * @author 054530
 * @version 1.0
 */
public class CardDeck {
    private final int number;
    private final ArrayList<Card> deck = new ArrayList<>();


    /**
     * @param number Identifies card decks according to player ID.
     */
    public CardDeck(int number) {
        this.number = number;
    }


    /**
     * @return The deck as a list of card values.
     */
    public ArrayList<Card> getDeck() {
        return this.deck;
    }


    /**
     * @param index The card in the deck to search for and get.
     * @return The indexed card from the deck.
     */
    public Card getDeckCard(int index) {
        return this.deck.get(index);
    }


    /**
     * @return The size of the deck.
     */
    public int getDeckSize() {
        return this.deck.size();
    }


    /**
     * @return The player ID who owns the deck.
     */
    public int getOwner() {
        return number;
    }


    /**
     * @param index The card in the deck to search for and set value to.
     * @param val   The value to set the card in the deck to.
     */
    public void setDeckCard(int index, Card val) {
        this.deck.set(index, val);
    }


    /**
     * @param val The value of the card to add to the deck.
     */
    public void addToDeck(Card val) {
        this.deck.add(val);
    }


    /**
     * @param index The card in the deck to search for and remove.
     */
    public void remFromDeck(int index) { this.deck.remove(index); }
}
