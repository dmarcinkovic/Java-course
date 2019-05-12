package hr.fer.zemris.java.hw05.db.demo;

import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * Class used to show how FieldValueGetters 
 * @author david
 *
 */
public class FieldValueGetterDemo {
	
	/**
	 * Method invoked when running the program. 
	 * @param args Arguments provided via command line. In this program they are not used.
	 */
	public static void main(String[] args) {
		StudentRecord record = getSomehowOneRecord();
		System.out.println("First name: " + FieldValueGetters.FIRST_NAME.get(record));
		System.out.println("Last name: " + FieldValueGetters.LAST_NAME.get(record));
		System.out.println("JMBAG: " + FieldValueGetters.JMBAG.get(record));
	}
	
	/**
	 * Method to return some instance of StudentRecord class.  
	 * @return Some manually written instance of StudentRecord class. 
	 */
	private static StudentRecord getSomehowOneRecord() {
		return new StudentRecord("Pero", "Peric", 2, "0035000");
	}
}	
