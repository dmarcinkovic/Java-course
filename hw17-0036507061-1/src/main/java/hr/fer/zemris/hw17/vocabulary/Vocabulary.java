package hr.fer.zemris.hw17.vocabulary;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.hw17.article.Article;

/**
 * Class that represents all words from all articles available. It takes care of
 * stop words. Those words are not included in vocabulary.
 * 
 * @author David
 *
 */
public class Vocabulary {

	/**
	 * Directory where all articles are stored.
	 */
	private Path directory;

	/**
	 * Set of stop words. Used to remove those words from out final vocabulary.
	 */
	private Set<String> stopWords;

	/**
	 * List of all articles.
	 */
	private List<Article> articles;

	/**
	 * Constructor used to initialize directory where all articles are stored and
	 * set of stop words.
	 * 
	 * @param dir  Directory where all articles are stores.
	 * @param stop Set of stop words.
	 */
	public Vocabulary(String dir, String stop) {
		directory = Paths.get(dir);

		getStopWords(Paths.get(stop));
		articles = new ArrayList<>();

		getListOfArticles();
	}

	/**
	 * Reads file in which stop words are written. Then it creates set of those
	 * words.
	 * 
	 * @param path Path in which stop words are written.
	 */
	private void getStopWords(Path path) {
		List<String> lines = null;

		try {
			lines = Files.readAllLines(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		stopWords = new HashSet<>(lines);
	}

	/**
	 * Returns stop words.
	 * 
	 * @return Stop words.
	 */
	public Set<String> getStopWords() {
		return stopWords;
	}

	/**
	 * MEthod that reads all articles from given directory and stores result in
	 * articles list.
	 */
	private void getListOfArticles() {
		DirectoryStream<Path> stream = null;
		try {
			stream = Files.newDirectoryStream(directory);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Path p : stream) {
			articles.add(new Article(p.toString(), stopWords));
		}
	}

	/**
	 * Returns list of articles.
	 * 
	 * @return List of articles.
	 */
	public List<Article> getArticles() {
		return articles;
	}
}
