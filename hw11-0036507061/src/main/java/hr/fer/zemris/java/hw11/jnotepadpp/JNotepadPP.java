package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

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
 * access actions via keyboard shortcuts. Program allows user to choose the
 * language in which hole GUI will be presented. Available languages are :
 * German, Croatian and English.
 * 
 * @author david
 *
 */
public class JNotepadPP extends JFrame { /// TODO ako je korisnik pokusava spreminiti vec postojeci file pitaj
											// ga da li to stvarno zeli

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
	 * Status bar placed at the bottom of the window.
	 */
	private JLabel statusBarLeft;

	/**
	 * Status bar placed at the bottom of the window.
	 */
	private JLabel statusBarRight;

	/**
	 * Stores information about the current date and time.
	 */
	private String date;

	/**
	 * {@link FormLocalizationProvider}.
	 */
	private FormLocalizationProvider flp;

	/**
	 * Constructor that initializes GUI and creates all necessary listeners.
	 */
	public JNotepadPP() throws HeadlessException {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(800, 600);

		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

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
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 0));

		statusBarLeft = new JLabel();
		statusBarRight = new JLabel();
		statusBarRight.setHorizontalAlignment(SwingConstants.RIGHT);

		statusBarLeft.setBorder(BorderFactory.createLineBorder(Color.black));
		statusBarRight.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.add(statusBarLeft);
		panel.add(statusBarRight);

		getContentPane().add(panel, BorderLayout.PAGE_END);
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
		configureInverCase();
		configureToLowercase();
		configureToUppercase();
		configureEnglishLanguage();
		configureGermanyLanguage();
		configureCroatiaLanguage();
	}

	/**
	 * Method used to configure exit application action. This method sets name for
	 * this action, also it sets accelerator key and mnemonic key. It sets one
	 * additional thing: description of the action. This description is shown when
	 * user holds the mouse for some time under the action menu of button on
	 * toolbar.
	 */
	private void configureExitApplication() {
		String translation = flp.getString("Exit");
		exitApplication.putValue(Action.NAME, translation);
		exitApplication.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		exitApplication.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);

		String descriptionTranslation = flp.getString("Exit_application");
		exitApplication.putValue(Action.SHORT_DESCRIPTION, descriptionTranslation);

		addLocalizationListener(exitApplication, "Exit", "Exit_application");
	}

	/**
	 * Method used to configure save document action. This method sets name for this
	 * action, also it sets accelerator key and mnemonic key. It sets one additional
	 * thing: description of the action. This description is shown when user holds
	 * the mouse for some time under the action menu of button on toolbar.
	 */
	private void configureSaveDocumentAction() {
		String translation = flp.getString("Save");
		saveDocument.putValue(Action.NAME, translation);
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		String descriptionTranslation = flp.getString("Save_file_to_disk");
		saveDocument.putValue(Action.SHORT_DESCRIPTION, descriptionTranslation);

		addLocalizationListener(saveDocument, "New", "Save_file_to_disk");
	}

	/**
	 * Method used to configure create new document action. This method sets name
	 * for this action, also it sets accelerator key and mnemonic key. It sets one
	 * additional thing: description of the action. This description is shown when
	 * user holds the mouse for some time under the action menu of button on
	 * toolbar.
	 */
	private void configureCreateNewDocument() {
		String translation = flp.getString("New");
		createNewDocument.putValue(Action.NAME, translation);
		createNewDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createNewDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

		String descriptionTranslation = flp.getString("Create_new_document");
		createNewDocument.putValue(Action.SHORT_DESCRIPTION, descriptionTranslation);

		addLocalizationListener(createNewDocument, "New", "Create_new_document");
	}

	/**
	 * Method used to create save as document action. This method sets name for this
	 * action, also it sets accelerator key and mnemonic key. It sets one additional
	 * thing: description of the action. This description is shown when user holds
	 * the mouse for some time under the action menu of button on toolbar.
	 */
	private void configureSaveAsDocument() {
		String translation = flp.getString("Save_as");
		saveAsDocument.putValue(Action.NAME, translation);
		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift A"));
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);

		String descriptionTranslation = flp.getString("Save_file_to_disk");
		saveAsDocument.putValue(Action.SHORT_DESCRIPTION, descriptionTranslation);

		addLocalizationListener(saveAsDocument, "Save_as", "Save_file_to_disk");
	}

	/**
	 * Method used to configure paste action. This method sets name for this action,
	 * also it sets accelerator key and mnemonic key. It sets one additional thing:
	 * description of the action. This description is shown when user holds the
	 * mouse for some time under the action menu of button on toolbar.
	 */
	private void configurePaste() {
		String translation = flp.getString("Paste");
		paste.putValue(Action.NAME, translation);
		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift V"));
		paste.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);

		String descriptionTranslation = flp.getString("Paste_text");
		paste.putValue(Action.SHORT_DESCRIPTION, descriptionTranslation);

		addLocalizationListener(paste, "Paste", "Paste_text");
	}

	/**
	 * Method used to configure copy action. This method sets name for this action,
	 * also it sets accelerator key and mnemonic key. It sets one additional thing:
	 * description of the action. This description is shown when user holds the
	 * mouse for some time under the action menu of button on toolbar.
	 */
	private void configureCopy() {
		String translation = flp.getString("Copy");
		copy.putValue(Action.NAME, translation);
		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift C"));
		copy.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);

		String descrptionTranslation = flp.getString("Copy_text");
		copy.putValue(Action.SHORT_DESCRIPTION, descrptionTranslation);

		addLocalizationListener(copy, "Copy", "Copy_text");
	}

	/**
	 * Method used to configure cut action. This method sets name for this action,
	 * also it sets accelerator key and mnemonic key. It sets one additional thing:
	 * description of the action. This description is shown when user holds the
	 * mouse for some time under the action menu of button on toolbar.
	 */
	private void configureCut() {
		String translation = flp.getString("Cut");
		cut.putValue(Action.NAME, translation);
		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift X"));
		cut.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);

		String descriptionTranslation = flp.getString("Cut_text._Delete_and_save_to_clipboard");
		cut.putValue(Action.SHORT_DESCRIPTION, descriptionTranslation);

		addLocalizationListener(cut, "Cut", "Cut_text._Delete_and_save_to_clipboard");
	}

	/**
	 * Configure statistical info action. This method sets name for this action,
	 * also it sets accelerator key and mnemonic key. It sets one additional thing:
	 * description of the action. This description is shown when user holds the
	 * mouse for some time under the action menu of button on toolbar.
	 */
	private void configureStatisticalInfo() {
		String translation = flp.getString("Info");
		statisticalInfo.putValue(Action.NAME, translation);
		statisticalInfo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		statisticalInfo.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);

		String descriptionTranslation = flp.getString("Show_statistical_info");
		statisticalInfo.putValue(Action.SHORT_DESCRIPTION, descriptionTranslation);

		addLocalizationListener(statisticalInfo, "Info", "Show_statistical_info");
	}

	/**
	 * Configure open existing document action. This method sets name for this
	 * action, also it sets accelerator key and mnemonic key. It sets one additional
	 * thing: description of the action. This description is shown when user holds
	 * the mouse for some time under the action menu of button on toolbar.
	 */
	private void configureOpen() {
		String translation = flp.getString("Open");
		openExistingDocument.putValue(Action.NAME, translation);
		openExistingDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openExistingDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

		String descriptionTranslation = flp.getString("Open_existing_document");
		openExistingDocument.putValue(Action.SHORT_DESCRIPTION, descriptionTranslation);

		addLocalizationListener(openExistingDocument, "Open", "Open_existing_document");
	}

	/**
	 * Configure close tab action. This method sets name for this action, also it
	 * sets accelerator key and mnemonic key. It sets one additional thing:
	 * description of the action. This description is shown when user holds the
	 * mouse for some time under the action menu of button on toolbar.
	 */
	private void configureClose() {
		String translation = flp.getString("Close");
		close.putValue(Action.NAME, translation);
		close.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		close.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);

		String descriptionTranslation = flp.getString("Close_current_tab");
		close.putValue(Action.SHORT_DESCRIPTION, descriptionTranslation);

		addLocalizationListener(close, "Close", "Close_current_tab");
	}

	/**
	 * Configure toUppercase action.This method sets name for this action, also it
	 * sets accelerator key and mnemonic key. It sets one additional thing:
	 * description of the action. This description is shown when user holds the
	 * mouse for some time under the action menu of button on toolbar.
	 */
	private void configureToUppercase() {
		String translation = flp.getString("To_Upper_Case");
		toUpperCase.putValue(Action.NAME, translation);
		toUpperCase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		toUpperCase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);

		String descriptionTranslation = flp.getString("Convert_selected_part_to_uppercase");
		toUpperCase.putValue(Action.SHORT_DESCRIPTION, descriptionTranslation);

		addLocalizationListener(toUpperCase, "To_Upper_Case", "Convert_selected_part_to_uppercase");
	}

	/**
	 * Configure toLowercase action. This method sets name for this action, also it
	 * sets accelerator key and mnemonic key. It sets one additional thing:
	 * description of the action. This description is shown when user holds the
	 * mouse for some time under the action menu of button on toolbar.
	 */
	private void configureToLowercase() {
		String translation = flp.getString("To_Lower_Case");
		toLowerCase.putValue(Action.NAME, translation);
		toLowerCase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		toLowerCase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);

		String descriptionTranslation = flp.getString("Convert_selected_part_to_lowercase");
		toLowerCase.putValue(Action.SHORT_DESCRIPTION, descriptionTranslation);

		addLocalizationListener(toLowerCase, "To_Lower_Case", "Convert_selected_part_to_lowercase");
	}

	/**
	 * Configure inverCase action. This method sets name for this action, also it
	 * sets accelerator key and mnemonic key. It sets one additional thing:
	 * description of the action. This description is shown when user holds the
	 * mouse for some time under the action menu of button on toolbar.
	 */
	private void configureInverCase() {
		String translation = flp.getString("Invert_Case");
		invertCaseAction.putValue(Action.NAME, translation);
		invertCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control G"));
		invertCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_G);

		String descriptionTranslation = flp.getString("Invert_casing_of_selected_part");
		invertCaseAction.putValue(Action.SHORT_DESCRIPTION, descriptionTranslation);

		addLocalizationListener(invertCaseAction, "Invert_Case", "Invert_casing_of_selected_part");
	}

	private void configureCroatiaLanguage() {
		String translation = flp.getString("Croatian");
		croatiaLanguage.putValue(Action.NAME, translation);
		croatiaLanguage.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control M"));
		croatiaLanguage.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_M);

		String descriptionTranslation = flp.getString("Change_language_to_croatian");
		croatiaLanguage.putValue(Action.SHORT_DESCRIPTION, descriptionTranslation);

		addLocalizationListener(croatiaLanguage, "Croatian", "Change_language_to_croatian");
	}

	private void configureEnglishLanguage() {
		String translation = flp.getString("English");
		englishLanguage.putValue(Action.NAME, translation);
		englishLanguage.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		englishLanguage.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

		String descriptionTranslation = flp.getString("Change_language_to_english");
		englishLanguage.putValue(Action.SHORT_DESCRIPTION, descriptionTranslation);

		addLocalizationListener(englishLanguage, "English", "Change_language_to_english");
	}

	private void configureGermanyLanguage() {
		String translation = flp.getString("Germany");
		germanyLanguage.putValue(Action.NAME, translation);
		germanyLanguage.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control B"));
		germanyLanguage.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_B);

		String descriptionTranslation = flp.getString("Change_language_to_germany");
		germanyLanguage.putValue(Action.SHORT_DESCRIPTION, descriptionTranslation);

		addLocalizationListener(germanyLanguage, "Germany", "Change_language_to_germany");
	}

	private void addLocalizationListener(Action action, String text, String description) {
		flp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				String translation = flp.getString(text);
				action.putValue(Action.NAME, translation);

				String descriptionTranslation = flp.getString(description);
				action.putValue(Action.SHORT_DESCRIPTION, descriptionTranslation);
			}
		});
	}

	/**
	 * Method that creates menu for each action.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		disableActions();

		JMenu file = new JMenu(flp.getString("File"));
		menuBar.add(file);
		addLocalizationListenerForMenu(file, "File");

		addFileMenuItems(file);

		JMenu edit = new JMenu(flp.getString("Edit"));
		addLocalizationListenerForMenu(edit, "Edit");

		menuBar.add(edit);

		addEditFileMenuItems(edit);

		JMenu tools = new JMenu(flp.getString("Tools"));
		addLocalizationListenerForMenu(tools, "Tools");

		JMenu changeCase = new JMenu(flp.getString("Change_case"));
		addLocalizationListenerForMenu(changeCase, "Change_case");

		tools.add(changeCase);

		addChangeCaseMenuItems(changeCase);

		JMenu sort = new JMenu(flp.getString("Sort"));
		tools.add(sort);
		addLocalizationListenerForMenu(sort, "Sort");

		JMenu languages = new JMenu(flp.getString("Languages"));
		addLocalizationListenerForMenu(languages, "Languages");

		addLanguageMenuItem(languages);

		menuBar.add(languages);
		menuBar.add(tools);

		setJMenuBar(menuBar);
	}

	private void addLocalizationListenerForMenu(JMenu menu, String text) {
		flp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				String translation = flp.getString(text);
				menu.setText(translation);
			}
		});
	}

	private void addLanguageMenuItem(JMenu language) {
		language.add(new JMenuItem(croatiaLanguage));
		language.add(new JMenuItem(germanyLanguage));
		language.add(new JMenuItem(englishLanguage));
	}

	private void addSortMenuItems(JMenu sort) {

	}

	/**
	 * Disables all actions.
	 */
	private void disableActions() {
		paste.setEnabled(false);
		copy.setEnabled(false);
		cut.setEnabled(false);

		statisticalInfo.setEnabled(false);
		saveDocument.setEnabled(false);
		saveAsDocument.setEnabled(false);
		close.setEnabled(false);

		invertCaseAction.setEnabled(false);
		toLowerCase.setEnabled(false);
		toUpperCase.setEnabled(false);
	}

	/**
	 * Method to add items to changeCase menu.
	 * 
	 * @param changeCase changeCase menu.
	 */
	private void addChangeCaseMenuItems(JMenu changeCase) {
		changeCase.add(new JMenuItem(toUpperCase));
		changeCase.add(new JMenuItem(toLowerCase));
		changeCase.add(new JMenuItem(invertCaseAction));
	}

	/**
	 * Method to add items to edit menu.
	 * 
	 * @param edit edit menu.
	 */
	private void addEditFileMenuItems(JMenu edit) {
		edit.add(new JMenuItem(paste));
		edit.add(new JMenuItem(copy));
		edit.add(new JMenuItem(cut));
	}

	/**
	 * Method to add items to file menu.
	 * 
	 * @param file file menu.
	 */
	private void addFileMenuItems(JMenu file) {
		file.add(new JMenuItem(statisticalInfo));
		file.add(new JMenuItem(saveDocument));
		file.add(new JMenuItem(saveAsDocument));
		file.add(new JMenuItem(close));
		file.add(new JMenuItem(createNewDocument));
		file.add(new JMenuItem(exitApplication));
		file.add(new JMenuItem(openExistingDocument));
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
	};

	private final Action croatiaLanguage = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
		}
	};

	private final Action germanyLanguage = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");

		}
	};

	private final Action englishLanguage = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		}
	};

	/**
	 * Action used to convert selected part of the text to upper case. For example,
	 * if selected String is abCD then after this action is executed this part will
	 * be ABCD.
	 */
	private final Action toUpperCase = new AbstractAction() {

		/**
		 * Default serial vesion UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}. Method that performs action that converts selected part of
		 * text to upper case. For example, if selected String is abCD then after this
		 * action is executed this part will be ABCD.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			getChangeCase(0);
		}
	};

	/**
	 * Action used to convert selected part of the text to lower case. For example,
	 * if String is abCD then after this action is executes this String will be
	 * abcd.
	 */
	private final Action toLowerCase = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}. Method that performs action that converts selected part of
		 * text to lower case. For example, if String is abCD then after this action is
		 * executes this String will be abcd.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			getChangeCase(1);
		}
	};

	/**
	 * Action used to invert casing of all character selected. For example, if
	 * String is abCD then after this action is executed this String will be ABcd.
	 */
	private final Action invertCaseAction = new AbstractAction() {

		/**
		 * Serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Action used to invert casing of all character selected. For example, if
		 * String is abCD then after this action is executed this String will be ABcd.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			getChangeCase(2);
		}
	};

	/**
	 * Change casing depending on action number. If action number is 0 then change
	 * all characters to upper case. If action is 1 then change all characters to
	 * lower case. If action is 2 then invert all character casing.
	 * 
	 * @param action Action.
	 */
	private void getChangeCase(int action) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		Document doc = editor.getDocument();
		Caret caret = editor.getCaret();

		int start = Math.min(caret.getDot(), caret.getMark());
		int len = Math.abs(caret.getDot() - caret.getMark());

		if (len == 0) {
			return;
		}

		String text = null;
		try {
			text = doc.getText(start, len);
			text = toggleCase(text, action);
			doc.remove(start, len);
			doc.insertString(start, text, null);
		} catch (BadLocationException e1) {
		}
	}

	/**
	 * Toggles case. If action number is 0 then change all characters to upper case.
	 * If action is 1 then change all characters to lower case. If action is 2 then
	 * invert all character casing.
	 */
	private String toggleCase(String text, int action) { /// TODO must do locale casing.
		switch (action) {
		case 0:
			return text.toUpperCase();
		case 1:
			return text.toLowerCase();
		}

		char[] data = text.toCharArray();

		for (int i = 0; i < data.length; i++) {
			if (Character.isUpperCase(data[i])) {
				data[i] = Character.toLowerCase(data[i]);
			} else if (Character.isLowerCase(data[i])) {
				data[i] = Character.toUpperCase(data[i]);
			}
		}

		return new String(data);
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
	 * Returns the line number of current position of dot.
	 * 
	 * @param text     Text presented in current tab.
	 * @param dotIndex Current position of dot.
	 * @return The column number of current position of dot.
	 */
	private int getLineNumber(String text, int dotIndex) {
		return getNumberOfNewLineSymbols(text.substring(0, dotIndex));
	}

	/**
	 * Returns the column number of current position of dot.
	 * 
	 * @param text     Text presented in current tab.
	 * @param dotIndex Current position of dot.
	 * @return The column number of current position of dot.
	 */
	private int getColumnNumber(String text, int dotIndex) {
		int index = dotIndex - 1;
		char[] data = text.toCharArray();

		while (index >= 0 && data[index] != '\n') {
			index--;
		}
		return dotIndex - index;
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
				toUpperCase.setEnabled(true);
				toLowerCase.setEnabled(true);
				invertCaseAction.setEnabled(true);
			} else {
				copy.setEnabled(false);
				cut.setEnabled(false);
				toUpperCase.setEnabled(false);
				toLowerCase.setEnabled(false);
				invertCaseAction.setEnabled(false);
			}

			if (currentModel.getTextComponent().isFocusOwner()) {
				paste.setEnabled(true);
			} else {
				paste.setEnabled(false);
			}

			if (currentModel != previousModel) {
				date = getDate();
			}

			updateStatusBar(currentModel, caret);
		}

		/**
		 * Returns the current date and time in format : yyyy/MM/dd HH:mm:ss. For
		 * example: 2019/05/19 13:24:23
		 * 
		 * @return Current date as String.
		 */
		private String getDate() {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();

			return dateFormat.format(date);
		}

		/**
		 * Method to update status bar. On status bar is presented total length of text,
		 * current dot line, current selection and current dot column.
		 * 
		 * @param currentModel Current tab.
		 * @param caret        Used to get the mark and dot position.
		 */
		private void updateStatusBar(SingleDocumentModel currentModel, Caret caret) {
			String text = currentModel.getTextComponent().getText();

			int length = text.length();
			int line = getLineNumber(text, caret.getDot());
			int column = getColumnNumber(text, caret.getDot());
			int selection = Math.abs(caret.getDot() - caret.getMark());

			StringBuilder sb = new StringBuilder();
			sb.append("<html><pre>").append("length:").append(length).append("\t").append("Ln:").append(line + 1)
					.append(" Col:").append(column).append(" Sel:").append(selection).append("</pre></html>");

			statusBarLeft.setText(sb.toString());
			statusBarRight.setText(date + " ");
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
				date = getDate();
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
				LocalizationProvider.getInstance().setLanguage("en");
				new JNotepadPP().setVisible(true);
			});
		} catch (InvocationTargetException | InterruptedException e) {
			System.out.println("Error");
		}
	}
}
