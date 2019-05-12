package hr.fer.zemris.java.hw05.db;

/**
 * Represents field names. Possible field names are: firstName, lastName, jmbag.
 * All other field names are incorrect.
 * 
 * @author david
 *
 */
public class FieldValueGetters {
	
	/**
	 * Represents firstName field.
	 */
	public static IFieldValueGetter FIRST_NAME = s -> {
		if (s == null) {
			throw new NullPointerException();
		}
		return s.getFirstName();
	};

	/**
	 * Represents lastName field.
	 */
	public static IFieldValueGetter LAST_NAME = s -> {
		if (s == null) {
			throw new NullPointerException();
		}
		return s.getLastName();
	};

	/**
	 * Represents jmbag field.
	 */
	public static IFieldValueGetter JMBAG = s -> {
		if (s == null) {
			throw new NullPointerException();
		}
		return s.getJmbag();
	};

}
