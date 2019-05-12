package hr.fer.zemris.java.hw03.prob1;

import java.util.Arrays;

/**
 * Simple lexical analyzer.
 * 
 * @author david
 *
 */
public class Lexer {
	/**
	 * Input text.
	 */
	private char[] data;

	/**
	 * Current token.
	 */
	private Token token;

	/**
	 * The index of the first unfinished character.
	 */
	private int currentIndex;

	/**
	 * Current lexer state. Initially state is set to BASIC.
	 */
	private LexerState state;

	/**
	 * The constructor receives input text to be tokenized and converts it to char
	 * array.
	 * 
	 * @param text Text to be tokenized.
	 * @throws NullPointerException if text is null.
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new NullPointerException();
		}
		state = LexerState.BASIC;
		data = text.toCharArray();
		currentIndex = 0;
	}

	/**
	 * Generates and returns next token. Deterministic final machine was used in
	 * this method. Deterministic final machine go through states. First state is
	 * called 'start' state. Then if we see character that is letter we go to state
	 * called 'word'. If we see digit we go to the state number, and so on. If we
	 * are in the word state and we see something that is not letter we go out to
	 * the 'notWord' state in which we convert result character array to String and
	 * return given token. On the other side, if we are in the 'number state' and we
	 * find something that is not digit we go to the 'notNumber' state in which we
	 * convert given character array to number and return that token. 
	 * 
	 * @return next token.
	 * @throw LexerException if method is called after EOF is received or text
	 *        consists of numbers that cannot be parsed to long.
	 */
	public Token nextToken() {

		/*
		 * States of deterministic final machine that generates new tokens.
		 */
		String start = "start";
		String word = "word";
		String number = "number";
		String notWord = "notWord";
		String notNumber = "notNumber";
		String escape = "escape";

		/*
		 * If nextToken is called after EOF is received throw LexerException.
		 */
		if (token != null && token.getType().equals(TokenType.EOF)) {
			throw new LexerException();
		}

		/*
		 * Check if token is EOF.
		 */
		if (isEOF()) {
			return token = new Token(TokenType.EOF, null);
		}

		String state = start;

		char[] buffer = new char[data.length + 1];
		int index = 0;

		for (int i = currentIndex, n = data.length; i < n; i++) {
			/*
			 * If state is start, find the next state. If state is word, if you receive
			 * non-alphabetic character move to notWord state, otherwise stay in the current
			 * state. If state is number, is you receive non-digit character move to
			 * notNumber state, otherwise stay in the current state. If you received escape
			 * character go to escape state. If you received any symbol return new Token of
			 * type SYMBOL.
			 * 
			 */

			if (this.state.equals(LexerState.EXTENDED)) {
				return token = getNextTokenWhenExtended();
			}

			if (state.equals(start)) {
				if (isWord(data[i])) {
					state = word;
					buffer[index++] = data[i];
				} else if (isNumber(data[i])) {
					state = number;
					buffer[index++] = data[i];
				} else if (isEscape(data[i])) {
					state = escape;
				} else if (isSymbol(data[i])) {
					currentIndex = i + 1;
					return token = new Token(TokenType.SYMBOL, data[i]);
				} else if (Character.isISOControl(data[i]) || Character.isWhitespace(data[i])) {
					currentIndex++;
					return token = nextToken();
				}
			} else if (state.equals(escape)) {
				if (isWord(data[i])) {
					throw new LexerException();
				}
				state = word;
				buffer[index++] = data[i];
			} else if (state.equals(word)) {
				if (isEscape(data[i])) {
					state = escape;
				} else if (!isWord(data[i])) {
					state = notWord;
				} else {
					buffer[index++] = data[i];
				}
			} else if (state.equals(number)) {
				if (!isNumber(data[i])) {
					state = notNumber;
				} else {
					buffer[index++] = data[i];
				}
			}

			/*
			 * If non-alphabetic character is received after word state, convert buffer to
			 * word. If non-digit character is received after number state, convert buffer
			 * to number.
			 */
			if (state.equals(notWord)) {
				currentIndex = i;
				buffer[index] = '\0';

				return token = new Token(TokenType.WORD, getWord(Arrays.copyOf(buffer, index)));
			} else if (state.equals(notNumber)) {
				currentIndex = i;
				buffer[index] = '\0';

				return token = new Token(TokenType.NUMBER, getNumber(buffer));
			}
		}

		/*
		 * If machine is in start state after considering all characters return EOF. If
		 * machine is in word state after considering all characters return Token of
		 * type Word. If machine is in number state after considering all character
		 * return Token of type word.
		 */
		if (state.equals(start)) {
			return token = new Token(TokenType.EOF, null);
		} else if (state.equals(word)) {
			buffer[index] = '\0';
			currentIndex = data.length;
			return token = new Token(TokenType.WORD, getWord(Arrays.copyOf(buffer, index)));
		} else if (state.equals(number)) {
			buffer[index] = '\0';
			currentIndex = data.length;
			return token = new Token(TokenType.NUMBER, getNumber(buffer));
		} else if (state.equals(escape)) {
			throw new LexerException();
		}

		return token = null;
	}

