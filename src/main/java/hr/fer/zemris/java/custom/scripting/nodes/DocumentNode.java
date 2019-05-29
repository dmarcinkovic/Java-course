package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class that represents document node. Document node is parent node. This is
 * something like \<html\> tag in HTML. This node is the container for all other
 * nodes in the text.
 * 
 * @author david
 *
 */
public class DocumentNode extends Node {

	/**
	 * Accepts all document nodes.
	 * 
	 * @param visitor Visitor in visitor design pattern.
	 */
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
}
