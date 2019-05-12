package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.db.lexer.Lexer;
import hr.fer.zemris.java.hw05.db.lexer.TokenType;

/**
 * Represents simple parser for database language.
 * 
 * @author david
 *
 */
public class QueryParser {
	List<ConditionalExpression> queries;

	/**
	 * Constructor that performs parsing.
	 * 
	 * @param text Text to be parsed.
	 * @throws NullPointerException if text is null.
	 */
	public QueryParser(String text) {
		if (text == null) {
			throw new NullPointerException();
		}

		queries = new ArrayList<>();

		parse(text);
	}

	/**
	 * Checks if given query is direct or not. Query is called direct if it has form
	 * jmbag="xxx". On the other words it must have only one comparison.
	 * 
	 * @return true if given query is director, otherwise returns false.
	 */
	public boolean isDirectQuery() {
		return queries.size() == 1 && queries.get(0).getFieldValueGetter().equals(FieldValueGetters.JMBAG)
				&& queries.get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS);
	}

	/**
	 * Returns jmbag from direct query.
	 * 
	 * @return Jmbag from direct query.
	 * @throws IllegalStateException if query is not direct.
	 */
	public String getQueriedJMBAG() {
		if (!isDirectQuery()) {
			throw new IllegalStateException();
		}
		return queries.get(0).getLiteral();
	}

	/**
	 * Method returns all conditional expressions of one query.
	 * 
	 * @return All conditional expressions of one query.
	 */
	public List<ConditionalExpression> getQuery() {
		return queries;
	}

	/**
	 * Method to parse given query.
	 * 
	 * @param text Given query.
	 * @throws ParserException if any error occurs during parsing.
	 * @throws LexerException  if any error occurs during lexical analysis.
	 */
	private void parse(String text) {
		Lexer lexer = new Lexer(text);

		boolean and = false;
		while (!lexer.nextToken().getType().equals(TokenType.EOF)) {
			and = false;
			queries.add(getNextConditionalExpression(lexer));
			
			if (lexer.nextToken().getType().equals(TokenType.EOF)) {
				break;
			}
			
			if (lexer.getToken().getType().equals(TokenType.LOGICAL_OPERATOR)) {
				and = true;
			} else {
				throw new ParserException();
			}
		}

		if (and) {
			throw new ParserException();
		}
	}

	/**
	 * Method to get next conditional expression. Conditional expression consists of
	 * field name, operator and string literal.
	 * 
	 * @param lexer Reference to lexer.
	 * @return Conditional expression.
	 * @throws ParserException if any error occurs while parsing.
	 * @throws LexerException  if any error occurs during lexical analysis.
	 */
	private ConditionalExpression getNextConditionalExpression(Lexer lexer) {
		String name = lexer.getToken().getValue();
		if (!isCorrectName(name)) {
			throw new ParserException();
		}

		String operator = lexer.nextToken().getValue();
		if (!isOperator(operator)) {
			throw new ParserException();
		}

		if (!lexer.nextToken().getType().equals(TokenType.STRING_LITERAL)) {
			throw new ParserException();
		}
		
		String stringLiteral = lexer.getToken().getValue();

		if (!isCorrectStringLiteral(stringLiteral)) {
			System.out.println("String literal consists of more than one wildcards.");
		}

		return new ConditionalExpression(getFieldValueGetters(name), stringLiteral, getComparisonOperator(operator));
	}

	/**
	 * Method that checks if given string consists more than one wildcards.
	 * 
	 * @param string String to check for.
	 * @return True if given string consists less than two wildcards.
	 */
	private boolean isCorrectStringLiteral(String string) {
		char[] array = string.toCharArray();
		int counter = 0;

		for (Character c : array) {
			if (c == '*') {
				counter++;
			}
			if (counter >= 2) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Method to get field value from given string.
	 * 
	 * @param name Given string.
	 * @return Field Value.
	 * @throws ParserException if name if not firstName nor lastName not jmbag.
	 *                         (those names are case sensitive, so if name FirstName
	 *                         this exception will be thrown).
	 */
	private IFieldValueGetter getFieldValueGetters(String name) {
		if (name.equals("firstName")) {
			return FieldValueGetters.FIRST_NAME;
		} else if (name.equals("lastName")) {
			return FieldValueGetters.LAST_NAME;
		} else if (name.equals("jmbag")) {
			return FieldValueGetters.JMBAG;
		}
		throw new ParserException();
	}

	/**
	 * Method to return comparison operator from given string.
	 * 
	 * @param operator given string.
	 * @return Comparison operator.
	 */
	private IComparisonOperator getComparisonOperator(String operator) {
		if (operator.equals("=")) {
			return ComparisonOperators.EQUALS;
		} else if (operator.equals("<=")) {
			return ComparisonOperators.LESS_OR_EQUALS;
		} else if (operator.equals("<")) {
			return ComparisonOperators.LESS;
		} else if (operator.equals(">")) {
			return ComparisonOperators.GREATER;
		} else if (operator.equals(">=")) {
			return ComparisonOperators.GREATER_OR_EQUALS;
		} else if (operator.equals("LIKE")) {
			return ComparisonOperators.LIKE;
		} else if (operator.equals("!=")) {
			return ComparisonOperators.NOT_EQUALS;
		}
		return null;
	}

	/**
	 * Method to check if name of the field is valid or not. Valid names are :
	 * jmbag, lastName, firstName, grade.
	 * 
	 * @param name Name to check if is valid or not.
	 * @return true if name is valid, otherwise returns false.
	 */
	private boolean isCorrectName(String name) {
		return name.equalsIgnoreCase("jmbag") || name.equalsIgnoreCase("lastName")
				|| name.equalsIgnoreCase("firstName");
	}

	/**
	 * Method to check if given string is operator or not. Operators are: <=,
	 * <, >=, >, =, !=, LIKE.
	 * 
	 * @param operator Operator to check if is valid or not.
	 * @return True if operator is valid, otherwise returns false.
	 */
	private boolean isOperator(String operator) {
		return operator.equals("LIKE") || operator.equals(">=") || operator.equals("<=") || operator.equals(">")
				|| operator.equals("<") || operator.equals("=") || operator.equals("!=");
	}
}
