package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Doubly linked list implementation of the collection. Collection allows
 * inserting all values, except null values. Duplicates are allowed.
 * 
 * @author david
 *
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * Variable to store the number of elements stored in collection.
	 */
	private int size;

	/**
	 * Pointer to the head (first element in list) of the doubly linked list.
	 */
	private ListNode first;

	/**
	 * Pointer to the tail (last element in list) of the doubly linked list.
	 */
	private ListNode last;

	/**
	 * Default constructor. Creates collection of size 0.
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
		size = 0;
	}

	/**
	 * Constructor that receives collection and appends all its elements to this
	 * collection.
	 * 
	 * @param collection Collection which elements will be added to this one.
	 * @throws NullPointerException if collection is null.
	 */
	public LinkedListIndexedCollection(Collection collection) {
		addAll(Objects.requireNonNull(collection));
	}

	/**
	 * Private class that represents one node of the linked list. It contains
	 * pointers to next and previous element(doubly linked list) and value to be
	 * stored.
	 * 
	 * @author david
	 *
	 */
	private static class ListNode {
		/**
		 * Pointer to the previous element in list.
		 */
		ListNode previous;

		/**
		 * Pointer to the next element in the list.
		 */
		ListNode next;

		/**
		 * Value to be stored in doubly linked list.
		 */
		Object value;

		/**
		 * Constructor that create one node of the linked list.
		 * 
		 * @param value Value to be stored in list.
		 */
		public ListNode(Object value) {
			this.value = value;
			previous = next = null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * {@inheritDoc} It adds element in constant time.
	 */
	@Override
	public void add(Object value) {
		ListNode newNode = new ListNode(Objects.requireNonNull(value));

		if (size == 0) {
			first = last = newNode;
		} else {
			last.next = newNode;
			newNode.previous = last;
			last = newNode;
		}
		size++;
	}

	/**
	 * Returns the element at the specified position in this list. Finds element in
	 * O(n/2 + 1).
	 * 
	 * @param index Index of the element.
	 * @return Object stored in collection at given index.
	 * @throws IllegalArgumentException if index is out of bounds (less than zero,
	 *                                  greater than size of the collection).
	 */
	public Object get(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException();
		}

		ListNode currentNode = null;
		int currentIndex = -1;

		/*
		 * If index is on the right side of the list find the element going from the
		 * end. Otherwise find element going from the start.
		 */
		if (index > size / 2) {
			currentNode = last;
			currentIndex = size - 1;
		} else {
			currentNode = first;
			currentIndex = 0;
		}

		while (currentNode != null) {
			if (currentIndex == index) {
				return currentNode.value;
			}

			if (index > size / 2) {
				currentIndex--;
				currentNode = currentNode.previous;
			} else {
				currentIndex++;
				currentNode = currentNode.next;
			}
		}
		return null;
	}

	/**
	 * Method to insert value at given element. Average complexity is O(n).
	 * 
	 * @param value    Value to be stored at given position.
	 * @param position Position where value will be inserted-
	 * @throws IllegalArgumentException if position if out of bounds.
	 * @throws NullPointerException     if value is null.
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IllegalArgumentException();
		}

		/*
		 * Inserting at the end of list is same as adding to list.
		 */
		if (position == size) {
			add(value);
			return;
		}

		ListNode newNode = new ListNode(Objects.requireNonNull(value));

		/*
		 * Insert value to begin of the list.
		 */
		if (position == 0) {
			first.previous = newNode;
			newNode.next = first;
			first = newNode;
			size++;
			return;
		}

		/*
		 * Insert element somewhere in middle. Traverse list to find given index and
		 * then insert value to list.
		 */
		ListNode current = first;
		ListNode previous = null;
		int index = 0;

		while (current != null) {
			if (index == position) {
				current.previous = newNode;
				previous.next = newNode;
				newNode.next = current;
				newNode.previous = previous;
				size++;
				return;
			}
			index++;
			previous = current;
			current = current.next;
		}
	}

	/**
	 * Returns the index of the first occurrence of the specified element in this
	 * list, or -1 if this list does not contain the element. Works in O(n).
	 * 
	 * @param value Value to search for.
	 * @return The first occurrence of the given element or -1 if element does not
	 *         exist in list.
	 */
	public int indexOf(Object value) {
		ListNode currentNode = first;

		int currentPosition = 0;
		while (currentNode != null) {
			if (currentNode.value.equals(value)) {
				return currentPosition;
			}
			currentNode = currentNode.next;
			currentPosition++;
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
	 * Method to remove element with the specified index.
	 * 
	 * @param index Element to be deleted from list.
	 * @throws IllegalArgumentException if index is out of bounds.
	 */
	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException();
		}

		/*
		 * There are four base cases. First case is when our list is of size 1. In that
		 * case we just set pointers first and last to null. Second case is when we try
		 * to remove from the head of the list. We make first to point to the second
		 * element. Third case is when we try to remove from the tail of the list. We
		 * make last to point to the penultimate. Fourth case is when we try to remove
		 * from the middle of the list.
		 */
		if (size == 1) {
			first = null;
			last = null;
			size--;
			return;
		} else if (index == 0) {
			first = first.next;
			first.previous = null;
			size--;
			return;
		} else if (index == size - 1) {
			last = last.previous;
			last.next = null;
			size--;
			return;
		}

		int currentPosition = 0;
		ListNode currentNode = first;
		ListNode previousNode = null;
		
		while (currentNode != null) {
			if (currentPosition == index) {
				previousNode.next = currentNode.next;
				currentNode.next.previous = previousNode;
				size--;
				break;
			}
			previousNode = currentNode;
			currentNode = currentNode.next;
			currentPosition++;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		int index = 0;
		ListNode currentNode = first;

		while (currentNode != null) {
			array[index++] = currentNode.value;
			currentNode = currentNode.next;
		}

		return array;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(Object value) {
		ListNode currentNode = first;

		while (currentNode != null) {
			if (currentNode.value.equals(value)) {
				return true;
			}
			currentNode = currentNode.next;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void forEach(Processor processor) {
		ListNode currentNode = first;

		while (currentNode != null) {
			processor.process(currentNode.value);
			currentNode = currentNode.next;
		}
	}
}
