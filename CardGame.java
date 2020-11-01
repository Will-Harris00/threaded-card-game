import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CardGame {
    public static void main (String[] args) throws IOException {
        Scanner inputPlayers = new Scanner(System.in);
        System.out.print("Please enter the number of players: ");
        int numPlayers;
        try {
            numPlayers = Integer.parseInt(inputPlayers.nextLine());
            while (numPlayers < 2) {
                System.out.println("Input must be a positive integer with a minimum of two players.");
                System.out.print("Please enter the number of players: ");
                numPlayers = inputPlayers.nextInt();
            }
        } catch (NumberFormatException e) {
            System.out.println("Input must be an integer.");
            return;
        }

        Scanner inputPack = new Scanner(System.in);
        System.out.print("Please enter location of pack to load: ");
        String packIn = inputPack.nextLine();
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(packIn));
        } catch (FileNotFoundException e) {
            System.out.println("Please specify the pack to load.");
            return;
        }

        String line;
        ArrayList<Integer> packArr = new ArrayList<>();
        int numLine = 0;
        while((line = in.readLine()) != null){
            int value;
            try {
                value = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid pack - Each line must only contain a positive integer.");
                return;
            }
            if (value > 0) {
                packArr.add(value);
            } else {
                System.out.println("Invalid pack - Each line must only contain a positive integer.");
                return;
            }
            numLine++;
        }
        if (numLine != 8 * numPlayers) {
            System.out.println("The number of lines in pack file should be " + numPlayers * 8);
            return;
        }
        System.out.println(countFrequencies(packArr, numPlayers));
        //create array of player object
        Player[] playerObj = new Player[numPlayers];
        CardDeck[] deckObj = new CardDeck[numPlayers];
        //create & initialize actual player objects using constructor
        for (int p = 0; p < numPlayers; p++) {
            playerObj[p] = new Player();
            deckObj[p] = new CardDeck();
        }
        System.out.println(packArr.toString());
        dealCards(packArr, numPlayers, playerObj, deckObj);
        System.out.println(deckObj[3].getDeckCard(3).getValue());
        System.out.println(deckObj[3].getDeckCard(3).getHolder());
    }


    public static void dealCards(ArrayList<Integer> packArr, int numPlayers, Player[] playerObj, CardDeck[] deckObj) {
        int j = 0;
        int i = 0;
        while (i < packArr.size()) {
            while (playerObj[numPlayers - 1].handSize() < 4) {
                Card c = new Card();
                c.setValue(packArr.get(i));
                c.setHolder(j+1);
                playerObj[j].addToHand(c);
                System.out.println("\nHand Size: " + playerObj[j].handSize());
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
            c.setHolder(j+1);
            deckObj[j].addToDeck(c);
            System.out.println("\nDeck Size: " + deckObj[j].deckSize());
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


    public static boolean countFrequencies(ArrayList<Integer> packArr, int numPlayers) {
        // hashmap to store the frequency of element
        Map<String, Integer> dict = new HashMap<>();

        for (Integer i : packArr) {
            String key = i.toString();
            Integer j = dict.get(key);
            dict.put(key, (j == null) ? 1 : j + 1);
        }

        for (Map.Entry<String, Integer> val : dict.entrySet()) {
            System.out.println("Element " + val.getKey() + " "
                    + "occurs"
                    + ": " + val.getValue() + " times");
        }

        boolean playGame = false;

        for (int p = 1; p < numPlayers + 1; p++) {
            String key = Integer.toString(p);
            try {
                if (dict.get(key) >= 4) {
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
            for (Map.Entry<String, Integer> val : dict.entrySet()) {
                if (val.getValue() >= 4) {
                    System.out.println("\nThere is the possibility of a winning hand but the game may stagnate.");
                    playGame = true;
                }
            }
            return playGame;
        }
    }
}
