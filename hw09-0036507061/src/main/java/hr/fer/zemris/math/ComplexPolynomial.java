package hr.fer.zemris.math;

import java.util.Arrays;

/**
 * Represents one complex polynomial. For example, (7+2i)z^3+2z^2+5z+1 is
 * complex polynomial with factors (7 + 2i), 2, 5, 1.
 * 
 * @author david
 *
 */
public class ComplexPolynomial {

	/**
	 * Stores factors of the polynomial.
	 */
	private Complex[] factors;

	/**
	 * Constructor to initialize factors. Factors are in complex domain. For
	 * example. if a complex polynomial is (7+2i)z^3+2z^2+5z+1 factors are : (7 +
	 * 2i), 2, 5, 1. Factors should be ordered ascending by power. So already given
	 * example will be passed to this constructor like : 1, 5, 2, 7 + 2i.
	 * 
	 * @param factors Factors of polynomial.
	 */
	public ComplexPolynomial(Complex... factors) {
		int index = factors.length - 1;

		while (factors[index].getImaginary() == 0 && factors[index].getReal() == 0) {
			index--;
		}

		this.factors = Arrays.copyOf(factors, index + 1);
	}

	/**
	 * Returns the order of the polynomial. For example, for (7+2i)z^3+2z^2+5z+1
	 * returns 3.
	 * 
	 * @return The order od the polynomial.
	 */
	public short order() {
		return (short) ((short) factors.length - 1);
	}

	/**
	 * Computes a new polynomial this * p.
	 * 
	 * @param p Multiplier.
	 * @return Computed new polynomial this * p.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] newFactors = new Complex[order() + p.order() + 1];

		for (int i = 0; i < newFactors.length; i++) {
			newFactors[i] = new Complex();
			for (int j = 0; j < i + 1; j++) {
				if (j < p.factors.length && i-j < this.factors.length) {
					Complex temp = p.factors[j].multiply(this.factors[i-j]);
					newFactors[i] = newFactors[i].add(temp);
				}
			}
		}

		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Computes first derivative of this polynomial. For example, for
	 * (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5.
	 * 
	 * @return First derivative of this polynomial.
	 */
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[factors.length - 1];

		for (int i = 1; i < factors.length; i++) {
			newFactors[i - 1] = factors[i].multiply(new Complex(i, 0));
		}

		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Computes polynomial value at given point z.
	 * 
	 * @param z Given point z.
	 * @return Computed polynomial value at given point z.
	 */
	public Complex apply(Complex z) {
		Complex result = new Complex();

		for (int i = 0; i < factors.length; i++) {
			Complex temp = z.power(i);
			temp = temp.multiply(factors[i]);

			result = result.add(temp);
		}

		return result;
	}

	/**
	 * Returns string that represents this complex polynomial.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = factors.length - 1; i >= 0; i--) {

			sb.append(factors[i]);

			if (i != 0) {
				sb.append("*z^").append(i);
				sb.append("+");
			}

		}

		return sb.toString();
	}
}
