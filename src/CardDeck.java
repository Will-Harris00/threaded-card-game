package src;

import java.util.ArrayList;

/**
 * Handles changes to the decks of cards as the game progresses.
 *
 * @author 014485
 * @author 054530
 * @version 1.0
 */
public class CardDeck {
    private final ArrayList<Card> deck = new ArrayList<>();


    /**
     *
     */
    public CardDeck() {
    }


    /**
     * @return The deck as a list of card values.
     */
    public ArrayList<Card> getDeck() {
        return this.deck;
    }


    /**
     * @param index The card in the deck to search for and get.
     *
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
     * @param val The value of the card to add to the deck.
     */
    public void addToDeck(Card val) {
        this.deck.add(val);
    }


    /**
     * @param index The card in the deck to search for and remove.
     */
    public void remFromDeck(int index) {
        this.deck.remove(index);
    }
}
