package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

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
	 * Constructor.
	 */
	public DefaultMultipleDocumentModel() {
		listeners = new ArrayList<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return new MyIterator(list);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel model = new DefaultSingleDocumentModel(null, "unnamed");

		list.add(model);

		return model;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel getCurrentDocument() {
		return current;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if path is null.
	 */
	@Override
	public SingleDocumentModel loadDocument(Path path) { // TODO change this.
		Objects.requireNonNull(path);

		SingleDocumentModel model = new DefaultSingleDocumentModel(path, "unnamed");

		list.add(model);

		return model;
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

		if (alreadyExistsModel(openedFilePath)) { // TODO print error to user.

		}

		editor = model.getTextComponent();

		saveDocument.putValue(Action.NAME, "Save");
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.putValue(Action.SHORT_DESCRIPTION, "Save file to disk");
	}

	/**
	 * Checks if specified file is already opened.
	 * 
	 * @param path File to check if is already opened.
	 * @return True if specified file is already opened.
	 */
	private boolean alreadyExistsModel(Path path) {
		for (SingleDocumentModel model : list) {
			if (model.getFilePath().normalize().toString().equals(path.normalize().toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void closeDocument(SingleDocumentModel model) {
		boolean removed = list.remove(model);

		if (removed && list.size() > 0) {
			current = list.get(0);
		} else if (removed) {
			current = null;
		}
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
	private static class MyIterator implements Iterator<SingleDocumentModel> {
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
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Save file");

				if (jfc.showSaveDialog(DefaultMultipleDocumentModel.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this, "Ništa nije snimljeno",
							"Infomacija", JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				openedFilePath = jfc.getSelectedFile().toPath();
			}

			try {
				Files.writeString(openedFilePath, editor.getText());
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this,
						"Dogodila se pogreška pri snimanju datoteke", "Greška", JOptionPane.ERROR_MESSAGE);
				return;
			}

			JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this, "Dokument je uredno spremljen.",
					"Informacija", JOptionPane.INFORMATION_MESSAGE);
		}
	};
}
