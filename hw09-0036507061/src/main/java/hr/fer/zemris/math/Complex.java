package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents complex number. Complex number is defined with its real
 * and imaginary part. A complex number can be expressed as a + bi, where i is
 * square root of -1 and a and b are real and imaginary parts, respectively.
 * 
 * @author david
 *
 */
public class Complex {

	/**
	 * Real part of the complex number.
	 */
	private double real;

	/**
	 * Imaginary part of the complex number.
	 */
	private double imaginary;

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Default constructor. Sets real and imaginary part to 0.
	 */
	public Complex() {
		real = 0;
		imaginary = 0;
	}

	/**
	 * Constructor that excepts real and imaginary part of the complex number.
	 * 
	 * @param re Real part of the complex number.
	 * @param im Imaginary part of the complex number.
	 */
	public Complex(double re, double im) {
		real = re;
		imaginary = im;
	}

	/**
	 * Returns real part of the complex number.
	 * 
	 * @return Real part of the complex number.
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Returns imaginary part of the complex number.
	 * 
	 * @return Imaginary part of the complex number.
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Returns module of complex number.
	 * 
	 * @return Module of complex number.
	 */
	public double module() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Performs multiplication between this complex number and given one. This
	 * complex number is multiplicand and given complex number c is multiplier.
	 * 
	 * @param c Multiplier.
	 * @return Complex number after performing multiplication.
	 * @throws NullPointerException if c is null.
	 */
	public Complex multiply(Complex c) {
		if (c == null) {
			throw new NullPointerException();
		}
		double real = this.real * c.real - this.imaginary * c.imaginary;
		double imaginary = this.imaginary * c.real + this.real * c.imaginary;
		return new Complex(real, imaginary);
	}

	/**
	 * Performs division between this complex number and given one. Dividend is this
	 * number and divisor is given complex number c.
	 * 
	 * @param c Divisor.
	 * @return Complex number after performing division.
	 * @throws NullPointerException if c is null.
	 * @throws ArithmeticException  when dividing by zero.
	 */
	public Complex divide(Complex c) {
		if (c == null) {
			throw new NullPointerException();
		}
		
		double real = this.real * c.real + this.imaginary * c.imaginary;
		double imaginary = this.imaginary * c.real - this.real * c.imaginary;
		double denominator = c.real * c.real + c.imaginary * c.imaginary;

		if (denominator == 0) {
			throw new ArithmeticException();
		}

		return new Complex(real / denominator, imaginary / denominator);
	}

	/**
	 * Performs addition between this complex number and given one.
	 * 
	 * @param c Addend.
	 * @return Complex number after performing addition.
	 * @throws NullPointerException is c is null.
	 */
	public Complex add(Complex c) {
		if (c == null) {
			throw new NullPointerException();
		}
		return new Complex(real + c.real, imaginary + c.imaginary);
	}

	/**
	 * Performs subtraction between this complex number and given one. This complex
	 * number is minuend and given complex number c is subtrahend.
	 * 
	 * @param c Subtrahend.
	 * @return Complex number after performing subtraction.
	 * @throws NullPointerException if c is null.
	 */
	public Complex sub(Complex c) {
		if (c == null) {
			throw new NullPointerException();
		}
		return new Complex(real - c.real, imaginary - c.imaginary);
	}

	/**
	 * Return negate of this imaginary number.
	 * 
	 * @return Negate of this imaginary number.
	 */
	public Complex negate() {
		return new Complex(-real, -imaginary);
	}

	/**
	 * Method to calculate nth power of the complex number. Result is calculated
	 * using De Moivre's formula.
	 * 
	 * @param n The power to be calculated.
	 * @return Nth power of complex number.
	 * @throws IllegalArgumentException when n is less than zero.
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}
		double magnitude = module();
		double angle = getAngle();
		double real = Math.pow(magnitude, n) * Math.cos(angle * n);
		double imaginary = Math.pow(magnitude, n) * Math.sin(angle * n);
		return new Complex(real, imaginary);
	}

	/**
	 * Method to calculate nth root of the complex number. Result is calculated
	 * using De Moivre's formula.
	 * 
	 * @param n The root to be calculated.
	 * @return Nth root of complex number.
	 * @throws IllegalArgumentException when n is less or equal to 0.
	 */
	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		double magnitude = module();
		double angle = getAngle();

		List<Complex> roots = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			double real = Math.pow(magnitude, 1. / n) * Math.cos((angle + 2 * i * Math.PI) / n);
			double imaginary = Math.pow(magnitude, 1. / n) * Math.sin((angle + 2 * i * Math.PI) / n);
			roots.add(new Complex(real, imaginary));
		}
		return roots;
	}

	/**
	 * Method to convert complex number to string.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(20);

		sb.append("(");
		sb.append(real);

		if (imaginary < 0) {
			sb.append("-");
		} else {
			sb.append("+");
		}

		sb.append("i").append(Math.abs(imaginary));

		sb.append(")");

		return sb.toString();
	}

	/**
	 * Method that returns angle of complex number in radian. Angle is defined as
	 * angle between real-axes and direction that connects this point with origin
	 * (number 0).
	 * 
	 * @return
	 */
	private double getAngle() {
		double angle = 0;

		if (real == 0) {
			angle = Math.signum(imaginary) * Math.PI / 2;
		} else {
			angle = Math.atan(imaginary / real);
			if (real < 0) {
				angle += Math.PI;
			}
		}

		if (angle < 0) {
			angle += 2 * Math.PI;
		}
		return angle;
	}
}