import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    private Player pl;

    @Before
    public void setUp() {
        pl = new Player(1);
        // set up a target test hand
        Card n = new Card();
        n.setValue(5);
        for (int i = 0; i < 4; i ++) {
            pl.getHand().add(n);
        }
    }

    @After
    public void tearDown () {
        pl = null;
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
    }
}
