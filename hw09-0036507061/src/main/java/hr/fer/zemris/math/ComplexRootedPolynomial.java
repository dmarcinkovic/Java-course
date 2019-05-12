package hr.fer.zemris.math;

/**
 * Represents complex rooted polynomial. Complex rooted polynomial is complex
 * polynomial in which roots are easy to find. For example, (z - (1-2i))*(z +
 * 4). In that example roots are: (1-2i) and -4.
 * 
 * @author david
 *
 */
public class ComplexRootedPolynomial {
	private Complex constant;
	private Complex[] roots;

	/**
	 * Initialize constant and roots.
	 * 
	 * @param constant Constant of the expression. For example, if complex rooted
	 *                 polynomial is 2*(z-1)*(z+1) constant is 2.
	 * @param roots    Roots of given complex rooted number. For example, if complex
	 *                 rooted polynomial is 2*(z-1)*(z+1) roots are 1 and -1.
	 * 
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.constant = constant;
		this.roots = roots;
	}

	/**
	 * Computes polynomial value at given point z.
	 * 
	 * @param z Given point.
	 * @return Computed polynomial value at given point z.
	 */
	public Complex apply(Complex z) {
		ComplexPolynomial complexPolynomial = toComplexPolynom();

		return complexPolynomial.apply(z);
	}

	/**
	 * Converts this representation to ComplexPolynomial type.
	 * 
	 * @return Converted ComplexPolynomial.
	 */
	public ComplexPolynomial toComplexPolynom() {
		Complex[] factors = new Complex[roots.length + 1];

		for (int i = 0; i < factors.length; i++) {
			factors[i] = new Complex();
		}

		getComplexFactors(factors, 0, 0, Complex.ONE);

		for (int i = 0; i < factors.length; i++) {
			factors[i] = factors[i].multiply(constant);
		}

		return new ComplexPolynomial(factors);
	}

	/**
	 * Method to recursively find all complex factors.
	 * 
	 * @param factors Array in which factors are stored.
	 * @param power   Current power.
	 * @param index   Current index.
	 * @param number  Complex number that is used to compute final factor.
	 */
	private void getComplexFactors(Complex[] factors, int power, int index, Complex number) {
		if (index >= roots.length) {
			factors[power] = factors[power].add(number);
			return;
		}

		getComplexFactors(factors, power + 1, index + 1, number);
		number = number.multiply(roots[index].negate());
		getComplexFactors(factors, power, index + 1, number);
	}

	/**
	 * Converts complex rooted polynomial to string.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(constant);

		sb.append("*");

		for (int i = 0; i < roots.length; i++) {
			sb.append("(z-");
			sb.append(roots[i]);
			sb.append(")");

			if (i != roots.length - 1) {
				sb.append("*");
			}
		}

		return sb.toString();
	}

	/**
	 * Finds index of closest root for given complex number z that is within
	 * treshold; if there is no such root, returns -1 first root has index 0, second
	 * index 1, etc.
	 * 
	 * @param z        Given complex number z.
	 * @param treshold Given threshold.
	 * @return index of closest root for given complex number z that is within
	 *         treshold.
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) { // TODO : fix this
		int index = -1;
		double minDistance = treshold;

		for (int i = 0; i < roots.length; i++) {
			Complex temp = z.sub(roots[i]);
			
			if (temp.module() < minDistance) {
				index = i;
				minDistance = temp.module();
			}
		}
		return index;
	}
}
