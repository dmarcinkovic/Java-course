package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Junit test for Vector2D methods.
 * 
 * @author david
 *
 */
class Vector2DTest {
	private double E = 1e-5;

	@Test
	public void testGetX() {
		Vector2D vector = new Vector2D(2.12, 6576);
		assertEquals(vector.getX(), 2.12, E);
	}

	@Test
	public void testGetY() {
		Vector2D vector = new Vector2D(43, 3.14);
		assertEquals(vector.getY(), 3.14, E);
	}

	@Test
	public void testTranslate() {
		Vector2D vector = new Vector2D(3, 4);
		Vector2D offset = new Vector2D(2, 1);

		vector.translate(offset);
		assertEquals(vector.getX(), 5, E);
		assertEquals(vector.getY(), 5, E);
	}

	@Test
	public void testTranslateException() {
		Vector2D vector = new Vector2D(3, 5);

		assertThrows(NullPointerException.class, () -> {
			vector.translate(null);
		});
	}

	@Test
	public void testTranslated() {
		Vector2D vector = new Vector2D(1, 1);
		Vector2D offset = new Vector2D(4, -2);

		Vector2D translated = vector.translated(offset);

		assertEquals(translated.getX(), 5, E);
		assertEquals(translated.getY(), -1, E);
	}

	@Test
	public void testTranslatedException() {
		Vector2D vector = new Vector2D(-1, -4);

		assertThrows(NullPointerException.class, () -> {
			@SuppressWarnings("unused")
			Vector2D translated = vector.translated(null);
		});
	}

	@Test
	public void testRotate() {
		Vector2D vector = new Vector2D(3, 3);

		vector.rotate(Math.PI / 4);

		assertEquals(vector.getX(), 0, E);
		assertEquals(vector.getY(), 3 * Math.sqrt(2), E);
	}

	@Test
	public void testRotate2() {
		Vector2D vector = new Vector2D(3, 3);

		vector.rotate(Math.PI);

		assertEquals(vector.getX(), -3, E);
		assertEquals(vector.getY(), -3, E);
	}

	@Test
	public void testRotateByNegativeAngle() {
		Vector2D vector = new Vector2D(1, 1);

		vector.rotate(-3 * Math.PI / 4);

		assertEquals(vector.getX(), 0, E);
		assertEquals(vector.getY(), -Math.sqrt(2), E);
	}

	@Test
	public void testRotated() {
		Vector2D vector = new Vector2D(3, 3);

		Vector2D result = vector.rotated(Math.PI / 4);

		assertEquals(result.getX(), 0, E);
		assertEquals(result.getY(), 3 * Math.sqrt(2), E);
	}

	@Test
	public void testRotated2() {
		Vector2D vector = new Vector2D(3, 3);

		Vector2D result = vector.rotated(Math.PI);

		assertEquals(result.getX(), -3, E);
		assertEquals(result.getY(), -3, E);
	}

	@Test
	public void testRotateByNegativeAngled() {
		Vector2D vector = new Vector2D(1, 1);

		Vector2D result = vector.rotated(-3 * Math.PI / 4);

		assertEquals(result.getX(), 0, E);
		assertEquals(result.getY(), -Math.sqrt(2), E);
	}

	@Test
	public void testScale() {
		Vector2D vector = new Vector2D(2, 4);

		vector.scale(3);

		assertEquals(vector.getX(), 6, E);
		assertEquals(vector.getY(), 12, E);
	}

	@Test
	public void testNegativeScale() {
		Vector2D vector = new Vector2D(2, 4);

		vector.scale(-1);

		assertEquals(vector.getX(), -2, E);
		assertEquals(vector.getY(), -4, E);
	}

	@Test
	public void testScaleForScaleLessThanOne() {
		Vector2D vector = new Vector2D(3, -1);

		vector.scale(0.3);

		assertEquals(vector.getX(), 0.9, E);
		assertEquals(vector.getY(), -0.3, E);
	}

	@Test
	public void testScaled() {
		Vector2D vector = new Vector2D(2, 4);

		Vector2D result = vector.scaled(3);

		assertEquals(result.getX(), 6, E);
		assertEquals(result.getY(), 12, E);
	}

	@Test
	public void testNegativeScaled() {
		Vector2D vector = new Vector2D(2, 4);

		Vector2D result = vector.scaled(-1);

		assertEquals(result.getX(), -2, E);
		assertEquals(result.getY(), -4, E);
	}

	@Test
	public void testScaledForScaleLessThanOne() {
		Vector2D vector = new Vector2D(3, -1);

		Vector2D result = vector.scaled(0.3);

		assertEquals(result.getX(), 0.9, E);
		assertEquals(result.getY(), -0.3, E);
	}

	@Test
	public void testCopy() {
		Vector2D vector = new Vector2D(-3, 14);

		Vector2D copy = vector.copy();

		assertEquals(copy.getX(), -3, E);
		assertEquals(copy.getY(), 14, E);
	}
}
