package hr.fer.zemris.java.hw06.shell.commands.parser;

/**
 * A program that performs lexical analysis. Lexical analysis, lexing or
 * tokenization is the process of converting a sequence of characters into a
 * sequence of tokens.
 * 
 * @author david
 *
 */
public class Lexer {
	/**
	 * Character array that represents sequence of characters to be converted to
	 * sequence of tokens.
	 */
	private char[] data;

	/**
	 * Current position in data array.
	 */
	private int currentIndex;

	/**
	 * Last converted token.
	 */
	private Token token;

	/**
	 * Lexer state.
	 */
	private LexerState state = LexerState.WORD;

	/**
	 * Constructor to initialize data array with character.
	 * 
	 * @param text String to convert to tokens.
	 * @throws NullPointerException if text is null.
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new NullPointerException();
		}

		data = text.toCharArray();
		currentIndex = 0;
	}

	/**
	 * Method to return current token. If nextToken method is not called, returned
	 * token will be null.
	 * 
	 * @return current token.
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Method to get next token.
	 * 
	 * @return Next token.
	 */
	public Token nextToken() {
		skipBlanks();

		if (currentIndex >= data.length) {
			return token = new Token(TokenType.EOF, null);
		}

		if (state.equals(LexerState.GROUP)) {
			return token = nextGroup();
		} else {
			return token = nextWord();
		}
	}

	/**
	 * Method to set new lexer state.
	 * 
	 * @param state New state of the lexer.
	 */
	public void setLexerState(LexerState state) {
		this.state = state;
	}

	/**
	 * Returns current state. 
	 * @return current state.
	 */
	public LexerState getLexerState() {
		return state;
	}
	
	/**
	 * Method to skip all blanks.
	 */
	private void skipBlanks() {
		while (currentIndex < data.length && Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
		}
	}

	/**
	 * Return next token when lexer is in LexerState.GROUP state.
	 * 
	 * @return Next token when lexer is in LexerState.GROUP.
	 */
	private Token nextGroup() {
		StringBuilder sb = new StringBuilder();

		for (int i = currentIndex; i < data.length; i++) {
			if (Character.isWhitespace(data[i])) {
				int number = Integer.parseInt(sb.toString());
				currentIndex = i + 1;
				return new Token(TokenType.NUMBER, number);
			} else if (isEndOfGroup(i)) {
				if (sb.toString().isEmpty()) {
					currentIndex = i+1;
					return new Token(TokenType.END_GROUP, "}");
				}
				currentIndex = i;
				int number = Integer.parseInt(sb.toString());
				return new Token(TokenType.NUMBER, number);
			} else if (isPaddingCharacter(i)) {
				currentIndex = i + 1;
				return new Token(TokenType.PADDING, data[i]);
			} else if (Character.isDigit(data[i])) {
				sb.append(data[i]);
			} else if (data[i] == ',') {
				if (sb.toString().isEmpty()) {
					currentIndex = i+1;
					return new Token(TokenType.COMMA, data[i]);
				}
				currentIndex = i;
				int number = Integer.parseInt(sb.toString());
				return new Token(TokenType.NUMBER, number);
			} else {
				throw new IllegalArgumentException();
			}
		}

		throw new IllegalArgumentException();
	}

	/**
	 * Checks if current character is padding character. Padding character is
	 * represented with digit 0.
	 * 
	 * @param index Current index.
	 * @return True if current character is padding, otherwise returns false.
	 */
	private boolean isPaddingCharacter(int index) {
		return data[index] == '0' && index + 1 < data.length && Character.isDigit(data[index + 1]);
	}

	/**
	 * Check if current character is start of the group. Start of the group is this
	 * sequence of character: "${".
	 * 
	 * @param i Current index.
	 * @return True if current character is start of the group, otherwise returns
	 *         false.
	 */
	private boolean isStartOfGroup(int i) {
		return data[i] == '$' && i + 1 < data.length && data[i + 1] == '{';
	}

	/**
	 * Checks if current character is end of the group. Start of the group is '}'
	 * character.
	 * 
	 * @param i Current index.
	 * @return True if current character is end of the group, otherwise returns
	 *         false.
	 */
	private boolean isEndOfGroup(int i) {
		return data[i] == '}';
	}

	/**
	 * Returns next token when lexer is in LexerState.WORD state.
	 * 
	 * @return Next token when lexer is in LexerState.WORD state.
	 */
	private Token nextWord() {
		StringBuilder sb = new StringBuilder();

		for (int i = currentIndex; i < data.length; i++) {
			if (isStartOfGroup(i)) {
				if (sb.toString().isEmpty()) {
					currentIndex = i+2;
					return new Token(TokenType.START_GROUP, "${");
				}
				currentIndex = i;
				return new Token(TokenType.TEXT, sb.toString());
			} else {
				sb.append(data[i]);
			}
		}

		currentIndex = data.length;
		return new Token(TokenType.TEXT, sb.toString());
	}
}
