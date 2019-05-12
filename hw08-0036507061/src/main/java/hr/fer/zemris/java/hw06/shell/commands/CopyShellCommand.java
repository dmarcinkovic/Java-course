package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
 * Class represents copy command. Copy command copies content of one file to
 * another. It expects two arguments. First argument must be path to the file.
 * Second arguments can be path to file or directory.
 * 
 * @author david
 *
 */
public class CopyShellCommand extends ShellCommandUtil implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] allArguments = getArguments(arguments);

		if (allArguments.length != 2) {
			return writeErrorMessage(env, 0);
		}

		return copy(env, allArguments[0], allArguments[1]);
	}

	/**
	 * Method that copies content of the file1 to the file1. If file2 is directory,
	 * file1 will be copied to that directory with the original name.
	 * 
	 * @param env   Shell environment
	 * @param file1 File to be copied.
	 * @param file2 File to copy to.
	 * @return ShellStatus. If copying executes successfully returns
	 *         ShellStatus.CONTINUE, otherwise returns ShellStatus.TERMINATE.
	 */
	private ShellStatus copy(Environment env, String file1, String file2) {
		Path path1 = null;
		Path path2 = null;

		try {
			path1 = Paths.get(file1);
			path2 = Paths.get(file2);
			
			path1 = env.getCurrentDirectory().resolve(path1);
			path2 = env.getCurrentDirectory().resolve(path2);
		} catch (InvalidPathException e) {
			return writeErrorMessage(env, 1);
		}

		if (Files.isDirectory(path1)) {
			return writeErrorMessage(env, 2);
		} else if (!Files.exists(path1)) {
			return writeErrorMessage(env, 3);
		} else if (Files.isDirectory(path2)) {
			return copyFromFileToDirectory(env, path1, path2);
		}

		return copyFromFileToFile(env, path1, path2);
	}

	/**
	 * Method that copies one file to another. If destination file does not exist,
	 * user will be asked if he wants to overwrite the file.
	 * 
	 * @param env   Shell environment.
	 * @param path1 Source file.
	 * @param path2 Destination file.
	 * @return ShellStatsus.
	 */
	private ShellStatus copyFromFileToFile(Environment env, Path path1, Path path2) {

		// If source and destination file are the same, do nothing.
		if (path1.toAbsolutePath().toString().equals(path2.toAbsolutePath().toString())) {
			return ShellStatus.CONTINUE;
		}

		if (Files.exists(path2)) {
			if (writeMessegeToUser(env,
					"Do you want to overwrite the " + path2.getFileName()
							+ " ? (write 'yes' or 'no' (without quotes)) \n" + env.getPromptSymbol() + " ")
									.equals(ShellStatus.TERMINATE)) {
				return ShellStatus.TERMINATE;
			}

			String userResponse = waitForUserResponse(env);

			if (userResponse == null) {
				return ShellStatus.TERMINATE;
			} else if (userResponse.equals("no")) {
				return ShellStatus.CONTINUE;
			}

			return copyContent(path1, path2, env);
		} else {

			try {
				Files.createFile(path2);
			} catch (IOException e) {
				return writeErrorMessage(env, 1);
			}

			return copyContent(path1, path2, env);
		}
	}

	/**
	 * Method that copies content from one file to another.
	 * 
	 * @param source      Source file.
	 * @param destination Destination file.
	 * @return ShellStatus.
	 */
	private ShellStatus copyContent(Path source, Path destination, Environment env) {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(destination.toString()))) {
			List<String> lines = Files.readAllLines(source);

			for (String line : lines) {
				bw.write(line + "\n");
			}

		} catch (IOException e) {
			return writeErrorMessage(env, 1);
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method that waits for the user's response. User was asked if he wants to
	 * overwrite the existing file.
	 * 
	 * @param env Shell environment.
	 * @return ShellStatus. If reading from the console and writing to the console
	 *         finishes successfully ShellStatus.CONTINUE will be returned.
	 */
	private String waitForUserResponse(Environment env) {
		try {
			String userResponse = env.readLine();

			while (!userResponse.equals("yes") && !userResponse.equals("no")) {
				if (writeMessegeToUser(env,
						"Error. Write 'yes' or 'no' again (without quotes). \n" + env.getPromptSymbol() + " ")
								.equals(ShellStatus.TERMINATE)) {
					return null;
				}
				userResponse = env.readLine();
			}
			return userResponse;
		} catch (ShellIOException e) {
			return null;
		}
	}

	/**
	 * Method that writes message to the user.
	 * 
	 * @param env  Shell environment.
	 * @param text Message to be written to the console.
	 * @return ShellStatus. If writing executes successfully ShellStatus.CONTINUE
	 *         will be returned.
	 */
	private ShellStatus writeMessegeToUser(Environment env, String text) {
		try {
			env.write(text);
			return ShellStatus.CONTINUE;
		} catch (ShellIOException e) {
			return ShellStatus.TERMINATE;
		}
	}

	/**
	 * Method that copies the source file to the destination directory.
	 * 
	 * @param env   Shell environment.
	 * @param path1 Source file.
	 * @param path2 Destination directory.
	 * @return ShellStatus.
	 */
	private ShellStatus copyFromFileToDirectory(Environment env, Path path1, Path path2) {

		if (!Files.exists(path2)) {
			return writeErrorMessage(env, 4);
		}

		Path destinationFile = null;
		try {
			destinationFile = Paths.get(path2.toString() + "/" + path1.getFileName());
			destinationFile = env.getCurrentDirectory().resolve(destinationFile);
		} catch (InvalidPathException e) {
			return writeErrorMessage(env, 1);
		}

		// If source and destination file are the same.
		if (destinationFile.toAbsolutePath().toString().equals(path1.toAbsolutePath().toString())) {
			return ShellStatus.CONTINUE;
		}

		if (Files.exists(destinationFile)) {
			if (writeMessegeToUser(env,
					"Do you want to overwrite the " + destinationFile.getFileName()
							+ " ? (write 'yes' or 'no' (without quotes)) \n" + env.getPromptSymbol() + " ")
									.equals(ShellStatus.TERMINATE)) {
				return ShellStatus.TERMINATE;
			}

			String answer = waitForUserResponse(env);
			if (answer.equals("no")) {
				return ShellStatus.CONTINUE;
			}
		}

		return copyContent(path1, destinationFile, env);
	}

	/**
	 * Method to write error message to the console.
	 * 
	 * @param env       Shell environment.
	 * @param errorCode Error code.
	 * @return ShellStatus. If writing to the shell is executed successfully
	 *         ShellStatus.CONTINUE is returned. Otherwise ShellStatus.TERMINATE is
	 *         returned.
	 */
	private ShellStatus writeErrorMessage(Environment env, int errorCode) {
		try {
			switch (errorCode) {
			case 0:
				env.writeln("Wrong number of arguments.");
				break;
			case 1:
				env.writeln("Could not copy file.");
				break;
			case 2:
				env.writeln("First arguments must not be directory.");
				break;
			case 3:
				env.writeln("Source file does not exist.");
				break;
			case 4:
				env.writeln("Destination directory does not exist");
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
		return "copy";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();

		commandDescription.add("Command copy copies content of one file to another.");
		commandDescription.add("Copy takes two arguments: first argument is source file,");
		commandDescription.add("second argument is destination file. If second argument is ");
		commandDescription.add("directory, file provided as first argument will be copied");
		commandDescription.add("to that directory.");

		commandDescription = Collections.unmodifiableList(commandDescription);

		return commandDescription;
	}

}
