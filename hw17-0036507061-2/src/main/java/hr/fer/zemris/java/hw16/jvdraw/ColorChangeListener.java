package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

/**
 * Interface that represents classes who wants to be informed when new color is
 * selected.
 * 
 * @author David
 *
 */
public interface ColorChangeListener {

	/**
	 * Method that is called when new color is selected. It informs all registered
	 * listeners about this change.
	 * 
	 * @param source   Color provider. This class is source because in this class we
	 *                 change the color and inform registered listener.
	 * @param oldColor Old color.
	 * @param newColor New color.
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
