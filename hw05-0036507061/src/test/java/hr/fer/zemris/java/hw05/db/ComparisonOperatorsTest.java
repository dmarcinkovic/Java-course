package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComparisonOperatorsTest {

	@Test
	void testGreater() {
		IComparisonOperator oper = ComparisonOperators.GREATER;

		assertFalse(oper.satisfied("Ana", "Pero"));
		assertTrue(oper.satisfied("Anamarija", "Ana"));
		assertFalse(oper.satisfied("Zeljko", "Željko"));
	}

	@Test
	public void testLess() {
		IComparisonOperator oper = ComparisonOperators.LESS;

		assertTrue(oper.satisfied("Ana", "Pero"));
		assertFalse(oper.satisfied("Anamarija", "Ana"));
		assertTrue(oper.satisfied("Zeljko", "Željko"));
	}
	
	@Test
	public void testGreaterOfEqual() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		
		assertFalse(oper.satisfied("Ana", "Pero"));
		assertTrue(oper.satisfied("Anamarija", "Ana"));
		assertFalse(oper.satisfied("Zeljko", "Željko"));
		assertTrue(oper.satisfied("FER", "FER"));
	}
	
	@Test
	public void testLessOrEqual() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		
		assertTrue(oper.satisfied("Ana", "Pero"));
		assertFalse(oper.satisfied("Anamarija", "Ana"));
		assertTrue(oper.satisfied("Zeljko", "Željko"));
		assertTrue(oper.satisfied("Fer", "Fer"));
	}
	
	@Test
	public void testEqual() {
		IComparisonOperator oper = ComparisonOperators.EQUALS; 
		
		assertTrue(oper.satisfied("Štefica", "Štefica")); 
		assertFalse(oper.satisfied("Jasna", "Štefica"));
	}
	
	@Test
	public void testNotEqual() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS; 
		
		assertFalse(oper.satisfied("Štefica", "Štefica")); 
		assertTrue(oper.satisfied("Jasna", "Štefica"));
	}
	
	@Test
	public void testLike() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		
		assertFalse(oper.satisfied("Zagreb", "Aba*"));
		assertFalse(oper.satisfied("AAA", "AA*AA"));
		assertTrue(oper.satisfied("AAAA", "AA*AA"));
		assertFalse(oper.satisfied("ABAB", "ABABB"));
		assertTrue(oper.satisfied("ABAA", "ABAA"));
		assertTrue(oper.satisfied("AB", "AB*"));
		assertTrue(oper.satisfied("AB", "*AB"));
		assertTrue(oper.satisfied("ABABA", "*"));
		assertTrue(oper.satisfied("A", "A*"));
		assertTrue(oper.satisfied("B", "*B"));
		assertFalse(oper.satisfied("BAB", "BAB*BAB"));
		assertTrue(oper.satisfied("BABA", "B*ABA"));
		assertFalse(oper.satisfied("", "AB*BA"));
		assertTrue(oper.satisfied("", "*"));
		assertFalse(oper.satisfied("AB", "ABA*"));
		assertFalse(oper.satisfied("BA", "*BABBA"));
	}
	
}
