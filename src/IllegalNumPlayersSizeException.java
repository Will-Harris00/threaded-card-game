package src;

/**
 * IllegalNumberPlayerSize exception to be thrown when an input is given which can be parsed as an
 * integer, but is not greater than 1.
 *
 * @author Isaac Cheng
 * @author William Harris
 * @version 1.0
 */
public class IllegalNumPlayersSizeException extends Exception {
    // Constructs an instance of the exception with no message.
    public IllegalNumPlayersSizeException() {
    }

    /**
     * Constructs an instance of the exception containing the message argument.
     *
     * @param message Message explaining the cause of the exception.
     */
    public IllegalNumPlayersSizeException(String message) {
        super(message);
    }
}
