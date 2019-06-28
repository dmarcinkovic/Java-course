package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Point;

/**
 * Visitor used to convert DrawingModel to .jvd format. It returns converted
 * format in toString method.
 * 
 * @author David
 *
 */
public class SaveDrawingModel implements GeometricalObjectVisitor {

	/**
	 * StringBuilder used to store String representation of DrawingModel.
	 */
	private StringBuilder sb = new StringBuilder();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Line line) {
		sb.append("LINE ");

		Point start = line.getStartPoint();
		Point end = line.getEndPoint();

		sb.append(start.x).append(" ").append(start.y).append(" ");
		sb.append(end.x).append(" ").append(end.y).append(" ");

		Color c = line.getLineColor();

		sb.append(c.getRed()).append(" ").append(c.getGreen()).append(" ").append(c.getBlue()).append("\n");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Circle circle) {
		sb.append("CIRCLE ");

		Point center = circle.getCenter();

		sb.append(center.x).append(" ").append(center.y).append(" ");
		sb.append(circle.getRadius().intValue()).append(" ");

		Color c = circle.getLineColor();
		sb.append(c.getRed()).append(" ").append(c.getGreen()).append(" ").append(c.getBlue()).append("\n");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(FilledCircle filledCircle) {
		sb.append("FCIRCLE ");

		Point center = filledCircle.getCenter();

		sb.append(center.x).append(" ").append(center.y).append(" ");
		sb.append(filledCircle.getRadius().intValue()).append(" ");

		Color lineColor = filledCircle.getLineColor();
		sb.append(lineColor.getRed()).append(" ").append(lineColor.getGreen()).append(" ").append(lineColor.getBlue())
				.append(" ");

		Color fillColor = filledCircle.getFillColor();
		sb.append(fillColor.getRed()).append(" ").append(fillColor.getGreen()).append(" ").append(fillColor.getBlue())
				.append("\n");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return sb.toString();
	}

}
