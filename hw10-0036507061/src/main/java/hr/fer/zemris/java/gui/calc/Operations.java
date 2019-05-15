package hr.fer.zemris.java.gui.calc;

import java.awt.Container;
import java.util.Stack;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.MyButton;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Represents all operations available in this calculator. Those are : push,
 * pop, clr, reset. Push operation adds current number to stack. Pop operation
 * pops number from stack and displays it to calculator display. Clr operation
 * clears current number from display. Reset operation clears current and active
 * operand.
 * 
 * @author david
 *
 */
public class Operations implements ICalculator {
	/**
	 * Calculator model. Used to communicate with calculator display.
	 */
	private CalcModelImpl model;

	/**
	 * Container in which function buttons are added.
	 */
	private Container container;

	/**
	 * Stack to store numbers.
	 */
	private Stack<Double> stack;

	/**
	 * Constructor to initialize model and container in which operation buttons are
	 * added.
	 * 
	 * @param model     Calculator model. Used to communicate with calculator
	 *                  display.
	 * @param container Container in which function buttons are added.
	 */
	public Operations(CalcModel model, Container container) {
		this.model = (CalcModelImpl) model;
		this.container = container;
		stack = new Stack<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addButtons() {
		addButton("clr", 1, 7);
		addButton("reset", 2, 7);
		addButton("push", 3, 7);
		addButton("pop", 4, 7);
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

		container.add(button, constraint);

		button.addActionListener(e -> {
			JButton source = (JButton) e.getSource();

			doAction(source.getText());
		});
	}

	/**
	 * Performs specified action with given name. Clr operation clears current
	 * number from display. Reset operation clears current and active operand. Push
	 * operation adds current number to stack. op operation pops number from stack
	 * and displays it to calculator display.
	 * 
	 * @param text Name of specified operation.
	 */
	private void doAction(String text) {
		switch (text) {
		case "clr":
			model.clear();
			break;
		case "reset":
			model.clearAll();
			break;
		case "push":
			stack.push(model.getValue());
			break;
		case "pop":
			pop();
			break;
		}
	}

	/**
	 * Performs pop operation. Pops one number from stack and displays it on
	 * calculator's display. If stack is empty, this method will print error message
	 * to calculator's display.
	 */
	private void pop() {
		if (stack.isEmpty()) {
			model.printErrorMessage("Stack is empty");
			model.clearWithoutInforming();
			return;
		}
		model.setValue(stack.pop());
	}
}
