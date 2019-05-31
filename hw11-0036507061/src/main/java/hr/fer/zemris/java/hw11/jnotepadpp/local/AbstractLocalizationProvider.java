package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract localization provider. This is an abstract class that implements
 * ILocalizationProvider. It does not implement getString method. That will do
 * classes inherited from this class.
 * 
 * @author David
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	/**
	 * List of all registered listeners.
	 */
	private List<ILocalizationListener> listeners;

	/**
	 * Constructor used  to initialize listeners list.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}

	/**
	 * Method used to inform all registered listeners that change has occured.
	 */
	public void fire() {
		for (ILocalizationListener listener : listeners) {
			listener.localizationChanged();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}
}
