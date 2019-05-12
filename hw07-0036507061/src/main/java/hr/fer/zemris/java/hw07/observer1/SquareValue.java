package hr.fer.zemris.java.hw07.observer1;

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
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		System.out.println("Provided new value: " + value + " square is " + value * value);
	}

}
