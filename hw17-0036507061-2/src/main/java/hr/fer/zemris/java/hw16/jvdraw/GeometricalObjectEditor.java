package hr.fer.zemris.java.hw16.jvdraw;

import javax.swing.JPanel;

/**
 * Abstract class that represents objects who are capable of changing
 * GeometricalObjects presented at canvas.
 * 
 * @author David
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Method that checks if uses's input in valid. When user double-clicks on
	 * GeometricalObject presented at list on the right size of window, new dialog
	 * for editing particular GeometricalObject will be popped up. In that dialog
	 * user can type new parameter of particular object. For example, if object is
	 * line, user can change start and end point of line.
	 */
	public abstract void checkEditing();

	/**
	 * Method that applies changes to GeometricalObjects.
	 */
	public abstract void acceptEditing();
}
