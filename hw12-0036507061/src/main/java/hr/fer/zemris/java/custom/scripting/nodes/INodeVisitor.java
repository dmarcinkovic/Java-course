package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface that describes Visitor object in Visitor design pattern.
 * 
 * @author David
 *
 */
public interface INodeVisitor {

	/**
	 * Visits all text nodes.
	 * 
	 * @param node Reference to TextNode.
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Visits all ForLoopNodes.
	 * 
	 * @param node Reference to ForLoopNode.
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Visits all EchoNodes.
	 * 
	 * @param node Reference to EchoNode.
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Visits all DocumentNodes.
	 * 
	 * @param node Reference to DocumentNode.
	 */
	public void visitDocumentNode(DocumentNode node);
}
