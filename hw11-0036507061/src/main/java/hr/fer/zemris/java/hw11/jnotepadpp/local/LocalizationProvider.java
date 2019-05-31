package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class that is used to provide translation for all components presented in GUI.
 * @author David
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	
	/**
	 * Reference to class that supplies translation.
	 */
	private ResourceBundle bundle;
	
	/**
	 * Instance to this class.  
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();
	
	/**
	 * Current language.
	 */
	private String language;
	
	/**
	 * Private constructor, so that nobody outside can create instance of this class.
	 */
	private LocalizationProvider() {
	}

	/**
	 * Returns instance of this class.
	 * @return Instance of this class.
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	/**
	 * Sets language.
	 * @param language new Language
	 */
	public void setLanguage(String language) {
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale); 
		fire();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getString(String name) {
		return bundle.getString(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCurrentLanguage() {
		return language;
	}

}