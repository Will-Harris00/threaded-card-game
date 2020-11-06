import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Player extends Thread {
    private final int number;
    private ArrayList<Card> hand = new ArrayList<>();

    public Player(int number) { this.number = number; }

    public int getOwner() { return this.number; }

    public ArrayList<Card> getHand() { return this.hand; }

    public void setHandCard(int index, Card val) { this.hand.set(index, val); }

    public Card getHandCard(int index) { return this.hand.get(index); }

    public void remFromHand (int index) { this.hand.remove(index); }

    public void addToHand (Card val) { this.hand.add(val); }

    public int handSize() { return this.hand.size(); }

    public void run () {
        play();
    }

    public void play() {
        synchronized(this) {
            boolean winner = true;
            while (winner) {
                System.out.println("player " + getOwner() + " draws a " +
                        ((CardGame.deckObj[getOwner()-1]).getDeckCard(0).getValue()) + " from deck " + (getOwner()));

                if (getOwner() != CardGame.numPlayers) {
                    System.out.println("player " + getOwner() + " discards " +
                            ((CardGame.deckObj[getOwner()]).getDeckCard(0).getValue()) + " to deck " + (getOwner()+1));
                } else {
                    System.out.println("player " + CardGame.numPlayers + " discards a " +
                            ((CardGame.deckObj[0]).getDeckCard(0).getValue()) + " to deck 1");
                }

                System.out.println("player " + getOwner() + " current hand is ");
                for (int i = 0; i < CardGame.playerObj[getOwner()-1].getHand().size(); i++) {
                    System.out.println("player: " + getOwner() + " Card: " + CardGame.playerObj[getOwner()-1].getHandCard(i).getValue() + ", ");
                }
                winner = CardGame.isWinner(CardGame.playerObj[getOwner()-1]);
            }
        }
    }
}
