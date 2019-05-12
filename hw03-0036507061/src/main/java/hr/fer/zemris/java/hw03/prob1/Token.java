package hr.fer.zemris.java.hw03.prob1;

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
	 * Value of token.
	 */
	private Object value; 
	
	/**
	 * Constructor that receives token type and value of token.
	 * @param type Token type. 
	 * @param value	Value of token.
	 */
	public Token(TokenType type, Object value) {
		this.type = type; 
		this.value = value;
	}
	
	/**
	 * Returns the value of token.
	 * @return Value of token.
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Return token type.
	 * @return token type.
	 */
	public TokenType getType() {
		return type;
	}
}
