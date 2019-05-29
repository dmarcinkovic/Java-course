package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Program that reads smart script from file and parse it into a tree and then
 * reproduces its original form onto standard output.
 * 
 * @author David
 *
 */
public class TreeWriter {

	/**
	 * Method invoked when running the program. This method accepts one argument via
	 * command line. This argument represents path to file which content is smart
	 * script. It reads that content and creates tree. After that, using Visitor
	 * design pattern, reproduces original script and prints it to standard output.
	 * 
	 * @param args Argument provided via command line. Only one argument is
	 *             expected: path to file which content is one smart script.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Wrong number of arguments. I will stop this program.");
			return;
		}

		Path file = Paths.get(args[0]);
		List<String> lines = null;
		try {
			lines = Files.readAllLines(file);
		} catch (IOException e) {
			System.out.println("Cannot open file.");
			return;
		} catch (SmartScriptParserException e) {
			System.out.println("Cannot parse.");
			return;
		}

		String text = getText(lines);
		SmartScriptParser parser = new SmartScriptParser(text);
		WriterVisitor visitor = new WriterVisitor();
		parser.getDocumentNode().accept(visitor);

		System.out.println(visitor.toString());

	}

	/**
	 * Implementation of INodeVisitor. This is visitor in Visitor desing pattern.
	 * 
	 * @author David
	 *
	 */
	private static class WriterVisitor implements INodeVisitor {

		/**
		 * StringBuilder in which all nodes in text form are stored.
		 */
		private StringBuilder sb = new StringBuilder();

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTextNode(TextNode node) {
			sb.append(node.getText()).append(" ");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			appendString(node);
			
			for (int i = 0, n = node.numberOfChildren(); i<n; i++) {
				Node child = node.getChild(i);
				
				if (child instanceof TextNode) {
					((TextNode)child).accept(this);
				}else if (child instanceof EchoNode) {
					((EchoNode)child).accept(this);
				}else if (child instanceof ForLoopNode){
					((ForLoopNode)child).accept(this);
				}
				
			}
		}
		
		/**
		 * Appends all forLoopNode's elements to StringBuilder.
		 * @param node ForLoopNode.
		 */
		private void appendString(ForLoopNode node) {
			sb.append("{$ ").append("FOR ");

			String variable = node.getVariable().asText();
			String startExpression = node.getStartExpression().asText();
			String endExpression = node.getEndExpression().asText();
			Element stepExpression = node.getStepExpression();

			sb.append(variable).append(" ").append(startExpression).append(" ").append(endExpression).append(" ");

			if (stepExpression != null) {
				sb.append(stepExpression.asText()).append(" ");
			}

			sb.append("$}");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			sb.append("{$= ");

			Element[] elements = node.getElements();

			for (int i = 0; i < elements.length; i++) {
				String str = elements[i].asText();
				if (elements[i] instanceof ElementFunction) {
					sb.append("@").append(str);
				} else if (elements[i] instanceof ElementString) {
					str = getElementString(str);
					sb.append("\"").append(str).append("\"");
				} else {
					sb.append(str);
				}

				sb.append(" ");
			}

			sb.append("$}");
		}

		/**
		 * Method that converts reconstructs original string from given one. This method
		 * adds escaping character before each whitespace.
		 * 
		 * @param elementString String
		 * @return Original string.
		 */
		private String getElementString(String elementString) {
			StringBuilder sb = new StringBuilder();

			for (int i = 0, n = elementString.length(); i < n; i++) {
				char c = elementString.charAt(i);
				if (c == '\r') {
					sb.append("\\r");
				} else if (c == '\t') {
					sb.append("\\t");
				} else if (c == '\n') {
					sb.append("\\n");
				} else if (c == '\\') {
					sb.append("\\\\");
				} else if (c == '\"') {
					sb.append("\\\"");
				} else {
					sb.append(c);
				}
			}

			return sb.toString();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int index = 0, n = node.numberOfChildren(); index < n; index++) {
				Node child = node.getChild(index);

				if (child instanceof TextNode) {
					((TextNode) child).accept(this);
				} else if (child instanceof EchoNode) {
					((EchoNode) child).accept(this);
				} else if (child instanceof ForLoopNode) {
					((ForLoopNode) child).accept(this);
					sb.append("{$END$}");
				}
			}
		}

		/**
		 * Returns original smart script.
		 */
		public String toString() {
			return sb.toString();
		}

	}

	/**
	 * Convert list of Strings to one string.
	 * 
	 * @param list List of strings to be converted.
	 * @return String obtained by concatenating all String presented in list.
	 */
	private static String getText(List<String> list) {
		StringBuilder sb = new StringBuilder();

		for (String s : list) {
			sb.append(s).append("\n");
		}

		return sb.toString();
	}
}
