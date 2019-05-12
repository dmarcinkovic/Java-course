package hr.fer.zemris.java.hw07.demo2;

/**
 * Demo example of primes collection.
 * 
 * @author david
 *
 */
public class PrimesDemo1 {

	/**
	 * Method invoked when running the program. This method prints first 5 prime
	 * numbers.
	 * 
	 * @param args Arguments provided via command line. In this example they are not
	 *             used.
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them

		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}
	}
}
