package hr.fer.zemris.java.gui.calc;

import java.awt.Container;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.MyButton;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Represents all operators available in our basic calculator. Those are : +, -,
 * /, *, =, x^n, x^(1/n).
 * 
 * @author david
 *
 */
public class Operators implements ICalculator {
	/**
	 * Calculator model. Used to communicate with calculator display.
	 */
	private CalcModelImpl model;

	/**
	 * Container in which function buttons are added.
	 */
	private Container container;

	/**
	 * Boolean flag that is equal to true if we have to display inverse functions.
	 */
	private boolean inverse;

	/**
	 * Reference to button that have inverse function. This button is initially x^n.
	 */
	private JButton button;

	/**
	 * Constructor to initialize model and container in which operator buttons are
	 * added.
	 * 
	 * @param model     Calculator model. Used to communicate with calculator
	 *                  display.
	 * @param container Container in which function buttons are added.
	 */
	public Operators(CalcModel model, Container container) {
		this.model = (CalcModelImpl) model;
		this.container = container;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addButtons() {
		addButton("=", 1, 6);
		addButton("/", 2, 6);
		addButton("*", 3, 6);
		addButton("-", 4, 6);
		addButton("+", 5, 6);
		addButton("x^n", 5, 1);
	}

	/**
	 * Toggle inverse flag. If it was false now it will be true and if it was true
	 * now it will be false.
	 */
	private void toggle() {
		if (inverse) {
			inverse = false;
		} else {
			inverse = true;
		}
	}

	/**
	 * Set from x^n to x^(1/n) and vice versa. When user wants to, for example, use
	 * x^(1/n) it must check the check box in right corner.
	 * 
	 */
	public void swap() {
		toggle();

		if (inverse) {
			button.setText("x^(1/n)");
		} else {
			button.setText("x^n");
		}
	}

	/**
	 * Add button with specified text to layout to specified row and column.
	 * 
	 * @param text Text that will appear on button.
	 * @param row  Row in which button is placed within Container.
	 * @param col  Column in which button is placed within Container.
	 */
	private void addButton(String text, int row, int col) {
		RCPosition constraint = new RCPosition(row, col);

		JButton button = new MyButton(text);

		if (text.equals("x^n")) {
			this.button = button;
		}

		container.add(button, constraint);

		button.addActionListener(e -> {
			JButton source = (JButton) e.getSource();

			doAction(source.getText());
		});
	}

	/**
	 * Perform action based on what button is pressed. If button '=' is pressed this
	 * method will perform specified operation between last two typed values. If
	 * button is '/' this function will store the first number and wait for used to
	 * enter second number in able to perform division. Same is for +, - and any
	 * other binary operators presented in this calculator.
	 * 
	 * @param text Name of the operator.
	 */
	private void doAction(String text) {
		switch (text) {
		case "=":
			equal();
			break;
		case "/":
			divide();
			break;
		case "*":
			multiply();
			break;
		case "-":
			minus();
			break;
		case "+":
			plus();
			break;
		case "x^n":
			exponent();
			break;
		case "x^(1/n)":
			exponentInverse();
			break;
		}
	}

	/**
	 * This method will perform specified operation between last two typed values
	 * and print result to display.
	 */
	private void equal() {
		if (model.isActiveOperandSet()) {
			double first = model.getActiveOperand();
			double second = model.getValue();

			DoubleBinaryOperator operator = model.getPendingBinaryOperation();

			model.setValue(operator.applyAsDouble(first, second));
			model.clearWithoutInforming();

			model.clearActiveOperand();
		} else {
			model.printErrorMessage("Error");
			model.clearWithoutInforming();
		}
	}

	/**
	 * This method will perform divide operation between first number and second
	 * number. This method gets first number and then wait for the user to enter the
	 * second number in order to be able to perform operation.
	 */
	private void divide() {
		model.setActiveOperand(model.getValue());
		model.setPendingBinaryOperation((t1, t2) -> t1 / t2);
	}

	/**
	 * Performs multiplication between tow numbers. This method gets first number
	 * and then wait for the user to enter the second number in order to be able to
	 * perform operation.
	 */
	private void multiply() {
		model.setActiveOperand(model.getValue());
		model.setPendingBinaryOperation((t1, t2) -> t1 * t2);
	}

	/**
	 * Perform subtraction between two numbers. This method gets first number and
	 * then wait for the user to enter the second number in order to be able to
	 * perform operation.
	 */
	private void minus() {
		model.setActiveOperand(model.getValue());
		model.setPendingBinaryOperation((t1, t2) -> t1 - t2);
	}

	/**
	 * Performs + operation between two numbers. This method gets first number and
	 * then wait for the user to enter the second number in order to be able to
	 * perform operation.
	 */
	private void plus() {
		model.setActiveOperand(model.getValue());
		model.setPendingBinaryOperation((t1, t2) -> t1 + t2);
	}

	/**
	 * Calculates x^n here x is the first number typed in calculator, and n is
	 * second one. This method gets first number and then wait for the user to enter
	 * the second number in order to be able to perform operation.
	 */
	private void exponent() {
		model.setActiveOperand(model.getValue());
		model.setPendingBinaryOperation((t1, t2) -> Math.pow(t1, t2));
	}

	/**
	 * Calculates x^(1/n) where x is the first number typed in calculator, and n is
	 * second one. This method gets first number and then wait for the user to enter
	 * the second number in order to be able to perform operation.
	 */
	private void exponentInverse() {
		model.setActiveOperand(model.getValue());
		model.setPendingBinaryOperation((t1, t2) -> Math.pow(t1, 1. / t2));
	}
}
