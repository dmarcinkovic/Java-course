package hr.fer.zemris.java.hw16.jvdraw;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that is implementation of DrawingModel interface. This class keeps
 * track of all objects presented to the user and is responsible for managing
 * those geometrical objects.
 * 
 * @author David
 *
 */
public class DrawingModelImpl implements DrawingModel {

	/**
	 * List of all geometrical objects presented on the screen.
	 */
	private List<GeometricalObject> geometricalObjects;

	/**
	 * Boolean flag that keeps track of modification status. When any object is
	 * added to the canvas it changes to true.
	 */
	private boolean modificationFlag = false;

	/**
	 * List of all registered listeners.
	 */
	private List<DrawingModelListener> listeners;

	/**
	 * Constructor.
	 */
	public DrawingModelImpl() {
		geometricalObjects = new ArrayList<>();
		listeners = new ArrayList<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSize() {
		return geometricalObjects.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GeometricalObject getObject(int index) {
		return geometricalObjects.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(GeometricalObject object) {
		geometricalObjects.add(object);

		object.addGeometricalObjectListener((o) -> {
			for (DrawingModelListener l : listeners) {
				int index = indexOf(o);
				l.objectsChanged(this, index, index);
			}
		});

		modificationFlag = true;

		int index = geometricalObjects.size() - 1;

		for (DrawingModelListener l : listeners) {
			l.objectsAdded(this, index, index);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(GeometricalObject object) {
		int index = geometricalObjects.indexOf(object);

		geometricalObjects.remove(object);
		modificationFlag = true;

		for (DrawingModelListener l : listeners) {
			l.objectsRemoved(this, index, index);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int currentPosition = geometricalObjects.indexOf(object);
		int newPosition = currentPosition + offset;

		if (newPosition >= getSize() || newPosition < 0) {
			return;
		}
		
		GeometricalObject temp = geometricalObjects.get(currentPosition);
		geometricalObjects.set(currentPosition, geometricalObjects.get(newPosition));
		geometricalObjects.set(newPosition, temp);

		modificationFlag = true;

		for (DrawingModelListener l : listeners) {
			l.objectsChanged(this, currentPosition, newPosition);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int indexOf(GeometricalObject object) {
		return geometricalObjects.indexOf(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		if (geometricalObjects.isEmpty()) {
			return;
		}

		modificationFlag = true;

		int n = geometricalObjects.size();

		geometricalObjects.clear();
		for (DrawingModelListener l : listeners) {
			l.objectsRemoved(this, 0, n - 1);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearModifiedFlag() {
		modificationFlag = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isModified() {
		return modificationFlag;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

}
