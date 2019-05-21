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

	private void informListenersDocumentChanged(SingleDocumentModel previous, SingleDocumentModel current) {
		for (MultipleDocumentListener l : listeners) {
			l.currentDocumentChanged(previous, current);
		}
	}

	private void informListenersAddedDocument(SingleDocumentModel model) {
		for (MultipleDocumentListener l : listeners) {
			l.documentAdded(model);
		}
	}

	private void informListenersRemovedDocument(SingleDocumentModel model) {
		for (MultipleDocumentListener l : listeners) {
			l.documentRemoved(model);
		}
	}

	private SingleDocumentModel addNewDocument(Path path) {
		SingleDocumentModel tab = new DefaultSingleDocumentModel(path, null);

		addCaretListener(tab);

		String tip = ((DefaultSingleDocumentModel)tab).getPathName();
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

			informListenersAddedDocument(current);
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

	private final Action saveAsDocument = new AbstractAction() {

		/**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

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

	private class MySingleDocumentListener implements SingleDocumentListener {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			int index = list.indexOf(model);

			if (model.isModified()) {
				setIconAt(index, unsaved);
			} else {
				setIconAt(index, saved);
			}
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			int index = list.indexOf(current);

			setTitleAt(index, openedFilePath.getFileName().toString());
		}

	}
}
