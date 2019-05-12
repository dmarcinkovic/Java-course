package hr.fer.zemris.java.hw05.db;

/**
 * Exception that is thrown when any unexpected behavior occurs during parsing
 * 
 * @author david
 *
 */
public class ParserException extends RuntimeException {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a {@code ParserException} with the specified detail message.
	 * 
	 * @param message
	 */
	public ParserException(String message) {
		super(message);
	}

	/**
	 * Constructs a {@code ParserException} with no detail message.
	 */
	public ParserException() {
		super();
	}

}
