package hr.fer.zemris.java.hw16.jvdraw;

/**
 * Geometrical Object visitor. Interface that is used in Visitor design pattern.
 * It can be used to visit lines, circles and filled circles.
 * 
 * @author David
 *
 */
public interface GeometricalObjectVisitor {

	/**
	 * Method used to visit lines.
	 * 
	 * @param line Line to be visited.
	 */
	void visit(Line line);

	/**
	 * Method used to visit circles.
	 * 
	 * @param circle Circle to be visited.
	 */
	void visit(Circle circle);

	/**
	 * Method used to visit filledCircles.
	 * 
	 * @param filledCircle FilledCircle to be visited.
	 */
	void visit(FilledCircle filledCircle);
}
