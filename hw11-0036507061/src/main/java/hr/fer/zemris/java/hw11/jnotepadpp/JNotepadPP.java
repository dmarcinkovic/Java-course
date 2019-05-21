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

/**
 * Program that creates program similar to Notepad++ on Window operating system.
 * User can add one or more tabs in which he can work in parallel. Also, user
 * can save those modified documents at whatever directory he wants. This
 * program has very nice GUI. At the start of the page there is toolbar with all
 * supported actions. Supported actions are : copy, paste, cut, close tab, exit
 * application, save document, save document as, show statistical info, create
 * new document, open existing document. All those actions are also available
 * via menu buttons presented at page start. Information about changes are
 * printed to status bar which is placed at bottom of window. Also, user can
 * access actions via keyboard shortcuts.  
 * 
 * @author david
 *
 */
public class JNotepadPP extends JFrame {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Window title. This changes dependently on tab that is selected.
	 */
	private String windowTitle = "unnamed";

	/**
	 * JTabbePane model.
	 */
	private DefaultMultipleDocumentModel model;

	/**
	 * Constructor that initializes GUI and creates all necessary listeners.
	 */
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

	/**
	 * Initialize gui. This method add menus, toolbar and status bar to this window.
	 */
	private void initGUI() {
		Container cp = getContentPane();

		cp.setLayout(new BorderLayout());
		cp.add(model);

		configureActions();
		createMenus();
		createToolbar();
		createStatusBar();
	}

	/**
	 * Creates status bar. Status bar is just JLabel. This status bar is updated
	 * when some changes in GUI occurs. For example when document is saved.
	 */
	private void createStatusBar() {
		JLabel statusBar = new JLabel("Status bar");

		getContentPane().add(statusBar, BorderLayout.PAGE_END);
	}

	/**
	 * Creates toolbar with this actions: copy, cut, paste, exit application, close
	 * tab, show statistical info, save document, save document as, create new
	 * document, open existing document. This toolbar is floatable, which means that
	 * user can drag that toolbar outside the main window.
	 */
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

	/**
	 * Method used to configure actions. This method sets name to each action, also
	 * it sets accelerator key and mnemonic key. It sets one additional thing:
	 * description of the action. This description is shown when user holds the
	 * mouse for some time under the action menu of button on toolbar.
	 */
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

	/**
	 * Method used to configure exit application action. This method sets name for
	 * this action, also it sets accelerator key and mnemonic key. It sets one
	 * additional thing: description of the action. This description is shown when
	 * user holds the mouse for some time under the action menu of button on
	 * toolbar.
	 */
	private void configureExitApplication() {
		exitApplication.putValue(Action.NAME, "Exit");
		exitApplication.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		exitApplication.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exitApplication.putValue(Action.SHORT_DESCRIPTION, "Exit application");
	}

	/**
	 * Method used to configure save document action. This method sets name for this
	 * action, also it sets accelerator key and mnemonic key. It sets one additional
	 * thing: description of the action. This description is shown when user holds
	 * the mouse for some time under the action menu of button on toolbar.
	 */
	private void configureSaveDocumentAction() {
		saveDocument.putValue(Action.NAME, "Save");
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.putValue(Action.SHORT_DESCRIPTION, "Save file to disk");
	}

	/**
	 * Method used to configure create new document action. This method sets name
	 * for this action, also it sets accelerator key and mnemonic key. It sets one
	 * additional thing: description of the action. This description is shown when
	 * user holds the mouse for some time under the action menu of button on
	 * toolbar.
	 */
	private void configureCreateNewDocument() {
		createNewDocument.putValue(Action.NAME, "New");
		createNewDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createNewDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		createNewDocument.putValue(Action.SHORT_DESCRIPTION, "Create new document");
	}

	/**
	 * Method used to create save as document action. This method sets name for this
	 * action, also it sets accelerator key and mnemonic key. It sets one additional
	 * thing: description of the action. This description is shown when user holds
	 * the mouse for some time under the action menu of button on toolbar.
	 */
	private void configureSaveAsDocument() {
		saveAsDocument.putValue(Action.NAME, "Save as...");
		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift A"));
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsDocument.putValue(Action.SHORT_DESCRIPTION, "Save file to disk");
	}

	/**
	 * Method used to configure paste action. This method sets name for this action,
	 * also it sets accelerator key and mnemonic key. It sets one additional thing:
	 * description of the action. This description is shown when user holds the
	 * mouse for some time under the action menu of button on toolbar.
	 */
	private void configurePaste() {
		paste.putValue(Action.NAME, "Paste");
		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift V"));
		paste.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		paste.putValue(Action.SHORT_DESCRIPTION, "Paste text");
	}

