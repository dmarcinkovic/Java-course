package hr.fer.zemris.hw17.shell;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.hw17.article.Article;
import hr.fer.zemris.hw17.commands.ExitCommand;
import hr.fer.zemris.hw17.commands.HelpCommand;
import hr.fer.zemris.hw17.commands.QueryCommand;
import hr.fer.zemris.hw17.commands.ResultsCommand;
import hr.fer.zemris.hw17.commands.ShellCommand;
import hr.fer.zemris.hw17.commands.TypeCommand;
import hr.fer.zemris.hw17.vocabulary.Vocabulary;

/**
 * Class represents environment implementation of the Environment interface.
 * 
 * @author David
 *
 */
public class EnvironmentImplementation implements Environment {

	/**
	 * Reference to vocabulary.
	 */
	private Vocabulary vocabulary;

	/**
	 * Map that associates path name of article with the Article object.
	 */
	private Map<String, Article> articles;

	/**
	 * List that represents the result of previously executed command.
	 */
	private List<String> commands;

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
	 * Name of previously executed command.
	 */
	private String name;
	
	/**
	 * Constructor to initialize private members.
	 */
	public EnvironmentImplementation(String articlesPath, String stopWordsPath) {
		multilineSymbol = '|';
		promptSymbol = '>';
		morelineSymbol = '\\';
		scan = new Scanner(System.in);
		articles = new HashMap<>();
		vocabulary = new Vocabulary(articlesPath, stopWordsPath);
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
		map.put("help", new HelpCommand());
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

	/**
	 * {@index}
	 */
	@Override
	public void setResultOfPreviousCommand(List<String> commands) {
		this.commands = commands;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getResultOfPreviousCommand() {
		return commands;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Article getDocument(String path) {
		if (articles.get(path) == null) {
			articles.put(path, new Article(Paths.get(path), vocabulary, vocabulary.getNumberOfArticles()));
		}

		return articles.get(path);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vocabulary getVocabulay() {
		return vocabulary;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPreviousCommand() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPreviousCommand(String name) {
		this.name = name;
	}

}
