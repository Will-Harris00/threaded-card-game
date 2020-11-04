import java.util.ArrayList;

public class Player extends Thread {
    private final int number;
    private ArrayList<Card> hand = new ArrayList<>();

    public Player(int number) { this.number = number; }

    public int getOwner() { return number; }

    public void setHandCard(int index, Card val) { this.hand.set(index, val); }

    public Card getHandCard(int index) { return this.hand.get(index); }

    public void remFromHand (int index) { this.hand.remove(index); }

    public void addToHand (Card val) { this.hand.add(val); }

    public int handSize() { return this.hand.size(); }

    public void run () {
        System.out.println(CardGame.playerObj[getOwner()-1].getHandCard(0).getValue());
    }
}
