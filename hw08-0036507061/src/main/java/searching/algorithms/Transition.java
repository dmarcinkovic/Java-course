package searching.algorithms;

/**
 * One transition of puzzle.
 * @author david
 *
 * @param <S>
 */
public class Transition<S> {
	/**
	 * Current state. 
	 */
	private S state;
	
	/**
	 * Cost.
	 */
	private double cost;
	
	/**
	 * Constructor to initialize private fields.
	 * @param state State. 
	 * @param cost Cost. 
	 */
	public Transition(S state, double cost) {
		this.state = state;
		this.cost = cost;
	}

	/**
	 * Returns the state.
	 * 
	 * @return the state
	 */
	public S getState() {
		return state;
	}

	/**
	 * Returns the cost.
	 * 
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

}
