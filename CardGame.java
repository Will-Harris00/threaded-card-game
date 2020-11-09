import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Executable multi-threaded card game with n players, each with a deck of cards. Every player holds four cards,
 * with the hands and decks drawn from a pack which contains 8n cards. Each card has a face value of a non-negative
 * integer.
 * <p>
 * Cards are continually drawn and discarded by players sequentially until a player has four cards with the same value
 * in their hand. This player is declared as the winner.
 *
 * @author 014485
 * @author 054530
 * @version 1.0
 */
public class CardGame {
    // Creates array of player hand and card deck objects.
    public static Player[] playerObj;
    public static CardDeck[] deckObj;
    static int numPlayers;


    public static void main(String[] args) throws IOException {
        int numPlayers = validateInput();

        ArrayList<Integer> packArr = importPack(numPlayers);
        // Initialises array of player hand and deck objects.
        playerObj = new Player[numPlayers];
        deckObj = new CardDeck[numPlayers];
        // Creates and initialises player objects using constructor.
        for (int p = 0; p < numPlayers; p++) {
            playerObj[p] = new Player(p + 1);
            deckObj[p] = new CardDeck(p + 1);
        }

        System.out.println("\nCard Pack: " + packArr.toString());
        dealCards(packArr, numPlayers, playerObj, deckObj);

        for (Player player : playerObj) {
            player.start();
            player.setName("Player" + player.getOwner());
            System.out.println(player.getName());
        }
    }


    /**
     * Method to set up the game for the user according to the number of players.
     *
     * @return The number of players in the game.
     */
    public static int validateInput() {
        Scanner inputPlayers = new Scanner(System.in);
        System.out.print("Please enter the number of players: ");
        numPlayers = 0;
        try {
            numPlayers = Integer.parseInt(inputPlayers.nextLine());
            while (numPlayers < 2) {
                System.out.println("Input must be a positive integer with a minimum of two players.");
                System.out.print("Please enter the number of players: ");
                numPlayers = inputPlayers.nextInt();
            }
        } catch (NumberFormatException e) {
            System.out.println("Input must be an integer.");
            System.exit(1);
        }
        return numPlayers;
    }


    /**
     * Method which imports the pack of cards from a given .txt file.
     *
     * @param numPlayers The number of players in the game.
     * @return The array of cards in the pack.
     * @throws IOException Failures to read the given file.
     */
    public static ArrayList<Integer> importPack(int numPlayers) throws IOException {
        Scanner inputPack = new Scanner(System.in);
        System.out.print("Please enter location of pack to load: ");
        String packIn = inputPack.nextLine();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(packIn));
        } catch (FileNotFoundException e) {
            System.out.println("Please specify the pack to load.");
            System.exit(1);
        }

        String line;
        ArrayList<Integer> packArr = new ArrayList<>();
        int numLine = 0;
        while ((line = in.readLine()) != null) {
            int value = 0;
            try {
                value = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid pack - Each line must only contain a positive integer.");
                System.exit(1);
            }
            if (value > 0) {
                packArr.add(value);
            } else {
                System.out.println("Invalid pack - Each line must only contain a positive integer.");
                System.exit(1);
            }
            numLine++;
        }
        if (numLine != 8 * numPlayers) {
            System.out.println("The number of lines in the pack file should be " + numPlayers * 8);
            System.exit(1);
        }

        boolean playGame = countFrequencies(packArr, numPlayers);
        System.out.println("\nPlay Game: " + playGame);

        if (!playGame) {
            System.exit(1);
        }

        return packArr;
    }


    /**
     * Method which deals cards from the pack to the players' decks.
     *
     * @param packArr    The array of cards in the pack.
     * @param numPlayers The number of players in the game.
     * @param playerObj  The list of players, each identified by player IDs.
     * @param deckObj    The list of decks of players, each identified by player IDs.
     */
    public static void dealCards(ArrayList<Integer> packArr, int numPlayers, Player[] playerObj, CardDeck[] deckObj) {
        int j = 0;
        int i = 0;
        while (i < packArr.size()) {
            while (playerObj[numPlayers - 1].getHandSize() < 4) {
                Card c = new Card();
                c.setValue(packArr.get(i));
                c.setHolder(j + 1);
                playerObj[j].addToHand(c);
                System.out.println("\nHand Size: " + playerObj[j].getHandSize());
                System.out.println("Player: " + (j + 1));
                if (j < numPlayers - 1) {
                    j++;
                } else {
                    j = 0;
                }
                System.out.println("Assigned Card: " + packArr.get(i));
                i++;
            }
            Card c = new Card();
            c.setValue(packArr.get(i));
            c.setHolder(j + 1);
            deckObj[j].addToDeck(c);
            System.out.println("\nDeck Size: " + deckObj[j].getDeckSize());
            System.out.println("Deck: " + (j + 1));
            if (j < numPlayers - 1) {
                j++;
            } else {
                j = 0;
            }
            System.out.println("Assigned Card: " + packArr.get(i));
            i++;
        }
    }


    public static Map<Integer, Integer> genHashMap(ArrayList<Integer> packArr) {
        // hashmap to store the frequency of element
        Map<Integer, Integer> dict = new HashMap<>();

        for (Integer key : packArr) {
            Integer j = dict.get(key);
            dict.put(key, (j == null) ? 1 : j + 1);
        }

        for (Map.Entry<Integer, Integer> val : dict.entrySet()) {
            System.out.println("Element " + val.getKey() + " "
                    + "occurs"
                    + ": " + val.getValue() + " times");
        }
        return dict;
    }


    /**
     * Method which counts the frequency of each card value in every player's hand to help determine their status in
     * the game and what strategy they should employ.
     *
     * @param packArr    The array of cards in the pack.
     * @param numPlayers The number of players in the game.
     * @return Whether the game should continue or not.
     */
    public static boolean countFrequencies(ArrayList<Integer> packArr, int numPlayers) {
        // hashmap to store the frequency of element
        Map<Integer, Integer> dict = genHashMap(packArr);

        boolean playGame = false;

        for (int p = 1; p < numPlayers + 1; p++) {
            try {
                if (dict.get(p) >= 4) {
                    System.out.println("\nPlayer " + p + " could collect a winning hand.");
                    playGame = true;
                } else {
                    System.out.println("\nPlayer " + p + " is at a disadvantage as there are\nfewer than four of their preferred cards.");
                }
            } catch (NullPointerException e) {
                System.out.println("\nPlayer " + p + " has no preferred cards in pack.");
            }
        }

        if (playGame) {
            System.out.println("\nThere is guaranteed to be a winner.");
            return true;
        } else {
            // displaying the occurrence of elements in the arraylist
            for (Map.Entry<Integer, Integer> val : dict.entrySet()) {
                if (val.getValue() >= 4) {
                    System.out.println("\nThere is the possibility of a winning hand but the game may stagnate.");
                    playGame = true;
                }
            }
            return playGame;
        }
    }


    /**
     * Method which declares the winner of the game.
     *
     * @param player The ID of the player whose hand to check.
     * @return The name of the winner.
     */
    public static boolean isWinner(Player player) {
        boolean winner = true;
        int match = player.getHandCard(0).getValue();

        for (Card element : player.getHand()) {
            if (element.getValue() != match) {
                // System.out.print(element.getValue());
                winner = false;
                break;
            }
        }
        return winner;
    }
}
