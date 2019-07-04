package hr.fer.zemris.hw17.article;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.hw17.vocabulary.Vocabulary;

/**
 * Class that represents one article. It contains information about the path
 * where this article is stored.
 * 
 * @author David
 *
 */
public class Article {

	/**
	 * Path where this article is stored.
	 */
	private Path path;

	/**
	 * Vocabulary. This class contains all words presented.
	 */
	private Vocabulary vocabulary;

	/**
	 * Stores calculated value of idf for every word in vocabulary.
	 */
	private double[] vector;

	/**
	 * Total number of documents.
	 */
	private int totalNumberOfDocuments;

	/**
	 * Constructor. Used to initialize path, vocabulary and total number of
	 * documents.
	 * 
	 * @param path                   Path where this articles is stored.
	 * @param vocabulary             Vocabulary.
	 * @param totalNumberOfDocuments Total number od documents.
	 */
	public Article(Path path, Vocabulary vocabulary, int totalNumberOfDocuments) {
		this.path = path;
		this.vocabulary = vocabulary;
		this.totalNumberOfDocuments = totalNumberOfDocuments;
	}

	/**
	 * Returns path on which this article is stored.
	 * 
	 * @return Path.
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Returns vector.
	 * 
	 * @return Vector.
	 */
	public double[] getVector() {
		if (vector == null) {
			createVector();
		}
		return vector;
	}

	/**
	 * Method that creates vector. It goes through all words and calculates idf for
	 * every word. The formula is : log(|D| / |deD: w e d|). This is ration of total
	 * number of documents presented and number of document that contains particular
	 * word.
	 */
	private void createVector() {
		vector = new double[vocabulary.getSize()];
		
		List<String> words = vocabulary.getWords(path.toString());
		Map<String, Integer> map = new HashMap<>();

		for (String word : words) {
			map.merge(word, 1, (k, v) -> v + 1);
		}

		Set<String> allWords = vocabulary.getVocabulary();
		int index = 0;
		for (String s : allWords) {
			Integer tf = map.get(s);
			
			if (tf == null) {
				tf = 0;
			}

			int idf = vocabulary.getFrequency(s);

			vector[index] = tf * Math.log10(totalNumberOfDocuments / idf);

			index++;
		}
	}

}
