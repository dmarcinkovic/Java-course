package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Arrays;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Class that represents the content of '=' tag. Everything that is inside of
 * '=' tag is of type EchoNode.
 * 
 * @author david
 *
 */
public class EchoNode extends Node {
	/**
	 * Private member of this class. If is an array of elements presented in '='
	 * tag.
	 */
	private Element[] elements;

	/**
	 * Constructor to initialize elements.
	 * 
	 * @param elements Array of elements.
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * Method to return all elements presented in this node.
	 * 
	 * @return Elements.
	 */
	public Element[] getElements() {
		return Arrays.copyOf(elements, elements.length);
	}

	/**
	 * Accepts echo nodes.
	 * 
	 * @param visitor Visitor in visitor design pattern.
	 */
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
