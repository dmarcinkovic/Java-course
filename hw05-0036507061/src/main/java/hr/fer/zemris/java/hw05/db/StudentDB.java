package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw05.db.lexer.LexerException;

/**
 * Represents student database. This database supports only one command named
 * query(this command is case sensitive). After that keyword comes the name of
 * the field. Fields are firstName, lastName and jmbag. After that comes one
 * operator and string literal. For example, this is correct query : query jmbag
 * = "0000".
 * 
 * @author david
 *
 */
public class StudentDB {

	/**
	 * Method invoked when running the program. Method reads queries from command
	 * line till 'exit' command is not received. After that method prints table
	 * produced by given query.
	 * 
	 * @param args Arguments provided via command line. In this program they are not
	 *             used.
	 */
	public static void main(String[] args) {
		StudentDatabase db = new StudentDatabase(getDatabase());
		Scanner scanner = new Scanner(System.in);
		
		String input = null;

		while (true) {
			System.out.print("> ");
			input = scanner.nextLine();

			if (input.equals("exit")) {
				break;
			}
			
			print(input, db);
		} 
		
		System.out.println("Goodbye!");

		scanner.close();
	}
	
	/**
	 * Method to print result from query. 
	 * @param input String representing query that user has typed.
	 * @param db Database of students.
	 */
	public static void print(String input, StudentDatabase db) {
		List<String> output = getOutput(input, db);
		
		output.forEach(System.out::println);
	}
	
	/**
	 * Method to get list of Strings. Each string represent one row of the table. 
	 * @param input String representing query that user has typed.
	 * @param db Database of students.
	 * @return List of Strings.
	 */
	public static List<String> getOutput(String input, StudentDatabase db) {
		List<String> output = new ArrayList<>();
		
		try {
			input = removeQueryKeyword(input);
		} catch (IllegalArgumentException e) {
			output.add("Wrong query.");
			return output;
		}
		
		try {
			QueryParser parser = new QueryParser(input);
			List<StudentRecord> records = db.filter(new QueryFilter(parser.getQuery()));
			
			output = RecordFormatter.format(records);
			
			if (parser.isDirectQuery()) {
				output.add(0, "Using index for record retrieval.");
			}
		} catch (ParserException | LexerException e) {
			output.add("Wrong query.");
		}
		return output;
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

	/**
	 * Method to remove query keyword from input.
	 * 
	 * @param input Given input.
	 * @return Input without the keyword query.
	 * @throws IllegalArgumentException if query keyword is not written correctly.
	 */
	private static String removeQueryKeyword(String input) {
		input = input.trim();
		String[] arr = input.split("\\s+", 2);

		if (!arr[0].equals("query")) {
			throw new IllegalArgumentException();
		}
		return arr[1];
	}
}
