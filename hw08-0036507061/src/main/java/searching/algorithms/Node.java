package searching.algorithms;

/**
 * This is object that stores reference to current state, reference to parent
 * node and cost.
 * 
 * @author david
 *
 * @param <S> Type of node.
 */
public class Node<S> {
	/**
	 * Reference to parent node.
	 */
	private Node<S> parent;

	/**
	 * State.
	 */
	private S state;

	/**
	 * Cost.
	 */
	private double cost;

	/**
	 * Constructor to initialize private fields.
	 * 
	 * @param parent Reference to parent node.
	 * @param state  State.
	 * @param cost   Cost.
	 */
	public Node(Node<S> parent, S state, double cost) {
		this.parent = parent;
		this.state = state;
		this.cost = cost;
	}

	/**
	 * Returns state.
	 * 
	 * @return state.
	 */
	public S getState() {
		return state;
	}

	/**
	 * Returns cost.
	 * 
	 * @return cost.
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Returns parent.
	 * 
	 * @return parent.
	 */
	public Node<S> getParent() {
		return parent;
	}
}
