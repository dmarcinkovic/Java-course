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
 * This command prints to the terminal all the paths existing in stack starting
 * from last added.
 * 
 * @author david
 *
 */
public class ListdShellCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.trim().isEmpty()) {
			return writeErrorMessage(env, 0);
		}

		return list(env);
	}

	/**
	 * Print all paths stored in stack.
	 * 
	 * @param env Shell environment.
	 * @return ShellStatus. If writing to shell executes successfully this method
	 *         will return ShellStatus.CONTINUE, otherwise it will return
	 *         ShellStatus.TERINATE.
	 */
	private ShellStatus list(Environment env) {
		try {
			@SuppressWarnings("unchecked")
			Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");

			printStack(stack, env);
		} catch (ShellIOException e) {
			return ShellStatus.TERMINATE;
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Prints stack.
	 * 
	 * @param stack Stack to be printed.
	 * @param env   Shell environment.
	 */
	private void printStack(Stack<Path> stack, Environment env) {
		if (stack.isEmpty()) {
			return;
		}

		Path path = stack.pop();

		env.writeln(path.toString());

		printStack(stack, env);

		stack.push(path);
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
		return "listd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();

		commandDescription.add("This command prints to terminal all the paths");
		commandDescription.add("existing in stack starting from last added.");
		commandDescription.add("Command expects no arguments.");

		commandDescription = Collections.unmodifiableList(commandDescription);

		return commandDescription;
	}

}
