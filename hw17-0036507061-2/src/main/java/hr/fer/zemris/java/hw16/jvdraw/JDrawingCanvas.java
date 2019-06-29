package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

/**
 * Central component. On this component all graphical objects are added.
 * Graphical objects that can be added are: lines, circles and filled circles.
 * 
 * @author David
 *
 */
public class JDrawingCanvas extends JComponent {

	/**
	 * Reference to currentState.
	 */
	private Tool currentState;

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Reference to DrawingModel.
	 */
	private DrawingModel model;

	/**
	 * Constructor used to initialize DrawingModel.
	 * 
	 * @param model DrawingModel.
	 */
	public JDrawingCanvas(DrawingModel model) {
		this.model = model;

		model.addDrawingModelListener(new DrawingModelListenerImpl());

		addMouseListener(new MyMouseListener());
		addMouseMotionListener(new MyMouseMotionListener());
	}

	/**
	 * Setter for currentState.
	 * 
	 * @param currentState CurrentState.
	 */
	public void setCurrentState(Tool currentState) {
		this.currentState = currentState;
	}

	/**
	 * Returns currentState.
	 * 
	 * @return CurrentState.
	 */
	public Tool getCurrentState() {
		return currentState;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g) {
		for (int i = 0; i < model.getSize(); i++) {
			model.getObject(i).paint((Graphics2D) g);
		}

		if (currentState != null) {
			currentState.paint((Graphics2D) g);
		}
	}

	/**
	 * Implementation of DrawingModelListener. When new GeometricalObject is added
	 * to DrawingModel, or existing GeometricalObject is removed or modified then
	 * repaint method is called.
	 * 
	 * @author David
	 *
	 */
	private class DrawingModelListenerImpl implements DrawingModelListener {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void objectsAdded(DrawingModel source, int index0, int index1) {
			repaint();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void objectsRemoved(DrawingModel source, int index0, int index1) {
			currentState = null;
			repaint();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void objectsChanged(DrawingModel source, int index0, int index1) {
			repaint();
		}

	}

	/**
	 * Implementation of MouseMotionListener. This class in every method just
	 * delegates mouse events to current state and then drawing of
	 * GeometricalObjects is done by Tool objects.
	 * 
	 * @author David
	 *
	 */
	private class MyMouseMotionListener implements MouseMotionListener {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void mouseDragged(MouseEvent e) {
			if (currentState != null) {
				currentState.mouseDragged(e);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void mouseMoved(MouseEvent e) {
			if (currentState != null) {
				currentState.mouseMoved(e);
			}
		}

	}

	/**
	 * Implementation of MouseListener. This class in every method just delegates
	 * mouse events to current state and then drawing of GeometricalObjects is done
	 * by Tool objects.
	 * 
	 * @author David
	 *
	 */
	private class MyMouseListener implements MouseListener {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if (currentState != null) {
				currentState.mouseClicked(e);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			if (currentState != null) {
				currentState.mousePressed(e);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
			if (currentState != null) {
				currentState.mouseReleased(e);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void mouseEntered(MouseEvent e) {
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void mouseExited(MouseEvent e) {
		}

	}
}
