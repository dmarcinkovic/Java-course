package hr.fer.zemris.hw17.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import hr.fer.zemris.hw17.shell.Environment;
import hr.fer.zemris.hw17.shell.ShellIOException;
import hr.fer.zemris.hw17.shell.ShellStatus;
import hr.fer.zemris.hw17.vocabulary.Vocabulary;

/**
 * Prints ten best results for given query. Expects at least one argument.
 * 
 * @author David
 *
 */
public class QueryCommand implements ShellCommand {

	/**
	 * Map that contains frequency for every word.
	 */
	private Map<String, Integer> frequency;

	/**
	 * Constructor.
	 */
	public QueryCommand() {
		frequency = new HashMap<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.trim().isEmpty()) {
			return writeErrorMessage(env, 0);
		}
		
		env.setPreviousCommand("query");

		// TODO ovdje u listu zapisati rezultat te pozbati
		// env.setPreviouslyExecutedCommand.
		Set<String> words = getWordsFromInput(arguments, env);

		writeMessage(env, "Query is: " + words.toString());
		writeMessage(env, "Top 10 results: ");

		double[] vector1 = getVector(words, env);

		List<String> articles = env.getVocabulay().getListOfArticles();
		Queue<Integer> queue = new PriorityQueue<>();
		List<String> topTen = new ArrayList<>();
		
		for (String path : articles) {
			topTen.add(path);
			if (topTen.size() == 10) {
				break;
			}
			double[] vector2 = env.getDocument(path).getVector();
		}

		 env.setResultOfPreviousCommand(topTen);
		 
		 printResult(env, topTen);

		return ShellStatus.CONTINUE;
	}
	
	private void printResult(Environment env, List<String> topTen) {
		int index = 0;
		for (String s : topTen) {
			env.writeln("[ " + index + " ] (0) " + s);
			index++;
		}
	}

	private double getScalaraProduct(double[] vector1, double[] vector2) {
		double res = 0; 
		
		for (int i = 0, n = Math.min(vector1.length, vector2.length); i<n; i++) {
			res += vector1[i]*vector2[i];
		}
		
		return res;
	}
	
	/**
	 * Method that returns the length of given vector.
	 * 
	 * @param vector Given vector.
	 * @return Length of the vector.
	 */
	private double getLength(double[] vector) {
		double res = 0;

		for (int i = 0; i < vector.length; i++) {
			res += vector[i] * vector[i];
		}

		return Math.sqrt(res);
	}

	/**
	 * Method that creates vector for given query.
	 * 
	 * @param words Set of words given as arguments in query.
	 * @param env   Shell environment.
	 * @return Vector for given query.
	 */
	private double[] getVector(Set<String> words, Environment env) {
		double[] vector = new double[words.size()];
		Vocabulary vocabulary = env.getVocabulay();

		int index = 0;
		for (String word : words) {
			int tf = frequency.get(word);

			int idf = vocabulary.getFrequency(word);

			vector[index] = tf * Math.log10(vocabulary.getNumberOfArticles() / idf);

			index++;
		}

		return vector;
	}

	/**
	 * Method that writes message to the shell.
	 * 
	 * @param env     Shell environment.
	 * @param message Message to be written to the shell.
	 */
	private void writeMessage(Environment env, String message) {
		env.writeln(message);
	}

	/**
	 * Returns Set of words given as arguments in query. Stop words are not
	 * considered.
	 * 
	 * @param arguments Shell arguments.
	 * @param env       Shell environments.
	 * @return Set of words given as arguments in query.
	 */
	private Set<String> getWordsFromInput(String arguments, Environment env) {
		Set<String> stopWords = null;
		try {
			stopWords = env.getVocabulay().getStopWords();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> words = getWords(arguments, stopWords);

		for (String word : words) {
			frequency.merge(word, 1, (k, v) -> v + 1);
		}

		return new LinkedHashSet<String>(words);
	}

	/**
	 * Returns list of words given as arguments in query. Stop words are not
	 * considered.
	 * 
	 * @param arguments Shell arguments.
	 * @param stopWords Set of stop words in Croatian language.
	 * @return List of words given as shell arguments in query.
	 */
	private List<String> getWords(String arguments, Set<String> stopWords) {
		List<String> words = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		char[] array = arguments.toCharArray();

		for (Character c : array) {
			if (Character.isAlphabetic(c)) {
				sb.append(c);
			} else {
				String word = sb.toString();
				if (!word.isEmpty() && !stopWords.contains(word)) {
					words.add(word);
				}
				sb = new StringBuilder();
			}
		}

		String word = sb.toString();

		if (!word.isEmpty() && !stopWords.contains(word)) {
			words.add(word);
		}

		return words;
	}

	/**
	 * Method that writes the error message to the console.
	 */
	private ShellStatus writeErrorMessage(Environment env, int errorCode) {
		try {
			if (errorCode == 0) {
				env.writeln("Invalid number of arguments.");
			} else if (errorCode == 1) {

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
		return "query";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();

		commandDescription.add("Prints ten best results for given query. Expects at least one argument.");

		commandDescription = Collections.unmodifiableList(commandDescription);

		return commandDescription;
	}

}
