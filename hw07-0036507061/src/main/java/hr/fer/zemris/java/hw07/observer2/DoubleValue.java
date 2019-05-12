package hr.fer.zemris.java.hw07.observer2;

/**
 * One concrete implementation of IntegerStorageObserver. DoubleValue class
 * write to the standard output double value (i.e. “value * 2”) of the current
 * value which is stored in subject, but only first n times since its
 * registration with the subject (n is given in constructor); after writing the
 * double value for the n-th time, the observer automatically de-registers
 * itself from the subject.
 * 
 * @author david
 *
 */
public class DoubleValue implements IntegerStorageObserver {
	/**
	 * Stores the number of times value will be printed to standard output.
	 */
	private int n;

	/**
	 * Constructor to initialize number of times value will be printed to standard
	 * output.
	 * 
	 * @param n
	 */
	public DoubleValue(int n) {
		this.n = n;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		int value = istorage.getNewValue();
		if (n > 0) {
			System.out.println("Double value: " + value * 2);
			n--;
		} else {
			istorage.getIstorage().removeObserver(this);
		}

	}

}
