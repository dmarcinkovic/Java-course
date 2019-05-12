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
public class ElementsGetterDemo4 {
	
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
		ElementsGetter getter1 = col.createElementsGetter();
		ElementsGetter getter2 = col.createElementsGetter();
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
	}
	
	/**
	 * Method to create empty collection. It creates instance of
	 * ArrayIndexedCollection and returns it.
	 * 
	 * @return Empty collection.
	 */
	private static Collection stvoriPraznuKolekciju() {
		Collection collection = new ArrayIndexedCollection();
		return collection;
	}
}
