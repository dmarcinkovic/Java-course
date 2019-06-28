package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.actions.SaveAsAction;

/**
 * Implementation of WindowListener. This class is used to listen the closing
 * event. If user attempts to close the program, this method will be called and
 * appropriate action will be executed.
 * 
 * @author David
 *
 */
public class MyWindowListener extends WindowAdapter {

	/**
	 * 
	 * Reference to DrawingModel. This class has information about the modification
	 * status.
	 */
	private DrawingModel model;

	/**
	 * Reference to the main window.
	 */
	private JVDraw frame;

	/**
	 * Reference to SaveAsAction. It is used to ask user to save document if
	 * modified.
	 */
	private SaveAsAction saveAsAction;

	/**
	 * Constructor used to receive reference to DrawingModel. This reference is then
	 * used to check modification status.
	 * 
	 * @param model DrawingModel.
	 * @param frame Reference to main window.
	 */
	public MyWindowListener(DrawingModel model, JVDraw frame, SaveAsAction saveAsAction) {
		this.model = model;
		this.frame = frame;
		this.saveAsAction = saveAsAction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		if (model.isModified()) {
			int result = JOptionPane.showConfirmDialog(frame, "Exit without saving?", "Exit",
					JOptionPane.YES_NO_OPTION);

			if (result == JOptionPane.YES_OPTION) {
				e.getWindow().dispose();
				return;
			}

			saveAsAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Save"));
		}
		
		if (!saveAsAction.isCanceled()) {
			e.getWindow().dispose();
		}
	}
}
