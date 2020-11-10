import java.io.FileWriter;
import java.io.IOException;
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

    // Starts the game.
    public void run() {
        try {
            play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param len Number of cards in the deck.
     * @return Random number from 1 to the length of the list.
     */
    public int random(int len) {
        Random rand = new Random(); //instance of random class
        return rand.nextInt(len);
    }

    /**
     * Method which keeps the card drawn by the player and adds it to their hand.
     *
     * @param c The card drawn by the player.
     */
    public void keep(Card c) {
        CardGame.playerObj[getOwner() - 1].addToHand(c);
    }

    /**
     * Method which decides what the player should do with their newly drawn card.
     *
     * @param c The card drawn by the player.
     */
    public synchronized void newStrat(Card c) {
        boolean doneDiscard = false;

         /*
        ArrayList<Integer> pick = new ArrayList<>();

        for (Card i : hand) {
            pick.add(i.getValue());
        }

        // Map<Integer, Integer> map = CardGame.genHashMap(pick);
        Map<Integer, Integer> map = new HashMap<>();
        map = CardGame.genHashMap(pick);
         */

        // If drawn card is preferred, keep card and discard the first non-preferred card in hand.
        if (c.getValue() == getOwner()) {
            writeToFile("Owner: " + getOwner() + ", Preferred: " + c.getValue());
            writeToFile(System.lineSeparator());
            for (Card j : hand) {
                if (j.getValue() != getOwner()) {
                    remove(j);
                    discard(j);
                    doneDiscard = true;
                    break;
                }
            }
            if (!doneDiscard) {
                remove(c);
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
            writeToFile("Owner: " + getOwner() + ", Card Value: " + c.getValue());
            writeToFile(System.lineSeparator());
            // If hand doesn't contain preferred cards, check hand for other players' preferred cards and discard them.
            for (Card j : hand) {
                if (j.getValue() <= CardGame.numPlayers && j.getValue() != getOwner()) {
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
                            if (k.getValue() != getOwner() && k.getValue() != c.getValue()) {
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
        /*
        // if card is not preferred check if hand already contains preferred card
        else {
            System.out.println("Owner: " + getOwner() + ", Card Value: " + c.getValue());
            try {
                // if hand contains preferred card, discard pick-up
                map.get(getOwner());
            } catch (NullPointerException e) {
                try {
                    // checks if hand contains picked-up card
                    map.get(c.getValue());

                    // Example player 1 picks-up a card with face value two
                    // hand is 5432 keep 2 No.key-value pairs = 4 && map.getValue(c.getValue) == 1
                    if (map.size() == 4 && map.get(c.getValue()) == 1) {
                        for (Card j : hand) {
                            if (j.getValue() != c.getValue()) {
                                discard(j);
                                break;
                            }
                        }
                        keep(c);
                        System.out.println("Path A" + hand.size());
                    }
                    // hand is 4432 keep the picked-up 2. No.key-value pairs = 3 && map.getValue(c.getValue) == 1
                    else if (map.size() == 3 && map.get(c.getValue()) == 1) {
                        for (Card j : hand) {
                            if (j.getValue() != c.getValue() && map.get(j.getValue()) == 1) {
                                discard(j);
                                break;
                            }
                        }
                        keep(c);
                        System.out.println("Path B" + hand.size());
                    }
                    // hand is 4322 keep the picked-up 2. No.key-value pairs = 3 && map.getValue(c.getValue) == 2
                    else if (map.size() == 3 && map.get(c.getValue()) == 2) {
                        for (Card j : hand) {
                            if (j.getValue() != c.getValue() && map.get(j.getValue()) == 1) {
                                discard(j);
                                break;
                            }
                        }
                        keep(c);
                        System.out.println("Path C" + hand.size());
                    }
                    // hand is 3332 discard the picked-up 2. No.key-value pairs = 2 && map.getValue(c.getValue) == 1
                    else if (map.size() == 2 && map.get(c.getValue()) == 1) {
                        discard(c);
                        System.out.println("Path D" + hand.size());
                    }
                    // hand is 3322 keep the picked-up 2. No.key-value pairs && map.getValue(c.getValue) == 2
                    else if (map.size() == 2 && map.get(c.getValue()) == 2) {
                        for (Card j : hand) {
                            if (j.getValue() != c.getValue() && map.get(j.getValue()) == 2) {
                                discard(j);
                                break;
                            }
                        }
                        keep(c);
                        System.out.println("Path E" + hand.size());
                    }
                    // hand is 2223 keep the picked-up 2. No.key-value pairs && map.getValue(c.getValue) == 3
                    else if (map.size() == 2 && map.get(c.getValue()) == 3) {
                        for (Card j : hand) {
                            if (j.getValue() != c.getValue() && map.get(j.getValue()) == 1) {
                                discard(j);
                                break;
                            }
                        }
                        keep(c);
                        System.out.println("Path F" + hand.size());
                    } else {
                        System.out.println(map.size());
                        System.out.println("error");
                        seeHand();
                        System.out.println("magic card: " + c.getValue());
                    }

                } catch (NullPointerException f) {
                    // When the card picked-up is not in the hand discard immediately
                    discard(c);
                }
            }
        }
         */
    }

    /**
     * Method which draws a card from the player's deck.
     *
     * @return The card from the player's deck.
     */
    public Card draw() {
        StringBuilder writeString = new StringBuilder();
        int index = random(CardGame.deckObj[getOwner() - 1].getDeck().size());
        writeString.append("player ").append(getOwner()).append(" draws a ").append((CardGame.deckObj[getOwner() - 1])
                .getDeckCard(index).getValue()).append(" from deck ").append(getOwner());

        Card c = CardGame.deckObj[getOwner() - 1].getDeckCard(index);

        CardGame.deckObj[getOwner() - 1].remFromDeck(index);

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
        if (getOwner() != CardGame.numPlayers) {
            CardGame.deckObj[getOwner()].addToDeck(n);
            writeString.append("player ").append(getOwner()).append(" discards ")
                    .append(n.getValue()).append(" to deck ").append(getOwner() + 1);
        } else {
            CardGame.deckObj[0].addToDeck(n);
            writeString.append("player ").append(CardGame.numPlayers)
                    .append(" discards a ").append(n.getValue()).append(" to deck 1");
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
        CardGame.playerObj[getOwner() - 1].remFromHand(hand.indexOf(n));
    }

    // Displays the cards in the hand of a player.
    public void seeHand() {
        StringBuilder writeString = new StringBuilder();
        writeString.append("player ").append(getOwner()).append(" current hand is ");
        for (int i = 0; i < CardGame.playerObj[getOwner() - 1].getHand().size(); i++) {
            writeString.append(CardGame.playerObj[getOwner() - 1].getHandCard(i).getValue()).append(" ");
        }
        System.out.println(writeString.toString().trim());
        writeToFile(writeString.toString().trim());
        writeToFile(System.lineSeparator());
    }

    /**
     * Writes the output of actions in the game to a file.
     *
     * @param writeString The description of the game action which has just occurred.
     */
    public void writeToFile(String writeString) {
        try {
            FileWriter myWriter = new FileWriter("player" + getOwner() + "_output.txt", true);
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
            new FileWriter("player" + getOwner() + "_output.txt", false);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // Method for each player to play the game until a winner is established.
    public void play() throws IOException {
        synchronized (this) {
            boolean winner = false;
            createFile();
            while (!winner) {
                // for (int i = 0; i < 100; i++) {
                winner = CardGame.isWinner(CardGame.playerObj[getOwner() - 1]);
                if (CardGame.deckObj[getOwner() - 1].getDeck().size() != 0) {
                    Card c = draw();

                    seeHand();
                    newStrat(c);

                    System.out.println("Deck Not Zero");
                } else {
                    System.out.println("Deck is Empty");
                }
            }
            writeToFile(Boolean.toString(CardGame.isWinner(CardGame.playerObj[getOwner() - 1])));
            writeToFile(System.lineSeparator());
            seeHand();
        }
    }
}
