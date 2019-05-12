package hr.fer.zemris.java.custom.collections;

/**
 * This interface will model objects that receive an object and examine whether
 * this object is acceptable or not.
 * 
 * @author david
 * @FunctionalInterface
 */
public interface Tester<T> {

	/**
	 * Tests whether given object is acceptable or not.
	 * 
	 * @param objan object to check whether it is acceptable or not.
	 * @return True if object is acceptable, otherwise returns false.
	 */
	boolean test(T obj);
}
