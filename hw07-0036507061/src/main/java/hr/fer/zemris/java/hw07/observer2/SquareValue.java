package hr.fer.zemris.java.hw07.observer2;

/**
 * One concrete implementation of IntegerStorageObserver. Prints square of the
 * number stored in the IntegerStorage class.
 * 
 * @author david
 *
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		int value = istorage.getNewValue();
		System.out.println("Provided new value: " + value + " square is " + value * value);
	}

}
