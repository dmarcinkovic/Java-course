package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
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
 * Represents mkdir command. This command creates new directory in current
 * directory.
 * 
 * @author david
 *
 */
public class MkdirShellCommand extends ShellCommandUtil implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] allArguments = getArguments(arguments);

		if (allArguments.length != 1) {
			return writeErrorMessage(env, 0);
		}

		return mkdir(env, allArguments[0]);
	}

	/**
	 * Method to create directory in current directory. If could not create
	 * directory, writes appropriate message to the console.
	 * 
	 * @param env           Shell environment.
	 * @param directoryName Name of directory to be created.
	 * @return ShellStatus. If everything executes successfully returns
	 *         ShellStatus.CONTINUE, otherwise return ShellStatus.TERMINATE.
	 */
	private ShellStatus mkdir(Environment env, String directoryName) {
		try {
			Path directory = Paths.get(directoryName);
			
			if (Files.exists(directory)) {
				return writeErrorMessage(env, 1);
			}
			
			Files.createDirectories(directory);
			
			return ShellStatus.CONTINUE;
		} catch (Exception e) {
			return writeErrorMessage(env, 2);
		}
	}

	/**
	 * Writes the error message to the console.
	 * 
	 * @param env       Shell environment.
	 * @param errorCode error code.
	 * @return ShellStatus. If writing to the console executes successfully returns
	 *         ShellStatus.CONTINUE.
	 */
	private ShellStatus writeErrorMessage(Environment env, int errorCode) {
		try {
			switch (errorCode) {
			case 0:
				env.writeln("Wrong number of arguments.");
				break;
			case 1:
				env.writeln("Directory already exists.");
				break;
			case 2:
				env.writeln("Could not make directory.");
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
		return "mkdir";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();

		commandDescription.add("Creates new directory with the specified name.");
		commandDescription.add("Takes only one argument: name of the director to be created.");

		commandDescription = Collections.unmodifiableList(commandDescription);

		return commandDescription;
	}

}
