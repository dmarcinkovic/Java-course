package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * Class that is used for editing lines presented at canvas.
 * 
 * @author David
 *
 */
public class LineEditor extends GeometricalObjectEditor {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Reference to line that will be edited.
	 */
	private Line line;

	/**
	 * Text area in which user can type new x coordinate of start point of line.
	 */
	private JTextArea startX;

	/**
	 * Text area in which user can type new y coordinate of start point of line.
	 */
	private JTextArea startY;

	/**
	 * Text area in which user can type new x coordinate of end point of line.
	 */
	private JTextArea endX;

	/**
	 * Text area in which user can type new y coordinate of end point of line.
	 */
	private JTextArea endY;

	/**
	 * JColorArea. Used to allow user to change color of line.
	 */
	private JColorArea fgColorArea;

	/**
	 * New startX
	 */
	private int sx;

	/**
	 * New startY.
	 */
	private int sy;

	/**
	 * New endX.
	 */
	private int ex;

	/**
	 * New endY.
	 */
	private int ey;

	/**
	 * Constructor used to initialize line that will be edited.
	 * 
	 * @param line Line.
	 */
	public LineEditor(Line line) {
		this.line = line;

		startX = new JTextArea(String.valueOf(line.getStartPoint().x));
		startY = new JTextArea(String.valueOf(line.getStartPoint().y));

		endX = new JTextArea(String.valueOf(line.getEndPoint().x));
		endY = new JTextArea(String.valueOf(line.getEndPoint().y));

		setLayout(new GridLayout(0, 2));

		fgColorArea = new JColorArea(line.getLineColor());

		add(new JLabel("Choose line color"));
		add(fgColorArea);
		fgColorArea.addMouseListener(new MouseListenerImpl(this));

		add(new JLabel("Start x"));
		add(startX);

		add(new JLabel("Start y"));
		add(startY);

		add(new JLabel("End x"));
		add(endX);

		add(new JLabel("End y"));
		add(endY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkEditing() {
		String lineStartX = startX.getText();
		String lineStartY = startY.getText();
		String lineEndX = endX.getText();
		String lineEndY = endY.getText();

		try {
			sx = Integer.parseInt(lineStartX);
			sy = Integer.parseInt(lineStartY);

			ex = Integer.parseInt(lineEndX);
			ey = Integer.parseInt(lineEndY);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void acceptEditing() {
		line.setStartPoint(sx, sy);
		line.setEndPoint(ex, ey);
		Color c = fgColorArea.getCurrentColor();
		line.setLineColor(new Color(c.getRed(), c.getGreen(), c.getBlue()));
	}

}
