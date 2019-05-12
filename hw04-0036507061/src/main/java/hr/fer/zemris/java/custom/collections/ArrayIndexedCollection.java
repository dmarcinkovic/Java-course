package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Array based list. It is implemented using dynamic array. When array is
 * filled, new array is created with the double of size as previous. Collection
 * allows inserting all values, except null values. Duplicates are allowed.
 * 
 * @author david
 *
 */
public class ArrayIndexedCollection<T> implements List<T> {
	/**
	 * Variable to store the number of elements stored in collection.
	 */
	private int size;

	/**
	 * Array where elements are stored.
	 */
	private T[] elements;

	/**
	 * Counts how many modifications has been made.
	 */
	private long modificationCount = 0;

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
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		elements = (T[])new Object[initialCapacity];
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
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<T> collection, int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}

		if (collection == null) {
			throw new NullPointerException();
		}

		if (initialCapacity < collection.size()) {
			initialCapacity = collection.size();
		}

		elements = (T[])new Object[initialCapacity];
		addAll(collection);
	}

	/**
	 * Constructor to initialize this collection. It copies elements from
	 * collection.
	 * 
	 * @param collection
	 * @throws NullPointerException is collection is null.
	 */
	public ArrayIndexedCollection(Collection<T> collection) {
		this(collection, 1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		Arrays.fill(elements, null);
		size = 0;
		modificationCount++;
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
	public void add(T value) {
		int capacity = elements.length;
		if (size == capacity) {
			capacity *= 2;
			elements = Arrays.copyOf(elements, capacity);
		}
		elements[size] = Objects.requireNonNull(value);
		size++;

		modificationCount++;
	}

	/**
	 * {@inheritDoc} Works in constant time.
	 * 
	 */
	@Override
	public T get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insert(T value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}

		if (isEmpty()) {
			add(value);
			return;
		}

		modificationCount++;

		add(elements[size - 1]);
		for (int i = size - 2; i > position; i--) {
			elements[i] = elements[i - 1];
		}

		elements[position] = Objects.requireNonNull(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
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
	 * {@inheritDoc}
	 */
	@Override
	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		modificationCount++;

		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		size--;
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

	/**
	 * Private static class representing an implementation of the object whose task
	 * is to retract element by element when requested by the user.
	 * 
	 * @author david
	 *
	 */
	private static class ArrayListElementsGetter<T> implements ElementsGetter<T> {
		/**
		 * Private field that keeps track of the current position in list.
		 */
		private int index;

		/**
		 * Reference to {@link ArrayIndexedCollection}.
		 */
		private ArrayIndexedCollection<T> collection;

		/**
		 * Counts how many modifications has been made.
		 */
		private long savedModificationCount;

		/**
		 * Constructor that initializes field collection.
		 * 
		 * @param collection The collection from which elements will be given.
		 */
		public ArrayListElementsGetter(ArrayIndexedCollection<T> collection) {
			this.collection = collection;
			index = 0;
			savedModificationCount = collection.modificationCount;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNextElement() {
			if (savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException();
			}
			return index < collection.size;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public T getNextElement() {
			if (!hasNextElement()) {
				throw new NoSuchElementException();
			}

			if (savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException();
			}

			T element = collection.get(index);
			index++;

			return element;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ArrayListElementsGetter<T>(this);
	}
}
