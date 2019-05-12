package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class StudentDatabaseTest {

	@Test
	void test() {
		List<String> list = new ArrayList<>();

		list.add("0036000	Perić	Pero	5");
		list.add("0036001	Josipovic	Josip	4");
		list.add("0036002	Štefić	Štefica	1");
		list.add("0036242	Jasnić	Jasna	4");

		StudentDatabase student = new StudentDatabase(list);

		StudentRecord record = student.forJMBAG("0036000");
		assertEquals(record.getFirstName(), "Pero");
		assertEquals(record.getLastName(), "Perić");

		record = student.forJMBAG("0036001");
		assertEquals(record.getFirstName(), "Josip");
		assertEquals(record.getLastName(), "Josipovic");
		assertEquals(record.getFinalGrade(), 4);

		record = student.forJMBAG("0036002");
		assertEquals(record.getFirstName(), "Štefica");
		assertEquals(record.getLastName(), "Štefić");
		assertEquals(record.getFinalGrade(), 1);

		record = student.forJMBAG("0036242");
		assertEquals(record.getFirstName(), "Jasna");
		assertEquals(record.getLastName(), "Jasnić");
		assertEquals(record.getFinalGrade(), 4);
	}

	@Test
	public void testAllTrue() {
		IFilter filter = v -> true;

		List<String> list = new ArrayList<>();

		list.add("0036000	Perić	Pero	5");
		list.add("0036001	Josipovic	Josip	4");
		list.add("0036002	Štefić	Štefica	1");
		list.add("0036242	Jasnić	Jasna	4");

		StudentDatabase student = new StudentDatabase(list);
		
		List<StudentRecord> records = student.filter(filter); 
		
		assertEquals(records.size(), 4);
		
		assertEquals(records.get(0).getFirstName(), "Pero");
		assertEquals(records.get(0).getLastName(), "Perić");
		assertEquals(records.get(0).getJmbag(), "0036000");
		
		assertEquals(records.get(1).getFirstName(), "Josip");
		assertEquals(records.get(1).getLastName(), "Josipovic");
		assertEquals(records.get(1).getJmbag(), "0036001");
		
		assertEquals(records.get(2).getFirstName(), "Štefica");
		assertEquals(records.get(2).getLastName(), "Štefić");
		assertEquals(records.get(2).getJmbag(), "0036002");
		
		assertEquals(records.get(3).getFirstName(), "Jasna");
		assertEquals(records.get(3).getLastName(), "Jasnić");
		assertEquals(records.get(3).getJmbag(), "0036242");
	}

	@Test
	public void testAllFalse() {
		IFilter filter = v -> false;
		
		List<String> list = new ArrayList<>();

		list.add("0036000	Perić	Pero	5");
		list.add("0036001	Josipovic	Josip	4");
		list.add("0036002	Štefić	Štefica	1");
		list.add("0036242	Jasnić	Jasna	4");

		StudentDatabase student = new StudentDatabase(list);
		
		List<StudentRecord> records = student.filter(filter); 
		
		assertEquals(records.size(), 0);
	}
	
	@Test
	public void testConstructorException() {
		assertThrows(NullPointerException.class, () -> {
			new StudentDatabase(null);
		});
	}
	
	@Test
	public void testFilter() {
		IFilter filter = v -> v.getFinalGrade() > 3;
		
		List<String> list = new ArrayList<>();
		
		list.add("12345	Djurić	Djuro	3");
		list.add("12346	Marić	Marko	2");
		list.add("12347	Ivić	Ivana	4");
		list.add("12348	Marić	Marija	5");
		list.add("12349	Perić	Pero	5");
		list.add("12350	Ivić	Ivan	1");
		
		StudentDatabase student = new StudentDatabase(list);
		
		List<StudentRecord> records = student.filter(filter); 
		
		assertEquals(records.size(), 3);
		
		assertEquals(records.get(0).getFirstName(), "Ivana");
		assertEquals(records.get(0).getLastName(), "Ivić");
		
		assertEquals(records.get(1).getFirstName(), "Marija");
		assertEquals(records.get(1).getLastName(), "Marić");
		
		assertEquals(records.get(2).getFirstName(), "Pero");
		assertEquals(records.get(2).getLastName(), "Perić");
	}
}
