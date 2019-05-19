package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class JNotepadPP extends JFrame {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	private String windowTitle = "unnamed";

	private DefaultMultipleDocumentModel model;

	public JNotepadPP() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(500, 500);
		setLocationRelativeTo(null);

		model = new DefaultMultipleDocumentModel();

		setTitle("(" + windowTitle + ") - JNotepad++");

		initGUI();
	}

	private void initGUI() {
		Container cp = getContentPane();

		cp.setLayout(new BorderLayout());
		cp.add(model);

		configureActions();
		createMenus();
	}

	private void configureActions() {
		saveDocument.putValue(Action.NAME, "Save");
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.putValue(Action.SHORT_DESCRIPTION, "Open file from disk");
		
		createNewDocument.putValue(Action.NAME, "Create new");
		createNewDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift alt N"));
		createNewDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		createNewDocument.putValue(Action.SHORT_DESCRIPTION, "Create new document");
	}

	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		JMenu file = new JMenu("File");
		menuBar.add(file);
		file.add(new JMenuItem(saveDocument));

		JMenu edit = new JMenu("Edit");
		menuBar.add(edit);
		edit.add(new JMenuItem(createNewDocument));

		setJMenuBar(menuBar);
	}

	private final Action saveDocument = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.saveDocument(model.getCurrentDocument(), null);
		}
	};

	private final Action createNewDocument = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.createNewDocument();
		}
	};

	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(() -> {
				new JNotepadPP().setVisible(true);
			});
		} catch (InvocationTargetException | InterruptedException e) {
			System.out.println("Error");
		}
	}
}
