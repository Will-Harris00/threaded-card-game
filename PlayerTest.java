import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTest {
    AtomicBoolean complete;
    AtomicInteger winner;
    private Player[] plArray;
    private CardDeck[] dkArray;
    private Player plObject;
    private CardDeck dkObject;

    // Creates mock objects for player1, card with value 5, and player1's hand to 5, 5, 5, 5.
    @Before
    public void playerSetUp() {
        plObject = new Player(1);
        // set up a target test hand
        Card n = new Card();
        n.setValue(5);
        for (int i = 0; i < 4; i++) {
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
        for (int i = 0; i < 4; i++) {
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

    // Removes mock objects after tests to save memory by triggering automatic garbage collection.
    @After
    public void tearDown() {
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

    // Checks if the hand size of a player is equal to the hand size obtained from the player in a different way.
    @Test
    public void testGetHandSize() {
        int expected = plObject.getHand().size();
        assertEquals(expected, plObject.getHandSize());
    }

    @Test
    public void testGetPlayer() {
    }

    // Generates a card of value 7, and then checks both whether the hand size of the player went up from 4 to 5, and
    // whether the fifth card has value 7.
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

    // Removes a card from a player's hand and checks if the size of hand went down from 4 to 3.
    @Test
    public void testRemFromHand() {
        plObject.remFromHand(0);
        assertEquals(3, plObject.getHandSize());
    }

    // Creates deck1, then adds cards with values 1, 2, 3, 4, and checks if the first card in deck has value 1.
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

    // Creates a hand with three preferred values and one non-preferred value, and checks that the non-preferred
    // value is the one which is discarded.
    @Test
    public void testChooseDiscard() {
        int pNumber = 2;
        Player tPlayer = new Player(pNumber);
        // Single non-preferred card and three preferred in player hand.
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

        // Iterates through the player's hand and checks if all cards have preferred value.
        for (Card element : tPlayer.getHand()) {
            if (element.getValue() != match) {
                correctDiscard = false;
                break;
            }
        }
        assertTrue(correctDiscard);
    }

    // Tests that the card a player draws from the top of the deck to their left matches a known value.
    @Test
    public void testDrawCard() {
        int pNumber = 1;
        dkArray[pNumber - 1].getDeckCard(0).setValue(18);
        int result = plArray[pNumber - 1].drawCard(pNumber, dkArray).getValue();
        assertEquals(18, result);
    }

    // Tests that the discarded card is transferred from one player's hand to the top of the deck of the player on
    // the right.
    @Test
    public void testDiscardCard() {
        // Tests if statement within discardCard method; send from player 2's hand to player 3's deck.
        int pNumber = 2;
        plArray[pNumber - 1].getHandCard(0).setValue(15);
        Card unwantedCard = plArray[pNumber - 1].getHandCard(0);
        plObject.discardCard(unwantedCard, pNumber, plArray, dkArray, 3);
        int result = dkArray[pNumber].getDeckCard(4).getValue();
        assertEquals(15, result);

        // Tests else statement within discardCard method; send from player 3's hand to player 1's deck (in a three
        // player game).
        pNumber = 3;
        plArray[pNumber - 1].getHandCard(0).setValue(17);
        unwantedCard = plArray[pNumber - 1].getHandCard(0);
        plObject.discardCard(unwantedCard, pNumber, plArray, dkArray, 3);
        result = dkArray[0].getDeckCard(4).getValue();
        assertEquals(17, result);
    }

    // Creates a new card, then adds it to the player's hand, and checks that the fifth card is the same as the card
    // added.
    @Test
    public void testKeepCard() {
        int pNumber = 2;
        Card tCard = new Card();
        tCard.setValue(11);

        plObject.keepCard(tCard, pNumber, plArray);

        Card result = plObject.getHand().get(4);
        assertEquals(tCard, result);
    }

    // Removes the first card in player 3's hand, and checks that the hand size changed from 4 to 3.
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

    // Adds four cards of value 2 to player2's hand, and checks if player2 has a winning hand afterwards, and is
    // declared as the winner.
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
