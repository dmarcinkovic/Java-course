package hr.fer.zemris.java.hw05.db.demo;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.QueryFilter;
import hr.fer.zemris.java.hw05.db.QueryParser;
import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * Demo example to test all classes in this package. 
 * @author david
 *
 */
public class Demo {
	
	/**
	 * Method invoked when running the program. 
	 * @param args Arguments provided via command line. In this program they are not used.
	 */
	public static void main(String[] args) {
		List<String> list = new ArrayList<>();

		list.add("0036000	Perić	Pero	5");
		list.add("0036001	Josip	Josipovic	4");
		list.add("0036002	Štefica	Štefić	1");
		list.add("0036242	Jasna	Jasnić	4");
		list.add("0036123	Perić	Pero	2");
		list.add("0036124	Horvat	Pero	3");
		list.add("0036314	Josipović	Pero	5");

		StudentDatabase db = new StudentDatabase(list);
		//QueryParser parser = new QueryParser("jmbag = \"0036000\"");
		QueryParser parser = new QueryParser("firstName = \"Pero\" and jmbag LIKE \"00361*\"");
		
		if (parser.isDirectQuery()) {
			StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
			System.out.println(r.getFirstName() + " " + r.getLastName() + " " + r.getJmbag());
			ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.JMBAG, "0036*",
					ComparisonOperators.LIKE);
			expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(r), expr.getLiteral());
		} else {
			for (StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
				System.out.println(r.getFirstName() + " " + r.getLastName() + " " + r.getJmbag());
				ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.JMBAG, "0036*",
						ComparisonOperators.LIKE);
				expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(r), expr.getLiteral());
			}
		}
	}
}
