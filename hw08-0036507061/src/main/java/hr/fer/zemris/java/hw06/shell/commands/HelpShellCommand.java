package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Help command. This command is used to print information about the specified
 * command.
 * 
 * @author david
 *
 */
public class HelpShellCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] allArguments = arguments.trim().split("\\s+");
		
		if (allArguments.length > 1) {
			return writeErrorMessage(env, 0);
		}
		
		if (arguments.trim().isEmpty()) {
			return printAllCommands(env);
		}

		return commandInfo(env, allArguments[0]);
	}

	/**
	 * Method that prints information about the given command.
	 * 
	 * @param env        Shell environment.
	 * @param comandName Name of the command.
	 * @return ShellStatus.
	 */
	private ShellStatus commandInfo(Environment env, String comandName) {
		ShellCommand shellCommand = env.commands().get(comandName);
		
		if (shellCommand == null) {
			return writeErrorMessage(env, 1);
		}
		
		return writeHelpInstructions(env, shellCommand.getCommandDescription());
	}

	/**
	 * Method that writes help instructions to the console.
	 * 
	 * @param env         Shell environment.
	 * @param description Description of the command.
	 * @return ShellStatus. ShellStatus.CONTINUE will be returned if writing to the
	 *         shell executes successfully.
	 */
	private ShellStatus writeHelpInstructions(Environment env, List<String> description) {
		try {
			for (String s : description) {
				env.writeln(s);
			}
		} catch (ShellIOException e) {
			return ShellStatus.TERMINATE;
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method that prints all the command available in this shell. This is printed
	 * when user does not provide any argument with help command.
	 * 
	 * @param env Shell environment.
	 * @return ShellStatus. If writing to the shell executes successfully
	 *         ShellStatus.CONTINUE will be returned.
	 */
	private ShellStatus printAllCommands(Environment env) {
		Set<String> commands = env.commands().keySet();

		try {
			env.writeln("All supported commands are:");
			
			for (String s : commands) {
				env.writeln(s);
			}
		} catch (ShellIOException e) {
			return ShellStatus.TERMINATE;
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method that writes error message to the console.
	 * 
	 * @param env       Shell environment.
	 * @param errorCode error code.
	 * @return ShellStatus. If writing to the shell executes successfully
	 *         ShellStatus.CONTINUE will be returned.
	 */
	private ShellStatus writeErrorMessage(Environment env, int errorCode) {
		try {
			switch (errorCode) {
			case 0:
				env.writeln("Wrong number of arguments.");
				break;
			case 1:
				env.writeln("Command does not exist");
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
		return "help";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();

		commandDescription.add("Prints information about the specified command.");
		commandDescription.add("If no argument is provided, all available commands ");
		commandDescription.add("will be listed. Command expects one argument: the name of ");
		commandDescription.add("the command to be giving help.");

		commandDescription = Collections.unmodifiableList(commandDescription);

		return commandDescription;
	}

}
