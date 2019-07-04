package hr.fer.zemris.java.hw16.jvdraw.editor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.geometric.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.listeners.MouseListenerImpl;

/**
 * Class that is used for editing filled circles presented at canvas.
 * 
 * @author David
 *
 */
public class FilledCircleEditor extends GeometricalObjectEditor {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * JColorArea. It is used to get circle outline color.
	 */
	private JColorArea fgColorArea;
	
	/**
	 * JColorArea. It is used to get circle fill color.
	 */
	private JColorArea bgColorArea;
	
	/**
	 * Text area used to allow user to type new x coordinate of center of circle.
	 */
	private JTextArea x;
	
	/**
	 * Text area used to allow user to type new y coordinate of center of circle.
	 */
	private JTextArea y;
	
	/**
	 * Text area used to allow user to type new radius of circle.
	 */
	private JTextArea radius;
	
	 /**
	  * New centerX.
	  */
	private int cx;
	
	 /**
	  * New centerY.
	  */
	private int cy;
	
	/**
	 *  Circle to be edited.
	 */
	private FilledCircle circle;
	
	/**
	 * New radius.
	 */
	private double r;

	/**
	 * Constructor.
	 * @param circle Filled circle.
	 */
	public FilledCircleEditor(FilledCircle circle) {
		this.circle = circle;

		bgColorArea = new JColorArea(circle.getFillColor());
		fgColorArea = new JColorArea(circle.getLineColor());

		x = new JTextArea(String.valueOf(circle.getCenter().x));
		y = new JTextArea(String.valueOf(circle.getCenter().y));
		radius = new JTextArea(String.valueOf(circle.getRadius()));

		setLayout(new GridLayout(0, 2));

		add(new JLabel("Choose circle area color"));
		add(bgColorArea);
		bgColorArea.addMouseListener(new MouseListenerImpl(this));

		add(new JLabel("Choose circle outline color"));
		add(fgColorArea);
		fgColorArea.addMouseListener(new MouseListenerImpl(this));

		add(new JLabel("Center x"));
		add(x);

		add(new JLabel("Center y"));
		add(y);

		add(new JLabel("Radius"));
		add(radius);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkEditing() {
		String centerX = x.getText();
		String centerY = y.getText();

		try {
			cx = Integer.parseInt(centerX);
			cy = Integer.parseInt(centerY);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}

		String s = radius.getText();

		try {
			r = Double.parseDouble(s);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void acceptEditing() {
		circle.setCenter(cx, cy);
		circle.setRadius(r);
	
		Color c1 = bgColorArea.getCurrentColor();
		circle.setFillColor(new Color(c1.getRed(), c1.getGreen(), c1.getBlue()));
		
		Color c2 = fgColorArea.getCurrentColor();
		circle.setLineColor(new Color(c2.getRed(), c2.getGreen(), c2.getBlue()));
	}

}
