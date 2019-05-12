package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FieldValueGettersTest {

	@Test
	public void testSimpleExample() {
		IFieldValueGetter fieldValueGetter = FieldValueGetters.FIRST_NAME;
		
		StudentRecord records = new StudentRecord("Pero", "Perić", 3, "0036");
		
		assertEquals(fieldValueGetter.get(records), "Pero");
	}
	
	@Test
	public void testSimpleExample2() {
		IFieldValueGetter fieldValueGetter = FieldValueGetters.LAST_NAME;
		
		StudentRecord records = new StudentRecord("Pero", "Perić", 3, "0036");
		
		assertEquals(fieldValueGetter.get(records), "Perić");
	}
	
	@Test
	public void testSimpleExample3() {
		IFieldValueGetter fieldValueGetter = FieldValueGetters.JMBAG;
		
		StudentRecord records = new StudentRecord("Pero", "Perić", 3, "0036");
		
		assertEquals(fieldValueGetter.get(records), "0036");
	}
	
	@Test
	public void testWhenNull() {
		IFieldValueGetter fieldValueGetter = FieldValueGetters.JMBAG;
		
		assertThrows(NullPointerException.class, () -> {
			fieldValueGetter.get(null);
		});
	}
}
