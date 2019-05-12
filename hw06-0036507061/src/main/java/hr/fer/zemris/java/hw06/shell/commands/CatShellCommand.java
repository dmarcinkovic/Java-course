package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
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
 * Represents cat command. This command prints the content of the file specified
 * as argument.
 * 
 * @author david
 *
 */
public class CatShellCommand extends ShellCommandUtil implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		// Read arguments.
		String[] allArguments = getArguments(arguments);

		if (allArguments == null) {
			return writeErrorMessage(env, 1);
		}

		if (allArguments.length != 1 && allArguments.length != 2) {
			return writeErrorMessage(env, 0);
		}

		// Read from file.
		List<String> fileContent = null;

		if (allArguments.length == 1) {
			fileContent = getFileContent(allArguments[0]);
		} else {
			fileContent = getFileContent(allArguments[0], allArguments[1]);
		}

		if (fileContent == null) {
			return writeErrorMessage(env, 1);
		}

		// Write to the console.
		return printContentOfTheFile(env, fileContent);
	}
	
	/**
	 * Method that prints the content of the file to the console.
	 * 
	 * @param env     Environment of the shell.
	 * @param content List representing the content of the file.
	 * @return ShellStatus. ShellStatus.CONTINUE will be returned if the writing to
	 *         the console executes successfully, otherwise ShellStatus.TERMINATE
	 *         will be returned.
	 */
	private ShellStatus printContentOfTheFile(Environment env, List<String> content) {
		try {
			content.forEach(t -> env.writeln(t));
			return ShellStatus.CONTINUE;
		} catch (ShellIOException e) {
			return ShellStatus.TERMINATE;
		}
	}

	/**
	 * Method that reads the content from the file and returns it as a list of
	 * strings. Null will be returned if filename is not valid name of the file on
	 * the disk, or charsetName is not valid name of the charset.
	 * 
	 * @param filename    String representing name of the file to read from.
	 * @param charsetName Name of the charset.
	 * @return List of strings representing the content of the file.
	 */
	private List<String> getFileContent(String filename, String charsetName) {
		Charset c = null;
		try {
			c = Charset.forName(charsetName);
		} catch (Exception e) {
			return null;
		}

		Path file = null;

		try {
			file = Paths.get(filename);
		} catch (InvalidPathException e) {
			return null;
		}

		return readFromFile(file, c);
	}

	/**
	 * Method that reads the content from the file and returns it as a list of
	 * strings. Null will be returned if filename is not valid name of the file on
	 * the disk.
	 * 
	 * @param filename String representing name of the file to read from.
	 * @return List of strings representing the content of the file.
	 */
	private List<String> getFileContent(String filename) {
		return getFileContent(filename, "UTF-8");
	}

	/**
	 * Method that reads the content of the file and returns it as a list of
	 * strings.
	 * 
	 * @param file File to read from.
	 * @param c    Specified charset.
	 * @return List of strings representing the file content.
	 */
	private List<String> readFromFile(Path file, Charset c) {
		try {
			List<String> lines = Files.readAllLines(file, c);
			return lines;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Method that writes the error message to the console when unable to execute
	 * the command.
	 * 
	 * @param errorCode the error code.
	 * @param env       Environment of the shell.
	 * @return ShellStatus. If writing to the shell executes successfully
	 *         ShellStatus.CONTINUE will be returns, otherwise ShellStatus.TERMINATE
	 *         will be returned.
	 */
	private ShellStatus writeErrorMessage(Environment env, int errorCode) {
		try {
			switch (errorCode) {
			case 0:
				env.writeln("Wrong number of arguments.");
				break;
			case 1:
				env.writeln("Could not read from file.");
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
		return "cat";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();

		commandDescription.add("Print the content of the given file.");

		commandDescription = Collections.unmodifiableList(commandDescription);

		return commandDescription;
	}

}
