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
public class Collection {
	/**
	 * Default constructor that does nothing.
	 */
	protected Collection() {

	}

	/**
	 * Method to check if collection contains any(more than zero) element.
	 * 
	 * @return True if collection contains one or more elements.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Method to return number of elements stored in collection.
	 * 
	 * @return Number of elements stored in collection.
	 */
	public int size() {
		return 0;
	}

	/**
	 * Method to insert elements to the end of the collection.
	 * 
	 * @param value Value to be stored in collection.
	 * @throws NullPointerException if value is null.
	 */
	public void add(Object value) {

	}

	/**
	 * Method to check if given value is stored in collection. If value is null
	 * returns false because null values are not allowed.
	 * 
	 * @param value The value to check if is presented in the collection.
	 * @return True if value is presented in collection.
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Method to remove given value from collection. If there are more than one
	 * instance of value, method removes value that has smaller index (appears first
	 * going from start of the collection). If there is no given value in collection
	 * method does nothing.
	 * 
	 * @param value Value to be removed from the collection.
	 * @return True if element is removed successfully.
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Method to convert collection to array of objects.
	 * 
	 * @return Array representation of collection.
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method to traverse through all values stored in collection.
	 * 
	 * @param processor Instance of Processor. Method process will be called for
	 *                  every value stored in collection.
	 */
	public void forEach(Processor processor) {

	}

	/**
	 * Appends all of the elements from specified collection to this one.
	 * 
	 * @param other Collection that contains elements to be added in this
	 *              collection.
	 * @throws NullPointerException if other is null.
	 */
	public void addAll(Collection other) {
		if (other == null) {
			throw new NullPointerException();
		}
		class AddToCollection extends Processor {
			public void process(Object value) {
				add(value);
			}
		}

		Processor processor = new AddToCollection();
		other.forEach(processor);
	}

	/**
	 * Removes all elements from the collection.
	 * After this method is called size of collection will be zero.
	 */
	public void clear() {

	}
}
