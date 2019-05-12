package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Class represents one token of input program.
 * @author david
 *
 */
public class Token {
	
	/**
	 * Token type. 
	 */
	private TokenType type;
	
	/**
	 *  Value of token.
	 */
	private String value;

	/**
	 * Constructor that receives token type and value of token.
	 * @param type Token type. 
	 * @param value	Value of token.
	 */
	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Return token type.
	 * @return token type.
	 */
	public TokenType getType() {
		return type;
	}
	
	/**
	 * Returns the value of token.
	 * @return Value of token.
	 */
	public String getValue() {
		return value;
	}
}
