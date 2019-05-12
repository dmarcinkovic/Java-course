package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents charsets command. Charsets command is used to print all the
 * available charsets.
 * 
 * @author david
 *
 */
public class CharsetsShellCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.trim().isEmpty()) {
			return writeErrorMessage(env, 0);
		}

		return printCharsets(env);
	}

	/**
	 * Prints all available charsets to the console.
	 * 
	 * @param env Shell environment.
	 * @return ShellStatus. If writing to the shell is executed successfully
	 *         ShellStatus.CONTINUE is returned. Otherwise ShellStatus.TERMINATE is
	 *         returned.
	 */
	private ShellStatus printCharsets(Environment env) {
		Set<Entry<String, Charset>> c = Charset.availableCharsets().entrySet();

		for (Entry<String, Charset> i : c) {
			if (printToTheConsole(env, i.getKey()).equals(ShellStatus.TERMINATE)) {
				return ShellStatus.TERMINATE;
			}
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method that writes text to the console.
	 * 
	 * @param env  Shell environment.
	 * @param text Text to be written to the console.
	 * @return ShellStatus. If writing to the shell is executed successfully
	 *         ShellStatus.CONTINUE is returned. Otherwise ShellStatus.TERMINATE is
	 *         returned.
	 */
	private ShellStatus printToTheConsole(Environment env, String text) {
		try {
			env.writeln(text);
			return ShellStatus.CONTINUE;
		} catch (ShellIOException e) {
			return ShellStatus.TERMINATE;
		}
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
		return "charsets";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();

		commandDescription.add("Command charsets prints all available charsets.");

		commandDescription = Collections.unmodifiableList(commandDescription);

		return commandDescription;
	}

}