	/**
	 * Method used to configure copy action. This method sets name for this action,
	 * also it sets accelerator key and mnemonic key. It sets one additional thing:
	 * description of the action. This description is shown when user holds the
	 * mouse for some time under the action menu of button on toolbar.
	 */
	private void configureCopy() {
		copy.putValue(Action.NAME, "Copy");
		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift C"));
		copy.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copy.putValue(Action.SHORT_DESCRIPTION, "Copy text");
	}

	/**
	 * Method used to configure cut action. This method sets name for this action,
	 * also it sets accelerator key and mnemonic key. It sets one additional thing:
	 * description of the action. This description is shown when user holds the
	 * mouse for some time under the action menu of button on toolbar.
	 */
	private void configureCut() {
		cut.putValue(Action.NAME, "Cut");
		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift X"));
		cut.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cut.putValue(Action.SHORT_DESCRIPTION, "Cut text. Delete and save to clipboard.");
	}

	/**
	 * Configure statistical info action. This method sets name for this action,
	 * also it sets accelerator key and mnemonic key. It sets one additional thing:
	 * description of the action. This description is shown when user holds the
	 * mouse for some time under the action menu of button on toolbar.
	 */
	private void configureStatisticalInfo() {
		statisticalInfo.putValue(Action.NAME, "Info");
		statisticalInfo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		statisticalInfo.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		statisticalInfo.putValue(Action.SHORT_DESCRIPTION, "Show statistical info.");
	}

	/**
	 * Configure open existing document action. This method sets name for this
	 * action, also it sets accelerator key and mnemonic key. It sets one additional
	 * thing: description of the action. This description is shown when user holds
	 * the mouse for some time under the action menu of button on toolbar.
	 */
	private void configureOpen() {
		openExistingDocument.putValue(Action.NAME, "Open");
		openExistingDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openExistingDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openExistingDocument.putValue(Action.SHORT_DESCRIPTION, "Open existing document.");
	}

	/**
	 * Configure close tab action. This method sets name for this action, also it
	 * sets accelerator key and mnemonic key. It sets one additional thing:
	 * description of the action. This description is shown when user holds the
	 * mouse for some time under the action menu of button on toolbar.
	 */
	private void configureClose() {
		close.putValue(Action.NAME, "Close");
		close.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		close.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		close.putValue(Action.SHORT_DESCRIPTION, "Close current tab.");
	}

	/**
	 * Method that creates menu for each action.
	 */
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

