package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

/**
 * Demonstration of class ElementsGreater.
 * 
 * @author david
 *
 */
public class ElementsGetterDemo2 {

	/**
	 * Method invoked when running the program. It is used to test is
	 * ElementsGreater is working correctly or not.
	 * 
	 * @param args Arguments provided via command line. In this example they are not
	 *             used.
	 */
	public static void main(String[] args) {
		Collection col = stvoriPraznuKolekciju(); // npr. new ArrayIndexedCollection();

		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");

		ElementsGetter getter = col.createElementsGetter();

		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());

		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());

		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());

		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());

		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());

		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());

		System.out.println("Jedan element: " + getter.getNextElement());

		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
	}

	/**
	 * Method to create empty collection. It creates instance of
	 * {@link LinkedListIndexedCollection} and returns it.
	 * 
	 * @return Empty collection.
	 */
	private static Collection stvoriPraznuKolekciju() {
		Collection collection = new LinkedListIndexedCollection();
		return collection;
	}
}
