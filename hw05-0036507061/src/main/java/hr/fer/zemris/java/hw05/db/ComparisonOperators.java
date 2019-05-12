package hr.fer.zemris.java.hw05.db;

/**
 * Class representing comparison operators. Comparison operators are : less,
 * less or equal, greater, greater or equal, equal, not equal, like.
 * 
 * @author david
 *
 */
public class ComparisonOperators {

	/**
	 * LESS operator checks if given value1 is less than value2.
	 */
	public static final IComparisonOperator LESS = (v1, v2) -> v1.compareTo(v2) < 0;

	/**
	 * LESS_OR_EQUALS operator checks if given value1 is less than or equal to
	 * value2.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) <= 0;

	/**
	 * GREATER operator checks if given value1 is greater than value2.
	 */
	public static final IComparisonOperator GREATER = (v1, v2) -> v1.compareTo(v2) > 0;

	/**
	 * GREATER_OR_EQUALS operator checks if given value1 is greater than or equal to
	 * value1.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) >= 0;

	/**
	 * EQUALS operator checks if given value1 and value2 are equal.
	 */
	public static final IComparisonOperator EQUALS = (v1, v2) -> v1.equals(v2);

	/**
	 * NOT_EQUALS operator checks if given value1 and value2 are not equal.
	 */
	public static final IComparisonOperator NOT_EQUALS = (v1, v2) -> !v1.equals(v2);

	/**
	 * LIKE operator checks if given string matches to given pattern. Pattern
	 * consists only of letters and '*' sign which is wildcard character which means
	 * one or more characters of any type.
	 */
	public static final IComparisonOperator LIKE = new ComparisonOperatorLike();

	/**
	 * Implementation of strategy IComparisonOperator. It implements LIKE operator.
	 * LIKE operator checks if given string matches to given pattern. Pattern
	 * consists only of letters and '*' sign which is wildcard character which means
	 * one or more characters of any type.
	 * 
	 * @author david
	 *
	 */
	private static class ComparisonOperatorLike implements IComparisonOperator {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean satisfied(String value1, String value2) {
			String[] splited = value2.split("\\*");

			if (value2.equals("*")) {
				return true;
			} else if (splited.length == 0) {
				return false;
			} else if  (!isCorrectStringLiteral(value2)) {
				return false;
			}else if (splited.length > 2) {
				return false;
			} else if (value2.startsWith("*")) {
				return value1.endsWith(splited[1]);
			} else if (value2.endsWith("*")) {
				return value1.startsWith(splited[0]);
			} else if (splited.length == 1) {
				return value2.equals(value1);
			} else if (splited.length == 2) {
				if (splited[0].length() + splited[1].length() > value1.length()) {
					return false;
				}
				return value1.startsWith(splited[0]) && value1.endsWith(splited[1]);
			}
			return false;
		}
		
		/**
		 * Method that checks if given string is valid string literal when operator is LIKE. 
		 * @param string String to check for. 
		 * @return True if given String is valid, otherwsie returns false.
		 */
		private boolean isCorrectStringLiteral(String string) {
			char[] array = string.toCharArray();
			int counter = 0;

			for (Character c : array) {
				if (c == '*') {
					counter++;
				}
				if (counter >= 2) {
					return false;
				}
			}
			return true;
		}

	}
}
