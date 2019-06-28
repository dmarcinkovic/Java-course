package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Graphics2D;

/**
 * Visitor used to draw all GeometricalObjects presented at canvas to image.
 * 
 * @author David
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/**
	 * Reference to graphics. It is used to draw GeometricalObjects to image.
	 */
	private Graphics2D graphics;

	/**
	 * X offset.
	 */
	private int xOffset;

	/**
	 * Y offset.
	 */
	private int yOffset;

	/**
	 * Constructor. It receives reference to graphics so that it can draw objects
	 * using that graphics. Also, it receives x and y offset so that we can place
	 * objects to image.
	 * 
	 * @param graphics Reference to graphics. It is used to draw GeometricalObjects
	 *                 to image.
	 * @param xOffset  X offset.
	 * @param yOffset  Y offset.
	 */
	public GeometricalObjectPainter(Graphics2D graphics, int xOffset, int yOffset) {
		this.graphics = graphics;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Line line) {
		line.moveByOffset(xOffset, yOffset);

		line.paint(graphics);

		line.moveByOffset(-xOffset, -yOffset);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Circle circle) {
		circle.moveByOffset(xOffset, yOffset);
		circle.paint(graphics);
		circle.moveByOffset(-xOffset, -yOffset);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(FilledCircle filledCircle) {
		filledCircle.moveByOffset(xOffset, yOffset);
		filledCircle.paint(graphics);
		filledCircle.moveByOffset(-xOffset, -yOffset);
	}
}
