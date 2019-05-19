package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class JNotepadPP extends JFrame{

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	private String windowTitle = "unnamed";
	
	public JNotepadPP() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(500, 500);
		setLocationRelativeTo(null);
		
		setTitle("(" + windowTitle + ") - JNotepad++");
		
		initGUI();
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		
		cp.setLayout(new BorderLayout());
		
		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab(windowTitle, new JScrollPane(new JTextArea()));
		
		cp.add(tabs);
	}
	
	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(() -> {
				new JNotepadPP().setVisible(true);
			});
		} catch (InvocationTargetException | InterruptedException e) {
		}
	}
}
