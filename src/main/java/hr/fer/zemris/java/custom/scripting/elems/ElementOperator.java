package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element representing operator expression. Operators are '+', '-', '*', '/',
 * '^'.
 * 
 * @author david
 *
 */
public class ElementOperator extends Element {
	/**
	 * Property to store operator.
	 */
	private String symbol;

	/**
	 * Constructor to initialize operator.
	 * 
	 * @param symbol new operator.
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return symbol;
	}
}
