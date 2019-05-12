package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Implements Command interface. This class executes only one command: Copies
 * the state from the top of the stack and this copy pushes again to the stack.
 * 
 * @author david
 *
 */
public class PushCommand implements Command {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		
		TurtleState copy = state.copy(); 
		
		ctx.pushState(copy);
	}

}
