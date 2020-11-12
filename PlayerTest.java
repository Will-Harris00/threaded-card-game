import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class PlayerTest {
    private Player[] plArr;
    private CardDeck[] dkArr;
    private Player pl;
    private CardDeck dk;
    AtomicBoolean complete;
    AtomicInteger winner;

    @Before
    public void playerSetUp() {
        pl = new Player(1);
        // set up a target test hand
        Card n = new Card();
        n.setValue(5);
        for (int i = 0; i < 4; i ++) {
            pl.getHand().add(n);
        }
    }

    @Before
    public void deckSetUp() {
        dk = new CardDeck(1);
        // set up a target test deck
        Card m = new Card();
        m.setValue(8);
        for (int i = 0; i < 4; i ++) {
            dk.getDeck().add(m);
        }
    }

    @Before
    public void playerArrSetUp() {
        // create an array of length one containing a three player objects
        plArr = new Player[3];
        // add the player to the player array
        for (int i = 0; i < 3; i++) {
            plArr[i] = pl;
        }
    }

    @Before
    public void deckArrSetUp() {
        // create an array of length one containing a three deck object
        dkArr = new CardDeck[3];
        // add the deck to the deck array
        for (int i = 0; i < 3; i++) {
            dkArr[i] = dk;
        }
    }

    @After
    public void tearDown () {
        pl = null;
        dk = null;
        plArr = null;
        dkArr = null;
    }

    @Test
    public void testGetHandCard() {
        assertEquals(5, pl.getHandCard(0).getValue());
    }

    @Test
    public void testGetHand() {
    }

    @Test
    public void testGetHandSize() {
        int expected = pl.getHand().size();
        assertEquals(expected, pl.getHandSize());
    }

    @Test
    public void testGetPlayer() {
    }

    @Test
    public void testAddToHand() {
        Card n = new Card();
        n.setValue(7);
        pl.getHand().add(n);
        int expected = pl.getHand().get(4).getValue();
        int result = n.getValue();
        assertEquals(5, pl.getHandSize());
        assertEquals(expected, result);
    }

    @Test
    public void testRemFromHand() {
        pl.remFromHand(0);
        assertEquals(3, pl.getHandSize());
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
    public void testDiscard() {
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

        assertEquals(true, correctDiscard);
    }

    @Test
    public void testKeep() {
        int pNumber = 2;
        Card tCard = new Card();
        tCard.setValue(11);

        pl.keepCard(tCard, pNumber, plArr);

        Card result = pl.getHand().get(4);
        assertEquals(tCard, result);
    }

    @Test
    public void testRemove() {
        int pNumber = 3;
        Card tCard = plArr[pNumber-1].getHandCard(0);

        pl.removeCard(tCard, pNumber, plArr);

        int result = plArr[pNumber-1].getHandSize();
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
