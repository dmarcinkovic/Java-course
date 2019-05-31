package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Represents the state of lexer.
 * 
 * @author david
 *
 */
public enum LexerState {
	/**
	 *  State when lexer is outside the tag. 
	 */
	TEXT, 
	
	/**
	 * State when lexer is inside the tag. 
	 */
	TAG
}
