package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Junit tests for all method from class LexerTest.
 * 
 * @author david
 *
 */
class LexerTest {

	@Test
	void testSampleText() {
		String text = "This is sample text.\n\t {$";
		Lexer lexer = new Lexer(text);

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.TEXT);
		assertEquals(token.getValue(), "This is sample text.\n\t ");

		token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.OPENING_TAG);
		assertEquals(token.getValue(), "{$");

		lexer.setLexerState(LexerState.TAG);

		token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.EOF);

		assertThrows(SmartScriptParserException.class, () -> lexer.nextToken());
	}

	@Test
	public void testSpecialCharacter() {
		Lexer lexer = new Lexer("this is { some text{ $");

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.TEXT);
		assertEquals(token.getValue(), "this is { some text{ $");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.EOF);
	}

	@Test
	public void testSpecialCharacter2() {
		Lexer lexer = new Lexer("this is $ } inside { $ the tag");

		lexer.setLexerState(LexerState.TAG);

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.VARIABLE);
		assertEquals(token.getValue(), "this");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.VARIABLE);
		assertEquals(token.getValue(), "is");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.SYMBOL);
		assertEquals(token.getValue(), '$');

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.SYMBOL);
		assertEquals(token.getValue(), '}');

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.VARIABLE);
		assertEquals(token.getValue(), "inside");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.SYMBOL);
		assertEquals(token.getValue(), '{');

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.SYMBOL);
		assertEquals(token.getValue(), '$');

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.VARIABLE);
		assertEquals(token.getValue(), "the");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.VARIABLE);
		assertEquals(token.getValue(), "tag");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.EOF);
		assertEquals(token.getValue(), null);
	}

	@Test
	public void testTags() {
		String input = "{$ FOR i 1 10 1 $}";

		Lexer lexer = new Lexer(input);

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.OPENING_TAG);
		assertEquals(token.getValue(), "{$");

		lexer.setLexerState(LexerState.TAG);

		token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.VARIABLE);
		assertEquals(token.getValue(), "FOR");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.VARIABLE);
		assertEquals(token.getValue(), "i");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.INTEGER);
		assertEquals(token.getValue(), 1);

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.INTEGER);
		assertEquals(token.getValue(), 10);

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.INTEGER);
		assertEquals(token.getValue(), 1);

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.CLOSING_TAG);
		assertEquals(token.getValue(), "$}");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.EOF);
	}

	@Test
	public void testMultipleSpaces() {
		String input = "    \n \n \r \t \n     \n{$";

		Lexer lexer = new Lexer(input);

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.TEXT);
		assertEquals(token.getValue(), "    \n \n \r \t \n     \n");

		token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.OPENING_TAG);

		token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.EOF);
	}

	@Test
	public void testEndTag() {
		String input = "{$END$}";

		Lexer lexer = new Lexer(input);

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.OPENING_TAG);

		lexer.setLexerState(LexerState.TAG);

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.VARIABLE);

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.CLOSING_TAG);

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.EOF);
	}

	@Test
	public void testLongExpression() {
		String input = " sin({$=i$}^2) = {$=   ";

		Lexer lexer = new Lexer(input);

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.TEXT);
		assertEquals(token.getValue(), " sin(");

		token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.OPENING_TAG);

		lexer.setLexerState(LexerState.TAG);

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.SYMBOL);
		assertEquals(token.getValue(), '=');

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.VARIABLE);
		assertEquals(token.getValue(), "i");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.CLOSING_TAG);
		assertEquals(token.getValue(), "$}");

		lexer.setLexerState(LexerState.TEXT);

		token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.TEXT);
		assertEquals(token.getValue(), "^2) = ");

		token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.OPENING_TAG);

		lexer.setLexerState(LexerState.TAG);

		token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.SYMBOL);

		assertEquals(token.getValue(), '=');

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.EOF);
		assertEquals(token.getValue(), null);
	}

	@Test
	public void testString() {
		String input = "{$\"this is string inside ==   # % ! .... \"";
		Lexer lexer = new Lexer(input);

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.OPENING_TAG);

		lexer.setLexerState(LexerState.TAG);

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.STRING);
		assertEquals(token.getValue(), "this is string inside ==   # % ! .... ");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.EOF);
	}

	@Test
	public void testConverToNumber() {
		String input = "{$Name-3.14";

		Lexer lexer = new Lexer(input);

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.OPENING_TAG);

		lexer.setLexerState(LexerState.TAG);

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.VARIABLE);
		assertEquals(token.getValue(), "Name");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.DOUBLE);

		double E = 1e-6;
		assertEquals((double) token.getValue(), -3.14, E);

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.EOF);
	}

	@Test
	public void testUnclosedString() {
		assertThrows(SmartScriptParserException.class, () -> {
			String input = "\"this should be error ";
			Lexer lexer = new Lexer(input);
			lexer.setLexerState(LexerState.TAG);
			@SuppressWarnings("unused")
			Token token = lexer.nextToken();
		});
	}

	@Test
	public void testEqualTokenization() {
		String input1 = "{$ FOR i-1.35bbb\"1\" $}";
		String input2 = "{$ FOR i -1.35 bbb \"1\" $}";

		Lexer lexer1 = new Lexer(input1);
		Lexer lexer2 = new Lexer(input2);

		Token token1 = lexer1.nextToken();
		Token token2 = lexer2.nextToken();

		lexer1.setLexerState(LexerState.TAG);
		lexer2.setLexerState(LexerState.TAG);

		while (!token1.getType().equals(TokenType.CLOSING_TAG)) {
			assertEquals(token1.getType(), token2.getType());
			assertEquals(token1.getValue(), token2.getValue());
			token1 = lexer1.nextToken();
			token2 = lexer2.nextToken();
		}

		lexer1.setLexerState(LexerState.TEXT);
		lexer2.setLexerState(LexerState.TEXT);

		token1 = lexer1.nextToken();
		token2 = lexer2.nextToken();

		assertEquals(token1.getType(), TokenType.EOF);
		assertEquals(token2.getType(), TokenType.EOF);
	}

	@Test
	public void testWordWithDigit() {
		String input = "Word123..--{$";

		Lexer lexer = new Lexer(input);

		Token token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.TEXT);
		assertEquals(token.getValue(), "Word123..--");

		token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.OPENING_TAG);

		lexer.setLexerState(LexerState.TAG);
		token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.EOF);
	}

	@Test
	public void testLexerConstructorException() {
		assertThrows(NullPointerException.class, () -> {
			@SuppressWarnings("unused")
			Lexer lexer = new Lexer(null);
		});
	}

	@Test
	public void testCallSeveralTimes() {
		Lexer lexer = new Lexer("");

		Token token = lexer.nextToken();
		assertEquals(token.getType(), lexer.getToken().getType());
		assertEquals(token.getType(), lexer.getToken().getType());
	}

	@Test
	public void testCallAfterEOF() {
		assertThrows(SmartScriptParserException.class, () -> {
			Lexer lexer = new Lexer("");

			@SuppressWarnings("unused")
			Token token = lexer.nextToken();

			token = lexer.nextToken();
		});
	}

	@Test
	public void testEmptyInput() {
		Lexer lexer = new Lexer("   \r\n\t    {$");

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.TEXT);

		token = lexer.nextToken();
		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.EOF);
	}

	@Test
	public void testValidFunctionName() {
		Lexer lexer = new Lexer("@function_is_valid-10.4");

		lexer.setLexerState(LexerState.TAG);

		Token token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.FUNCTION);
		assertEquals(token.getValue(), "@function_is_valid");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.DOUBLE);
		assertEquals(token.getValue(), -10.4);
	}

	@Test
	public void testFunctionStartSymbol() {
		Lexer lexer = new Lexer("   @ funct_ion_");
		lexer.setLexerState(LexerState.TAG);
		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.SYMBOL);
		assertEquals(token.getValue(), '@');

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.VARIABLE);
		assertEquals(token.getValue(), "funct_ion_");

		token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.EOF);
	}

	@Test
	public void testFunction() {
		Lexer lexer = new Lexer("@function");
		lexer.setLexerState(LexerState.TAG);

		Token token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.FUNCTION);
		assertEquals(token.getValue(), "@function");
	}

	@Test
	public void testNotFunction() {
		Lexer lexer = new Lexer("@1function");
		lexer.setLexerState(LexerState.TAG);
		
		assertThrows(SmartScriptParserException.class, () ->{
			lexer.nextToken();
		});
	}

	@Test
	public void testDoubleTokenType() {
		Lexer lexer = new Lexer("  -3.14-2.11-4.55.");

		lexer.setLexerState(LexerState.TAG);
		Token token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.DOUBLE);
		assertEquals(token.getValue(), -3.14);

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.DOUBLE);
		assertEquals(token.getValue(), -2.11);

		assertThrows(SmartScriptParserException.class, () -> lexer.nextToken());
	}

	@Test
	public void testTag() {
		Lexer lexer = new Lexer("");

		lexer.setLexerState(LexerState.TAG);

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.EOF);
	}

	@Test
	public void testOperator() {
		Lexer lexer = new Lexer("^+/-");

		lexer.setLexerState(LexerState.TAG);

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.OPERATOR);
		assertEquals(token.getValue(), '^');

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.OPERATOR);
		assertEquals(token.getValue(), '+');

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.OPERATOR);
		assertEquals(token.getValue(), '/');

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.OPERATOR);
		assertEquals(token.getValue(), '-');

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.EOF);

	}

	@Test
	public void testEscapeOutsideTheTag1() {
		Lexer lexer = new Lexer(" \\\\  {$");

		Token token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.TEXT);

		assertEquals(token.getValue(), " \\  ");
	}

	@Test
	public void testEscapeOutsideTheTag2() {
		Lexer lexer = new Lexer(" \\{$ ");

		Token token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.TEXT);
		assertEquals(token.getValue(), " {$ ");
	}

	@Test
	public void testEscapeOutsideTheTagException1() {
		Lexer lexer = new Lexer(" \\ ");

		assertThrows(SmartScriptParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testEscapeOutsideTheTagException2() {
		Lexer lexer = new Lexer(" \\a");

		assertThrows(SmartScriptParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testEscapeOutsideTheTagException3() {
		Lexer lexer = new Lexer("\\");

		assertThrows(SmartScriptParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testEscapeOutsideTheTag3() {
		Lexer lexer = new Lexer("\\\\\\\\");

		Token token = lexer.nextToken();

		assertEquals(token.getValue(), "\\\\");
	}

	@Test
	public void testTwoNumbers() {
		Lexer lexer = new Lexer("    1234 \t\n\r 5678    ");

		lexer.setLexerState(LexerState.TAG);

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.INTEGER);
		assertEquals(token.getValue(), 1234);

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.INTEGER);
		assertEquals(token.getValue(), 5678);
	}

	@Test
	public void testEscapeInsideTheTag() {
		Lexer lexer = new Lexer("\" This is inside the string \\\\  \"");

		lexer.setLexerState(LexerState.TAG);

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.STRING);
		assertEquals(token.getValue(), " This is inside the string \\  ");
	}

	@Test
	public void testEscapeInsideTheTag2() {
		Lexer lexer = new Lexer("\" This is inside the string \\\"  \"");

		lexer.setLexerState(LexerState.TAG);

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.STRING);
		assertEquals(token.getValue(), " This is inside the string \"  ");
	}

	@Test
	public void testEscapeInsideTheTag3() {
		Lexer lexer = new Lexer("\"This is some string\\n\"");

		lexer.setLexerState(LexerState.TAG);

		Token token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.STRING);
		assertEquals(token.getValue(), "This is some string\n");
	}

	@Test
	public void testEscapeInsideTheTag4() {
		Lexer lexer = new Lexer("\"This is some string\\t\"");

		lexer.setLexerState(LexerState.TAG);

		Token token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.STRING);
		assertEquals(token.getValue(), "This is some string\t");
	}

	@Test
	public void testEscapeInsideTheTag5() {
		Lexer lexer = new Lexer("\"This is some string\\r\"");

		lexer.setLexerState(LexerState.TAG);

		Token token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.STRING);
		assertEquals(token.getValue(), "This is some string\r");
	}

	@Test
	public void testEscapeInsideTheTagException1() {
		Lexer lexer = new Lexer("\"\\\r\"");

		lexer.setLexerState(LexerState.TAG);

		assertThrows(SmartScriptParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testEscapeInsideTheTagException2() {
		Lexer lexer = new Lexer("\"\\1\"");

		lexer.setLexerState(LexerState.TAG);
		assertThrows(SmartScriptParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testEscapeInsideTheTagException3() {
		Lexer lexer = new Lexer("12414\\\\");

		lexer.setLexerState(LexerState.TAG);

		assertThrows(SmartScriptParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testEscapeInsideTheTagException4() {
		Lexer lexer = new Lexer("Var\\iable");

		lexer.setLexerState(LexerState.TAG);

		assertThrows(SmartScriptParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testEscapeInsideTheTagException5() {
		Lexer lexer = new Lexer("@functio\\n");

		lexer.setLexerState(LexerState.TAG);

		assertThrows(SmartScriptParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testEscapeInsideTheTagException6() {
		Lexer lexer = new Lexer(" \" \\  \"");

		lexer.setLexerState(LexerState.TAG);

		assertThrows(SmartScriptParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testStringExpression() {
		Lexer lexer = new Lexer("\"#$%!#$$%%$######\"     \n\t");

		lexer.setLexerState(LexerState.TAG);

		Token token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.STRING);
		assertEquals(token.getValue(), "#$%!#$$%%$######");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.EOF);
		assertEquals(token.getValue(), null);
	}

	@Test
	public void testEmpty() {
		Lexer lexer = new Lexer("   \r\n\t    ");

		lexer.setLexerState(LexerState.TAG);

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.EOF);
	}

	@Test
	public void testFunction2() {
		String input = "{$= i i * @sin \"0.000\" @decfmt $}";

		Lexer lexer = new Lexer(input);

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.OPENING_TAG);

		lexer.setLexerState(LexerState.TAG);

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.SYMBOL);
		assertEquals(token.getValue(), '=');

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.VARIABLE);
		assertEquals(token.getValue(), "i");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.VARIABLE);
		assertEquals(token.getValue(), "i");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.OPERATOR);
		assertEquals(token.getValue(), '*');

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.FUNCTION);
		assertEquals(token.getValue(), "@sin");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.STRING);
		assertEquals(token.getValue(), "0.000");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.FUNCTION);
		assertEquals(token.getValue(), "@decfmt");
	}

	@Test
	public void testNumberAndDot() {
		Lexer lexer = new Lexer("4353.");

		lexer.setLexerState(LexerState.TAG);

		Token token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.DOUBLE);
		assertEquals(token.getValue(), 4353.0);

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.EOF);
	}
	
	@Test
	public void testEscape2() {
		String text = "{$= \\\" \"String with wrong  escape\"$}";
		
		Lexer lexer = new Lexer(text);
		
		Token token = lexer.nextToken(); 
		
		assertEquals(token.getType(), TokenType.OPENING_TAG);
		
		lexer.setLexerState(LexerState.TAG);
		
		lexer.nextToken();
		
		assertThrows(SmartScriptParserException.class, () -> {
			lexer.nextToken();
		});
	}

	@Test
	public void testEscape() {
		Lexer lexer = new Lexer("{$\"Some \\\\ test X\"$}");
		Token token = lexer.nextToken();

		lexer.setLexerState(LexerState.TAG);

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.STRING);
		assertEquals(token.getValue(), "Some \\ test X");
	}

	@Test
	public void testReviewExample1() {
		String text = "\\{$= i i * @sin \"0.000\" @decfmt $}";

		Lexer lexer = new Lexer(text);

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.TEXT);
		assertEquals(token.getValue(), "{$= i i * @sin \"0.000\" @decfmt $}");
	}

	@Test
	public void testReviewExample2() {
		String text = "\\{$= i i * @sin \"0.000\\\\\" @decfmt $}";

		Lexer lexer = new Lexer(text);

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.TEXT);
		assertEquals(token.getValue(), "{$= i i * @sin \"0.000\\\" @decfmt $}");
	}

	@Test
	public void testReviewExample3() {
		String text = "\\{$= i i * @sin \"0.000\\\n\" @decfmt $}";

		Lexer lexer = new Lexer(text);

		assertThrows(SmartScriptParserException.class, () -> {
			@SuppressWarnings("unused")
			Token token = lexer.nextToken();
		});
	}

	@Test
	public void testReviewExample4() {
		String text = "{$= i i * @sin \"0.000\\n\" @decfmt $}";

		Lexer lexer = new Lexer(text);

		Token token = lexer.nextToken();

		assertEquals(token.getType(), TokenType.OPENING_TAG);
		lexer.setLexerState(LexerState.TAG);

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.SYMBOL);
		assertEquals(token.getValue(), '=');

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.VARIABLE);
		assertEquals(token.getValue(), "i");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.VARIABLE);
		assertEquals(token.getValue(), "i");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.OPERATOR);
		assertEquals(token.getValue(), '*');

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.FUNCTION);
		assertEquals(token.getValue(), "@sin");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.STRING);
		assertEquals(token.getValue(), "0.000\n");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.FUNCTION);
		assertEquals(token.getValue(), "@decfmt");

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.CLOSING_TAG);
		assertEquals(token.getValue(), "$}");

		lexer.setLexerState(LexerState.TEXT);

		token = lexer.nextToken();
		assertEquals(token.getType(), TokenType.EOF);
		assertEquals(token.getValue(), null);
	}
}
