package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.geometric.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.visitor.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.visitor.GeometricalObjectPainter;

/**
 * Action that allows user to export drawn GeometricalObject in .gif, .jpg and
 * .png format.
 * 
 * @author David
 *
 */
public class ExportAction extends AbstractAction {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Reference to canvas.
	 */
	private JComponent canvas;

	/**
	 * Reference to drawing model. It is used to add loaded GeometricalObjects to
	 * this model so that those GeometricalObjects can be presented at canvas.
	 */
	private DrawingModel model;

	/**
	 * Extension of exported image. It can be gif, png or jpg.
	 */
	private String extension;

	/**
	 * Setter for canvas.
	 * 
	 * @param canvas Component on which all GeometricalObjects are added.
	 */
	public void setCanvas(JComponent canvas) {
		this.canvas = canvas;
	}

	/**
	 * Setter for DrawingModel.
	 * 
	 * @param model Reference to drawing model. It is used to add loaded
	 *              GeometricalObjects to this model so that those
	 *              GeometricalObjects can be presented at canvas.
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
		Path path = getSelectedFile();

		if (path == null) {
			return;
		}

		System.out.println(path.toString());
		if (Files.exists(path)) {
			int result = JOptionPane.showConfirmDialog(canvas, "File already exists. Do you want to overwrite it?",
					"Info", JOptionPane.YES_NO_OPTION);

			if (result != JOptionPane.YES_OPTION) {
				return;
			}
		}

		Rectangle box = getBoundings();

		BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = image.createGraphics();

		// Set background color white.
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, box.width, box.height);

		drawObjects(g, (int) box.getX(), (int) box.getY());
		g.dispose();

		try {
			ImageIO.write(image, extension, path.toFile());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		JOptionPane.showMessageDialog(canvas, "Image is exported.", "Image exported", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Method that creates GeometricalObjectPainter and draws objects to the image.
	 * 
	 * @param g       Graphics used to draw GeometricalObjects to image.
	 * @param xOffset X coordinate of top left corner of image. When drawing image
	 *                we must move all GeometricalObjects by that offset.
	 * @param yOffset Y coordiante of top left corner of image.
	 */
	private void drawObjects(Graphics2D g, int xOffset, int yOffset) {
		GeometricalObjectPainter painter = new GeometricalObjectPainter(g, xOffset, yOffset);

		for (int i = 0, n = model.getSize(); i < n; i++) {
			GeometricalObject object = model.getObject(i);

			object.accept(painter);
		}
	}

	/**
	 * Method that returns bounding of image.
	 * 
	 * @return Rectangle representing bounding of image.
	 */
	private Rectangle getBoundings() {
		GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator(canvas.getWidth(), canvas.getHeight());

		for (int i = 0, n = model.getSize(); i < n; i++) {
			GeometricalObject object = model.getObject(i);

			object.accept(bbcalc);
		}

		return bbcalc.getBoundingBox();
	}

	/**
	 * Return Path that user chose in JFileChooser. This method returns null if user
	 * cancels JFileChooser window.
	 * 
	 * @return Path that user chose in JFileChooser.
	 */
	private Path getSelectedFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("jpg", "jpg"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("png", "png"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("gif", "gif"));

		fileChooser.setAcceptAllFileFilterUsed(false);

		if (fileChooser.showSaveDialog(canvas) != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		
		javax.swing.filechooser.FileFilter filter = fileChooser.getFileFilter();
		extension = filter.getDescription();
		
		return Paths.get(fileChooser.getSelectedFile().toString() + "." + extension);
	}

}
