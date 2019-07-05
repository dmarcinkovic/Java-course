package hr.fer.zemris.hw17.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import hr.fer.zemris.hw17.article.Article;
import hr.fer.zemris.hw17.shell.Environment;
import hr.fer.zemris.hw17.shell.Result;
import hr.fer.zemris.hw17.shell.ShellIOException;
import hr.fer.zemris.hw17.shell.ShellStatus;
import hr.fer.zemris.hw17.util.Util;
import hr.fer.zemris.hw17.vector.Vector;

/**
 * Prints ten best results for given query. Expects at least one argument.
 * 
 * @author David
 *
 */
public class QueryCommand implements ShellCommand {

	/**
	 * Constructor.
	 */
	public QueryCommand() {
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

		List<String> words = new ArrayList<>();
		List<String> lines = new ArrayList<>();
		lines.add(arguments);

		Util.getWords(lines, env.getVocabulay().getStopWords(), words);

		double[] vector1 = null;
		try {
			vector1 = Util.createVector(words, env.getVocabulay());
		} catch (ArithmeticException e) {
			writeMessage(env, "No results");
			return ShellStatus.CONTINUE;
		}

		List<Result> res = getResult(env, vector1);

		printResult(res, env, words);
		env.setResultOfPreviousCommand(res);
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method that returns result of query as List of Result objects.
	 * 
	 * @param env     Shell environment.
	 * @param vector1 Vector1.
	 * @return List of Result objects.
	 */
	private List<Result> getResult(Environment env, double[] vector1) {
		List<Article> articles = env.getVocabulay().getArticles();
		Queue<Result> queue = new PriorityQueue<>();

		for (Article article : articles) {
			Vector v = env.getVector(article.getPath().toString());

			double modulo1 = Vector.modulo(vector1);
			double modulo2 = Vector.modulo(v);
			double scalar = Vector.scalarProduct(vector1, v.getVector());

			queue.add(new Result(scalar / (modulo1 * modulo2), article.toString()));

			if (queue.size() > 10) {
				queue.poll();
			}
		}

		List<Result> result = new ArrayList<>(queue);
		Collections.sort(result, (v1, v2) -> v2.compareTo(v1));
		return result;
	}

	/**
	 * Method used to print result to Shell. It prints top ten results in descending
	 * order.
	 * 
	 * @param result List of Result objects.
	 * @param env    Shell environment.
	 * @param words  List of words.
	 */
	private void printResult(List<Result> result, Environment env, List<String> words) {
		List<Result> list = new ArrayList<>(result);

		writeMessage(env, "Query is: " + words.toString());
		writeMessage(env, "Top 10 results: ");

		int index = 0;
		for (Result res : list) {
			writeMessage(env, "[ " + index + "] " + res.toString());
			index++;
		}
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
