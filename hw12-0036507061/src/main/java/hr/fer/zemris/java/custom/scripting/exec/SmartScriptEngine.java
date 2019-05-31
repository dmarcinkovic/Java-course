package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Engine that is able to execute SmartScript language. This engine get
 * documentNode and traverses through all it's children and performs accept
 * method on them. This engine is able to perform simple mathematical operations
 * such as : multiplication, division, subtraction and addition.
 * 
 * @author David
 *
 */
public class SmartScriptEngine {

	/**
	 * Reference to DocumentNode.
	 */
	private DocumentNode documentNode;

	/**
	 * Reference to RequestContext.
	 */
	private RequestContext requestContext;

	/**
	 * Multistack. Multistack is similar to map, but each key is associated with one
	 * stack in which values are pushed.
	 */
	private ObjectMultistack multistack = new ObjectMultistack();

	/**
	 * Visitor.
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			double initialValue = Double.parseDouble(node.getStartExpression().asText());
			double endValue = Double.parseDouble(node.getEndExpression().asText());
			double step = node.getStepExpression() == null ? 1 : Double.parseDouble(node.getStepExpression().asText());
			String variable = node.getVariable().asText();

			multistack.push(variable, new ValueWrapper(initialValue));

			while (initialValue <= endValue) {
				forLoopChildren(node);

				initialValue = (Double) multistack.pop(variable).getValue();
				initialValue += step;
				multistack.push(variable, new ValueWrapper(initialValue));
			}

			multistack.pop(variable);
		}

		/**
		 * Method that traverses through all children of given node and calls their's
		 * accept methods.
		 * 
		 * @param node Given parent node.
		 */
		private void forLoopChildren(ForLoopNode node) {
			for (int i = 0, n = node.numberOfChildren(); i < n; i++) {
				Node child = node.getChild(i);

				if (child instanceof TextNode) {
					((TextNode) child).accept(this);
				} else if (child instanceof EchoNode) {
					((EchoNode) child).accept(this);
				} else if (child instanceof ForLoopNode) {
					((ForLoopNode) child).accept(this);
				}

			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> stack = new Stack<>();

			Element[] elements = node.getElements();

			for (int i = 0; i < elements.length; i++) {
				if (elements[i] instanceof ElementConstantDouble || elements[i] instanceof ElementConstantInteger) {
					stack.push(elements[i].asText());
				} else if (elements[i] instanceof ElementString) {
					stack.push(elements[i].asText());
				} else if (elements[i] instanceof ElementVariable) {
					Double varaible = (Double) multistack.peek(elements[i].asText()).getValue();
					stack.push(varaible.toString());
				} else if (elements[i] instanceof ElementFunction) {
					performWhenFunction((ElementFunction) elements[i], stack);
				} else if (elements[i] instanceof ElementOperator) {
					performWhenOperator((ElementOperator) elements[i], stack);
				}
			}

			for (Object o : stack) {
				try {
					requestContext.write((String) o);
				} catch (IOException e) {
				}
			}
		}

		/**
		 * Performs specific task when element in EchoNode is Function. Available
		 * functions are : sin, decfmt, dup, swap, setMimeType, paramGet, pparamGet,
		 * pparamSet, tparamGet, tparamSet, tparamDel.
		 * 
		 * @param element Given element.
		 * @param stack   Stack in which elements are pushed.
		 */
		private void performWhenFunction(ElementFunction element, Stack<Object> stack) {
			String function = element.asText();

			switch (function) {
			case "sin":
				sin(stack);
				break;
			case "decfmt":
				decfmt(stack);
				break;
			case "dup":
				dup(stack);
				break;
			case "swap":
				swap(stack);
				break;
			case "setMimeType":
				setMimeType(stack);
				break;
			case "paramGet":
				paramGet(stack);
				break;
			case "pparamGet":
				pparamGet(stack);
				break;
			case "pparamSet":
				pparamSet(stack);
				break;
			case "pparamDel":
				pparamDel(stack);
				break;
			case "tparamGet":
				tparamGet(stack);
				break;
			case "tparamSet":
				tparamSet(stack);
				break;
			case "tparamDel":
				tparamDel(stack);
				break;
			default:
				System.err.println("Error");
				System.exit(1);
			}
		}

		/**
		 * Removes association for name from requestContext persistentParameters map.
		 * 
		 * @param stack Stack from which operators are popped.
		 */
		private void pparamDel(Stack<Object> stack) {
			if (stack.isEmpty()) {
				System.err.println("Error");
				System.exit(1);
			}

			String name = (String) stack.pop();
			requestContext.removePersistentParameter(name);
		}

		/**
		 * Removes association for name from requestContext temporaryParameters map.
		 * 
		 * @param stack Stack from which operators are popped.
		 */
		private void tparamDel(Stack<Object> stack) {
			if (stack.isEmpty()) {
				System.err.println("Error");
				System.exit(1);
			}

			String name = (String) stack.pop();
			requestContext.removeTemporaryParameter(name);
		}

		/**
		 * Stores a value into requestContext temporaryParameters map.
		 * 
		 * @param stack Stack from which operators are popped.
		 */
		private void tparamSet(Stack<Object> stack) {
			if (stack.size() < 2) {
				System.err.println("Error");
				System.exit(1);
			}

			String name = (String) stack.pop();
			String value = (String) stack.pop();
			requestContext.setTemporaryParameter(name, value);
		}

		/**
		 * Same as paramGet but reads from requestContext temporaryParameters map.
		 * 
		 * @param stack Stack from which operators are popped.
		 * @see paramGet.
		 */
		private void tparamGet(Stack<Object> stack) {
			if (stack.size() < 2) {
				System.err.println("Error");
				System.exit(1);
			}

			String defValue = (String) stack.pop();
			String name = (String) stack.pop();
			
			String value = requestContext.getTemporaryParameter(name);
			if (value != null) {
				stack.push(value);
			} else {
				stack.push(defValue);
			}
		}

		/**
		 * Stores a value into requestContext persistantParameter map.
		 * 
		 * @param stack Stack from which operators are popped.
		 */
		private void pparamSet(Stack<Object> stack) {
			if (stack.size() < 2) {
				System.err.println("Error");
				System.exit(1);
			}

			String name = (String) stack.pop();
			String value = (String) stack.pop();

			requestContext.setPersistentParameter(name, value);
		}

		/**
		 * Same as paramGet but reads from requestContext persistantParameters map.
		 * 
		 * @param stack Stack from which operators are popped.
		 */
		private void pparamGet(Stack<Object> stack) {
			if (stack.size() < 2) {
				System.err.println("Error");
				System.exit(1);
			}

			String defValue = (String) stack.pop();
			String name = (String) stack.pop();
			String value = requestContext.getPersistentParameter(name);

			if (value != null) {
				stack.push(value);
			} else {
				stack.push(defValue);
			}
		}

		/**
		 * Obtains from requestContext parameters map a value mapped for name and pushes
		 * it onto stack. If there is not such mapping, it pushes instead defValue onto
		 * stack.
		 * 
		 * @param stack Stack from which operators are popped.
		 */
		private void paramGet(Stack<Object> stack) {
			if (stack.size() < 2) {
				System.err.println("Error");
				System.exit(1);
			}

			String defValue = (String) stack.pop();
			String name = (String) stack.pop();
			String value = requestContext.getParameter(name);

			if (value != null) {
				stack.push(value);
			} else {
				stack.push(defValue);
			}
		}

		/**
		 * Takes string x and calls requestContext.setMimeType(x). Does not produce any
		 * result.
		 * 
		 * @param stack Stack for which operators are popped.
		 */
		private void setMimeType(Stack<Object> stack) {
			if (stack.isEmpty()) {
				System.err.println("Error");
				System.exit(1);
			}
			String x = (String) stack.pop();
			requestContext.setMimeType(x);
		}

		/**
		 * Replaces the order of two topmost items on stack.
		 * 
		 * @param stack Stack for which operators are popped.
		 */
		private void swap(Stack<Object> stack) {
			if (stack.size() < 2) {
				System.err.println("Error");
				System.exit(1);
			}

			Object a = stack.pop();
			Object b = stack.pop();
			stack.push(a);
			stack.push(b);
		}

		/**
		 * Duplicates current top value from stack.
		 * 
		 * @param stack Stack for which operators are popped.
		 */
		private void dup(Stack<Object> stack) {
			if (stack.isEmpty()) {
				System.err.println("Error");
				System.exit(1);
			}

			Object top = stack.pop();
			stack.push(top);
			stack.push(top);
		}

		/**
		 * Formats decimal number using given format f which is compatible with
		 * DecimalFormat; Produces a string.
		 * 
		 * @param stack Stack for which operators are popped.
		 */
		private void decfmt(Stack<Object> stack) {
			if (stack.size() < 2) {
				System.err.println("Erorr");
				System.exit(1);
			}
			String format = (String) stack.pop();
			Double number = Double.parseDouble((String) stack.pop());

			DecimalFormat df = new DecimalFormat(format);
			String result = df.format(number);

			stack.push(result);
		}

		/**
		 * Calculate sinus from given argument and stores the result back to stack.
		 * 
		 * @param stack Stack for which operators are popped.
		 */
		private void sin(Stack<Object> stack) {
			if (stack.isEmpty()) {
				System.err.println("Error");
				System.exit(1);
			}

			Double value = Double.parseDouble((String) stack.pop());
			Double result = Math.sin(value);
			stack.push(result.toString());
		}

		/**
		 * Performs task when current element in EchoNode is operator. Supported
		 * operators are : '+', '*', '/', '-'.
		 * 
		 * @param element ElementOperator. Used to find out operation to be executed.
		 * @param stack   Stack from which operators are popped.
		 */
		private void performWhenOperator(ElementOperator element, Stack<Object> stack) {
			if (stack.size() < 2) {
				System.err.println("Error");
				System.exit(1);
			}

			Object first = stack.pop();
			Object second = stack.pop();

			if (!(first instanceof String) || !(second instanceof String)) {
				System.err.println("Error");
				System.exit(1);
			}
			String operator = element.asText();

			Double result = null;
			try {
				result = performOperation(first, second, operator);
			} catch (NumberFormatException e) {
				System.err.println("Error");
				System.exit(1);
			}
			if (result == null) {
				System.err.println("Error");
				System.exit(1);
			}
			stack.push(result.toString());
		}

		/**
		 * Performs operation. It convert objects first and second to double, executes
		 * operator and pushes result again to stack.
		 * 
		 * @param first    First operand.
		 * @param second   Second operand.
		 * @param operator Operation to be performed.
		 * @return Result.
		 */
		private Double performOperation(Object first, Object second, String operator) {
			double value1 = Double.parseDouble((String) first);
			double value2 = Double.parseDouble((String) second);

			switch (operator) {
			case "+":
				return value1 + value2;
			case "-":
				return value1 - value2;
			case "/":
				return value2 == 0 ? null : value1 / value2;
			case "*":
				return value1 * value2;
			}
			return null;
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
				}
			}

		}
	};

	/**
	 * Constructor to initialize documentNode and requestContext.
	 * 
	 * @param documentNode   Reference to DocumentNode.
	 * @param requestContext Reference to RequestContext.
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Calls accept on instance of DocumentNode.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}
