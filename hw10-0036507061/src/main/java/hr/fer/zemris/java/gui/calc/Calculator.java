package hr.fer.zemris.java.gui.calc;

import java.awt.Container;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.MyLabel;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class Calculator extends JFrame {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Initializes gui for calculator.
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Java Calculator v1.0");
		initGUI();
		setSize(500, 500);
	}

	/**
	 * Initializes gui for calculator. It adds all necessary components to
	 * container.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		CalcLayout layout = new CalcLayout(3);
		cp.setLayout(layout);

		CalcModel model = new CalcModelImpl();

		JLabel label = new MyLabel(model, SwingConstants.RIGHT);

		cp.add(label, new RCPosition(1, 1));

		Numbers numbers = new Numbers(model, cp);
		numbers.addNumbers();
	}


	/**
	 * Method invoked when running the program. This method creates new window in
	 * which calculator is presented.
	 * 
	 * @param args Arguments provided via command line. In this program they are not
	 *             used.
	 */
	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(() -> {
				new Calculator().setVisible(true);
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
