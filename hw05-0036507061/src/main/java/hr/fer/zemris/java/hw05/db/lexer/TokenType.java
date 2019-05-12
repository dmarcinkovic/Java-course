package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Represents type of the token.
 * @author david
 *
 */
public enum TokenType {
	/**
	 * Represents token that consists of letters, digits and underscores.
	 */
	NAME, 
	
	/**
	 * Represents operators : <, <=, >, >=, =, != 
	 */
	OPERATOR, 
	
	/**
	 * Represents string literal. String literals are defined within '"'.
	 */
	STRING_LITERAL, 
	
	/**
	 * Represents logical operators. This program support only one logical operator: and.
	 */
	LOGICAL_OPERATOR, 
	
	/**
	 * Represents end of the query.
	 */
	EOF
}
