package hr.fer.zemris.java.hw16.jvdraw.listeners;

import hr.fer.zemris.java.hw16.jvdraw.geometric.GeometricalObject;

/**
 * Interface that represents the classes which are interested in receiving
 * notifications about the GeomtricalObject changes.
 * 
 * @author David
 *
 */
public interface GeometricalObjectListener {

	/**
	 * Method called when GeometricalObject change its state. I.e. new
	 * GeometricalObject is created or color of existing GeometricalObjects is
	 * updated.
	 * 
	 * @param o GeometricalObject.
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}
