package hr.fer.zemris.java.gui.calc.model;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class MyLabel extends JLabel implements CalcValueListener {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	public MyLabel(CalcModel model) {
		this(model, SwingConstants.LEFT);
	}

	public MyLabel(CalcModel model, int horizontalAligment) {
		super("0", horizontalAligment);
		model.addCalcValueListener(this);

		initLabel();
	}

	private void initLabel() {
		setBackground(Color.YELLOW);
		setOpaque(true);
		setFont(this.getFont().deriveFont(30f));
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		setBorder(border);
	}

	@Override
	public void valueChanged(CalcModel model) {
		String value = model.toString();
		this.setText(value);
	}

}
