package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
		this.file = path;
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
			modified = true;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void removeUpdate(DocumentEvent e) {
			modified = true;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void changedUpdate(DocumentEvent e) {
		}
	}

}
