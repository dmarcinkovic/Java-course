package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.ComplexNumber;

/**
 * Class to represent behavior of ComplexNumber class. It calculates simple
 * expression.
 * 
 * @author david
 *
 */
public class ComplexDemo {

	/**
	 * Method invoked when starting program.
	 * 
	 * @param args Arguments given via command line. In this program they are not
	 *             used.
	 */
	public static void main(String[] args) {

		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");

		/*
		 * There is of course two solutions to this expression (because we are taking
		 * second root of the number). One of this solution is printed below.
		 */
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];

		System.out.println(c3);
	}

}
