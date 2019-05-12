package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexRootedPolynomialTest {
	private double E = 1e-6;
	
	@Test
	public void testToComplexPolynom() {
		ComplexRootedPolynomial c = new ComplexRootedPolynomial(new Complex(2,0), new Complex(4,5), new Complex(6,1));
		
		ComplexPolynomial c1 = c.toComplexPolynom();
		
		assertEquals(c1.toString(), "(2.0+i0.0)*z^2+(-20.0-i12.0)*z^1+(38.0+i68.0)");
	}
	
	
	@Test
	public void testApply() {
		ComplexRootedPolynomial c = new ComplexRootedPolynomial(new Complex(2,0), new Complex(4,5), new Complex(6,1));
		
		Complex c1 = c.apply(new Complex(4, -1));
		assertEquals(c1.getReal(), -24, E);
		assertEquals(c1.getImaginary(), 24, E);
		
		Complex c2 = c.apply(new Complex());
		
		assertEquals(c2.getReal(), 38, E);
		assertEquals(c2.getImaginary(), 68, E);
	}
	
	@Test
	public void testIndexOfClosestRootFor() {
		ComplexRootedPolynomial c = new ComplexRootedPolynomial(new Complex(2,0), new Complex(4,5), new Complex(6,1));
		
		int index = c.indexOfClosestRootFor(new Complex(), Math.sqrt(40));
		
		assertEquals(index, 1);
		
		index = c.indexOfClosestRootFor(new Complex(), 1);
		
		assertEquals(index, -1);
	}
}
