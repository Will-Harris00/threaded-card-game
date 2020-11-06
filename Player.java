import java.util.ArrayList;
import java.util.Random;
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

    public int random(int len) {
        if (len == 0) {
            System.out.println("No more cards in Deck");
            System.exit(2);
        }
        Random rand = new Random(); //instance of random class
        return rand.nextInt(len);
    }

    public void play() {
        synchronized(this) {
            boolean winner = false;
            while (!winner) {
                int index = random(CardGame.deckObj[getOwner()-1].getDeck().size());
                System.out.println("player " + getOwner() + " draws a " +
                        ((CardGame.deckObj[getOwner()-1]).getDeckCard(index).getValue()) + " from deck " + (getOwner()));

                Card c = CardGame.deckObj[getOwner()-1].getDeckCard(index);

                System.out.println("player: " + getOwner() + " length: " + CardGame.deckObj[getOwner()-1].getDeck().size() + " index: " + index);
                CardGame.deckObj[getOwner()-1].remFromDeck(index);

                CardGame.playerObj[getOwner()-1].addToHand(c);
                if (getOwner() != CardGame.numPlayers) {
                    System.out.println("player " + getOwner() + " discards " +
                            (c.getValue()) + " to deck " + (getOwner()+1));
                } else {
                    System.out.println("player " + CardGame.numPlayers + " discards a " +
                            (c.getValue()) + " to deck 1");
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
