package hr.fer.zemris.hw17.vocabulary;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class that represents all words from all articles available. It takes care of
 * stop words. Those words are not included in vocabulary.
 * 
 * @author David
 *
 */
public class Vocabulary {

	/**
	 * Directory that contains all articles.
	 */
	private Path directory;

	/**
	 * File with stop words.
	 */
	private Path file;

	/**
	 * Set of all words from all articles available.
	 */
	private Set<String> vocabulary;

	/**
	 * Map that stores the frequency of every word in all articles.
	 */
	private Map<String, Integer> frequency;

	/**
	 * Map that contains set of words for every article.
	 */
	private Map<String, List<String>> words;

	/**
	 * Stores the number of articles.
	 */
	private int numberOfArticles;

	/**
	 * List of all articles.
	 */
	private List<String> articles;

	/**
	 * Constructor. Initializes directory that contains all articles and file with
	 * stop word.
	 * 
	 * @param dir       Directory that contains all articles.
	 * @param stopWords File with stop words.
	 */
	public Vocabulary(String dir, String stopWords) {
		file = Paths.get(stopWords);
		directory = Paths.get(dir);

		vocabulary = new HashSet<>();
		frequency = new HashMap<>();
		words = new HashMap<>();

		articles = new ArrayList<>();

		try {
			createVocabulary();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Returns in how many articles given word appears.
	 * 
	 * @param word Word to get frequency.
	 * @return Number of times given word appears in all articles.
	 */
	public Integer getFrequency(String word) {
		Integer result = frequency.get(word);
		if (result == null) {
			return 0;
		}
		return result;
	}

	/**
	 * Returns set of all words from all articles.
	 * 
	 * @return Set of all words from all articles.
	 */
	public Set<String> getVocabulary() {
		return vocabulary;
	}

	/**
	 * Returns number of words presented in vocabulary.
	 * 
	 * @return Number of words presented in vocabulary.
	 */
	public int getSize() {
		return vocabulary.size();
	}

	/**
	 * Method that returns the number of articles.
	 * 
	 * @return Number of articles.
	 */
	public int getNumberOfArticles() {
		return numberOfArticles;
	}

	/**
	 * Returns list of all articles.
	 * 
	 * @return List of all articles.
	 */
	public List<String> getListOfArticles() {
		return articles;
	}

	/**
	 * Returns set of words for particular article.
	 * 
	 * @param filename Path to article.
	 * @return Set of words for particular article.
	 */
	public List<String> getWords(String filename) {
		return words.get(filename);
	}

	/**
	 * Creates vocabulary. It reads file with stop words and all articles and then
	 * extract words from those articles.
	 * 
	 * @throws IOException When error occurs while opening the file.
	 */
	private void createVocabulary() throws IOException {
		Set<String> stopWords = getStopWords();

		DirectoryStream<Path> stream = Files.newDirectoryStream(directory);

		for (Path entry : stream) {
			articles.add(entry.toString());
			List<String> lines = Files.readAllLines(entry);

			List<String> list = new ArrayList<>();
			extractWords(lines, stopWords, list);
			words.put(entry.toString(), list);

			Set<String> set = new HashSet<>(list);
			for (String s : set) {
				frequency.merge(s, 1, (k, v) -> v + 1);
			}

			numberOfArticles++;
		}

	}

	/**
	 * Extracts words from given list of strings.
	 * 
	 * @param lines     List of string from which words are extracted.
	 * @param stopWords Set of all stop words.
	 * @param set       Set of words for every particular article.
	 */
	private void extractWords(List<String> lines, Set<String> stopWords, List<String> list) {
		for (String line : lines) {
			StringBuilder sb = new StringBuilder();
			char[] array = line.toCharArray();

			for (Character c : array) {
				if (Character.isAlphabetic(c)) {
					sb.append(c);
				} else {
					String word = sb.toString();
					if (!word.isEmpty() && !stopWords.contains(word)) {
						vocabulary.add(word);
						list.add(word);
					}
					sb = new StringBuilder();
				}
			}

			String word = sb.toString();
			if (!word.isEmpty() && !stopWords.contains(word)) {
				vocabulary.add(word);
				list.add(word);
			}
		}
	}

	/**
	 * Reads all stop words from file.s
	 * 
	 * @return Set of string representing stop words.
	 * @throws IOException When error while opening the file occurs.
	 */
	public Set<String> getStopWords() throws IOException {
		List<String> lines = Files.readAllLines(file);

		return new HashSet<String>(lines);
	}
}
