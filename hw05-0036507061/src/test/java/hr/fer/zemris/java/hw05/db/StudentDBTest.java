package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StudentDBTest {
	private static StudentDatabase db;
	
	@BeforeAll
	private static void load() {
		db = new StudentDatabase(getDatabase());
	}
	/**
	 * Method to load database.
	 * 
	 * @return Database.
	 */
	private static List<String> getDatabase() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("src/main/resources/DownloadFile.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}
	
	@Test
	public void testPrint() {
		String input = "query jmbag = \"0000000003\"";
		
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.size(), 5);
		assertEquals(output.get(2), "| 0000000003 | Bosnić | Andrea | 4 |");
	}
	
	@Test
	public void testPrint2() {
		String input = "query jmbag = \"0000000003\" AND lastName LIKE \"B*\"";
		
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.size(), 4);
		assertEquals(output.get(1), "| 0000000003 | Bosnić | Andrea | 4 |");
	}
	
	@Test
	public void testPrint3() {
		String input = " query jmbag = \"0000000003\" AND lastName LIKE \"L*\"";
		
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "Records selected: 0\n");
	}
	
	@Test
	public void testPrint4() {
		String input = "  query lastName LIKE \"B*\"";
		
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.size(), 7);
		assertEquals(output.get(1), "| 0000000002 | Bakamović | Petra     | 3 |");
	}
		
	@Test
	public void testPrint5() {
		String input = "query lastName LIKE \"Be*\"";
		
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "Records selected: 0\n");
	}
	
	@Test
	public void testSpacesIgnorable1() {
		String input = "query      lastName=\"Bosnić\"";
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.size(), 4);
		assertEquals(output.get(1), "| 0000000003 | Bosnić | Andrea | 4 |");
	}
	
	@Test
	public void testSpacesIgnorable2() {
		String input = "query lastName    =\"Bosnić\"";
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.size(), 4);
		assertEquals(output.get(1), "| 0000000003 | Bosnić | Andrea | 4 |");
	}
	
	@Test
	public void testSpacesIgnorable3() {
		String input = "query lastName= \"Bosnić\"";
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.size(), 4);
		assertEquals(output.get(1), "| 0000000003 | Bosnić | Andrea | 4 |");
	}
	
	@Test
	public void testSpacesIgnorable4() {
		String input = "query lastName =    \"Bosnić\"";
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.size(), 4);
		assertEquals(output.get(1), "| 0000000003 | Bosnić | Andrea | 4 |");
	}
	
	@Test
	public void testWrongQuery() {
		String input = "Query lastName = Bosnić\"\"";
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.get(0), "Wrong query.");
	}
	
	@Test
	public void testWrongQuery2() {
		String input = "queryy lastName = Bosnić\"\"";
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.get(0), "Wrong query.");
	}
	
	@Test
	public void testWrongFieldName() {
		String input = "query LastName = Bosnić\"\"";
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.get(0), "Wrong query.");
	}
	
	@Test
	public void testWrongFieldName2() {
		String input = "query lastNamee = Bosnić\"\"";
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.get(0), "Wrong query.");
	}
	
	@Test
	public void testWrongEqualSign() {
		String input = "query lastName == \"Bosnić\"";
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.get(0), "Wrong query.");
	}
	
	@Test
	public void testGetAll() {
		String input = "query jmbag LIKE \"*\"";
		
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.size(), 66);
	}
	
	@Test
	public void testUnclosedStringLiteral() {
		String input = "query lastName == \"Bosnić";
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.get(0), "Wrong query.");
	}
	
	@Test
	public void testStringWithoutQuotes() {
		String input = "query lastName == Bosnić";
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.get(0), "Wrong query.");
	}
	
	@Test
	public void testWrongExpression() {
		String input = "Štefica lastName == \"Bosnić\"";
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.get(0), "Wrong query.");
	}
	
	@Test
	public void testTwoWildcards() {
		String input = "query lastName LIKE \"Bos**\"";
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.get(0), "Records selected: 0\n");
	}
	
	@Test
	public void testTwoWildcards2() {
		String input = "query lastName LIKE \"**\"";
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.get(0), "Records selected: 0\n");
	}
	
	@Test
	public void testTwoWildcards3() {
		String input = "query lastName LIKE \"**Bos\"";
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.get(0), "Records selected: 0\n");
	}
	
	@Test
	public void testTwoWildcards4() {
		String input = "query lastName LIKE \"B*os*\"";
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.get(0), "Records selected: 0\n");
	}
	
	@Test
	public void testTwoWildcards5() {
		String input = "query lastName LIKE \"B***\"";
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.get(0), "Records selected: 0\n");
	}
	
	@Test
	public void testTwoWildcards6() {
		String input = "query lastName LIKE \"B*os*s\"";
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.get(0), "Records selected: 0\n");
	}
	
	@Test
	public void testLIKE() {
		String input = "query lastName Like \"Bos*\"";
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.get(0), "Wrong query.");
	}
	
	@Test
	public void testMoreConditions() {
		String input = "query jmbag LIKE \"000000000*\" and firstName LIKE \"M*\"";
		
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.size(), 6);
		
		assertEquals(output.get(1), "| 0000000001 | Akšamović | Marin | 2 |");
		assertEquals(output.get(2), "| 0000000004 | Božić     | Marin | 5 |");
		assertEquals(output.get(3), "| 0000000008 | Ćurić     | Marko | 5 |");
	}
	
	@Test
	public void testLikeWithoutWildcard() {
		String input = "query jmbag LIKE \"000000000*\" and firstName LIKE \"Marin\"";
		
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.size(), 5);
		
		assertEquals(output.get(1), "| 0000000001 | Akšamović | Marin | 2 |");
		assertEquals(output.get(2), "| 0000000004 | Božić     | Marin | 5 |");
	}
	
	@Test
	public void testQueryIsEmpty() {
		String input = "query lastName = \"Bosnić\" and  lastName != \"Bosnić\"";
		
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "Records selected: 0\n");
	}
	
	@Test
	public void testAndCaseInsensitive() {
		String input = "query lastName = \"Bosnić\" AnD  lastName != \"Bosnić\"";
		
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "Records selected: 0\n");
	}
	
	@Test
	public void testExtraAnd() {
		String input = "query lastName = \"Bosnić\" AnD  lastName != \"Bosnić\" and";
		
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "Wrong query.");
	}
	
	@Test
	public void testMissingAnd() {
		String input = "query lastName = \"Bosnić\"  lastName != \"Bosnić\"";
		
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "Wrong query.");
	}
	
	@Test
	public void testExtraAndAtMiddle() {
		String input = "query lastName = \"Bosnić\" AnD and lastName != \"Bosnić\"";
		
		List<String> output = StudentDB.getOutput(input, db);
		
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "Wrong query.");
	}
}
