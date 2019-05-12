package coloring.demo;

import java.util.Arrays;

import coloring.algorithms.Coloring;
import coloring.algorithms.Pixel;
import coloring.algorithms.SubspaceExploreUtil;
import marcupic.opjj.statespace.coloring.FillAlgorithm;
import marcupic.opjj.statespace.coloring.FillApp;
import marcupic.opjj.statespace.coloring.Picture;

/**
 * 
 * Program that starts new window and allow user to color the given picture.
 * User is also allowed to choose the coloring algorithm. Some of the available
 * coloring algorithms are depth-first and breadth-first search algorithms.
 * 
 * @author david
 *
 */
public class Bojanje2 {

	/**
	 * Method invoked when running the program. This method runs simple coloring
	 * application.
	 * 
	 * @param args Arguments provided via command line. In this program they are not
	 *             used.
	 */
	public static void main(String[] args) {
		FillApp.run(FillApp.OWL, Arrays.asList(bfs, dfs, bfsv));
	}

	/**
	 * Implements breadth-first search algorithm.
	 */
	private static final FillAlgorithm bfs = new FillAlgorithm() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getAlgorithmTitle() {
			return "Moj bfs!";
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.bfs(col, col, col, col);
		}
	};

	/**
	 * Implements depth-first search algorithm.
	 */
	private static final FillAlgorithm dfs = new FillAlgorithm() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getAlgorithmTitle() {
			return "Moj dfs!";
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.dfs(col, col, col, col);
		}
	};

	/**
	 * Implements breadth-first algorithm that keeps track of visited pixels.
	 */
	private static final FillAlgorithm bfsv = new FillAlgorithm() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getAlgorithmTitle() {
			return "Moj bfsv!";
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.bfsv(col, col, col, col);
		}
	};
}
