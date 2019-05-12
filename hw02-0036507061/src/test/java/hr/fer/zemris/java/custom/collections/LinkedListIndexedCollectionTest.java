package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Junit tests for all method from class LinkedListIndexedCollection.
 * @author david
 *
 */
class LinkedListIndexedCollectionTest {

	@Test
	public void testDefaultConstructor() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		
		assertEquals(collection.size(), 0);
	}
	
	@Test
	public void testSecondConstructor1() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(20);
		collection.add("Štefica");
		collection.add("Jasna");
		
		LinkedListIndexedCollection newCollection = new LinkedListIndexedCollection(collection);
		Object[] array = Arrays.copyOf(newCollection.toArray(), 3);
		assertArrayEquals(array, new Object[] {20, "Štefica", "Jasna"});
	}
	
	@Test
	public void testSecondConstructor2() {
		assertThrows(NullPointerException.class, () -> {
			@SuppressWarnings("unused")
			LinkedListIndexedCollection collection = new LinkedListIndexedCollection(null);
		});
	}
	
	@Test 
	public void testClear() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(30);
		collection.add("Fakultet elektrotehnike i racunarstva");
		
		collection.clear();
		assertEquals(collection.size(), 0);
	}
	
	@Test
	public void testSize1() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(1.21);
		collection.add("Štefica");
		collection.add(3.14);
		collection.add(67856);
		
		assertEquals(collection.size(), 4);
	}
	
	@Test 
	public void testSize2() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		
		assertEquals(collection.size(), 0);
	}
	
	@Test
	public void testIsEmpty1() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(1.21);
		collection.add("Štefica");
		collection.add(3.14);
		collection.add(67856);
		
		assertFalse(collection.isEmpty());
	}
	
	@Test 
	public void testIsEmpty2() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		
		assertTrue(collection.isEmpty());
	}
	
	@Test 
	public void testAdd() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		
		collection.add("FER");
		collection.add("Štefica");
		collection.add("Jasna");
		collection.add(3.14);
		
		Object[] array = collection.toArray();
		
		assertArrayEquals(array, new Object[] {"FER", "Štefica", "Jasna", 3.14});
	}
	
	@Test 
	public void testAddException() {
		assertThrows(NullPointerException.class, () -> {
			LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
			
			collection.add(null);
		});
	}
	
	@Test 
	public void testAdd2() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(54321);
		
		Object[] array = collection.toArray();
		
		assertArrayEquals(array, new Object[] {54321});
	}
	
	@Test 
	public void testGetNegativeIndex() {
		assertThrows(IllegalArgumentException.class, () -> {
			LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
			collection.add(54321);
			collection.add("Štefica");
			
			@SuppressWarnings("unused")
			Object o = collection.get(-1);
		});
	}
	
	@Test 
	public void testGetForIndexGreaterThanSize() {
		assertThrows(IllegalArgumentException.class, () -> {
			LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
			collection.add("Fer");
			collection.add(1492);
			collection.add(98);
			collection.add(1605);
			
			@SuppressWarnings("unused")
			Object o = collection.get(4);
		});
	}
	
	@Test 
	public void testGetFirst() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(1);
		collection.add(2);
		
		assertEquals(collection.get(0), 1);
	}
	
	@Test 
	public void testGetLast() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Štefica");
		collection.add(314);
		collection.add(576);
		collection.add("String");
		
		assertEquals(collection.get(3), "String");
	}
	
	@Test 
	public void testGet() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Jasmina");
		collection.add(351);
		collection.add(761);
		collection.add(-1);
		collection.add(3.14);
		
		assertEquals(collection.get(2), 761);
	}
	
	@Test 
	public void testInsertAtFront() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.insert("Štefica", 0);
		collection.insert(2.34, 0);
		collection.insert(6.71, 0);
		collection.insert("Jasna", 0);
		collection.insert("fer", 0);
		
		Object[] array = collection.toArray();
		assertArrayEquals(array, new Object[] {"fer", "Jasna", 6.71, 2.34, "Štefica"}); 
	}
	
	@Test 
	public void testInsertAtEnd() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.insert("Jasna", 0);
		collection.insert(0, 1);
		collection.insert(-1, 2);
		collection.insert("Ferko", 3);
		collection.insert(21, 4);
		
		Object[] array = collection.toArray();
		assertArrayEquals(array, new Object[] {"Jasna", 0, -1, "Ferko", 21});
	}
	
	@Test
	public void testInsert() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.insert("Štefica", 0);
		collection.insert(-1, 1);
		collection.insert(2468, 1);
		collection.insert(21, 2);
		collection.insert("Jasna", 3);
		collection.insert(3.14, 4);
		
		Object[] array = collection.toArray();
		assertArrayEquals(array, new Object[] {"Štefica", 2468, 21, "Jasna", 3.14, -1});
	}
	
	@Test 
	public void testInsert2() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.insert("Jasna", 0);
		collection.insert(-7, 0);
		collection.insert("Value", 1);
		collection.insert(8, 2);
		collection.insert("Value", 4);
		collection.insert("Štefica", 4);
		
		Object[] array = collection.toArray();
		assertArrayEquals(array, new Object[] {-7, "Value", 8, "Jasna", "Štefica", "Value"});
	}
	
	@Test 
	public void testInsertException() {
		assertThrows(IllegalArgumentException.class, () -> {
			LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
			collection.insert(92, 0);
			collection.insert(2, 2);
		});
	}
	
	@Test 
	public void testInsertException1() {
		assertThrows(IllegalArgumentException.class, () -> {
			LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
			collection.insert("Dragi korisnik", -1);
		});
	}
	
	@Test 
	public void testInsertException2() {
		assertThrows(IllegalArgumentException.class, () -> {
			LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
			collection.insert(20, 0);
			collection.insert("Jasna", 2);
		});
	}
	
	@Test 
	public void testIndexOf() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Value");
		collection.add("String");
		collection.add(3.14);
		
		assertEquals(collection.indexOf("String"), 1);
	}
	
	@Test 
	public void testIndexOfDuplicates() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("String");
		collection.add("Fer");
		collection.add("Fer");
		
		assertEquals(collection.indexOf("Fer"), 1);
	}
	
	@Test
	public void testIndexOfNotExists() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(0);
		collection.add("Jasna");
		
		assertEquals(collection.indexOf("Štefica"), -1);
	}
	
	@Test 
	public void testIndexOfNullValue() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(-5);
		collection.add("Štefica");
		
		assertEquals(collection.indexOf(null), -1);
	}
	
	@Test 
	public void testRemoveFromFront() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(-6);
		collection.add(23);
		collection.add("String");
		collection.add("Štefica");
		collection.add("Jasna");
		collection.add(-3.14);
		
		collection.remove(0);
		
		Object[] array = collection.toArray();
		assertArrayEquals(array, new Object[] {23, "String", "Štefica", "Jasna", -3.14});
 	}
	
	@Test 
	public void testRemoveFromEnd() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(-6);
		collection.add(23);
		collection.add("String");
		collection.add("Štefica");
		collection.add("Jasna");
		collection.add(-3.14);
		
		collection.remove(5);
		
		Object[] array = collection.toArray();
		assertArrayEquals(array, new Object[] {-6, 23, "String", "Štefica", "Jasna"});
	}
	
	@Test 
	public void testRemoveTwiceFromFront() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(-6);
		collection.add(23);
		collection.add("String");
		collection.add("Štefica");
		collection.add("Jasna");
		collection.add(-3.14);
		
		collection.remove(0);
		collection.remove(0);
		
		Object[] array = collection.toArray();
		assertArrayEquals(array, new Object[] {"String", "Štefica", "Jasna", -3.14});
	}
	
	@Test 
	public void testRemoveTwiceFronEnd() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(-6);
		collection.add(23);
		collection.add("String");
		collection.add("Štefica");
		collection.add("Jasna");
		collection.add(-3.14);
		
		collection.remove(5);
		collection.remove(4);
		
		Object[] array = collection.toArray();
		assertArrayEquals(array, new Object[] {-6, 23, "String", "Štefica"});
	}
	
	@Test 
	public void testRemoveOnlyElementSize() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Value");
		
		collection.remove(0);
		
		assertEquals(collection.size(), 0);
	}
	
	@Test 
	public void testRemoveOnlyElement() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Value");
		
		collection.remove(0);
		
		Object[] array = collection.toArray();
		assertEquals(array.length, 0);
	}
	
	@Test 
	public void testRemoveException() {
		assertThrows(IllegalArgumentException.class, () -> {
			LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
			collection.remove(0);
		});
	}
	
	@Test
	public void testRemoveException2() {
		assertThrows(IllegalArgumentException.class, () -> {
			LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
			collection.add("Štefica");
			collection.remove(2);
		});
	}
	
	@Test
	public void testRemoveValue() {
			String kava = "kava";
			String caj = "caj";
			String sok = "sok";
			LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
			collection.add(kava);
			collection.add(caj);
			collection.add(sok);
			
			collection.remove(caj); 
			
			assertEquals(kava, collection.get(0));
			assertEquals(sok, collection.get(1));
	}
	
	@Test 
	public void testRemoveTwice() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Kava");
		collection.add("Caj");
		collection.add("Sok");
		collection.add("mineralna");
		collection.add("cips");
		
		collection.remove("Sok");
		collection.remove("Caj");
		
		assertEquals(collection.get(0), "Kava");
		assertEquals(collection.get(1), "mineralna"); 
		assertEquals(collection.get(2), "cips");
	}
	
	@Test 
	public void testToArray() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		Object[] array = collection.toArray();
		
		assertEquals(array.length, 0);
	}
	
	@Test 
	public void testToArray2() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Štefica");
		collection.add("Jasna");
		
		Object[] array = collection.toArray();
		
		assertArrayEquals(array, new Object[] {"Štefica", "Jasna"});
	}
	
	@Test 
	public void testContains() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(-3.14);
		collection.add(-999);
		
		assertTrue(collection.contains(-999));
	}
	
	@Test 
	public void testContains2() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(-67);
		collection.add(-56);
		
		assertFalse(collection.contains(-68));
	}
	
	@Test 
	public void testContains3() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Štefica");
		collection.add("Jasna");
		
		assertFalse(collection.contains(null));
	}
	
	@Test 
	public void testForEach() {
		StringBuilder sb = new StringBuilder();
		class Print extends Processor {
			
			@Override
			public void process(Object o) {
				sb.append(o.toString());
			}
		}
		
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(20);
		collection.add("String");
		
		collection.forEach(new Print());
		
		assertEquals(sb.toString(), "20String");
	}
	
	@Test 
	public void testRemoveWithValue() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Štefica");
		collection.add("Jasna");
		collection.add(3.14);
		collection.add(5);
		
		collection.remove("Štefica");
		
		Object[] array = collection.toArray();
		assertArrayEquals(array, new Object[] {"Jasna", 3.14, 5});
	}
	
	@Test 
	public void testRemoveWithValue2() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Fer");
		collection.add("Fakultet elektrotehnike i racunarstva");
		
		assertTrue(collection.remove("Fer"));
	}
	
	@Test 
	public void testRemoveWithValue3() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Štefica");
		
		assertFalse(collection.remove(null));
	}
	
}
