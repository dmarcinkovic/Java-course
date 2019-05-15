package hr.fer.zemris.java.gui.calc;

import java.awt.Container;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JCheckBox;
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

/**
 * Basic calculator with functions : "1/x" , "sin", "cos" , "log", "ln", "tan",
 * "ctg", "x^n", "arcsin", "arccos", "arctan", "arcctg", "e^x", "10^x", x^(1/n)
 * and operations : push , pop, +, -, / and *.
 * 
 * @author david
 *
 */
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
		setSize(600, 300);
		initGUI();
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

		addButtons(cp, model);
	}

	/**
	 * Add buttons with number and functions and operators. Available functions are:
	 * "1/x" , "sin", "cos" , "log", "ln", "tan", "ctg", "x^n", "arcsin", "arccos",
	 * "arctan", "arcctg", "e^x", "10^x", x^(1/n). Also it adds all operation
	 * buttons. Available operations are : +, -, /, *.
	 * 
	 * @param cp    Content pain in which buttons are added.
	 * @param model Calculator model.
	 */
	private void addButtons(Container cp, CalcModel model) {
		new Numbers(model, cp).addButtons();

		Functions functions = new Functions(model, cp);
		functions.addButtons();

		Operators operators = new Operators(model, cp);

		operators.addButtons();

		JCheckBox checkBox = new JCheckBox("Inv");

		cp.add(checkBox, new RCPosition(5, 7));

		checkBox.addActionListener(e -> {
			functions.swap();
			operators.swap();
		});

		new Operations(model, cp).addButtons();
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
