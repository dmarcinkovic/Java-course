package hr.fer.zemris.java.hw07.demo2;

/**
 * Demo example of primes collections.
 * 
 * @author david
 *
 */
public class PrimesDemo2 {

	/**
	 * Method invoked when running the program. This method prints Cartesian product
	 * of first two prime numbers.
	 * 
	 * @param args Argument provided via command line. In this example they are not used.
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);

		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}
}
