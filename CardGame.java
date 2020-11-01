import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
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
        ArrayList<String> packArr = new ArrayList<>();
        while((line = in.readLine()) != null){
            if (line.matches("[0-9]")) {
                System.out.println(line);
                packArr.add(line);
            }
        }
        System.out.println(packArr.toString());
    }
}
