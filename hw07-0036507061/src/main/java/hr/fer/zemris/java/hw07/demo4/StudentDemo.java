package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * StudentDemo class.
 * 
 * @author david
 *
 */
public class StudentDemo {

	/**
	 * Method invoked when running the program. This method reads info from
	 * studenti.txt creates list of StudentRecords.
	 * 
	 * @param args Arguments provided via command line. In this program they are not
	 *             used.
	 */
	public static void main(String[] args) {
		List<String> lines = null;

		try {
			lines = Files.readAllLines(Paths.get("./studenti.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<StudentRecord> records = convert(lines);

		System.out.println("Zadatak 1");
		System.out.println("=========");
		long broj = vratiBodovaViseOd25(records);
		System.out.println(broj);
		System.out.println();

		System.out.println("Zadatak 2");
		System.out.println("=========");

		long broj5 = vratiBrojOdlikasa(records);
		System.out.println(broj5);
		System.out.println();

		System.out.println("Zadatak 3");
		System.out.println("=========");
		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		odlikasi.forEach(System.out::println);
		System.out.println();

		System.out.println("Zadatak 4");
		System.out.println("=========");
		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		odlikasiSortirano.forEach(System.out::println);
		System.out.println();

		System.out.println("Zadatak 5");
		System.out.println("=========");
		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		nepolozeniJMBAGovi.forEach(System.out::println);
		System.out.println();

		System.out.println("Zadatak 6");
		System.out.println("=========");
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);

		for (Map.Entry<Integer, List<StudentRecord>> entries : mapaPoOcjenama.entrySet()) {
			System.out.println("Ocjena: " + entries.getKey());
			List<StudentRecord> list = entries.getValue();
			list.forEach(System.out::println);
			System.out.println();
		}

		System.out.println("Zadatak 7");
		System.out.println("=========");
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		mapaPoOcjenama2.forEach((k, v) -> System.out.println("Ocjena: " + k + " broj puta " + v));
		System.out.println();

		System.out.println("Zadatak 8");
		System.out.println("=========");
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);

		for (Map.Entry<Boolean, List<StudentRecord>> entries : prolazNeprolaz.entrySet()) {
			System.out.println("Prolaz: " + entries.getKey());
			List<StudentRecord> list = entries.getValue();
			list.forEach(System.out::println);
			System.out.println();
		}
	}

	/**
	 * Returns the number of students whose sum of exam scores is greater than 25.
	 * 
	 * @param records List of students.
	 * @return Number of students whose sum of exam scores is greater than 25.
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream().mapToDouble(t -> t.getLabScore() + t.getMidtermScore() + t.getTermScore())
				.filter(t -> t > 25).count();
	}

	/**
	 * Returns number of students with grade equal to 5.
	 * 
	 * @param records List of students.
	 * @return Number of students with grade equal to 5.
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream().mapToInt(t -> t.getGrade()).filter(t -> t == 5).count();
	}

	/**
	 * Returns students with grade equal to 5.
	 * 
	 * @param records List of students.
	 * @return Students with grade equal to 5.
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(t -> t.getGrade() == 5).collect(Collectors.toList());
	}

	/**
	 * Returns students with grade equal to 5 in sorted order.
	 * 
	 * @param records List of students.
	 * @return Students with grade equal to 5 in sorted order.
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(t -> t.getGrade() == 5)
				.sorted((t1, t2) -> Double.compare(t1.getLabScore() + t1.getMidtermScore() + t1.getTermScore(),
						t2.getLabScore() + t2.getTermScore() + t2.getMidtermScore()))
				.collect(Collectors.toList());
	}

	/**
	 * Returns the student's jmbag who has failed.
	 * 
	 * @param records List of students.
	 * @return Student's jmbag who has failed.
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream().filter(t -> t.getGrade() == 1).map(t -> t.getJmbag()).collect(Collectors.toList());
	}

	/**
	 * Returns map with grades as keys and Students as values.
	 * 
	 * @param records List of students.
	 * @return Map with grades as keys and Students as values.
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.groupingBy(t -> t.getGrade()));
	}

	/**
	 * Returns map with grades as keys and number of students with that grade as
	 * value.
	 * 
	 * @param records List of students.
	 * @return Map with grades as keys and number of students with that grade as
	 *         value.
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.toMap(t -> t.getGrade(), t -> 1, (k, v) -> k + 1));
	}

	/**
	 * Groups students in two groups. First group is students that have failed the
	 * exam and second group is students that have passed the exam.
	 * 
	 * @param records List of students.
	 * @return Map with students who have passed and failed the exam.
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream().collect(Collectors.partitioningBy(t -> t.getGrade() > 1));
	}

	/**
	 * Converts list of string to list of StudentRecords.
	 * 
	 * @param lines List of Strings.
	 * @return List of StudentRecords.
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> list = new ArrayList<>();

		for (String s : lines) {
			if (s.isEmpty()) {
				continue;
			}
			String[] studentRecord = s.split("\t");
			list.add(getStudentRecord(studentRecord));
		}

		return list;
	}

	/**
	 * Returns StudentRecord from array of Strings.
	 * 
	 * @param record Array of Strings.
	 * @return StudentRecord.
	 */
	private static StudentRecord getStudentRecord(String[] record) {
		String jmbag = record[0];
		String lastName = record[1];
		String firstName = record[2];
		double midtermScore = Double.parseDouble(record[3]);
		double termScore = Double.parseDouble(record[4]);
		double labScore = Double.parseDouble(record[5]);
		int grade = Integer.parseInt(record[6]);

		return new StudentRecord(jmbag, lastName, firstName, midtermScore, termScore, labScore, grade);
	}
}
