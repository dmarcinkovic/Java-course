package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Implements Command interface. This class executes only one command: scales
 * the current shift length.
 * 
 * @author david
 *
 */
public class ScaleCommand implements Command{
	
	/**
	 * Factor for which the shift length will be scaled.
	 */
	private double factor;
	
	/**
	 * Initializes the factor to some values.
	 * @param factor Factor for which the current vector will be scaled.
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		
		Vector2D position = state.getDirection();
		state.setPosition(position.scaled(factor));
	}
}
