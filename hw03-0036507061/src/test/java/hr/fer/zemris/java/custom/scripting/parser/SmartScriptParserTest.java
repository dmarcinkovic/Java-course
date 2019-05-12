package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Junit tests for all method from class SmartScriptParser.
 * 
 * @author david
 *
 */
class SmartScriptParserTest {
	
	private static void printNodes(Node node, int level) {
		for (int i = 0; i < level; i++) {
			System.out.print("    ");
		}
		System.out.println(node.getClass().getSimpleName());

		for (int i = 0; i < node.numberOfChildren(); i++) {
			printNodes(node.getChild(i), level+1);
		}
	}
	
	@Test
	public void testSample() {
		String document = loader("document1.txt");
		
		SmartScriptParser parser = new SmartScriptParser(document);
		
		DocumentNode node = parser.getDocumentNode();
		
		printNodes(node, 0);
		
		String expected = getHierarchyModel(node);
		String actual = "DocumentNode\n  TextNode\n  ForLoopNode\n    TextNode\n    EchoNode\n    "
				+ "TextNode\n  TextNode\n  ForLoopNode\n    TextNode\n    "
				+ "EchoNode\n    TextNode\n    EchoNode\n    TextNode\n";
		
		assertEquals(expected, actual, "Should be equals to true");
		
		TextNode textNode = (TextNode)node.getChild(0);
		
		assertEquals(textNode.getText(), "This is sample text.\n");
		
		ForLoopNode forLoop = (ForLoopNode)node.getChild(1);
		
		assertEquals(forLoop.getVariable().asText(), "i");
		assertEquals(forLoop.getStartExpression().asText(), "1");
		assertEquals(forLoop.getEndExpression().asText(), "10");
		assertEquals(forLoop.getStepExpression().asText(), "1");
		
		textNode = (TextNode)forLoop.getChild(0);
		assertEquals(textNode.getText(), "\n This is ");
		
		textNode = (TextNode)forLoop.getChild(2);
		assertEquals(textNode.getText(), "-th time this message is generated.\n");
	}

	@Test
	public void testMissingClosingTag() {

		assertThrows(SmartScriptParserException.class, () -> {
			String document = loader("document2.txt");
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser(document);
		});

	}

	@Test
	public void testMissingEndTag() {
		assertThrows(SmartScriptParserException.class, () -> {
			String document = loader("document3.txt");
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser(document);
		});
	}

	@Test
	public void testForLoopTooManyArguments() {
		assertThrows(SmartScriptParserException.class, () -> {
			String text = "{$ FOR year 1 10 \"1\" \"10\" $} {$END$}";
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser(text);
		});
	}

	@Test
	public void testNumberAsVariableException() {
		assertThrows(SmartScriptParserException.class, () -> {
			String text = "{$ FOR 3 1 10 1 $} {$END$}";
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser(text);
		});
	}

	@Test
	public void testOperatorAsVariableException() {
		assertThrows(SmartScriptParserException.class, () -> {
			String text = "{$ FOR * \"1\" -10 \"1\" $} {$END$}";
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser(text);
		});
	}

	@Test
	public void testFunctionElementException() {
		assertThrows(SmartScriptParserException.class, () -> {
			String text = "{$ FOR year @sin 10 $} {$END$}";
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser(text);
		});
	}

	@Test
	public void testTooFewArguments() {
		assertThrows(SmartScriptParserException.class, () -> {
			String text = "{$ FOR year $} {$END$}";
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser(text);
		});
	}

	@Test
	public void testCorrectForTag() {
		String text = "{$ FOR sco_re \"-1\"10 \"1\" $}{$END$}";
		SmartScriptParser parser = new SmartScriptParser(text);

		Node node = parser.getDocumentNode();
		String expected = getHierarchyModel(node);
		String actual = "DocumentNode\n  ForLoopNode\n";

		assertEquals(expected, actual, "Should be equal to true");
	}

	@Test
	public void testCorrectTagWithTwoArguments() {
		String text = "{$ FOR var 123 1$}  {$END$}";

		SmartScriptParser parser = new SmartScriptParser(text);

		Node node = parser.getDocumentNode();
		String expected = getHierarchyModel(node);
		String actual = "DocumentNode\n  ForLoopNode\n    TextNode\n";

		assertEquals(expected, actual);
	}

	@Test
	public void testWrongVariableName() {
		String text = "{$ FOR _i 3.14 5 $}{$END$}";

		assertThrows(SmartScriptParserException.class, () -> {
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser(text);
		});
	}

	@Test
	public void testWrongFunctionName() {
		String text = "{$= @1function $}";

		assertThrows(SmartScriptParserException.class, () -> {
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser(text);
		});
	}

	/*
	 * If { character is escaped then whole expression is considered as string, so
	 * Document model of test below consists only of DocumentNode and its only child
	 * TextNode.
	 */
	@Test
	public void testEscapedStartingTag() {
		String text = "\\{$        FOR     var   123 1$}  ";

		SmartScriptParser parser = new SmartScriptParser(text);
		Node node = parser.getDocumentNode();

		String expected = getHierarchyModel(node);
		String actual = "DocumentNode\n  TextNode\n";
		assertEquals(expected, actual);
	}

	@Test
	public void testFromDocument4() {
		String document = loader("document4.txt");

		SmartScriptParser parser = new SmartScriptParser(document);
		Node node = parser.getDocumentNode();

		String expected = getHierarchyModel(node);
		String actual = "DocumentNode\n  EchoNode\n  TextNode\n  EchoNode\n";
		assertEquals(expected, actual);
	}

