package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Doubly linked list implementation of the collection. Collection allows
 * inserting all values, except null values. Duplicates are allowed.
 * 
 * @author david
 *
 */
public class LinkedListIndexedCollection<T> implements List<T> {
	/**
	 * Counts how many modifications has been made.
	 */
	private long modificationCount = 0;

	/**
	 * Variable to store the number of elements stored in collection.
	 */
	private int size;

	/**
	 * Pointer to the head (first element in list) of the doubly linked list.
	 */
	private ListNode<T> first;

	/**
	 * Pointer to the tail (last element in list) of the doubly linked list.
	 */
	private ListNode<T> last;

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
	public LinkedListIndexedCollection(Collection<T> collection) {
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
	private static class ListNode<T> {
		/**
		 * Pointer to the previous element in list.
		 */
		ListNode<T> previous;

		/**
		 * Pointer to the next element in the list.
		 */
		ListNode<T> next;

		/**
		 * Value to be stored in doubly linked list.
		 */
		T value;

		/**
		 * Constructor that create one node of the linked list.
		 * 
		 * @param value Value to be stored in list.
		 */
		public ListNode(T value) {
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
		modificationCount++;
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
	public void add(T value) {
		ListNode<T> newNode = new ListNode<>(Objects.requireNonNull(value));

		if (size == 0) {
			first = last = newNode;
		} else {
			last.next = newNode;
			newNode.previous = last;
			last = newNode;
		}
		size++;
		modificationCount++;
	}

	/**
	 * {@inheritDoc} Finds element in O(n/2 + 1).
	 * 
	 */
	@Override
	public T get(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException();
		}

		ListNode<T> currentNode = null;
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
	 * {@inheritDoc}
	 */
	@Override
	public void insert(T value, int position) {

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

		ListNode<T> newNode = new ListNode<>(Objects.requireNonNull(value));

		modificationCount++;

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
		 * Insert element somewhere in the middle. Traverse list to find given index and
		 * then insert value to list.
		 */
		ListNode<T> current = first;
		ListNode<T> previous = null;
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
	 * {@inheritDoc}
	 */
	@Override
	public int indexOf(Object value) {
		ListNode<T> currentNode = first;

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
	 * {@inheritDoc}
	 */
	@Override
	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException();
		}

		modificationCount++;

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
		ListNode<T> currentNode = first;
		ListNode<T> previousNode = null;
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
		ListNode<T> currentNode = first;

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
		ListNode<T> currentNode = first;

		while (currentNode != null) {
			if (currentNode.value.equals(value)) {
				return true;
			}
			currentNode = currentNode.next;
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
	private static class LindexListElementsGetter<T> implements ElementsGetter<T> {
		/**
		 * Private filed that keeps the track of the position of the current element.
		 */
		private ListNode<T> currentElement;

		/**
		 * Counts how many modifications has been made.
		 */
		private long savedModificationCount;

		/**
		 * Reference to {@link LinkedListIndexedCollection}.
		 */
		private LinkedListIndexedCollection<T> collection;

		/**
		 * Constructor that initializes field collection.
		 * 
		 * @param collection The collection from which elements will be given.
		 */
		public LindexListElementsGetter(LinkedListIndexedCollection<T> collection) {
			this.collection = collection;
			currentElement = collection.first;
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
			return currentElement != null;
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

			T element = currentElement.value;
			currentElement = currentElement.next;

			return element;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new LindexListElementsGetter<T>(this);
	}
}
