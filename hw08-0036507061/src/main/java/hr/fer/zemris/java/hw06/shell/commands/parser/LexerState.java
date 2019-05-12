package hr.fer.zemris.java.hw06.shell.commands.parser;

/**
 * Represents the state of lexer.
 * 
 * @author david
 *
 */
public enum LexerState {
	/**
	 * Everything outside group.
	 */
	WORD,

	/**
	 * Everything inside the group.
	 */
	GROUP
}
