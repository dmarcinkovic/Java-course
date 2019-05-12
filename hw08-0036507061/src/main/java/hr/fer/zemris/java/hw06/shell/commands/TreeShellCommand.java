package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class represents tree command. Tree command takes one argument: path to
 * directory to be recursively listed. This command is similar to tree command
 * in linux terminal.
 * 
 * @author david
 *
 */
public class TreeShellCommand extends ShellCommandUtil implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] allArguments = getArguments(arguments);

		if (allArguments.length != 1 || arguments.isEmpty()) {
			return writeErrorMessage(env, 0);
		}

		return tree(env, allArguments[0]);
	}
	
	/**
	 * Prints the tree structure of given directory.
	 * 
	 * @param env           Shell environment.
	 * @param directoryName Name of the directory.
	 * @return ShellStatus.
	 */
	private ShellStatus tree(Environment env, String directoryName) {
		Path directory = null;

		try {
			directory = Paths.get(directoryName);
			
			directory = env.getCurrentDirectory().resolve(directory);
		} catch (InvalidPathException e) {
			return writeErrorMessage(env, 1);
		}

		if (!Files.isDirectory(directory)) {
			return writeErrorMessage(env, 2);
		} else if (!Files.exists(directory)) {
			return writeErrorMessage(env, 1);
		}

		return printTreeStructure(env, directory);
	}

	/**
	 * Prints the tree structure of given directory.
	 * 
	 * @param env           Shell environment.
	 * @param directoryName Name of the directory.
	 * @return ShellStatus.
	 */
	private ShellStatus printTreeStructure(Environment env, Path directory) {
		MyFileVisitor visitor = new MyFileVisitor();

		try {
			Files.walkFileTree(directory, visitor);
		} catch (IOException e) {
			return writeErrorMessage(env, 3);
		}

		String tree = visitor.getTree();

		return writeMessageToConsole(env, tree);
	}

	/**
	 * Method that writes the message to console.
	 * 
	 * @param env     Shell environment.
	 * @param message Message to be written to the console.
	 * @return ShellStatus.
	 */
	private ShellStatus writeMessageToConsole(Environment env, String message) {
		try {
			env.write(message);
		} catch (ShellIOException e) {
			return ShellStatus.TERMINATE;
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Implementation of FileVisitor class that is used to print the tree structure.
	 * 
	 * @author david
	 *
	 */
	private static class MyFileVisitor extends SimpleFileVisitor<Path> {
		private int level = 0;
		private StringBuilder sb = new StringBuilder();

		/**
		 * Method that return tree structure.
		 * 
		 * @return Tree structure.
		 */
		public String getTree() {
			return sb.toString();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			sb.append(" ".repeat(2 * level)).append(dir.getFileName()).append("\n");
			level++;
			return FileVisitResult.CONTINUE;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			sb.append(" ".repeat(2 * level)).append(file.getFileName()).append("\n");
			return FileVisitResult.CONTINUE;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}
	}

	/**
	 * Method that writes the error message to the console.
	 * 
	 * @param env       Shell environment.
	 * @param errorCode Error code.
	 * @return ShellStatus. If writing to the shell is executed successfully this
	 *         method will return ShellStatus.CONTINUE, otherwise it returns
	 *         ShellStatus.TERMINATE.
	 */
	private ShellStatus writeErrorMessage(Environment env, int errorCode) {
		try {
			switch (errorCode) {
			case 0:
				env.writeln("Wrong number of arguments.");
				break;
			case 1:
				env.writeln("Directory does not exist.");
				break;
			case 2:
				env.writeln("Given path is not directory.");
				break;
			case 3:
				env.writeln("Could not list tree structure.");
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
		return "tree";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();

		commandDescription.add("Prints recursively specified directory.");
		commandDescription.add("Command expects only one argument: path to ");
		commandDescription.add("directory to be recursively printed.");

		commandDescription = Collections.unmodifiableList(commandDescription);

		return commandDescription;
	}

}
