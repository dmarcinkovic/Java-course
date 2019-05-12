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
public interface List<T> extends Collection<T> {

	/**
	 * Returns the element at the specified position in this list.
	 * 
	 * @param index Index of the element.
	 * @return Value of stored at given index.
	 * @throws IndexOutOfBoundsException if index is out of bounds.
	 */
	T get(int index);

	/**
	 * Method to insert value at given position. It does not allow inserting null
	 * values. Method works in O(n).
	 * 
	 * @param value    Value to be stored in collection.
	 * @param position Position to store given element.
	 * @throws NullPointerException      if value is null.
	 * @throws IndexOutOfBoundsException is position is out of bounds.
	 */
	void insert(T value, int position);

	/**
	 * Returns the index of the first occurrence of the specified element in this
	 * list, or -1 if this list does not contain the element. Works in O(n).
	 * 
	 * @param value Value to search for.
	 * @return The first occurrence of the given element or -1 if element does not
	 *         exist in list.
	 */
	int indexOf(Object value);

	/**
	 * Method that removes element from specified index.
	 * 
	 * @param index Element to be deleted from list.
	 * @throws IllegalArgumentException if index is out of bounds.
	 */
	void remove(int index);
}
