package hr.fer.zemris.java.hw07.demo4;

/**
 * Class that stores information about the student.
 * 
 * @author david
 *
 */
public class StudentRecord {
	/**
	 * Student's jmbag.
	 */
	private String jmbag;

	/**
	 * Student's last name.
	 */
	private String lastName;

	/**
	 * Student's first name.
	 */
	private String firstName;

	/**
	 * Student's midterm exam score.
	 */
	private double midtermScore;

	/**
	 * Student's final exam score.
	 */
	private double termScore;

	/**
	 * Student's lab exercises score.
	 */
	private double labScore;

	/**
	 * Student's grade.
	 */
	private int grade;

	/**
	 * Constructor to initialize private fields.
	 * 
	 * @param jmbag        Student's jmbag.
	 * @param lastName     Student's last name.
	 * @param firstName    Student's first name.
	 * @param midtermScore Student's midterm exam score.
	 * @param termScore    Student's lab exercises score.
	 * @param labScore     Student's lab exercises score.
	 * @param grade        Student's grade.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, double midtermScore, double termScore,
			double labScore, int grade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.midtermScore = midtermScore;
		this.termScore = termScore;
		this.labScore = labScore;
		this.grade = grade;
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
	 * Returns last name.
	 * 
	 * @return last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns first name.
	 * 
	 * @return first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns midterm exam score.
	 * 
	 * @return midterm exam score.
	 */
	public double getMidtermScore() {
		return midtermScore;
	}

	/**
	 * Returns final exam score.
	 * 
	 * @return final exam score.
	 */
	public double getTermScore() {
		return termScore;
	}

	/**
	 * 
	 * Returns the points earned in laboratory exercises.
	 * 
	 * @return points earned in laboratory exercises.
	 */
	public double getLabScore() {
		return labScore;
	}

	/**
	 * Returns grade.
	 * 
	 * @return grade.
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append(jmbag).append("\t").append(lastName).append("\t").append(firstName).append("\t")
				.append(midtermScore).append("\t").append(termScore).append("\t").append(labScore).append("\t")
				.append(grade).toString();
	}

}
