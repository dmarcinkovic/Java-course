package hr.fer.zemris.java.hw16.jvdraw.state;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.geometric.Line;

/**
 * Class that implements Tool interface. This class is capable of creating lines
 * and drawing them to the screen.
 * 
 * @author David
 *
 */
public class AddLine implements Tool {

	/**
	 * Line that will be added to the canvas.
	 */
	private Line line;

	/**
	 * Boolean flag that keeps track when user clicks twice to the screen.
	 */
	private boolean secondClick = false;

	/**
	 * Reference to JDrawingCanvas. JDrawingCanvas is central component in which all
	 * GeometricalObjects are added.
	 */
	private JDrawingCanvas canvas;

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
	public AddLine(JDrawingCanvas canvas, IColorProvider lineColor, DrawingModel drawingModel) {
		line = new Line(lineColor);
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
			
			canvas.getCurrentState().mouseReleased(e);
			return;
		}

		if (line.getStartPoint() == null) {
			line.setStartPoint(e.getX(), e.getY());
			canvas.repaint();
		} else {
			secondClick = true;
			line.setEndPoint(e.getX(), e.getY());
			line.setLineColor(getCopy(lineColor.getCurrentColor()));
			
			drawingModel.add(line);
			canvas.setCurrentState(new AddLine(canvas, lineColor, drawingModel));
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

		if (line.getStartPoint() != null) {
			line.setEndPoint(e.getX(), e.getY());
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
		Point start = line.getStartPoint();
		Point end = line.getEndPoint();

		if (start == null || end == null) {
			return;
		}

		g2d.setStroke(new BasicStroke(1));
		
		Color c = lineColor.getCurrentColor();
		g2d.setColor(c);
		g2d.drawLine(start.x, start.y, end.x, end.y);
	}
}
