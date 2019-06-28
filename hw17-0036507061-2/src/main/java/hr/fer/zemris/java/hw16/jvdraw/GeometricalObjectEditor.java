package hr.fer.zemris.java.hw16.jvdraw;

import javax.swing.JPanel;

public abstract class GeometricalObjectEditor extends JPanel {
	
	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	public abstract void checkEditing();

	public abstract void acceptEditing();
}
