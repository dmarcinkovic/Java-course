package hr.fer.zemris.hw17.shell;

/**
 * Data structure that is used to store the result of query command. It stores
 * the article name and similarity between two vectors.
 * 
 * @author David
 *
 */
public class Result implements Comparable<Result> {

	/**
	 * Similarity between two vectors.
	 */
	private double similarity;

	/**
	 * Article name.
	 */
	private String articleName;

	/**
	 * Constructor used to initialize similarity and article name.
	 * 
	 * @param similarity  Similarity.
	 * @param articleName Article name.
	 */
	public Result(double similarity, String articleName) {
		this.similarity = similarity;
		this.articleName = articleName;
	}

	/**
	 * Returns similarity between two vector.
	 * 
	 * @return Similarity between two vectors.
	 */
	public double getSimilarity() {
		return similarity;
	}

	/**
	 * Returns article name.
	 * 
	 * @return Article name.
	 */
	public String getArticleName() {
		return articleName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" + String.valueOf(similarity) + ") " + articleName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Result o) {
		return Double.compare(similarity, o.getSimilarity());
	}
}
