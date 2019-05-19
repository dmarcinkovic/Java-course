package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface that represents one event listener. Implementation of this
 * interface will inform all interested listeners when any change occurs.
 * 
 * @author david
 *
 */
public interface MultipleDocumentListener {
	/**
	 * This method is used to inform listeners that current document is been
	 * changed.
	 * 
	 * @param previousModel The previous model.
	 * @param currentModel  The model which user switched to.
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * This method is used to inform listeners that new document is been added.
	 * 
	 * @param model {@link SingleDocumentModel}.
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * This method is used to inform listener that new document is been added.
	 * 
	 * @param model {@link SingleDocumentModel}.
	 */
	void documentRemoved(SingleDocumentModel model);
}
