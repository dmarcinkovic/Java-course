package hr.fer.zemris.hw17.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.hw17.commands.ExitCommand;
import hr.fer.zemris.hw17.commands.QueryCommand;
import hr.fer.zemris.hw17.commands.ResultsCommand;
import hr.fer.zemris.hw17.commands.ShellCommand;
import hr.fer.zemris.hw17.commands.TypeCommand;

/**
 * Class represents environment implementation of the Environment interface.
 * 
 * @author David
 *
 */
public class EnvironmentImplementation implements Environment {

	/**
	 * Multiline symbol. It is shown when user write command in two or more lines.
	 */
	private char multilineSymbol;

	/**
	 * Prompt symbol. It is shown in the same line user writes command. By default
	 * it is '>'.
	 */
	private char promptSymbol;

	/**
	 * Moreline symbol. It is escaping character that allows user to write command
	 * in more than one line.
	 */
	private char morelineSymbol;

	/**
	 * Scanner used to read user's input.
	 */
	private Scanner scan;

	/**
	 * Constructor to initialize private members.
	 */
	public EnvironmentImplementation() {
		multilineSymbol = '|';
		promptSymbol = '>';
		morelineSymbol = '\\';
		scan = new Scanner(System.in);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String readLine() throws ShellIOException {
		StringBuilder sb = new StringBuilder();

		try {
			String line = scan.nextLine();

			line = line.trim();

			while (line.endsWith(" " + String.valueOf(morelineSymbol))
					|| (line.length() == 1 && line.charAt(0) == morelineSymbol)) {
				sb.append(line.subSequence(0, line.length() - 1));

				System.out.print(multilineSymbol + " ");

				line = scan.nextLine();
				line = line.trim();
			}

			sb.append(line);
		} catch (Exception e) {
			System.out.println(e);
			throw new ShellIOException();
		}

		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SortedMap<String, ShellCommand> commands() {
		SortedMap<String, ShellCommand> map = new TreeMap<>();

		initializeCommands(map);

		map = Collections.unmodifiableSortedMap(map);
		return map;
	}

	/**
	 * Method to initialize commands.
	 * 
	 * @param map Map to initialize.
	 */
	private void initializeCommands(SortedMap<String, ShellCommand> map) {
		map.put("query", new QueryCommand());
		map.put("exit", new ExitCommand());
		map.put("type", new TypeCommand());
		map.put("results", new ResultsCommand());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character getMorelinesSymbol() {
		return morelineSymbol;
	}

}
