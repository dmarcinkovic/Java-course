package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element representing variable. Variable consists of letters, underscores and
 * digits.
 * 
 * @author david
 *
 */
public class ElementVariable extends Element {
	/**
	 * Name of the variable.
	 */
	private String name;

	/**
	 * Constructor to initialize name of the variable.
	 * 
	 * @param name new name of the variable.
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return name;
	}
}
