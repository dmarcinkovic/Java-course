package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Shows fractal images. Fractals are derived from Newton-Raphson iteration.
 * Pixels are colored according to the speed of convergence of that point. GUI
 * application was made by Doc.dr.sc. Marko Čupić.
 * 
 * @author david
 *
 */
public class Newton {

	/**
	 * Method that asks user to input complex factors of Complex rooted polynomial.
	 * User must provide at least two such a factors. Then new window is open in
	 * which fractal image is presented.
	 * 
	 * @param args Arguments provided via command line. In this example they are not
	 *             used.
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		List<Complex> roots = new ArrayList<>();
		int index = 1;
		while (true) {
			System.out.print("Root " + index + "> ");
			String complexNumber = scan.nextLine();

			if (complexNumber.equals("done")) {
				break;
			}

			Complex complex = null;
			try {
				complex = parse(complexNumber);
			} catch (NumberFormatException e) {
				System.out.println("Could not parse given complex number.");
				continue;
			}

			roots.add(complex);
			index++;
		}

		if (roots.size() < 2) {
			System.out.println("You did not provide at least two roots. I am going to stop this program.");
			scan.close();
			return;
		}

		ComplexRootedPolynomial polynoimal = new ComplexRootedPolynomial(Complex.ONE, toArray(roots));

		System.out.println("Image of fractal will appear shortly. Thank you.");

		FractalViewer.show(new MyProducer(polynoimal));

		scan.close();
	}

	/**
	 * Convert roots given as list of complex number to array of complex numbers.
	 * 
	 * @param roots Given list of complex numbers.
	 * @return Array of complex numbers.
	 */

	private static Complex[] toArray(List<Complex> roots) {
		Complex[] complexNumbers = new Complex[roots.size()];

		for (int i = 0; i < roots.size(); i++) {
			complexNumbers[i] = roots.get(i);
		}
		return complexNumbers;
	}

	/**
	 * Parse complex number given as string. Complex number must be in form a+ib,
	 * where a and b are real numbers. Also, it is allowed to write only real or
	 * imaginary part, but it is not allowed to provide empty input. For example, -i
	 * will be interpreted as complex number with real part equal to 0 and imaginary
	 * part equal to 1.
	 * 
	 * @param complexNumber Complex number as string.
	 * @return Complex number.
	 * @throws NumberFormatException if could not parse to complex number.
	 */
	private static Complex parse(String complexNumber) {
		if (complexNumber.isEmpty()) {
			throw new NumberFormatException();
		} else if (isPureComplex(complexNumber)) {
			return pureComplex(complexNumber);
		} else if (isPureReal(complexNumber)) {
			return pureReal(complexNumber);
		}

		return complex(complexNumber);
	}

	/**
	 * Returns complex number from String when String is in form a+ib.
	 * 
	 * @param complexNumber Given String.
	 * @return Complex number.
	 * @throws NumberFormatException if could not parse to complex number.
	 */
	private static Complex complex(String complexNumber) {
		complexNumber = complexNumber.trim();
		int indexToSplit = getIndex(complexNumber);

		if (indexToSplit < 0) {
			throw new NumberFormatException();
		}

		double real = getReal(complexNumber.substring(0, indexToSplit));
		double imaginary = getImaginary(complexNumber.substring(indexToSplit));

		return new Complex(real, imaginary);
	}

	/**
	 * Returns index of middle plus or minus.
	 * 
	 * @param complexNumber String representation of complex number.
	 * @return Index of middle '+' or '-';
	 */
	private static int getIndex(String complexNumber) {
		int index1 = complexNumber.lastIndexOf('+');
		int index2 = complexNumber.lastIndexOf('-');

		return Math.max(index1, index2);
	}

	/**
	 * Returns Complex number when only real part of complex number is provided.
	 * 
	 * @param complexNumber Given complex number.
	 * @return Complex number.
	 * @throws NumberFormatException if could not parse given string to real number.
	 */
	private static Complex pureReal(String complexNumber) {
		return new Complex(getReal(complexNumber), 0);
	}

