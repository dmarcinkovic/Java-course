package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.geometric.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.visitor.SaveDrawingModel;

/**
 * Action that allows user to save all modifications made from last save.
 * 
 * @author David
 *
 */
public class SaveAction extends AbstractAction {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Reference to DrawingModel. This class has collection of all
	 * GeometricalObjects drawn at canvas.
	 */
	private DrawingModel model;

	/**
	 * Reference to main window.
	 */
	private JVDraw frame;

	/**
	 * Reference to SaveAsAction.
	 */
	private SaveAsAction saveAsAction;

	/**
	 * Constructor.
	 * 
	 * @param file  File in which GeometricalObject presented at canvas will be
	 *              presented.
	 * @param frame Reference to main window.
	 */
	public SaveAction(JVDraw frame, SaveAsAction saveAsAction) {
		this.frame = frame;
		this.saveAsAction = saveAsAction;
	}

	/**
	 * Setter for DrawingModel.
	 * 
	 * @param model Reference to DrawingModel. This class has collection of all
	 *              GeometricalObjects drawn at canvas.
	 */
	public void setDrawingModel(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(new DrawingModelListenerImpl(this));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (SaveAsAction.getFile() == null && OpenAction.getFile() == null) {
			saveAsAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Save"));
			return;
		}

		if (!model.isModified()) {
			return;
		}

		Path file = SaveAsAction.getFile() == null ? OpenAction.getFile() : SaveAsAction.getFile();
		String jvd = getJvdFile();
		try {
			Files.writeString(file, jvd);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		model.clearModifiedFlag();
		JOptionPane.showMessageDialog(frame, "File saved successfully.", "Saved", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Method that returns String representing GeometricalObjects presented at
	 * canvas.
	 * 
	 */
	private String getJvdFile() {
		SaveDrawingModel saveDrawingModel = new SaveDrawingModel();

		for (int i = 0, n = model.getSize(); i < n; i++) {
			GeometricalObject object = model.getObject(i);

			object.accept(saveDrawingModel);
		}

		return saveDrawingModel.toString();
	}
}
