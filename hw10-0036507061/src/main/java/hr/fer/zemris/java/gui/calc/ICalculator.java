package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

public interface ICalculator {
	void setColor(Color color);
	void setIncreasedFont(boolean increasedFont);
	void addComponent(Component component, String text);
	List<Component> getComponents();
	Component pressed();
	void listen();
}
