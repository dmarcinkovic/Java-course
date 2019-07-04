package hr.fer.zemris.java.hw16.jvdraw.drawing;

import hr.fer.zemris.java.hw16.jvdraw.geometric.GeometricalObject;

/**
 * This interface represents class that keeps track of all objects presented to
 * the user and is responsible for managing those geometrical objects.
 * 
 * @author David
 *
 */
public interface DrawingModel {

	/**
	 * Returns the number of GeometricalObjects presented at canvas.
	 * 
	 * @return Number of GeometricalObjects presented at canvas.
	 */
	public int getSize();

	/**
	 * Returns the object from specified index.
	 * 
	 * @param index Index of returned GeometricalObject.
	 * @return GeometricalObject from specified index.
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Method that adds new GeometricalObject to the canvas.
	 * 
	 * @param object GeometricalObject that will be added to the canvas.
	 */
	public void add(GeometricalObject object);

	/**
	 * Method that removes GeometricalObject from the canvas.
	 * 
	 * @param object GeometricalObject that will be removed.
	 */
	public void remove(GeometricalObject object);

	/**
	 * Method that is used to change order of GeometricalObjects. It moves given
	 * GeometricalObject to the front by given offset. If current object position +
	 * offset is greater than number of objects presented at the canvas this method
	 * will do nothing.
	 * 
	 * @param object Object which has to change its order.
	 * @param offset Offset.
	 */
	public void changeOrder(GeometricalObject object, int offset);

	/**
	 * Returns index of given GeometricalObject.
	 * 
	 * @param object Given GeometricalObject.
	 * @return Index of given GeometricalObject.
	 */
	public int indexOf(GeometricalObject object);

	/**
	 * Clears the canvas. It removes all objects from the canvas.
	 */
	public void clear();

	/**
	 * Clear modified flag.
	 */
	public void clearModifiedFlag();

	/**
	 * Returns true if canvas has changed any of object.
	 * 
	 * @return True if canvas has modified, otherwise returns false.
	 */
	public boolean isModified();

	/**
	 * Method that is used to register interested listeners.
	 * 
	 * @param l Interested listener.
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Method that is used to deregister interested listeners.
	 * 
	 * @param l Listener that will no more receive notifications.
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}
