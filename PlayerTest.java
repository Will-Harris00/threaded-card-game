import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTest {
    private Player[] plArray;
    private CardDeck[] dkArray;
    private Player plObject;
    private CardDeck dkObject;
    AtomicBoolean complete;
    AtomicInteger winner;

    // Creates mock objects for player1, card with value 5, and player1's hand to 5, 5, 5, 5.
    @Before
    public void playerSetUp() {
        plObject = new Player(1);
        // set up a target test hand
        Card n = new Card();
        n.setValue(5);
        for (int i = 0; i < 4; i ++) {
            plObject.getHand().add(n);
        }
    }

    // Creates mock objects for deck1, card with value 8, and deck1's deck to 8, 8, 8, 8.
    @Before
    public void deckSetUp() {
        dkObject = new CardDeck(1);
        // set up a target test deck
        Card m = new Card();
        m.setValue(8);
        for (int i = 0; i < 4; i ++) {
            dkObject.getDeck().add(m);
        }
    }

    // Creates mock object for a player, and then assigns three duplicate players to the player array.
    @Before
    public void playerArrSetUp() {
        plArray = new Player[3];
        // Adds the player to the player array.
        for (int i = 0; i < 3; i++) {
            plArray[i] = plObject;
        }
    }

    // Creates mock object for a deck, and then assigns three duplicate decks to the deck array.
    @Before
    public void deckArrSetUp() {
        dkArray = new CardDeck[3];
        for (int i = 0; i < 3; i++) {
            dkArray[i] = dkObject;
        }
    }

    @After
    public void tearDown () {
        plObject = null;
        dkObject = null;
        plArray = null;
        dkArray = null;
    }

    @Test
    public void testGetHandCard() {
        assertEquals(5, plObject.getHandCard(0).getValue());
    }

    @Test
    public void testGetHand() {
    }

    @Test
    public void testGetHandSize() {
        int expected = plObject.getHand().size();
        assertEquals(expected, plObject.getHandSize());
    }

    @Test
    public void testGetPlayer() {
    }

    @Test
    public void testAddToHand() {
        Card n = new Card();
        n.setValue(7);
        plObject.getHand().add(n);
        int expected = plObject.getHand().get(4).getValue();
        int result = n.getValue();
        assertEquals(5, plObject.getHandSize());
        assertEquals(expected, result);
    }

    @Test
    public void testRemFromHand() {
        plObject.remFromHand(0);
        assertEquals(3, plObject.getHandSize());
    }

    @Test
    public void testDrawValue() {
        CardDeck tDeck = new CardDeck(1);
        int expected = 0;
        for (int i = 1; i <= 4; i++) {
            Card tCard = new Card();
            tCard.setValue(i);
            tDeck.addToDeck(tCard);
            if (i == 1) {
                expected = tCard.getValue();
            }
        }
        int result = tDeck.getDeck().get(0).getValue();
        assertEquals(expected, result);
    }

    @Test
    public void testDraw() {
        CardDeck tDeck = new CardDeck(1);
        Card expected = null;
        Card tCard = new Card();
        for (int i = 1; i <= 4; i++) {
            tCard.setValue(i);
            tDeck.addToDeck(tCard);
            if (i == 1) {
                expected = tCard;
            }
        }
        Card result = tDeck.getDeck().get(0);
        assertEquals(expected, result);
    }

    @Test
    public void testDrawCard() {
    }

    @Test
    public void testChooseDiscard() {
        int pNumber = 2;
        Player tPlayer = new Player(pNumber);
        // single non-preferred card and three preferred in player hand
        for (int i = 1; i <= 3; i++) {
            Card tempCard = new Card();
            tempCard.setValue(pNumber);
            tPlayer.addToHand(tempCard);
        }
        Card expected = new Card();
        expected.setValue(pNumber - 1);
        tPlayer.addToHand(expected);

        Card tCard = new Card();
        tCard.setValue(pNumber);

        int index = tPlayer.chooseDiscard();

        tPlayer.getHand().remove(index);

        int match = tPlayer.getHand().get(0).getValue();
        boolean correctDiscard = true;

        for (Card element : tPlayer.getHand()) {
            if (element.getValue() != match) {
                correctDiscard = false;
                break;
            }
        }
        assertTrue(correctDiscard);
    }

    @Test
    public void testDiscardCard() {
        // tests if statement within discardCard method
        int pNumber = 2;
        plArray[pNumber - 1].getHandCard(0).setValue(15);
        Card unwantedCard = plArray[pNumber - 1].getHandCard(0);
        int result = unwantedCard.getValue();
        plObject.discardCard(unwantedCard, pNumber, plArray, dkArray, 3);
        assertEquals(15, result);

        // tests else statement for discard to deck zero within discardCard method
        pNumber = 3;
        plArray[pNumber - 1].getHandCard(0).setValue(17);
        unwantedCard = plArray[pNumber - 1].getHandCard(0);
        result = unwantedCard.getValue();
        plObject.discardCard(unwantedCard, pNumber, plArray, dkArray, 3);
        assertEquals(17, result);
    }

    @Test
    public void testKeepCard() {
        int pNumber = 2;
        Card tCard = new Card();
        tCard.setValue(11);

        plObject.keepCard(tCard, pNumber, plArray);

        Card result = plObject.getHand().get(4);
        assertEquals(tCard, result);
    }

    @Test
    public void testRemoveCard() {
        int pNumber = 3;
        Card tCard = plArray[pNumber - 1].getHandCard(0);

        plObject.removeCard(tCard, pNumber, plArray);

        int result = plArray[pNumber - 1].getHandSize();
        assertEquals(3, result);
    }

    @Test
    public void testStrategy() {
    }

    @Test
    public void testViewArray() {

    }

    @Test
    public void testWriteToFile() {
    }

    @Test
    public void testCreateFile() {
    }

    @Test
    public void testRun() {
    }

    @Test
    public void isWinner() {
        int pNumber = 2;
        complete = new AtomicBoolean(false);
        winner = new AtomicInteger(0);
        Player pWinner = new Player(pNumber);
        Card cTemp = new Card();
        cTemp.setValue(pNumber);
        for (int i = 1; i <= 4; i++) {
            pWinner.addToHand(cTemp);
        }
        pWinner.isWinner(complete, winner);
        if (complete.get()) {
            assertEquals(2, winner.get());
        }
    }
}
