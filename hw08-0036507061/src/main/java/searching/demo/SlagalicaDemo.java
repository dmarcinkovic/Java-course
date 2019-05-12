package searching.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;

/**
 * Example program to test if algorithms for solving puzzle are working
 * correctly.
 * 
 * @author david
 *
 */
public class SlagalicaDemo {

	/**
	 * Method invoked when running the program. This method prints all steps for
	 * solving the puzzle.
	 * 
	 * @param args Arguments provided via command line. In this example they are not
	 *             used.
	 */
	public static void main(String[] args) {

		Slagalica slagalica = new Slagalica(new KonfiguracijaSlagalice(new int[]  {1,6,4,5,0,2,8,7,3} ));
		Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfsv(slagalica, slagalica, slagalica);

		if (rješenje == null) {
			System.out.println("Nisam uspio pronaći rješenje.");
		} else {
			System.out.println("Imam rješenje. Broj poteza je: " + rješenje.getCost());

			List<KonfiguracijaSlagalice> lista = new ArrayList<>();
			Node<KonfiguracijaSlagalice> trenutni = rješenje;

			while (trenutni != null) {
				lista.add(trenutni.getState());
				trenutni = trenutni.getParent();
			}

			Collections.reverse(lista);

			lista.stream().forEach(k -> {
				System.out.println(k);
				System.out.println();
			});
		}
	}
}