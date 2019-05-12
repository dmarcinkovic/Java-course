package hr.fer.zemris.java.hw05.db.lexer;

import java.util.Arrays;

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
	 * Stores text to be tokenized as character array.
	 */
	private char[] data;

	/**
	 * Current token.
	 */
	private Token token;

	/**
	 * Current index.
	 */
	private int currentIndex;

	/**
	 * Constructor to initialize text to be tokenized.
	 * 
	 * @param text Text to be tokenized.
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new NullPointerException();
		}

		data = text.toCharArray();
		currentIndex = 0;
	}

	/**
	 * Returns current token.
	 * 
	 * @return Current token.
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Method that returns next token.
	 * 
	 * @return next token.
	 * @throws LexerException if any error occurs during lexical analysis.
	 */
	public Token nextToken() {
		if (token != null && token.getType().equals(TokenType.EOF)) {
			throw new LexerException();
		}

		skipBlanks();

		if (isEOF()) {
			return nextEof();
		}

		if (isStringLiteral()) {
			return nextStringLiteral();
		} else if (isOperator()) {
			return nextOperator();
		} else if (isLogicalOperator()) {
			return nextLogicalOperator();
		} else if (isName()) {
			return nextName();
		} else {
			throw new LexerException();
		}
	}

	/**
	 * Checks if current token is of type eof. Eof is token that signals that end of
	 * query has been reached.
	 * 
	 * @return true if current token is eof.
	 */
	private boolean isEOF() {
		return currentIndex >= data.length;
	}

	/**
	 * Returns token of the type eof.
	 * 
	 * @return token of the type eof.
	 */
	private Token nextEof() {
		return token = new Token(TokenType.EOF, null);
	}

	/**
	 * Method to skip blanks.
	 */
	private void skipBlanks() {
		while (currentIndex < data.length && Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
		}
	}

	/**
	 * Method that checks if current token is of type logical operator. There is
	 * only one logical operator that is supported. That operator is 'and'.
	 * 
	 * @return true if current token is of type logical operator.
	 */
	private boolean isLogicalOperator() {
		return Character.toLowerCase(data[currentIndex]) == 'a' && currentIndex + 2 < data.length
				&& Character.toLowerCase(data[currentIndex + 1]) == 'n'
				&& Character.toLowerCase(data[currentIndex + 2]) == 'd';
	}

	/**
	 * Method that checks if current token is of type name. Name is token which
	 * consists of letters, digits or underscores.
	 * 
	 * @return true if current token is of type name.
	 */
	private boolean isName() {
		return Character.isLetter(data[currentIndex]);
	}

	/**
	 * Returns next token of type name. Name is token which consists of letter,
	 * digits or underscores.
	 * 
	 * @return token of type name.
	 * @throws LexerException if name consists of characters that are not letters,
	 *                        nor digits nor underscores.
	 */
	private Token nextName() {
		char[] result = new char[data.length + 1];
		int index = 0;

		for (int i = currentIndex, n = data.length; i < n; i++) {
			if (Character.isLetterOrDigit(data[i]) || data[i] == '_') {
				result[index++] = data[i];
			} else {
				String name = new String(Arrays.copyOf(result, index));
				currentIndex = i;
				return token = new Token(TokenType.NAME, name);
			}
		}
		String name = new String(Arrays.copyOf(result, index));
		currentIndex = data.length;
		return token = new Token(TokenType.NAME, name);
	}

	/**
	 * Returns next logical operator.
	 * 
	 * @return token of type logical operator.
	 */
	private Token nextLogicalOperator() {
		currentIndex += 3;
		return token = new Token(TokenType.LOGICAL_OPERATOR, "AND");
	}

	/**
	 * Method that checks if current token is of type String literal.
	 * 
	 * @return true if current Token is of type string literal.
	 */
	private boolean isStringLiteral() {
		return data[currentIndex] == '\"';
	}

	/**
	 * Method that returns new token of type String Literal. String literal starts
	 * and ends with '"'.
	 * 
	 * @return new Token of type String literal.
	 * @throws LexerException if string literal is not closed. On the others word if
	 *                        '"' is not seen this exception is thrown.
	 */
	private Token nextStringLiteral() {
		char[] result = new char[data.length + 1];
		int index = 0;
		
		for (int i = currentIndex + 1; i < data.length; i++) {
			if (data[i] == '"') {
				String string = new String(Arrays.copyOf(result, index));
				currentIndex = i + 1;
				return token = new Token(TokenType.STRING_LITERAL, string);
			} else {
				result[index++] = data[i];
			}
		}
		throw new LexerException();
	}

	/**
	 * Method that returns new token of type operator. Operator are : greater or
	 * equal operator (>=), greater operator (>), less or equal operator (<=), less
	 * operator (<), equal operator (=), not equal operator (!=).
	 * 
	 * @return new Token of type operator.
	 */
	private Token nextOperator() {
		if (isGreaterOrEqualOperator()) {
			currentIndex += 2;
			return token = new Token(TokenType.OPERATOR, ">=");
		} else if (isGreaterOperator()) {
			currentIndex++;
			return token = new Token(TokenType.OPERATOR, ">");
		} else if (isLessOrEqualOperator()) {
			currentIndex += 2;
			return token = new Token(TokenType.OPERATOR, "<=");
		} else if (isLessOperator()) {
			currentIndex++;
			return token = new Token(TokenType.OPERATOR, "<");
		} else if (isEqualOperator()) {
			currentIndex++;
			return token = new Token(TokenType.OPERATOR, "=");
		} else if (isNotEqualOperator()) {
			currentIndex += 2;
			return token = new Token(TokenType.OPERATOR, "!=");
		}
		return null;
	}

	/**
	 * Method that checks if current token is operator.
	 * 
	 * @return true if current token is operator.
	 */
	private boolean isOperator() {
		return isGreaterOrEqualOperator() || isGreaterOperator() || isLessOrEqualOperator() || isLessOperator()
				|| isEqualOperator() || isNotEqualOperator();
	}

	/**
	 * Method that checks if current token is greater of equal operator (>=).
	 * 
	 * @return true if current token is greater of equal operator.
	 */
	private boolean isGreaterOrEqualOperator() {
		return data[currentIndex] == '>' && currentIndex + 1 < data.length && data[currentIndex + 1] == '=';
	}

	/**
	 * Method that checks if current token is greater operator (>).
	 * 
	 * @return true if current token is greater operator.
	 */
	private boolean isGreaterOperator() {
		return data[currentIndex] == '>';
	}

	/**
	 * Method that checks if current token is less or equal operator (<=).
	 * 
	 * @return true if current token is less or equal operator.
	 */
	private boolean isLessOrEqualOperator() {
		return data[currentIndex] == '<' && currentIndex + 1 < data.length && data[currentIndex + 1] == '=';
	}

	/**
	 * Method that checks if current token is less operator (<).
	 * 
	 * @return true if current token is less operator.
	 */
	private boolean isLessOperator() {
		return data[currentIndex] == '<';
	}

	/**
	 * Method that checks if current token is equal operator (=).
	 * 
	 * @return true if current token is equal operator.
	 */
	private boolean isEqualOperator() {
		return data[currentIndex] == '=';
	}

	/**
	 * Method that checks if current token is not equal operator (!=).
	 * 
	 * @return true if current token is not equal operator.
	 */
	private boolean isNotEqualOperator() {
		return data[currentIndex] == '!' && currentIndex + 1 < data.length && data[currentIndex + 1] == '=';
	}
}
