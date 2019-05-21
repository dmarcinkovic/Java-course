package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Implementation of {@link SingleDocumentModel}.
 * 
 * @author david
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/**
	 * Path of the document that this class represents.
	 */
	private Path file;

	/**
	 * Text area in which user can type text.
	 */
	private JTextArea textArea;

	/**
	 * Flag that tells us if this document is modified or not.
	 */
	private boolean modified;

	/**
	 * Stores all listeners that are interested in listening this object.
	 */
	private List<SingleDocumentListener> listeners;

	/**
	 * Constructor to initialize path of the document and text that will appear at
	 * text area,
	 * 
	 * @param file Path of the document that this class represents.
	 * @param text Text that will appear at text area.
	 */
	public DefaultSingleDocumentModel(Path file, String text) {
		this.file = file;
		textArea = new JTextArea(text);
		listeners = new ArrayList<>();

		modified = true;
		textArea.getDocument().addDocumentListener(new MyDocumentListener());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Path getFilePath() {
		return file;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFilePath(Path path) {
		Objects.requireNonNull(path);
		this.file = path;
		informListenerFilePathChanged();
	}

	/**
	 * Method to inform all registered listener than path has changed.
	 */
	private void informListenerFilePathChanged() {
		for (SingleDocumentListener l : listeners) {
			l.documentFilePathUpdated(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isModified() {
		return modified;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		informListenersModifyStatus();
	}

	/**
	 * Method that informs all registered listeners that this document model is
	 * modified.
	 */
	private void informListenersModifyStatus() {
		for (SingleDocumentListener l : listeners) {
			l.documentModifyStatusUpdated(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getFilePath() == null ? "(unname)" : getFilePath().getFileName().toString();
	}

	/**
	 * Returns full path name or "(unnamed)" if path is not specified yet.
	 * 
	 * @return Full path name or "(unnamed)" if path is not specified yet.
	 */
	public String getPathName() {
		return getFilePath() == null ? "(unnamed)" : getFilePath().toAbsolutePath().toString();
	}

	/**
	 * Implementation of {@link DocumentListener}. In methods insertUpdate and
	 * removeUpdate modified flag is updated.
	 * 
	 * @author david
	 *
	 */
	private class MyDocumentListener implements DocumentListener {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void insertUpdate(DocumentEvent e) {
			setModified(true);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void removeUpdate(DocumentEvent e) {
			setModified(true);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void changedUpdate(DocumentEvent e) {
		}
	}

}
