package hr.fer.zemris.java.custom.collections;

/**
 * Throw when trying to access element of empty stack.
 * 
 * @author david
 *
 */
public class EmptyStackException extends RuntimeException {
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a {@code EmptyStackException} with the specified detail message.
	 * 
	 * @param message Message to be printed when exception is thrown
	 */
	public EmptyStackException(String message) {
		super(message);
	}

	/**
	 * Constructs a {@code EmptyStackException} with no detail message.
	 */
	public EmptyStackException() {
		super();
	}

}
