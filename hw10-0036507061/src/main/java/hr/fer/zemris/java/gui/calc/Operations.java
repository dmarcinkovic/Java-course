package hr.fer.zemris.java.gui.calc;

import java.awt.Container;
import java.util.Stack;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.MyButton;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class Operations implements ICalculator {
	private CalcModelImpl model;
	private Container container;
	private Stack<Double> stack;

	public Operations(CalcModel model, Container container) {
		this.model = (CalcModelImpl) model;
		this.container = container;
		stack = new Stack<>();
	}

	@Override
	public void addButtons() {
		addButton("clr", 1, 7);
		addButton("reset", 2, 7);
		addButton("push", 3, 7);
		addButton("pop", 4, 7);
	}

	private void addButton(String text, int row, int col) {
		RCPosition constraint = new RCPosition(row, col);

		JButton button = new MyButton(text);

		container.add(button, constraint);

		button.addActionListener(e -> {
			JButton source = (JButton) e.getSource();

			doAction(source.getText());
		});
	}

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

	private void pop() {
		if (stack.isEmpty()) {
			model.printErrorMessage("Stack is empty");
			model.clearWithoutInforming();
			return;
		}
		model.setValue(stack.pop());
	}
}
