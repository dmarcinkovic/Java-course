package hr.fer.zemris.java.gui.calc.model;

import java.awt.Color;

import javax.swing.JButton;

public class MyButton extends JButton{

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;
	
	public MyButton() {
		this(null);
	}
	
	public MyButton(String text) {
		super(text);
		
		setOpaque(true);
		setBackground(new Color(220, 220, 220));
	}
	
}
