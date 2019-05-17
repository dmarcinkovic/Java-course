package hr.fer.zemris.java.gui.calc;

import java.awt.Container;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.calc.model.MyButton;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Represents all numbers presented in this calculator. This calculator have
 * digits from zero to nine.
 * 
 * @author david
 *
 */
public class Numbers implements ICalculator {
	/**
	 * Calculator model. Used to communicate with calculator display.
	 */
	private CalcModelImpl model;

	/**
	 * Container in which function buttons are added.
	 */
	private Container container;

	/**
	 * Constructor to initialize model and container in which buttons with numbers
	 * are added.
	 * 
	 * @param model     Calculator model. Used to communicate with calculator
	 *                  display.
	 * @param container Container in which function buttons are added.
	 */
	public Numbers(CalcModel model, Container container) {
		this.model = (CalcModelImpl) model;
		this.container = container;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addButtons() {
		addButton("0", 5, 3, true);
		addButton("1", 4, 3, true);
		addButton("2", 4, 4, true);
		addButton("3", 4, 5, true);
		addButton("4", 3, 3, true);
		addButton("5", 3, 4, true);
		addButton("6", 3, 5, true);
		addButton("7", 2, 3, true);
		addButton("8", 2, 4, true);
		addButton("9", 2, 5, true);
		addButton("+/-", 5, 4, false);
		addButton(".", 5, 5, false);
	}

	/**
	 * Add button with specified text to layout to specified row and column.
	 * 
	 * @param text          Text that will appear on button.
	 * @param row           Row in which button is placed within Container.
	 * @param col           Column in which button is placed within Container.
	 * @param increacedFont Flag that says if button should have increased font.
	 */
	private void addButton(String text, int row, int col, boolean increacedFont) {
		RCPosition constraint = new RCPosition(row, col);

		JButton button = new MyButton(text);

		if (increacedFont) {
			button.setFont(button.getFont().deriveFont(30f));
		}

		container.add(button, constraint);

		button.addActionListener(e -> {
			JButton source = (JButton) e.getSource();

			doAction(source.getText());
		});
	}

	/**
	 * Performs actions based on what button is pressed. If button +/- is pressed
	 * this method will call swapSign method from model. If button . is pressed
	 * insertDecimalPoint from model will be called.
	 * 
	 * @param text Number.
	 */
	private void doAction(String text) {
		if (text.equals(".")) {
			try {
				model.insertDecimalPoint();
			} catch (CalculatorInputException e) {
				model.printErrorMessage("Error");
				model.clearWithoutInforming();
			}

			return;
		} else if (text.equals("+/-")) {
			model.swapSign();
			return;
		}

		Integer digit = Integer.parseInt(text);

		if (model.isEditable()) {
			model.insertDigit(digit);
		}else {
			model.clearAll();
			model.insertDigit(digit);
		}
	}
}
