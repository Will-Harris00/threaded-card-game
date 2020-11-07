import java.util.ArrayList;

public class CardDeck {
    private ArrayList<Card> deck = new ArrayList<>();
    private final int number;

    public CardDeck(int number) {
        this.number = number;
    }

    public ArrayList<Card> getDeck() {
        return this.deck;
    }

    public Card getDeckCard(int index) {
        return this.deck.get(index);
    }

    public int getDeckSize() {
        return this.deck.size();
    }

    public int getOwner() {
        return number;
    }

    public void setDeckCard(int index, Card val) {
        this.deck.set(index, val);
    }

    public void addToDeck(Card val) {
        this.deck.add(val);
    }

    public void remFromDeck(int index) {
        this.deck.remove(index);
    }


}
