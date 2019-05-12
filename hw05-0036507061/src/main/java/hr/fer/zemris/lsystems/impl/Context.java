package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This class enables the execution of the procedure fractal display.
 * 
 * @author david
 *
 */
public class Context {
	private ObjectStack<TurtleState> states;

	public Context() {
		states = new ObjectStack<>();
	}

	/**
	 * Returns the state from the top of the stack without removing it.
	 * 
	 * @return the state from the top of the stack without removing it.
	 */
	public TurtleState getCurrentState() {
		return states.peek();
	}

	/**
	 * Pushes the specified state to the top of the stack.
	 * 
	 * @param state State to be pushed to the top of the stack.
	 */
	public void pushState(TurtleState state) {
		states.push(state);
	}

	/**
	 * Removes one state from the top of the stack.
	 */
	public void popState() {
		states.pop();
	}
}
