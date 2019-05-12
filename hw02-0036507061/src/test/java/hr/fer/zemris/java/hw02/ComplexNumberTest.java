package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Junit tests for all method from class ComplexNumber.
 * 
 * @author david
 *
 */
class ComplexNumberTest {
	private static final double E = 10e-6;

	@Test
	public void testConstructor() {
		ComplexNumber c = new ComplexNumber(13.99, -15.6);

		assertEquals(c.getReal(), 13.99, E);
		assertEquals(c.getImaginary(), -15.6, E);
	}

	@Test
	public void testFromReal() {
		ComplexNumber c = ComplexNumber.fromReal(20);

		assertEquals(c.getReal(), 20, E);
		assertEquals(c.getImaginary(), 0, E);
	}

	@Test
	public void testFromImaginary() {
		ComplexNumber c = ComplexNumber.fromImaginary(-31.41);

		assertEquals(c.getReal(), 0, E);
		assertEquals(c.getImaginary(), -31.41);
	}

	@Test
	public void testFromMagnitudeAndAngle() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(2), Math.PI / 4);

		assertEquals(c.getReal(), 1, E);
		assertEquals(c.getImaginary(), 1, E);
	}

	@Test
	public void testGetReal() {
		ComplexNumber c = new ComplexNumber(-15151, 0);

		assertEquals(c.getReal(), -15151, E);
	}

	@Test
	public void testGetImaginary() {
		ComplexNumber c = new ComplexNumber(-314, 1.9999999);

		assertEquals(c.getImaginary(), 1.9999999, E);
	}

	@Test
	public void testGetMagnitude() {
		ComplexNumber c = new ComplexNumber(8, -6);

		assertEquals(c.getMagnitude(), 10, E);
	}

	@Test
	public void testGetAngle() {
		ComplexNumber c = ComplexNumber.parse("1+i");

		assertEquals(c.getAngle(), Math.PI / 4, E);
	}

	@Test
	public void testGetAngle2() {
		ComplexNumber c = ComplexNumber.parse("-1+i");

		assertEquals(c.getAngle(), 3 * (Math.PI / 4), E);
	}

	@Test
	public void testGetAngle3() {
		ComplexNumber c = ComplexNumber.parse("-1-i");

		assertEquals(c.getAngle(), 5 * (Math.PI / 4), E);
	}

	@Test
	public void testGetAngle4() {
		ComplexNumber c = ComplexNumber.parse("1-i");
		assertEquals(c.getAngle(), 7 * (Math.PI / 4), E);
	}

	@Test
	public void testGetAngle5() {
		ComplexNumber c = ComplexNumber.parse("1-2i");

		assertEquals(c.getAngle(), 5.176036589, E);
	}

	@Test
	public void testGetAngle6() {
		ComplexNumber c = ComplexNumber.parse("-3i");

		assertEquals(c.getAngle(), 3 * (Math.PI / 2), E);
	}

	@Test
	public void testGetAngle7() {
		ComplexNumber c = ComplexNumber.parse("2");

		assertEquals(c.getAngle(), 0, E);
	}

	@Test
	public void testGetAngle8() {
		ComplexNumber c = ComplexNumber.parse("-10");

		assertEquals(c.getAngle(), Math.PI, E);
	}

	@Test
	public void testGetAngle9() {
		ComplexNumber c = ComplexNumber.parse("i");

		assertEquals(c.getAngle(), Math.PI / 2, E);
	}

	@Test
	public void testAdd1() {
		ComplexNumber c1 = new ComplexNumber(3, 2);
		ComplexNumber c2 = new ComplexNumber(4, -1);

		ComplexNumber c3 = c1.add(c2);
		assertEquals(c3.getReal(), 7, E);
		assertEquals(c3.getImaginary(), 1, E);
	}

	@Test
	public void testAdd2() {
		ComplexNumber c1 = new ComplexNumber(1.1, 0);
		ComplexNumber c2 = new ComplexNumber(5.6, -1);

		ComplexNumber c3 = c1.add(c2);
		assertEquals(c3.getReal(), 6.7, E);
		assertEquals(c3.getImaginary(), -1, E);
	}

	@Test
	public void testAdd3() {
		ComplexNumber c1 = new ComplexNumber(0, -11.2);
		ComplexNumber c2 = new ComplexNumber(0, 12);

		ComplexNumber c3 = c1.add(c2);
		assertEquals(c3.getReal(), 0, E);
		assertEquals(c3.getImaginary(), 0.8, E);
	}

	@Test
	public void testAddException() {
		assertThrows(NullPointerException.class, () -> {
			ComplexNumber c1 = new ComplexNumber(4, -13);
			c1.add(null);
		});
	}

	@Test
	public void testSub1() {
		ComplexNumber c1 = new ComplexNumber(3, 2);
		ComplexNumber c2 = new ComplexNumber(4, -1);

		ComplexNumber c3 = c1.sub(c2);
		assertEquals(c3.getReal(), -1, E);
		assertEquals(c3.getImaginary(), 3, E);
	}

	@Test
	public void testSub2() {
		ComplexNumber c1 = new ComplexNumber(1.1, 0);
		ComplexNumber c2 = new ComplexNumber(5.6, -1);

		ComplexNumber c3 = c1.sub(c2);

		assertEquals(c3.getReal(), -4.5, E);
		assertEquals(c3.getImaginary(), 1, E);
	}

	@Test
	public void testSub3() {
		ComplexNumber c1 = new ComplexNumber(0, -11.2);
		ComplexNumber c2 = new ComplexNumber(0, 12);

		ComplexNumber c3 = c1.sub(c2);
		assertEquals(c3.getReal(), 0, E);
		assertEquals(c3.getImaginary(), -23.2, E);
	}

	@Test
	public void testSubException() {
		assertThrows(NullPointerException.class, () -> {
			ComplexNumber c1 = new ComplexNumber(0, -11.2);
			@SuppressWarnings("unused")
			ComplexNumber c2 = c1.sub(null);
		});
	}

	@Test
	public void testMul1() {
		ComplexNumber c1 = new ComplexNumber(3, 2);
		ComplexNumber c2 = new ComplexNumber(4, -1);

		ComplexNumber c3 = c1.mul(c2);

		assertEquals(c3.getReal(), 14, E);
		assertEquals(c3.getImaginary(), 5, E);
	}

	@Test
	public void testMul2() {
		ComplexNumber c1 = new ComplexNumber(0, 2);
		ComplexNumber c2 = new ComplexNumber(4, 0);

		ComplexNumber c3 = c1.mul(c2);

		assertEquals(c3.getReal(), 0, E);
		assertEquals(c3.getImaginary(), 8, E);
	}

	@Test
	public void testMul3() {
		ComplexNumber c1 = new ComplexNumber(-5.6, 0);
		ComplexNumber c2 = new ComplexNumber(3, 0);

		ComplexNumber c3 = c1.mul(c2);
		assertEquals(c3.getReal(), -16.8, E);
		assertEquals(c3.getImaginary(), 0, E);
	}

	@Test
	public void testMul4() {
		ComplexNumber c1 = new ComplexNumber(0, 2);
		ComplexNumber c2 = new ComplexNumber(0, -5.8);

		ComplexNumber c3 = c1.mul(c2);
		assertEquals(c3.getReal(), 11.6, E);
		assertEquals(c3.getImaginary(), 0, E);
	}

	@Test
	public void testMulException() {
		assertThrows(NullPointerException.class, () -> {
			ComplexNumber c1 = new ComplexNumber(0, 2);
			@SuppressWarnings("unused")
			ComplexNumber c2 = c1.mul(null);
		});
	}

	@Test
	public void testDiv1() {
		ComplexNumber c1 = new ComplexNumber(3, 2);
		ComplexNumber c2 = new ComplexNumber(4, -1);

		ComplexNumber c3 = c1.div(c2);
		assertEquals(c3.getReal(), 0.588235, E);
		assertEquals(c3.getImaginary(), 0.647059, E);
	}

	@Test
	public void testDiv2() {
		ComplexNumber c1 = new ComplexNumber(0, 2);
		ComplexNumber c2 = new ComplexNumber(4, 0);

		ComplexNumber c3 = c1.div(c2);
		assertEquals(c3.getReal(), 0, E);
		assertEquals(c3.getImaginary(), 0.5, E);
	}

	@Test
	public void testDiv3() {
		ComplexNumber c1 = new ComplexNumber(-5.6, 0);
		ComplexNumber c2 = new ComplexNumber(3, 0);

		ComplexNumber c3 = c1.div(c2);
		assertEquals(c3.getReal(), -5.6 / 3, E);
		assertEquals(c3.getImaginary(), 0, E);
	}

	@Test
	public void testDiv4() {
		ComplexNumber c1 = new ComplexNumber(0, 2);
		ComplexNumber c2 = new ComplexNumber(0, -5.8);

		ComplexNumber c3 = c1.div(c2);
		assertEquals(c3.getReal(), -2 / 5.8, E);
		assertEquals(c3.getImaginary(), 0, E);
	}

	@Test
	public void testDivException() {
		assertThrows(NullPointerException.class, () -> {
			ComplexNumber c1 = new ComplexNumber(5, -7);
			c1.div(null);
		});
	}

	@Test
	public void testDivArithmeticException() {
		assertThrows(ArithmeticException.class, () -> {
			ComplexNumber c1 = new ComplexNumber(3, -3.12);
			ComplexNumber c2 = new ComplexNumber(0, 0);
			c1.div(c2);
		});
	}

	@Test
	public void testPow1() {
		ComplexNumber c = new ComplexNumber(3, -2);

		ComplexNumber result = c.power(3);
		assertEquals(result.getReal(), -9, E);
		assertEquals(result.getImaginary(), -46, E);
	}

	@Test
	public void testPow2() {
		ComplexNumber c = new ComplexNumber(0, -14.5);

		ComplexNumber result = c.power(5);
		assertEquals(result.getReal(), 0, E);
		assertEquals(result.getImaginary(), -640973.40625, E);
	}

	@Test
	public void testPow3() {
		ComplexNumber c = new ComplexNumber(3.14, 0);

		ComplexNumber result = c.power(4);
		assertEquals(result.getReal(), 97.21171216, E);
		assertEquals(result.getImaginary(), 0);
	}

	@Test
	public void testPow4() {
		ComplexNumber c = new ComplexNumber(1, 1);

		ComplexNumber result = c.power(0);
		assertEquals(result.getReal(), 1, E);
		assertEquals(result.getImaginary(), 0, E);
	}

	@Test
	public void testPowForNegativeExponent() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber c = new ComplexNumber(5, 2);

			@SuppressWarnings("unused")
			ComplexNumber result = c.power(-2);
		});
	}

	@Test
	public void testRoot1() { 
		ComplexNumber c = new ComplexNumber(12, 16);

		ComplexNumber[] result = c.root(2);
		assertEquals(result[0].getReal(), 4, E);
		assertEquals(result[1].getReal(), -4, E);
		assertEquals(result[0].getImaginary(), 2, E);
		assertEquals(result[1].getImaginary(), -2, E);
	}

	@Test
	public void testRoot2() { /// TODO
		ComplexNumber c = new ComplexNumber(0, 16);
		
		ComplexNumber[] result = c.root(3);
		
		assertEquals(result[0].getReal(), 2.182247272, E);
		assertEquals(result[1].getReal(), -2.182247272, E); 
		assertEquals(result[2].getReal(), 0, E); 
		assertEquals(result[0].getImaginary(), 1.25992105, E); 
		assertEquals(result[1].getImaginary(), 1.25992105, E); 
		assertEquals(result[2].getImaginary(), -2.5198421, E);
	}

	@Test
	public void testRootForExponentZero() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber c = new ComplexNumber(5, 2);

			@SuppressWarnings("unused")
			ComplexNumber[] result = c.root(0);
		});
	}

	@Test
	public void testRootForNegativeExponent() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber c = new ComplexNumber(4, 2);

			@SuppressWarnings("unused")
			ComplexNumber[] result = c.root(-2);
		});
	}

	@Test
	public void testToString() {
		ComplexNumber c = new ComplexNumber(-5, -12);
		String complex = c.toString();

		assertEquals(complex, "-5.0 - 12.0i");
	}

	@Test
	public void testToString2() {
		ComplexNumber c = new ComplexNumber(0, -1);
		String complex = c.toString();
		assertEquals(complex, "-i");
	}

	@Test
	public void testToString3() {
		ComplexNumber c = new ComplexNumber(0, -2);
		String complex = c.toString();

		assertEquals(complex, "-2.0i");
	}

	@Test
	public void testToString4() {
		ComplexNumber c = new ComplexNumber(-5, 0);
		String complex = c.toString();

		assertEquals(complex, "-5.0");
	}

	@Test
	public void testToString5() {
		ComplexNumber c = new ComplexNumber(2, -1);
		String complex = c.toString();

		assertEquals(complex, "2.0 - i");
	}

	@Test
	public void testParse1() {
		ComplexNumber c = ComplexNumber.parse("-1-i");

		assertEquals(c.getReal(), -1, E);
		assertEquals(c.getImaginary(), -1, E);
	}

	@Test
	public void testParse2() {
		ComplexNumber c = ComplexNumber.parse("+1.0");

		assertEquals(c.getReal(), 1, E);
		assertEquals(c.getImaginary(), 0, E);
	}

	@Test
	public void testParse3() {
		ComplexNumber c = ComplexNumber.parse("-15.4654");

		assertEquals(c.getReal(), -15.4654, E);
		assertEquals(c.getImaginary(), 0, E);
	}

	@Test
	public void testParse4() {
		ComplexNumber c = ComplexNumber.parse("+i");

		assertEquals(c.getReal(), 0, E);
		assertEquals(c.getImaginary(), 1, E);
	}

	@Test
	public void testParse5() {
		ComplexNumber c = ComplexNumber.parse("-i");

		assertEquals(c.getReal(), 0, E);
		assertEquals(c.getImaginary(), -1, E);
	}

	@Test
	public void testParse6() {
		ComplexNumber c = ComplexNumber.parse("+14.7-56.7i");

		assertEquals(c.getReal(), 14.7, E);
		assertEquals(c.getImaginary(), -56.7, E);
	}

	@Test
	public void testParse7() {
		ComplexNumber c = ComplexNumber.parse("-3.17i");

		assertEquals(c.getReal(), 0, E);
		assertEquals(c.getImaginary(), -3.17, E);
	}

	@Test
	public void testParseException1() {
		assertThrows(NumberFormatException.class, () -> {
			@SuppressWarnings("unused")
			ComplexNumber c = ComplexNumber.parse("+1,0");

		});
	}

	@Test
	public void testParseException2() {
		assertThrows(NumberFormatException.class, () -> {
			@SuppressWarnings("unused")
			ComplexNumber c = ComplexNumber.parse("3+i2");
		});
	}

	@Test
	public void testParseException3() {
		assertThrows(NullPointerException.class, () -> {
			@SuppressWarnings("unused")
			ComplexNumber c = ComplexNumber.parse(null);
		});
	}

	@Test
	public void testParseException4() {
		assertThrows(NumberFormatException.class, () -> {
			@SuppressWarnings("unused")
			ComplexNumber c = ComplexNumber.parse("13.4 + 4i");
		});
	}

	@Test
	public void testParseException5() {
		assertThrows(NumberFormatException.class, () -> {
			@SuppressWarnings("unused")
			ComplexNumber c = ComplexNumber.parse("+-3.14");
		});
	}

	@Test
	public void testParseException6() {
		assertThrows(NumberFormatException.class, () -> {
			@SuppressWarnings("unused")
			ComplexNumber c = ComplexNumber.parse("1+-2i");
		});
	}
}
