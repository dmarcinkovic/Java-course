package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Implements Command interface. This class executes only one command: moves
 * forward by some step and leaves the colored trace.
 * 
 * @author david
 *
 */
public class DrawCommand implements Command {

	/**
	 * The step for which the turtle will move.
	 */
	private double step;

	/**
	 * Constructor that initializes the step.
	 * 
	 * @param step
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		
		Vector2D position = state.getPosition();
		Vector2D direction = state.getDirection();
		Vector2D newDirection = direction.scaled(step*state.getLength());

		Vector2D newPosition = position.translated(newDirection);
		
		painter.drawLine(position.getX(), position.getY(), newPosition.getX(), newPosition.getY(), state.getColor(),
				1f);
		
		state.setPosition(newPosition);
	}

}
