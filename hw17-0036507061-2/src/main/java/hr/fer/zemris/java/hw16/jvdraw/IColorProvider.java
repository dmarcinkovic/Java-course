package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

/**
 * Interface that represents classes who have information about the
 * 
 * @author David
 *
 */
public interface IColorProvider {

	/**
	 * Returns the current color.
	 * 
	 * @return Current color.
	 */
	public Color getCurrentColor();

	/**
	 * Adds interested listener. After this method is called, object l will be
	 * inform whenever the ColorProvider changes the color.
	 * 
	 * @param l Object that is interested in receiving notifications when color is
	 *          changed.
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Removes interested listener. After this method is called, object l will be no
	 * more informed when the ColorProvider changes the color.
	 * 
	 * @param l Object that will be removed.
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}
