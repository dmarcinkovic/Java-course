package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class ValueWrapperTest {

	@Test
	public void testValueWrapper1() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());
		
		assertEquals(v1.getValue(), 0);
		assertEquals(v2.getValue(), null);
	}
	
	@Test
	public void testValueWrapper2() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));

		v3.add(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).
		
		assertEquals(v3.getValue(), 13.0);
		assertEquals(v4.getValue(), 1);
	}
	
	@Test
	public void testValueWrapper3() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));

		v5.add(v6.getValue()); // v5 now stores Integer(13); v6 still stores Integer(1).
		
		assertEquals(v5.getValue(), 13);
		assertEquals(v6.getValue(), 1);
	}
	
	@Test
	public void testValueWrapper4() {
		ValueWrapper vv1 = new ValueWrapper(Boolean.valueOf(true));
		assertThrows(RuntimeException.class, () -> {
			vv1.add(Integer.valueOf(5)); // ==> throws, since current value is boolean
		});
	}
	
	@Test
	public void testValueWrapper5() {
		ValueWrapper vv2 = new ValueWrapper(Integer.valueOf(5));
		assertThrows(RuntimeException.class, () -> { 
			vv2.add(Boolean.valueOf(true)); 
		}); // ==> throws, since the argument value is boolean
	}
	
	@Test
	public void testValueWrapper6() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		
		assertEquals(v1.getValue(), null);
		assertEquals(v2.getValue(), null);
		
		v1.add(56);
		
		assertEquals(v1.getValue(), 56);
		
		v1.subtract(12);
		
		assertEquals(v1.getValue(), 44);
		
		v1.divide(4);
		assertEquals(v1.getValue(), 11);
		
		v1.multiply(3);
		assertEquals(v1.getValue(), 33);
		
		assertThrows(RuntimeException.class, () -> {
			v1.divide(v2.getValue());	
		});
	}
	
	@Test
	public void testValueWrapperForDouble() {
		ValueWrapper v1 = new ValueWrapper("3.4");
		ValueWrapper v2 = new ValueWrapper("1");
		
		assertEquals(v1.getValue(), "3.4"); 
		assertEquals(v2.getValue(), "1");
		
		v1.add(null);
		assertEquals(v1.getValue(), 3.4);
		
		v1.add("0");
		assertEquals(v1.getValue(), 3.4);
		
		v1.add(1.1);
		assertEquals(v1.getValue(), 4.5);
		
		v2.add(-4);
		assertEquals(v2.getValue(), -3);
	}
	
	@Test
	public void testValueWrapperException() {
		ValueWrapper v1 = new ValueWrapper("3.14.");
		
		assertThrows(RuntimeException.class, () -> {
			v1.add("5");
		});
	}
	
	@Test
	public void testValueWrapper7() {
		ValueWrapper v1 = new ValueWrapper("4.5");
		ValueWrapper v2 = new ValueWrapper("4.4");
		
		assertEquals(v1.numCompare(v2.getValue()), 1);
		assertEquals(v2.numCompare(v1.getValue()), -1);
		
		assertEquals(v1.numCompare(4.5), 0);
		assertEquals(v2.numCompare("4.4"), 0);
		
		assertThrows(RuntimeException.class, () -> {
			v1.subtract("-6.1.");
		});
		
		assertThrows(RuntimeException.class, () -> {
			v1.numCompare("3a");
		});
		
		assertThrows(RuntimeException.class, () -> {
			v1.multiply(new ArrayList<Integer>());
		});
		
		v1.multiply("2");
		assertEquals(v1.getValue(), 9.0);
		
		assertThrows(RuntimeException.class, () -> {
			v1.divide(null);
		});
		
		v1.divide("3");
		assertEquals(v1.getValue(), 3.0);
		
		v1.setValue(null);
		assertEquals(v1.getValue(), null);
		
		v1.subtract(null);
		assertEquals(v1.getValue(), 0);
		
		v1.subtract(3.5);
		assertEquals(v1.getValue(), -3.5);
		
		assertThrows(RuntimeException.class, () -> {
			v1.subtract("3.4e");
		});
	}
	
	@Test
	public void testNullEquals() {
		ValueWrapper v1 = new ValueWrapper(null);
		assertEquals(v1.numCompare(null), 0);
	}

	@Test
	public void testValueWrapper8() {
		ValueWrapper v1 = new ValueWrapper(null);
		
		assertEquals(v1.numCompare("0"), 0);
		assertEquals(v1.numCompare("-14564.1"), 1);
		assertEquals(v1.numCompare("343.23443"), -1);
		
		v1.setValue("3.89");
		
		assertEquals(v1.numCompare(1), 1);
		assertEquals(v1.numCompare("3.89"), 0);
		
		assertThrows(RuntimeException.class, () -> {
			v1.numCompare(new ArrayList<Integer>());
		});
		
		assertThrows(RuntimeException.class, () -> {
			v1.numCompare("3a");
		});
	}
}
