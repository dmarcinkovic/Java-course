package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Implementation of ListModel. This class is used to generate prime numbers.
 * 
 * @author david
 *
 */
public class PrimListModel implements ListModel<Long> {
	/**
	 * Stores prime numbers.
	 */
	private List<Long> data;

	/**
	 * Stores all listeners that have subscribed.
	 */
	private List<ListDataListener> listeners;

	/**
	 * Initialize list with number 1.
	 */
	public PrimListModel() {
		data = new ArrayList<>();
		listeners = new ArrayList<>();

		data.add(1L);
	}

	/**
	 * Method generates next prime number and informs all observers about that.
	 */
	public void next() {
		long nextPrime = getNextPrime();

		int position = data.size();
		data.add(nextPrime);

		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, position, position);
		for (ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
	}

	/**
	 * Returns next prime number.
	 * 
	 * @return Next prime number.
	 */
	private long getNextPrime() {
		long start = data.get(data.size() - 1);

		do {
			start++;
		} while (!isPrime(start));

		return start;
	}

	/**
	 * Checks if given number is prime or not.
	 * 
	 * @param number Number to check for.
	 * @return True if provided number is true, otherwise returns false.
	 */
	private boolean isPrime(long number) {
		for (int i = 2, n = (int) Math.sqrt(number); i <= n; i++) {
			if (number % i == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSize() {
		return data.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long getElementAt(int index) {
		return data.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addListDataListener(ListDataListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.remove(l);
	}

}
