package hr.fer.zemris.java.hw16.jvdraw.visitor;

import java.awt.Point;
import java.awt.Rectangle;

import hr.fer.zemris.java.hw16.jvdraw.geometric.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometric.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometric.Line;

/**
 * Visitor used to determine bounding box of canvas. Bounding box is the
 * smallest rectangle in which all GeometricalObjects can be placed.
 * 
 * @author David
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	/**
	 * Point with the greatest y coordinate.
	 */
	private Point bottom;

	/**
	 * Point with the smallest y coordinate.
	 */
	private Point top;

	/**
	 * Point with the smallest x coordinate.
	 */
	private Point left;

	/**
	 * Point with the greatest x coordinate.
	 */
	private Point right;

	/**
	 * Width of the canvas.
	 */
	private int width;

	/**
	 * Height of the canvas.
	 */
	private int height;

	/**
	 * Constructor that receives information about the canvas width and height.
	 * 
	 * @param width  Width of canvas.
	 * @param height Height of the canvas.
	 */
	public GeometricalObjectBBCalculator(int width, int height) {
		this.width = width;
		this.height = height;

		bottom = new Point(0, -1);
		top = new Point(0, height + 1);
		left = new Point(width + 1, 0);
		right = new Point(-1, 0);
	}

	/**
	 * Returns bounding box.
	 * 
	 * @return Bounding box.
	 */
	public Rectangle getBoundingBox() {
		checkBoundings();
		int x = left.x - 2;
		int y = top.y - 2;
		int w = right.x - left.x + 4;
		int h = bottom.y - top.y + 4;
	
		return new Rectangle(x, y, w, h);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Line line) {
		Point start = line.getStartPoint();
		Point end = line.getEndPoint();
		
		checkPoint(start);
		checkPoint(end);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Circle circle) {
		int radius = circle.getRadius().intValue();
		Point center = circle.getCenter();

		checkPoint(center.x - radius, center.y - radius);
		checkPoint(center.x + radius, center.y - radius);
		checkPoint(center.x - radius, center.y + radius);
		checkPoint(center.x + radius, center.y + radius);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(FilledCircle filledCircle) {
		int radius = filledCircle.getRadius().intValue();
		Point center = filledCircle.getCenter();

		checkPoint(center.x - radius, center.y - radius);
		checkPoint(center.x + radius, center.y - radius);
		checkPoint(center.x - radius, center.y + radius);
		checkPoint(center.x + radius, center.y + radius);
	}

	/**
	 * Method that checks if point is bottom, top, left or right.
	 * 
	 * @param p Point to check for.
	 */
	private void checkPoint(Point p) {
		checkPoint(p.x, p.y);
	}

	/**
	 * Method that checks if point is bottom, top, left, right.
	 * 
	 * @param x X coordinate of point.
	 * @param y Y coordinate of point.
	 */
	private void checkPoint(int x, int y) {
		if (y < top.y) {
			top.x = x;
			top.y = y;
		}

		if (y > bottom.y) {
			bottom.x = x;
			bottom.y = y;
		}

		if (x < left.x) {
			left.x = x;
			left.y = y;
		}

		if (x > right.x) {
			right.x = x;
			right.y = y;
		}
	}

	/**
	 * Method that check boundings. It checks if left point has x coordinate greater
	 * that zero and right has x coordinate less that width of the canvas. Also it
	 * checks if bottom has y coordinate less that height of the canvas and checks
	 * if top has y greater than zero.
	 */
	private void checkBoundings() {
		if (left.x < 0) {
			left.x = 0;
		}

		if (right.x > width) {
			right.x = width;
		}

		if (bottom.y > height) {
			bottom.y = height;
		}

		if (top.y < 0) {
			top.y = 0;
		}
	}

}
