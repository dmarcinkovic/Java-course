package hr.fer.zemris.java.tecaj_13.dao;

/**
 * Exception throw when any error occur while retrieving data from database.
 * 
 * @author David
 *
 */
public class DAOException extends RuntimeException {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with provided message and cause.
	 * 
	 * @param message Message.
	 * @param cause   Exception cause.
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor with provided message.
	 * 
	 * @param message Message.
	 */
	public DAOException(String message) {
		super(message);
	}
}