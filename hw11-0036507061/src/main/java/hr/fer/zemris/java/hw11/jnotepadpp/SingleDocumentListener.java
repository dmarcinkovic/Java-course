package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface that represents one event listener. Implementation of this
 * interface will inform all interested listeners when any change occurs.
 * 
 * @author david
 *
 */
public interface SingleDocumentListener {

	/**
	 * This method is used to inform registered listeners that document's modified
	 * status is updated.
	 * 
	 * @param model {@link SingleDocumentModel}.
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * This method is used to inform registered listeners that document's file path
	 * is updated.
	 * 
	 * @param model {@link SingleDocumentModel}.
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
