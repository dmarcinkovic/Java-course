package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Vector3Test {
	private double E = 1e-6;

	@Test
	public void testNorm() {
		Vector3 vec = new Vector3(1, 1, 1);

		assertEquals(vec.norm(), Math.sqrt(3), E);

		Vector3 v2 = vec.scale(3);

		assertEquals(v2.norm(), Math.sqrt(3) * 3, E);

		Vector3 v3 = new Vector3(-1, -10, 4);

		assertEquals(v3.norm(), Math.sqrt(13) * 3, E);
	}

	@Test
	public void testNormalize() {
		Vector3 v1 = new Vector3(1, 1, 1);

		Vector3 v2 = v1.normalized();
		assertEquals(v2.getX(), Math.sqrt(3) / 3, E);
		assertEquals(v2.getY(), Math.sqrt(3) / 3, E);
		assertEquals(v2.getZ(), Math.sqrt(3) / 3, E);

		Vector3 v3 = new Vector3(-1, -10, -4);

		Vector3 v4 = v3.normalized();

		assertEquals(v4.getX(), -Math.sqrt(13) / 39, E);
		assertEquals(v4.getY(), -10 * Math.sqrt(13) / 39, E);
		assertEquals(v4.getZ(), -4 * Math.sqrt(13) / 39, E);
	}

	@Test
	public void testAdd() {
		Vector3 v1 = new Vector3(4, 5, 5);
		Vector3 v2 = new Vector3(-1, 3, 5);
		
		Vector3 result = v1.add(v2);
		
		assertEquals(result.getX(), 3, E);
		assertEquals(result.getY(), 8, E);
		assertEquals(result.getZ(), 10, E);
	}
	
	@Test
	public void testSub() {
		Vector3 v1 = new Vector3(4, 5, 5);
		Vector3 v2 = new Vector3(-1, 3, 5);
		
		Vector3 result = v1.sub(v2);
		
		assertEquals(result.getX(), 5, E);
		assertEquals(result.getY(), 2, E);
		assertEquals(result.getZ(), 0, E);
	}
	
	@Test
	public void testDotProduct() {
		Vector3 v1 = new Vector3(2, 3, 0);
		Vector3 v2 = new Vector3(3, -3, -2);
		
		double result = v1.dot(v2);
		
		assertEquals(result, -3, E);
	}
	
	@Test
	public void testCrossProduct() {
		Vector3 v1 = new Vector3(1,-2,2);
		Vector3 v2 = new Vector3(6, 5, -1);
		
		Vector3 result = v1.cross(v2);
		
		assertEquals(result.getX(), -8, E);
		assertEquals(result.getY(), 13, E);
		assertEquals(result.getZ(), 17, E);
		
		Vector3 result2 = v2.cross(v1);
		
		assertEquals(result2.getX(), 8, E);
		assertEquals(result2.getY(), -13, E);
		assertEquals(result2.getZ(), -17, E);
	}
	
	@Test
	public void testScale() {
		Vector3 v1 = new Vector3(5, -1, -8);
		
		Vector3 result = v1.scale(-1);
		
		assertEquals(result.getX(), -5, E);
		assertEquals(result.getY(), 1, E);
		assertEquals(result.getZ(), 8, E);
		
		Vector3 result2 = v1.scale(0.5);
		
		assertEquals(result2.getX(), 2.5, E);
		assertEquals(result2.getY(), -0.5, E);
		assertEquals(result2.getZ(), -4, E);
	}
	
	@Test
	public void testCosAngle() {
		Vector3 v1 = new Vector3(0, 3, 0);
		Vector3 v2 = new Vector3(2, 0, 0);
		
		double cosAngle = v1.cosAngle(v2);
		
		assertEquals(cosAngle, 0, E);
		
		Vector3 v3 = new Vector3(3, -1, 4);
		Vector3 v4 = new Vector3(-3, 0, 1);
		
		double cosAngle2 = v3.cosAngle(v4);
		
		assertEquals(cosAngle2, -0.3100868365, E);
	}
}
