package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This command pushes current directory to stack and sets current directory to
 * one that is provided as argument.
 * 
 * @author david
 *
 */
public class PushdShellCommand extends ShellCommandUtil implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] allArguments = getArguments(arguments);

		if (allArguments.length != 1) {
			return writeErrorMessage(env, 0);
		}

		return push(env, allArguments[0]);
	}

	/**
	 * Method that pushes current directory to stack.
	 * 
	 * @param env Shell environment.
	 * @param dir Directory which will be pushed to stack.
	 * @return ShellStatus. If everything executes successfully ShellStatus.CONTINUE
	 *         will be returned.
	 */
	private ShellStatus push(Environment env, String dir) {
		Path directory = null;
		try {
			directory = Paths.get(dir);

			directory = env.getCurrentDirectory().resolve(directory);
		} catch (InvalidPathException e) {
			return writeErrorMessage(env, 1);
		}

		if (!Files.exists(directory)) {
			return writeErrorMessage(env, 2);
		}

		if (!Files.isDirectory(directory)) {
			return writeErrorMessage(env, 3);
		}

		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");

		if (stack == null) {
			stack = new Stack<>();
		}

		stack.push(env.getCurrentDirectory());
		env.setCurrentDirectory(directory);

		env.setSharedData("cdstack", stack);

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
			case 1:
				env.writeln("Invalid path.");
				break;
			case 2:
				env.writeln("Directory does not exists");
				break;
			case 3:
				env.writeln("Given path is not directory.");
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
		return "pushd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();

		commandDescription.add("This command pushes current directory to stack");
		commandDescription.add("and sets current directory to one that is provided as argument.");
		commandDescription.add("Command expects a single argument: path to directory.");

		commandDescription = Collections.unmodifiableList(commandDescription);

		return commandDescription;
	}

}
