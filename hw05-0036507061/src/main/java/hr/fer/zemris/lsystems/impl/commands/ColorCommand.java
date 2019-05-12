package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Implements Command interface. This class executes only one command: changes
 * the color of the current state.
 * 
 * @author david
 *
 */
public class ColorCommand implements Command{
	/**
	 * The color of current turtle state.
	 */
	private Color color; 
		
	/**
	 * Initializes the color. 
	 * @param color Current turtle state.
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		
		state.setColor(color);
	}
	
}
