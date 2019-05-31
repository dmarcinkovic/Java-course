package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Arrays;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

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
	private LexerState state = LexerState.TEXT;

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
		token = null;
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
	 * @throws SmartScriptParserException if any error occurs while parsing or
	 *                                    during the lexical analysis.
	 */
	public Token nextToken() {
		if (token != null && token.getType().equals(TokenType.EOF)) {
			throw new SmartScriptParserException();
		}

		if (isEOF()) {
			token = new Token(TokenType.EOF, null);
		} else if (state.equals(LexerState.TEXT)) {
			token = nextTokenWhenText();
		} else {
			token = nextTokenWhenTag();
		}
		return token;
	}

	/**
	 * Get next token when the state of lexer is TEXT. Method check what type of
	 * character is at currentIndex. If character at current index is letter this
	 * method will return new token of type word. If character is digit this method
	 * will return token of type number, and so on.
	 * 
	 * @return next token.
	 * @throws SmartScriptParserException if any error occurs while parsing or
	 *                                    during the lexical analysis.
	 */
	private Token nextTokenWhenText() {
		char[] result = new char[data.length + 1];
		int index = 0;
		String word = null;

		for (int i = currentIndex, n = data.length; i < n; i++) {
			if (isOpeningTag(data, i) && index == 0) {
				return nextOpeningTag();
			} else if (isOpeningTag(data, i)) {
				currentIndex = i;
				word = new String(Arrays.copyOf(result, index));
				return new Token(TokenType.TEXT, word);
			} else if (isValidEscapeOutsideTag(i)) {
				result[index++] = data[i + 1];
				i++;
			} else {
				result[index++] = data[i];
			}
		}
		
		word = new String(Arrays.copyOf(result, index));
		currentIndex = data.length;

		return new Token(TokenType.TEXT, word);
	}

	/**
	 * Get next token when the state of lexer is TAG.
	 * 
	 * @return next token.
	 * @throws SmartScriptParserException if any error occurs while parsing or
	 *                                    during the lexical analysis.
	 */
	private Token nextTokenWhenTag() {

		skipBlanks();

		if (isEOF()) {
			return new Token(TokenType.EOF, null);
		}

		for (int i = currentIndex, n = data.length; i < n; i++) {
			if (Character.isLetter(data[i])) {
				return nextVariable();
			} else if (Character.isDigit(data[i])) {
				return nextNumber();
			} else if (isClosingTag(data, i)) {
				return nextClosingTag();
			} else if (isMinusSign()) {
				return nextNumber();
			} else if (isOperator(data[i])) {
				return nextOperator();
			} else if (isString(data[i])) {
				return nextString();
			} else if (isFunction()) {
				return nextFunction();
			} else if (data[i] == '\\') {
				throw new SmartScriptParserException();
			} else if (isSymbol(data[i])) {
				return nextSymbol();
			}
		}
		return null;
	}

	/**
	 * Method to return next integer token.
	 * 
	 * @return next integer token.
	 * @throws SmartScriptParserException if cannot parse to number.
	 */
	private Token nextNumber() {
		char[] result = new char[data.length + 1];
		int index = 0;
		Token token = null;

		for (int i = currentIndex, n = data.length; i < n; i++) {
			if (Character.isDigit(data[i]) || data[i] == '.' || (i == currentIndex && data[i] == '-')) {
				result[index++] = data[i];
			} else if (data[i] == '\\') {
				throw new SmartScriptParserException();
			} else {
				currentIndex = i;
				token = getNextTokenWhenNumber(result, index);
				return token;
			}
		}

		token = getNextTokenWhenNumber(result, index);
		currentIndex = data.length;

		return token;
	}

	/**
	 * Method to return new token of type number from given character array.
	 * 
	 * @param result character array representing number.
	 * @param length Length of the character array.
	 * @return next number token.
	 * @throws SmartScriptParserException if cannot parse to number.
	 */
	private Token getNextTokenWhenNumber(char[] result, int length) {
		String word = new String(Arrays.copyOf(result, length));
		try {
			Integer number = Integer.parseInt(word);

			return new Token(TokenType.INTEGER, number);
		} catch (NumberFormatException e) {
			try {
				Double number = Double.parseDouble(word);

				return new Token(TokenType.DOUBLE, number);
			} catch (NumberFormatException e1) {
				throw new SmartScriptParserException();
			}
		}
	}

	/**
	 * Return new Token of type opening tag.
	 * 
	 * @return new Token of type opening tag.
	 */
	private Token nextOpeningTag() {
		String openingTag = "{$";

		currentIndex += 2;

		return new Token(TokenType.OPENING_TAG, openingTag);
	}

	/**
	 * Returns new Token of type closing tag.
	 * 
	 * @return new Token of type closing tag.
	 */
	private Token nextClosingTag() {
		String closingTag = "$}";

		currentIndex += 2;

		return new Token(TokenType.CLOSING_TAG, closingTag);
	}

	/**
	 * Returns new Token of type variable. Variable consists of letter, digits and
	 * underscores. Letter must be first character of variable.
	 * 
	 * @return new token of type variable.
	 * @throws SmartScriptParserException if escape character appears.
	 */
	private Token nextVariable() {
		char[] result = new char[data.length + 1];
		int index = 0;
		String variable = null;

		for (int i = currentIndex, n = data.length; i < n; i++) {
			if (Character.isLetterOrDigit(data[i]) || data[i] == '_') {
				result[index++] = data[i];
			} else if (data[i] == '\\') {
				throw new SmartScriptParserException();
			} else {
				variable = new String(Arrays.copyOf(result, index));

				currentIndex = i;

				return new Token(TokenType.VARIABLE, variable);
			}
		}
		variable = new String(Arrays.copyOf(result, index));
		currentIndex = data.length;

		return new Token(TokenType.VARIABLE, variable);
	}

	/**
	 * Return new token of type operator. Operators are : '+', '-', '*', '/' and
	 * '^'.
	 * 
	 * @return new token of type operator.
	 */
	private Token nextOperator() {
		currentIndex++;

		return new Token(TokenType.OPERATOR, data[currentIndex - 1]);
	}

	/**
	 * Method to return new token of type function. Token of type function start
	 * with '@' after which follow one or more letters, and then digits or
	 * underscores.
	 * 
	 * @return new token of type function.
	 * @throws SmartScriptParserException if escape character appears.
	 */
	private Token nextFunction() {
		char[] result = new char[data.length + 1];
		int index = 0;
		String function = null;

		result[index++] = '@';

		for (int i = currentIndex + 1, n = data.length; i < n; i++) {
			if (Character.isLetterOrDigit(data[i]) || data[i] == '_') {
				result[index++] = data[i];
			} else if (data[i] == '\\') {
				throw new SmartScriptParserException();
			} else {
				currentIndex = i;
				function = new String(new String(Arrays.copyOf(result, index)));

				return new Token(TokenType.FUNCTION, function);
			}
		}
		function = new String(new String(Arrays.copyOf(result, index)));
		currentIndex = data.length;

		return new Token(TokenType.FUNCTION, function);
	}

	/**
	 * + Returns new Token of type symbol.
	 * 
	 * @return new Token of type symbol.
	 */
	private Token nextSymbol() {
		currentIndex++;

		return new Token(TokenType.SYMBOL, data[currentIndex - 1]);
	}

	/**
	 * Return new token of type string. String is token that starts and ends with
	 * '"'.
	 * 
	 * @return new token of type string.
	 * @throws SmartScriptParserException if string is not closed, or wrong escape
	 *                                    is found.
	 */
	private Token nextString() {
		char[] result = new char[data.length + 1];
		int index = 0;

		for (int i = currentIndex + 1, n = data.length; i < n; i++) {
			if (data[i] == '"') {
				currentIndex = i + 1;
				String string = new String(Arrays.copyOf(result, index));
				return new Token(TokenType.STRING, string);
			} else if (isValidEscapeInsideTag(i)) {
				getEscaped(result, index, i);
				index++;
				i++;
			} else {
				result[index++] = data[i];
			}
		}
		throw new SmartScriptParserException();
	}

	/**
	 * Method to get escaped character when inside the string.
	 * 
	 * @param result character array in which characters are stored.
	 * @param index  index in result character array.
	 * @param i      index of data character array.
	 */
	private void getEscaped(char[] result, int index, int i) {
		if (data[i + 1] == 'n') {
			result[index] = '\n';
		} else if (data[i + 1] == 'r') {
			result[index] = '\r';
		} else if (data[i + 1] == 't') {
			result[index] = '\t';
		} else {
			result[index] = data[i + 1];
		}
	}

	/**
	 * Method used to skip blanks during the lexer analysis.
	 */
	private void skipBlanks() {
		while (currentIndex < data.length && Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
		}
	}

	/**
	 * Method to check if character at given index is opening tag. Opening tag is
	 * '{$'.
	 * 
	 * @param data  Data to check if is opening tag.
	 * @param index Given index.
	 * @return true if character at given index is opening tag.
	 */
	private boolean isOpeningTag(char[] data, int index) {
		return data[index] == '{' && index + 1 < data.length && data[index + 1] == '$';
	}

	/**
	 * Method to check if character at given index is closing tag. Closing tag is
	 * '$}'.
	 * 
	 * @param data  Data to check if is closing tag.
	 * @param index Given index.
	 * @return true if character at given index is closing tag.
	 */
	private boolean isClosingTag(char[] data, int index) {
		return data[index] == '$' && index + 1 < data.length && data[index + 1] == '}';
	}

	/**
	 * Method to check if given character c is operator. Operator is '+', '-', '*',
	 * '/' and '^'.
	 * 
	 * @param c Character to check if is operator.
	 * @return true if given character is operator, otherwise return false.
	 */
	private boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
	}

	/**
	 * Method to check if given character c is symbol or not.
	 * 
	 * @param c Character to check if is symbol.
	 * @return true if given character is symbol, otherwise returns false.
	 */
	private boolean isSymbol(char c) {
		return !Character.isWhitespace(c);
	}

	/**
	 * Method to check if given character c is start of the string.
	 * 
	 * @param c Character to check if is symbol or not.
	 * @return true if given character is symbol, otherwise returns false.
	 */
	private boolean isString(char c) {
		return c == '"';
	}

	/**
	 * Method to check if given character is start of the function. Function starts
	 * with @. After that character must stand one of more letter, otherwise it is
	 * symbol.
	 * 
	 * @return true if current character is start or the function.
	 */
	private boolean isFunction() {
		if (data[currentIndex] == '@' && currentIndex + 1 < data.length) {
			if (Character.isLetter(data[currentIndex + 1])) {
				return true;
			} else if (Character.isWhitespace(data[currentIndex + 1])) {
				return false;
			} else {
				throw new SmartScriptParserException();
			}
		}
		return false;
	}

	/**
	 * Method to check if current token is eof.
	 * 
	 * @return true if current token is eof.
	 */
	private boolean isEOF() {
		return currentIndex >= data.length;
	}

	/**
	 * Method to check if '-' is an operator or minus sign. It is the minus sign if
	 * immediately after that character comes digit. Otherwise it is operator.
	 * 
	 * @return true if character is minus sign.
	 */
	private boolean isMinusSign() {
		return data[currentIndex] == '-' && currentIndex + 1 < data.length && Character.isDigit(data[currentIndex + 1]);
	}

	/**
	 * Method to check if escaping sequence outside the tag is valid. Valid sequence
	 * is one that starts with '\' after which follows '\' (this is treated as one
	 * '\') or '"' or '\n' or '\t' or '\r'.
	 * 
	 * @param index Index of escape character in original character array.
	 * @return True if escaping sequence if valid.
	 * @throws SmartScriptParserException if after '\' appears something that is not
	 *                                    '\' or '{'.
	 */
	private boolean isValidEscapeOutsideTag(int index) {
		if (data[index] == '\\') {
			if (index + 1 < data.length && (data[index + 1] == '\\' || data[index + 1] == '{')) {
				return true;
			} else {
				throw new SmartScriptParserException();
			}
		}
		return false;
	}

	/**
	 * Method to check if escaping sequence inside the tag is valid. It must be
	 * inside the string, otherwise it is not valid sequence. Valid sequence is one
	 * that starts with '\' after which follows '\' (this is treated as one '\') or
	 * '{' (this is treated as '{', not as start of the opening tag).
	 * 
	 * @param index Index of escape character in original character array.
	 * @return True if escaping sequence if valid.
	 * @throws SmartScriptParserException if after '\' appears something that is not
	 *                                    '\' or '"' or '\n' or '\t' or '\r'.
	 */
	private boolean isValidEscapeInsideTag(int index) {
		if (data[index] == '\\' && index + 1 < data.length) {
			if (data[index + 1] == '\\' || data[index + 1] == '"' || data[index + 1] == 'n' || data[index + 1] == 't'
					|| data[index + 1] == 'r') {
				return true;
			} else {
				throw new SmartScriptParserException();
			}
		} else if (data[index] == '\\') {
			throw new SmartScriptParserException();
		}
		return false;
	}

	/**
	 * Method to set new lexer state.
	 * 
	 * @param state New state of the lexer.
	 */
	public void setLexerState(LexerState state) {
		this.state = state;
	}
}
