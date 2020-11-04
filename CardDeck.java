import java.util.ArrayList;

public class CardDeck {
    private final int number;
    private ArrayList<Card> deck = new ArrayList<>();

    public CardDeck(int number) { this.number = number; }

    public int getOwner() { return number; }

    public void setDeckCard(int index, Card val) { this.deck.set(index, val); }

    public Card getDeckCard(int index) { return this.deck.get(index); }

    public void remFromDeck (int index) { this.deck.remove(index); }

    public void addToDeck (Card val) { this.deck.add(val); }

    public int deckSize() { return this.deck.size(); }
}
