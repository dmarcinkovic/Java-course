package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element representing constant integer expression.
 * 
 * @author david
 *
 */
public class ElementConstantInteger extends Element {
	/**
	 * Integer value to store value of expression.
	 */
	private int value;

	/**
	 * Constructor to initialize integer value.
	 * 
	 * @param value new Value.
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return String.valueOf(value);
	}

}
