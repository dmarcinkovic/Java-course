package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represent database of student. Database consists of student jmbag, first
 * name, last name and final grade.
 * 
 * @author david
 *
 */
public class StudentDatabase {
	/**
	 * List of all students.
	 */
	private List<StudentRecord> students;

	/**
	 * Map that associate jmbag with students.
	 */
	private Map<String, StudentRecord> index;

	/**
	 * Constructor that initializes database.
	 * 
	 * @param database Given database.
	 * @throws NullPointerException if database is null.
	 */
	public StudentDatabase(List<String> database) {
		if (database == null) {
			throw new NullPointerException();
		}
		
		index = new HashMap<>();
		students = new ArrayList<>();
		
		init(database);
	}

	/**
	 * Method to init database. This method also checks if there are duplicates in
	 * database and checks if given final grade is in correct range(zero to five).
	 * 
	 * @param database
	 */
	private void init(List<String> database) {
		for (String student : database) {
			String[] records = student.split("\t");

			if (records.length != 4) {
				System.out.println("Error in database. Wrong number of elements.");
				System.exit(1);
			}

			int grade = Integer.parseInt(records[3]);

			if (grade < 1 || grade > 5) {
				System.out.println("Grade is out of range.");
				System.exit(1);
			}

			if (index.get(records[0]) != null) {
				System.out.println("Duplicate jmbags.");
				System.exit(1);
			}

			StudentRecord studentRecord = new StudentRecord(records[2], records[1], grade, records[0]);
			students.add(studentRecord);
			index.put(records[0], studentRecord);
		}
	}

	/**
	 * Returns StudentRecords with given jmbag.
	 * 
	 * @param jmbag given jmbag.
	 * @return StudentRecords with given jmbag.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}

	/**
	 * Does filtering. This method will return all StudentRecords that satisfies
	 * given condition.
	 * 
	 * @param filter Object that filters StudentRecords.
	 * @return list of StudentRecotds that satisfies given condition.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> temp = new ArrayList<>();

		for (StudentRecord student : students) {
			if (filter.accepts(student)) {
				temp.add(student);
			}
		}

		return temp;
	}
}
