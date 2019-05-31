package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface that is used to listen the localization changes.
 * 
 * @author David
 *
 */
public interface ILocalizationListener {
	
	/**
	 * Method used to inform all listeners that user changed the language. 
	 */
	void localizationChanged();
}
