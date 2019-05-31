package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface that is used to provide the localization for hole GUI. All
 * components will be translated when button for changing the language is
 * pressed.
 * 
 * @author David
 *
 */
public interface ILocalizationProvider {
	/**
	 * Adds new localization listener. For example, we can register Swing components
	 * to listen to this changes, and update text presented on them accordingly.
	 * 
	 * @param listener Listener to be registered.
	 */
	void addLocalizationListener(ILocalizationListener listener);

	/**
	 * Removes listener that has been registered to localization changes.
	 * @param listener Listener to be removed.
	 */
	void removeLocalizationListener(ILocalizationListener listener);

	/**
	 * Returns translation for given text.
	 * @param name Text to be translated.
	 * @return Translated string.
	 */
	String getString(String name);

	/**
	 * Returns current language.  
	 * @return current language.
	 */
	String getCurrentLanguage();
}
