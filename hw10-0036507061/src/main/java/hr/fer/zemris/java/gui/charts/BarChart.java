package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Represents bar chart.A bar chart is a chart or graph that presents
 * categorical data with rectangular bars with heights or lengths proportional
 * to the values that they represent.
 * 
 * @author david
 *
 */
public class BarChart {
	/**
	 * List in that stores data that will be represented as bars.
	 */
	private List<XYValue> list;

	/**
	 * X-axis description.
	 */
	private String xTitle;

	/**
	 * Y-axis description.
	 */
	private String yTitle;

	/**
	 * Minimum value that will be presented by this chart.
	 */
	private int minY;

	/**
	 * Maximum value that will be presented by this chart.
	 */
	private int maxY;

	/**
	 * Gap between numbers. For example if minY = 0 and maxY = 10 and gap equal to 2
	 * then these numbers will be presented next to y-coordinate: 0, 2, 4, 6, 8, 10.
	 */
	private int gap;

	/**
	 * Initialize private field.
	 * 
	 * @param list   List in that stores data that will be represented as bars.
	 * @param xTitle X-axis description.
	 * @param yTitle Y-axis description.
	 * @param minY   Minimum value that will be presented by this chart.
	 * @param maxY   Maximum value that will be presented by this chart.
	 * @param gap    Gap between numbers. For example if minY = 0 and maxY = 10 and
	 *               gap equal to 2 then these numbers will be presented next to
	 *               y-coordinate: 0, 2, 4, 6, 8, 10.
	 * @throws IllegalArgumentException if minY is less that zero or maxY is not
	 *                                  strictly greater than minY.
	 */
	public BarChart(List<XYValue> list, String xTitle, String yTitle, int minY, int maxY, int gap) {
		this.list = list;

		this.xTitle = xTitle;
		this.yTitle = yTitle;

		if (minY < 0) {
			throw new IllegalArgumentException("Miny should not be less than zero.");
		}

		this.minY = minY;

		if (maxY <= minY) {
			throw new IllegalArgumentException("MinX must be strictly greater than minY");
		}

		this.maxY = maxY;

		if (gap == 0) {
			throw new IllegalArgumentException("Gap should not be zero.");
		}

		this.gap = gap;

		if ((maxY - minY) % gap != 0) {
			this.maxY = ((maxY - minY) / gap) * gap + gap;
		}

		checkList();
	}

	/**
	 * Method to check if all y values from list are in valid range.
	 * 
	 * @throws IllegalArgumentException if any element is not in valid range.
	 */
	private void checkList() {
		for (XYValue value : list) {
			if (value.getY() < minY) {
				throw new IllegalArgumentException("Value should not be less than " + minY);
			}
		}
	}

	/**
	 * @return the list
	 */
	public List<XYValue> getList() {
		return list;
	}

	/**
	 * @return the xTitle
	 */
	public String getxTitle() {
		return xTitle;
	}

	/**
	 * @return the yTitle
	 */
	public String getyTitle() {
		return yTitle;
	}

	/**
	 * @return the minY
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * @return the maxY
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * @return the gap
	 */
	public int getGap() {
		return gap;
	}
}
