package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The collection that maps keys with values. SimpleHashtable cannot contain
 * duplicates.
 * 
 * @author david
 *
 * @param <K> The type of the keys.
 * @param <V> The type of the values that will be mapped with given keys.
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	/**
	 * Stores the size of the dictionary.
	 */
	private int size;

	/**
	 * Stores the entries of the dictionary.
	 */
	private TableEntry<K, V>[] table;
	
	/**
	 * Number of modifications.
	 */
	private int modificationCount = 0;
	
	/**
	 * Default constructor that creates map with 16 slots.
	 */
	public SimpleHashtable() {
		this(16);
	}

	/**
	 * Constructor that creates map with given capacity.
	 * 
	 * @param capacity Number of slots in map.
	 * @throws IllegalArgumentException if the capacity is less than 1.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException();
		}

		capacity = getNextPowerTwo(capacity);

		table = new TableEntry[capacity];
		size = 0;
	}

	/**
	 * Method to find the first greater number that is exponent of number 2. For
	 * example, if the given number is 31 this method will return 32.
	 * 
	 * @param number Number to find the first greater that is exponent of number 2.
	 * @return Returns the first number that is greater than given and is expoenent
	 *         of 2.
	 */
	private int getNextPowerTwo(int number) {
		double exponent = Math.log10(number) / Math.log10(2);
		int newExponent = (int) Math.ceil(exponent);

		return (int) Math.pow(2, newExponent);
	}

	/**
	 * Connects the specified value with the specified key in this dictionary. If
	 * dictionary already contains given key, the old value is replaced with the new
	 * value. Value is allowed to be null, but the key must not be null.
	 * 
	 * @param key   key to be connect with specified value.
	 * @param value value to be connected with given key.
	 * @throws NullPointerException if key is null.
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new NullPointerException();
		}

		if (containsKey(key)) {
			updateValue(key, value);
			return;
		}
		
		if (size >= table.length * 0.75) {
			resize();
		}
		
		modificationCount++;

		int slot = Math.abs(key.hashCode() % table.length);

		insertToEnd(table, slot, key, value);
		size++;
	}

	/**
	 * Method to resize the table if it gets too full.
	 */
	private void resize() {
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] newTable = new TableEntry[table.length * 2];

		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> head = table[i];
			while (head != null) {
				int slot = Math.abs(head.getKey().hashCode() % newTable.length);

				insertToEnd(newTable, slot, head.key, head.value);
				head = head.next;
			}

		}
		table = newTable;
	}

	/**
	 * Method to insert given key and value to end of the given slot.
	 * 
	 * @param slot  Slot in which the number will be inserted.
	 * @param key   key to be connect with specified value.
	 * @param value value to be connected with given key.
	 */
	private void insertToEnd(TableEntry<K, V>[] table, int slot, K key, V value) {
		TableEntry<K, V> head = table[slot];

		TableEntry<K, V> newEntry = new TableEntry<>(key, value);

		if (head == null) {
			table[slot] = newEntry;
			return;
		}

		while (head.next != null) {
			head = head.next;
		}

		head.next = newEntry;
	}

	/**
	 * Private method to update value of given TableEntry<K, V> with given key.
	 * 
	 * @param key   key to be connect with specified value.
	 * @param value value to be connected with given key.
	 */
	private void updateValue(K key, V value) {
		int slot = Math.abs(key.hashCode() % table.length);

		TableEntry<K, V> head = table[slot];

		while (head != null) {
			if (head.getKey().equals(key)) {
				head.setValue(value);
				return;
			}
			head = head.next;
		}
	}

	/**
	 * Returns the value to which the given key is mapped. If given key does not
	 * exists in the dictionary, null value will be returned.
	 * 
	 * @param key The key whose associated value will be returned.
	 * @return The value to which the key is associated in the dictionary. If the
	 *         key is null this method will return null.
	 */
	public V get(Object key) {
		if (key == null) {
			return null;
		}
		int slot = Math.abs(key.hashCode() % table.length);

		TableEntry<K, V> head = table[slot];
		while (head != null) {
			if (head.getKey().equals(key)) {
				return head.getValue();
			}
			head = head.next;
		}
		return null;
	}

	/**
	 * Returns the number of key-value mappings in this map.
	 * 
	 * @return Size of the dictionary.
	 */
	public int size() {
		return size;
	}

	/**
	 * Method to check if given key is presented in map or not. If the key is null
	 * this method will return false because this collection cannot contain null
	 * values.
	 * 
	 * @param key Key to check if is presented in map.
	 * @return True if the key is presented in the map.
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}

		int slot = Math.abs(key.hashCode() % table.length);

		TableEntry<K, V> head = table[slot];

		while (head != null) {
			if (head.getKey().equals(key)) {
				return true;
			}
			head = head.next;
		}
		return false;
	}

	/**
	 * Method to check if given value is presented in map or not.
	 * 
	 * @param value Value to check if is presented in map.
	 * @return True is the value is presented in the map, otherwise returns false.
	 */
	public boolean containsValue(Object value) {

		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> head = table[i];

			while (head != null) {
				if (head.getValue() == null) {
					if (value == null) {
						return true;
					}
				} else if (head.getValue().equals(value)) {
					return true;
				}
				head = head.next;
			}
		}
		return false;
	}

	/**
	 * Method that removes specified key-value from the map.
	 * 
	 * @param key Key to be removed from the map. If the key is null, nothing is
	 *            removed because this collection does not contain null values.
	 */
	@SuppressWarnings("null")
	public void remove(Object key) {
		if (!containsKey(key)) {
			return;
		}
		
		modificationCount++;
		
		int slot = Math.abs(key.hashCode() % table.length);
		TableEntry<K, V> head = table[slot];

		// Case when removing head from the list.
		if (head != null && head.getKey().equals(key)) {
			table[slot] = head.next;
			size--;
			return;
		}

		TableEntry<K, V> previous = null;
		while (head != null) {
			if (head.getKey().equals(key)) {
				previous.next = head.next;
				size--;
				return;
			}
			previous = head;
			head = head.next;
		}

		previous.next = head.next;
		size--;
	}

	/**
	 * Method to check if the map is empty or not. Map is empty if no pair key-value
	 * is stored in it.
	 * 
	 * @return True if map is empty, otherwise returns false
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("[");

		int counter = 0;
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> head = table[i];

			while (head != null) {
				counter++;
				sb.append(head.getKey()).append("=").append(head.getValue());
				if (counter < size) {
					sb.append(", ");
				}
				head = head.next;
			}
		}

		sb.append("]");

		return sb.toString();
	}

	/**
	 * Deletes all key-value pairs from the map.
	 */
	public void clear() {
		modificationCount++;
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		size = 0;
	}

	/**
	 * Static class that is used to store the key-value pair.
	 * 
	 * @author david
	 *
	 * @param <K> The type of the keys.
	 * @param <V> The type of the values that will be mapped with given keys.
	 */
	public static class TableEntry<K, V> {
		/**
		 * Key of this pair.
		 */
		private K key;

		/**
		 * Value of this pair.
		 */
		private V value;

		/**
		 * Pointer to the next TableEntry<K, V> in list.
		 */
		private TableEntry<K, V> next;

		/**
		 * Constructor to initialize key and value.
		 * 
		 * @param key   new key.
		 * @param value new value.
		 */
		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
			next = null;
		}

		/**
		 * Returns key.
		 * 
		 * @return key.
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Return value.
		 * 
		 * @return value.
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Method to set the value to new value.
		 * 
		 * @param value new value.
		 */
		public void setValue(V value) {
			this.value = value;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Implementation of the Iterator interface.
	 * 
	 * @author david
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		/**
		 * Slot of the table.
		 */
		private int slot;

		/**
		 * Current element of the map.
		 */
		private TableEntry<K, V> current;

		/**
		 * Index of current element in table.
		 */
		private int index;

		/**
		 * Stores how many times method remove() has been called.
		 */
		private int removeCalled = -1;
		
		/**
		 * Modification count.
		 */
		private int modCount;

		/**
		 * Constructor to initialize class members.
		 */
		public IteratorImpl() {
			index = 0;
			slot = 0;
			
			modCount = modificationCount;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			if (modCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
			return index < size;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public SimpleHashtable.TableEntry<K, V> next() {
			if (modCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
			
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			removeCalled = 0;
			
			findNext();
			TableEntry<K, V> entry = current;
			
			index++;
			
			return entry;
		}

		/**
		 * Method to remove the element while iterating.
		 * 
		 * @throws IllegalStateException if the next method has not yet been called, or
		 *                               remove method has already been called twice
		 *                               since the last time next() has been called.
		 */
		public void remove() {
			if (modCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
			
			removeCalled++;

			if (removeCalled != 1) {
				throw new IllegalStateException();
			}
			
			SimpleHashtable.this.remove(current.getKey());
			modCount = modificationCount;
			
			index--;
		}

		/**
		 * Method to find the next element.
		 */
		private void findNext() {
			if (current != null) {
				current = current.next;
			}

			while (current == null && slot < table.length) {
				current = table[slot++];
			}
		}
	}
}
