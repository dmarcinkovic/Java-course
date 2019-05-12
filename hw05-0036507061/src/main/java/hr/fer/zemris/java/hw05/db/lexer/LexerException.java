package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Exception that is thrown when any unexpected behavior occurs during lexer
 * analysis.
 * 
 * @author david
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a {@code LexerException} with the specified detail message.
	 * 
	 * @param message
	 */
	public LexerException(String message) {
		super(message);
	}

	/**
	 * Constructs a {@code LexerException} with no detail message.
	 */
	public LexerException() {
		super();
	}
}
