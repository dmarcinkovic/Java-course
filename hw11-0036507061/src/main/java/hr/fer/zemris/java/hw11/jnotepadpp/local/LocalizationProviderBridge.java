package hr.fer.zemris.java.hw11.jnotepadpp.local;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	private boolean connected;
	private ILocalizationProvider parent;
	private ILocalizationListener listener; 
	private String language;

	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
		listener = new LocalizationListener();
	}
	
	private class LocalizationListener implements ILocalizationListener {

		@Override
		public void localizationChanged() {
			fire(); 
		}
	}

	public void connect() {
		if (!connected) {
			connected = true;
			parent.addLocalizationListener(listener);
		}
	}

	public void disconnect() {
		if (connected) {
			connected = false;
			parent.removeLocalizationListener(listener);
		}
	}

	@Override
	public String getString(String text) {
		return parent.getString(text);
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}
}
