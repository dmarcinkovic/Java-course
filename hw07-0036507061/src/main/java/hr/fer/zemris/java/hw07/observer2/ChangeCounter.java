package hr.fer.zemris.java.hw07.observer2;

/**
 * One concrete implementation of IntegerStorageObserver.
 * @author david
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	/**
	 * Stores the number of changes. 
	 */
	private int counter = 1;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.println("Number of value changes since tracking: " + counter);
		counter++;
	}

}
