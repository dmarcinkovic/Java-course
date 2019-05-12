package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
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
 * Represents popd command. Popd command expects no arguments. Popd command
 * removes the top path and sets it as the current directory.
 * 
 * @author david
 *
 */
public class PopdShellCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.trim().isEmpty()) {
			return writeErrorMessage(env, 0);
		}

		return pop(env);
	}

	/**
	 * Removes the top path and sets it as current directory.
	 * 
	 * @param env Shell environment.
	 * @return ShellStatus. If everything executes successfully ShellStatus.CONTINUE
	 *         will be returned.
	 */
	private ShellStatus pop(Environment env) {
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");

		if (stack.isEmpty()) {
			return writeErrorMessage(env, 1);
		}

		Path currentDirectory = stack.pop();

		if (Files.exists(currentDirectory)) {
			env.setCurrentDirectory(currentDirectory);
		}

		env.setSharedData("cdstack", stack);
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method that writes the error message to the console when unable to execute
	 * the command.
	 * 
	 * @param errorCode the error code.
	 * @param env       Shell environment.
	 * @return ShellStatus. If writing to the console executes successfully
	 *         ShellStatus.CONTINUE will be returned, otherwise ShellStaus.TERMINATE
	 *         will be returned.
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
		return "popd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();

		commandDescription.add("Popd command removes the top path from stack and sets");
		commandDescription.add("it as current directory.");
		commandDescription.add("Popd command expects no arguments.");

		commandDescription = Collections.unmodifiableList(commandDescription);

		return commandDescription;
	}

}
