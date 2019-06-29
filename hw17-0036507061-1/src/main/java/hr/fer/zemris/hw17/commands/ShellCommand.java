package hr.fer.zemris.hw17.commands;

import java.util.List;

import hr.fer.zemris.hw17.shell.Environment;
import hr.fer.zemris.hw17.shell.ShellStatus;


/**
 * Program that represents one shell command. Command has its name and its
 * description.
 * 
 * @author David
 *
 */
public interface ShellCommand {

	/**
	 * Executes given command with given arguments. Method returns status after the
	 * executing this command. Status of the shell can be CONTINUE or TERMINATE. If
	 * commands executes successfully status will be CONTINUE. Otherwise status will
	 * be TERMINATE.
	 * 
	 * @param env       Environment of the shell.
	 * @param arguments Arguments provided by the user for the given command.
	 * @return Shell status after executing given command.
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Returns the name of the command.
	 * 
	 * @return Command name.
	 */
	String getCommandName();

	/**
	 * Returns description of the command. Description can be seen using help
	 * command.
	 * 
	 * @return Description of the command.
	 */
	List<String> getCommandDescription();
}
