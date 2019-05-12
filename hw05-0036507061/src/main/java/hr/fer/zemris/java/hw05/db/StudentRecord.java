package hr.fer.zemris.java.hw05.db;

/**
 * Represents records for each student. Student has its first name, last name,
 * final grade and jmbag.
 * 
 * @author david
 *
 */
public class StudentRecord {
	/**
	 * First name of the student.
	 */
	private String firstName;

	/**
	 * Last name of the student.
	 */
	private String lastName;

	/**
	 * Final grade of the student.
	 */
	private int finalGrade;

	/**
	 * Jmbag of the student.
	 */
	private String jmbag;

	/**
	 * Constructor that initialize fields.
	 * 
	 * @param firstName  First name of the student.
	 * @param lastName   Last name of the student.
	 * @param finalGrade Final grade of the student.
	 * @param jmbag      Jmbag of the student.
	 */
	public StudentRecord(String firstName, String lastName, int finalGrade, String jmbag) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.finalGrade = finalGrade;
		this.jmbag = jmbag;
	}

	/**
	 * Return first name of the student.
	 * 
	 * @return first name of the student.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns last name of the student.
	 * 
	 * @return last name of the student.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns jmbag.
	 * 
	 * @return jmbag.
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns final grade.
	 * 
	 * @return final grade.
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
}
