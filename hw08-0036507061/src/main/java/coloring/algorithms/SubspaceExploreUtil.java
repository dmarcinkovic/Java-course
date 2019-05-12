package coloring.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Class that implements three search algorithm used to color the picture.
 * 
 * @author david
 *
 */
public class SubspaceExploreUtil {

	/**
	 * Implements breadth-first algorithm.
	 * 
	 * @param s0         Used to find the start pixel.
	 * @param process    Processes current pixel.
	 * @param succ       Used to get all the neighbors of current pixel.
	 * @param acceptable Check is current pixel is acceptable.
	 */
	public static <S> void bfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		List<S> toExplore = new LinkedList<>();

		toExplore.add(s0.get());
		while (!toExplore.isEmpty()) {
			S si = toExplore.remove(0);

			if (!acceptable.test(si)) {
				continue;
			}

			process.accept(si);

			toExplore.addAll(succ.apply(si));
		}
	}

	/**
	 * Implements depth-first algorithm.
	 * 
	 * @param s0         Used to find the start pixel.
	 * @param process    Processes current pixel.
	 * @param succ       Used to get all the neighbors of current pixel.
	 * @param acceptable Check is current pixel is acceptable.
	 */
	public static <S> void dfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		List<S> toExplore = new LinkedList<>();

		toExplore.add(s0.get());
		while (!toExplore.isEmpty()) {
			S si = toExplore.remove(0);

			if (!acceptable.test(si)) {
				continue;
			}

			process.accept(si);

			toExplore.addAll(0, succ.apply(si));
		}
	}

	/**
	 * Implements breadth-first algorithm. Unless normal bfs this one keeps track of
	 * visited pixels.
	 * 
	 * @param s0         Used to find the start pixel.
	 * @param process    Processes current pixel.
	 * @param succ       Used to get all the neighbors of current pixel.
	 * @param acceptable Check is current pixel is acceptable.
	 */
	public static <S> void bfsv(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		List<S> toExplore = new LinkedList<>();
		toExplore.add(s0.get());

		Set<S> visited = new HashSet<>();
		visited.add(s0.get());

		while (!toExplore.isEmpty()) {
			S si = toExplore.remove(0);

			if (!acceptable.test(si)) {
				continue;
			}

			process.accept(si);

			List<S> children = succ.apply(si);
			children.removeIf(t -> visited.contains(t));

			toExplore.addAll(children);
			visited.addAll(children);
		}

	}
}
