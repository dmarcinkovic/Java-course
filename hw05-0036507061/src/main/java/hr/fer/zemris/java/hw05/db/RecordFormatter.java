package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to format records.
 * 
 * @author david
 *
 */
public class RecordFormatter {

	/**
	 * Method that formats records. This method will put all records in the
	 * formatted table.
	 * 
	 * @param records Records to be formatted.
	 * @return List of Strings. Each element in this list contain one row of the
	 *         table.
	 */
	public static List<String> format(List<StudentRecord> records) {
		List<String> output = new ArrayList<>();

		if (records.size() == 0) {
			output.add("Records selected: 0\n");
			return output;
		}

		int maxNameLength = 0;
		int maxLastNameLength = 0;
		int maxJmbagLength = 0;

		for (StudentRecord s : records) {
			if (s.getFirstName().length() > maxNameLength) {
				maxNameLength = s.getFirstName().length();
			}

			if (s.getLastName().length() > maxLastNameLength) {
				maxLastNameLength = s.getLastName().length();
			}

			if (s.getJmbag().length() > maxJmbagLength) {
				maxJmbagLength = s.getJmbag().length();
			}
		}

		output.add(getBorder(maxNameLength, maxLastNameLength, maxJmbagLength));

		for (StudentRecord s : records) {
			output.add(getOneRow(s, maxNameLength, maxLastNameLength, maxJmbagLength));
		}

		output.add(getBorder(maxNameLength, maxLastNameLength, maxJmbagLength));
		output.add("Records selected: " + records.size() + "\n");

		return output;
	}

	/**
	 * Method to get border of table.
	 * 
	 * @param maxNameLength     Max length of the first name in this table.
	 * @param maxLastNameLength Max length of the last name in this table.
	 * @param maxJmbagLength    Max length of the jmbag in this table.
	 * @return String representing border.
	 */
	private static String getBorder(int maxNameLength, int maxLastNameLength, int maxJmbagLength) {
		StringBuilder sb = new StringBuilder();

		sb.append("+=");

		for (int i = 0; i < maxJmbagLength; i++) {
			sb.append("=");
		}

		sb.append("=+=");

		for (int i = 0; i < maxLastNameLength; i++) {
			sb.append("=");
		}

		sb.append("=+=");

		for (int i = 0; i < maxNameLength; i++) {
			sb.append("=");
		}

		sb.append("=+===+");
		return sb.toString();
	}

	/**
	 * Method to get record in one row of the table.
	 * 
	 * @param record Record to be printed.
	 * @return String representation of the record.
	 */
	private static String getOneRow(StudentRecord record, int maxNameLength, int maxLastNameLength,
			int maxJmbagLength) {
		StringBuilder sb = new StringBuilder();

		sb.append("| ");

		int n = maxJmbagLength - record.getJmbag().length();
		sb.append(record.getJmbag()).append(getNSpaces(n)).append(" | ");

		n = maxLastNameLength - record.getLastName().length();
		sb.append(record.getLastName()).append(getNSpaces(n)).append(" | ");

		n = maxNameLength - record.getFirstName().length();
		sb.append(record.getFirstName()).append(getNSpaces(n)).append(" | ");

		sb.append(record.getFinalGrade()).append(" |");
		return sb.toString();
	}

	/**
	 * Returns string that consists from n spaces.
	 * 
	 * @param n Number of spaces that returned string will have.
	 * @return String consisting of n spaces.
	 */
	private static String getNSpaces(int n) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < n; i++) {
			sb.append(" ");
		}

		return sb.toString();
	}
}
