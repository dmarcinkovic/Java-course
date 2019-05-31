package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element representing String expression.
 * 
 * @author david
 *
 */
public class ElementString extends Element {
	/**
	 * Value to store String.
	 */
	private String value;

	/**
	 * Constructor to initialize value.
	 * 
	 * @param value new Value.
	 */
	public ElementString(String value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return value;
	}
}
