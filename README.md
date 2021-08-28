# threaded-card-game

Simulation of a card game, where the objective is to obtain four cards with the
same rank.

## Installation

### Java Version

This game has been designed for and developed on Java 11.

### Running the Application

1. Find the file `CardGame.java` in the [src](src) folder.
2. Compile and run the file.

## Usage

At the start of the game, the user will be prompted for the number of players in
the game. This input should be provided as a positive integer which is greater
than 1. Examples of valid inputs would be:

- `3`
- `10`
- `20`

(NB: You should have a valid card pack for the number of players you choose.
Card packs must have the number of players multiplied by 8, because each player
will have a deck of four cards and a hand of four cards in the game. For
example, a pack for 4 players must include 4 * 8 = 32 cards.)

After the number of players has been inputted, you must enter a file name
containing a pack of cards. This should be a .txt file, with a positive integer
on each line, and each integer separated by a new line. These .txt input files
should be moved to the `resources` folder - we have provided some examples to
get you started. For example, you may input:

- `two.txt`
- `five.txt`
- `10.txt`

## Testing

### JUnit

All tests in the test suites provided use JUnit 4.12.

### Test Suite

By running the test suite, TestSuite.java, all tests for CardGame.java and
Player.java.can be run at once.