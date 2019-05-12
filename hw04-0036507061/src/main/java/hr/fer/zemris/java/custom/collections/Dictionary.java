package hr.fer.zemris.java.custom.collections;

/**
 * The collection that maps keys with values. Dictionary cannot contain
 * duplicates.
 * 
 * @author david
 *
 * @param <K> The type of the keys.
 * @param <V> The type of the values that will be mapped with given keys.
 */
public class Dictionary<K, V> {
	/**
	 * Stores the size of the dictionary.
	 */
	private int size;

	/**
	 * Stores the entries of the dictionary.
	 */
	private ArrayIndexedCollection<Entry<K, V>> dictionary;

	/**
	 * Constructor to initialize private class members.s
	 */
	public Dictionary() {
		size = 0;
		dictionary = new ArrayIndexedCollection<>();
	}

	/**
	 * Method to check if Dictionary is empty or not.
	 * 
	 * @return true if dictionary is empty, otherwise returns false.
	 */
	public boolean isEmpty() {
		return size == 0;
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
	 * Clears the dictionary. After this method is called size of the dictionary
	 * will be zero.
	 */
	public void clear() {
		dictionary.clear();
		size = 0;
	}

	/**
	 * Connects the specified value with the specified key in this dictionary. If
	 * dictionary already contains given key, the old value is replaced with the new
	 * value. Value is allowed to be null, but the key must not be null.
	 * 
	 * @param key   key to be connect with specified value.
	 * @param value value to be connected with given key.
	 * @throws NullPointerException if the key is null.
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new NullPointerException();
		}
		Entry<K, V> entry = new Entry<>(key, value);
		if (dictionary.contains(entry)) {
			Entry<K ,V> original = dictionary.get(dictionary.indexOf(entry));
			original.setValue(value);
		}else {
			dictionary.add(entry);
			size++;
		}
	}
	/**
	 * Returns the value to which the given key is mapped. If given key does not
	 * exists in the dictionary, null value will be returned.
	 * 
	 * @param key The key whose associated value will be returned.
	 * @return The value to which the key is associated in the dictionary.
	 */
	public V get(Object key) {
		try {
			@SuppressWarnings("unchecked")
			Entry<K, V> entry = new Entry<>((K)key, null);
			int index = dictionary.indexOf(entry);
			
			if (index < 0) {
				return null;
			}
			
			Entry<K, V> original = dictionary.get(index);
			return original.getValue();
		}catch(ClassCastException e) {
			return null;
		}
	}

	/**
	 * A dictionary entry. This is key-value pair.
	 * 
	 * @author david
	 *
	 * @param <K> The type of the keys.
	 * @param <V> The type of the values that will be mapped with given keys.
	 */
	private static class Entry<K, V> {
		/**
		 * Key of the dictionary entry.
		 */
		private K key;

		/**
		 * Value associated with the key.
		 */
		private V value;

		/**
		 * Constructor to initialize key and value.
		 * 
		 * @param key   Key to be initialized.
		 * @param value Value to be initialized.
		 */
		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
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
		 * Returns value associated with the key.
		 * 
		 * @return Value.
		 */
		public V getValue() {
			return value;
		}
		
		/**
		 * Method to set the value to new value.
		 * @param value new Value.
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override 
		public boolean equals(Object o) {
			if (!(o instanceof Entry)) {
				return false;
			}
			@SuppressWarnings("unchecked")
			Entry<K, V> other = (Entry<K, V>)o;
			return this.key.equals(other.getKey());
		}
	}
}
