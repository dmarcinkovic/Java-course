package hr.fer.zemris.java.hw06.shell.commands.parser;

/**
 * Represents type of the token.
 * 
 * @author david
 *
 */
public enum TokenType {
	/**
	 * Represents text.
	 */
	TEXT,

	/**
	 * Start of the group. This is "${" sequence.
	 */
	START_GROUP,

	/**
	 * End of the group. This is "}".
	 */
	END_GROUP,

	/**
	 * Represents number.
	 */
	NUMBER,

	/**
	 * Represents comma character.
	 */
	COMMA,

	/**
	 * Represents padding character. Padding character is digit 0.
	 */
	PADDING,

	/**
	 * Represents EOF character. EOF means end the input sequence.
	 */
	EOF
}
