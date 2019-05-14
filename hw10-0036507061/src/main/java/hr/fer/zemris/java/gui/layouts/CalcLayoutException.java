package hr.fer.zemris.java.gui.layouts;

/**
 * Thrown when more components are added to CalcLayout or component is added to
 * illegal position, i.e. negative row or column.
 * 
 * @author david
 *
 */
public class CalcLayoutException extends RuntimeException {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a {@code CalcLayoutException} with the specified detail message.
	 *
	 * @param message the detail message.
	 */
	public CalcLayoutException(String message) {
		super(message);
	}

	/**
	 * Constructs a {@code CalcLayoutException} with no detail message.
	 */
	public CalcLayoutException() {
		super();
	}

}
