package hr.fer.zemris.java.hw07.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents Subject in Observer model.
 * 
 * @author david
 *
 */
public class IntegerStorage {
	/**
	 * Value that is printed to standard output.
	 */
	private int value;

	/**
	 * List of all observers.
	 */
	private List<IntegerStorageObserver> observers; // use ArrayList here!!!

	/**
	 * Initializes value and observers.
	 * 
	 * @param initialValue Initial value.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new ArrayList<>();
	}

	/**
	 * Method to add observer to list of observers only if is not already presented
	 * in that list.
	 * 
	 * @param observer Observer to be added to list.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * Method to remove observer from list.
	 * 
	 * @param observer Observer to be removed from list.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
	}

	/**
	 * Method to clear all observers from list.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Method to return value.
	 * 
	 * @return value.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets new value.
	 * 
	 * @param value new value.
	 */
	public void setValue(int value) {

		// Only if new value is different than the current value:
		if (this.value != value) {

			// Update current value
			this.value = value;

			List<IntegerStorageObserver> copy = new ArrayList<>(observers);

			// Notify all registered observers
			if (copy != null) {
				for (IntegerStorageObserver observer : copy) {
					observer.valueChanged(this);
				}
			}
		}
	}
}