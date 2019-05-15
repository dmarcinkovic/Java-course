package hr.fer.zemris.java.gui.calc.model;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * Extension of JLabel object in java. This Label is just like normal label, but
 * there are some differences. This label has default background color equal to
 * yellow and font size equal to 30.
 * 
 * @author david
 *
 */
public class MyLabel extends JLabel implements CalcValueListener {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for label with horizontal alignment equal to SwingConstants.LEFT.
	 * 
	 * @param model Calculator model.
	 */
	public MyLabel(CalcModel model) {
		this(model, SwingConstants.LEFT);
	}

	/**
	 * Constructor for label with specified alignment.
	 * 
	 * @param model              Calculator model.
	 * @param horizontalAligment Horizontal alignment.
	 */
	public MyLabel(CalcModel model, int horizontalAlignment) {
		super("0", horizontalAlignment);
		model.addCalcValueListener(this);

		initLabel();
	}

	/**
	 * Method to initialize this label with background color equal to yellow and
	 * font with size 30. Also, this method sets black border around the label.
	 */
	private void initLabel() {
		setBackground(Color.YELLOW);
		setOpaque(true);
		setFont(this.getFont().deriveFont(30f));

		Border border = BorderFactory.createLineBorder(Color.BLACK);
		setBorder(border);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void valueChanged(CalcModel model) {
		String value = model.toString();
		this.setText(value);
	}

}
