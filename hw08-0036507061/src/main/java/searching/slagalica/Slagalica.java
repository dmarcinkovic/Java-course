package searching.slagalica;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;

/**
 * Represents puzzle. This class implements all required interfaces to
 * implements searching algorithms.
 * 
 * @author david
 *
 */
public class Slagalica implements Predicate<KonfiguracijaSlagalice>, Supplier<KonfiguracijaSlagalice>,
		Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>> {
	/**
	 * Start configuration.
	 */
	private KonfiguracijaSlagalice startConfiguration;

	/**
	 * Constructor to initialize start configuration of puzzle.
	 * 
	 * @param startConfiguration
	 */
	public Slagalica(KonfiguracijaSlagalice startConfiguration) {
		this.startConfiguration = startConfiguration;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice t) {
		List<Transition<KonfiguracijaSlagalice>> list = new LinkedList<>();

		int[] configuration = t.getPolje();
		int spaceIndex = t.indexOfSpace();

		add(list, configuration, spaceIndex, 1);
		add(list, configuration, spaceIndex, 2);
		add(list, configuration, spaceIndex, 3);
		add(list, configuration, spaceIndex, 4);

		return list;
	}

	/**
	 * Adds transition to list.
	 * 
	 * @param list          List in which transitions will be added.
	 * @param configuration One puzzle configuration.
	 * @param spaceIndex    Index of space in puzzle.
	 * @param index         Index of transition.
	 */
	private void add(List<Transition<KonfiguracijaSlagalice>> list, int[] configuration, int spaceIndex, int index) {
		Transition<KonfiguracijaSlagalice> transition = getTransition(index, spaceIndex, configuration);
		if (transition != null) {
			list.add(transition);
		}
	}

	/**
	 * Returns transitions from current configuration.
	 * 
	 * @param neighbor      Index of neighbor.
	 * @param spaceIndex    Index of space in puzzle.
	 * @param configuration Current configuration.
	 * @return Required transition.
	 */
	private Transition<KonfiguracijaSlagalice> getTransition(int neighbor, int spaceIndex, int[] configuration) {
		int[] newConfiguration = getNeighbor(configuration, neighbor, spaceIndex);

		if (newConfiguration == null) {
			return null;
		}

		KonfiguracijaSlagalice conf = new KonfiguracijaSlagalice(newConfiguration);
		return new Transition<KonfiguracijaSlagalice>(conf, 1);
	}

	/**
	 * Returns neighbor with given index.
	 * 
	 * @param configuration Current configuration.
	 * @param i             Index of neighbor.
	 * @param spaceIndex    Index of space in puzzle.
	 * @return Array of integers representing new configuration.
	 */
	private int[] getNeighbor(int[] configuration, int i, int spaceIndex) {
		int row = getRow(spaceIndex);
		int col = getCol(spaceIndex);
		int[] newConfiguration = Arrays.copyOf(configuration, configuration.length);

		int newIndex = 0;
		switch (i) {
		case 1:
			newIndex = getIndex(row - 1, col);
			break;
		case 2:
			newIndex = getIndex(row, col + 1);
			break;
		case 3:
			newIndex = getIndex(row, col - 1);
			break;
		case 4:
			newIndex = getIndex(row + 1, col);
			break;
		}

		if (newIndex < 0 || newIndex >= configuration.length) {
			return null;
		}

		swap(newConfiguration, spaceIndex, newIndex);

		return newConfiguration;
	}

	/**
	 * Swaps two elements in array.
	 * 
	 * @param array Array.
	 * @param i     First index.
	 * @param j     Second index.
	 */
	private void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	/**
	 * Returns row from given index.
	 * 
	 * @param index Given index.
	 * @return Row from given index.
	 */
	private int getRow(int index) {
		return index / 3;
	}

	/**
	 * Returns column from given index.
	 * 
	 * @param index Given index.
	 * @return Column from given index.
	 */
	private int getCol(int index) {
		return index % 3;
	}

	/**
	 * Returns index from given row and column.
	 * 
	 * @param row given row.
	 * @param col given column.
	 * @return Index from given row and column.
	 */
	private int getIndex(int row, int col) {
		if (row < 0 || row >= 3 || col < 0 || col >= 3) {
			return -1;
		}
		return row * 3 + col;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public KonfiguracijaSlagalice get() {
		return startConfiguration;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean test(KonfiguracijaSlagalice t) {
		int[] array = t.getPolje();

		return !Arrays.equals(array, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 });
	}

}
