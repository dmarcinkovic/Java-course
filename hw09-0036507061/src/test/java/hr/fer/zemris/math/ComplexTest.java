package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

class ComplexTest {
	private double E = 1e-6;

	@Test
	public void testModule() {
		Complex c1 = new Complex();

		assertEquals(c1.module(), 0, E);

		Complex c2 = new Complex(-3, 4);

		assertEquals(c2.module(), 5, E);
	}

	@Test
	public void testMultiply() {
		Complex c1 = new Complex(-2, -1);
		Complex c2 = new Complex(-1, 3);

		Complex c3 = c1.multiply(c2);

		assertEquals(c3.getImaginary(), -5, E);
		assertEquals(c3.getReal(), 5, E);
	}

	@Test
	public void testDivide() {
		Complex c1 = new Complex(-2, -1);
		Complex c2 = new Complex(-1, 3);

		Complex c3 = c1.divide(c2);

		assertEquals(c3.getImaginary(), 7. / 10, E);
		assertEquals(c3.getReal(), -1. / 10, E);

		Complex c4 = new Complex();

		assertThrows(ArithmeticException.class, () -> c3.divide(c4));
	}
	
	@Test
	public void testAdd() {
		Complex c1 = new Complex(-2, -1);
		Complex c2 = new Complex(-1, 3);
		
		Complex c3 = c1.add(c2);
		
		assertEquals(c3.getImaginary(), 2, E);
		assertEquals(c3.getReal(), -3, E);
	}
	
	@Test
	public void testSub() {
		Complex c1 = new Complex(-2, -1);
		Complex c2 = new Complex(-1, 3);
		
		Complex c3 = c1.sub(c2);
		
		assertEquals(c3.getImaginary(), -4, E);
		assertEquals(c3.getReal(), -1, E);
	}
	
	@Test
	public void testNegate() {
		Complex c1 = new Complex(3,0);
		
		c1 = c1.negate();
		
		assertEquals(c1.getImaginary(), 0, E);
		assertEquals(c1.getReal(), -3, E);
	}
	
	@Test
	public void testPow() {
		Complex c = new Complex(3,4);
		
		c = c.power(3);
		
		assertEquals(c.getImaginary(), 44, E);
		assertEquals(c.getReal(), -117, E);
	}
	
	@Test
	public void testRoot() {
		Complex c = new Complex(-117, 44);
		
		List<Complex> list = c.root(3);
		
		assertEquals(list.get(0).getImaginary(), 4, E);
		assertEquals(list.get(0).getReal(), 3, E);
	}
	
	
}
