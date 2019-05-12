package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Implements Command interface. This class executes only one command: Rotate
 * the vector direction by the specified angle.
 * 
 * @author david
 *
 */
public class RotateCommand implements Command{
	/**
	 * Angle of rotation-
	 */
	private double angle;
	
	/**
	 * Initializes angle of rotation.
	 * @param angle Angle of rotation.
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		
		Vector2D direction = currentState.getDirection();
		currentState.setDirection(direction.rotated(angle));
	}
	
}