	/**
	 * Action that saves documents. If document is not saved yet, this action ask
	 * user to choose the filename and directory in which he wants to save the
	 * document. If is saved already, this method will just save all changes made.
	 */
	private final Action saveDocument = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}. Saves the document. If document is not saved yet, this action
		 * ask user to choose the filename and directory in which he wants to save the
		 * document. If is saved already, this method will just save all changes made.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			model.saveDocument(model.getCurrentDocument(), null);
		}
	};

	/**
	 * Action to save document. It always asks user to choose the filename and
	 * directory in which he wants to save the document.
	 */
	private final Action saveAsDocument = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}. t always asks user to choose the filename and directory in
		 * which he wants to save the document.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			model.saveAsDocument(model.getCurrentDocument());
		}
	};

	/**
	 * Action that creates new document. This action sets default name to "unnamed".
	 * Also, this action , after it creates new document, switches to that newly
	 * created document.
	 */
	private final Action createNewDocument = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			model.createNewDocument();
		}
	};

	/**
	 * Exits application. This action asks user if he wants to save all unsaved
	 * documents. If user at any moment clicks the cancel button, application will
	 * not be quit, but tabs that he closed will stay closed.
	 */
	private final Action exitApplication = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * This method performs action that asks user if he wants to save all unsaved
		 * documents. If user at any moment clicks the cancel button, application will
		 * not be quit, but tabs that he closed will stay closed.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			new MyWindowListener().windowClosing(new WindowEvent(JNotepadPP.this, WindowEvent.WINDOW_CLOSING));
		}
	};

	/**
	 * Action that is used to close one selected tab. If that tab is modified, this
	 * action will ask the user to confirm that he wants to saves changes he made,
	 * or he wants to quit without saving.
	 */
	private final Action close = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Performs closing action. If that tab is modified, this action will ask the
		 * user to confirm that he wants to saves changes he made, or he wants to quit
		 * without saving.
		 */
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

	/**
	 * Asks user if he wants to close the tab without saving. If he clicks yes, then
	 * the tab will be closed and last made change will not be saved. If user clicks
	 * no, then new window will appear, that will ask the user to choose the
	 * filename and directory.
	 * 
	 * @param tab  Tab to be closed.
	 * @param name Text to be presented in option pane.
	 */
	private void askUser(SingleDocumentModel tab, String name) {
		int result = JOptionPane.showConfirmDialog(JNotepadPP.this, "Do you want to close " + name + " without saving?",
				"Question", JOptionPane.YES_NO_CANCEL_OPTION);

		if (result == JOptionPane.YES_OPTION) {
			model.closeDocument(tab);
		} else if (result == JOptionPane.NO_OPTION) {
			saveDocument.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Save"));
		}
	}

	/**
	 * Returns the text from clipboard.
	 * 
	 * @return The text from clipboard.
	 */
	private String getTextFromClipboard() {
		String data = null;
		try {
			data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (HeadlessException | UnsupportedFlavorException | IOException e1) {
		}
		return data;
	}

	/**
	 * Action used to paste the text from clipboard to current document. This action
	 * is not enabled until the document is created.
	 */
	private final Action paste = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}. Method that performs an action that pastes text from clipboard
		 * to current document.
		 */
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

	/**
	 * Action that is used to cut the text from document and save that text to
	 * clipboard. This action is not enabled until the document is created and the
	 * text is marked.
	 */
	private Action cut = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Method that performs an action that cuts the marked text and saves that text
		 * to clipboard.
		 */
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

	/**
	 * Action used to copy the text from document to clipboard. This action is
	 * disabled until there is no marked text.
	 */
	private final Action copy = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Performs copy action. This action is disabled until there is no marked text.
		 */
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

	/**
	 * Method that saves the specified String to clipboard. This method is used in
	 * cut and copy actions.
	 * 
	 * @param theString Specified String.
	 */
	private void setTextToClipboard(String theString) {
		StringSelection selection = new StringSelection(theString);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);
	}

	/**
	 * Action used to open existing document. If document user choose already exists
	 * as tab in this notepad than this action will just switch to that tab. If does
	 * not exists, then new tab will be created with content of chosen file.
	 */
	private final Action openExistingDocument = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}. Method that performs action that opens existing document. If
		 * document user choose already exists as tab in this notepad than this action
		 * will just switch to that tab. If does not exists, then new tab will be
		 * created with content of chosen file.
		 */
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

	/**
	 * Action used to show to user statistical info. This action prints information
	 * about the total number of characters, non-space characters and number of
	 * lines in current tab.
	 */
	private Action statisticalInfo = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Method that performs action that shows to user statistical info.This action
		 * prints information about the total number of characters, non-space characters
		 * and number of lines in current tab.
		 */
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

		/**
		 * Returns the number of new line symbols. This method is used to find out the
		 * number of lines in document. Simply, the number of lines is equal : number of
		 * new line character + 1.
		 * 
		 * @param text Document text.
		 * @return Number of new line symbols.
		 */
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

		/**
		 * Counts all non-whitespace characters. White space characters are : tab, new
		 * line and space.
		 * 
		 * @param text Document text.
		 * @return Number of non-whitespace characters.
		 */
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

	/**
	 * Implementation of WindowListener. This class is used to listen the closing
	 * event. If user attempts to close the program, this method will be called and
	 * appropriate action will be executed.
	 * 
	 * @author david
	 *
	 */
	private class MyWindowListener extends WindowAdapter {

		/**
		 * {@inheritDoc}
		 */
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

	/**
	 * Method that asks user to close the tab.
	 * 
	 * @param itr  Iterator user to iterate through all modified tabs.
	 * @param text Information text that appears to user.
	 * @return False if user click on cancel button, otherwise returns true.
	 */
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

	/**
	 * Implementation of MultipleDocumentListener. This class is used to inform this
	 * window when some document has changed, or document has been added or removed.
	 * 
	 * @author david
	 *
	 */
	private class MyMultipleDocumentListener implements MultipleDocumentListener {

		/**
		 * {@inheritDoc}
		 */
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

		/**
		 * {@inheritDoc}
		 */
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

		/**
		 * {@inheritDoc}
		 */
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

	/**
	 * Method invoked when running the program. This method creates new window.
	 * 
	 * @param args Arguments provided via command line. In this program they are not
	 *             used.
	 */
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
