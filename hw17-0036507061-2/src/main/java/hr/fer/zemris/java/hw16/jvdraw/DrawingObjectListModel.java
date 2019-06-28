package hr.fer.zemris.java.hw16.jvdraw;

import javax.swing.AbstractListModel;

/**
 * Class that is implementation of ListModel. It is used to show to user the
 * list of all objects presented at the canvas.
 * 
 * @author David
 *
 */
public class DrawingObjectListModel extends AbstractListModel<String> implements DrawingModelListener {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Reference to drawing model.
	 */
	private DrawingModel drawingModel;

	/**
	 * Constructor. It is used to get reference to drawing model.
	 * 
	 * @param drawingModel Reference to drawing model.
	 */
	public DrawingObjectListModel(DrawingModel drawingModel) {
		this.drawingModel = drawingModel;
		drawingModel.addDrawingModelListener(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSize() {
		return drawingModel.getSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getElementAt(int index) {
		GeometricalObject object = drawingModel.getObject(index);

		return object.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(source, index0, index1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(source, index0, index1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
	}

}
