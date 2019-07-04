package hr.fer.zemris.java.hw16.jvdraw.drawing;

/**
 * Listeners that is used to inform registered listeners when any
 * GeometricalObject from DrawingModel changes its state. I.e. any object has
 * been added, removed or changed.
 * 
 * @author David
 *
 */
public interface DrawingModelListener {

	/**
	 * Informs registered listeners that new GeometricalObject has been added to
	 * DrawingModel.
	 * 
	 * @param source Source DrawingModel.
	 * @param index0 Index of which GeometricalObject has been added.
	 * @param index1 Index of which GeometricalObject has been added.
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Informs registered listeners that GeometricalObject has been removed.
	 * 
	 * @param source Source DrawingModel.
	 * @param index0 Index of GeometricalObject which has been removed.
	 * @param index1 Index of GeometricalObject which has been removed.
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Informs registered listeners that GeometricalObject has been modified.
	 * 
	 * @param source Source DrawingModel.
	 * @param index0 Index of GeometricalObject which has been changed.
	 * @param index1 Index of GeometricalObject which has been changed.
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
