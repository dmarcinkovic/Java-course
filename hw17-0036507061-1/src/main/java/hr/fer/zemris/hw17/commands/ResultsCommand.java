package hr.fer.zemris.hw17.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.hw17.shell.Environment;
import hr.fer.zemris.hw17.shell.Result;
import hr.fer.zemris.hw17.shell.ShellIOException;
import hr.fer.zemris.hw17.shell.ShellStatus;

/**
 * Command that prints the result of previously executed command to the shell.
 * 
 * @author David
 *
 */
public class ResultsCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.trim().isEmpty()) {
			return writeErrorMessage(env, 0);
		}

		List<Result> lastCommand = env.getResultOfPreviousCommand();

		if (lastCommand == null) {
			return writeErrorMessage(env, 1);
		}

		env.setPreviousCommand("results");

		int index = 0;
		for (Result s : lastCommand) {
			if (writeToShell(env, "[ " + index + "] " + s.toString()).equals(ShellStatus.TERMINATE)) {
				return ShellStatus.TERMINATE;
			}
			index++;
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Method that writes message to shell.
	 * 
	 * @param env Shell environment.
	 * @param s   Message to be written to shell.
	 * @return ShellStatus.CONTINUE if writing to the shell executes successfully,
	 *         otherwise returns ShellStatus.TERMIANTE.
	 */
	private ShellStatus writeToShell(Environment env, String s) {
		try {
			env.writeln(s);
		} catch (ShellIOException e) {
			return ShellStatus.TERMINATE;
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method that writes the error message to the console.
	 * 
	 * @param env Shell environment.
	 */
	private ShellStatus writeErrorMessage(Environment env, int errorCode) {
		try {
			if (errorCode == 0) {
				env.writeln("Invalid number of arguments.");
			} else if (errorCode == 1) {
				env.writeln("Last executed command was not 'query' command.");
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
		return "results";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();

		commandDescription.add("Comand that prints the result of previously");
		commandDescription.add("executed command. It takes no arguments.");

		commandDescription = Collections.unmodifiableList(commandDescription);

		return commandDescription;
	}

}
