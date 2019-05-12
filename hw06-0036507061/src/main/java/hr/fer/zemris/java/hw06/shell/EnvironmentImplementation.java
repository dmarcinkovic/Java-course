package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

/**
 * Class represents environment implementation of the Environment interface.
 * 
 * @author david
 *
 */
public class EnvironmentImplementation implements Environment {
	private char multilineSymbol;
	private char promptSymbol;
	private char morelineSymbol;
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
		map.put("cat", new CatShellCommand());
		map.put("charsets", new CharsetsShellCommand());
		map.put("copy", new CopyShellCommand());
		map.put("exit", new ExitShellCommand());
		map.put("help", new HelpShellCommand());
		map.put("hexdump", new HexdumpShellCommand());
		map.put("ls", new LsShellCommand());
		map.put("mkdir", new MkdirShellCommand());
		map.put("symbol", new SymbolShellCommand());
		map.put("tree", new TreeShellCommand());
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
	public void setMultilineSymbol(Character symbol) {
		multilineSymbol = symbol;
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
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character getMorelinesSymbol() {
		return morelineSymbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMorelinesSymbol(Character symbol) {
		morelineSymbol = symbol;
	}

}
