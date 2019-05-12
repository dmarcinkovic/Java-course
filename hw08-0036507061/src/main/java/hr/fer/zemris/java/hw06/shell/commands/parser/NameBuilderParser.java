package hr.fer.zemris.java.hw06.shell.commands.parser;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.commands.FilterResult;

/**
 * Parser for the expression given as last argument for massrename sub commands
 * show and executes.
 * 
 * @author david
 *
 */
public class NameBuilderParser {
	/**
	 * Object that is implementation of NameBuilder. This object collects all
	 * NameBuilder and calls their execute command.
	 */
	private Merge merge = new Merge();

	/**
	 * Constructor to initialize expression that will be parsed.
	 * 
	 * @param izraz Expression to be parsed.
	 */
	public NameBuilderParser(String izraz) {
		parse(izraz);
	}

	/**
	 * Returns composite NameBuilder.
	 * 
	 * @return NameBuilder.
	 */
	public NameBuilder getNameBuilder() {
		return merge;
	}

	/**
	 * Method to parse given expression.
	 * 
	 * @param expression Given expression.
	 */
	private void parse(String expression) {
		try {
			Lexer lexer = new Lexer(expression);
			Token token = lexer.nextToken();

			while (!token.getType().equals(TokenType.EOF)) {
				if (lexer.getLexerState().equals(LexerState.GROUP)) {
					parseWhenStateGroup(lexer);
				} else {
					parseWhenStateWord(lexer);
				}
				token = lexer.nextToken();
			}

		} catch (IllegalArgumentException e) {
			merge = null;
			return;
		}
	}

	/**
	 * Returns index in group that is stored as instance of token.
	 * 
	 * @param token Instance of token.
	 * @return Index of group.
	 */
	private int getIndex(Token token) {
		if (!token.getType().equals(TokenType.NUMBER)) {
			throw new IllegalArgumentException();
		}

		int index = (Integer) token.getValue();
		return index;
	}

	/**
	 * Method checks if current group is in {expression} form or {expression,
	 * details} form. If it is in {expression} form this method adds new NameBuilder
	 * to composite NameBuilder.
	 * 
	 * @param token Token.
	 * @param lexer Lexer.
	 * @param index Index of group.
	 */
	private void nextGroupOrComma(Token token, Lexer lexer, int index) {
		if (token.getType().equals(TokenType.END_GROUP)) {
			lexer.setLexerState(LexerState.WORD);
			merge.add(DefaultNameBuilders.group((index)));
		} else if (!token.getType().equals(TokenType.COMMA)) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Returns padding character. Padding characters are '0' (digit zero) or '
	 * '(space). This method checks if current token is of type padding. If so it
	 * just returns this character. If token is of type number then padding
	 * character is ' '.
	 * 
	 * @param token token.
	 * @return Padding character presented in current group.
	 */
	private char nextPadding(Token token) {
		char padding = 0;

		if (token.getType().equals(TokenType.PADDING)) {
			padding = (char) token.getValue();
		} else if (token.getType().equals(TokenType.NUMBER)) {
			padding = ' ';
		}

		return padding;
	}

	/**
	 * Returns minimum width. Width must not be negative number.
	 * 
	 * @param token Token.
	 * @return Minimum width.
	 */
	private int nextMinwidth(Token token) {
		if (!token.getType().equals(TokenType.NUMBER)) {
			throw new IllegalArgumentException();
		}

		int minWidth = (Integer) token.getValue();
		return minWidth;
	}

	/**
	 * Parse expression when state is group.
	 * 
	 * @param lexer Lexer to get required tokens.
	 */
	private void parseWhenStateGroup(Lexer lexer) {
		Token token = lexer.getToken();

		int index = getIndex(token);
		token = lexer.nextToken();

		nextGroupOrComma(token, lexer, index);
		if (token.getType().equals(TokenType.END_GROUP)) {
			return;
		}

		token = lexer.nextToken();

		char padding = nextPadding(token);

		if (token.getType().equals(TokenType.PADDING)) {
			token = lexer.nextToken();
		}

		int minWidth = nextMinwidth(token);

		token = lexer.nextToken();

		if (!token.getType().equals(TokenType.END_GROUP)) {
			throw new IllegalArgumentException();
		}

		lexer.setLexerState(LexerState.WORD);

		merge.add(DefaultNameBuilders.group(index, padding, minWidth));
	}

	/**
	 * Parse expression when state is word.
	 * 
	 * @param lexer Lexer to get required tokens.
	 */
	private void parseWhenStateWord(Lexer lexer) {
		Token token = lexer.getToken();

		if (token.getType().equals(TokenType.EOF)) {
			return;
		}

		merge.add(DefaultNameBuilders.text((String) token.getValue()));

		token = lexer.nextToken();
		if (token.getType().equals(TokenType.START_GROUP)) {
			lexer.setLexerState(LexerState.GROUP);
		}
	}

	/**
	 * Implementation of NameBuilder. Instance of this class will be composite
	 * NameBuilder that gets references to all other NameBuilders and calls their
	 * execute method.
	 * 
	 * @author david
	 *
	 */
	private static class Merge implements NameBuilder {
		List<NameBuilder> list = new ArrayList<>();

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void execute(FilterResult result, StringBuilder sb) {
			list.forEach(t -> t.execute(result, sb));
		}

		/**
		 * Adds NameBuilder to internal list.
		 * 
		 * @param nb NameBuilder to be added to internal list.
		 */
		public void add(NameBuilder nb) {
			list.add(nb);
		}
	}
}
