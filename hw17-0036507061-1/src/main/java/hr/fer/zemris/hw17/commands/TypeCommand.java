package hr.fer.zemris.hw17.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.hw17.shell.Environment;
import hr.fer.zemris.hw17.shell.ShellIOException;
import hr.fer.zemris.hw17.shell.ShellStatus;

/**
 * Command that prints selected article. This command expects exactly one
 * argument: index of article. This index must be in range 0-9 because query
 * command prints only ten best matching articles.
 * 
 * @author David
 *
 */
public class TypeCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = arguments.trim().split("\\s+");

		if (arguments.trim().isEmpty() || args.length > 1) {
			return writeErrorMessage(env, 0);
		}

		int index = -1;
		try {
			index = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			return writeErrorMessage(env, 2);
		}

		if (index < 0 || index > 9) {
			return writeErrorMessage(env, 3);
		}

		List<String> result = env.getResultOfPreviousCommand();

		if (result == null) {
			return writeErrorMessage(env, 1);
		}

		env.setResultOfPreviousCommand(null);
		
		return printArticle(result.get(index), env);
	}

	/**
	 * Prints article to the shell.
	 * 
	 * @param path Path to the article.
	 * @param env  Shell argument.
	 * @return ShellStatus.CONTINUE if writing to the shell executes successfully,
	 *         otherwise returns false.
	 */
	private ShellStatus printArticle(String path, Environment env) {
		List<String> lines = null;

		Path file = Paths.get(path);
		try {
			lines = Files.readAllLines(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			for (String line : lines) {
				env.writeln(line);
			}
		} catch (ShellIOException e) {
			return ShellStatus.TERMINATE;
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Method that writes the error message to the console.
	 */
	private ShellStatus writeErrorMessage(Environment env, int errorCode) {
		try {
			if (errorCode == 0) {
				env.writeln("Invalid number of arguments.");
			} else if (errorCode == 1) {
				env.writeln("Previously executed command should be 'query'.");
			} else if (errorCode == 2) {
				env.writeln("Given argument must be integer.");
			} else if (errorCode == 3) {
				env.writeln("Given argument must be in range [0, 9].");
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
		return "type";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();

		commandDescription.add("Prints the selected articles. Expects exactly one argument: ");
		commandDescription.add(" index of article. This command has to be executed right after ");
		commandDescription.add(" query command.");

		commandDescription = Collections.unmodifiableList(commandDescription);

		return commandDescription;
	}

}
