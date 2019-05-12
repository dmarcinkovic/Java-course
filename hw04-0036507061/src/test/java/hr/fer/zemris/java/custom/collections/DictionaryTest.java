package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Junit tests for Dictionary methods.
 * 
 * @author david
 *
 */
class DictionaryTest {

	@Test
	public void testEmptyDictionary() {
		Dictionary<String, Integer> dict = new Dictionary<String, Integer>();

		assertEquals(dict.size(), 0);
	}

	@Test
	public void testPutNullKey() {
		Dictionary<String, Integer> dict = new Dictionary<String, Integer>();

		assertThrows(NullPointerException.class, () -> {
			dict.put(null, 23524);
		});
	}

	@Test
	public void testPutNullValue() {
		Dictionary<Integer, Integer> dict = new Dictionary<Integer, Integer>();

		dict.put(11, null);
		dict.put(45, 12);
		dict.put(56, null);

		assertEquals(dict.get(11), null);
		assertEquals(dict.get(45), 12);
	}

	@Test
	public void testPutDuplicates() {
		Dictionary<String, Integer> dict = new Dictionary<String, Integer>();

		dict.put("Key", 34);
		dict.put("Štefica", 21);
		dict.put("Jasna", 34);

		assertEquals(dict.get("Key"), 34);
		assertEquals(dict.get("Štefica"), 21);
		assertEquals(dict.get("Jasna"), 34);

		dict.put("Štefica", 1);

		assertEquals(dict.get("Štefica"), 1);
	}

	@Test
	public void testSize() {
		Dictionary<Double, Integer> dict = new Dictionary<Double, Integer>();

		dict.put(45.454, 11);
		dict.put(3.14, 0);
		dict.put(2.11, -3);

		assertFalse(dict.isEmpty());
		assertEquals(dict.size(), 3);

		dict.put(2.11, -4);
		assertEquals(dict.size(), 3);
	}

	@Test
	public void testGetNullKey() {
		Dictionary<String, Integer> dict = new Dictionary<String, Integer>();

		dict.put("String", 12);
		dict.put("Štefica", 1);

		assertEquals(dict.get(null), null);
	}

	@Test
	public void testGet() {
		Dictionary<String, Integer> dict = new Dictionary<String, Integer>();

		dict.put("String", 1);
		dict.put("Štefica", 34);
		dict.put("Jabuka", 0);
		dict.put("Jasna", -1);

		assertEquals(dict.get("string"), null);
		assertEquals(dict.get(34353), null);
	}

	@Test
	public void testGet2() {
		Dictionary<String, Integer> dict = new Dictionary<String, Integer>();

		dict.put("14234", 12423);
		dict.put("Štefica", 123);
		dict.put("Djurdja", 21);
		dict.put("Štefica", 1);
		dict.put("Djurdja", 22);

		assertEquals(dict.get("Štefica"), 1);
		assertEquals(dict.get("Djurdja"), 22);
		assertEquals(dict.size(), 3);
	}

	@Test
	public void testSize2() {
		Dictionary<String, Integer> dict = new Dictionary<String, Integer>();

		assertEquals(dict.size(), 0);

		dict.put("Štefica", 1);
		dict.put("Jasminka", -1);
		dict.put("FER", 314);

		assertEquals(dict.size(), 3);
	}

	@Test
	public void testClear() {
		Dictionary<String, Integer> dict = new Dictionary<String, Integer>();

		dict.put("Jasna", 322);
		dict.put("Štefica", -45);
		dict.put("Fer", 3);
		dict.put("Fer", 21);

		assertEquals(dict.size(), 3);

		dict.clear();

		assertEquals(dict.size(), 0);

		dict.put("Štefica", 12);
		dict.put("Štefica", -23432);

		assertEquals(dict.size(), 1);
		assertEquals(dict.get("Štefica"), -23432);
		assertEquals(dict.get("Fer"), null);
	}
}
