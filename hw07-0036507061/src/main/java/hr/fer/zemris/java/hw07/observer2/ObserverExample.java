package hr.fer.zemris.java.hw07.observer2;

/**
 * Observer example to represent the Observer model behaviour. 
 * 
 * @author david
 *
 */
public class ObserverExample {
	
	/**
	 * Method invoked when running the program. This method is example to represent
	 * the Observer model behaviour.
	 * 
	 * @param args Arguments provided via command line. In this example they are not
	 *             used.
	 */
	public static void main(String[] args) {

		IntegerStorage istorage = new IntegerStorage(20);

		IntegerStorageObserver observer = new SquareValue();

		istorage.addObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));

		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
//		istorage.removeObserver(observer);
//		
//		istorage.addObserver(new ChangeCounter());
//		istorage.addObserver(new DoubleValue(5));

		istorage.removeObserver(observer);

		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}