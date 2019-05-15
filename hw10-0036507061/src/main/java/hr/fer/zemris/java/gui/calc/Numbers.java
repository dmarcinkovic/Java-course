package hr.fer.zemris.java.gui.calc;

import java.awt.Container;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.MyButton;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class Numbers implements ICalculator {
	private CalcModel model;
	
	private Container container; 

	public Numbers(CalcModel model, Container container) {
		this.model = model;
		this.container = container;
	}
	
	public void addNumbers() {
		addButton("0", 5, 3);
		addButton("1", 4, 3);
		addButton("2", 4, 4);
		addButton("3", 4, 5);
		addButton("4", 3, 3);
		addButton("5", 3, 4);
		addButton("6", 3, 5);
		addButton("7", 2, 3);
		addButton("8", 2, 4);
		addButton("9", 2, 5);
	}

	@Override
	public void addButton(String text, int row, int col) {
		RCPosition constraint = new RCPosition(row, col);

		JButton button = new MyButton(text);
		button.setFont(button.getFont().deriveFont(30f));
		
		container.add(button, constraint);
		
		button.addActionListener(e -> {
			JButton source = (JButton) e.getSource();

			Integer digit = Integer.parseInt(source.getText());

			model.insertDigit(digit);
		});
	}

}
