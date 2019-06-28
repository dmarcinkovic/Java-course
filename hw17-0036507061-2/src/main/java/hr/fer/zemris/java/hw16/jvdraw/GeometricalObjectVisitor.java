package hr.fer.zemris.java.hw16.jvdraw;

/**
 * 
 * @author David
 *
 */
public interface GeometricalObjectVisitor {
	void visit(Line line);

	void visit(Circle circle);

	void visit(FilledCircle filledCircle);
}
