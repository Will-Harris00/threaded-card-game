import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.*;

public class CardGameTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void main() {
    }

    /**
     * Tests validateNumPlayersInput method with a valid input of a positive integer.
     *
     * @throws NumberFormatException When an input which cannot be parsed as an integer is entered.
     */
    @Test
    public void testValidateNumPlayersInput() throws NumberFormatException, IllegalNumPlayersSizeException {
        // Creates an input which can be parsed as an integer greater than 1.
        Scanner inputPlayersPosInt = new Scanner("4");
        int expected = 4;
        int result = CardGame.validateNumPlayersInput(inputPlayersPosInt);
        assertEquals(expected, result);
    }

    // Tests validateNumPlayersInput with an invalid input of an integer which is too small.
    @Test
    public void testValidateNumPlayersInput_IllegalSmallValue() {
        // Creates an input which can be parsed as an integer, but is too small.
        try {
            Scanner inputPlayersString = new Scanner("1");
            CardGame.validateNumPlayersInput(inputPlayersString);
            fail("Should have thrown IllegalNumPlayersSizeException.");
        } catch (IllegalNumPlayersSizeException e) {
            assertTrue(true);
        }
    }

    // Tests validateNumPlayersInput with an invalid input of a negative integer value.
    @Test
    public void testValidateNumPlayersInput_IllegalNegValue() {
        // Creates an input which can be parsed as an integer, but is negative.
        try {
            Scanner inputPlayersString = new Scanner("-1");
            CardGame.validateNumPlayersInput(inputPlayersString);
            fail("Should have thrown IllegalNumPlayersSizeException.");
        } catch (IllegalNumPlayersSizeException e) {
            assertTrue(true);
        }
    }

    /**
     * Tests validateNumPlayersInput method with an invalid input of a string.
     *
     * @throws NumberFormatException          When an input which cannot be parsed as an integer is entered.
     * @throws IllegalNumPlayersSizeException When an input which can be parsed as an integer, but is too small, is
     *                                        entered.
     */
    @Test(expected = NumberFormatException.class)
    public void testValidateNumPlayersInput_IllegalStringValue() throws NumberFormatException, IllegalNumPlayersSizeException {
        // Creates an input which cannot be passed as an integer (only a string).
        Scanner inputPlayersString = new Scanner("string");
        CardGame.validateNumPlayersInput(inputPlayersString);
        fail("NumberFormatException should have been thrown.");
    }

    // Tests validatePackInput method with an invalid non-existing file name.
    @Test(expected = FileNotFoundException.class)
    public void testValidatePackInput_IllegalFileName() {
        Scanner inputPack = new Scanner("invalid.txt");
        CardGame.validatePackInput(inputPack);
        fail("FileNotFoundException should have been thrown.");
    }

    @Test
    public void testImportPack() {
    }

    @Test
    public void testDealCards() {
    }

    @Test
    public void testGenHashMap() {
    }

    @Test
    public void testCountFrequencies() {
    }
}