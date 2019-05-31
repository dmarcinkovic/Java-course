package hr.fer.zemris.java.gui.calc;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.MyButton;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Represents all function available in calculator. Those are: "1/x" , "sin",
 * "cos" , "log", "ln", "tan", "ctg", "x^n", "arcsin", "arccos", "arctan",
 * "arcctg", "e^x", "10^x", x^(1/n).
 * 
 * @author david
 *
 */
public class Functions implements ICalculator {
	/**
	 * Calculator model. Used to communicate with calculator display.
	 */
	private CalcModelImpl model;

	/**
	 * Container in which function buttons are added.
	 */
	private Container container;

	/**
	 * List of all buttons.
	 */
	private List<JButton> buttons;

	/**
	 * Names of functions.
	 */
	private List<String> text;

	/**
	 * Names of inverse functions.
	 */
	private List<String> inverseText;

	/**
	 * Boolean flag that is equal to true if we have to display inverse functions.
	 */
	private boolean inverse;

	/**
	 * Constructor to initialize model and container in which function buttons are
	 * added.
	 * 
	 * @param model     Calculator model. Used to communicate with calculator
	 *                  display.
	 * @param container Container in which function buttons are added.
	 */
	public Functions(CalcModel model, Container container) {
		this.model = (CalcModelImpl) model;
		this.container = container;

		buttons = new ArrayList<>();

		text = new ArrayList<>(Arrays.asList("1/x", "sin", "log", "cos", "ln", "tan", "ctg"));

		inverseText = new ArrayList<>(Arrays.asList("1/x", "arcsin", "10^x", "arccos", "e^x", "arctan", "arcctg"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addButtons() {
		addButton("1/x", 2, 1);
		addButton("sin", 2, 2);
		addButton("log", 3, 1);
		addButton("cos", 3, 2);
		addButton("ln", 4, 1);
		addButton("tan", 4, 2);
		addButton("ctg", 5, 2);
	}

	/**
	 * Swap function from normal to inverse and vice versa.
	 */
	public void swap() {
		toggle();

		if (inverse) {
			setText(inverseText);
		} else {
			setText(text);
		}
	}

	/**
	 * Set text to all buttons. Used in swapping between normal and inverse function
	 * and vice versa.
	 * 
	 * @param text List of new names of buttons.
	 */
	private void setText(List<String> text) {
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).setText(text.get(i));
		}
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
	 * Add button with specified text to layout to specified row and column.
	 * 
	 * @param text Text that will appear on button.
	 * @param row  Row in which button is placed within Container.
	 * @param col  Column in which button is placed within Container.
	 */
	private void addButton(String text, int row, int col) {
		RCPosition constraint = new RCPosition(row, col);

		JButton button = new MyButton(text);

		buttons.add(button);

		container.add(button, constraint);

		button.addActionListener(e -> {
			JButton source = (JButton) e.getSource();

			String functionName = source.getText();
			doOperation(functionName);
		});
	}

	/**
	 * Perform specified operation with given name as method parameter. Available
	 * functions are: "1/x" , "sin", "cos" , "log", "ln", "tan", "ctg", "x^n",
	 * "arcsin", "arccos", "arctan", "arcctg", "e^x", "10^x", x^(1/n).
	 * 
	 * @param name Name of specified function.
	 */
	private void doOperation(String name) {
		double number = model.getValue();
		double result = 0;
		switch (name) {
		case "1/x":
			result = 1./number;
			break;
		case "sin":
			result = Math.sin(number);
			break;
		case "log":
			result = Math.log10(number);
			break;
		case "cos":
			result = Math.cos(number);
			break;
		case "ln":
			result = Math.log(number);
			break;
		case "tan":
			result = Math.tan(number);
			break;
		case "ctg":
			result = 1. / Math.tan(number);
			break;
		case "arcsin":
			result = Math.asin(number);
			break;
		case "10^x":
			result = Math.pow(10, number);
			break;
		case "arccos":
			result = Math.acos(number);
			break;
		case "e^x":
			result = Math.pow(Math.E, number);
			break;
		case "arctan":
			result = Math.atan(number);
			break;
		case "arcctg":
			result = Math.atan(1. / number);
			break;
		}
		model.setResult(result);
	}
}
