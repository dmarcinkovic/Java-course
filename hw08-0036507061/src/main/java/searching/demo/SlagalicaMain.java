package searching.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

/**
 * Example program to test if algorithms for solving puzzle are working
 * correctly.
 * 
 * @author david
 *
 */
public class SlagalicaMain {

	/**
	 * Method invoked when running the program. This method prints all steps for
	 * solving the puzzle. After puzzle is solved this method creates window that
	 * represents solution.
	 * 
	 * @param args It expects only one argument: start configuration.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Dear user, you provided wrong number of arguments. Bye.");
			return;
		}

		if (!checkConfiguration(args[0])) {
			System.out.println(
					"Dear user, you provided wrong puzzle configuration. Configuration must have 9 numbers numbered from 0 to 8.");
			return;
		}

		Slagalica slagalica = new Slagalica(new KonfiguracijaSlagalice(getConfiguration(args[0])));
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

			SlagalicaViewer.display(rješenje);
		}
	}

	/**
	 * Creates configuration from given string.
	 * 
	 * @param s Given String.
	 * @return Created configuration.
	 */
	private static int[] getConfiguration(String s) {
		int[] configuration = new int[9];

		for (int i = 0, n = s.length(); i < n; i++) {
			int index = Integer.parseInt(String.valueOf(s.charAt(i)));
			configuration[i] = index;
		}

		return configuration;
	}

	/**
	 * Checks if configuration provided as program argument is valid.
	 * 
	 * @param configuration Configuration provided as program argument.
	 * @return True if configuration provided as program argument is valid.
	 */
	private static boolean checkConfiguration(String configuration) {
		configuration = configuration.trim();
		if (configuration.length() != 9) {
			return false;
		}

		boolean[] array = new boolean[9];
		for (int i = 0, n = configuration.length(); i < n; i++) {
			try {
				int index = Integer.parseInt(String.valueOf(configuration.charAt(i)));
				if (array[index] == true) {
					return false;
				}
				array[index] = true;
			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
				return false;
			}
		}
		return true;
	}
}