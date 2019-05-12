package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception that is thrown when any exception or unexpected behavior occurs during parsing.
 * @author david
 *
 */
public class SmartScriptParserException extends RuntimeException{
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Construct a {@link SmartScriptParserException} with no detail message.
	 */
	public SmartScriptParserException() {
		super();
	}
	
	/**
	 * Construct a {@link SmartScriptParserException} with detail message.
	 * @param message Detail message.
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

}
