package hr.fer.zemris.java.gui.calc.model;

import java.awt.Color;

import javax.swing.JButton;

/**
 * Extension of JButton object in java. This Button is just like normal JButton
 * button but it has background equal to RGB(220, 220, 220) value.
 * 
 * @author david
 *
 */
public class MyButton extends JButton {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for Button without text.
	 */
	public MyButton() {
		this(null);
	}

	/**
	 * Constructor for Button with specified text.
	 * 
	 * @param text Specified text.
	 */
	public MyButton(String text) {
		super(text);

		setOpaque(true);
		setBackground(new Color(220, 220, 220));
	}

}
