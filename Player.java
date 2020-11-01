import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand = new ArrayList<>();

    public void setHandCard(int index, Card val) { this.hand.set(index, val); }

    public Card getHandCard(int index) { return this.hand.get(index); }

    public void remFromHand (int index) { this.hand.remove(index); }

    public void addToHand (Card val) { this.hand.add(val); }

    public int handSize() { return this.hand.size(); }
}
