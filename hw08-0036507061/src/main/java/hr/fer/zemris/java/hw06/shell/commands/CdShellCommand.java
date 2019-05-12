package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents cd shell command. Cd shell command is used to change current
 * directory. It expects a single argument : path to new current directory.
 * 
 * @author david
 *
 */
public class CdShellCommand extends ShellCommandUtil implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] allArguments = getArguments(arguments);

		if (allArguments.length != 1 || arguments.isEmpty()) {
			return writeErrorMessage(0, env);
		}

		return changeDirectory(allArguments[0], env);
	}

	/**
	 * Method used to change directory.
	 * 
	 * @param directory New directory.
	 * @param env       Shell environment.
	 * @return ShellStatus.
	 */
	private ShellStatus changeDirectory(String directory, Environment env) {
		try {
			Path givenDirectory = Paths.get(directory);

			Path realDirectory = env.getCurrentDirectory().resolve(givenDirectory);

			env.setCurrentDirectory(realDirectory);
		} catch (IllegalArgumentException e) {
			return writeErrorMessage(1, env);
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
	private ShellStatus writeErrorMessage(int errorCode, Environment env) {
		try {
			switch (errorCode) {
			case 0:
				env.writeln("Wrong number of arguments. ");
				break;
			case 1:
				env.writeln("Invalid path.");
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
		return "cd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();

		commandDescription.add("Command to change current working directory.");
		commandDescription.add("Expects a single argument : path to new current directory.");

		commandDescription = Collections.unmodifiableList(commandDescription);

		return commandDescription;
	}

}
