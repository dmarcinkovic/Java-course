package hr.fer.zemris.math.demo;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Simple example to check that classes ComplexPolynomial and
 * ComplexRootedPolynomial works correctly.
 * 
 * @author david
 *
 */
public class ComplexPolynomialDemo {

	/**
	 * Method invoked when running the program. This method checks that methods from
	 * ComplexPolynomial and ComplexRootedPolynomial works correctly.
	 * 
	 * @param args Argument provided via command line. In this example they are not
	 *             used.
	 */
	public static void main(String[] args) {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(2, 0), Complex.ONE, Complex.ONE_NEG,
				Complex.IM, Complex.IM_NEG);
		ComplexPolynomial cp = crp.toComplexPolynom();

		System.out.println(crp);
		System.out.println(cp);
		System.out.println(cp.derive());
	}
}
