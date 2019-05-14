package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.List;

import javax.swing.JComponent;

/**
 * Class that represents bar chart component. A bar chart is a chart or graph
 * that presents categorical data with rectangular bars with heights or lengths
 * proportional to the values that they represent.
 * 
 * @author david
 *
 */
public class BarChartComponent extends JComponent {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Color of rectangular bars.
	 */
	private Color color;

	/**
	 * Color of shades. This is used to make shade of rectangular bars.
	 */
	private Color shade;

	/**
	 * Represents color of lines that represents grid. This color have some opacity
	 * that is specified with the alpha value of color.
	 */
	private Color gridColor;

	/**
	 * Bar chart that is presented as this component.
	 */
	private BarChart barChart;

	/**
	 * Padding between some window and axis description.
	 */
	private final int PADDING = 10;

	/**
	 * Stroke with stroke weight equal to two.
	 */
	private final BasicStroke TWO;

	/**
	 * Stroke with stroke weight equal to one. This is default stroke weight.
	 */
	private final BasicStroke ONE;

	/**
	 * Constructor to initialize bar chart and other private fields.
	 * 
	 * @param barChart Bar chart.
	 */
	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
		color = new Color(220, 100, 0);
		shade = new Color(150, 150, 150);
		gridColor = new Color(200, 100, 50, 50);
		TWO = new BasicStroke(2);
		ONE = new BasicStroke(1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics graphics) {
		Graphics2D graph = (Graphics2D) graphics;

		int textHeight = graphics.getFontMetrics().getHeight();

		int startX = getXStart(graph, textHeight);
		int startY = getHeight() - getYStart(graph);

		drawXAxis(graph, startX - 5, startY, getWidth() - 2 * PADDING);
		drawYAxis(graph, startX, startY + 5, 2 * PADDING);

		int yAxisLength = startY - 2 * PADDING;
		int xAxisLength = getWidth() - 2 * PADDING - startX;

		graph.setFont(new Font("default", Font.BOLD, 12));
		drawYDescription(graph, barChart.getyTitle(), yAxisLength, textHeight, startY);
		drawXDescription(graph, barChart.getxTitle(), startX, xAxisLength, getHeight());

		drawYCoordinateNumbers(graph, startX - getMaxWidth(graph) - PADDING, PADDING + textHeight, yAxisLength,
				textHeight);

		drawXCoordinateNumbers(graph, startX, xAxisLength, getHeight() - 2 * PADDING - textHeight);

		makeGrid(graph, startX, xAxisLength, 2 * PADDING, yAxisLength);
		drawCharts(graph, startX, startY, xAxisLength, yAxisLength - 5);

	}

	/**
	 * This method draws rectangular bars with height proportional to the number
	 * that is encapsulated with XYValue class. Bars have light red color and shade
	 * to their right side. Shade is only presented if no other bar covers this
	 * area.
	 * 
	 * @param graphics Used to draw shapes.
	 * @param startX   Start x coordinate of x-axis.
	 * @param startY   Start y-coordinate of y-axis.
	 * @param xLength  Length of the x-axis.
	 * @param yLength  Length of the y-axis.
	 */
	private void drawCharts(Graphics2D graphics, int startX, int startY, int xLength, int yLength) {
		List<XYValue> list = barChart.getList();

		int x = startX;
		int width = xLength / list.size();

		for (XYValue value : list) {
			int y = getChartY(value.getY() - barChart.getMinY(), yLength, startY);
			int height = startY - y;

			drawRectangle(graphics, x, y, width - 1, height);
			x += width;
		}
	}

	/**
	 * Draws rectangular which top left corner have x and y coordinate. Width of
	 * rectangular is equal to width and height of rectangular is equal to height.
	 * Rectangle is filled with red color.
	 * 
	 * @param graph  Used to draw graphics.
	 * @param x      X coordinate of top left corner of rectangular.
	 * @param y      Y coordinate of top left corner of rectangular.
	 * @param width  Width of rectangle.
	 * @param height Height of rectangle.
	 */
	private void drawRectangle(Graphics2D graph, int x, int y, int width, int height) {
		graph.setColor(color);
		graph.fillRect(x, y, width, height);

		graph.setColor(shade);
		graph.fillRect(x + width, y + 3, 4, height - 3);
	}

