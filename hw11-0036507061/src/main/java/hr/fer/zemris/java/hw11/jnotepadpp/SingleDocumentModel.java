package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Represents a model of single document , having information about file path
 * from which document was loaded, document modification status and reference to
 * Swing component which is used for editing.
 * 
 * @author david
 *
 */
public interface SingleDocumentModel {

	/**
	 * Returns text that is stored in Swing component which is used for editing.
	 * 
	 * @return Text that is stored in Swing component which is used for editing.
	 */
	JTextArea getTextComponent();

	/**
	 * Returns file path from which document was loaded.
	 * 
	 * @return File path from which document was loaded.
	 */
	Path getFilePath();

	/**
	 * Sets file path from which document was loaded.
	 * 
	 * @param path File path from which document was loaded.
	 */
	void setFilePath(Path path);

	/**
	 * Returns true if document is modified, otherwise returns false.
	 * 
	 * @return True is document is modified, otherwise returns false.
	 */
	boolean isModified();

	/**
	 * Sets modified to new value.
	 * 
	 * @param modified New value of modified.
	 */
	void setModified(boolean modified);

	/**
	 * Adds listener interested to this object changes.
	 * 
	 * @param l Listener to be added.
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Removes listener that is not interested any more.
	 * 
	 * @param l Listener to be removed.
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}