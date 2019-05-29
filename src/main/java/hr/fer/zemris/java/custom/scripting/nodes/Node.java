package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Class represents one node of graph.
 * 
 * @author david
 *
 */
public class Node {
	/**
	 * Stores all children of this node.
	 */
	private ArrayIndexedCollection collection = null; 

	/**
	 * Adds given child to a collection of children.
	 * 
	 * @param child Child to be added into collection.
	 */
	public void addChildNode(Node child) {
		if (collection == null) {
			collection = new ArrayIndexedCollection();
		}
		collection.add(child);
	}

	/**
	 * Returns number of children that this node has.
	 * 
	 * @return Number of children that this node has.
	 */
	public int numberOfChildren() {
		if (collection == null)
			return 0;
		return collection.size();
	}

	/**
	 * Method to return child from specified index.
	 * 
	 * @param index Index of child.
	 * @return Child at given index.
	 * @throws IndexOutOfBoundsException if the index is invalid.
	 */
	public Node getChild(int index) {
		return (Node) collection.get(index);
	}
	
}
