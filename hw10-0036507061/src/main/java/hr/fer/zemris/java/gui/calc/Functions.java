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

public class Functions implements ICalculator {
	private CalcModelImpl model;

	private Container container;
	private List<JButton> buttons;
	private List<String> text;
	private List<String> inverseText;
	
	private boolean inverse;

	public Functions(CalcModel model, Container container) {
		this.model = (CalcModelImpl)model;
		this.container = container;

		buttons = new ArrayList<>();

		text = new ArrayList<>(Arrays.asList("1/x", "sin", "log", "cos", "ln", "tan", "ctg"));

		inverseText = new ArrayList<>(Arrays.asList("1/x", "arcsin", "10^x", "arccos", "e^x", "arctan", "arcctg"));
	}

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

	public void swap() {
		toggle();
		
		if (inverse) {
			setText(inverseText);
		}else {
			setText(text);
		}
	}
	
	private void setText(List<String> text) {
		for (int i = 0; i<buttons.size(); i++) {
			buttons.get(i).setText(text.get(i));
		}
	}
	
	private void toggle() {
		if (inverse) {
			inverse = false;
		}else {
			inverse = true;
		}
	}
	
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

	private void doOperation(String name) {
		double number = model.getValue();

		switch (name) {
		case "1/x":
			model.setValue(1. / number);
			break;
		case "sin":
			model.setValue(Math.sin(number));
			break;
		case "log":
			model.setValue(Math.log10(number));
			break;
		case "cos":
			model.setValue(Math.cos(number));
			break;
		case "ln":
			model.setValue(Math.log(number));
			break;
		case "tan":
			model.setValue(Math.tan(number));
			break;
		case "ctg":
			model.setValue(1. / Math.tan(number));
			break;
		case "arcsin":
			model.setValue(Math.asin(number));
			break;
		case "10^x":
			model.setValue(Math.pow(10, number));
			break;
		case "arccos":
			model.setValue(Math.acos(number));
			break;
		case "e^x":
			model.setValue(Math.pow(Math.E, number));
			break;
		case "arctan":
			model.setValue(Math.atan(number));
			break;
		case "arcctg":
			model.setValue(Math.atan(1. / number));
			break;
		}
		model.clearWithoutInforming();
	}
}
