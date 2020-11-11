import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    private Player env;

    @Before
    public void setUp() {
        env = new Player(1);
        // set up a target test hand
        Card n = new Card();
        n.setValue(5);
        for (int i = 0; i < 3; i ++) {
            env.getHand().add(n);
        }
    }

    @After
    public void tearDown () {
        env = null;
    }

    @Test
    public void testGetHandCard() {
        int expected = env.getHand().get(0).getValue();
        // run the Checker
        assertEquals(expected, env.getHandCard(0).getValue());
    }

    @Test
    public void getHand() {
    }

    @Test
    public void getHandSize() {
    }

    @Test
    public void getPlayer() {
    }

    @Test
    public void addToHand() {
    }

    @Test
    public void remFromHand() {
    }

    @Test
    public void drawValue() {
    }

    @Test
    public void draw() {
    }

    @Test
    public void discard() {
    }

    @Test
    public void keep() {
    }

    @Test
    public void remove() {
    }

    @Test
    public void viewArray() {
    }

    @Test
    public void strategy() {
    }

    @Test
    public void writeToFile() {
    }

    @Test
    public void createFile() {
    }

    @Test
    public void run() {
    }

    @Test
    public void isWinner() {
    }
}
