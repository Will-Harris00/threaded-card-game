import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
        Scanner inputPlayersInt = new Scanner("4");
        assertDoesNotThrow(() -> CardGame.validateInput(inputPlayersInt));
        // Scanner inputPlayersString = new Scanner("string");
        // assertThrows(NumberFormatException.class, () -> CardGame.validateInput(inputPlayersString));
    }

    private void assertThrows(Class<NumberFormatException> numberFormatExceptionClass, Object o) {
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