package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

public class JNotepadPP extends JFrame {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	private String windowTitle = "unnamed";

	private DefaultMultipleDocumentModel model;

	public JNotepadPP() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(600, 500);

		model = new DefaultMultipleDocumentModel();

		setTitle("JNotepad++");

		initGUI();
		setLocationRelativeTo(null);

		addWindowListener(new MyWindowListener());

		model.addMultipleDocumentListener(new MyMultipleDocumentListener());
	}

	private void initGUI() {
		Container cp = getContentPane();

		cp.setLayout(new BorderLayout());
		cp.add(model);

		configureActions();
		createMenus();
		createToolbar();
		createStatusBar();
	}

	private void createStatusBar() {
		JLabel statusBar = new JLabel("Status bar");

		getContentPane().add(statusBar, BorderLayout.PAGE_END);
	}

	private void createToolbar() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);

		tb.add(new JButton(copy));
		tb.add(new JButton(paste));
		tb.add(new JButton(cut));
		tb.add(new JButton(statisticalInfo));
		tb.add(new JButton(saveDocument));
		tb.add(new JButton(saveAsDocument));
		tb.add(new JButton(exitApplication));
		tb.add(new JButton(openExistingDocument));
		tb.add(new JButton(createNewDocument));
		tb.add(new JButton(close));

		getContentPane().add(tb, BorderLayout.PAGE_START);
	}

	private void configureActions() {
		configureSaveDocumentAction();
		configureCreateNewDocument();
		configureExitApplication();
		configureSaveAsDocument();
		configurePaste();
		configureCopy();
		configureCut();
		configureStatisticalInfo();
		configureOpen();
		configureClose();
	}

	private void configureExitApplication() {
		exitApplication.putValue(Action.NAME, "Exit");
		exitApplication.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		exitApplication.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exitApplication.putValue(Action.SHORT_DESCRIPTION, "Exit application");
	}

	private void configureSaveDocumentAction() {
		saveDocument.putValue(Action.NAME, "Save");
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.putValue(Action.SHORT_DESCRIPTION, "Save file to disk");
	}

	private void configureCreateNewDocument() {
		createNewDocument.putValue(Action.NAME, "New");
		createNewDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createNewDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		createNewDocument.putValue(Action.SHORT_DESCRIPTION, "Create new document");
	}

	private void configureSaveAsDocument() {
		saveAsDocument.putValue(Action.NAME, "Save as...");
		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift A"));
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsDocument.putValue(Action.SHORT_DESCRIPTION, "Save file to disk");
	}

	private void configurePaste() {
		paste.putValue(Action.NAME, "Paste");
		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift V"));
		paste.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		paste.putValue(Action.SHORT_DESCRIPTION, "Paste text");
	}

	private void configureCopy() {
		copy.putValue(Action.NAME, "Copy");
		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift C"));
		copy.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copy.putValue(Action.SHORT_DESCRIPTION, "Copy text");
	}

	private void configureCut() {
		cut.putValue(Action.NAME, "Cut");
		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift X"));
		cut.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cut.putValue(Action.SHORT_DESCRIPTION, "Cut text. Delete and save to clipboard.");
	}

	private void configureStatisticalInfo() {
		statisticalInfo.putValue(Action.NAME, "Info");
		statisticalInfo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		statisticalInfo.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		statisticalInfo.putValue(Action.SHORT_DESCRIPTION, "Show statistical info.");
	}

	private void configureOpen() {
		openExistingDocument.putValue(Action.NAME, "Open");
		openExistingDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openExistingDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openExistingDocument.putValue(Action.SHORT_DESCRIPTION, "Open existing document.");
	}

	private void configureClose() {
		close.putValue(Action.NAME, "Close");
		close.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		close.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		close.putValue(Action.SHORT_DESCRIPTION, "Close current tab.");
	}

	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		paste.setEnabled(false);
		copy.setEnabled(false);
		cut.setEnabled(false);

		statisticalInfo.setEnabled(false);
		saveDocument.setEnabled(false);
		saveAsDocument.setEnabled(false);
		close.setEnabled(false);

		JMenu file = new JMenu("File");
		menuBar.add(file);
		file.add(new JMenuItem(createNewDocument));
		file.add(new JMenuItem(exitApplication));
		file.add(new JMenuItem(openExistingDocument));

		JMenu edit = new JMenu("Edit");
		menuBar.add(edit);

		edit.add(new JMenuItem(paste));
		edit.add(new JMenuItem(copy));
		edit.add(new JMenuItem(cut));

		file.add(new JMenuItem(statisticalInfo));
		file.add(new JMenuItem(saveDocument));
		file.add(new JMenuItem(saveAsDocument));
		file.add(new JMenuItem(close));

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

	private final Action saveAsDocument = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.saveAsDocument(model.getCurrentDocument());
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

	private final Action exitApplication = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			new MyWindowListener().windowClosing(new WindowEvent(JNotepadPP.this, WindowEvent.WINDOW_CLOSING));
		}
	};

	private final Action close = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel tab = model.getCurrentDocument();

			if (tab.isModified()) {
				askUser(tab, tab.toString());
			} else {
				model.closeDocument(tab);
			}
		}
	};

	private void askUser(SingleDocumentModel tab, String name) {
		int result = JOptionPane.showConfirmDialog(JNotepadPP.this, "Do you want to close " + name + " without saving?",
				"Question", JOptionPane.YES_NO_CANCEL_OPTION);

		if (result == JOptionPane.YES_OPTION) {
			model.closeDocument(tab);
		} else if (result == JOptionPane.NO_OPTION) {
			saveDocument.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Save"));
		}
	}

	private String getTextFromClipboard() {
		String data = null;
		try {
			data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (HeadlessException | UnsupportedFlavorException | IOException e1) {
		}
		return data;
	}

	private final Action paste = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			String data = getTextFromClipboard();

			if (data != null && data != "") {
				JTextArea editor = model.getCurrentDocument().getTextComponent();
				Caret caret = editor.getCaret();

				int start = Math.min(caret.getMark(), caret.getDot());
				int end = Math.max(caret.getMark(), caret.getDot());

				Document doc = editor.getDocument();
				String text = null;
				try {
					text = doc.getText(0, doc.getLength());
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}

				StringBuilder sb = new StringBuilder();
				sb.append(text.substring(0, start)).append(data).append(text.substring(end));

				editor.setText(sb.toString());
			}
		}
	};

	private Action cut = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea editor = model.getCurrentDocument().getTextComponent();

			Document doc = editor.getDocument();
			Caret caret = editor.getCaret();

			int start = Math.min(caret.getDot(), caret.getMark());
			int len = Math.abs(caret.getDot() - caret.getMark());

			String text = null;
			try {
				text = doc.getText(start, len);
				doc.remove(start, len);
			} catch (BadLocationException e1) {
				JOptionPane.showMessageDialog(JNotepadPP.this, "Cannot cut.", "Error", JOptionPane.ERROR_MESSAGE);
			}

			setTextToClipboard(text);
		}
	};

	private final Action copy = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea editor = model.getCurrentDocument().getTextComponent();
			Caret caret = editor.getCaret();

			int start = Math.min(caret.getDot(), caret.getMark());
			int len = Math.abs(caret.getDot() - caret.getMark());

			String theString = null;
			try {
				theString = editor.getDocument().getText(start, len);
			} catch (BadLocationException e1) {
				JOptionPane.showMessageDialog(JNotepadPP.this, "Cannot copy", "Error", JOptionPane.ERROR_MESSAGE);
			}

			setTextToClipboard(theString);
		}
	};

	private void setTextToClipboard(String theString) {
		StringSelection selection = new StringSelection(theString);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);
	}

	private final Action openExistingDocument = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Open file");

			if (jfc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			Path file = jfc.getSelectedFile().toPath();

			if (!Files.isReadable(file)) {
				JOptionPane.showMessageDialog(JNotepadPP.this, "You do not have right to read from that file.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			model.loadDocument(file);
		}
	};

	private Action statisticalInfo = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea editor = model.getCurrentDocument().getTextComponent();

			int length = editor.getText().length();
			int nonSpaceLength = getNumberOfNonSpaces(editor.getText());
			int numberOfLines = getNumberOfNewLineSymbols(editor.getText()) + 1;

			StringBuilder sb = new StringBuilder();
			sb.append("Your document has ").append(length).append(" characters, ").append(nonSpaceLength)
					.append(" non-blank characters and ").append(numberOfLines).append(" lines.");

			JOptionPane.showMessageDialog(JNotepadPP.this, sb.toString(), "Info", JOptionPane.INFORMATION_MESSAGE);
		}

		private int getNumberOfNewLineSymbols(String text) {
			int number = 0;
			char[] data = text.toCharArray();

			for (Character c : data) {
				if (c == '\n') {
					number++;
				}
			}

			return number;
		}

		private int getNumberOfNonSpaces(String text) {
			int number = 0;
			char[] data = text.toCharArray();

			for (Character c : data) {
				if (!Character.isWhitespace(c)) {
					number++;
				}
			}

			return number;
		}
	};

	private class MyWindowListener extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			Iterator<SingleDocumentModel> itr = model.iterator();

			while (itr.hasNext()) {
				SingleDocumentModel document = itr.next();

				if (document.isModified()) {
					StringBuilder sb = new StringBuilder();
					sb.append("Do you want to close the: ").append(document.toString()).append(" without saving?");

					if (!askUserToCloseTheTab(itr, sb.toString())) {
						return;
					}
				}
			}

			e.getWindow().dispose();
		}
	}

	private boolean askUserToCloseTheTab(Iterator<SingleDocumentModel> itr, String text) {
		int result = JOptionPane.showConfirmDialog(JNotepadPP.this, text, "Question", JOptionPane.YES_NO_CANCEL_OPTION);

		if (JOptionPane.YES_OPTION == result) {
			itr.remove();
		} else if (JOptionPane.NO_OPTION == result) {
			saveDocument.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Save"));
		} else {
			return false;
		}
		return true;
	}

	private class MyMultipleDocumentListener implements MultipleDocumentListener {

		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
			if (previousModel == null && currentModel == null) {
				throw new NullPointerException();
			}

			windowTitle = ((DefaultSingleDocumentModel) currentModel).getPathName();
			setTitle("(" + windowTitle + ") - JNotepad++");

			Caret caret = currentModel.getTextComponent().getCaret();

			if (caret.getDot() != caret.getMark()) {
				copy.setEnabled(true);
				cut.setEnabled(true);
			} else {
				copy.setEnabled(false);
				cut.setEnabled(false);
			}
		}

		@Override
		public void documentAdded(SingleDocumentModel model) {
			if (JNotepadPP.this.model.getNumberOfDocuments() != 0) {
				paste.setEnabled(true);
				statisticalInfo.setEnabled(true);
				saveDocument.setEnabled(true);
				saveAsDocument.setEnabled(true);
				close.setEnabled(true);
			}
		}

		@Override
		public void documentRemoved(SingleDocumentModel model) {
			if (JNotepadPP.this.model.getNumberOfDocuments() == 0) {
				paste.setEnabled(false);
				statisticalInfo.setEnabled(false);
				saveDocument.setEnabled(false);
				saveAsDocument.setEnabled(false);
				close.setEnabled(false);
			}
		}
	}

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
