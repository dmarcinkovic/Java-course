package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.collections.SimpleHashtable.TableEntry;

/**
 * Junit tests for SimpleHashtable methods.
 * 
 * @author david
 *
 */
class SimpleHashtableTest {

	@Test
	public void testConstructorException() {
		assertThrows(IllegalArgumentException.class, () -> {
			@SuppressWarnings("unused")
			SimpleHashtable<String, Integer> map = new SimpleHashtable<>(0);
		});
	}

	@Test
	public void testSize() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<>(1023);
		assertEquals(map.size(), 0);
	}

	@Test
	public void testSize2() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<>(1);
		map.put("Štefica", 23);
		map.put("Jasna", 1);
		map.put("Štefica", 45);
		map.put("FER", -11);
		map.put("Fer", -1);
		map.put("String", 0);

		assertEquals(map.size(), 5);

		map.put("Fer", 1);
		map.put("Štefica", 0);
		assertEquals(map.size(), 5);
	}

	@Test
	public void testPutException() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<>(3);

		assertThrows(NullPointerException.class, () -> {
			map.put(null, 12);
		});
	}

	@Test
	public void testPutNullValue() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<>();

		map.put("Some key", null);

		assertEquals(map.size(), 1);
	}

	@Test
	public void testPutDuplicates() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<>(2);
		map.put("Štefica", 23);
		map.put("Jasna", 1);
		map.put("Štefica", 45);
		map.put("FER", -11);
		map.put("Fer", -1);
		map.put("String", 0);

		assertEquals(map.size(), 5);
		assertEquals(map.get("Štefica"), 45);
		assertEquals(map.get("Fer"), -1);

		map.put("Jasna", 12);

		assertEquals(map.get("Jasna"), 12);
		assertEquals(map.size(), 5);
	}

	@Test
	public void testGet() {
		SimpleHashtable<Integer, Double> map = new SimpleHashtable<Integer, Double>();

		assertEquals(map.get(null), null);

		map.put(13, 13.0);
		map.put(1, 1.0);

		assertEquals(map.get(13), 13.0);
		assertEquals(map.get(1), 1.0);
	}

	@Test
	public void testGetNonExisting() {
		SimpleHashtable<String, Double> map = new SimpleHashtable<String, Double>(3);

		assertEquals(map.get(null), null);
		assertEquals(map.get("Štefica"), null);

		map.put("Štefica", -45.);
		map.put("Jasna", -1.);

		assertEquals(map.get("Štefica"), -45);
		assertEquals(map.get("Jasna"), -1.);
		assertEquals(map.get("Fer"), null);
	}

	@Test
	public void testContainsValue() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<String, Integer>();

		assertFalse(map.containsValue(null));

		map.put("String", null);
		map.put("Jasna", -3);

		assertTrue(map.containsValue(null));

		map.put("Štefica", null);
		assertTrue(map.containsValue(null));
		assertFalse(map.containsValue("Jasna"));

		assertTrue(map.containsValue(-3));
	}

	@Test
	public void testContainsKey() {
		SimpleHashtable<String, Double> map = new SimpleHashtable<String, Double>();

		assertFalse(map.containsKey(null));
		assertFalse(map.containsKey("  "));
		assertFalse(map.containsKey(3.14));

		map.put("String", 3.14);
		map.put("FER", 3.);

		assertTrue(map.containsKey("String"));
		assertTrue(map.containsKey("String"));
		assertTrue(map.containsKey("FER"));
	}

	@Test
	public void testRemoveFromEmpty() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<String, Integer>();

		map.remove("String");
		assertEquals(map.size(), 0);

		map.remove(null);
		assertEquals(map.size(), 0);
	}

	@Test
	public void testRemove() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<String, Integer>();

		map.put("String", 34);
		map.put("Štefica", 12);
		map.put("Jasna", 0);

		map.remove("Štefica");
		assertEquals(map.size(), 2);
		assertFalse(map.containsKey("Štefica"));
		assertFalse(map.containsValue(12));

		map.put("Štefica", 1);

		assertEquals(map.size(), 3);
		assertTrue(map.containsKey("Štefica"));
		assertTrue(map.containsValue(1));

		map.put("FER", -1);
		map.put("Some text", 0);

		map.remove("String");
		assertEquals(map.size(), 4);

		map.remove(null);
		map.remove("Jozefina");
		assertEquals(map.size(), 4);

		map.remove("Štefica");
		map.remove("Some text");

		assertFalse(map.containsKey("Some text"));
	}

	@Test
	public void testRemoveFromBegin() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<String, Integer>();

		map.put("String", 1);
		map.put("String2", 2);
		map.put("Štefica", 3);
		map.put("Jasna", 4);
		map.put("Jasna", 5);
		map.put("Jozo", -1);

		map.remove("String");
		assertEquals(map.size(), 4);

		map.remove("String2");
		assertFalse(map.containsKey("String2"));
		assertFalse(map.containsValue(2));
		assertFalse(map.containsValue(null));
		assertFalse(map.containsKey(null));

		map.remove("Štefica");
		assertFalse(map.containsKey("Štefica"));
		assertEquals(map.size(), 2);

		assertTrue(map.containsKey("Jasna"));

		map.remove("Jasna");
		map.remove("Jozo");
		assertEquals(map.size(), 0);

		map.remove("Jozo");
		assertEquals(map.size(), 0);
	}

	@Test
	public void testRemoveFromMiddle() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<String, Integer>();

		map.put("String", 1);
		map.put("String2", 2);
		map.put("Štefica", 3);
		map.put("Jasna", 4);
		map.put("Jasna", 5);
		map.put("Jozo", -1);

		map.remove(null);
		map.remove(null);

		assertEquals(map.size(), 5);

		map.remove("Jozo");
		map.remove("Jasna");

		assertEquals(map.size(), 3);
		assertFalse(map.containsKey("Jozo"));
		assertFalse(map.containsKey("Jasna"));
		assertFalse(map.containsValue(-1));

		map.remove("Štefica");
		map.remove("String2");

		assertEquals(map.size(), 1);

		map.remove("String");
		assertFalse(map.containsValue(1241));

		map.remove("Jasna");
		assertFalse(map.containsKey("Jasna"));
		assertFalse(map.containsValue(-1));

		map.remove(null);
		assertEquals(map.size(), 0);
	}

	@Test
	public void testRemove2() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<String, Integer>();

		map.put("String", 1);
		map.put("String2", 2);
		map.put("Štefica", 3);
		map.put("Jasna", 4);
		map.put("Jasna", 5);
		map.put("Jozo", -1);

		map.remove("String");

		assertEquals(map.size(), 4);

		map.remove("Štefica");
		map.remove("Jasna");
		map.remove("Not exists");

		assertEquals(map.size(), 2);
		assertFalse(map.containsKey("Štefica"));
		assertFalse(map.containsValue(3));

		map.remove("Jozo");
		map.remove("String2");

		assertTrue(map.isEmpty());

		map.remove("No exists");

	}

	@Test
	public void testRemoveFromEnd() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<String, Integer>();

		map.put("String", 1);
		map.put("String2", 2);
		map.put("Štefica", 3);
		map.put("Jasna", 4);
		map.put("Jasna", 5);
		map.put("Jozo", -1);

		map.remove("Štefica");
		map.remove("Jasna");
		map.remove("String2");

		assertEquals(map.size(), 2);
		assertTrue(map.containsKey("String"));
		assertTrue(map.containsValue(1));

		map.remove("Jozo");
		assertTrue(map.containsValue(1));

		map.remove("string");
		assertEquals(map.size(), 1);

		map.remove("String");
		assertFalse(map.containsKey("String"));
	}

	@Test
	public void testClear() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<String, Integer>();

		map.put("String", 1);
		map.put("String2", 2);
		map.put("Štefica", 3);
		map.put("Jasna", 4);
		map.put("Jasna", 5);
		map.put("Jozo", -1);

		map.clear();

		assertFalse(map.containsKey("Jasna"));
		assertFalse(map.containsKey("Jozo"));
		assertFalse(map.containsValue(-1));
		assertEquals(map.size(), 0);

		map.put("New String", 1242);
		map.put("New String", 12);

		assertFalse(map.containsValue(1242));
		assertTrue(map.containsKey("New String"));
		assertTrue(map.containsValue(12));

		assertEquals(map.size(), 1);
	}

	@Test
	public void testIterator() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<String, Integer>(1023);

		map.put("Štefica", 1);

		StringBuilder sb = new StringBuilder();

		for (SimpleHashtable.TableEntry<String, Integer> pair : map) {
			sb.append(pair.getKey());
		}

		assertEquals(sb.toString(), "Štefica");

		map.put("Jasna", 31);
		map.put("FER", -1);

		sb = new StringBuilder();
		for (SimpleHashtable.TableEntry<String, Integer> pair : map) {
			sb.append(pair.getKey());
		}

		assertEquals(sb.toString(), "ŠteficaJasnaFER");

		map.put("String", null);
		map.put("FER", -2);

		sb = new StringBuilder();
		for (SimpleHashtable.TableEntry<String, Integer> pair : map) {
			sb.append(pair.getValue());
		}

		assertEquals(sb.toString(), "131-2null");
	}

	@Test
	public void testIteratorRemove() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<String, Integer>();

		map.put("String", -3);
		map.put("Štefica", 34);
		map.put("Jasna", -1);
		map.put("FER", 12);
		map.put("Ferko", 89);
		map.put("String2", 55);

		Iterator<TableEntry<String, Integer>> itr = map.iterator();
		TableEntry<String, Integer> element = itr.next();

		assertEquals(element.getKey(), "FER");
		itr.remove();

		element = itr.next();
		assertEquals(element.getKey(), "Ferko");

		itr.remove();
		element = itr.next();

		itr.remove();

		element = itr.next();
		assertEquals(element.getKey(), "String");

		itr.remove();

		element = itr.next();
		assertEquals(element.getKey(), "Jasna");
		element = itr.next();
		assertEquals(element.getKey(), "String2");
		itr.remove();
		assertEquals(map.size(), 1);
		assertTrue(map.containsKey("Jasna"));
		assertFalse(map.containsKey("Štefica"));
		assertFalse(map.containsKey("String2"));
		assertThrows(IllegalStateException.class, () -> itr.remove());
	}

	@Test
	public void testIteratorRemove2() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<String, Integer>();

		map.put("String", -3);
		map.put("Štefica", 34);
		map.put("Jasna", -1);
		map.put("FER", 12);
		map.put("Ferko", 89);
		map.put("String2", 55);

		Iterator<TableEntry<String, Integer>> itr = map.iterator();
		@SuppressWarnings("unused")
		TableEntry<String, Integer> element = itr.next();

		itr.remove();

		assertThrows(IllegalStateException.class, () -> itr.remove());
	}

	@Test
	public void testIteratorRemove3() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<String, Integer>();

		map.put("Štefica", 0);

		assertEquals(map.size(), 1);
		assertTrue(map.containsKey("Štefica"));
		assertTrue(map.containsValue(0));
		assertFalse(map.containsValue(null));

		Iterator<TableEntry<String, Integer>> itr = map.iterator();
		TableEntry<String, Integer> element = itr.next();

		assertEquals(element.getKey(), "Štefica");

		itr.remove();

		assertTrue(map.isEmpty());
		assertThrows(NoSuchElementException.class, () -> itr.next());

	}

	@Test
	public void testIteratorRemoveBeforeNext() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<String, Integer>();
		map.put("Štefica", 34);
		map.put("Jasna", -1);

		Iterator<TableEntry<String, Integer>> itr = map.iterator();
		assertThrows(IllegalStateException.class, () -> itr.remove());
	}

	@Test
	public void testIteratorModificationException() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<String, Integer>();

		map.put("String", -3);
		map.put("Štefica", 34);
		Iterator<TableEntry<String, Integer>> itr = map.iterator();

		map.remove("String");
		assertThrows(ConcurrentModificationException.class, () -> itr.hasNext());
		assertThrows(ConcurrentModificationException.class, () -> itr.remove());
		assertThrows(ConcurrentModificationException.class, () -> itr.next());
	}

	@Test
	public void testIteratorModificationException2() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<String, Integer>();

		map.put("String", -3);
		map.put("Štefica", 34);
		Iterator<TableEntry<String, Integer>> itr = map.iterator();

		map.clear();

		assertThrows(ConcurrentModificationException.class, () -> itr.hasNext());
		assertThrows(ConcurrentModificationException.class, () -> itr.remove());
		assertThrows(ConcurrentModificationException.class, () -> itr.next());
	}

	@Test
	public void testIteratorModificationException3() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<String, Integer>();

		map.put("String", -3);
		map.put("Štefica", 34);
		Iterator<TableEntry<String, Integer>> itr = map.iterator();

		map.put("Štefica", 12);

		assertFalse(map.containsValue(34));
		assertTrue(map.containsKey("Štefica"));

		/// should not throw an exception.
		assertTrue(itr.hasNext());

		map.put("Jasna", -1);

		assertThrows(ConcurrentModificationException.class, () -> itr.hasNext());
		assertThrows(ConcurrentModificationException.class, () -> itr.remove());
		assertThrows(ConcurrentModificationException.class, () -> itr.next());
	}

	@Test
	public void testIteratorModificationException4() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<String, Integer>();

		map.put("String", -3);
		map.put("Štefica", 34);
		map.put("Jasna", -1);
		map.put("FER", 12);
		map.put("Ferko", 89);
		map.put("String2", 55);

		Iterator<TableEntry<String, Integer>> itr = map.iterator();
		Iterator<TableEntry<String, Integer>> itr2 = map.iterator();

		itr.next();

		itr.remove();

		assertThrows(ConcurrentModificationException.class, () -> itr2.hasNext());
		assertThrows(ConcurrentModificationException.class, () -> itr2.remove());
		assertThrows(ConcurrentModificationException.class, () -> itr2.next());
	}

	@Test
	public void testTwoIterators() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<String, Integer>();

		map.put("String", -3);
		map.put("Štefica", 34);
		map.put("Jasna", -1);
		map.put("FER", 12);
		map.put("Ferko", 89);
		map.put("String2", 55);

		Iterator<TableEntry<String, Integer>> itr = map.iterator();
		Iterator<TableEntry<String, Integer>> itr2 = map.iterator();
		
		
		while (itr.hasNext()) {
			TableEntry<String, Integer> elem1 = itr.next();
			TableEntry<String, Integer> elem2 = itr2.next();
			
			assertEquals(elem1.getKey(), elem2.getKey());
		}
	}
}
