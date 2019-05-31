package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Parser to do syntax analysis. Syntax analysis is the process of analysing a
 * string of symbols. This parser is used to process syntax analysis of language
 * that was invented by doc.dr.sc Marko Čupić.
 * 
 * @author david
 *
 */
public class SmartScriptParser {
	/**
	 * Reference to lexer that will be providing tokens from source code.
	 */
	private Lexer lexer;

	/**
	 * Stack to implement Document model.
	 */
	private ObjectStack stack;

	/**
	 * Stores the number of for loop tags.
	 */
	private int numberOfForTags = 0;

	/**
	 * Stores the number of end tags.
	 */
	private int numberOfEndTags = 0;

	/**
	 * Constructor to do syntax analysis of given text.
	 * 
	 * @param text Text to do syntax analysis.
	 */
	public SmartScriptParser(String text) {
		lexer = new Lexer(text);
		stack = new ObjectStack();

		try {
			parse();
		} catch (SmartScriptParserException e) {
			throw new SmartScriptParserException();
		}
	}

	/**
	 * Method to process the lexical analysis process. It constructors hierarchical
	 * structure of document node.
	 * 
	 * @throws SmartScriptParserException if any error occurs during the lexical
	 *                                    analysis.
	 */
	private void parse() {
		stack.push(new DocumentNode());

		Token token = lexer.nextToken();

		while (token.getType() != TokenType.EOF) {
			if (token.getType().equals(TokenType.TEXT)) {
				Node node = (Node) stack.peek();
				Node child = new TextNode((String) token.getValue());
				node.addChildNode(child);
			} else if (token.getType().equals(TokenType.OPENING_TAG)) {
				lexer.setLexerState(LexerState.TAG);
				insideTheTag();
			}
			token = lexer.nextToken();
		}

		if (numberOfEndTags != numberOfForTags) {
			throw new SmartScriptParserException();
		}
	}

	/**
	 * Method to process lexical analysis of given text inside the tag. Valid tags
	 * are 'FOR' and '=' tags. For tag consists of one variable name, and start
	 * expression, end expression and optionally step expression. '=' tag consists
	 * of one or more elements.
	 * 
	 * @throws SmartScriptParserException if any error occurs during the parsing.
	 */
	private void insideTheTag() {
		Token token = lexer.nextToken();
		if (token.getValue().equals('=')) {
			equalsTag();
			return;
		}
		try {
			String s = (String) token.getValue();

			if (s.equalsIgnoreCase("FOR")) {
				forTag();
			} else if (s.equalsIgnoreCase("end")) {
				endTag();
			} else {
				equalsTag();
			}

		} catch (ClassCastException | SmartScriptParserException e) {
			throw new SmartScriptParserException();
		}
	}

	/**
	 * Method to extract elements from equals tag. Equals tag starts with the '='
	 * sign after which follows arbitrary number of elements.
	 * 
	 * @throws SmartScriptParserException if any error occurs during parsing.
	 */
	private void equalsTag() {
		
		Token token = lexer.nextToken();
		ArrayIndexedCollection list = new ArrayIndexedCollection();

		while (!token.getType().equals(TokenType.CLOSING_TAG)) {
			if (token.getType().equals(TokenType.EOF)) {
				throw new SmartScriptParserException();
			} else if (token.getType().equals(TokenType.OPENING_TAG)) {
				throw new SmartScriptParserException();
			} else {
				list.add(getElement(token));
			}
			token = lexer.nextToken();
		}

		createEqualsTag(list);
		lexer.setLexerState(LexerState.TEXT);
	}

	/**
	 * Method to create equals tag from given list of elements.
	 * 
	 * @param list List of elements.
	 */
	private void createEqualsTag(ArrayIndexedCollection list) {
		Element[] elements = new Element[list.size()];

		for (int i = 0, n = list.size(); i < n; i++) {
			Element element = (Element) list.get(i);
			elements[i] = element;
		}

		Node node = (Node) stack.peek();
		node.addChildNode(new EchoNode(elements));
	}

