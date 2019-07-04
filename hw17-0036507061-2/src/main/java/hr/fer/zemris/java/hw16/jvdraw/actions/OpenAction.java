package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.geometric.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometric.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometric.Line;

/**
 * Action that allows user to choose the .jvd file from which GeometricalObjects
 * will be loaded.
 * 
 * @author David
 *
 */
public class OpenAction extends AbstractAction {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Reference to parent component. This reference is necessary when showing the
	 * FileChooser to user.
	 */
	private Container window;

	/**
	 * Reference to drawing model. It is used to add loaded GeometricalObjects to
	 * this model so that those GeometricalObjects can be presented at canvas.
	 */
	private DrawingModel model;
	
	/**
	 * Reference to opened .jvd file.
	 */
	private static Path path = null;

	/**
	 * Constructor used to initialize window and model.
	 * 
	 * @param window Container on which JOptionPane and JFileChooser are added.
	 * @param model  Reference to drawing model. It is used to add loaded
	 *               GeometricalObjects to this model so that those
	 *               GeometricalObjects can be presented at canvas.
	 */
	public OpenAction(Container window) {
		this.window = window;
	}

	/**
	 * Setter for drawing model.
	 * 
	 * @param model Reference to drawing model. It is used to add loaded
	 *              GeometricalObjects to this model so that those
	 *              GeometricalObjects can be presented at canvas.
	 */
	public void setDrawingModel(DrawingModel model) {
		this.model = model;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JVD files", "jvd");
		
		fileChooser.setFileFilter(filter);
		fileChooser.setAcceptAllFileFilterUsed(false);

		if (fileChooser.showOpenDialog(window) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		Path file = fileChooser.getSelectedFile().toPath();
		
		if (!Files.isReadable(file)) {
			JOptionPane.showMessageDialog(window, "You do not have right to open this file.", "Info",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		List<String> lines = null;
		try {
			lines = Files.readAllLines(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		loadObjects(lines);
		model.clearModifiedFlag();
		path = file;
	}

	/**
	 * Method used to load GeometricalObjects from .jvd file.
	 * 
	 * @param lines List of String representing the content of .jvd file.
	 */
	private void loadObjects(List<String> lines) {
		model.clear();
		
		for (int i = 0, n = lines.size(); i < n; i++) {
			String[] input = lines.get(i).split(" ");

			String type = input[0];

			if (type.equals("LINE")) {
				loadLine(input);
			} else if (type.equals("CIRCLE")) {
				loadCircle(input);
			} else if (type.equals("FCIRCLE")) {
				loadFilledCircle(input);
			} else {
				JOptionPane.showMessageDialog(window, "Error while parsing at line " + (i + 1), "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}

	/**
	 * Method used to load filled circle to canvas.
	 * 
	 * @param input Array of string representing center point, radius and line and
	 *              fill color.
	 */
	private void loadFilledCircle(String[] input) {
		if (input.length != 10) {
			JOptionPane.showMessageDialog(window, "Error while parsing.", "Error", JOptionPane.ERROR_MESSAGE);
		}

		FilledCircle filledCircle = new FilledCircle();
		try {
			filledCircle.setCenter(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
			filledCircle.setRadius(Double.parseDouble(input[3]));
			filledCircle.setLineColor(
					new Color(Integer.parseInt(input[4]), Integer.parseInt(input[5]), Integer.parseInt(input[6])));
			filledCircle.setFillColor(
					new Color(Integer.parseInt(input[7]), Integer.parseInt(input[8]), Integer.parseInt(input[9])));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(window, "Error while parsing.", "Error", JOptionPane.ERROR_MESSAGE);
		}

		model.add(filledCircle);
	}

	/**
	 * Method used to load circle to canvas.
	 * 
	 * @param input Array of strings representing center point, radius and line
	 *              color.
	 */
	private void loadCircle(String[] input) {
		if (input.length != 7) {
			JOptionPane.showMessageDialog(window, "Error while parsing.", "Error", JOptionPane.ERROR_MESSAGE);
		}

		Circle circle = new Circle();
		try {
			circle.setCenter(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
			circle.setRadius((double) Integer.parseInt(input[3]));
			circle.setLineColor(
					new Color(Integer.parseInt(input[4]), Integer.parseInt(input[5]), Integer.parseInt(input[6])));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(window, "Error while parsing.", "Error", JOptionPane.ERROR_MESSAGE);
		}

		model.add(circle);
	}

	/**
	 * Method used to load line to canvas.
	 * 
	 * @param input Array of strings representing start point, end point and line
	 *              color.
	 */
	private void loadLine(String[] input) {
		if (input.length != 8) {
			JOptionPane.showMessageDialog(window, "Error while parsing.", "Error", JOptionPane.ERROR_MESSAGE);
		}

		Line line = new Line();
		try {
			line.setStartPoint(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
			line.setEndPoint(Integer.parseInt(input[3]), Integer.parseInt(input[4]));
			line.setLineColor(
					new Color(Integer.parseInt(input[5]), Integer.parseInt(input[6]), Integer.parseInt(input[7])));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(window, "Error while parsing.", "Error", JOptionPane.ERROR_MESSAGE);
		}

		model.add(line);
	}
	
	/**
	 * Return loaded .jvd file.
	 * @return Loaded .jvd file.
	 */
	public static Path getFile() {
		return path;
	}

}
