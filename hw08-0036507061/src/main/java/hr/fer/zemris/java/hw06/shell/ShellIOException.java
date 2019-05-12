package hr.fer.zemris.java.hw06.shell;

/**
 * Throw when reading or writing fails.
 * 
 * @author david
 *
 */
public class ShellIOException extends RuntimeException {

	/**
	 * Default version Uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a {@code ShellIOException} with no detail message.
	 */
	public ShellIOException() {
		super();
	}

	/**
	 * Constructs a {@code ShellIOException} with the specified detail message.
	 *
	 * @param message the detail message.
	 */
	public ShellIOException(String message) {
		super(message);
	}

}
