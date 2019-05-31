package hr.fer.zemris.java.custom.scripting.lexer;

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
	 * Represents constants integer value.
	 */
	INTEGER,

	/**
	 * Represents constant double value.
	 */
	DOUBLE,

	/**
	 * Represents any symbol.
	 */
	SYMBOL,

	/**
	 * Indicate that there are no more tokens.
	 */
	EOF,

	/**
	 * Represents opening tag. Opening tag is '{$'.
	 */
	OPENING_TAG,

	/**
	 * Represent closing tag. Closing tag is '$}'.
	 */
	CLOSING_TAG,

	/**
	 * Represents String. This is similar to word, but it must start with '"' and
	 * end with '"'.
	 */
	STRING,

	/**
	 * Represents operator. Operators are plus('+'), minus('-'), multiplication
	 * sign('*'), divide sing('/') and power character('^').
	 */
	OPERATOR,

	/**
	 * Represent function. Function start with '@' and rest of the function is word.
	 */
	FUNCTION,

	/**
	 * Represent variable token. Variable token consists of letters, digits or
	 * underscore. First character must be letter.
	 */
	VARIABLE
}
