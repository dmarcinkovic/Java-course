package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.DropdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ListdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MassrenameShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PopdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PushdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PwdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

/**
 * Class represents environment implementation of the Environment interface.
 * 
 * @author david
 *
 */
public class EnvironmentImplementation implements Environment {
	/**
	 * Represents multiline symbol. By default multiline symbol is '|'.
	 */
	private char multilineSymbol;

	/**
	 * Represents prompt symbol. By default prompt symbol is '>'.
	 */
	private char promptSymbol;

	/**
	 * Represents moreline symbol. By default moreline symbol is '\'.
	 */
	private char morelineSymbol;

	/**
	 * Reference to scanner.
	 */
	private Scanner scan;

	/**
	 * Stores information about the current directory.
	 */
	private Path currentDirectory;

	/**
	 * Shared data.
	 */
	private Map<String, Object> sharedData;

	/**
	 * Constructor to initialize private members.
	 */
	public EnvironmentImplementation() {
		multilineSymbol = '|';
		promptSymbol = '>';
		morelineSymbol = '\\';
		scan = new Scanner(System.in);
		currentDirectory = Paths.get(".");
		sharedData = new HashMap<String, Object>();
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
		map.put("pwd", new PwdShellCommand());
		map.put("cd", new CdShellCommand());
		map.put("pushd", new PushdShellCommand());
		map.put("popd", new PopdShellCommand());
		map.put("listd", new ListdShellCommand());
		map.put("dropd", new DropdShellCommand());
		map.put("massrename", new MassrenameShellCommand());
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Path getCurrentDirectory() {
		return currentDirectory.normalize().toAbsolutePath();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalArgumentException is given directory does not exists.
	 */
	@Override
	public void setCurrentDirectory(Path path) {
		if (!Files.exists(path)) {
			throw new IllegalArgumentException("Directory does not exists.");
		}
		this.currentDirectory = path;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getSharedData(String key) {
		return sharedData.get(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSharedData(String key, Object value) {
		sharedData.put(key, value);
	}

}
