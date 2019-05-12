package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Class to test SmartScriptParser. Program expects exactly one argument in
 * command line: path to the file that contains text that will be parsed. If
 * wrong number of arguments is provided program writes appropriate message and
 * exits.
 * 
 * @author david
 *
 */
public class SmartScriptTester {

	/**
	 * Method invoked when running the program. Arguments are explained bellow.
	 * 
	 * @param args Arguments provided via command line. Exactly one argument is
	 *             expected : path to the file that contains text that will be
	 *             parsed.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Wrong number of arguments");
			return;
		}

		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}

		System.out.println("Original text");
		System.out.println(docBody);

		DocumentNode document = parser.getDocumentNode();

		System.out.println();
		System.out.println("Original document body");
		printNodes(document, 0);

		String originalDocumentBody = createOriginalDocumentBody(document);

		System.out.println();
		System.out.println("Created from given document body");
		System.out.println(originalDocumentBody);

		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();

		System.out.println();
		System.out.println("Created from created document body.");
		printNodes(document2, 0);

	}

	/**
	 * Method to create original text from given document model.
	 * 
	 * @param document Document model that will be converted to original text.
	 * @return Original text.
	 */
	private static String createOriginalDocumentBody(DocumentNode document) {
		StringBuilder sb = new StringBuilder();

		return getDocumentModelAsString(document, sb);
	}

	/**
	 * Helping method to convert given document model to string.
	 * 
	 * @param document Given document model.
	 * @param sb       Reference to StringBuilder.
	 * @return String representation of given document model.
	 */
	private static String getDocumentModelAsString(Node document, StringBuilder sb) {

		printDocumentBody(document, sb);
		for (int i = 0; i < document.numberOfChildren(); i++) {
			getDocumentModelAsString(document.getChild(i), sb);
		}

		if (document instanceof ForLoopNode) {
			sb.append("{$END$}");
		}

		return sb.toString();
	}

	/**
	 * Helping method that converts one instance of document to String.
	 * 
	 * @param document Instance of document to be converted to String.
	 * @param sb       Reference to StringBuilder.
	 */
	private static void printDocumentBody(Node document, StringBuilder sb) {
		if (document instanceof EchoNode) {
			sb.append("{$= ");

			Element[] elements = ((EchoNode) document).getElements();

			for (int i = 0; i < elements.length; i++) {
				String str = elements[i].asText();
				sb.append(str).append(" ");
			}

			sb.append("$}");
		} else if (document instanceof ForLoopNode) {
			sb.append("{$ ").append("FOR ");

			String variable = ((ForLoopNode) document).getVariable().asText();
			String startExpression = ((ForLoopNode) document).getStartExpression().asText();
			String endExpression = ((ForLoopNode) document).getEndExpression().asText();
			Element stepExpression = ((ForLoopNode) document).getStepExpression();

			String stepsExpression = stepExpression != null ? stepExpression.asText() : null;

			sb.append(variable).append(" ");
			sb.append(startExpression).append(" ");
			sb.append(endExpression).append(" ");

			if (stepsExpression != null) {
				sb.append(stepsExpression).append(" ");
			}

			sb.append("$}");
		} else if (document instanceof TextNode) {
			String text = ((TextNode) document).getText();

			sb.append(text);
		}
	}

	/**
	 * Method to get original text from given element structure.
	 * 
	 * @param element Element structure.
	 * @return Original text.
	 */
	@SuppressWarnings("unused")
	private String getOriginalText(Element element) {
		StringBuilder sb = new StringBuilder();
		char[] array = element.asText().toCharArray();

		if (element instanceof ElementFunction) {
			sb.append('@').append(element.asText());
		} else if (element instanceof ElementString) {
			sb.append('"');
			for (int i = 0; i < array.length; i++) {
				if (array[i] == '"' || array[i] == '\\') {
					sb.append("\\").append(array[i]);
				} else if (array[i] == '\n') {
					sb.append("\\\n");
				} else if (array[i] == '\t') {
					sb.append("\\\t");
				} else if (array[i] == '\r') {
					sb.append("\\\r");
				}
			}
			sb.append('"');
		} else {
			sb.append(element.asText());
		}

		return sb.toString();
	}

	/**
	 * Method to print nodes in hierarchical manner.
	 * 
	 * @param node  Parent node.
	 * @param level Current level in recursion.
	 */
	private static void printNodes(Node node, int level) {
		for (int i = 0; i < level; i++) {
			System.out.print("    ");
		}
		System.out.println(node.getClass().getSimpleName());

		for (int i = 0; i < node.numberOfChildren(); i++) {
			printNodes(node.getChild(i), level + 1);
		}
	}
}
