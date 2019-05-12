package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This command removes the top directory and dismisses it.
 * 
 * @author david
 *
 */
public class DropdShellCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.trim().isEmpty()) {
			return writeErrorMessage(env, 0);
		}

		return drop(env);
	}

	/**
	 * Removes the top directory and dismisses it.
	 * 
	 * @param env Shell environment.
	 * @return ShellStatus. ShellStaus.CONTINUE will be returned if everything
	 *         executes successfully.
	 */
	private ShellStatus drop(Environment env) {
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>)env.getSharedData("cdstack");
		
		if (stack.isEmpty()) {
			return writeErrorMessage(env, 1);
		}
		
		stack.pop();
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method that writes the error message to the console when unable to execute
	 * the command.
	 * 
	 * @param errorCode the error code.
	 * @param env       Shell environment.
	 * @return ShellStatus.
	 */
	private ShellStatus writeErrorMessage(Environment env, int errorCode) {
		try {
			switch (errorCode) {
			case 0:
				env.writeln("Too many arguments.");
				break;
			case 1:
				env.writeln("Nema pohranjenih direktorija.");
				break;
			}
		} catch (ShellIOException e) {
			return ShellStatus.TERMINATE;
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "dropd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();

		commandDescription.add("This command removes the top directory and dismisses it.");
		commandDescription.add("Command expects no arguments.");

		commandDescription = Collections.unmodifiableList(commandDescription);

		return commandDescription;
	}

}
