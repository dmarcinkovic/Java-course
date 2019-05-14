package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * Program that creates new window in which two lists and one button are
 * presented. If button is pressed on each list will be added next prime number.
 * At start, list contains only number 1.
 * 
 * @author david
 *
 */
public class PrimDemo extends JFrame {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Set default close operation and initialize gui.
	 */
	public PrimDemo() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		setSize(500, 500);
	}

	/**
	 * Initialize gui components. This method adds two lists and one button to this
	 * window.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		PrimListModel model = new PrimListModel();
		JList<Long> list1 = new JList<>(model);
		JList<Long> list2 = new JList<>(model);

		JPanel centralPanel = new JPanel(new GridLayout(1, 0));
		cp.add(centralPanel, BorderLayout.CENTER);

		centralPanel.add(new JScrollPane(list1));
		centralPanel.add(new JScrollPane(list2));

		JButton next = new JButton("sljedeći");
		cp.add(next, BorderLayout.PAGE_END);

		next.addActionListener(e -> {
			model.next();
		});
	}

	/**
	 * Method invoked when running the program. This method creates new GUI
	 * thread(Event Dispatching Thread) and creates new window in which two lists
	 * and one button at page end.
	 * 
	 * @param args Arguments provided via command line. In this program they are not used.
	 */ 
	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(() -> {
				new PrimDemo().setVisible(true);
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
