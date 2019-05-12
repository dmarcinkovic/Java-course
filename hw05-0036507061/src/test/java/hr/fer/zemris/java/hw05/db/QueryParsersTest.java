package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.lexer.LexerException;

class QueryParsersTest {

	@Test
	public void testSimpleQuery() {
		QueryParser parser = new QueryParser(" jmbag=\"0000000003\"");

		assertTrue(parser.isDirectQuery());

		List<ConditionalExpression> queries = parser.getQuery();

		assertEquals(queries.get(0).getComparisonOperator(), ComparisonOperators.EQUALS);
		assertEquals(queries.get(0).getLiteral(), "0000000003");
		assertEquals(queries.get(0).getFieldValueGetter(), FieldValueGetters.JMBAG);
	}

	@Test
	public void testSimpleQuery2() {
		QueryParser parser = new QueryParser(" lastName = \"Blažić\"");

		assertFalse(parser.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> parser.getQueriedJMBAG());

		List<ConditionalExpression> queries = parser.getQuery();

		assertEquals(queries.get(0).getComparisonOperator(), ComparisonOperators.EQUALS);
		assertEquals(queries.get(0).getLiteral(), "Blažić");
		assertEquals(queries.get(0).getFieldValueGetter(), FieldValueGetters.LAST_NAME);
	}

	@Test
	public void testSimpleQuery3() {
		QueryParser parser = new QueryParser(" firstName>\"A\" and lastName LIKE \"B*ć\"");

		assertFalse(parser.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> parser.getQueriedJMBAG());

		List<ConditionalExpression> queries = parser.getQuery();

		assertEquals(queries.get(0).getComparisonOperator(), ComparisonOperators.GREATER);
		assertEquals(queries.get(0).getLiteral(), "A");
		assertEquals(queries.get(0).getFieldValueGetter(), FieldValueGetters.FIRST_NAME);

		assertEquals(queries.get(1).getComparisonOperator(), ComparisonOperators.LIKE);
		assertEquals(queries.get(1).getLiteral(), "B*ć");
		assertEquals(queries.get(1).getFieldValueGetter(), FieldValueGetters.LAST_NAME);
	}

	@Test
	public void testSimpleQuery4() {
		QueryParser parser = new QueryParser(
				" firstName>\"A\" and firstName<\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");

		assertFalse(parser.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> parser.getQueriedJMBAG());

		List<ConditionalExpression> queries = parser.getQuery();

		assertEquals(queries.get(0).getComparisonOperator(), ComparisonOperators.GREATER);
		assertEquals(queries.get(0).getLiteral(), "A");
		assertEquals(queries.get(0).getFieldValueGetter(), FieldValueGetters.FIRST_NAME);

		assertEquals(queries.get(1).getComparisonOperator(), ComparisonOperators.LESS);
		assertEquals(queries.get(1).getLiteral(), "C");
		assertEquals(queries.get(1).getFieldValueGetter(), FieldValueGetters.FIRST_NAME);

		assertEquals(queries.get(2).getComparisonOperator(), ComparisonOperators.LIKE);
		assertEquals(queries.get(2).getLiteral(), "B*ć");
		assertEquals(queries.get(2).getFieldValueGetter(), FieldValueGetters.LAST_NAME);
		
		assertEquals(queries.get(3).getComparisonOperator(), ComparisonOperators.GREATER);
		assertEquals(queries.get(3).getLiteral(), "0000000002");
		assertEquals(queries.get(3).getFieldValueGetter(), FieldValueGetters.JMBAG);
	}
	
	@Test
	public void testExtraAnd1() {
		assertThrows(ParserException.class, () -> {
			new QueryParser(" and lastName = \"Blažić\"");
		});
	}
	
	@Test
	public void testExtraAnd2() {
		assertThrows(ParserException.class, () -> {
			new QueryParser(" lastName = \"Blažić\" and");
		});
	}
	
	@Test
	public void testExtraAnd3() {
		assertThrows(ParserException.class, () -> {
			new QueryParser(" lastName = and \"Blažić\"");
		});
	}
	
	@Test
	public void testExtraAnd4() {
		assertThrows(ParserException.class, () -> {
			new QueryParser(" lastName and = and \"Blažić\"");
		});
	}
	
	@Test
	public void testExtraAnd5() {
		assertThrows(ParserException.class, () -> {
			new QueryParser(" and");
		});
	}
	
	@Test
	public void testExtraAnd6() {
		assertThrows(ParserException.class, () -> {
			new QueryParser(" lastName and = \"Blažić\"");
		});
	}
	
	@Test
	public void testAndoperatorCaseInsensivity() {
		QueryParser parser = new QueryParser(" firstName>\"A\" aNd lastName LIKE \"B*ć\"");

		assertFalse(parser.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> parser.getQueriedJMBAG());

		List<ConditionalExpression> queries = parser.getQuery();

		assertEquals(queries.get(0).getComparisonOperator(), ComparisonOperators.GREATER);
		assertEquals(queries.get(0).getLiteral(), "A");
		assertEquals(queries.get(0).getFieldValueGetter(), FieldValueGetters.FIRST_NAME);

		assertEquals(queries.get(1).getComparisonOperator(), ComparisonOperators.LIKE);
		assertEquals(queries.get(1).getLiteral(), "B*ć");
		assertEquals(queries.get(1).getFieldValueGetter(), FieldValueGetters.LAST_NAME);
	}
	
