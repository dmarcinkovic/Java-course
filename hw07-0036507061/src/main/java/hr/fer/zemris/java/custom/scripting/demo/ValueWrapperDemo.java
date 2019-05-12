package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

/**
 * ValueWrapper example. This class is used to test ValueWrapper class.
 * 
 * @author david
 *
 */
public class ValueWrapperDemo {

	/**
	 * Invoked when running the program. Test few ValueWrapper methods.
	 * 
	 * @param args Arguments provided via command line. In this example they are not
	 *             used.
	 */
	public static void main(String[] args) {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);

		v1.add(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.

		System.out.println(v1.getValue()); // Integer(0)
		System.out.println(v2.getValue()); // null

		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));

		v3.add(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).

		System.out.println(v3.getValue()); // Double(13.0)
		System.out.println(v4.getValue()); // Integer(1)

		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));

		v5.add(v6.getValue()); // v5 now stores Integer(13); v6 still stores Integer(1).

		System.out.println(v5.getValue()); // Integer(13)
		System.out.println(v6.getValue()); // Integer(1)

		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));

		v7.add(v8.getValue()); // throws RuntimeException
	}
}
