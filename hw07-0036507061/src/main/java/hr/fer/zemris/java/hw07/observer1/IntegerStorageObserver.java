package hr.fer.zemris.java.hw07.observer1;

/**
 * Interface that represents Observer in Observer model.
 * 
 * @author david
 *
 */
public interface IntegerStorageObserver {

	/**
	 * Method to inform observer that value has changed..
	 * 
	 * @param istorage
	 */
	public void valueChanged(IntegerStorage istorage);
}