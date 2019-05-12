package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Program that draws the LSystem picture. It loads configuration from the file.
 * 
 * @author david
 *
 */
public class Glavni3 {

	/**
	 * Method invoked when running the program. It loads configuration from the
	 * file. Depth can be chosen in the combo box at the top of the window.
	 * 
	 * @param args Arguments provided via command line. In this example they are not
	 *             used.
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}
}