	/**
	 * Method to extract elements from for tag. For tag consists of variable,
	 * startExpression, endExpression and optionally stepExpression.
	 * 
	 * @throws SmartScriptParserException if any error occurs during parsing.
	 */
	private void forTag() {
		Token token = lexer.nextToken();
		numberOfForTags++;

		// If there is more end tags then for loop tags throw an exception.
		if (numberOfEndTags > numberOfForTags) {
			throw new SmartScriptParserException();
		}

		if (!token.getType().equals(TokenType.VARIABLE)) {
			throw new SmartScriptParserException();
		}

		ElementVariable variable = new ElementVariable((String) token.getValue());

		Element startExpression = isValidForTag(lexer.nextToken()) ? getElement(lexer.getToken()) : null;
		Element endExpression = isValidForTag(lexer.nextToken()) ? getElement(lexer.getToken()) : null;
		Element stepExpression = isValidForTag(lexer.nextToken()) ? getElement(lexer.getToken()) : null;

		createForTag(variable, startExpression, endExpression, stepExpression);
	}

	/**
	 * Method that creates for tag from given variable, startExpression,
	 * endExpression and stepExpression.
	 * 
	 * @param variable Variable.
	 * @param start    StartExpression.
	 * @param end      EndExpression
	 * @param step     StepExpression.
	 * @throws SmartScriptParserException if cannot create forLoop tag.
	 */
	private void createForTag(ElementVariable variable, Element start, Element end, Element step) {
		if (start == null || end == null) {
			throw new SmartScriptParserException();
		}
		
		if (!lexer.getToken().getType().equals(TokenType.CLOSING_TAG)
				&& !lexer.nextToken().getType().equals(TokenType.CLOSING_TAG)) {
			throw new SmartScriptParserException();
		}

		lexer.setLexerState(LexerState.TEXT);
		ForLoopNode forLoopNode = new ForLoopNode(variable, start, end, step);

		Node node = (Node) stack.peek();
		node.addChildNode(forLoopNode);

		stack.push(forLoopNode);
	}

	/**
	 * Method to extract element from end tag.
	 * 
	 * @throws SmartScriptParserException if end tag is not valid.
	 */
	private void endTag() {
		if (!lexer.nextToken().getType().equals(TokenType.CLOSING_TAG)) {
			throw new SmartScriptParserException();
		}
		numberOfEndTags++;

		// If number of end tags is greater than the number of for tag throw an
		// exception.
		if (numberOfEndTags > numberOfForTags) {
			throw new SmartScriptParserException();
		}

		lexer.setLexerState(LexerState.TEXT);
		stack.pop();
	}

	/**
	 * Method to check if given token is valid element for for tag. Valid are :
	 * Variable, Number, String.
	 * 
	 * @param token token to check for.
	 * @return true if token is valid, otherwise returns false.
	 */
	private boolean isValidForTag(Token token) {
		if (token == null) {
			return false;
		}
		return token.getType().equals(TokenType.VARIABLE) || token.getType().equals(TokenType.INTEGER)
				|| token.getType().equals(TokenType.DOUBLE) || token.getType().equals(TokenType.STRING);
	}

	/**
	 * Method to return element according to given token type.
	 * 
	 * @param token given token.
	 * @return Element of type according to given token type.
	 */
	private Element getElement(Token token) {
		Element element = null;

		if (token == null) {
			return null;
		}

		if (token.getType().equals(TokenType.STRING)) {
			String value = (String) token.getValue();
			element = new ElementString(value);
		} else if (token.getType().equals(TokenType.DOUBLE)) {
			double value = (double) token.getValue();
			element = new ElementConstantDouble(value);
		} else if (token.getType().equals(TokenType.INTEGER)) {
			int value = (int) token.getValue();
			element = new ElementConstantInteger(value);
		} else if (token.getType().equals(TokenType.FUNCTION)) {
			String name = (String) token.getValue();
			element = new ElementFunction(name.substring(1));
		} else if (token.getType().equals(TokenType.VARIABLE)) {
			String name = (String) token.getValue();
			element = new ElementVariable(name);
		} else if (token.getType().equals(TokenType.OPERATOR)) {
			char operator = (char) token.getValue();
			element = new ElementOperator(String.valueOf(operator));
		}
		return element;
	}

	/**
	 * Method to return DocumentNode of parsed text.
	 * 
	 * @return Parsed DocumentNode.
	 */
	public DocumentNode getDocumentNode() {
		return (DocumentNode) stack.peek();
	}
}
