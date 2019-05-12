package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents symbol command. Symbol command is used to print or change prompt
 * or morelines symbol.
 * 
 * @author david
 *
 */
public class SymbolShellCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] allArguments = arguments.trim().split("\\s+");

		if (allArguments.length > 2) {
			return writeErrorMessage(env, 0);
		}

		if (allArguments.length == 1) {
			return printSymbol(env, allArguments[0]);
		}

		return changeSymbol(env, allArguments[0], allArguments[1]);
	}

	/**
	 * Method to change prompt or morelines symbol.
	 * 
	 * @param env       Shell environment.
	 * @param symbol    Symbol.
	 * @param newSymbol new symbol.
	 * @return ShellStatus.
	 */
	private ShellStatus changeSymbol(Environment env, String symbol, String newSymbol) {
		try {
			if (symbol.equals("PROMPT")) {
				env.writeln("Symbol for PROMPT changed from '" + String.valueOf(env.getPromptSymbol()) + "' to '"
						+ String.valueOf(newSymbol.charAt(0)) + "'");
				env.setPromptSymbol(newSymbol.charAt(0));
			} else if (symbol.equals("MORELINES")) {
				env.writeln("Symbol for MORELINES changed from '" + String.valueOf(env.getMorelinesSymbol()) + "' to '"
						+ String.valueOf(newSymbol.charAt(0)) + "'");
				env.setMorelinesSymbol(newSymbol.charAt(0));
			} else if (symbol.equals("MULTILINE")) {
				env.writeln("Symbol for MULTILINE changed from '" + String.valueOf(env.getMultilineSymbol()) + "' to '"
						+ String.valueOf(newSymbol.charAt(0)) + "'");
				env.setMultilineSymbol(newSymbol.charAt(0));
			} else {
				return writeErrorMessage(env, 1);
			}
		} catch (ShellIOException e) {
			return ShellStatus.TERMINATE;
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Prints symbol to the console.
	 * 
	 * @param env    Shell environment.
	 * @param symbol Symbol to be printed to the console.
	 * @return ShellStatus.
	 */
	private ShellStatus printSymbol(Environment env, String symbol) {
		try {
			if (symbol.equals("PROMPT")) {
				env.writeln("Symbol for PROMPT is '" + String.valueOf(env.getPromptSymbol()) + "'");
			} else if (symbol.equals("MORELINES")) {
				env.writeln("Symbol for MORELINES is '" + String.valueOf(env.getMorelinesSymbol()) + "'");
			} else if (symbol.equals("MULTILINE")) {
				env.writeln("Symbol for MULTILINE is '" + String.valueOf(env.getMultilineSymbol()) + "'");
			} else {
				return writeErrorMessage(env, 1);
			}
		} catch (ShellIOException e) {
			return ShellStatus.TERMINATE;
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method that prints error message to the console.
	 * 
	 * @param env       Shell environment.
	 * @param errorCode error code.
	 * @return ShellStatus. ShellStatus.CONTINUE will be returned if writing to the
	 *         console executes successfully.
	 */
	private ShellStatus writeErrorMessage(Environment env, int errorCode) {
		try {
			switch (errorCode) {
			case 0:
				env.writeln("Wrong number of arguments.");
				break;
			case 1:
				env.writeln("Wrong argument.");
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
		return "symbol";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();

		commandDescription.add("Command to change or print prompt or morelines symbol.");

		commandDescription = Collections.unmodifiableList(commandDescription);

		return commandDescription;
	}

}
