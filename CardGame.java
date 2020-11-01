import java.io.*;
import java.util.ArrayList;
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
        System.out.println(packArr.toString());
    }
}
