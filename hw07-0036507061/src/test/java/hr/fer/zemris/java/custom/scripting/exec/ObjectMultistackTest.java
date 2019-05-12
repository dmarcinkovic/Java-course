package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class ObjectMultistackTest {

	@Test
	void testObjectMultistack1() {
		ObjectMultistack obj = new ObjectMultistack();

		obj.push("fer", new ValueWrapper(20));
		obj.push("fer", new ValueWrapper(50));
		obj.push("fer", new ValueWrapper(40));
		obj.push("fer", new ValueWrapper(30));
		obj.push("fer", new ValueWrapper(10));

		assertEquals(obj.pop("fer").getValue(), 10);
		assertEquals(obj.pop("fer").getValue(), 30);
		assertEquals(obj.pop("fer").getValue(), 40);
		assertEquals(obj.pop("fer").getValue(), 50);
		assertEquals(obj.pop("fer").getValue(), 20);
	}

	@Test
	public void testException() {
		ObjectMultistack obj = new ObjectMultistack();
		assertThrows(NoSuchElementException.class, () -> {
			obj.pop("fer");
		});

		assertThrows(NoSuchElementException.class, () -> {
			obj.pop(null);
		});

		assertThrows(NoSuchElementException.class, () -> {
			obj.pop("FER");
		});
	}

	@Test
	public void testNullPointerException() {
		ObjectMultistack obj = new ObjectMultistack();
		assertThrows(NullPointerException.class, () -> {
			obj.push(null, new ValueWrapper(null));
		});

		assertThrows(NullPointerException.class, () -> {
			obj.isEmpty(null);
		});
	}

	@Test
	public void testIsEmpty() {
		ObjectMultistack obj = new ObjectMultistack();
		obj.push("fer", new ValueWrapper(null));

		assertFalse(obj.isEmpty("fer"));
		assertTrue(obj.isEmpty("FER"));
		
		obj.pop("fer");
		
		assertTrue(obj.isEmpty("fer")); 
		
		assertThrows(RuntimeException.class, () -> {
			obj.pop("fer");
		});
		
		assertThrows(RuntimeException.class, () -> {
			obj.peek("FER");
		});
		
		obj.push("Štefica", new ValueWrapper(null));
		
		assertFalse(obj.isEmpty("Štefica"));
		
		obj.pop("Štefica"); 
		
		assertTrue(obj.isEmpty("Štefica")); 
	}

	@Test
	public void testPeek() {
		ObjectMultistack obj = new ObjectMultistack();
		
		obj.push("fer" , new ValueWrapper(null));
		
		assertEquals(obj.peek("fer").getValue(), null);

		assertThrows(NoSuchElementException.class, () -> {
			obj.peek("Fer");
		});
	}
	
	@Test
	public void testNullValue() {
		ObjectMultistack obj = new ObjectMultistack();
		
		assertThrows(NullPointerException.class, () -> {
			obj.push("key", null);
		});
	}
}
