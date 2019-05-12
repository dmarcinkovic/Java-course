package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents pwd shell command. This command is used to print the current
 * working directory. It expects no arguments.
 * 
 * @author david
 *
 */
public class PwdShellCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.trim().isEmpty()) {
			return writeErrorMessage(env, 0);
		}

		return printCurrentWorkingDirectory(env);
	}

	/**
	 * Prints current working directory to shell.
	 * 
	 * @param env Shell environment.
	 * @return ShellStatus.CONTINUE is writing to the shell executes successfully,
	 *         otherwise returns ShellStatus.TERMINATE.
	 */
	private ShellStatus printCurrentWorkingDirectory(Environment env) {
		try {
			env.writeln(env.getCurrentDirectory().toString());
		} catch (ShellIOException e) {
			return ShellStatus.TERMINATE;
		}
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
				env.writeln("Wrong number of arguments.");
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
		return "pwd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();

		commandDescription.add("Prints the path to current working directory.");
		commandDescription.add("Command expects no arguments.");

		commandDescription = Collections.unmodifiableList(commandDescription);

		return commandDescription;
	}

}
