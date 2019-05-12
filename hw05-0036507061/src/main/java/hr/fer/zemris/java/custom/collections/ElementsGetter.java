package hr.fer.zemris.java.custom.collections;

import java.util.NoSuchElementException;

/**
 * Interface that represents an object whose task is to retract element by
 * element when the user requests it.
 * 
 * @author david
 *
 */
public interface ElementsGetter<T> {
	/**
	 * Returns true if the collection has more elements.
	 * 
	 * @return True if there is more elements in the collection.
	 * @throws ConcurrentModificationException when collection has been modified.
	 */
	boolean hasNextElement();

	/**
	 * Method to return next element from the collection.
	 * 
	 * @return Next element from the collection.
	 * @throws NoSuchElementException          when there is no more elements in the
	 *                                         collection.
	 * @throws ConcurrentModificationException when collection has been modified.
	 */
	T getNextElement();

	/**
	 * Default method that calls given Processor p for every remaining element.
	 * 
	 * @param p Processor that define action.
	 */
	default void processRemaining(Processor<T> p) {
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}

}
