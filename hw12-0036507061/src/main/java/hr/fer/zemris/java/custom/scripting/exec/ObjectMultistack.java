package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Collection that connects keys with values. Unlike map, this collection can
 * store more values for one key. It is allowed for value to be null, but key
 * must not be null. Values are stored in stack. This means that element that is
 * push last will be popped first.
 * 
 * @author david
 *
 */
public class ObjectMultistack {

	/**
	 * Map to connect String with List of values.
	 */
	private Map<String, MultistackEntry> map;

	/**
	 * Initialize private members.
	 */
	public ObjectMultistack() {
		map = new HashMap<>();
	}

	/**
	 * Method to associate new value with given key.
	 * 
	 * @param keyName      Given key.
	 * @param valueWrapper New value.
	 * @throws NullPointerException if keyName or valueWrapper are null.
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		if (keyName == null) {
			throw new NullPointerException();
		}

		if (valueWrapper == null) {
			throw new NullPointerException();
		}

		MultistackEntry head = map.get(keyName);

		MultistackEntry newEntry = new MultistackEntry(valueWrapper);
		newEntry.next = head;
		head = newEntry;

		map.put(keyName, head);
	}

	/**
	 * Method to remove last added value that is associated with given key from this
	 * collection.
	 * 
	 * @param keyName given key.
	 * @return Value that will be removed.
	 * @throws NoSuchElementException if stack is empty.
	 */
	public ValueWrapper pop(String keyName) {
		MultistackEntry head = map.get(keyName);

		if (head == null) {
			throw new NoSuchElementException();
		}

		ValueWrapper value = head.getValue();

		head = head.next;
		map.put(keyName, head);

		if (head == null) {
			map.remove(keyName);
		}

		return value;
	}

	/**
	 * Method that returns last added value that is associated with the given key.
	 * Unlike the pop method , this method does not remove element from the
	 * collection, it just returns the value.
	 * 
	 * @param keyName given key.
	 * @return Last added values.
	 * @throws NoSuchElementException if stack is empty.
	 */
	public ValueWrapper peek(String keyName) {
		MultistackEntry head = map.get(keyName);

		if (head == null) {
			throw new NoSuchElementException();
		}

		ValueWrapper value = head.getValue();

		return value;
	}

	/**
	 * Method to check if stack that is associated with the given key is empty or
	 * not.
	 * 
	 * @param keyName given key.
	 * @return true if stack that is associated with the given key is empty,
	 *         otherwise returns false.
	 * @throws NullPointerException if keyName is null.
	 */
	public boolean isEmpty(String keyName) {
		if (keyName == null) {
			throw new NullPointerException();
		}
		return map.get(keyName) == null;
	}

	/**
	 * Structure that represents one element that is associated with the key.
	 * 
	 * @author david
	 *
	 */
	private static class MultistackEntry {
		/**
		 * Stores the value.
		 */
		private ValueWrapper value;

		/**
		 * Pointer to the next node in stack.
		 */
		MultistackEntry next;

		/**
		 * Public constructor to initialize value.
		 * 
		 * @param value Value to be initialized.
		 */
		public MultistackEntry(ValueWrapper value) {
			this.value = value;
		}

		/**
		 * Returns value.
		 * 
		 * @return value.
		 */
		public ValueWrapper getValue() {
			return value;
		}
	}
}
