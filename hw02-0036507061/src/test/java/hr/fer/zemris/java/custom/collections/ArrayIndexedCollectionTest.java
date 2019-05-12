package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

/**
 * Junit tests for all method from class ArrayIndexedCollection.
 * @author david
 *
 */
class ArrayIndexedCollectionTest {

	@Test
	public void testDefaultConstructor() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();

		assertEquals(collection.toArray().length, 0);
	}

	@Test
	public void testSecondConstructor() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(10);

		assertEquals(collection.toArray().length, 0);
	}

	@Test
	public void testSecondConstructorException() {

		assertThrows(IllegalArgumentException.class, () -> {
			@SuppressWarnings("unused")
			ArrayIndexedCollection collection = new ArrayIndexedCollection(0);
		});
	}

	@Test
	public void testSecondConstructorNegativeCapacity() {
		assertThrows(IllegalArgumentException.class, () -> {
			@SuppressWarnings("unused")
			ArrayIndexedCollection collection = new ArrayIndexedCollection(-1);
		});
	}

	@Test
	public void testSecondConstructorForCapacityOne() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(1);
		
		assertEquals(collection.toArray().length, 0);
	}

	@Test
	public void testSecondConstructorForLargeCapacity() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(10000000);

		assertEquals(collection.toArray().length, 0);
	}

	@Test
	public void testThirdConstuctorNUllPointerException() {
		assertThrows(NullPointerException.class, () -> {
			@SuppressWarnings("unused")
			ArrayIndexedCollection collection = new ArrayIndexedCollection(null, 10);
		});
	}

	@Test
	public void testThirdConstructorIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> {
			@SuppressWarnings("unused")
			ArrayIndexedCollection collection = new ArrayIndexedCollection(null, 0);
		});
	}
	
	@Test
	public void testThirdConstructor() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(20);
		collection.add("Štefica");
		collection.add("Jasna");
		
		ArrayIndexedCollection newCollection = new ArrayIndexedCollection(collection);
		Object[] array = newCollection.toArray();
		
		assertArrayEquals(array, new Object[] {20, "Štefica", "Jasna"});
	}
	
	@Test 
	public void testThirdConstructor1() {
		Collection collection = new ArrayIndexedCollection();
		collection.add(50);
		collection.add("Štefica");
		collection.add("Jasna");
		
		ArrayIndexedCollection newCollection = new ArrayIndexedCollection(collection, 10);
		
		Object[] array = newCollection.toArray();
		
		assertArrayEquals(array, new Object[] {50, "Štefica", "Jasna"});
	}

	@Test
	public void testThirdConstructorSize1() {
		Collection collection = new ArrayIndexedCollection();
		collection.add(50);
		collection.add(20);
		ArrayIndexedCollection newCollection = new ArrayIndexedCollection(collection, 1);
		assertEquals(newCollection.toArray().length, 2);
	}

	@Test
	public void testThirdConstructorSize2() {
		Collection collection = new ArrayIndexedCollection();
		collection.add(12);
		collection.add(-40);
		ArrayIndexedCollection newCollection = new ArrayIndexedCollection(collection, 3);
		assertEquals(newCollection.toArray().length, 2);
	}

	@Test
	public void testThirdConstructorSize3() {
		Collection collection = new ArrayIndexedCollection();
		collection.add(12);
		collection.add(-40);
		ArrayIndexedCollection newCollection = new ArrayIndexedCollection(collection, 2);
		assertEquals(newCollection.toArray().length, 2);
	}

	@Test
	public void testFourthConstructor() {
		Collection collection = new ArrayIndexedCollection();
		collection.add(3.14);
		collection.add("String");
		collection.add(6);
		collection.add(5.1f);
		ArrayIndexedCollection newCollection = new ArrayIndexedCollection(collection);
		assertEquals(newCollection.toArray().length, 4);
	}
	
	@Test 
	public void testFourthConstructor1() {
		Collection collection = new ArrayIndexedCollection();
		collection.add(50);
		collection.add("Štefica");
		collection.add("Jasna");
		
		ArrayIndexedCollection newCollection = new ArrayIndexedCollection(collection);
		
		Object[] array = newCollection.toArray();
		
		assertArrayEquals(array, new Object[] {50, "Štefica", "Jasna"});
	}

	@Test
	public void testFourthConstructorNUllPointerException() {
		assertThrows(NullPointerException.class, () -> {
			@SuppressWarnings("unused")
			ArrayIndexedCollection colllection = new ArrayIndexedCollection(null);
		});
	}

	@Test
	public void testSizeGreaterThanZero() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(3.14);
		collection.add("FER");
		collection.add(50);
		assertEquals(collection.size(), 3);
	}

	@Test
	public void testSizeZero() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertEquals(collection.size(), 0);
	}

	@Test
	public void testResizeingCapacity() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		collection.add("FER");
		collection.add(2.78);
		collection.add(3);
		collection.add(1000000L);
		assertEquals(collection.toArray().length, 4);
	}

	@Test
	public void testResizeingSize() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
		collection.add(231);
		collection.add(2.78);
		collection.add("Fakultet elektrotehnike i racunarstva");
		assertEquals(collection.size(), 3);
	}

	@Test
	public void testResizeingSize2() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
		collection.add(231);
		collection.add(2.78);
		assertEquals(collection.toArray().length, 2);
	}

	@Test
	public void testResizeing3() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(1);
		collection.add("value");
		collection.add(3.14);
		collection.add(3.14);
		collection.add("Some text");
		collection.add(3463463);
		assertEquals(collection.toArray().length, 5);
	}

	@Test
	public void testClear1() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
		collection.add(231);
		collection.add(2.78);
		collection.add("Fakultet elektrotehnike i racunarstva");
		collection.clear();
		assertEquals(collection.size(), 0);
	}

	@Test
	public void testClear2() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
		collection.add(231);
		collection.add(2.78);
		collection.add("Fakultet elektrotehnike i racunarstva");
		collection.clear();
		assertEquals(collection.toArray().length, 0);
	}

	@Test
	public void testToArray() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
		collection.add(567);
		collection.add("String");

		Object[] array = collection.toArray();
		assertArrayEquals(array, new Object[] { 567, "String" });
	}

	@Test
	public void testIsEmpty() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(34);
		collection.add(3.14);
		collection.add("FER");

		assertFalse(collection.isEmpty());
	}

	@Test
	public void testIsEmpty2() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertTrue(collection.isEmpty());
	}

	@Test
	public void testEmptySize() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertEquals(collection.size(), 0);
	}

	@Test
	public void testNonEmptySize() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(1);
		assertEquals(collection.size(), 1);
	}

	@Test
	public void testAddNullPointerException() {
		assertThrows(NullPointerException.class, () -> {
			ArrayIndexedCollection collection = new ArrayIndexedCollection();
			collection.add(null);
		});
	}

	@Test
	public void testAdd() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("Fakultet elektrotehnike i racunarstva");
		Object[] array = collection.toArray();
		assertEquals(array[0], "Fakultet elektrotehnike i racunarstva");
	}

	@Test
	public void testAddNull() {
		assertThrows(NullPointerException.class, () -> {
			ArrayIndexedCollection collection = new ArrayIndexedCollection();
			collection.add(null);
		});
	}
	
	@Test
	public void testGet() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(1);
		collection.add("value");
		assertEquals(collection.get(0), "value");
	}

	@Test
	public void testGet2() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(1);
		collection.add("String");
		collection.add(56);
		collection.add(23);
		collection.add(78);

		assertEquals(collection.get(3), 78);
	}

	@Test
	public void testGetNegativeIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
			collection.add(1);
			@SuppressWarnings("unused")
			Object o = collection.get(-1);
		});
	}

	@Test
	public void testGetForEmptyCollection() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
			@SuppressWarnings("unused")
			Object o = collection.get(0);
		});
	}

	@Test
	public void testGetException() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
			collection.add(1);
			collection.add("FER");
			@SuppressWarnings("unused")
			Object o = collection.get(2);
		});
	}

	@Test
	public void testInsertToEmptyCollection() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(1);
		collection.insert(45, 0);

		Object[] array = collection.toArray();
		assertArrayEquals(array, new Object[] { 45 });
	}

	@Test
	public void testInsert1() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
		collection.insert("FER", 0);
		collection.insert(6.28, 0);
		collection.insert(5, 0);
		collection.insert(50, 0);

		Object[] array = collection.toArray();

		assertArrayEquals(array, new Object[] { 50, 5, 6.28, "FER" });
	}

	@Test
	public void testInsert2() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
		collection.insert("Fakultet elektrotehnike i racunarstva", 0);
		collection.insert(3.14, 1);
		collection.insert(24, 2);
		collection.insert(100, 3);

		Object[] array = collection.toArray();

		assertArrayEquals(array, new Object[] { "Fakultet elektrotehnike i racunarstva", 3.14, 24, 100 });
	}

	@Test
	public void testInsertException() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			ArrayIndexedCollection collection = new ArrayIndexedCollection();
			collection.insert(-1, -1);

		});
	}

	@Test
	public void testInsertException2() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			ArrayIndexedCollection collection = new ArrayIndexedCollection();
			collection.insert(1, 1);
		});
	}

	@Test
	public void testInsertException3() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			ArrayIndexedCollection collection = new ArrayIndexedCollection();
			collection.insert(1, 0);
			collection.add(45);
			collection.insert(34, 1);
			collection.insert(999, 4);
		});
	}

	@Test
	public void testInsert3() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(4);
		collection.insert(56, 0);
		collection.add(21);
		collection.insert("Štefica", 2);
		collection.insert("Jasna", 2);

		Object[] array = collection.toArray();
		assertArrayEquals(array, new Object[] { 56, 21, "Jasna", "Štefica" });
	}

	@Test
	public void testInsert4() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
		collection.insert(24, 0);
		collection.insert(76, 1);
		collection.insert(56, 1);
		collection.insert("Štefica", 1);

		Object[] array = collection.toArray();
		assertArrayEquals(array, new Object[] { 24, "Štefica", 56, 76 });
	}

	@Test
	public void testInsertDuplicates() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(4);
		collection.insert("FER", 0);
		collection.add("FER");
		collection.insert(69, 2);
		collection.insert(69, 1);

		Object[] array = collection.toArray();
		assertArrayEquals(array, new Object[] { "FER", 69, "FER", 69 });
	}
	
	@Test
	public void testInsertNull() {
		assertThrows(NullPointerException.class, () -> {
			ArrayIndexedCollection collection = new ArrayIndexedCollection();
			collection.insert(null, 0);
		});
	}

	@Test
	public void testIndexOf() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
		collection.add(45);
		collection.add("Štefica");

		assertEquals(collection.indexOf("Štefica"), 1);
	}

	@Test
	public void testIndexOfTestNotExists() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(78);
		assertEquals(collection.indexOf(79), -1);
	}

	@Test
	public void testIndexOfDuplicates() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("Štefica");
		collection.insert("Jasna", 1);
		collection.add("FER");
		collection.add("Jasna");
		assertEquals(collection.indexOf("Jasna"), 1);
	}

	@Test
	public void testIndexOfDuplicates2() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("Štefica");
		collection.insert("Štefica", 0);
		collection.insert("Jasna", 0);

		assertEquals(collection.indexOf("Štefica"), 1);
	}

	@Test
	public void testIndexOFForNull() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(69);
		assertEquals(collection.indexOf(null), -1);
	}

	@Test
	public void testRemoveFromEmpty() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			ArrayIndexedCollection collection = new ArrayIndexedCollection();
			collection.remove(0);
		});
	}

	@Test
	public void testRemoveException() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			ArrayIndexedCollection collection = new ArrayIndexedCollection();
			collection.add("Štefica");
			collection.add("Jasna");

			collection.remove(2);
		});
	}

	@Test
	public void testRemove1() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(56);
		collection.add(76);
		collection.add("Štefica");
		collection.add("Jasna");

		collection.remove(0);
		Object[] array = collection.toArray();
		assertArrayEquals(array, new Object[] { 76, "Štefica", "Jasna" });
	}

	@Test
	public void testRemoveOnlyElement() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("Jasna");
		collection.remove(0);
		
		assertEquals(collection.size(), 0);
	}
	
	@Test
	public void testRemoveFromFront() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("Štefica");
		collection.add("FER");
		collection.add("Jasna");
		
		collection.remove(0);
		
		Object[] array = collection.toArray();
		assertArrayEquals(array, new Object[] {"FER", "Jasna"});
		
	}
	
	@Test
	public void testRemoveFromEnd() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("Štefica");
		collection.add("FER");
		collection.add("Jasna");
		
		collection.remove(2);
		
		Object[] array = collection.toArray();
		assertArrayEquals(array, new Object[] {"Štefica", "FER"});
	}
	
	@Test
	public void testRemoveTwiceFromEnd() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(20);
		collection.add(30);
		collection.add(-12.3);
		collection.add(3.14);
		
		collection.remove(3);
		collection.remove(2);
		
		Object[] array = collection.toArray();
		assertArrayEquals(array, new Object[] {20, 30});
	}
	
	@Test
	public void testRemoveTwiceFromFront() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(20);
		collection.add(30);
		collection.add(-12.3);
		collection.add(3.14);
		
		collection.remove(0);
		collection.remove(0);
		
		Object[] array = collection.toArray();
		assertArrayEquals(array, new Object[] {-12.3, 3.14});
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
		
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(20);
		collection.add("String");
		
		collection.forEach(new Print());
		
		assertEquals(sb.toString(), "20String");
	}
	
	@Test
	public void testContains1() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(20);
		collection.add("Štefica");
		collection.add("Jasna");
		collection.add(3.14);
		
		assertTrue(collection.contains("Štefica"));
	}
	
	@Test
	public void testContains2() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertFalse(collection.contains("Jasna"));
	}
	
	@Test
	public void testContains3() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("Štefica");
		collection.add(-1);
		assertFalse(collection.contains(null));
	}
	
	@Test 
	public void testRemoveWithValue() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
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
		ArrayIndexedCollection collection = new ArrayIndexedCollection();;
		collection.add("Fer");
		collection.add("Fakultet elektrotehnike i racunarstva");
		
		assertTrue(collection.remove("Fer"));
	}
	
	@Test 
	public void testRemoveWithValue3() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("Štefica");
		
		assertFalse(collection.remove(null));
	}
}
