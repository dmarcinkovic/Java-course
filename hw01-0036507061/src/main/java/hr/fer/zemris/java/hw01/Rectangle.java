package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program that calculates perimeter and area of rectangle. If width and height
 * are correctly set via the command line it calculates area and perimeter based
 * on that arguments. Otherwise it allows user to input width and height from
 * keyboard.
 * 
 * @author david
 *
 */
public class Rectangle {

	/**
	 * Method invoked when running the program. Arguments explained below.
	 * 
	 * @param args Arguments given from command line. If two arguments are provided
	 *             correctly it calculates area and perimeter based on that
	 *             arguments.
	 * 
	 */
	public static void main(String[] args) {
		if (args.length != 2 && args.length != 0) {
			System.out.println("Broj argumenata naredbenog retka je različit od 0 i od 2, završavam program.");
			return;
		}

		if (args.length == 2) {
			try {
				double width = Double.parseDouble(args[0]);
				double height = Double.parseDouble(args[1]);

				if (width >= 0 && height >= 0) {
					double perimeter = perimeter(width, height);
					double area = area(width, height);

					System.out.println("Pravokutnik širine " + width + " i visine " + height + " ima površinu " + area
							+ " te opseg " + perimeter + ".");
				} else {
					System.out.println("Unijeli ste negativnu vrijednost putem naredbenog retka.");
				}

			} catch (NumberFormatException e) {
				System.out.println("Ulazni argumenti se ne mogu protumačiti kao broj.");
			}
			return;
		}
		Scanner sc = new Scanner(System.in);

		double width = getInput("širinu", sc);
		double height = getInput("visinu", sc);

		double area = area(width, height);
		double perimeter = perimeter(width, height);

		System.out.println("Pravokutnik širine " + width + " i visine " + height + " ima površinu " + area
				+ " te opseg " + perimeter + ".");

		sc.close();
	}

	/**
	 * Method to get input from user based on inputType.
	 * 
	 * @param inputType String that specifies input type. It can be width or height.
	 * @param sc        Scanner to input data from keyboard.
	 * @return width or height based on inputType.
	 */
	private static double getInput(String inputType, Scanner sc) {

		while (true) {
			System.out.print("Unesite " + inputType + "  > ");
			String input = sc.next();

			try {
				double result = Double.parseDouble(input);

				if (result < 0) {
					System.out.println("Unijeli ste negativnu vrijednost.");
					continue;
				}

				return result;
			} catch (NumberFormatException e) {
				System.out.printf("'%s' se ne može protumačiti kao broj.%n", input);
			}

		}
	}

	/**
	 * Method that calculates perimeter of rectangle with given width and height.
	 * Formula for perimeter is 2 * (width + height).
	 * 
	 * @param width  Width of rectangle.
	 * @param height Height of rectangle.
	 * @return Perimeter of rectangle.
	 */
	public static double perimeter(double width, double height) {
		return 2 * (width + height);
	}

	/**
	 * Method that calculates area of rectangle with given width and height. Formula
	 * for calculating area is width * height.
	 * 
	 * @param width  Width of rectangle.
	 * @param height Height of rectangle.
	 * @return Area of rectangle.
	 */
	public static double area(double width, double height) {
		return width * height;
	}

}
