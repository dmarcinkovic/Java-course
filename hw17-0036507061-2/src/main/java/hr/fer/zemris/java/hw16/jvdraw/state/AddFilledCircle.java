package hr.fer.zemris.java.hw16.jvdraw.state;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.geometric.FilledCircle;

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
			canvas.getCurrentState().mouseReleased(e);
			return;
		}

		if (filledCircle.getCenter() == null) {
			filledCircle.setCenter(e.getX(), e.getY());
			canvas.repaint();
		} else {
			secondClick = true;
			filledCircle.setSecondPoint(e.getX(), e.getY());

			filledCircle.setFillColor(getCopy(fillColor.getCurrentColor()));
			filledCircle.setLineColor(getCopy(lineColor.getCurrentColor()));
			drawingModel.add(filledCircle);

			canvas.setCurrentState(new AddFilledCircle(canvas, lineColor, fillColor, drawingModel));
		}
	}

	/**
	 * Returns copy of original color.
	 * 
	 * @param c Original color.
	 * @return Copy of original color.
	 */
	private Color getCopy(Color c) {
		return new Color(c.getRed(), c.getGreen(), c.getBlue());
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
