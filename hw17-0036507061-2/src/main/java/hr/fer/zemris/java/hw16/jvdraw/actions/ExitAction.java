package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.MyWindowListener;

/**
 * Implementation of Exit action. This action checks if canvas has been modifies
 * since last saving, if so, it asks user to save changes.
 * 
 * @author David
 *
 */
public class ExitAction extends AbstractAction {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Reference to DrawingModel. This class has information about the modification
	 * status.
	 */
	private DrawingModel model;
	
	/**
	 * Reference to main window.
	 */
	private JVDraw frame;
	
	/**
	 * Reference to SaveAsAction. It is used to ask user to save document if
	 * modified.
	 */
	private SaveAsAction saveAsAction;
	
	/**
	 * Constructor.
	 * @param frame Reference to main window.
	 */
	public ExitAction(JVDraw frame, SaveAsAction saveAsAction) {
		this.frame = frame;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		MyWindowListener listener = new MyWindowListener(model, frame, saveAsAction);
		listener.windowClosing(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	/**
	 * Setter for DrawingModel.
	 * 
	 * @param model Reference to DrawingModel. This class has information about the
	 *              modification status.
	 */
	public void setDrawingModel(DrawingModel model) {
		this.model = model;
	}

}
