package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Collection of primes. This collection contains first n prime numbers. Prime
 * number is a whole number greater than 1 that cannot be made by multiplying
 * other whole numbers.
 * 
 * @author david
 *
 */
public class PrimesCollection implements Iterable<Integer> {
	private int n;

	/**
	 * Constructor to initialize the number of primes.
	 * 
	 * @param n
	 */
	public PrimesCollection(int n) {
		this.n = n;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<Integer> iterator() {
		return this.new MyIterator();
	}

	/**
	 * Implementation of iterator. Iterates through first n primes. 
	 * @author david
	 *
	 */
	private class MyIterator implements Iterator<Integer> {
		private int index = 0;
		private int lastGeneratedPrime = 2;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return index < n;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @throws NoSuchElementException if the iteration has no more elements.
		 */
		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			index++;
			return getNextPrime();
		}

		/**
		 * Method that returns next prime.
		 * 
		 * @return Next prime.
		 */
		private int getNextPrime() {
			while (!isPrime(lastGeneratedPrime)) {
				lastGeneratedPrime++;
			}
			return lastGeneratedPrime++;
		}

		/**
		 * Checks if given number is prime or not.
		 * 
		 * @param n Number to check for.
		 * @return True if given number is prime.
		 */
		private boolean isPrime(int n) {
			for (int i = 2; i <= Math.sqrt(n); i++) {
				if (n % i == 0) {
					return false;
				}
			}
			return true;
		}

	}

}
