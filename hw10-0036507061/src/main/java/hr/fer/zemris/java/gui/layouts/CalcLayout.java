package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class represents one layout manager. This layout manager is used for
 * making calculators. It consists of 7 columns and 5 rows. Component with the
 * row and column equal to 1 has width of 5 columns. All components that will be
 * arranged with this layout manager have equal width and height, except one
 * with row and column 1 as already explained.
 * 
 * @author david
 *
 */
public class CalcLayout implements LayoutManager2 {
	/**
	 * Gap between components.
	 */
	private int gap;

	/**
	 * Number of rows this layout consist of.
	 */
	private final int ROWS = 5;

	/**
	 * Number of columns this layout consist of.
	 */
	private final int COLS = 7;

	/**
	 * Number of columns component with row and column equal to one consist of.
	 */
	private final int FIRST_COMPONENT_COLS = 5;

	/**
	 * Component with row and column equal to one.
	 */
	private Component firstComponent;

	/**
	 * Map that connect components with their constraints.
	 */
	private Map<Component, RCPosition> map;

	/**
	 * Map that connect constrains with components.
	 */
	private Map<RCPosition, Component> map2;

	/**
	 * Initialize gap between components in the container.
	 * 
	 * @param gap Gap.
	 */
	public CalcLayout(int gap) {
		this.gap = gap;
		map = new HashMap<>();
		map2 = new HashMap<>();
	}

	/**
	 * Initializes gap to default value 0.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws UnsupportedOperationException if this method is invoked.
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
		RCPosition postion = map.get(comp);
		map.remove(comp);
		map2.remove(postion);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		List<Dimension> list = Arrays.asList(parent.getComponents()).stream()
				.filter(t -> t.equals(firstComponent) == false).map(t -> t.getPreferredSize())
				.collect(Collectors.toList());
		return getMaxDimensions(list, firstComponent == null ? null : firstComponent.getPreferredSize());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension maximumLayoutSize(Container target) {
		List<Dimension> list = Arrays.asList(target.getComponents()).stream()
				.filter(t -> t.equals(firstComponent) == false).map(t -> t.getMaximumSize())
				.collect(Collectors.toList());
		return getMaxDimensions(list, firstComponent == null ? null : firstComponent.getMaximumSize());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		List<Dimension> list = Arrays.asList(parent.getComponents()).stream()
				.filter(t -> t.equals(firstComponent) == false).map(t -> t.getMinimumSize())
				.collect(Collectors.toList());
		return getMaxDimensions(list, firstComponent == null ? null : firstComponent.getMinimumSize());
	}

	/**
	 * Returns max dimensions of all components in this layout.
	 * 
	 * @param list List containing all dimensions of components.
	 * @param dim  Dimension of the component with row and column equal to one.
	 * @return Max dimensions of all components in this layout.
	 */
	private Dimension getMaxDimensions(List<Dimension> list, Dimension dim) {
		double maxWidth = 0;
		double maxHeight = 0;

		if (dim != null) {
			maxWidth = (double) (dim.getWidth() - (FIRST_COMPONENT_COLS - 1) * gap) / FIRST_COMPONENT_COLS;
		}

		for (Dimension dimension : list) {
			if (dimension.getWidth() > maxWidth) {
				maxWidth = (int) dimension.getWidth();
			}

			if (dimension.getHeight() > maxHeight) {
				maxHeight = (int) dimension.getHeight();
			}
		}

		return new Dimension((int) (COLS * maxWidth + (COLS - 1) * gap), (int) (ROWS * maxHeight + (ROWS - 1) * gap));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();

		int maxWidth = parent.getWidth() - insets.left - insets.right;
		int maxHeight = parent.getHeight() - insets.bottom - insets.top;

		setComponents(parent.getComponents(), maxWidth - (COLS - 1) * gap, maxHeight - (ROWS - 1) * gap, insets);
	}

	/**
	 * Sets components to their position according to constrains. This layout
	 * accepts constrains as class that encapsulates row and column.
	 * 
	 * @param components Components to be arranged into container.
	 * @param w          Width of the each component.
	 * @param h          Height of the each component.
	 * @param maxWidth   Width of the container excluding insets.
	 */
	private void setComponents(Component[] components, int maxWidth, int maxHeight, Insets insets) { 
		for (int i = 0; i < components.length; i++) {
			int row = map.get(components[i]).getRow();
			int col = map.get(components[i]).getCol();

			int[] h = getH(maxHeight);
			int[] w = getW(maxWidth);

			int x = getX(w, col, insets.left);
			int y = getY(h, row, insets.top);

			if (row == 1 && col == 1) {
				components[i].setBounds(x, y, w[4] + 4 * gap, h[0]);
			} else if (row == 1) {
				components[i].setBounds(x, y, w[col - 1] - w[col - 2], h[0]);
			} else if (col == 1) {
				components[i].setBounds(x, y, w[0], h[row - 1] - h[row - 2]);
			} else {
				components[i].setBounds(x, y, w[col - 1] - w[col - 2], h[row - 1] - h[row - 2]);
			}
		}
	}

