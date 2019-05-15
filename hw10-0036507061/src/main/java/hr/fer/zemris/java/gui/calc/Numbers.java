package hr.fer.zemris.java.gui.calc;

import java.awt.Container;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.calc.model.MyButton;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class Numbers implements ICalculator {
	private CalcModelImpl model;

	private Container container;

	public Numbers(CalcModel model, Container container) {
		this.model = (CalcModelImpl)model;
		this.container = container;
	}

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

		model.insertDigit(digit);
	}
}
