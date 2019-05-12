package hr.fer.zemris.java.hw07.observer2;

/**
 * Object that store information about one state of Subject in Observer model.
 * 
 * @author david
 *
 */
public class IntegerStorageChange {
	/**
	 * Reference to IntegerStorage.
	 */
	private IntegerStorage istorage;

	/**
	 * Previous value.
	 */
	private int previousValue;

	/**
	 * New value.
	 */
	private int newValue;

	/**
	 * Constructor to initialize IntegerStorage, previous and newValue.
	 * 
	 * @param istorage      integerStorage.
	 * @param previousValue previous value.
	 * @param newValue      new value.
	 */
	public IntegerStorageChange(IntegerStorage istorage, int previousValue, int newValue) {
		this.istorage = istorage;
		this.previousValue = previousValue;
		this.newValue = newValue;
	}

	/**
	 * Returns IntegerStorage.
	 * 
	 * @return IntegerStograge.
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}

	/**
	 * Returns previous value.
	 * 
	 * @return previous value.
	 */
	public int getPreviousValue() {
		return previousValue;
	}

	/**
	 * Returns next value.
	 * 
	 * @return next value.
	 */
	public int getNewValue() {
		return newValue;
	}

}