	/**
	 * Return y coordinate of left up corner of component.
	 * 
	 * @param h   Height of the component.
	 * @param row Row in which component will be placed.
	 * @return Y coordinate of left up corner of component.
	 */
	private int getY(int[] h, int row, int insetY) {
		if (row == 1) {
			return insetY;
		}

		return h[row - 2] + (row - 1) * gap + insetY;
	}

	/**
	 * Returns x coordinate of left up corner of component.
	 * 
	 * @param w   Width of the component.
	 * @param col Column in which component will be placed.
	 * @return X coordinate of left up corner of component.
	 */
	private int getX(int[] w, int col, int insetX) {
		if (col == 1) {
			return insetX;
		}
		return w[col - 2] + (col - 1) * gap + insetX;
	}

	/**
	 * Returns an array that contains height for every row.
	 * 
	 * @param maxHeight Height of the container without insets. This is the height
	 *                  of the available space of container.
	 * @return An array that contains height for every row.
	 */
	private int[] getH(int maxHeight) {
		int reminder = maxHeight % ROWS;
		int h = maxHeight / ROWS;

		int[] array = new int[ROWS];
		Arrays.fill(array, h);

		switch (reminder) {
		case 3:
			array[0]++;
			array[4]++;
		case 1:
			array[2]++;
			break;
		case 4:
			array[0]++;
			array[4]++;
		case 2:
			array[1]++;
			array[3]++;
			break;
		}

		for (int i = 1; i < array.length; i++) {
			array[i] += array[i - 1];
		}

		return array;
	}

	/**
	 * Returns an array that contains width for every column.
	 * 
	 * @param maxWidth Width of the container without insets. This is the width of
	 *                 the available space of container.
	 * @return An array that contains width for every column.
	 */
	private int[] getW(int maxWidth) {
		int reminder = maxWidth % COLS;
		int w = maxWidth / COLS;

		int[] array = new int[COLS];
		Arrays.fill(array, w);

		switch (reminder) {
		case 5:
			array[0]++;
			array[6]++;
		case 3:
			array[1]++;
			array[5]++;
		case 1:
			array[3]++;
			break;

		case 6:
			array[1]++;
			array[5]++;
		case 4:
			array[0]++;
			array[6]++;
		case 2:
			array[2]++;
			array[4]++;
			break;
		}

		for (int i = 1; i < array.length; i++) {
			array[i] += array[i - 1];
		}

		return array;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws UnsupportedOperationException if constraints is not RCPosition.
	 * @throws CalcLayoutException           If row and column are not in required
	 *                                       range. I.e. row must be less than or
	 *                                       equal to 5, column must be less than or
	 *                                       equal to 7 and Components with row
	 *                                       equal to one and column equal to 2, 3,
	 *                                       4, 5 should not be used.
	 * @throws NullPointerException          if comp is null.
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (!(constraints instanceof RCPosition) && !(constraints instanceof String)) {
			throw new UnsupportedOperationException();
		}
		Objects.requireNonNull(comp);

		RCPosition position = null;

		if (constraints instanceof String) {
			position = getPosition((String) constraints);
		} else {
			position = (RCPosition) constraints;
		}

		if (position.getRow() < 1 || position.getRow() > 5) {
			throw new CalcLayoutException();
		} else if (position.getCol() < 1 || position.getCol() > 7) {
			throw new CalcLayoutException();
		} else if (position.getRow() == 1 && (position.getCol() > 1 && position.getCol() < 6)) {
			throw new CalcLayoutException();
		}

		if (position.getRow() == 1 && position.getCol() == 1) {
			firstComponent = comp;
		}

		Component currentComponent = map2.get(position);

		if (currentComponent != null && currentComponent != comp) {
			throw new CalcLayoutException();
		}

		map.put(comp, position);
		map2.put(position, comp);
	}

	/**
	 * Parses given String s and returns instance of RCPosition class.
	 * 
	 * @param s String to be parsed.
	 * @return Obtained RCPosition.
	 * @throws CalcLayoutException if String cannot be parsed into RCPosition.
	 */
	private RCPosition getPosition(String s) {
		int index = s.indexOf(',');

		try {
			int row = Integer.parseInt(s.substring(0, index).trim());
			int col = Integer.parseInt(s.substring(index + 1).trim());
			return new RCPosition(row, col);
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			throw new CalcLayoutException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void invalidateLayout(Container target) {

	}
}