	/**
	 * Returns Complex number when only imaginary part of complex number is
	 * provided.
	 * 
	 * @param complexNumber Given complex number.
	 * @return Complex number.
	 * @throws NumberFormatException if could not parse given string to Complex
	 *                               number.
	 */
	private static Complex pureComplex(String complexNumber) {
		complexNumber = complexNumber.replaceFirst("i", "");

		if (complexNumber.isEmpty() || complexNumber.equals("+") || complexNumber.equals("-")) {
			return new Complex(0, Double.parseDouble(complexNumber + "1"));
		}

		return new Complex(0, Double.parseDouble(complexNumber));
	}

	/**
	 * Checks if Complex number given as string has only real part.
	 * 
	 * @param complexNumber Complex number given as string.
	 * @return True if only real part of complex is provided in input.
	 */
	private static boolean isPureReal(String complexNumber) {
		return !complexNumber.contains("i") && !complexNumber.trim().isEmpty();
	}

	/**
	 * Checks if Complex number given as string has only imaginary part.
	 * 
	 * @param complexNumber Complex number given as string.
	 * @return True if only imaginary part of complex is provided in input.
	 */
	private static boolean isPureComplex(String complexNumber) {
		boolean seenNumber = false;

		for (int i = 0; i < complexNumber.length(); i++) {
			char c = complexNumber.charAt(i);

			if (Character.isDigit(c)) {
				seenNumber = true;
			} else if (c == 'i' && !seenNumber) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns real part of complex number.
	 * 
	 * @param s Complex number given as String.
	 * @return Double representing real part of complex number.
	 */
	private static double getReal(String s) {
		if (s.trim().isEmpty()) {
			return 0;
		}

		return Double.parseDouble(s.trim());
	}

	/**
	 * Returns imaginary part of complex number.
	 * 
	 * @param s Complex number given as String.
	 * @return Double representing imaginary part of complex number.
	 */
	private static double getImaginary(String s) {
		s = s.replaceFirst(" ", "").replaceFirst("\t", "").replaceFirst("i", "");

		if (s.isEmpty() || s.equals("+") || s.equals("-")) {
			s += "1";
		}

		return Double.parseDouble(s);
	}

	/**
	 * Implementation of one task. One task is used to draw 1/8 part of image.
	 * 
	 * @author david
	 *
	 */
	public static class Worker implements Callable<Void> {
		/**
		 * Minimum real part.
		 */
		private double reMin;

		/**
		 * Maximum real part.
		 */
		private double reMax;

		/**
		 * Minimum imaginary part.
		 */
		private double imMin;

		/**
		 * Minimum imaginary part.
		 */
		private double imMax;

		/**
		 * Start y.
		 */
		private int minY;

		/**
		 * End y.
		 */
		private int maxY;

		/**
		 * Width of the window.
		 */
		private int width;

		/**
		 * Height of the window.
		 */
		private int height;

		/**
		 * Polynomial given as complex rooted polynomial.
		 */
		private ComplexRootedPolynomial polynomial;

		/**
		 * Polynomial given as complex polynomial.
		 */
		private ComplexPolynomial polynom;

		/**
		 * Derived polynomial.
		 */
		private ComplexPolynomial derived;

		/**
		 * Array that contains indices of the closest root from given complex number z.
		 */
		private short[] data;

		/**
		 * Max number of iterations.
		 */
		private int m;

		/**
		 * Offset of piece of image.
		 */
		private int offset;

		/**
		 * Initialize private fields.
		 * 
		 * @param reMin      Minimum real part.
		 * @param reMax      Max real part.
		 * @param imMin      Minimum imaginary part.
		 * @param imMax      Max imaginary part.
		 * @param width      Width of the window.
		 * @param height     Height of the window.
		 * @param minY       Start y.
		 * @param maxY       End y.
		 * @param polynomial Polynomial.
		 * @param data       Data.
		 * @param m          Max number of iteration.
		 * @param offset     Offset of piece of image.
		 */
		public Worker(double reMin, double reMax, double imMin, double imMax, int width, int height, int minY, int maxY,
				ComplexRootedPolynomial polynomial, short[] data, int m, int offset) {
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.polynomial = polynomial;
			this.data = data;
			this.m = m;
			this.minY = minY;
			this.maxY = maxY;
			this.offset = offset;
			polynom = polynomial.toComplexPolynom();
			derived = polynom.derive();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Void call() throws Exception {

			double rootTreshold = 2e-3;
			double convergenceTreshold = 1e-3;
			for (int y = minY; y <= maxY; y++) {
				for (int x = 0; x < width; x++) {
					double module = 0;

					double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
					double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;

					Complex c = new Complex(cre, cim);
					Complex zn = c;

					int iters = 0;
					do {
						Complex numerator = polynom.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);

						zn = zn.sub(fraction);
						module = znold.sub(zn).module();

						iters++;
					} while (iters < m && module > convergenceTreshold);
					int index = polynomial.indexOfClosestRootFor(zn, rootTreshold);
					data[offset++] = (short) (index + 1);
				}
			}
			return null;
		}
	}

	/**
	 * Implementation of IFractalProducer. This class have only one method :
	 * produce. This method is ought to split job into smaller jobs and send them to
	 * Worker class.
	 * 
	 * @author david
	 *
	 */
	public static class MyProducer implements IFractalProducer {
		/**
		 * Polynomial given in form : Constant * (z - root1) * (z - root2) ... * (z -
		 * rootN).
		 */
		private ComplexRootedPolynomial polynomial;

		/**
		 * Polynomial given in form z1 * z^n + z2 * z ^ (n-1) ... + z(n-1) * z^1 + zn.
		 */
		private ComplexPolynomial polynom;

		/**
		 * Initialize polynomial.
		 * 
		 * @param polynomial Polynomial given in complex root polynomial form.
		 */
		public MyProducer(ComplexRootedPolynomial polynomial) {
			this.polynomial = polynomial;
			this.polynom = polynomial.toComplexPolynom();
		}

		/**
		 * This method is used to split job into smaller tasks and send them to instance
		 * of Worker. After worker has done with those jobs this method collects all
		 * results and send them graphical user interface.
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("I am starting to calculate...");

			int m = 16 * 16;
			short[] data = new short[width * height];
			final int numberOfTapes = 8;
			int numberOfYByTape = height / numberOfTapes;

			int numberOfCores = Runtime.getRuntime().availableProcessors();
			ExecutorService pool = Executors.newFixedThreadPool(numberOfCores, new DaemonicThreadFactory());
			List<Future<Void>> results = new ArrayList<>();

			for (int i = 0; i < numberOfTapes; i++) {
				if (cancel.get()) {
					break;
				}
				int yMin = i * numberOfYByTape;
				int yMax = (i + 1) * numberOfYByTape - 1;

				if (i == numberOfTapes - 1) {
					yMax = height - 1;
				}

				int offset = yMin * width;
				Worker task = new Worker(reMin, reMax, imMin, imMax, width, height, yMin, yMax, polynomial, data, m,
						offset);
				results.add(pool.submit(task));
			}

			for (Future<Void> task : results) {
				try {
					task.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}

			pool.shutdown();

			System.out.println("Done calculating. I am going to inform observer ie. GUI!");
			observer.acceptResult(data, (short) (polynom.order() + 1), requestNo);
		}

	}

	/**
	 * Implementation of ThreadFactory. ThreadFactory has only one method:
	 * newThread. In this method method new Deamon Thread is created.
	 * 
	 * @author david
	 *
	 */
	public static class DaemonicThreadFactory implements ThreadFactory {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Thread newThread(Runnable r) {
			Thread thread = Executors.defaultThreadFactory().newThread(r);
			thread.setDaemon(true);
			return thread;
		}
	}
}
