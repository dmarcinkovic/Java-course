package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

class QueryFilterTest {

	@Test
	void test() {
		StudentDatabase db = new StudentDatabase(getDatabase());
		
		QueryParser parser = new QueryParser("lastName = \"Akšamović\" ");
		
		List<StudentRecord> records = db.filter(new QueryFilter(parser.getQuery()));
		
		assertEquals(records.size(), 1);
	}
	
	@Test
	public void testDatabase1() {
		StudentDatabase db = new StudentDatabase(getDatabase());
		
		QueryParser parser = new QueryParser("firstName = \"Ivan\" ");
		
		List<StudentRecord> records = db.filter(new QueryFilter(parser.getQuery()));
		
		assertEquals(records.size(), 5);
		
		assertEquals(records.get(0).getLastName(), "Cvrlje");
		assertEquals(records.get(1).getLastName(), "Marić");
		assertEquals(records.get(2).getLastName(), "Pilat");
		assertEquals(records.get(3).getLastName(), "Rakipović");
		assertEquals(records.get(4).getLastName(), "Šimunov");
	}
	
	@Test
	public void testDatabase2() {
		StudentDatabase db = new StudentDatabase(getDatabase());
		
		QueryParser parser = new QueryParser("firstName = \"ivan\" ");
		
		List<StudentRecord> records = db.filter(new QueryFilter(parser.getQuery()));
		
		assertEquals(records.size(), 0);
	}
	
	@Test
	public void testDatabase3() {
		StudentDatabase db = new StudentDatabase(getDatabase());
		
		QueryParser parser = new QueryParser("firstName LIKE \"Iva*\" ");
		
		List<StudentRecord> records = db.filter(new QueryFilter(parser.getQuery()));
		
		assertEquals(records.size(), 5);
		
		assertEquals(records.get(0).getLastName(), "Cvrlje");
		assertEquals(records.get(1).getLastName(), "Marić");
		assertEquals(records.get(2).getLastName(), "Pilat");
		assertEquals(records.get(3).getLastName(), "Rakipović");
		assertEquals(records.get(4).getLastName(), "Šimunov");
	}
	
	@Test
	public void testDatabase4() {
		assertThrows(ParserException.class, () -> {
			new QueryParser("firstname = \"ivan\" ");
		});
	}
	
	@Test
	public void testDatabase5() {
		StudentDatabase db = new StudentDatabase(getDatabase());
		
		QueryParser parser = new QueryParser("firstName LIKE \"iva*\" ");
		
		List<StudentRecord> records = db.filter(new QueryFilter(parser.getQuery()));
		
		assertEquals(records.size(), 0);
	}
	
	@Test
	public void testDatabase6() {
		StudentDatabase db = new StudentDatabase(getDatabase());
		
		QueryParser parser = new QueryParser("firstName LIKE \"Ivan\" ");
		
		List<StudentRecord> records = db.filter(new QueryFilter(parser.getQuery()));
		
		assertEquals(records.size(), 5);
		
		assertEquals(records.get(0).getLastName(), "Cvrlje");
		assertEquals(records.get(1).getLastName(), "Marić");
		assertEquals(records.get(2).getLastName(), "Pilat");
		assertEquals(records.get(3).getLastName(), "Rakipović");
		assertEquals(records.get(4).getLastName(), "Šimunov");
	}
	
	@Test
	public void testDatabase7() {
		StudentDatabase db = new StudentDatabase(getDatabase());
		
		QueryParser parser = new QueryParser("jmbag LIKE \"000000000*\" ");
		
		List<StudentRecord> records = db.filter(new QueryFilter(parser.getQuery()));
		
		assertEquals(records.size(), 9);
	}
	
	@Test
	public void testDatabase8() {
		StudentDatabase db = new StudentDatabase(getDatabase());
		
		QueryParser parser = new QueryParser("lastName = \"Zadro\" ");
		
		List<StudentRecord> records = db.filter(new QueryFilter(parser.getQuery()));
		
		assertEquals(records.size(), 1);
		assertEquals(records.get(0).getFirstName(), "Kristijan");
	}
	
	@Test
	public void testException() {
		assertThrows(NullPointerException.class, () -> {
			new QueryFilter(null);
		});
	}
	
	/**
	 * Method to load database.
	 * @return Database.
	 */
	private List<String> getDatabase(){
		List<String> lines = null;
		try {
			lines = Files.readAllLines(
					 Paths.get("src/main/resources/DownloadFile.txt"),
					 StandardCharsets.UTF_8
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return lines;
	}
}
