package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.commands.parser.NameBuilder;
import hr.fer.zemris.java.hw06.shell.commands.parser.NameBuilderParser;

/**
 * 
 * This command is used for mass renaming / moving files.
 * 
 * @author david
 *
 */
public class MassrenameShellCommand extends ShellCommandUtil implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] allArguments = getArguments(arguments);

		if (allArguments.length != 4 && allArguments.length != 5) {
			return writeErrorMessage(env, 0);
		}

		if (allArguments[2].equals("filter") || allArguments[2].equals("groups")) {
			return filterOrGroup(env, allArguments, allArguments[2]);
		} else if (allArguments[2].equals("show") || allArguments[2].equals("execute")) {
			return showOrExecute(env, allArguments, allArguments[2]);
		}

		return writeErrorMessage(env, 1);
	}

	/**
	 * Implements show sub command.
	 * 
	 * @param env          Shell environment.
	 * @param allArguments Arguments provided in shell.
	 * @return If everything executes successfully this method will return
	 *         ShellStatus.CONTINUE, otherwise will return ShellStatus.TERMINATE.
	 */
	private ShellStatus showOrExecute(Environment env, String[] allArguments, String command) {
		if (allArguments.length != 5) {
			return writeErrorMessage(env, 0);
		}

		Path dir1 = getDirectory(allArguments[0], env);
		Path dir2 = getDirectory(allArguments[1], env);

		if (dir1 == null || dir2 == null) {
			return writeErrorMessage(env, 3);
		}

		return showOrExecute(env, dir1, dir2, allArguments[3], allArguments[4], command);
	}

	/**
	 * Implementation of execute sub command. This method move or rename files.
	 * 
	 * @param env         Shell environment.
	 * @param source      Source directory.
	 * @param destination Destination directory.
	 * @param mask        Mask to filter the source directory files.
	 * @param expression  Expression to create new names for the filtered files.
	 * @param commnad     String that tell which sub command has to executed.
	 * @return ShellStatus. If everything executes successfully this method will
	 *         return ShellStatus.CONTINUE, otherwise will return
	 *         ShellStatus.TERMINATE.
	 */
	private ShellStatus showOrExecute(Environment env, Path source, Path destination, String mask, String expression,
			String command) {
		List<FilterResult> files = null;

		try {
			files = filter(source, mask);
		} catch (IOException e) {
			return writeErrorMessage(env, 4);
		}

		if (files == null) {
			return writeErrorMessage(env, 5);
		}

		NameBuilderParser parser = new NameBuilderParser(expression);
		NameBuilder builder = parser.getNameBuilder();

		if (builder == null) {
			return writeErrorMessage(env, 6);
		}

		for (FilterResult file : files) {
			StringBuilder sb = new StringBuilder();

			builder.execute(file, sb);

			String novoIme = sb.toString();

			if (command.equals("show")) {
				show(env, file.toString(), novoIme);
			} else {
				execute(env, source, destination, file.toString(), novoIme);
			}
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Method prints selected names are new names in form "selectedName => newName"
	 * .
	 * 
	 * @param env         Shell environment.
	 * @param currentName Selected name.
	 * @param newName     new name.
	 */
	private void show(Environment env, String currentName, String newName) {
		env.write(currentName);
		env.write(" => ");
		env.writeln(newName);
	}

	/**
	 * Method renames/moves selected files from source directory to destination
	 * directory.
	 * 
	 * @param env        Shell environment.
	 * @param source     Source directory.
	 * @param desination Destination directory.
	 * @param oldName    Current name of the files.
	 * @param newName    New name of the file.
	 */
	private void execute(Environment env, Path source, Path desination, String oldName, String newName) {
		try {
			Path p1 = Paths.get(source.toString() + File.separator + oldName);
			Path p2 = Paths.get(desination.toString() + File.separator + newName);
			Files.move(p1, p2);
			show(env, p1.toString(), p2.toString());
		} catch (IOException e) {
			writeErrorMessage(env, 7);
		}
		return;
	}

	/**
	 * Implements filter or group sub command.
	 * 
	 * @param env          Shell environment.
	 * @param allArguments Arguments provided in shell.
	 * @param command      String that tells what sub command will be executed.
	 * @return ShellStatus. If everything executes successfully this method will
	 *         return ShellStatus.CONTINUE, otherwise will return
	 *         ShellStatus.TERMINATE.
	 */
	private ShellStatus filterOrGroup(Environment env, String[] allArguments, String command) {
		if (allArguments.length != 4) {
			return writeErrorMessage(env, 0);
		}

		Path dir1 = getDirectory(allArguments[0], env);
		Path dir2 = getDirectory(allArguments[1], env);

		if (dir1 == null || dir2 == null) {
			return writeErrorMessage(env, 3);
		}

		if (command.equals("filter")) {
			return listFiles(allArguments[3], env, dir1);
		}

		return group(allArguments[3], env, dir1);
	}

	/**
	 * Method that prints groups of given regular expression.
	 * 
	 * @param mask Given mask.
	 * @param env  Shell environment.
	 * @param dir  Directory to be listed.
	 * @return ShellStatus. If everything executes successfully this method will
	 *         return ShellStatus.CONTINUE, otherwise will return
	 *         ShellStatus.TERMINATE.
	 */
	private ShellStatus group(String mask, Environment env, Path dir) {
		List<FilterResult> list = null;

		try {
			list = filter(dir, mask);
		} catch (IOException e) {
			return writeErrorMessage(env, 4);
		}

		if (list == null) {
			return writeErrorMessage(env, 5);
		}

		try {
			for (FilterResult f : list) {
				printGroups(env, f);
			}
		} catch (ShellIOException e) {
			return ShellStatus.TERMINATE;
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Method to print groups to the console.
	 * 
	 * @param env Shell environment.
	 * @param f   FilterResult.
	 */
	private void printGroups(Environment env, FilterResult f) {
		env.write(f.toString());
		env.write(" ");

		for (int i = 0, n = f.numberOfGroups(); i <= n; i++) {
			env.write(Integer.toString(i));
			env.write(": ");

			env.write(f.group(i));
			env.write(" ");
		}
		env.writeln(" ");
	}

	/**
	 * Method that list all the files from given directory which names matches given
	 * mask.
	 * 
	 * @param mask Given mask.
	 * @param env  Shell environment.
	 * @param dir  Directory to be listed.
	 * @return ShellStatus. ShellStatus.CONTINUE will be returned if listing all the
	 *         files to console executes successfully.
	 */
	private ShellStatus listFiles(String mask, Environment env, Path dir) {
		List<FilterResult> list = null;

		try {
			list = filter(dir, mask);
		} catch (IOException e) {
			return writeErrorMessage(env, 4);
		}

		if (list == null) {
			return writeErrorMessage(env, 5);
		}

		try {
			list.forEach(t -> env.writeln(t.toString()));
		} catch (ShellIOException e) {
			return ShellStatus.TERMINATE;
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Returns full path of directory from given string.
	 * 
	 * @param dir         Given String.
	 * @param environment Shell environment.
	 * @return Full path to given directory, or null if given string does not
	 *         represent directory.
	 */
	private Path getDirectory(String dir, Environment environment) {
		Path directory = null;
		try {
			directory = Paths.get(dir);

			directory = environment.getCurrentDirectory().resolve(directory);
		} catch (InvalidPathException e) {
			return null;
		}

		if (Files.isDirectory(directory)) {
			return directory;
		}
		return directory;
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
				env.writeln("Wrong number of arguments.");
				break;
			case 1:
				env.writeln("Wrong subcommand.");
				break;
			case 2:
				env.writeln("Invalid path.");
				break;
			case 3:
				env.writeln("Argument does not represent directory.");
				break;
			case 4:
				env.writeln("Could read from directory.");
				break;
			case 5:
				env.writeln("Wrong regex.");
				break;
			case 6:
				env.writeln("Wrong grouping in expression.");
				break;
			case 7:
				env.writeln("Could not move/rename file.");
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
		return "massrename";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();

		commandDescription.add("This command is used for mass renaming / moving files");
		commandDescription.add("Command expects 4 or 5 arguments.");
		commandDescription.add("First argument is source directory. It must be directory.");
		commandDescription.add("Second argument is destination directory. It must be directory.");
		commandDescription.add("Third argument is command to be executed. It may be : filter, groups");
		commandDescription.add("show or execute.");
		commandDescription.add("Filter and groups take 4 arguments.");
		commandDescription.add("Show and execute take 5 arguments.");

		commandDescription = Collections.unmodifiableList(commandDescription);

		return commandDescription;
	}

	/**
	 * Runs through given directory and returns the list of type FilterResult of all
	 * files in current directory which names matches given pattern.
	 * 
	 * @param dir     Given directory.
	 * @param pattern Patter to match.
	 * @return List of FilterResult.
	 * @throws IOException if could not list all the files from given directory.
	 */
	private List<FilterResult> filter(Path dir, String pattern) throws IOException {
		DirectoryStream<Path> stream = Files.newDirectoryStream(dir);

		List<FilterResult> list = new ArrayList<>();

		Pattern p = null;

		try {
			p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE & Pattern.UNICODE_CASE);
		} catch (PatternSyntaxException e) {
			return null;
		}

		for (Path file : stream) {
			Matcher m = p.matcher(file.getFileName().toString());
			if (m.matches()) {
				list.add(new FilterResult(file, pattern));
			}
		}
		return list;
	}
}
