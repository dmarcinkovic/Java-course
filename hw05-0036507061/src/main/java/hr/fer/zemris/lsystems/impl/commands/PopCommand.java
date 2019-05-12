package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Implements Command interface. This class executes only one command: removes
 * one state from the top of the stack.
 * 
 * @author david
 *
 */
public class PopCommand implements Command {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}

}
