package hr.fer.zemris.java.hw05.db;

/**
 * Interface represents model for classes that are used to filter some Student
 * records.
 * 
 * @author david
 *
 */
public interface IFilter {

	/**
	 * Method used to accept records that satisfies some condition.
	 * 
	 * @param record Reference to StudentRecord class.
	 * @return True if records satisfies some specified condition.
	 */
	public boolean accepts(StudentRecord record);
}
