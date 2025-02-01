package robert;

/**
 * Represents exceptions specific to the Robert chatbot.
 */
public class RobertException extends Exception {
    /**
     * Constructs a new RobertException with the specified message.
     *
     * @param message The detail message.
     */
    public RobertException(String message) {
        super(message);
    }
}
