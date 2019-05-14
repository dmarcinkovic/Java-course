package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.MyButton;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;


public class Calculator extends JFrame {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;
	
	private final int ROW = 5; 
	
	private final int COL = 7; 
	
	private Component[][] components;
	
	/**
	 * Initializes gui for calculator. 
	 */
	public Calculator() {
		components = new Component[ROW][COL];
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Java Calculator v1.0");
		initGUI();
		pack();
	}

	/**
	 * Initializes gui for calculator. It adds all necessary components to
	 * container.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		Color c = cp.getBackground();
			
		
		/// Imati model koji implementira akciju i salje u taj razred referencu na calcmodel. 
		// Zatim u actionPerformed zvati metodu insertDigit i takve slicne metode. 
		CalcLayout layout = new CalcLayout(5);
		
		cp.setLayout(layout);
		JButton button = new JButton("Button1");
		JButton button2 = new JButton("Button2");
		
		cp.add(button, new RCPosition(5, 5));
		cp.add(button2, new RCPosition(2, 5));
		
		List<JButton> list = new ArrayList<>();
		
		for (JButton ca : list) {
			ca.addActionListener(e -> {
				System.out.println(ca.getText());
			});
		}
	
		button.addActionListener(e -> {
			System.out.println(button.getText());
		});
		
		components[1][2] = new MyButton("7");
		components[0][0] = new JLabel("TEST");
		components[0][5] = new MyButton("=");
		
		cp.add(components[1][2], new RCPosition(2, 3));
		cp.add(components[0][0], new RCPosition(1, 1));
		cp.add(components[0][5], new RCPosition(1, 6));
		
		components[0][0].setBackground(Color.YELLOW);
		components[0][0].setVisible(true);		
		components[0][0].setFont(components[0][0].getFont().deriveFont(30f));
		((JComponent) components[0][0]).setOpaque(true);
		
		System.out.println();
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
