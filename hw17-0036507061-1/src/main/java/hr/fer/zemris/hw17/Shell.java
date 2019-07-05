package hr.fer.zemris.hw17;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.hw17.commands.ShellCommand;
import hr.fer.zemris.hw17.shell.Environment;
import hr.fer.zemris.hw17.shell.EnvironmentImplementation;
import hr.fer.zemris.hw17.shell.ShellIOException;
import hr.fer.zemris.hw17.shell.ShellStatus;

/**
 * Program that represents shell.Available commands are : exit command, results
 * command, type command and query command.
 * 
 * @author David
 *
 */
public class Shell {

	/**
	 * Directory in which all articles are.
	 */
	private static Path dir;
	
	/**
	 * Method invoked when running the program. Method represents shell that can be
	 * used to search text documents.
	 * 
	 * @param args Arguments provided via command line. This program expects path to
	 *             directory that contains articles.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Wrong number of arguments.");
			return;
		}
		
		try {
			dir = Paths.get(args[0]);
		}catch(InvalidPathException e) {
			System.out.println("Invalid path");
			return;
		}
		
		if (!Files.exists(dir) || !Files.isDirectory(dir) || !Files.isReadable(dir)) {
			System.out.println("Error");
			return;
		}

		Environment env = buildEnvironment();

		ShellStatus status = ShellStatus.CONTINUE;

		do {
			env.write(env.getPromptSymbol() + " ");
			String line = env.readLine();

			String commandName = getCommandName(line).trim();
			String arguments = getArguments(line);

			ShellCommand command = env.commands().get(commandName);

			if (command == null) {
				status = writeErrorMessage("Unrecognized command", env);
			} else {
				status = command.executeCommand(env, arguments);
			}

		} while (!status.equals(ShellStatus.TERMINATE));
	}

	/**
	 * Method that writes error message to console.
	 * 
	 * @param message Message to be written to the console.
	 * @param env     Shell environment.
	 * @return ShellStaus. If writing to the shell executes successfully returns
	 *         ShellStatus.CONTINUE.
	 */
	private static ShellStatus writeErrorMessage(String message, Environment env) {
		try {
			env.writeln(message);
		} catch (ShellIOException e) {
			return ShellStatus.TERMINATE;
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method to extract arguments from input.
	 * 
	 * @param arguments String to extract the arguments.
	 * @return arguments.
	 */
	private static String getArguments(String arguments) {
		int index = getIndexOfFirstWhitespace(arguments);

		if (index == -1) {
			return "";
		}

		return arguments.substring(index);
	}

	/**
	 * Method to extract command name from input.
	 * 
	 * @param commandName String to extract the command name.
	 * @return command name.
	 */
	private static String getCommandName(String commandName) {
		int index = getIndexOfFirstWhitespace(commandName);

		if (index == -1) {
			return commandName;
		}

		return commandName.substring(0, index);
	}

	/**
	 * Returns index of first appearing whitespace.
	 * 
	 * @param string String to search for first appearing whitespace.
	 * @return Index of first appearing whitespace.
	 */
	private static int getIndexOfFirstWhitespace(String string) {
		int index = -1;
		for (int i = 0; i < string.length(); i++) {
			if (Character.isWhitespace(string.charAt(i))) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * Method to build environment.
	 * 
	 * @return built environment.
	 */
	private static Environment buildEnvironment() {
		return new EnvironmentImplementation(dir.toString(), "src/main/resources/hrvatski_stoprijeci.txt");
	}
}