	@Test
	public void testLIKEOperatorCaseSensitivity() {
		assertThrows(ParserException.class, () -> {
			new QueryParser(" firstName LiKe \"a*b\"");
		});
	}
	
	@Test
	public void testWrongName() {
		assertThrows(LexerException.class, () -> {
			new QueryParser(" 1firstName LiKe \"a*b\"");
		});
	}
	
	@Test
	public void testUnclosedString() {
		assertThrows(LexerException.class, () -> {
			new QueryParser(" firstName LIKE \"asa");
		});
	}
	
	@Test
	public void testWrongOrder() {
		assertThrows(ParserException.class, () -> {
			new QueryParser(" LIKE firstName \"asa\"");
		});
	}
	
	@Test
	public void testWrongName1() {
		assertThrows(ParserException.class, () -> {
			new QueryParser(" FirstName LIKE \"asa\"");
		});
	}
	
	@Test
	public void testWrongName2() {
		assertThrows(ParserException.class, () -> {
			new QueryParser(" LastName LIKE \"asa\"");
		});
	}
	
	@Test
	public void testWrongName3() {
		assertThrows(ParserException.class, () -> {
			new QueryParser(" JMBAG LIKE \"asa\"");
		});
	}
	
	@Test
	public void testMultipleWhiteSpaces() {
		QueryParser parser = new QueryParser(" \tjmbag \t \t    LIKE \t \t \"asa\" \t ");

		assertFalse(parser.isDirectQuery());

		List<ConditionalExpression> queries = parser.getQuery();

		assertEquals(queries.get(0).getComparisonOperator(), ComparisonOperators.LIKE);
		assertEquals(queries.get(0).getLiteral(), "asa");
		assertEquals(queries.get(0).getFieldValueGetter(), FieldValueGetters.JMBAG);
	}
	
	@Test
	public void testGetQueriedJmbag() {
		QueryParser parser = new QueryParser(" jmbag = \"0036\"");
		
		assertTrue(parser.isDirectQuery()); 
		
		assertEquals(parser.getQueriedJMBAG(), "0036");
	}
	
	@Test
	public void testConstructorException() {
		assertThrows(NullPointerException.class, () -> {
			new QueryParser(null);
		});
	}
	
	@Test
	public void testLessOrEqualsOperator() {
		QueryParser parser = new QueryParser(" firstName <=\"10\"");


		List<ConditionalExpression> queries = parser.getQuery();

		assertEquals(queries.get(0).getComparisonOperator(), ComparisonOperators.LESS_OR_EQUALS);
		assertEquals(queries.get(0).getLiteral(), "10");
		assertEquals(queries.get(0).getFieldValueGetter(), FieldValueGetters.FIRST_NAME);
	}
	
	@Test
	public void testGreaterOrEqualsOperator() {
		QueryParser parser = new QueryParser(" firstName >=\"10\"");


		List<ConditionalExpression> queries = parser.getQuery();

		assertEquals(queries.get(0).getComparisonOperator(), ComparisonOperators.GREATER_OR_EQUALS);
		assertEquals(queries.get(0).getLiteral(), "10");
		assertEquals(queries.get(0).getFieldValueGetter(), FieldValueGetters.FIRST_NAME);
	}
	
	@Test
	public void testNotEqualsOperator() {
		QueryParser parser = new QueryParser(" firstName !=\"10\"");


		List<ConditionalExpression> queries = parser.getQuery();

		assertEquals(queries.get(0).getComparisonOperator(), ComparisonOperators.NOT_EQUALS);
		assertEquals(queries.get(0).getLiteral(), "10");
		assertEquals(queries.get(0).getFieldValueGetter(), FieldValueGetters.FIRST_NAME);
	}
	
	@Test
	public void testWrongOperator() {
		assertThrows(ParserException.class, () -> {
			new QueryParser(" firstName !=!=\"10\"");
		});
	}
	
	@Test
	public void testWildCardAppearsTwice() {
		QueryParser parser = new QueryParser(" jmbag LIKE\"ab**\"");

		List<ConditionalExpression> queries = parser.getQuery();

		assertEquals(queries.get(0).getComparisonOperator(), ComparisonOperators.LIKE);
		assertEquals(queries.get(0).getLiteral(), "ab**");
		assertEquals(queries.get(0).getFieldValueGetter(), FieldValueGetters.JMBAG);
	}
	
	@Test
	public void testMissingAnd() {
		assertThrows(ParserException.class, () -> {
			new QueryParser(" lastName = \"Bosnić\" lastName = \"Bosnić\"");
		});
	}
	
	@Test
	public void testExtraAnd() {
		assertThrows(ParserException.class, () -> {
			new QueryParser("lastName = \"Bosnić\" and and lastName = \"Bosnić\"");
		});
	}
	
	@Test
	public void testMissingTwoAnds() {
		assertThrows(ParserException.class, () -> {
			new QueryParser("lastName = \"Bosnić\" lastName = \"Bosnić\" and lastName = \"Bosnić\"");
		});
	}
	
	@Test
	public void testAndAtStart() {
		assertThrows(ParserException.class, () -> {
			new QueryParser("and lastName = \"Bosnić\"");
		});
	}
}
