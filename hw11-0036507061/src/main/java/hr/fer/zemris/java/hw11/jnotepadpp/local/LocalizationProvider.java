package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {
	private ResourceBundle bundle;
	private static final LocalizationProvider instance = new LocalizationProvider();
	
	private LocalizationProvider() {
	}

	public static LocalizationProvider getInstance() {
		return instance;
	}

	public void setLanguage(String language) {
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale); 
		fire();
	}

	@Override
	public String getString(String name) {
		return bundle.getString(name);
	}

	@Override
	public String getCurrentLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

}