package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * JUnit test cases to test method factorial from class Factorial.
 * 
 * @author david
 *
 */
class FactorialTest {

	@Test
	public void testForNegativenNumber() {
		assertThrows(IllegalArgumentException.class, () -> {
			@SuppressWarnings("unused")
			long result = Factorial.factorial(-1);
		});

	}

	@Test
	public void testFor0() {
		long result = Factorial.factorial(0);
		assertEquals(result, 1L);
	}

	@Test
	public void testFor20() {
		long result = Factorial.factorial(20);
		assertEquals(result, 2432902008176640000L);
	}

	@Test
	public void testForHugeNumber() {
		assertThrows(IllegalArgumentException.class, () -> {
			@SuppressWarnings("unused")
			long result = Factorial.factorial(21);
		});
	}

	@Test
	public void testForVeryHugeNumber() {
		assertThrows(IllegalArgumentException.class, () -> {
			@SuppressWarnings("unused")
			long result = Factorial.factorial(12345678);
		});
	}

	@Test
	public void testForNumberInRange() {
		long result = Factorial.factorial(7);
		assertEquals(result, 5040L);
	}
}
