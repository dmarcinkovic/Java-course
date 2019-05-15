package hr.fer.zemris.java.gui.calc;

import java.awt.Container;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.MyButton;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class Operators implements ICalculator {
	private CalcModel model;

	private Container container;

	private boolean inverse;

	private JButton button;

	public Operators(CalcModel model, Container container) {
		this.model = model;
		this.container = container;
	}

	@Override
	public void addButtons() {
		addButton("=", 1, 6);
		addButton("/", 2, 6);
		addButton("*", 3, 6);
		addButton("-", 4, 6);
		addButton("+", 5, 6);
		addButton("x^n", 5, 1);
	}

	private void toggle() {
		if (inverse) {
			inverse = false;
		} else {
			inverse = true;
		}
	}

	public void swap() {
		toggle();

		if (inverse) {
			button.setText("x^(1/n)");
		} else {
			button.setText("x^n");
		}
	}

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
	
	private void doAction(String text) {
		
	}
}
