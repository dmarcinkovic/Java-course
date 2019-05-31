package hr.fer.zemris.java.custom.collections;

/**
 * Class represents a stack collection. Stack is LIFO collection. This means
 * that last inserted element will be popped first. It contains push method to
 * add element in this collection and pop method to remove element. Those
 * methods work in O(1) time complexity.
 * 
 * @author david
 *
 */
public class ObjectStack {
	private ArrayIndexedCollection stack = new ArrayIndexedCollection();

	/**
	 * Method to check if stack contains one or more elements.
	 * 
	 * @return True if stack contains one or more elements.
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	/**
	 * Returns size of the stack.
	 * 
	 * @return Size of the stack.
	 */
	public int size() {
		return stack.size();
	}

	/**
	 * Method to add element to the top of the stack.
	 * 
	 * @param value Value to be pushed in stack.
	 * @throws NullPointerException is value is null.
	 */
	public void push(Object value) {
		stack.add(value);
	}

	/**
	 * Returns peek of the stack (the last inserted element) and deletes it.
	 * 
	 * @return Peek of the stack (The last inserted element).
	 * @throws EmptyStackException if stack is empty.
	 */
	public Object pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}

		Object element = stack.get(size() - 1);
		stack.remove(size() - 1);
		return element;
	}

	/**
	 * Returns peek of the stack (the last inserted element).
	 * 
	 * @return Peek of the stack (The last inserted element).
	 * @throws EmptyStackException if stack is empty.
	 */
	public Object peek() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}

		Object element = stack.get(size() - 1);
		return element;
	}

	/**
	 * Removes all the elements from stack.
	 */
	public void clear() {
		stack.clear();
	}
}
