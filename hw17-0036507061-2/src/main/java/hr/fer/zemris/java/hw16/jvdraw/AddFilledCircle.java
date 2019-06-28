package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 * Class that implements Tool interface. This class is capable of creating
 * filled circles and drawing them to the screen
 * 
 * @author David
 *
 */
public class AddFilledCircle implements Tool {

	/**
	 * Filled Circle that will be added to canvas.
	 */
	private FilledCircle filledCircle;

	/**
	 * Reference to JDrawingCanvas. JDrawingCanvas is central component in which all
	 * GeometricalObjects are added.
	 */
	private JDrawingCanvas canvas;

	/**
	 * Boolean flag that keeps track when user clicks twice to the screen.
	 */
	private boolean secondClick = false;

	/**
	 * Object that keeps track of all GeometricalObjects presented at canvas.
	 */
	private DrawingModel drawingModel;

	/**
	 * Line color.
	 */
	private IColorProvider lineColor;

	/**
	 * Fill color.
	 */
	private IColorProvider fillColor;

	/**
	 * Constructor.
	 * 
	 * @param canvas       Reference to canvas.
	 * @param lineColor    Line color.
	 * @param fillColor    Fill color.
	 * @param drawingModel Reference to DrawingModel. This is object that keeps
	 *                     track of all GeometricalObjects presented at canvas.
	 */
	public AddFilledCircle(JDrawingCanvas canvas, IColorProvider lineColor, IColorProvider fillColor,
			DrawingModel drawingModel) {
		filledCircle = new FilledCircle(lineColor, fillColor);
		this.lineColor = lineColor;
		this.fillColor = fillColor;
		this.canvas = canvas;
		this.drawingModel = drawingModel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if (secondClick) {
			addNewFilledCircle(e);
			return;
		}

		if (filledCircle.getCenter() == null) {
			filledCircle.setCenter(e.getX(), e.getY());
			canvas.repaint();
		} else {
			secondClick = true;
			filledCircle.setSecondPoint(e.getX(), e.getY());

			drawingModel.add(filledCircle);
		}
	}

	/**
	 * Method that creates new Filled Circle when user clicks the mouse.
	 * 
	 * @param e MouseEvent.
	 */
	private void addNewFilledCircle(MouseEvent e) {
		Tool currentState = canvas.getCurrentState();
		currentState = new AddFilledCircle(canvas, lineColor, fillColor, drawingModel);
		canvas.setCurrentState(currentState);
		currentState.mouseReleased(e);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		if (secondClick) {
			return;
		}

		if (filledCircle.getCenter() != null) {
			filledCircle.setSecondPoint(e.getX(), e.getY());
			canvas.repaint();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics2D g2d) {
		Point center = filledCircle.getCenter();
		Double radius = filledCircle.getRadius();

		if (center == null || radius == null) {
			return;
		}

		int r = radius.intValue();

		g2d.setColor(lineColor.getCurrentColor());
		g2d.setStroke(new BasicStroke(4));
		g2d.drawOval(center.x - r, center.y - r, 2 * r, 2 * r);
		g2d.setColor(fillColor.getCurrentColor());
		g2d.fillOval(center.x - r, center.y - r, 2 * r, 2 * r);
	}

}
