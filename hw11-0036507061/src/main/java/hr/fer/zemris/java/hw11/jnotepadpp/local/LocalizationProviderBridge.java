package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Localization provider bridge.
 * 
 * @author David
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * Flag that is used to check
	 */
	private boolean connected;

	/**
	 * Localization provider.
	 */
	private ILocalizationProvider parent;

	/**
	 * Listener that is used to listen the changes of language.
	 */
	private ILocalizationListener listener;

	/**
	 * Current language.
	 */
	private String language;

	/**
	 * Constructor to initialize LocalizationProvider.
	 * 
	 * @param parent LocalizationProvider.
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
		listener = new LocalizationListener();
	}

	/**
	 * Implementation of LocalizationListeners. This class is used to inform all
	 * listeners when user changes language in menu bar.
	 * 
	 * @author David
	 *
	 */
	private class LocalizationListener implements ILocalizationListener {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void localizationChanged() {
			fire();
		}
	}

	/**
	 * Connects provider.
	 */
	public void connect() {
		if (!connected) {
			connected = true;
			parent.addLocalizationListener(listener);
		}
	}

	/**
	 * Disconnects provider.
	 */
	public void disconnect() {
		if (connected) {
			connected = false;
			parent.removeLocalizationListener(listener);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getString(String text) {
		return parent.getString(text);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCurrentLanguage() {
		return language;
	}
}
