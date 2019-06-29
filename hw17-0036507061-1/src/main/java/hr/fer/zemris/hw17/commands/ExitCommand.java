package hr.fer.zemris.hw17.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.hw17.shell.Environment;
import hr.fer.zemris.hw17.shell.ShellIOException;
import hr.fer.zemris.hw17.shell.ShellStatus;

/**
 * Command used to terminate the shell.
 * 
 * @author David
 *
 */
public class ExitCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.trim().length() != 0) {
			return writeErrorMessage(env);
		}

		return ShellStatus.TERMINATE;
	}

	/**
	 * Method that writes the error message to the console.
	 */
	private ShellStatus writeErrorMessage(Environment env) {
		try {
			env.writeln("Invalid number of arguments.");
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
		return "exit";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();

		commandDescription.add("Exits the shell.");

		commandDescription = Collections.unmodifiableList(commandDescription);

		return commandDescription;
	}

}
