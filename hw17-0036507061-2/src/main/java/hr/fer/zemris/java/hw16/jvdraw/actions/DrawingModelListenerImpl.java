package hr.fer.zemris.java.hw16.jvdraw.actions;

import javax.swing.Action;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.DrawingModelListener;

/**
 * Class used to implement all method from DrawingModelListener interface. This
 * action must be listener on DrawingModel because we want to enable this action
 * when more than zero GeometricalObjects are presented at canvas. Also, we want
 * to disable this action when there are no GeometricalObjects presented at
 * canvas.
 * 
 * @author David
 *
 */
public class DrawingModelListenerImpl implements DrawingModelListener {

	/**
	 * Action that will be enabled/disabled.
	 */
	private Action action;

	/**
	 * Constructor used to initialize action.
	 * 
	 * @param action Action that will be enabled/disabled.
	 */
	public DrawingModelListenerImpl(Action action) {
		this.action = action;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		action.setEnabled(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		if (source.getSize() == 0) {
			action.setEnabled(false);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
	}
}
