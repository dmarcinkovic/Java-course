package hr.fer.zemris.java.gui.layouts.demo;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Demo example to show how custom created CalcLayuot works.
 * 
 * @author david
 *
 */
public class DemoFrame1 extends JFrame {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor to initialize gui components.
	 */
	public DemoFrame1() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		initGUI();
	}

	/**
	 * Constructor to initialize gui components.
	 */
	/*
	 * public DemoFrame1() {
	 * setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); initGUI();
	 * pack(); }
	 */

	/**
	 * Method to initialize gui components. This method is used to add components to
	 * this layout.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		CalcLayout layout = new CalcLayout(3);
		cp.setLayout(layout);

		cp.add(l("tekst 1"), new RCPosition(1, 1));
		cp.add(l("tekst 2"), new RCPosition(2, 3));
		cp.add(l("tekst stvarno najdulji"), new RCPosition(2, 7));
		cp.add(l("tekst kraći"), new RCPosition(4, 2));
		cp.add(l("tekst srednji"), new RCPosition(4, 5));
		cp.add(l("tekst"), new RCPosition(4, 7));
	}

	/**
	 * Returns JLabel with default yellow color and opaque set to true.
	 * 
	 * @param text Text to be added to component.
	 * @return JLabel with default yellow color nad opaque set to true.
	 */
	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		return l;
	}

	/**
	 * Method invoked when running the program. This method create instance of this
	 * class and runs the gui that crates new window.
	 * 
	 * @param args Arguments provided via command line. In this program they are not
	 *             used.
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			new DemoFrame1().setVisible(true);
		});
	}
}
