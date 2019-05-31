package hr.fer.zemris.java.custom.collections;

/**
 * The Processor is a model of an object capable of performing some operation on
 * the passed object.
 * 
 * @author david
 * @FunctionalInterface
 */
public interface Processor {

	/**
	 * This method performs some action with given value.
	 * 
	 * @param value Value on which action will be performed.
	 */
	public void process(Object value);
}
