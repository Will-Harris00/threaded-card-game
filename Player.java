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
    public synchronized void run() {
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
    public synchronized Card draw() {
        int index = random(CardGame.deckObj[getOwner() - 1].getDeck().size());
        System.out.println("player " + getOwner() + " draws a " +
                ((CardGame.deckObj[getOwner() - 1]).getDeckCard(index).getValue()) + " from deck " + (getOwner()));

        Card c = CardGame.deckObj[getOwner() - 1].getDeckCard(index);

        System.out.println("player: " + getOwner() + " length: " + CardGame.deckObj[getOwner() - 1].getDeck().size() + " index: " + index);
        CardGame.deckObj[getOwner() - 1].remFromDeck(index);
        return c;
    }

    public synchronized void keep(Card c) {
        CardGame.playerObj[getOwner() - 1].addToHand(c);
    }

    public synchronized void strategy(Card c) {
        boolean doKeep = false;
        if (c.getValue() == getOwner()) {
            keep(c);
            doKeep = true;
        } else {
            for (Card j : hand) {
                if (j.getValue() == c.getValue()) {
                    keep(c);
                    doKeep = true;
                    break;
                }
            }
        }

        if (!doKeep) {
            discard(c);
        } else {
            chooseDiscard();
        }
    }

    public synchronized void chooseDiscard() {
        boolean doneDiscard = false;
        seeHand();
        System.out.println("Hand Size with Extra Card: " + getHandSize());
        for (Card j : hand) {
            int val = j.getValue();
            if (val == getOwner()) {
                continue;
            } else {
                for (Card k : hand) {
                    if (j.getValue() == k.getValue() && hand.indexOf(j) != hand.indexOf(k)) {
                        continue;
                    } else {
                        remFromHand(hand.indexOf(k));
                        discard(k);
                        doneDiscard = true;
                        break;
                    }
                }
                if (doneDiscard) {
                    System.out.println("New Hand Size: " + getHandSize());
                    break;
                }
            }
        }
    }

    public void discard(Card n) {
        if (getOwner() != CardGame.numPlayers) {
            CardGame.deckObj[getOwner()].addToDeck(n);
            System.out.println("player " + getOwner() + " discards " +
                    (n.getValue()) + " to deck " + (getOwner() + 1));
        } else {
            CardGame.deckObj[0].addToDeck(n);
            System.out.println("player " + CardGame.numPlayers + " discards a " +
                    (n.getValue()) + " to deck 1");
        }
    }

    public void seeHand() {
        System.out.println("player " + getOwner() + " current hand is ");
        for (int i = 0; i < CardGame.playerObj[getOwner() - 1].getHand().size(); i++) {
            System.out.println("player: " + getOwner() + " Card: " + CardGame.playerObj[getOwner() - 1].getHandCard(i).getValue() + ", ");
        }
    }

    public synchronized void play() {
        boolean winner = false;
        while (!winner) {

            Card c = draw();

            strategy(c);

            seeHand();
            winner = CardGame.isWinner(CardGame.playerObj[getOwner() - 1]);
        }
    }
}
