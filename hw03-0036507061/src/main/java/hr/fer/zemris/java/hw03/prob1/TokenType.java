package hr.fer.zemris.java.hw03.prob1;

/**
 * Represent token type.
 * 
 * @author david
 *
 */
public enum TokenType {
	/**
	 * Indicate that there is not more tokens.
	 */
	EOF,

	/**
	 * A string of one or more characters over which method Character.isLetter
	 * returns true.
	 */
	WORD,

	/**
	 * One or more digits representing number that can be parsed into long.
	 */
	NUMBER,

	/***
	 * Any single character when words, numbers and spaces are removed from text.
	 */
	SYMBOL
}
