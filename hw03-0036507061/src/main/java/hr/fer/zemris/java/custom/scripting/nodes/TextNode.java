package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Represents any text inside the DocumentNode. 
 * @author david
 *
 */
public class TextNode extends Node {
	/**
	 * Text in the DocumentNode. 
	 */
	private String text;

	/**
	 * Constructor to initialize member text.
	 * @param text Text in the DocumentNode.
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/**
	 * Method to return text.
	 * @return text. 
	 */
	public String getText() {
		return text;
	}
	
	
}
