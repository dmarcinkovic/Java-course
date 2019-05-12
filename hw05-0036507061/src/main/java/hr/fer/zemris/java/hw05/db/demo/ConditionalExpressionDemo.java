package hr.fer.zemris.java.hw05.db.demo;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * Demo example to test ConditionalExpression class.
 * 
 * @author david
 *
 */
public class ConditionalExpressionDemo {

	/**
	 * Method invoked when running the program.
	 * 
	 * @param args Arguments provided via command line. In this program they are not
	 *             used.
	 */
	public static void main(String[] args) {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Bos*",
				ComparisonOperators.LIKE);
		StudentRecord record = getSomehowOneRecord();
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record), // returns
				// record
				expr.getLiteral() // returns "Bos*"
		);
		System.out.println(recordSatisfies);
	}
	
	/**
	 * Method to return some instance of StudentRecord class.  
	 * @return Some manually written instance of StudentRecord class. 
	 */
	private static StudentRecord getSomehowOneRecord() {
		return new StudentRecord("Perica", "Bos", 5, "12345");
	}
}
