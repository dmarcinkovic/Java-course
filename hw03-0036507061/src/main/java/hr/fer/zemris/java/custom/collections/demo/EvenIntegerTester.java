package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.Tester;

/**
 * Class that is an implementation of Tester. It is demonstration example to
 * test if everything is implemented correctly. 
 * 
 * @author david
 *
 */
public class EvenIntegerTester implements Tester {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean test(Object obj) {
		if (!(obj instanceof Integer))
			return false;
		Integer i = (Integer) obj;
		return i % 2 == 0;
	}

	/**
	 * Method invoked when running the program. It tests whether Tester work
	 * correctly. It tests if given objects are even numbers.
	 * 
	 * @param args Arguments provided via command line. In this example they are not
	 *             used.
	 */
	public static void main(String[] args) {
		Tester t = new EvenIntegerTester();

		System.out.println(t.test("Ivo"));
		System.out.println(t.test(22));
		System.out.println(t.test(3));

	}
}
