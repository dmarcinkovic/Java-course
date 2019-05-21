package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Implementation of {@link MultipleDocumentModel}.
 * 
 * @author david
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Stores all {@link SingleDocumentModel} that are registered to this Document
	 * model.
	 */
	private List<SingleDocumentModel> list;

	/**
	 * Current {@link SingleDocumentModel}.
	 */
	private SingleDocumentModel current;

	/**
	 * Stores all listeners that are interested in listening this object.
	 */
	private List<MultipleDocumentListener> listeners;

	/**
	 * Current model text area.
	 */
	private JTextArea editor;

	/**
	 * Path of current model.
	 */
	private Path openedFilePath;

	/**
	 * Image icon for modified document.
	 */
	private ImageIcon unsaved;

	/**
	 * Image icon for unmodified document.
	 */
	private ImageIcon saved;

	/**
	 * Constructor.
	 */
	public DefaultMultipleDocumentModel() {
		listeners = new ArrayList<>();
		list = new ArrayList<>();

		current = null;

		saved = loadImageIcon("icons/Saved.png");
		unsaved = loadImageIcon("icons/Unsaved.png");

		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				int index = sourceTabbedPane.getSelectedIndex();

				if (index >= list.size() || index < 0) {
					return;
				}

				SingleDocumentModel previous = current;
				current = list.get(index);

				informListenersDocumentChanged(previous, current);
			}
		};

		this.addChangeListener(changeListener);
	}

	/**
	 * Method to load icons from specified path. Those icons are used to show user
	 * modified status. If red cuboid is presented that means that current document
	 * (i.e. current tab presented to user) is modified. On the other hand, if
	 * current document is not modified, green cuboid will be presented in left
	 * corner of tab.
	 * 
	 * @param path Path from which the icons are loaded.
	 * @return Image icon that is loaded from given path.
	 */
	private ImageIcon loadImageIcon(String path) {
		try (InputStream is = this.getClass().getResourceAsStream(path)) {

			if (is == null) {
				JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this,
						"Cannot open icons for showing modified status.", "Error", JOptionPane.ERROR_MESSAGE);
			}

			byte[] bytes = null;
			try {
				bytes = is.readAllBytes();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this,
						"Cannot open icons for showing modified status.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			return new ImageIcon(bytes);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this,
					"Cannot open icons for showing modified status.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	/**
	 * This method informs all registered listeners that documents has changed.
	 * 
	 * @param previous Previous tab.
	 * @param current  Current tab.
	 */
	private void informListenersDocumentChanged(SingleDocumentModel previous, SingleDocumentModel current) {
		for (MultipleDocumentListener l : listeners) {
			l.currentDocumentChanged(previous, current);
		}
	}

	/**
	 * This method informs all registered listeners that new document has been added
	 * to this model.
	 * 
	 * @param model New document that is added to this model.
	 */
	private void informListenersAddedDocument(SingleDocumentModel model) {
		for (MultipleDocumentListener l : listeners) {
			l.documentAdded(model);
		}
	}

	/**
	 * This method informs all registered listeners that one document has been
	 * removed from this model.
	 * 
	 * @param model Removed document.
	 */
	private void informListenersRemovedDocument(SingleDocumentModel model) {
		for (MultipleDocumentListener l : listeners) {
			l.documentRemoved(model);
		}
	}

	/**
	 * Method to add new document to this model and registers itself to
	 * SingeDocumentListener.
	 * 
	 * @param path Path from which the document is added.
	 * @return Newly added document.
	 */
	private SingleDocumentModel addNewDocument(Path path) {
		SingleDocumentModel tab = new DefaultSingleDocumentModel(path, null);

		addCaretListener(tab);

		String tip = ((DefaultSingleDocumentModel) tab).getPathName();
		this.insertTab(tab.toString(), unsaved, new JScrollPane(tab.getTextComponent()), tip, list.size());

		list.add(tab);

		switchTab(current);
		tab.addSingleDocumentListener(new MySingleDocumentListener());

		return tab;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return new MyIterator(list);
	}

	/**
	 * Create new caret listener for every document presented in this model. This
	 * method is used to keep track when cut and copy actions have to be enabled or
	 * disabled. If there is no selected area (i.e. dot and mark are at same
	 * position) copy and cut actions have to be disable, otherwise they have to be
	 * enabled.
	 * 
	 * @param model Model to register caret listener.
	 */
	private void addCaretListener(SingleDocumentModel model) {
		model.getTextComponent().getCaret().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				informListenersDocumentChanged(model, model);
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel model = addNewDocument(null);

		current = model;

		informListenersAddedDocument(current);

		switchTab(current);
		return model;
	}

	/**
	 * Sets opened file path.
	 * 
	 * @param openedFilePath New openedFilePath.
	 */
	public void setOpenedFilePath(Path openedFilePath) {
		this.openedFilePath = openedFilePath;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel getCurrentDocument() {
		return current;
	}

	/**
	 * Method that returns text from file.
	 * 
	 * @param path Path of the file.
	 * @return Text from file.
	 */
	private String getText(Path path) {
		String text = null;
		try {
			text = Files.readString(path);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this, "Cannot load document.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return text;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if path is null.
	 */
	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path);

		SingleDocumentModel previous = current;

		String text = getText(path);
		if (text == null) {
			return null;
		}

		SingleDocumentModel model = alreadyExistsModel(path);

		if (model != null) {
			switchTab(model);

			informListenersDocumentChanged(previous, current);

			return model;
		} else {
			model = new DefaultSingleDocumentModel(path, text);
			model.setModified(false);
		}

		addCaretListener(model);

		current = model;

		String tip = ((DefaultSingleDocumentModel) model).getPathName();
		this.insertTab(model.toString(), saved, new JScrollPane(model.getTextComponent()), tip, list.size());

		list.add(model);
		model.addSingleDocumentListener(new MySingleDocumentListener());

		informListenersAddedDocument(current);
		informListenersDocumentChanged(previous, current);

		switchTab(current);

		return model;
	}

	/**
	 * Method that switches tabs.
	 * 
	 * @param model Model of new tab.
	 */
	private void switchTab(SingleDocumentModel model) {
		int index = list.indexOf(model);
		this.setSelectedIndex(index);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException is model is null.
	 */
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		Objects.requireNonNull(model);

		if (newPath == null) {
			openedFilePath = model.getFilePath();
		} else {
			openedFilePath = newPath;
		}

		editor = model.getTextComponent();

		saveDocument.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Save"));

		if (openedFilePath != null) {
			current.setFilePath(openedFilePath);
			current.setModified(false);

			int index = list.indexOf(current);
			setToolTipTextAt(index, openedFilePath.toString());

			informListenersDocumentChanged(current, current);
		}
	}

	/**
	 * Method used to save document. The difference from save document method is
	 * that this method allows the user to choose the filename whether this file
	 * already have name or not.
	 * 
	 * @param model Model of document.
	 */
	public void saveAsDocument(SingleDocumentModel model) {
		Objects.requireNonNull(model);

		editor = model.getTextComponent();

		saveAsDocument.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Save as..."));

		if (openedFilePath == null) {
			return;
		}

		current.setFilePath(openedFilePath);
		current.setModified(false);

		informListenersDocumentChanged(current, current);

		int index = list.indexOf(current);
		setToolTipTextAt(index, openedFilePath.toString());
	}

	/**
	 * Checks if specified file is already opened.
	 * 
	 * @param path File to check if is already opened.
	 * @return True if specified file is already opened.
	 */
	private SingleDocumentModel alreadyExistsModel(Path path) {
		for (SingleDocumentModel model : list) {
			if (Objects.equals(path, model.getFilePath())) {
				return model;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = list.indexOf(model);

		this.remove(index);
		boolean removed = list.remove(model);

		if (removed && list.size() > 0) {
			current = list.get(0);
		} else if (removed) {
			current = null;
		} else {
			return;
		}

		switchTab(current);
		informListenersRemovedDocument(model);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getNumberOfDocuments() {
		return list.size();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IndexOutOfBoundsException if index is less than zero, or greater than
	 *                                   number of documents.
	 */
	@Override
	public SingleDocumentModel getDocument(int index) {
		if (index < 0 || index >= list.size()) {
			throw new IndexOutOfBoundsException();
		}
		return list.get(index);
	}

	/**
	 * Iterator for iterating through this {@link DefaultMultipleDocumentModel}.
	 * 
	 * @author david
	 *
	 */
	private class MyIterator implements Iterator<SingleDocumentModel> {
		/**
		 * List of all {@link SingleDocumentModel} documents.
		 */
		private List<SingleDocumentModel> list;

		/**
		 * Current index in list.
		 */
		private int index = 0;

		/**
		 * Initialize list.
		 * 
		 * @param list List.
		 */
		public MyIterator(List<SingleDocumentModel> list) {
			this.list = list;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return index < list.size();
		}

		@Override
		public void remove() {
			index--;
			closeDocument(list.get(index));
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public SingleDocumentModel next() {
			index++;
			return list.get(index - 1);
		}
	}

	/**
	 * Action to save the document. If document is already saved once, document will
	 * be saved without asking the user, otherwise it will create new window in
	 * which he can choose the file path he wants.
	 */
	private final Action saveDocument = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc} When user clicks on save button or perform keyboard shortcut
		 * for saving the document, this method is invoked. If document has not been
		 * already saved, this method will offer to user new window in which it can type
		 * path to that document, otherwise it will save the document without asking the
		 * user.
		 * 
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (openedFilePath == null) {
				saveAsDocument.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Save"));
			}

			// User pressed cancel.
			if (openedFilePath == null) {
				return;
			}

			try {
				Files.writeString(openedFilePath, editor.getText());
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this,
						"An error occured while saving document.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this, "Document is saved successfully.",
					"Information", JOptionPane.INFORMATION_MESSAGE);
		}
	};

	/**
	 * Action to save document. The difference from save document action is that
	 * this action allows the user to choose the filename whether this file already
	 * have name or not.
	 */
	private final Action saveAsDocument = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}. Open file chooser and allow user to choose the file name. If
		 * file already exists, warn user. If file is already presented as tab in this
		 * DefaultMultipleDocumentModel do not save that document, but abort this
		 * action.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save file");

			if (jfc.showSaveDialog(DefaultMultipleDocumentModel.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this, "Nothing is saved.", "Information",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			openedFilePath = jfc.getSelectedFile().toPath();

			if (alreadyExistsModel(openedFilePath) != null && model != current) {
				JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this, "Tab already exists", "Warning",
						JOptionPane.WARNING_MESSAGE);
				openedFilePath = null;
				return;
			} else if (Files.exists(openedFilePath)) {
				JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this, "File already exists", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	};

	/**
	 * This class is implementation of SingleDocumentListener. It is used to get
	 * information about changes occured in each document that this model constis
	 * of.
	 * 
	 * @author david
	 *
	 */
	private class MySingleDocumentListener implements SingleDocumentListener {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			int index = list.indexOf(model);

			if (model.isModified()) {
				setIconAt(index, unsaved);
			} else {
				setIconAt(index, saved);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			int index = list.indexOf(current);

			setTitleAt(index, openedFilePath.getFileName().toString());
		}

	}
}
