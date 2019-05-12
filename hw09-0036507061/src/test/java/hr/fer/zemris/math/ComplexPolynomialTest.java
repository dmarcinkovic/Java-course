package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexPolynomialTest {
	private double E = 1e-6;
	
	@Test
	public void testOrder() {
		ComplexPolynomial c = new ComplexPolynomial(Complex.ZERO, Complex.IM, Complex.IM_NEG); 
		
		assertEquals(c.order(), 2);
		
		ComplexPolynomial c2 = new ComplexPolynomial(Complex.ZERO, Complex.IM, Complex.IM_NEG, Complex.ZERO);
		
		assertEquals(c2.order(), 2);
		
		ComplexPolynomial c3 = new ComplexPolynomial(Complex.ZERO, Complex.IM, Complex.IM_NEG, Complex.ZERO, Complex.ZERO);
		
		assertEquals(c3.order(), 2);
	}
	
	@Test
	public void testMultiply() {
		ComplexPolynomial c1 = new ComplexPolynomial(new Complex(1, 0), new Complex(2, 3));
		ComplexPolynomial c2 = new ComplexPolynomial(new Complex(2, -1), new Complex(0, 3));
		
		ComplexPolynomial c3 = c1.multiply(c2);
		assertEquals(c3.toString(), "(-9.0+i6.0)*z^2+(7.0+i7.0)*z^1+(2.0-i1.0)");
	}
	
	@Test
	public void testDerive() {
		ComplexPolynomial c = new ComplexPolynomial(new Complex(2,3), new Complex(-4,5), new Complex(8,0));
		
		ComplexPolynomial derived = c.derive();
		
		assertEquals(derived.toString(), "(16.0+i0.0)*z^1+(-4.0+i5.0)");
	}
	
	@Test
	public void testApply() {
		ComplexPolynomial c = new ComplexPolynomial(new Complex(2,3), new Complex(-4,5), new Complex(8,0));
		
		Complex c1 = c.apply(new Complex(2,-1));
		
		assertEquals(c1.getReal(), 23,E );
		assertEquals(c1.getImaginary(), -15 ,E);
	}
}
