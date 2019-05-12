package hr.fer.zemris.java.hw05.db.demo;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.IComparisonOperator;

/**
 * Demo example to test ComparisonOperator class. 
 * @author david
 *
 */
public class ComparisonOperatorDemo {
	
	/**
	 * Method invoked when running the program. 
	 * @param args Arguments provided via command line. In this program they are not used.
	 */
	public static void main(String[] args) {
		IComparisonOperator op = ComparisonOperators.LESS;
		System.out.println(op.satisfied("Ana", "Jasna")); // true, since Ana < Jasna
		
		IComparisonOperator oper = ComparisonOperators.LIKE;
		System.out.println(oper.satisfied("Zagreb", "Aba*")); // false
		System.out.println(oper.satisfied("AAA", "AA*AA")); // false
		System.out.println(oper.satisfied("AAAA", "AA*AA")); // true
		System.out.println(oper.satisfied("ABAB", "ABABB")); // false
		System.out.println(oper.satisfied("ABAA", "ABAA")); // true
		System.out.println(oper.satisfied("AB", "AB*")); // true
		System.out.println(oper.satisfied("AB", "*AB")); // true
	}
}
