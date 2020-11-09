import java.util.*;

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
    public void run() { play(); }


    /**
     * @param len Number of cards in the deck.
     * @return Random number from 1 to the length of the list.
     */
    public int random(int len) {
        Random rand = new Random(); //instance of random class
        return rand.nextInt(len);
    }


    /**
     * Method which continues to play the game, with each player drawing and discarding cards, until a player has four
     * cards with the same values, and therefore wins the game.
     */
    public Card draw() {
        int index = random(CardGame.deckObj[getOwner() - 1].getDeck().size());
        System.out.println("player " + getOwner() + " draws a " +
                ((CardGame.deckObj[getOwner() - 1]).getDeckCard(index).getValue()) + " from deck " + (getOwner()));

        Card c = CardGame.deckObj[getOwner() - 1].getDeckCard(index);

        System.out.println("player: " + getOwner() + " length: " + CardGame.deckObj[getOwner() - 1].getDeck().size() + " index: " + index);
        CardGame.deckObj[getOwner() - 1].remFromDeck(index);
        return c;
    }


    public void keep(Card c) {
        CardGame.playerObj[getOwner() - 1].addToHand(c);
    }


    public synchronized void newStrat(Card c) {

        ArrayList<Integer> pick = new ArrayList<>();

        for (Card i : hand) {
            pick.add(i.getValue());
        }

        Map<Integer, Integer> map = CardGame.genHashMap(pick);
        // if picked-up card is preferred, keep card and discard non-preferred
        if (c.getValue() == getOwner()) {
            System.out.println("Owner: " + getOwner() + ", Preferred: " + c.getValue());
            keep(c);
            for (Card j : hand) {
                if (j.getValue() != getOwner()) {
                    discard(j);
                    break;
                }
            }
        }

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
                    //5432 keep 2 No.pairs = 4 && map.getValue(c.getValue) == 1
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
                        System.out.println("magic");
                        seeHand();
                        System.out.println("magic card: " + c.getValue());
                    }

                } catch (NullPointerException f) {
                    // When the card picked-up is not in the hand discard immediately
                    discard(c);
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


    public void play() {
        boolean winner = false;
        // while (!winner) {
        for (int i = 0; i < 15; i++) {
            if (CardGame.deckObj[getOwner() - 1].getDeck().size() != 0) {
                Card c = draw();

                newStrat(c);

                seeHand();

                System.out.println("Deck Not Zero");
            }
            else {
                System.out.println("Deck is Empty");
            }
            winner = CardGame.isWinner(CardGame.playerObj[getOwner() - 1]);
        }
        System.out.println("fin");
        seeHand();
    }
}
