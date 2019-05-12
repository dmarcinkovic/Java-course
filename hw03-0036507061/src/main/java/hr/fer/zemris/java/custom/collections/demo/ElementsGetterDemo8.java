package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;

/**
 * Demonstration of class ElementsGreater.
 * 
 * @author david
 *
 */
public class ElementsGetterDemo8 {
	
	/**
	 * Method invoked when running the program. It is used to test is
	 * ElementsGreater is working correctly or not.
	 * 
	 * @param args Arguments provided via command line. In this example they are not
	 *             used.
	 */
	public static void main(String[] args) {
		Collection col = new ArrayIndexedCollection();

		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");

		ElementsGetter getter = col.createElementsGetter();

		getter.getNextElement();
		getter.processRemaining(System.out::println);
	}
}
