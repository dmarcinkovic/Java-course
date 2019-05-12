package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * This interface represents one command to be executed. 
 * @author david
 *
 */
public interface Command {
	/**
	 * Method that executes one command.
	 * @param ctx Context of the turtle.
	 * @param painter Class able to paint. 
	 */
	void execute(Context ctx, Painter painter);
}
