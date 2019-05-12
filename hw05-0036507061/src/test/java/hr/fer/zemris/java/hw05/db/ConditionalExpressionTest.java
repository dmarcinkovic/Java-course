package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ConditionalExpressionTest {

	@Test
	void test() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "*ć",
				ComparisonOperators.LIKE);
		StudentRecord record = new StudentRecord("Pero", "Perić", 3, "24032");

		boolean result = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		assertTrue(result);
	}

	@Test
	public void testExpression() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Pero",
				ComparisonOperators.LIKE);
		StudentRecord record = new StudentRecord("Pero", "Perić", 3, "24032");

		boolean result = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		assertTrue(result);
	}

	@Test
	public void testExpression2() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.JMBAG, "3*", ComparisonOperators.LIKE);
		StudentRecord record = new StudentRecord("Pero", "Perić", 3, "24032");

		boolean result = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		assertFalse(result);
	}

	@Test
	public void testExpression3() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.JMBAG, "3",
				ComparisonOperators.GREATER);
		StudentRecord record = new StudentRecord("Pero", "Perić", 3, "24032");

		boolean result = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		assertFalse(result);
	}

	@Test
	public void testExpression4() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.JMBAG, "24032",
				ComparisonOperators.GREATER_OR_EQUALS);
		StudentRecord record = new StudentRecord("Pero", "Perić", 3, "24032");

		boolean result = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		assertTrue(result);
	}

	@Test
	public void testExpression5() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "3*",
				ComparisonOperators.LESS);
		StudentRecord record = new StudentRecord("Pero", "Perić", 3, "24032");

		boolean result = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		assertFalse(result);
	}

	@Test
	public void testExpression6() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Pero",
				ComparisonOperators.EQUALS);
		StudentRecord record = new StudentRecord("Pero", "Perić", 3, "24032");

		boolean result = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		assertTrue(result);
	}

	@Test
	public void testExpression7() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Pero",
				ComparisonOperators.NOT_EQUALS);
		StudentRecord record = new StudentRecord("Pero", "Perić", 3, "24032");

		boolean result = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		assertFalse(result);
	}

	@Test
	public void testExpression8() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "pero",
				ComparisonOperators.NOT_EQUALS);
		StudentRecord record = new StudentRecord("Pero", "Perić", 3, "24032");

		boolean result = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		assertTrue(result);
	}

	@Test
	public void testFirstNameCaseSensitive() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "štefica",
				ComparisonOperators.EQUALS);

		StudentRecord record = new StudentRecord("šteficA", "Štefić", 5, "24132");

		boolean result = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		assertFalse(result);
	}

	@Test
	public void testLastNameCaseSensitive() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Štefić",
				ComparisonOperators.EQUALS);

		StudentRecord record = new StudentRecord("šteficA", "štefić", 5, "24132");

		boolean result = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		assertFalse(result);
	}

	@Test
	public void testLikeOperator() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "*tefić",
				ComparisonOperators.LIKE);

		StudentRecord record = new StudentRecord("šteficA", "štefić", 5, "24132");

		boolean result = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		assertTrue(result);
	}

	@Test
	public void testLikeOperator2() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "*tefi*",
				ComparisonOperators.LIKE);

		StudentRecord record = new StudentRecord("šteficA", "štefić", 5, "24132");

		boolean result = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		assertFalse(result);
	}

	@Test
	public void testLikeOperator3() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.JMBAG, "*", ComparisonOperators.LIKE);

		StudentRecord record = new StudentRecord("šteficA", "štefić", 5, "24132");

		boolean result = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		assertTrue(result);
	}

	@Test
	public void testLikeOperator4() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.JMBAG, "**", ComparisonOperators.LIKE);

		StudentRecord record = new StudentRecord("šteficA", "štefić", 5, "24132");

		boolean result = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		assertFalse(result);
	}

	@Test
	public void testLikeOperator5() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "AA*AA",
				ComparisonOperators.LIKE);

		StudentRecord record = new StudentRecord("AAA", "štefić", 5, "24132");

		boolean result = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		assertFalse(result);
	}

	@Test
	public void testLikeOperator6() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "a*aa",
				ComparisonOperators.LIKE);

		StudentRecord record = new StudentRecord("AAA", "štefić", 5, "24132");

		boolean result = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		assertFalse(result);
	}

	@Test
	public void testLikeOperator7() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "aaa",
				ComparisonOperators.LIKE);

		StudentRecord record = new StudentRecord("AAA", "štefić", 5, "24132");

		boolean result = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		assertFalse(result);
	}

	@Test
	public void testLikeOperator8() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "AAA",
				ComparisonOperators.LIKE);

		StudentRecord record = new StudentRecord("AAA", "štefić", 5, "24132");

		boolean result = expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record),
				expr.getLiteral());
		assertTrue(result);
	}
}
