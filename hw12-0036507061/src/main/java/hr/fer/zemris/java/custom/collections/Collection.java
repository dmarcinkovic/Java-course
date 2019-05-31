package hr.fer.zemris.java.custom.collections;

/**
 * An ordered collection of objects that allows inserting duplicates but do not
 * allow inserting null values. The user can access values by their
 * index(position in the collection). Elements can be inserted in an arbitrary
 * position in the collection.
 * 
 * @author david
 *
 */
public interface Collection {

	/**
	 * Method to check if collection contains any(more than zero) elements.
	 * 
	 * @return True if collection contains one or more elements.
	 */
	default boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Method to return number of elements stored in collection.
	 * 
	 * @return Number of elements stored in collection.
	 */
	int size();

	/**
	 * Method to insert elements to the end of the collection.
	 * 
	 * @param value Value to be stored in collection.
	 * @throws NullPointerException if value is null.
	 */
	void add(Object value);

	/**
	 * Method to check if given value is stored in collection. If value is null
	 * returns false because null values are not allowed.
	 * 
	 * @param value The value to check if is presented in the collection.
	 * @return True if value is presented in collection.
	 */
	boolean contains(Object value);

	/**
	 * Method to remove given value from collection. If there are more than one
	 * instance of value, method removes value that has smallest index (appears
	 * first going from start of the collection). If there is no given value in
	 * collection method does nothing.
	 * 
	 * @param value Value to be removed from the collection.
	 * @return True if element is removed successfully.
	 */
	boolean remove(Object value);

	/**
	 * Method to convert collection to array of objects.
	 * 
	 * @return Array representation of collection.
	 */
	Object[] toArray();

	/**
	 * Method to traverse through all values stored in collection and do some action
	 * specified by processor.
	 * 
	 * @param processor Instance of Processor. Method process will be called for
	 *                  every value stored in collection.
	 */
	default void forEach(Processor processor) {
		ElementsGetter getter = this.createElementsGetter();

		while (getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}

	/**
	 * Appends all of the elements from specified collection to this one.
	 * 
	 * @param other Collection that contains elements to be added in this
	 *              collection.
	 * @throws NullPointerException if other is null.
	 */
	default void addAll(Collection other) {
		if (other == null) {
			throw new NullPointerException();
		}
		class AddToCollection implements Processor {
			public void process(Object value) {
				add(value);
			}
		}

		Processor processor = new AddToCollection();
		other.forEach(processor);
	}

	/**
	 * Removes all elements from the collection. After this method is called size of
	 * collection will be zero.
	 */
	public void clear();

	/**
	 * Method used to create objects of type ElementsGetter- The task of the object
	 * ElementsGetter is to return element by element when the used requests it.
	 * 
	 * @return object of type ElementsGetter
	 */
	public ElementsGetter createElementsGetter();

	/**
	 * Default method to append all acceptable objects, which are examined by
	 * tester, from given collection to this collection.
	 * 
	 * @param col    Collection to add acceptable objects.
	 * @param tester Tester to test acceptability.
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter getter = col.createElementsGetter();

		while (getter.hasNextElement()) {

			Object o = getter.getNextElement();
			if (tester.test(o)) {
				this.add(o);
			}
		}
	}
}
