package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that is subject in Observer design pattern. This class can notify
 * interested Observers that its state has been changed.
 * 
 * @author David
 *
 */
public abstract class GeometricalObject {

	/**
	 * List of all interested listeners.
	 */
	private List<GeometricalObjectListener> listeners;

	/**
	 * Constructor.
	 */
	public GeometricalObject() {
		listeners = new ArrayList<>();
	}

	/**
	 * Method that creates GeometricalObjectEditor.
	 * 
	 * @return created GeometricalObjectEditor.
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Method used in implementation of Visitor design pattern.
	 * 
	 * @param v Reference to GeometricalObjectVisitor.
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * Method used to enable GeometricalObject to be able to draw itself.
	 * 
	 * @param g2d Graphics object.
	 */
	public abstract void paint(Graphics2D g2d);

	/**
	 * Method used to register interested GeometricalObjectListener. This object
	 * will be informed whenever this object changes its state.
	 * 
	 * @param l GeometricalObjectListener that will be registered.
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}

	/**
	 * Method used to remove GeometricalObjectListener from listeners list. After
	 * this method is called, this object will be no more informed when this object
	 * changes it state.
	 * 
	 * @param l GeometricalObjectListener that will be removed from listeners list.
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}

}
