import java.util.ArrayList;

public class Player {
    private ArrayList<Integer> hand = new ArrayList<>();
    private ArrayList<Integer> deck = new ArrayList<>();

    public void setHand(int index, int val) { this.hand.set(index, val); }

    public int getHand(int index) { return this.hand.get(index); }

    public void remFromHand (int index) { this.hand.remove(index); }

    public void addToHand (int val) { this.hand.add(val); }

    public int handSize() { return this.hand.size(); }


    public void setDeck(int index, int val) { this.deck.set(index, val); }

    public int getDeck(int index) { return this.deck.get(index); }

    public void remFromDeck (int index) { this.deck.remove(index); }

    public void addToDeck (int val) { this.deck.add(val); }

    public int deckSize() { return this.deck.size(); }
}
