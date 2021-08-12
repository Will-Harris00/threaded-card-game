package src;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manages actions of the players in the game.
 *
 * @author 014485
 * @author 054530
 * @version 1.1
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
     *
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

    // Returns the value of the card which the player has most recently picked up.
    public int drawValue() {
        return CardGame.deckObj[pNumber - 1].getDeckCard(0).getValue();
    }

    /**
     * Method which draws a card from the player's deck.
     *
     * @return The card from the player's deck.
     */
    public synchronized Card drawCard() {
        // src.Player picks a card from the top of the deck to their left.
        StringBuilder writeString = new StringBuilder();
        writeString.append("player ").append(getPlayer()).append(" draws a ").append(drawValue())
                .append(" from deck ")
                .append(getPlayer());

        Card card = CardGame.deckObj[getPlayer() - 1].getDeckCard(0);

        CardGame.deckObj[getPlayer() - 1].remFromDeck(0);

        System.out.println(writeString.toString().trim());
        writeToFile("player", writeString.toString().trim());
        writeToFile("player", System.lineSeparator());

        return card;
    }

    /**
     * Method which discards a card from the player's hand to a deck.
     *
     * @param unwantedCard The card to discard from the player's hand.
     * @param playerNum    The player ID by which to identify the player.
     * @param playerArr    The array list containing all player objects.
     */
    public synchronized void discardCard(Card unwantedCard, int playerNum,
                                         Player[] playerArr, CardDeck[] deckArr, int totalPlayers) {
        removeCard(unwantedCard, playerNum, playerArr);
        StringBuilder writeString = new StringBuilder();
        // src.Player discards card to the bottom of the deck to their right.
        if (playerNum != totalPlayers) {
            deckArr[playerNum].addToDeck(unwantedCard);
            writeString.append("player ").append(playerNum).append(" discards ")
                    .append(unwantedCard.getValue())
                    .append(" to deck ").append(getPlayer() + 1);
        } else {
            // Edge case when last player discards card back to the first player's deck.
            deckArr[0].addToDeck(unwantedCard);
            writeString.append("player ").append(totalPlayers).append(" discards a ")
                    .append(unwantedCard.getValue())
                    .append(" to deck 1");
        }

        System.out.println(writeString.toString().trim());
        writeToFile("player", writeString.toString().trim());
        writeToFile("player", System.lineSeparator());
    }

    /**
     * Method which keeps the card drawn by the player and adds it to their hand.
     *
     * @param card      The card drawn by the player.
     * @param playerNum The player ID by which to identify the player.
     * @param playerArr The array list containing all player objects.
     */
    public void keepCard(Card card, int playerNum, Player[] playerArr) {
        playerArr[playerNum - 1].addToHand(card);
    }

    /**
     * Method which removes the card from the player's hand.
     *
     * @param unwantedCard The card to remove from the player's hand.
     * @param playerNum    The player ID by which to identify the player.
     * @param playerArr    The array list containing all player objects.
     */
    public synchronized void removeCard(Card unwantedCard, int playerNum, Player[] playerArr) {
        playerArr[playerNum - 1].remFromHand(hand.indexOf(unwantedCard));
    }

    /**
     * Displays the cards in the hand/deck of a player.
     *
     * @param delim  Delimiter to display the player's initial/current/final hand or deck.
     * @param isHand Boolean to check whether it wants the hand of the player (to view hand) or not
     *               (to view deck).
     */
    public void viewArray(String delim, boolean isHand) {
        StringBuilder writeString = new StringBuilder();
        // Writes a hand array to output file.
        if (isHand) {
            writeString.append("player ").append(getPlayer()).append(delim);
            for (int i = 0; i < CardGame.playerObj[getPlayer() - 1].getHand().size(); i++) {
                writeString.append(CardGame.playerObj[getPlayer() - 1].getHandCard(i).getValue())
                        .append(" ");
            }
            System.out.println(writeString.toString().trim());
            writeToFile("player", writeString.toString().trim());
        }
        // Writes a deck array to output file.
        else {
            writeString.append(delim);
            for (int j = 0; j < CardGame.deckObj[getPlayer() - 1].getDeck().size(); j++) {
                writeString.append(CardGame.deckObj[getPlayer() - 1].getDeckCard(j).getValue())
                        .append(" ");
            }
            System.out.println(writeString.toString().trim());
            writeToFile("deck", writeString.toString().trim());
        }
    }

    /**
     * Method which decides what the player should do with their newly drawn card.
     *
     * @param card         The card drawn by the player.
     * @param playerNum    The player ID by which to identify the player.
     * @param playerArr    The array list containing all player objects.
     * @param totalPlayers The number of players playing the game.
     */
    public synchronized void strategy(Card card, int playerNum, Player[] playerArr,
                                      CardDeck[] deckArr, int totalPlayers) {
        synchronized (this) {
            // Keep every card that it picks up.
            keepCard(card, getPlayer(), playerArr);

            discardCard(hand.get(chooseDiscard()), playerNum, playerArr, deckArr, totalPlayers);
        }
    }

    /**
     * Randomly chooses the index of the card in the hand to be discarded.
     *
     * @return Index of the card in the hand to be discarded.
     */
    public synchronized int chooseDiscard() {
        boolean doneDiscard = false;
        Random rand = new Random();
        // Initialises index to an invalid index to force random index to be generated.
        int index = -2;

        while (!doneDiscard) {
            // Randomly discard a card of non-preferred value which hasn't just been picked up.
            index = rand.nextInt(4);
            if (hand.get(index).getValue() != pNumber) {
                doneDiscard = true;
            }
        }
        return index;
    }

    /**
     * Writes the output of actions in the game to a file.
     *
     * @param delim       Delimiter to decide whether the file being written to is the deck output
     *                    or the player output.
     * @param writeString The description of the game action which has just occurred.
     */
    public void writeToFile(String delim, String writeString) {
        try {
            FileWriter myWriter = new FileWriter(delim + getPlayer() + "_output.txt", true);
            myWriter.write(writeString);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Creates an output file for the player.
     *
     * @param delim Delimiter to decide whether the file being created is for the deck output or the
     *              player output.
     */
    public void createFile(String delim) {
        try {
            new FileWriter(delim + getPlayer() + "_output.txt", false);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    // Method for each player to play the game until a winner is established.
    public void run() {
        isWinner(CardGame.complete, CardGame.winner);
        createFile("player");
        createFile("deck");
        viewArray(" initial hand ", true);
        writeToFile("player", System.lineSeparator());
        while (!CardGame.complete.get()) {
            if (CardGame.deckObj[getPlayer() - 1].getDeck().size() != 0) {
                synchronized (this) {
                    Card card = drawCard();

                    strategy(card, getPlayer(), CardGame.playerObj, CardGame.deckObj,
                            CardGame.numPlayers);
                    // Writes current hand to player output file.
                    if (!CardGame.complete.get()) {
                        viewArray(" current hand is ", true);
                        writeToFile("player", System.lineSeparator());
                    }
                }
                isWinner(CardGame.complete, CardGame.winner);
                System.out.println(getPlayer() + " Deck Not Zero");
            } else {
                System.out.println(getPlayer() + " Empty Deck");
            }
        }
        // Writes the final output texts for each player output file.
        StringBuilder writeString = new StringBuilder();
        // Outputs for players who didn't win.
        if (CardGame.winner.get() != pNumber) {
            writeString.append("player ").append(CardGame.winner.get())
                    .append(" has informed player ")
                    .append(pNumber).append(" that player ").append(CardGame.winner.get())
                    .append(" has won");
            writeString.append(System.lineSeparator());
            writeString.append("player ").append(pNumber).append(" exits");
            writeString.append(System.lineSeparator());
            writeToFile("player", writeString.toString());
            viewArray(" hand: ", true);
        } else {
            // Outputs for the player who won.
            System.out.println("player " + pNumber + " wins");
            writeString.append("player ").append(pNumber).append(" wins");
            writeString.append(System.lineSeparator());
            writeString.append("player ").append(pNumber).append(" exits");
            writeString.append(System.lineSeparator());
            writeToFile("player", writeString.toString());
            viewArray(" final hand: ", true);
        }
        viewArray("deck" + pNumber + " contents: ", false);
    }

    // Method which declares the winner of the game.
    public void isWinner(AtomicBoolean complete, AtomicInteger winner) {
        int match = this.hand.get(0).getValue();

        for (Card element : this.hand) {
            if (element.getValue() != match) {
                return;
            }
        }
        synchronized (this) {
            complete.compareAndSet(false, true);
            if (winner.get() == 0 && complete.get()) {
                winner.compareAndSet(0, pNumber);
                System.out.println("player " + pNumber + " wins");
            }
        }
    }
}