package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.List;

/**
 * Class to represent how List interface works.
 * 
 * @author david
 *
 */
public class ListDemo {

	/**
	 * Method invoked when starting the program. It is used to test whether List
	 * interface is correct or not.
	 * 
	 * @param args Arguments given via command line. In this example they are used.
	 *             If user provide any argument in the command line they will be
	 *             ignored.
	 */
	public static void main(String[] args) {
		List col1 = new ArrayIndexedCollection();
		List col2 = new LinkedListIndexedCollection();

		col1.add("Ivana");
		col2.add("Jasna");

		Collection col3 = col1;
		Collection col4 = col2;

		col1.get(0);
		col2.get(0);

		// col3.get(0); // neće se prevesti! Razumijete li zašto?
		// col4.get(0); // neće se prevesti! Razumijete li zašto?

		col1.forEach(System.out::println); // Ivana
		col2.forEach(System.out::println); // Jasna
		col3.forEach(System.out::println); // Ivana
		col4.forEach(System.out::println); // Jasna
	}
}
