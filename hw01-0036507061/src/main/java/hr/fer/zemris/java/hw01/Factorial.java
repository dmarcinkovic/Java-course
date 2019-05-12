package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program that calculates a factorial of number ranging from 3 to 20. Number is
 * given in standard input. Program can be terminated when 'kraj' is given as
 * input.
 * 
 * @author david
 *
 */
public class Factorial {

	/**
	 * Method invoked when running the program. Arguments explained below.
	 * 
	 * @param args Arguments given from command line. In this program they are not
	 *             used.
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.print("Unesite broj > ");
			String input = sc.nextLine();

			if (input.equals("kraj")) {
				break;
			}

			try {
				long number = Long.parseLong(input);

				if (number < 3 || number > 20) {
					System.out.printf("'%d' nije broj u dozvoljenom rasponu.%n", number);
					continue;
				}

				System.out.printf("%d! = %d%n", number, factorial((int) number));

			} catch (NumberFormatException e) {
				System.out.printf("'%s' nije cijeli broj.%n", input);
			}

		}

		sc.close();
		System.out.println("Doviđenja.");
	}

	/**
	 * Method that calculates factorial of given number. Factorial is calculated by
	 * formula : n! = (n-1) * (n-2) * ... * 2 * 1. For example : factorial of number
	 * 5 is 5 * 4 * 3 * 2 * 1.
	 * 
	 * @param number Number for which to calculate factorial.
	 * @return Factorial of given number.
	 * @throws IllegalArgumentException when given number is negative or greater
	 *                                  than 20 (20! factorial does not fit in java
	 *                                  long type).
	 */
	public static long factorial(int number) {
		long result = 1;

		if (number < 0) {
			throw new IllegalArgumentException("Number is negative.");
		} else if (number > 20) {
			throw new IllegalArgumentException("Number does not fit in long.");
		}

		for (int i = 1; i <= number; i++) {
			result *= i;
		}

		return result;
	}

}
