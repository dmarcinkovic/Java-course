package hr.fer.zemris.hw17.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.hw17.article.Article;
import hr.fer.zemris.hw17.vocabulary.Vocabulary;

/**
 * Util class that has static method for extracting words from list of strings
 * and creating vectors from those words.
 * 
 * @author David
 *
 */
public class Util {

	/**
	 * Method that returns words as list of Strings. Words are read from file which
	 * path is given as method argument. This method also accepts set of stop words.
	 * This is necessary because we do no want stop words to affect search result.
	 * 
	 * @param filename  Filename.
	 * @param stopWords Set of stop words.
	 * @return Extracted words.
	 */
	public static List<String> getWords(String filename, Set<String> stopWords) {
		List<String> result = new ArrayList<>();

		Path file = Paths.get(filename);
		getWords(file, result, stopWords);

		return result;
	}

	/**
	 * Method that returns words as list of Strings. Words are read from file which
	 * path is given as method argument. This method also accepts set of stop words.
	 * This is necessary because we do no want stop words to affect search result.
	 * It stores result in list that is given as method argument.
	 * 
	 * @param lines     List of string from which words will be extracted.
	 * @param stopWords Set of stop words.
	 * @param list      List in which extracted words will be stored.
	 */
	public static void getWords(List<String> lines, Set<String> stopWords, List<String> list) {
		for (String line : lines) {
			StringBuilder sb = new StringBuilder();

			char[] array = line.toCharArray();

			for (Character c : array) {
				if (Character.isAlphabetic(c)) {
					sb.append(c);
				} else {
					String word = sb.toString();

					if (!word.isEmpty() && !stopWords.contains(word)) {
						list.add(word);
					}

					sb = new StringBuilder();
				}
			}

			String word = sb.toString();

			if (!word.isEmpty() && !stopWords.contains(word)) {
				list.add(word);
			}
		}
	}

	/**
	 * Method that returns words as list of Strings. Words are read from file which
	 * path is given as method argument. This method also accepts set of stop words.
	 * This is necessary because we do no want stop words to affect search result.
	 * It stores result in list that is given as method argument.
	 * 
	 * @param filename  Filename.
	 * @param list      List in which extracted words will be stored.
	 * @param stopWords Set of stop words.
	 */
	private static void getWords(Path filename, List<String> list, Set<String> stopWords) {
		List<String> lines = null;

		try {
			lines = Files.readAllLines(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}

		getWords(lines, stopWords, list);
	}

	/**
	 * Method that creates vector from given list of words.
	 * 
	 * @param words      List of words.
	 * @param vocabulary Vocabulary. Used to get all articles available.
	 * @return Vector as array of doubles.
	 */
	public static double[] createVector(List<String> words, Vocabulary vocabulary) {
		Map<String, Integer> map = new HashMap<>();

		for (String word : words) {
			map.merge(word, 1, (k, v) -> v + 1);
		}

		Set<String> set = vocabulary.getWords();
		double[] result = new double[set.size()];

		int index = 0;
		for (String word : set) {
			Integer tf = map.get(word);
			double idf = getIdf(word, vocabulary.getArticles());
			
			if (tf == null) {
				tf = 0;
			}
			
			result[index++] = tf * idf;
		}

		return result;
	}

	/**
	 * Return idf. This is calculated by formula: log10(N/d) where N is total number
	 * of articles available, and d is number that counts in how many articles
	 * specific word appears.
	 * 
	 * @param word       Word.
	 * @param vocabulary Vocabulary.
	 * @return Idf.
	 */
	private static double getIdf(String word, List<Article> vocabulary) {
		int n = 0;
		for (Article article : vocabulary) {
			Set<String> words = article.getSetOfWords();

			if (words.contains(word)) {
				n++;
			}
		}
		return Math.log10(vocabulary.size() / n);
	}

}
