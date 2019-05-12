package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element representing function expression. Function expression starts with @
 * sing. After that comes one or more letters, then underscore or digit.
 * 
 * @author david
 *
 */
public class ElementFunction extends Element {
	/**
	 * Name of the function.
	 */
	private String name;

	/**
	 * Constructor to initialize function name.
	 * 
	 * @param name New function name.
	 */
	public ElementFunction(String name) {
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
