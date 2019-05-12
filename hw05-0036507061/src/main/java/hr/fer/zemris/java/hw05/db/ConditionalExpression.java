package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Class represents one conditional expression. One conditional expression
 * consists of one field name, operator symbol and string literal.
 * 
 * @author david
 *
 */
public class ConditionalExpression {
	/**
	 * Name of the field.
	 */
	private IFieldValueGetter fieldValueGetter;

	/**
	 * String literal.
	 */
	private String literal;

	/**
	 * Comparison operator. Operators are less, less or equal to, greater, greater
	 * or equal, equal, not equal and like.
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * Constructor that initializes all fields of this class.
	 * 
	 * @param fieldValueGetter   Name of the field.
	 * @param literal            String literal.
	 * @param comparisonOperator Comparison operator. Operators are : less, less or
	 *                           equal to, greater, greater or equal to, equal, not
	 *                           equal and like.
	 * @throws NullPointerException if fieledValueGetter or literal or comparisonOperator are null.
	 */
	public ConditionalExpression(IFieldValueGetter fieldValueGetter, String literal,
			IComparisonOperator comparisonOperator) {
		this.fieldValueGetter = Objects.requireNonNull(fieldValueGetter);
		this.literal = Objects.requireNonNull(literal);;
		this.comparisonOperator = Objects.requireNonNull(comparisonOperator);
	}

	/**
	 * Returns fieldValueGetter.
	 * 
	 * @return fieldValueGetter.
	 */
	public IFieldValueGetter getFieldValueGetter() {
		return fieldValueGetter;
	}

	/**
	 * Returns string literal.
	 * 
	 * @return string literal.
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns comparisonOperator.
	 * 
	 * @return comparisonOperator.
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
}