	@Test
	public void testFromDocument5() {
		String document = loader("document5.txt");

		SmartScriptParser parser = new SmartScriptParser(document);
		Node node = parser.getDocumentNode();

		String expected = getHierarchyModel(node);
		String actual = "DocumentNode\n  EchoNode\n  TextNode\n  EchoNode\n";
		assertEquals(expected, actual);
	}

	@Test
	public void testFromDocument6() {
		String document = loader("document6.txt");

		SmartScriptParser parser = new SmartScriptParser(document);
		Node node = parser.getDocumentNode();
		String expected = getHierarchyModel(node);
		String actual = "DocumentNode\n  EchoNode\n  TextNode\n  EchoNode\n  ForLoopNode\n    EchoNode\n";
		assertEquals(expected, actual);
	}

	@Test
	public void testMissingStartTag() {
		String text = "FOR {$END$}";

		assertThrows(SmartScriptParserException.class, () -> {
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser(text);
		});
	}

	@Test
	public void testWrongEscapeException() {
		String text = "This is some text with \\b wrong escape.";

		assertThrows(SmartScriptParserException.class, () -> {
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser(text);
		});
	}

	@Test
	public void testWrongEscapeInStringInsideTag() {
		String text = "{$= \\\" \"String with wrong  escape\"$}";
		assertThrows(SmartScriptParserException.class, () -> {
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser(text);
		});
	}
	

	@Test
	public void testExampleFromHomeWork() {
		String text = "Example \\{$=1$}. Now actually write one {$=1$}";

		SmartScriptParser parser = new SmartScriptParser(text);
		Node node = parser.getDocumentNode();

		String expected = getHierarchyModel(node);
		String actual = "DocumentNode\n  TextNode\n  EchoNode\n";
		assertEquals(expected, actual);
	}

	
	@Test
	public void testStringEscape() {
		String text = "\" this is string    \"";
		
		SmartScriptParser parser = new SmartScriptParser(text);
		Node node = parser.getDocumentNode();
		
		String expected = getHierarchyModel(node);
		String actual = "DocumentNode\n  TextNode\n";
		assertEquals(expected, actual);
	}
	
	
	@Test
	public void testRewievExample1() {
		String text = "\\{$= i i * @sin \"0.000\" @decfmt $}";
		
		SmartScriptParser parser = new SmartScriptParser(text);
		
		Node node = parser.getDocumentNode();
		
		String expected = getHierarchyModel(node);
		String actual = "DocumentNode\n  TextNode\n";
		
		assertEquals(expected, actual);
		
		TextNode textNode = (TextNode)node.getChild(0); 
		
		assertEquals(textNode.getText(), "{$= i i * @sin \"0.000\" @decfmt $}");
	}
	
	@Test
	public void testRewievExample2() {
		String text = "\\{$= i i * @sin \"0.000\\\\\" @decfmt $}";
		
		SmartScriptParser parser = new SmartScriptParser(text);
		
		Node node = parser.getDocumentNode();
		
		String expected = getHierarchyModel(node);
		String actual = "DocumentNode\n  TextNode\n";
		
		assertEquals(expected, actual);
		
		TextNode textNode = (TextNode)node.getChild(0); 
		
		assertEquals(textNode.getText(), "{$= i i * @sin \"0.000\\\" @decfmt $}");
	}
	
	@Test
	public void testRewievExample3() {
		String text = "\\{$= i i * @sin \"0.000\\n\" @decfmt $}";
		
		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(text);
		});
	}
	
	@Test
	public void testReviewExample4() {
		String text = "{$= i i * @sin \"0.000\\n\" @decfmt $}";
		
		SmartScriptParser parser = new SmartScriptParser(text);
		
		Node node = parser.getDocumentNode();
		
		String expected = getHierarchyModel(node);
		String actual = "DocumentNode\n  EchoNode\n";
		
		assertEquals(expected, actual);
		
		EchoNode echo =(EchoNode) node.getChild(0);
		
		Element[] elements = echo.getElements();
		
		assertEquals(elements[0].asText(), "i");
		assertTrue(elements[0] instanceof ElementVariable);
		
		assertEquals(elements[1].asText(), "i");
		assertTrue(elements[1] instanceof ElementVariable);
		
		assertEquals(elements[2].asText(), "*");
		assertTrue(elements[2] instanceof ElementOperator);
		
		assertEquals(elements[3].asText(), "sin");
		assertTrue(elements[3] instanceof ElementFunction);
		
		assertEquals(elements[4].asText(), "0.000\n");
		assertTrue(elements[4] instanceof ElementString);
		
		assertEquals(elements[5].asText(), "decfmt");
		assertTrue(elements[5] instanceof ElementFunction);
	}
	
	/**
	 * Private method to load text from given file and convert it to String.
	 * 
	 * @param filename Path to file.
	 * @return Converted String.
	 */
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}

	/**
	 * Private method to get hierarchy model of node.
	 * 
	 * @param node Node to get hierarchy model for.
	 * @return String representation of hierarchy.
	 */
	private String getHierarchyModel(Node node) {
		StringBuilder sb = new StringBuilder();

		documentToString(sb, node, 0);

		return sb.toString();
	}

	/**
	 * Method that traverse through all children of nodes and converts it to String.
	 * 
	 * @param sb    StringBuilder to collect all Strings.
	 * @param node  Node to get String representation.
	 * @param level Depth of given graph.
	 */
	private void documentToString(StringBuilder sb, Node node, int level) {
		for (int i = 0; i < level; i++) {
			sb.append("  ");
		}
		sb.append(node.getClass().getSimpleName());
		sb.append("\n");

		for (int i = 0; i < node.numberOfChildren(); i++) {
			documentToString(sb, node.getChild(i), level + 1);
		}
	}
	
}
