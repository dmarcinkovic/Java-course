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
	 * Vocabulary. This class contains all words presented 
	 */
	private Vocabulary vocabulary;
	private double[] vector;
	private int totalNumberOfDocuments;

	public Article(Path path, Vocabulary vocabulary, int totalNumberOfDocuments) {
		this.path = path;
		this.vocabulary = vocabulary;
		this.totalNumberOfDocuments = totalNumberOfDocuments;
	}

	public Path getPath() {
		return path;
	}

	public double[] getVector() {
		if (vector == null) {
			createVector();
		}
		return vector;
	}

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
			System.out.println(s);
			System.out.println(map.get(s));
			int tf = map.get(s);
			int idf = vocabulary.getFrequency(s);

			vector[index] = tf * Math.log10(totalNumberOfDocuments / idf);

			index++;
		}
	}

}
