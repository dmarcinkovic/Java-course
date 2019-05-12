package hr.fer.zemris.java.hw03.prob1;

/**
 * Thrown when any king of unexpected error occurs during tokenization. It
 * includes : If text consists of numbers that cannot be represented as long.
 * When method for generating next token is call after EOF is received.
 * 
 * @author david
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct {@link LexerException} with no detail message.
	 */
	public LexerException() {
		super();
	}

	/**
	 * Construct a {@link LexerException} with detail message.
	 * 
	 * @param message the detail message.
	 */
	public LexerException(String message) {
		super(message);
	}
}
