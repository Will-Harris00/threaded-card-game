import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

/**
 * Manages actions of the players in the game.
 *
 * @author 014485
 * @author 054530
 * @version 1.0
 */
public class Player extends Thread {
    private final int pNumber;
    private final ArrayList<Card> hand = new ArrayList<>();


    /**
     * @param pNumber The player ID by which to identify the player.
     */
    public Player(int pNumber) {
        this.pNumber = pNumber;
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
    public int getPlayer() {
        return this.pNumber;
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


    public int drawValue() {
        return CardGame.deckObj[pNumber - 1].getDeckCard(0).getValue();
    }


    /**
     * Method which keeps the card drawn by the player and adds it to their hand.
     *
     * @param c The card drawn by the player.
     */
    public void keep(Card c) {
        CardGame.playerObj[getPlayer() - 1].addToHand(c);
    }


    /**
     * Method which decides what the player should do with their newly drawn card.
     *
     * @param c The card drawn by the player.
     */
    public synchronized void strategy(Card c) {
        boolean doneDiscard = false;

        // If drawn card is preferred, keep card and discard the first non-preferred card in hand.
        if (c.getValue() == getPlayer()) {
            System.out.println(("Player: " + getPlayer() + ", Preferred: " + c.getValue()));
            for (Card j : hand) {
                if (j.getValue() != getPlayer()) {
                    remove(j);
                    discard(j);
                    doneDiscard = true;
                    break;
                }
            }
            if (!doneDiscard) {
                // remove(c);
                discard(c);
            } else {
                keep(c);
            }
        }

        // If drawn card is another player's preferred card, then discard.
        else if (c.getValue() <= CardGame.numPlayers) {
            discard(c);
        }
        // If drawn card is no players preferred card, perform additional checks.
        else {
            System.out.println("Player: " + getPlayer() + ", Card Value: " + c.getValue());
            // If hand doesn't contain preferred cards, check hand for other players' preferred cards and discard them.
            for (Card j : hand) {
                if (j.getValue() <= CardGame.numPlayers && j.getValue() != getPlayer()) {
                    discard(j);
                    remove(j);
                    doneDiscard = true;
                    break;
                }
            }
            if (doneDiscard) {
                keep(c);
            }
            // If hand contains no players' preferred cards, check for matching cards with their existing hand.
            else {
                for (Card j : hand) {
                    if (j.getValue() == c.getValue()) {
                        for (Card k : hand) {
                            if (k.getValue() != getPlayer() && k.getValue() != c.getValue()) {
                                remove(j);
                                discard(k);
                                doneDiscard = true;
                                break;
                            }
                        }
                    }
                    if (doneDiscard) {
                        keep(c);
                    }
                    // If hand contains no players' preferred cards and no matching cards, discard drawn card.
                    else {
                        discard(c);
                    }
                    break;
                }
            }
        }
    }


    /**
     * Method which draws a card from the player's deck.
     *
     * @return The card from the player's deck.
     */
    public Card draw() {
        // player picks a card from the top of the deck to their left
        StringBuilder writeString = new StringBuilder();
        writeString.append("player " + getPlayer() + " draws a " +
                drawValue() + " from deck " + getPlayer());

        Card c = CardGame.deckObj[getPlayer() - 1].getDeckCard(0);

        CardGame.deckObj[getPlayer() - 1].remFromDeck(0);

        System.out.println(writeString.toString().trim());
        writeToFile(writeString.toString().trim());
        writeToFile(System.lineSeparator());

        return c;
    }


    /**
     * Method which discards a card from the player's hand to a deck.
     *
     * @param n The card to discard from the player's hand.
     */
    public void discard(Card n) {
        StringBuilder writeString = new StringBuilder();
        // player discards card to the bottom of the deck to their right
        if (getPlayer() != CardGame.numPlayers) {
            CardGame.deckObj[getPlayer()].addToDeck(n);
            writeString.append("player " + getPlayer() + " discards " +
                    n.getValue() + " to deck " + (getPlayer() + 1));
        } else {
            CardGame.deckObj[0].addToDeck(n);
            writeString.append("player " + CardGame.numPlayers +
                    " discards a " + n.getValue() + " to deck 1");
        }
        System.out.println(writeString.toString().trim());
        writeToFile(writeString.toString().trim());
        writeToFile(System.lineSeparator());
    }


    /**
     * Method which removes the card from the player's hand.
     *
     * @param n The card to remove from the player's hand.
     */
    public void remove(Card n) {
        CardGame.playerObj[getPlayer() - 1].remFromHand(hand.indexOf(n));
    }


    // Displays the cards in the hand of a player.
    public void seeHand(String delim) {
        StringBuilder writeString = new StringBuilder();
        writeString.append("player " + getPlayer() + delim);
        for (int i = 0; i < CardGame.playerObj[getPlayer() - 1].getHand().size(); i++) {
            writeString.append(CardGame.playerObj[getPlayer() - 1].getHandCard(i).getValue()).append(" ");
        }
        System.out.println(writeString.toString().trim());
        writeToFile(writeString.toString().trim());
    }


    /**
     * Writes the output of actions in the game to a file.
     *
     * @param writeString The description of the game action which has just occurred.
     */
    public void writeToFile(String writeString) {
        try {
            FileWriter myWriter = new FileWriter("player" + getPlayer() + "_output.txt", true);
            myWriter.write(writeString);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    // Creates an output file for each player.
    public void createFile() {
        try {
            new FileWriter("player" + getPlayer() + "_output.txt", false);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    // Method for each player to play the game until a winner is established.
    public void run() {
        // synchronized (this) {
        isWinner();
        createFile();
        seeHand(" initial hand ");
        writeToFile(System.lineSeparator());
        while (!CardGame.complete.get()) {
            isWinner();
            /*
            if (CardGame.complete.get()) {
                Thread.currentThread().interrupt();
            }

             */

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                // e.printStackTrace();
                // Thread.currentThread().interrupt();
            }

            System.out.println(Thread.currentThread().getName() + CardGame.complete);
            synchronized (this) {
                if (CardGame.deckObj[getPlayer() - 1].getDeck().size() != 0) {
                    Card c = draw();

                    strategy(c);
                    // writes current hand to deck
                    if (!CardGame.complete.get()) {
                        seeHand(" current hand is ");
                        writeToFile(System.lineSeparator());
                    }

                    System.out.println(getPlayer() + " Deck Not Zero");
                } else {
                    System.out.println(getPlayer() + " Empty Deck");
                }
            }
        }
            /*
            for (Player player : CardGame.playerObj) {
                if (player.getPlayer() != this.pNumber) {
                    player.interrupt();
                }
            }
             */
        Thread.currentThread().interrupt();
        StringBuilder writeString = new StringBuilder();
        if (CardGame.winner.get() != pNumber) {
            if (CardGame.winner.get() == 0) {
                CardGame.winner.get();
            }
            writeString.append("player " + CardGame.winner.get() +
                    " has informed player " + pNumber +
                    " that player " + CardGame.winner.get() + " has won");
            writeString.append(System.lineSeparator());
            writeString.append("player " + pNumber + " exits");
            writeString.append(System.lineSeparator());
            writeToFile(writeString.toString());
            seeHand(" hand: ");

        } else {
            System.out.println("player " + pNumber + " wins");
            writeString.append("player " + pNumber + " wins");
            writeString.append(System.lineSeparator());
            writeString.append("player " + pNumber + " exits");
            writeString.append(System.lineSeparator());
            writeToFile(writeString.toString());
            seeHand(" final hand: ");
        }
        //}
    }


    /**
     * Method which declares the winner of the game.
     *
     * @return The name of the winner.
     */
    public void isWinner() {
        int match = this.hand.get(0).getValue();

        for (Card element : this.hand) {
            if (element.getValue() != match) {
                return;
            }
        }
        synchronized (this) {
            CardGame.complete.compareAndSet(false, true);
            if (CardGame.winner.get() == 0 && CardGame.complete.get()) {
                System.out.println("player " + pNumber + " wins");
                CardGame.winner.compareAndSet(0, pNumber);
                CardGame.complete.set(true);
            }
        }
        /*
        synchronized (this) {
            if (CardGame.winner == 0) {
                System.out.println("player " + pNumber + " wins");
                CardGame.winner = pNumber;
            }
        }
         */
    }
}
