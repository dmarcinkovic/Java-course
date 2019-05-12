package hr.fer.zemris.java.hw05.db.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LexerTest {

	@Test
	void testSimpleQuery() {
		String query = "query jmbag =   \"003600\"";

		Lexer lexer = new Lexer(query);

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.NAME);
		assertEquals(token.getValue(), "query");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.NAME);
		assertEquals(token.getValue(), "jmbag");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.OPERATOR);
		assertEquals(token.getValue(), "=");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.STRING_LITERAL);
		assertEquals(token.getValue(), "003600");

		token = lexer.nextToken();
	}

	@Test
	public void testSimpleQuery2() {
		String text = "query jmbag123_1";

		Lexer lexer = new Lexer(text);

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.NAME);
		assertEquals(token.getValue(), "query");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.NAME);
		assertEquals(token.getValue(), "jmbag123_1");
	}

	@Test
	public void testIncorrectName() {
		String query = "12313query";

		Lexer lexer = new Lexer(query);

		assertThrows(LexerException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testLogicalOperator() {
		String text = "query jmbag=\"1214\" anD jmbag = \"123\"";

		Lexer lexer = new Lexer(text);

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.NAME);
		assertEquals(token.getValue(), "query");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.NAME);
		assertEquals(token.getValue(), "jmbag");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.OPERATOR);
		assertEquals(token.getValue(), "=");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.STRING_LITERAL);
		assertEquals(token.getValue(), "1214");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.LOGICAL_OPERATOR);
		assertEquals(token.getValue(), "AND");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.NAME);
		assertEquals(token.getValue(), "jmbag");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.OPERATOR);
		assertEquals(token.getValue(), "=");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.STRING_LITERAL);
		assertEquals(token.getValue(), "123");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.EOF);
		assertEquals(token.getValue(), null);
	}

	@Test
	public void testLogicalOperator2() {
		String text = "and";

		Lexer lexer = new Lexer(text);

		assertEquals(lexer.nextToken().getType(), TokenType.LOGICAL_OPERATOR);
		assertEquals(lexer.getToken().getValue(), "AND");
	}

	@Test
	public void testCallAfterEOF() {
		String text = "query ";

		Lexer lexer = new Lexer(text);

		lexer.nextToken();
		assertEquals(lexer.getToken().getType(), TokenType.NAME);
		assertEquals(lexer.getToken().getValue(), "query");

		lexer.nextToken();

		assertEquals(lexer.getToken().getType(), TokenType.EOF);
		assertEquals(lexer.getToken().getValue(), null);

		assertThrows(LexerException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testConstructor() {
		assertThrows(NullPointerException.class, () -> {
			new Lexer(null);
		});
	}

	@Test
	public void testUnclosedStringLiteral() {
		String text = "\"unclosed";

		Lexer lexer = new Lexer(text);

		assertThrows(LexerException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testOperator1() {
		String text = "!=";

		Lexer lexer = new Lexer(text);

		assertEquals(lexer.nextToken().getType(), TokenType.OPERATOR);
		assertEquals(lexer.getToken().getValue(), "!=");
	}

	@Test
	public void testOperator2() {
		String text = "<=";

		Lexer lexer = new Lexer(text);
		assertEquals(lexer.nextToken().getType(), TokenType.OPERATOR);
		assertEquals(lexer.getToken().getValue(), "<=");

		assertEquals(lexer.nextToken().getType(), TokenType.EOF);
	}

	@Test
	public void testOperator3() {
		String text = "<      ";

		Lexer lexer = new Lexer(text);

		assertEquals(lexer.nextToken().getType(), TokenType.OPERATOR);
		assertEquals(lexer.getToken().getValue(), "<");

		assertEquals(lexer.nextToken().getType(), TokenType.EOF);
	}

	@Test
	public void testOperator4() {
		String text = ">\t";

		Lexer lexer = new Lexer(text);

		assertEquals(lexer.nextToken().getType(), TokenType.OPERATOR);
		assertEquals(lexer.getToken().getValue(), ">");

		assertEquals(lexer.nextToken().getType(), TokenType.EOF);
	}

	@Test
	public void testOperator5() {
		String text = ">=\n";

		Lexer lexer = new Lexer(text);

		assertEquals(lexer.nextToken().getType(), TokenType.OPERATOR);
		assertEquals(lexer.getToken().getValue(), ">=");

		assertEquals(lexer.nextToken().getType(), TokenType.EOF);
	}

	@Test
	public void testOperator6() {
		String text = "!= \n";

		Lexer lexer = new Lexer(text);

		assertEquals(lexer.nextToken().getType(), TokenType.OPERATOR);
		assertEquals(lexer.getToken().getValue(), "!=");

		assertEquals(lexer.nextToken().getType(), TokenType.EOF);
	}
}
