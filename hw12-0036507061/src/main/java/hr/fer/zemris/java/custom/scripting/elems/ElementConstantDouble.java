package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element representing constant double expression.
 * 
 * @author david
 *
 */
public class ElementConstantDouble extends Element {
	/**
	 * Double value to store value of expression.
	 */
	private double value;

	/**
	 * Constructor to initialize double value.
	 * 
	 * @param value new Value.
	 */
	public ElementConstantDouble(double value) {
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
