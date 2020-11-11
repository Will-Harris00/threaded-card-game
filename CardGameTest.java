import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CardGameTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void main() {
    }

    @Test
    void validateInput() {
        // Checks that a positive integer input doesn't throw any exceptions.
        Scanner inputPlayersPosInt = new Scanner("4");
        assertDoesNotThrow(() -> CardGame.validateInput(inputPlayersPosInt));
        // Checks that a string input throws NumberFormatException.
        Scanner inputPlayersString = new Scanner("string");
        assertThrows(NumberFormatException.class, () -> CardGame.validateInput(inputPlayersString));
    }

    @Test
    void importPack() {
    }

    @Test
    void dealCards() {
    }

    @Test
    void genHashMap() {
    }

    @Test
    void countFrequencies() {
    }
}