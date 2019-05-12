package hr.fer.zemris.java.hw05.db;

/**
 * Strategy that is responsible for obtaining a requested field value from given
 * StudentRecord. There are three concrete strategies. One for each string field
 * of StudentRecord class.
 * 
 * @author david
 *
 */
public interface IFieldValueGetter {

	/**
	 * Method that returns one field of StudentRecord class.
	 * 
	 * @param record Reference to StudentRecord class.
	 * @return Specified field from StudentRecord class.
	 * @throws NullPointerException if record is null.
	 */
	public String get(StudentRecord record);
}
