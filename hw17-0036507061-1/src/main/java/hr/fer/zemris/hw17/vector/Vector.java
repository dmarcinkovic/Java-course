package hr.fer.zemris.hw17.vector;

import hr.fer.zemris.hw17.article.Article;
import hr.fer.zemris.hw17.util.Util;
import hr.fer.zemris.hw17.vocabulary.Vocabulary;

/**
 * Data structure that encapsulates vector that is obtained from article. It has
 * static methods for calculating scalar product and modulo of vector.
 * 
 * @author David
 *
 */
public class Vector {

	/**
	 * Vector represented as array of doubles.
	 */
	private double[] vector;

	/**
	 * Constructor used to initialize article and vocabulary.
	 * 
	 * @param article    Article.
	 * @param vocabulary Vocabulary.
	 */
	public Vector(Article article, Vocabulary vocabulary) {
		vector = Util.createVector(article.getListOfWords(), vocabulary);
	}

	/**
	 * Returns vector as array of doubles.
	 * 
	 * @return Vector as array of doubles.
	 */
	public double[] getVector() {
		return vector;
	}

	/**
	 * Method that calculates modulo of given vector.
	 * 
	 * @param v Vector which modulo will be calculated.
	 * @return Modulo of given vector.
	 */
	public static double modulo(Vector v) {
		double[] vec = v.getVector();

		return modulo(vec);
	}

	/**
	 * Method that calculates modulo of given vector.
	 * 
	 * @param vec Vector which modulo will be calculated.
	 * @return Modulo of given vector.
	 */
	public static double modulo(double[] vec) {
		double result = 0;

		for (Double i : vec) {
			result += i * i;
		}

		return Math.sqrt(result);
	}

	/**
	 * Method that calculates scalar product between two vectors.
	 * 
	 * @param v1 Vector1 given as array of doubles.
	 * @param v2 Vector2 given as array of doubles.
	 * @return Scalar product between two vectors.
	 */
	public static double scalarProduct(double[] v1, double[] v2) {
		double result = 0;
		
		for (int i = 0, n = Math.min(v1.length, v2.length); i < n; i++) {
			result += v1[i] * v2[i];
		}

		return result;
	}
}
