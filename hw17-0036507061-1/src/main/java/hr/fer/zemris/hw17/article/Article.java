package hr.fer.zemris.hw17.article;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.hw17.util.Util;

/**
 * Class that represents one article. It contains information about the path
 * where this article is stored.
 * 
 * @author David
 *
 */
public class Article {

	/**
	 * Path of article.
	 */
	private Path path;

	/**
	 * List of all words presented in this article.
	 */
	private List<String> allWords;

	/**
	 * Set of all words presented in this article.
	 */
	private Set<String> words;

	/**
	 * Constructor used to initialize path of article and stopWords.
	 * 
	 * @param filename  Path of article given as String.
	 * @param stopWords Set of stop words in croatian language.
	 */
	public Article(String filename, Set<String> stopWords) {
		path = Paths.get(filename);

		allWords = Util.getWords(filename, stopWords);

		words = new HashSet<>(allWords);
	}

	/**
	 * Returns path of article.
	 * 
	 * @return Path of article.
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Returns set of words.
	 * 
	 * @return Set of words.
	 */
	public Set<String> getSetOfWords() {
		return words;
	}

	/**
	 * Returns list of all words presented in this article.
	 * 
	 * @return
	 */
	public List<String> getListOfWords() {
		return allWords;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return path.toString();
	}
}
