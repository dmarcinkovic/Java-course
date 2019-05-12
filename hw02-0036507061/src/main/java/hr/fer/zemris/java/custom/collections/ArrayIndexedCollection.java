package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based list. It is implemented using dynamic array. When array is
 * filled, new array is created with the double of size as previous. Collection
 * allows inserting all values, except null values. Duplicates are allowed.
 * 
 * @author david
 *
 */
public class ArrayIndexedCollection extends Collection {
	/**
	 * Variable to store the number of elements stored in collection.
	 */
	private int size;

	/**
	 * Array where elements are stored.
	 */
	private Object[] elements;

	/**
	 * Constant that presents default capacity of list.
	 */
	private static int DEFAULT_CAPACITY = 16;

	/**
	 * Default constructor that creates collection of the size 16.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructor to create array based collection of size given with the argument
	 * initialCapacity.
	 * 
	 * @param initialCapacity Capacity of list.
	 * @throws IllegalArgumentException if initialCapacity is less than one.
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		elements = new Object[initialCapacity];
	}

	/**
	 * Constructor to initialize this collection. It copies elements from
	 * collection. If initialCapacity is less than size of the given collection than
	 * its size will be the size of the given collection, otherwise it will have
	 * size given by the argument initialCapacity.
	 * 
	 * @param collection      Collection which elements will be added to this
	 *                        collection.
	 * @param initialCapacity Capacity of the list.
	 * @throws IllegalArgumentException if initial capacity is less than one.
	 * @throws NullPointerException     if collection is null.
	 * 
	 */
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}

		if (collection == null) {
			throw new NullPointerException();
		}

		if (initialCapacity < collection.size()) {
			initialCapacity = collection.size();
		}

		elements = new Object[initialCapacity];
		addAll(collection);
	}

	/**
	 * Constructor to initialize this collection. It copies elements from
	 * collection.
	 * 
	 * @param collection
	 * @throws NullPointerException is collection is null.
	 */
	public ArrayIndexedCollection(Collection collection) {
		this(collection, 1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		Arrays.fill(elements, null);
		size = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size); 
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * {@inheritDoc} Method works in constant time.
	 */
	@Override
	public void add(Object value) {
		int capacity = elements.length;
		if (size == capacity) {
			capacity *= 2;
			elements = Arrays.copyOf(elements, capacity);
		}
		elements[size] = Objects.requireNonNull(value);
		size++;
	}

	/**
	 * Method to return element at specified index. Works in constant time.
	 * 
	 * @param index Index of the element.
	 * @return Value of stored at given index.
	 * @throws IndexOutOfBoundsException if index is out of bounds.
	 */
	public Object get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}

	/**
	 * Method to insert value at given position. It does not allow inserting null
	 * values. Method works in O(n).
	 * 
	 * @param value    Value to be stored in collection.
	 * @param position Position to store given element.
	 * @throws NullPointerException      if value is null.
	 * @throws IndexOutOfBoundsException is position is out of bounds.
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}

		if (isEmpty()) {
			add(value);
			return;
		}

		add(elements[size - 1]);
		for (int i = size - 2; i > position; i--) {
			elements[i] = elements[i - 1];
		}

		elements[position] = Objects.requireNonNull(value);
	}

	/**
	 * Method to return index of first occurrence of the given element. Method works
	 * in O(n).
	 * 
	 * @param value Value to find index.
	 * @return index of first occurrence of given element.
	 */
	public int indexOf(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);

		if (index == -1) {
			return false;
		}

		remove(index);
		return true;
	}

	/**
	 * Method that removes element from specified index.
	 * 
	 * @param index Index to remove from.
	 * @throws IndexOutOfBoundsException if index is out of bounds.
	 */
	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		size--;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
}