	/**
	 * Method to generate new token when LexerState is extended. When state is
	 * extended all symbols (except '#'), words and numbers are considered as words.
	 * 
	 * @return Generated token.
	 */
	private Token getNextTokenWhenExtended() {
		/*
		 * States of deterministic final machine when lexer state is extended.
		 */
		String start = "start";
		String notSpace = "notSpace";
		String notAlpha = "notAlpha";

		String state = start;
		char[] buffer = new char[data.length + 1];
		int index = 0;

		for (int i = currentIndex; i < data.length; i++) {
			if (state.equals(start)) {
				if (isExtendedCharacter(data[i])) {
					currentIndex = i + 1;
					return new Token(TokenType.SYMBOL, data[i]);
				} else if (isWord(data[i]) || isNumber(data[i]) || isSymbol(data[i]) || isEscape(data[i])) {
					state = notSpace;
					buffer[index++] = data[i];
				}
			} else if (state.equals(notSpace)) {
				if (isExtendedCharacter(data[i])) {
					state = notAlpha;
				} else if (!isWord(data[i]) && !isNumber(data[i]) && !isSymbol(data[i]) && !isEscape(data[i])) {
					state = notAlpha;
				} else {
					buffer[index++] = data[i];
				}
			}

			if (state.equals(notAlpha)) {
				buffer[index] = '\0';
				currentIndex = i;
				state = start;
				return new Token(TokenType.WORD, getWord(Arrays.copyOf(buffer, index)));
			}
		}

		if (state.equals(start)) {
			currentIndex = data.length;
			return new Token(TokenType.EOF, null);
		}
		return null;
	}

	/**
	 * Method to check if current character is EOF.
	 * 
	 * @return true if current character is EOF.
	 */
	private boolean isEOF() {
		return currentIndex > data.length - 1;
	}

	/**
	 * Method to check if given character is number of not.
	 * 
	 * @param ch given character.
	 * @return true if given character is number, otherwise it returns false.
	 */
	private boolean isNumber(char ch) {
		return Character.isDigit(ch);
	}

	/**
	 * Method to check if given character is symbol or not.
	 * 
	 * @param ch Given character.
	 * @return True is given character is symbol, otherwise it returns false.
	 */
	private boolean isSymbol(char ch) {
		return !Character.isWhitespace(ch) && !Character.isLetterOrDigit(ch) && !Character.isISOControl(ch);
	}

	/**
	 * Method to check if given character is alphabetic or not.
	 * 
	 * @param ch Given character.
	 * @return True is given character is is word, otherwise it returns false.
	 */
	private boolean isWord(char ch) {
		return Character.isLetter(ch);
	}

	/**
	 * Method to check if given character is escape sign or not.
	 * 
	 * @param ch Given character.
	 * @return True if given character is word, otherwise it returns false.
	 */
	private boolean isEscape(char ch) {
		return ch == '\\';
	}

	/**
	 * Method to check if given character is extended character or not. Extended
	 * character is '#'.
	 * 
	 * @param ch Given character.
	 * @return True if given character is extended, otherwise returns false.
	 */
	private boolean isExtendedCharacter(char ch) {
		return ch == '#';
	}

	/**
	 * Method to convert number given as character array to long number.
	 * 
	 * @param buffer Character array to be converted.
	 * @return Converted number.
	 */
	private Long getNumber(char[] buffer) {
		String str = new String(buffer);
		str = str.trim();
		try {
			long number = Long.parseLong(str);
			return number;
		} catch (NumberFormatException e) {
			throw new LexerException();
		}
	}

	/**
	 * Method to convert word given as character array to string.
	 * 
	 * @param buffer Character array to be converted.
	 * @return Converted number.
	 */
	private String getWord(char[] buffer) {
		return new String(buffer);
	}

	/**
	 * Returns last generated token. It can be called multiple times and it will
	 * produce the same output, until new token has been generated.
	 * 
	 * @return Last generated token.
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Method to set state.
	 * 
	 * @param state New state.
	 * @throws NullPointerException if state is null.
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new NullPointerException();
		}

		this.state = state;
	}
}
