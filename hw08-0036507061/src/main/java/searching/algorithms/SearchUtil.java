package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Implements two searching algorithms that are used to solve the puzzle. First
 * one is breadth-first search and second one is breadth-first search that keeps
 * track of visited states.
 * 
 * @author david
 *
 */
public class SearchUtil {

	/**
	 * Implements breadth-first search which keeps tracks of visited states.
	 * 
	 * @param s0   Supplier to get start configuration.
	 * @param succ Returns all successors of current state.
	 * @param goal Tests if current state state is equal to goal state.
	 * @return Returns node that stores all the steps for solving the puzzle.
	 */
	public static <S> Node<S> bfsv(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		List<Node<S>> toExplore = new LinkedList<>();
		Set<S> visited = new HashSet<>();

		Node<S> currentNode = new Node<S>(null, s0.get(), 0);
		toExplore.add(currentNode);
		visited.add(currentNode.getState());

		while (!toExplore.isEmpty()) {
			currentNode = toExplore.remove(0);

			if (!goal.test(currentNode.getState())) {
				return currentNode;
			}

			getListOfNodes(succ.apply(currentNode.getState()), currentNode.getCost(), currentNode, visited, toExplore);
		}

		return null;
	}

	/**
	 * Returns list of nodes that have not been visited yet.
	 * 
	 * @param apply   List of transitions.
	 * @param cost    Current cost.
	 * @param parent  Parent node.
	 * @param visited visited configurations.
	 * @param nodes   Nodes to be visited.
	 */
	private static <S> void getListOfNodes(List<Transition<S>> apply, double cost, Node<S> parent, Set<S> visited,
			List<Node<S>> nodes) {

		for (Transition<S> transition : apply) {
			S state = transition.getState();

			if (visited.contains(state)) {
				continue;
			}

			nodes.add(new Node<S>(parent, state, cost + transition.getCost()));
			visited.add(state);
		}
	}

	/**
	 * Implements breadth-first algorithm.
	 * 
	 * @param s0   Supplier to get start configuration.
	 * @param succ Returns all successors of current state.
	 * @param goal Tests if current state state is equal to goal state.
	 * @return Returns node that stores all the steps for solving the puzzle.
	 */
	public static <S> Node<S> bfs(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		List<Node<S>> toExplore = new LinkedList<>();

		Node<S> currentNode = new Node<S>(null, s0.get(), 0);
		toExplore.add(currentNode);

		while (!toExplore.isEmpty()) {
			currentNode = toExplore.remove(0);

			if (!goal.test(currentNode.getState())) {
				return currentNode;
			}

			List<Node<S>> listOfNodes = getListOfNodes(succ.apply(currentNode.getState()), currentNode.getCost(),
					currentNode);
			toExplore.addAll(listOfNodes);
		}

		return null;
	}

	/**
	 * Returns all neighbors of current configuration.
	 * 
	 * @param apply  List of transitions.
	 * @param cost   Current cost.
	 * @param parent Parent node.
	 * @return all neighbors of current configuraion.
	 */
	private static <S> List<Node<S>> getListOfNodes(List<Transition<S>> apply, double cost, Node<S> parent) {
		List<Node<S>> nodes = new LinkedList<Node<S>>();

		for (Transition<S> transition : apply) {
			nodes.add(new Node<S>(parent, transition.getState(), cost + transition.getCost()));
		}

		return nodes;
	}
}
