package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Program that draws 'Koch Curve' using Lindermayer Systems. 'Koch Curve' can
 * be drawn up to 6-th level.
 * 
 * @author david
 *
 */
public class Glavni1 {

	/**
	 * Method invoked when running the program. This program draws 'Koch Curve' with
	 * the specified depth. Program creates new window in which 'Koch Curve' is
	 * drawn. Depth can be chosen in combo box at the top of the window.
	 * 
	 * @param args Arguments provided via command line. In this example they are not
	 *             used.
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));
	}

	/**
	 * Defines the origin of the turtle, angle, unit length. Also, this method
	 * defines commands.
	 * 
	 * @param provider Provider.
	 * @return LSystem representing Koch Curve.
	 */
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		return provider.createLSystemBuilder().registerCommand('F', "draw 1").registerCommand('+', "rotate 60")
				.registerCommand('-', " rotate -60").setOrigin(0.05, 0.4).setAngle(0).setUnitLength(0.9)
				.setUnitLengthDegreeScaler(1.0 / 3.0).registerProduction('F', "F+F--F+F").setAxiom("F").build();
	}

}
