package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.geometric.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.visitor.SaveDrawingModel;

/**
 * Action that allows user to choose the destination of .jvd file he wants to
 * save.
 * 
 * @author David
 *
 */
public class SaveAsAction extends AbstractAction {

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
	 * File in which GeometricalObject presented at canvas will be presented.
	 */
	private static Path path = null;

	/**
	 * Boolean flag that keeps track when user canceled operation.
	 */
	private boolean canceled = true;

	/**
	 * Constructor.
	 * 
	 * @param frame Reference to main window.
	 */
	public SaveAsAction(JVDraw frame) {
		this.frame = frame;
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
		JFileChooser jfc = new JFileChooser();

		jfc.setAcceptAllFileFilterUsed(false);
		jfc.addChoosableFileFilter(new FileNameExtensionFilter("JVD files", "jvd"));

		canceled = true;
		
		if (jfc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		Path file = jfc.getSelectedFile().toPath();

		if (!file.toString().endsWith(".jvd")) {
			JOptionPane.showMessageDialog(frame, "File should have .jvd extension.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (Files.exists(file)) {
			int result = JOptionPane.showConfirmDialog(frame, "File already exists. Do you want to overwrite it?",
					"Save", JOptionPane.YES_NO_OPTION);

			if (result != JOptionPane.YES_OPTION) {
				return;
			}
		}

		String jvdFile = getJvdFile();
		try {
			Files.writeString(file, jvdFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		model.clearModifiedFlag();
		path = file;
		
		canceled = false;
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

	/**
	 * Returns file in which GeometricalObject presented at canvas will be
	 * presented.
	 * 
	 * @return File in which GeometricalObject presented at canvas will be
	 *         presented.
	 */
	public static Path getFile() {
		return path;
	}

	/**
	 * Getter for canceled.
	 * 
	 * @return Canceled.
	 */
	public boolean isCanceled() {
		return canceled;
	}
}
