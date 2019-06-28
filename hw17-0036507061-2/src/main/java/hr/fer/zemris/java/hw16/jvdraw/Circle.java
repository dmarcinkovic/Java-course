package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Class that represents Circle.
 * 
 * @author David
 *
 */
public class Circle extends GeometricalObject {

	/**
	 * Center of Circle.
	 */
	private Point center;

	/**
	 * Point on the circle. It is recorded when user clicks the mouse for the second
	 * time.
	 */
	private Point secondPoint;

	/**
	 * Radius of this circle.
	 */
	private Double radius;

	/**
	 * Line color. I.e. stroke color.
	 */
	private Color lineColor;

	/**
	 * Stroke line with stroke weight equal to one.
	 */
	private static final BasicStroke ONE = new BasicStroke(1);

	/**
	 * Constructor used to initialize line color.
	 * 
	 * @param lineColor Line color.
	 */
	public Circle(IColorProvider lineColor) {
		this.lineColor = getCopy(lineColor.getCurrentColor());
	}

	/**
	 * Default constructor.
	 */
	public Circle() {
	}

	/**
	 * Sets center point.
	 * 
	 * @param x New x coordinate of the center.
	 * @param y New y coordinate of the center.
	 */
	public void setCenter(int x, int y) {
		if (center == null) {
			center = new Point();
		}

		center.x = x;
		center.y = y;
	}

	/**
	 * Setter for radius.
	 * 
	 * @param radius Radius of circle.
	 */
	public void setRadius(Double radius) {
		this.radius = radius;
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
	 * Getter for line color.
	 * 
	 * @return Line color.
	 */
	public Color getLineColor() {
		return lineColor;
	}

	/**
	 * Getter for Center point.
	 * 
	 * @return Center point.
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Sets second point.
	 * 
	 * @param x New x coordinate of the second point.
	 * @param y New y coordinate of the second point.
	 */
	public void setSecondPoint(int x, int y) {
		if (secondPoint == null) {
			secondPoint = new Point();
		}

		secondPoint.x = x;
		secondPoint.y = y;
		calculateRadius();
	}

	/**
	 * Method used to calculate radius of circle.
	 */
	private void calculateRadius() {
		radius = center.distance(secondPoint);
	}

	/**
	 * Getter for radius.
	 * 
	 * @return Radius.
	 */
	public Double getRadius() {
		return radius;
	}

	/**
	 * Method that moves this circle by xOffset in x coordinate and by yOffset in y
	 * coordinate.
	 * 
	 * @param xOffset X offset.
	 * @param yOffset Y offset.
	 */
	public void moveByOffset(int xOffset, int yOffset) {
		center.x -= xOffset;
		center.y -= yOffset;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		// TODO Auto-generated method stub
		return null;
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
		int r = radius.intValue();

		g2d.setStroke(ONE);
		g2d.setColor(lineColor);
		g2d.drawOval(center.x - r, center.y - r, 2 * r, 2 * r);
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
		int r = radius.intValue();

		return "Circle(" + center.x + "," + center.y + ")," + r;
	}
}
