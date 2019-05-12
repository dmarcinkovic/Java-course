package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents ls command. This command is used to list the specified directory.
 * This command is similar to ls -l command in linux. It has information about
 * the permits, size of the file, file date and time creation and finally file
 * name.
 * 
 * @author david
 *
 */
public class LsShellCommand extends ShellCommandUtil implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] allArguments = getArguments(arguments);

		if (allArguments.length != 1 || arguments.isEmpty()) {
			return writeErrorMessage(1, env);
		}

		return listFiles(env, allArguments[0]);
	}

	/**
	 * Method used to list the file. It checks if file given with filename exists
	 * and checks if it is directory.
	 * 
	 * @param env      Shell environment.
	 * @param filename Name of the directory to be listed.
	 * @return ShellStatus. If everything executes correctly returned value will be
	 *         ShellStatus.CONTINUE, otherwise it will be ShellStatusTERMINATE.
	 */
	private ShellStatus listFiles(Environment env, String filename) {
		Path directory = null;

		try {
			directory = Paths.get(filename);
		} catch (InvalidPathException e) {
			return writeErrorMessage(2, env);
		}

		if (!Files.isDirectory(directory)) {
			return writeErrorMessage(3, env);
		}

		try {
			DirectoryStream<Path> stream = Files.newDirectoryStream(directory);

			for (Path p : stream) {
				if (!printInformationAboutTheFile(env, p)) {
					return writeErrorMessage(4, env);
				}
			}
		} catch (IOException e) {
			return writeErrorMessage(0, env);
		}

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
	private ShellStatus writeErrorMessage(int errorCode, Environment env) {
		try {
			switch (errorCode) {
			case 0:
				env.writeln("Could not list directory.");
				break;
			case 1:
				env.writeln("Invalid number of arguments.");
				break;
			case 2:
				env.writeln("File does not exists.");
				break;
			case 3:
				env.writeln("Given path is not directory.");
				break;
			case 4:
				env.writeln("Could not read file attributes.");
			}
		} catch (ShellIOException e) {
			return ShellStatus.TERMINATE;
		}
		return ShellStatus.CONTINUE;
	}

	

	/**
	 * Prints information about the given file to the console.
	 * 
	 * @param file given file.
	 */
	private boolean printInformationAboutTheFile(Environment env, Path file) {
		String permits = getPermits(file);
		long fileSize = 0L;

		try {
			fileSize = Files.size(file);
		} catch (IOException e) {
			return false;
		}

		String creationDate = getCreationDate(file);

		if (creationDate == null) {
			return false;
		}

		String creationTime = getCreationTime(file);

		if (creationTime == null) {
			return false;
		}

		try {
			env.writeln(String.format("%-5s %2$10d %3$10s %4$s %5$s", permits, fileSize, creationDate, creationTime,
					file.getFileName()));
		} catch (ShellIOException e) {
			return false;
		}

		return true;
	}

	/**
	 * Method used to get the creation time of the given file.
	 * 
	 * @param path given path to the file.
	 * @return String representing the creation time of the given file. It returns
	 *         null if cannot read creation time.
	 */
	private String getCreationTime(Path path) {
		BasicFileAttributes attrs = null;
		try {
			attrs = Files.readAttributes(path, BasicFileAttributes.class);
		} catch (IOException e) {
			return null;
		}

		String creationTime = attrs.creationTime().toString().substring(11, 19);
		return creationTime;
	}

	/**
	 * Method used to get the creation date of the given file
	 * 
	 * @param path given path.
	 * @return String representing the creation date of the given file. It returns
	 *         null if cannot read creation date.
	 */
	private String getCreationDate(Path path) {
		BasicFileAttributes attrs = null;
		try {
			attrs = Files.readAttributes(path, BasicFileAttributes.class);
		} catch (IOException e) {
			return null;
		}

		String creationDate = attrs.creationTime().toString().substring(0, 10);
		return creationDate;
	}

	/**
	 * Method used to get permits of the given file.
	 * 
	 * @param file given file.
	 * @return String representing the permits of the given file.
	 */
	private String getPermits(Path file) {
		StringBuilder sb = new StringBuilder();

		sb.append(getDirectoryInfo(file)).append(getReadableInfo(file)).append(getWritableInfo(file))
				.append(getExecutableInfo(file));

		return sb.toString();
	}

	/**
	 * Method returns 'd' if given path is represents directory, otherwise returns
	 * '-'.
	 * 
	 * @param file given path.
	 * @return 'd' if given path represents directory, otherwise returns '-'.
	 */
	private char getDirectoryInfo(Path file) {
		if (Files.isDirectory(file)) {
			return 'd';
		}
		return '-';
	}

	/**
	 * Method returns 'w' if given file is writable, otherwise returns '-'.
	 * 
	 * @param file given path.
	 * @return 'w' if given path is writable, otherwise returns '-'.
	 */
	private char getWritableInfo(Path file) {
		if (Files.isWritable(file)) {
			return 'w';
		}
		return '-';
	}

	/**
	 * Method returns 'r' if given file is readable, otherwise returns '-';
	 * 
	 * @param file given path.
	 * @return 'w' if given path is readable, otherwise returns '-'.
	 */
	private char getReadableInfo(Path file) {
		if (Files.isReadable(file)) {
			return 'r';
		}
		return '-';
	}

	/**
	 * Method returns 'x' if given file is executable, otherwise returns '-'.
	 * 
	 * @param file given path.
	 * @return 'x' is given path is readable, otherwise returns '-'.
	 */
	private char getExecutableInfo(Path file) {
		if (Files.isExecutable(file)) {
			return 'x';
		}
		return '-';
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "ls";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();

		commandDescription.add("List information about the files in the specified directory.");
		commandDescription.add("Sorts entries alphabetically.");
		commandDescription.add("It takes one argument: directory to be listed.");
		commandDescription.add("It writes information about the permits, size,");
		commandDescription.add("date and time of last change.");

		commandDescription = Collections.unmodifiableList(commandDescription);

		return commandDescription;
	}

}
