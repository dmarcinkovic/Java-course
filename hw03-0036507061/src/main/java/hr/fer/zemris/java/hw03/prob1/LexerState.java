package hr.fer.zemris.java.hw03.prob1;

/**
 * Represents the state of lexer.
 * 
 * @author david
 *
 */
public enum LexerState {
	/**
	 * Basic state, before receiving first '#' and after receiving second '#'.
	 */
	BASIC,

	/**
	 * Extended state, in between two '#'.
	 */
	EXTENDED
}
