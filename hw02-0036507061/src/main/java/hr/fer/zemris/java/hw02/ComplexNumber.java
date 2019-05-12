package hr.fer.zemris.java.hw02;

import java.util.Arrays;

/**
 * Class that represents complex number. Complex number is defined with its real
 * and imaginary part. A complex number can be expressed as a + bi, where i is
 * square root of -1 and a and b are real and imaginary parts, respectively.
 * 
 * @author david
 *
 */
public class ComplexNumber {
	/**
	 * Real part of the complex number.
	 */
	private final double real;

	/**
	 * Imaginary part of the complex number.
	 */
	private final double imaginary;

	/**
	 * Constructor that excepts real and imaginary part of the complex number.
	 * 
	 * @param real      Real part of the complex number.
	 * @param imaginary Imaginary part of the complex number.
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Static method that creates ComplexNuber from given real number. It it
	 * supposed that imaginary part of complex number is zero.
	 * 
	 * @param real Real part of the complex number.
	 * @return new ComplexNumber created from given real part.
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Static method that creates ComplexNuber from given imaginary number. It it
	 * supposed that real part of complex number is zero.
	 * 
	 * @param imaginary Imaginary part of the complex number.
	 * @return new ComplexNumber created from given imaginary part.
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Static method that creates ComplexNumber from given magnitude and angle.
	 * 
	 * @param magnitude Distance from origin.
	 * @param angle     Angle of complex number in radians. Angle is defined as
	 *                  angle between real-axes and direction that connects this
	 *                  point with origin (number 0).
	 * @return new ComplexNumber created from given magnitude and angle.
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		double real = magnitude * Math.cos(angle);
		double imaginary = magnitude * Math.sin(angle);

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Method that parses given String to complex number.
	 * 
	 * @param s String to be parsed to complex number.
	 * @return Complex number given as String.
	 * @throws NullPointerException  if String s is null.
	 * @throws NumberFormatException is String s cannot be parsed into
	 *                               ComplexNumber.
	 */
	public static ComplexNumber parse(String s) {
		if (s == null) {
			throw new NullPointerException();
		}

		/*
		 * Get rid of the leading and trailing spaces.
		 */
		s = s.trim();
		char[] array = s.toCharArray();

		double real = 0;
		double imaginary = 0;

		int start = 0;
		int end = -1;
		boolean seenDigit = false;
		for (int i = 0; i < array.length; i++) {
			/**
			 * If character is not one of 'i', '+', '-' or digit throw an exception.
			 */
			if (!Character.isDigit(array[i]) && array[i] != 'i' && array[i] != '.' && array[i] != '+'
					&& array[i] != '-') {
				throw new NumberFormatException();
			}

			if (Character.isDigit(array[i])) {
				seenDigit = true;
			}

			/*
			 * If any digit is already seen and now you found '+', '-' or 'i' this means you
			 * found whole number. For example, +3.14-3i, so when you see '-' you know that
			 * you found real part of the number, but when you see '+' you know that is the
			 * sign of the number.
			 */
			if (seenDigit && (array[i] == '+' || array[i] == '-' || array[i] == 'i')) {
				end = i;
				if (array[i] == 'i') {
					imaginary = getNumber(s, start, end, true);

					if (i != array.length - 1) {
						throw new NumberFormatException();
					}
				} else {
					real = getNumber(s, start, end, false);
				}
				start = i;
				seenDigit = false;
			} else if (array[i] == 'i') {
				end = i;

				if (i != array.length - 1) {
					throw new NumberFormatException();
				}
				imaginary = getNumber(s, start, end, true);
			}
		}

		/*
		 * Case when given number consists only from real part.
		 */
		if (Character.isDigit(array[array.length - 1])) {
			real = getNumber(s, 0, array.length, false);
		}

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Helping method to convert part of the String to it's double value.
	 * 
	 * @param s           String to be converted.
	 * @param start       Starting index of part of the String to be converted.
	 * @param end         Ending index of the part of the String to be converted.
	 * @param isImaginary Boolean value to tell if the number we are converting is
	 *                    imaginary or not.
	 * @return Double value of the given part of the String.
	 * @throws NullPointerException  if String s is null.
	 * @throws NumberFormatException if number cannot be parsed from String to
	 *                               double.
	 */
	private static double getNumber(String s, int start, int end, boolean isImaginary) {
		if (s == null) {
			throw new NullPointerException();
		}

		String number = s.substring(start, end);
		char[] array = number.toCharArray();
		char[] result = new char[number.length() + 1];
		int index = 0;

		boolean seenDigit = false;
		for (Character c : array) {
			if (c == ' ') {
				continue;
			}
			result[index++] = c;
			if (Character.isDigit(c)) {
				seenDigit = true;
			}
		}

		if (seenDigit == false && isImaginary == false) {
			throw new NumberFormatException();
		} else if (seenDigit == false) {
			result[index++] = '1';
		}

		String num = new String(Arrays.copyOf(result, index));
		return Double.parseDouble(num);
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
	 * Return distance from origin.
	 * 
	 * @return Distance from origin.
	 */
	public double getMagnitude() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Method that returns angle of complex number in radian. Angle is defined as
	 * angle between real-axes and direction that connects this point with origin
	 * (number 0).
	 * 
	 * @return
	 */
	public double getAngle() {
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

	/**
	 * Performs addition between this complex number and given one.
	 * 
	 * @param c Addend.
	 * @return Complex number after performing addition.
	 * @throws NullPointerException is c is null.
	 */
	public ComplexNumber add(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException();
		}
		return new ComplexNumber(real + c.getReal(), imaginary + c.getImaginary());
	}

	/**
	 * Performs subtraction between this complex number and given one. This complex
	 * number is minuend and given complex number c is subtrahend.
	 * 
	 * @param c Subtrahend.
	 * @return Complex number after performing subtraction.
	 * @throws NullPointerException if c is null.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException();
		}
		return new ComplexNumber(real - c.getReal(), imaginary - c.getImaginary());
	}

	/**
	 * Performs multiplication between this complex number and given one. This
	 * complex number is multiplicand and given complex number c is multiplier.
	 * 
	 * @param c Multiplier.
	 * @return Complex number after performing multiplication.
	 * @throws NullPointerException if c is null.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException();
		}
		double real = this.real * c.getReal() - this.imaginary * c.getImaginary();
		double imaginary = this.imaginary * c.getReal() + this.real * c.getImaginary();
		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Performs division between this complex number and given one. Dividend is this
	 * number and divisor is given complex number c.
	 * 
	 * @param c Divisor.
	 * @return Complex number after performing division.
	 * @throws NullPointerException if c is null.
	 * @throws ArithmeticException when dividing by zero.
	 */
	public ComplexNumber div(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException();
		}
		double real = this.real * c.getReal() + this.imaginary * c.getImaginary();
		double imaginary = this.imaginary * c.getReal() - this.real * c.getImaginary();
		double denominator = c.getReal() * c.getReal() + c.getImaginary() * c.getImaginary();
		
		if (denominator == 0) {
			throw new ArithmeticException();
		}
		
		return new ComplexNumber(real / denominator, imaginary / denominator);
	}

	/**
	 * Method to calculate nth power of the complex number. Result is calculated
	 * using De Moivre's formula.
	 * 
	 * @param n The power to be calculated.
	 * @return Nth power of complex number.
	 * @throws IllegalArgumentException when n is less than zero.
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}
		double magnitude = getMagnitude();
		double angle = getAngle();
		double real = Math.pow(magnitude, n) * Math.cos(angle * n);
		double imaginary = Math.pow(magnitude, n) * Math.sin(angle * n);
		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Method to calculate nth root of the complex number. Result is calculated
	 * using De Moivre's formula.
	 * 
	 * @param n The root to be calculated.
	 * @return Nth root of complex number.
	 * @throws IllegalArgumentException when n is less or equal to 0.
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		
		double magnitude = getMagnitude();
		double angle = getAngle();
		
		ComplexNumber[] roots = new ComplexNumber[n];
		for (int i = 0; i < n; i++) {
			double real = Math.pow(magnitude, 1. / n) * Math.cos((angle + 2 * i * Math.PI) / n);
			double imaginary = Math.pow(magnitude, 1. / n) * Math.sin((angle + 2 * i * Math.PI) / n);
			roots[i] = new ComplexNumber(real, imaginary);
		}
		return roots;
	}

	/**
	 * Method to convert complex number to string.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(20);

		if (real == 0 && imaginary == 0) {
			sb.append("0.0");
		} else if (real == 0) {
			if (imaginary == 1) {
				sb.append("i");
			} else if (imaginary == -1) {
				sb.append("-i");
			} else {
				sb.append(Double.toString(imaginary)).append("i");
			}
		} else if (imaginary == 0) {
			sb.append(Double.toString(real));
		} else {
			sb.append(Double.toString(real));

			if (imaginary < 0) {
				sb.append(" - ");
			} else {
				sb.append(" + ");
			}

			if (Math.abs(imaginary) != 1) {
				sb.append(Double.toString(Math.abs(imaginary)));
			}
			sb.append("i");
		}

		return sb.toString();
	}
}
