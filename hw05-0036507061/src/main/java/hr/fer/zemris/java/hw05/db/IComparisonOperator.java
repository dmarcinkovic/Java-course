package hr.fer.zemris.java.hw05.db;

/**
 * Strategy that represents comparison operator. Possible comparison operators
 * are: less, less or equal, greater, greater or equal, equal, not equal and
 * like.
 * 
 * @author david
 *
 */
public interface IComparisonOperator {

	/**
	 * Method to check if given String value1 and value2 satisfies the specified
	 * comparison operation. For example: if comparison operator is less than this
	 * method will check if value1 is less than value2.
	 * 
	 * @param value1 String value1.
	 * @param value2 String value2.
	 * @return True if value1 and value2 satisfies specified comparison operation.
	 */
	public boolean satisfied(String value1, String value2);
}
