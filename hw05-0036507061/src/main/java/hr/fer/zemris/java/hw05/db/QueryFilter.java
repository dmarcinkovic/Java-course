package hr.fer.zemris.java.hw05.db;

import java.util.List;
import java.util.Objects;

/**
 * This class implements IFilter. It is used to determine is given StudentRecord
 * satisfies given conditionalExpression.
 * 
 * @author david
 *
 */
public class QueryFilter implements IFilter {
	/**
	 * Conditional expression.
	 */
	private List<ConditionalExpression> conditionalExpressions;

	/**
	 * Constructor that is used to initialize conditionExpression.
	 * 
	 * @param conditionalExpressions conditionalExpression.
	 * @throws NullPointerException if conditionalExpressions is null.
	 */
	public QueryFilter(List<ConditionalExpression> conditionalExpressions) {
		this.conditionalExpressions = Objects.requireNonNull(conditionalExpressions);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression condition : conditionalExpressions) {
			boolean result = condition.getComparisonOperator().satisfied(condition.getFieldValueGetter().get(record),
					condition.getLiteral());
			
			if (result == false) {
				return false;
			}
		}
		return true;
	}

}
