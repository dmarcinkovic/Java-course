package hr.fer.zemris.java.hw16.jvdraw.geometric;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.editor.LineEditor;
import hr.fer.zemris.java.hw16.jvdraw.visitor.GeometricalObjectVisitor;

/**
 * Class that represents Line. When user clicks to the screen for the first line
 * then start point is added. When users clicks for the second time then end
 * point is determined. Line is drawn between those two points.
 * 
 * @author David
 *
 */
public class Line extends GeometricalObject {

	/**
	 * Starting point of the line.
	 */
	private Point start;

	/**
	 * End point of the line.
	 */
	private Point end;

	/**
	 * Line color.
	 */
	private Color lineColor;

	/**
	 * Stroke line with stroke weight equal to one.
	 */
	private static final BasicStroke ONE = new BasicStroke(1);

	/**
	 * Constructor used to initialize Color.
	 * 
	 * @param lineColor Line color.
	 */
	public Line(IColorProvider lineColor) {
		this.lineColor = getCopy(lineColor.getCurrentColor());
	}

	/**
	 * Default constructor.
	 */
	public Line() {
	}

	/**
	 * Setter for start point.
	 * 
	 * @param x New x coordinate for start point.
	 * @param y New y coordinate for start point.
	 */
	public void setStartPoint(int x, int y) {
		if (start == null) {
			start = new Point();
		}
		start.x = x;
		start.y = y;
	}

	/**
	 * Setter for end point.
	 * 
	 * @param x New x coordinate for end point.
	 * @param y New y coordinate for end point.
	 */
	public void setEndPoint(int x, int y) {
		if (end == null) {
			end = new Point();
		}
		end.x = x;
		end.y = y;
	}

	/**
	 * Getter for starting point.
	 * 
	 * @return Start point.
	 */
	public Point getStartPoint() {
		return start;
	}

	/**
	 * Getter for end point.
	 * 
	 * @return End point.
	 */
	public Point getEndPoint() {
		return end;
	}

	/**
	 * Setter for line color.
	 * 
	 * @param lineColor Line color.
	 */
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	/**
	 * Getter for lineColor.
	 * 
	 * @return Line color.
	 */
	public Color getLineColor() {
		return lineColor;
	}

	/**
	 * Move line by offset.
	 * 
	 * @param xOffset X offset.
	 * @param yOffset Y offset.
	 */
	public void moveByOffset(int xOffset, int yOffset) {
		start.x -= xOffset;
		start.y -= yOffset;

		end.x -= xOffset;
		end.y -= yOffset;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics2D g2d) {
		g2d.setStroke(ONE);
		g2d.setColor(lineColor);
		g2d.drawLine(start.x, start.y, end.x, end.y);
	}

	/**
	 * Return the copy of the color.
	 * 
	 * @param c Original color.
	 * @return Copied color.
	 */
	private Color getCopy(Color c) {
		Color copy = new Color(c.getRed(), c.getGreen(), c.getBlue());
		return copy;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Line(" + start.x + "," + start.y + ")-(" + end.x + "," + end.y + ")";
	}
}
