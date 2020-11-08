import java.util.ArrayList;
import java.util.Random;

/**
 * Manages actions of the players in the game.
 *
 * @author 014485
 * @author 054530
 * @version 1.0
 */
public class Player extends Thread {
    private final int number;
    private final ArrayList<Card> hand = new ArrayList<>();

    /**
     * @param number The player ID by which to identify the player.
     */
    public Player(int number) {
        this.number = number;
    }

    /**
     * @return The current cards held by the player.
     */
    public ArrayList<Card> getHand() {
        return this.hand;
    }

    /**
     * @param index The card in the player's hand to search for and get.
     * @return The indexed card from the player's hand.
     */
    public Card getHandCard(int index) {
        return this.hand.get(index);
    }

    /**
     * @return The number of cards held by the player.
     */
    public int getHandSize() {
        return this.hand.size();
    }

    /**
     * @return The player ID.
     */
    public int getOwner() {
        return this.number;
    }

    /**
     * @param index The card in the player's hand to search for and set.
     * @param val   The value to change the indexed card from the player's hand to.
     */
    public void setHandCard(int index, Card val) {
        this.hand.set(index, val);
    }

    /**
     * @param val The value to change to the player's hand.
     */
    public void addToHand(Card val) {
        this.hand.add(val);
    }

    /**
     * @param index The card in the player's hand to search for and remove.
     */
    public void remFromHand(int index) {
        this.hand.remove(index);
    }

    /**
     * Starts the game.
     */
    public void run() {
        play();
    }

    /**
     * @param len Number of cards in the deck.
     * @return Random number from 1 to the length of the list.
     */
    public int random(int len) {
        if (len == 0) {
            System.out.println("No more cards in Deck");
            System.exit(2);
        }
        Random rand = new Random(); //instance of random class
        return rand.nextInt(len);
    }

    /**
     * Method which continues to play the game, with each player drawing and discarding cards, until a player has four
     * cards with the same values, and therefore wins the game.
     */
    public void play() {
        synchronized (this) {
            boolean winner = false;
            while (!winner) {
                int index = random(CardGame.deckObj[getOwner() - 1].getDeck().size());
                System.out.println("player " + getOwner() + " draws a " +
                        ((CardGame.deckObj[getOwner() - 1]).getDeckCard(index).getValue()) + " from deck " + (getOwner()));

                Card c = CardGame.deckObj[getOwner() - 1].getDeckCard(index);

                System.out.println("player: " + getOwner() + " length: " + CardGame.deckObj[getOwner() - 1].getDeck().size() + " index: " + index);
                CardGame.deckObj[getOwner() - 1].remFromDeck(index);

                CardGame.playerObj[getOwner() - 1].addToHand(c);
                if (getOwner() != CardGame.numPlayers) {
                    System.out.println("player " + getOwner() + " discards " +
                            (c.getValue()) + " to deck " + (getOwner() + 1));
                } else {
                    System.out.println("player " + CardGame.numPlayers + " discards a " +
                            (c.getValue()) + " to deck 1");
                }

                System.out.println("player " + getOwner() + " current hand is ");
                for (int i = 0; i < CardGame.playerObj[getOwner() - 1].getHand().size(); i++) {
                    System.out.println("player: " + getOwner() + " Card: " + CardGame.playerObj[getOwner() - 1].getHandCard(i).getValue() + ", ");
                }
                winner = CardGame.isWinner(CardGame.playerObj[getOwner() - 1]);
            }
        }
    }
}
