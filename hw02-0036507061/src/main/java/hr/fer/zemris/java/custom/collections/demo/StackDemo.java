package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class to represent how stack is used on very simple problem. Given postfix
 * expression program calculates the result and prints in to standard output.
 * 
 * @author david
 *
 */
public class StackDemo {

	/**
	 * Method start when running the program. Method evaluates expression given in
	 * postfix form.
	 * 
	 * @param args Arguments given via command line. Only one argument is expected.
	 *             Argument represents postfix expression to be evaluated.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Wrong number of arguments.");
			return;
		}

		String[] expression = args[0].split(" ");
		ObjectStack stack = new ObjectStack();

		for (String s : expression) {
			try {
				int number = Integer.parseInt(s);
				stack.push(number);
			} catch (NumberFormatException e) {
				
				int operand1 = 0;
				int operand2 = 0;
				
				try {
					operand1 = (int) stack.pop();
					operand2 = (int) stack.pop();
				}catch(EmptyStackException e1) {
					System.out.println("Expression is invalid!");
					return;
				}
				
				int result = 0;
				switch (s) {
				case "+":
					result = operand2 + operand1;
					break;
				case "-":
					result = operand2 - operand1;
					break;
				case "*":
					result = operand2 * operand1;
					break;
				case "/":
					if (operand1 == 0) {
						System.out.println("Cannot divide by zero.");
						return;
					}
					result = operand2 / operand1;
					break;
				case "%":
					if (operand1 == 0) {
						System.out.println("Cannot divide by zero.");
						return;
					}
					result = operand2 % operand1;
					break;
				default : 
					System.out.println("Expression is invalid!");
					return;
				} 
				stack.push(result);
			}
		}

		if (stack.size() != 1) {
			System.out.println("Expression is invalid.");
		} else {
			System.out.println("Expression evaluates to " + stack.pop() + ".");
		}

	}
}
