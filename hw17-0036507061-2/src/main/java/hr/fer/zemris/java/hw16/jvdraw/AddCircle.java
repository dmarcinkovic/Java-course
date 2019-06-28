package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 * Class that implements Tool interface. This class is capable of creating
 * circles and drawing them to the screen
 * 
 * @author David
 *
 */
public class AddCircle implements Tool {

	/**
	 * Circle that will be added to canvas.
	 */
	private Circle circle;

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
	 * Constructor.
	 * 
	 * @param canvas       Reference to canvas.
	 * @param lineColor    Line color.
	 * @param drawingModel Reference to DrawingModel. This is object that keeps
	 *                     track of all GeometricalObjects presented at canvas.
	 */
	public AddCircle(JDrawingCanvas canvas, IColorProvider lineColor, DrawingModel drawingModel) {
		circle = new Circle(lineColor);
		this.lineColor = lineColor;
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
			addNewCircle(e);
			return;
		}

		if (circle.getCenter() == null) {
			circle.setCenter(e.getX(), e.getY());
			canvas.repaint();
		} else {
			secondClick = true;
			circle.setSecondPoint(e.getX(), e.getY());

			drawingModel.add(circle);
		}
	}

	/**
	 * Method that creates new Circle when user clicks the mouse.
	 * 
	 * @param e MouseEvent.
	 */
	private void addNewCircle(MouseEvent e) {
		Tool currentState = canvas.getCurrentState();

		currentState = new AddCircle(canvas, lineColor, drawingModel);
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

		if (circle.getCenter() != null) {
			circle.setSecondPoint(e.getX(), e.getY());
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
		Point center = circle.getCenter();
		Double radius = circle.getRadius();

		if (center == null || radius == null) {
			return;
		}

		int r = radius.intValue();

		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(lineColor.getCurrentColor());
		g2d.drawOval(center.x - r, center.y - r, 2 * r, 2 * r);
	}

}
