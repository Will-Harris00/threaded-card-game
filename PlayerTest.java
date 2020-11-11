import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
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
        // create an array of length one containing a single player object
        plArr = new Player[1];
        // add the player to the player array
        plArr[0] = pl;
    }

    @Before
    public void deckArrSetUp() {
        // create an array of length one containing a single deck object
        plArr = new Player[1];
        // add the deck to the deck array
        plArr[0] = pl;
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
        int expectedVal = pl.getHand().get(4).getValue();
        assertEquals(5, pl.getHandSize());
        assertEquals(expectedVal, n.getValue());
    }

    @Test
    public void testRemFromHand() {
        pl.remFromHand(0);
        assertEquals(3, pl.getHandSize());
    }

    @Test
    public void testDrawValue() {
    }

    @Test
    public void testDraw() {
    }

    @Test
    public void testDiscard() {
    }

    @Test
    public void testKeep() {
    }

    @Test
    public void testRemove() {
    }

    @Test
    public void testViewArray() {
    }

    @Test
    public void testStrategy() {
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