	/**
	 * Returns the y coordinate of chart bar with specified height of bar.
	 * 
	 * @param height Height of bar.
	 * @param length Length of y-axis.
	 * @param startY Y coordinate of point(0, 0).
	 * @return Y coordinate of chart bar with specified height of bar.
	 */
	private int getChartY(int height, int length, int startY) {
		double oneUnitLength = (double) length / (barChart.getMaxY() - barChart.getMinY());
		return (int) (startY - oneUnitLength * height);
	}

	/**
	 * Creates grid behind bars.
	 * 
	 * @param graphics Used to draw graphics.
	 * @param startX   X coordinate of point(0, 0).
	 * @param xLength  Length of x-axis.
	 * @param startY   Y coordinate of point(0, 0).
	 * @param yLength  Length of y-axis.
	 */
	private void makeGrid(Graphics2D graphics, int startX, int xLength, int startY, int yLength) {
		graphics.setColor(gridColor);
		int yCount = (barChart.getMaxY() - barChart.getMinY()) / barChart.getGap();
		int yGap = yLength / yCount;

		int y = startY + 5;
		for (int i = 0; i < yCount; i++) {
			drawHorizontalLine(graphics, startX - 5, y, startX + xLength);
			y += yGap;
		}

		int xCount = barChart.getList().size();
		int xGap = xLength / xCount;

		int x = startX + xGap;
		for (int i = 0; i < xCount; i++) {
			drawVerticalLine(graphics, x, startY, startY + yLength + 5);
			x += xGap;
		}
		graphics.setColor(Color.BLACK);
	}

	/**
	 * Draws x-axis with stroke equal to two and color equal to grey.
	 * 
	 * @param graphics Used to draw graphics.
	 * @param startX   X coordinate of point(0, 0).
	 * @param startY   Y coordinate of point(0, 0).
	 * @param endX     X coordinate of end of x-axis.
	 */
	private void drawXAxis(Graphics2D graphics, int startX, int startY, int endX) {
		graphics.setColor(shade);
		graphics.setStroke(TWO);

		drawHorizontalLine(graphics, startX, startY, endX);

		graphics.fillPolygon(new int[] { endX, endX, endX + 7 }, new int[] { startY - 6, startY + 6, startY }, 3);

		graphics.setColor(Color.BLACK);
		graphics.setStroke(ONE);
	}

	/**
	 * Draws horizontal line from startX to endX on startY coordinate.
	 * 
	 * @param graphics Used to draw graphics.
	 * @param startX   Start x coordinate of horizontal line.
	 * @param startY   Start y coordinate of horizontal line.
	 * @param endX     End x coordinate of horizontal line.
	 */
	private void drawHorizontalLine(Graphics2D graphics, int startX, int startY, int endX) {
		Shape line = new Line2D.Double(startX, startY, endX, startY);
		graphics.draw(line);
	}

	/**
	 * Draws y-axis with stroke equal to two and color equal to grey.
	 * 
	 * @param graphics Used to draw graphics.
	 * @param startX   X coordinate of point(0, 0).
	 * @param startY   Y coordinate of point(0, 0).
	 * @param endY     End y coordinate of y-axis.
	 */
	private void drawYAxis(Graphics2D graphics, int startX, int startY, int endY) {
		graphics.setColor(shade);
		graphics.setStroke(TWO);

		drawVerticalLine(graphics, startX, startY, endY);

		graphics.fillPolygon(new int[] { startX - 6, startX + 6, startX }, new int[] { endY, endY, endY - 7 }, 3);

		graphics.setColor(Color.BLACK);
		graphics.setStroke(ONE);
	}

	/**
	 * Draws vertical line from startY to endY on startX coordinate.
	 * 
	 * @param graphics Used to draw graphics.
	 * @param startX   Start x coordinate of vertical line.
	 * @param startY   Start y coordinate of vertical line.
	 * @param endY     End y coordinate of vertical line.
	 */
	private void drawVerticalLine(Graphics2D graphics, int startX, int startY, int endY) {
		Shape line = new Line2D.Double(startX, startY, startX, endY);
		graphics.draw(line);
	}

