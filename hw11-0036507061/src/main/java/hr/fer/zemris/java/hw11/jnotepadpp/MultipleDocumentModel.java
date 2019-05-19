package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Represents a model capable of holding zero, one or more documents, where each
 * document and having a concept of current document - the one which is shown to
 * the user and on which user works.
 * 
 * @author david
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	/**
	 * Creates new document and returns it.
	 * 
	 * @return Created document.
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Returns current document. Current document is the one which is shown to the
	 * user and on which user works.
	 * 
	 * @return Current document.
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Loads new document.
	 * 
	 * @param path Path from which the user wants to load new document.
	 * @return Loaded document.
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves current document which specified path. If path specified is null than
	 * this document will be saved at model's current file path.
	 * 
	 * @param model   Model that represents document that has to be saved.
	 * @param newPath Specified path.
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Close document from specified model.
	 * 
	 * @param model Model that stores document to be closed.
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Adds new listener interested in this object's changes.
	 * 
	 * @param l Listener to be added.
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Removes listener that is not interested any more.
	 * 
	 * @param l Listener to be added.
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Returns the number of documents that this model stores.
	 * 
	 * @return Number of documents that this model stores.
	 */
	int getNumberOfDocuments();

	/**
	 * Returns document that is stored at specified position.
	 * 
	 * @param index Specified position.
	 * @return Model that represents document that is stored at specified position.
	 */
	SingleDocumentModel getDocument(int index);
}