	/**
	 * Draws numbers next to y-axis.
	 * 
	 * @param graphics   Used to draw graphics.
	 * @param startX     X coordinate of point(0, 0).
	 * @param startY     Y coordinate of point(0, 0).
	 * @param lineHeight Length of the y-axis.
	 * @param textHeight Height of text.
	 */
	private void drawYCoordinateNumbers(Graphics2D graphics, int startX, int startY, int lineHeight, int textHeight) {
		int maxY = barChart.getMaxY();
		int minY = barChart.getMinY();
		int countNumbers = (maxY - minY) / barChart.getGap();
		int gap = lineHeight / countNumbers;

		int maxWidth = graphics.getFontMetrics().stringWidth(String.valueOf(maxY));
		
		int y = startY;
		int inc = barChart.getGap();
		for (int i = maxY; i >= minY; i -= inc) {
			String text = String.valueOf(i);
			
			int textWidth = graphics.getFontMetrics().stringWidth(text);
			
			graphics.drawString(text, startX + (maxWidth - textWidth), y + textHeight / 2);

			y += gap;
		}
	}

	/**
	 * Draws numbers next to x-axis.
	 * 
	 * @param graphics Used to draw graphics.
	 * @param startX   X coordinate of point(0, 0).
	 * @param length   Length of the x-axis.
	 * @param y        Y coordinate where the text will be displayed.
	 */
	private void drawXCoordinateNumbers(Graphics2D graphics, int startX, int length, int y) {
		List<XYValue> list = barChart.getList();
		int countNumber = list.size();
		int gap = length / countNumber;

		int x = startX + gap / 2;

		for (int i = 1; i <= countNumber; i++) {
			String number = String.valueOf(list.get(i - 1).getX());
			int textWidth = graphics.getFontMetrics().stringWidth(number);
			graphics.drawString(number, x - textWidth / 2, y);
			x += gap;
		}
	}

	/**
	 * Draws description next to x-axis.
	 * 
	 * @param graphics Used to draw graphics.
	 * @param text     Description that will be displayed.
	 * @param startX   X coordinate of point(0, 0).
	 * @param length   Length of the x-axis.
	 * @param height   Component height.
	 */
	private void drawXDescription(Graphics2D graphics, String text, int startX, int length, int height) {
		int textWidth = graphics.getFontMetrics().stringWidth(text);

		graphics.drawString(text, startX + length / 2 - textWidth / 2, height - PADDING);
	}

	/**
	 * Draws description next to y-axis.
	 * 
	 * @param graphics   Used to draw graphics.
	 * @param text       Description that will be displayed.
	 * @param length     Length of y-axis.
	 * @param textHeight Height of text.
	 * @param startY     Y coordinate of y-axis.
	 */
	private void drawYDescription(Graphics2D graphics, String text, int length, int textHeight, int startY) {
		AffineTransform saveAT = graphics.getTransform();
		graphics.rotate(-Math.PI / 2);

		int textWidth = graphics.getFontMetrics().stringWidth(text);
		graphics.drawString(text, -1 * (startY - length / 2 + textWidth / 2), PADDING + textHeight);
		graphics.setTransform(saveAT);
	}

	/**
	 * Returns width of the text.
	 * 
	 * @param graphics Used to get the width of the text.
	 * @return Width of the text.
	 */
	private int getMaxWidth(Graphics graphics) {
		String number = String.valueOf(barChart.getMaxY());

		int maxWidth = graphics.getFontMetrics().stringWidth(number);
		return maxWidth;
	}

	/**
	 * Returns x coordinate in which x-axis and y-axis starts.
	 * 
	 * @param graphics   Used to get width of description that will be added between
	 *                   axes and edge of the component.s
	 * @param textHeight Height of the text.
	 * @return X coordinate in which x-axis and y-axis starts.
	 */
	private int getXStart(Graphics2D graphics, int textHeight) {
		int maxWidth = getMaxWidth(graphics);

		return 3 * PADDING + maxWidth + textHeight;
	}

	/**
	 * Returns y coordinate in which x-axis and y-axis starts.
	 * 
	 * @param graphics Used to draw graphics.
	 * @return Y coordinate in which x-axis and y-axis starts.
	 */
	private int getYStart(Graphics2D graphics) {
		int textHeight = graphics.getFontMetrics().getHeight();

		return 3 * PADDING + 2 * textHeight;
	}
}
