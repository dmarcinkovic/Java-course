package hr.fer.zemris.java.hw13.servleti;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Servlet used to draw image of pie-chart using information about the OS usage.
 * The image is presented in .png format. This servlet can be accessed by typing
 * /reportImage to the end of the path.
 * 
 * @author David
 *
 */
public class ReportImage extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");

		OutputStream outputStream = resp.getOutputStream();

		JFreeChart chart = getChart();
		int width = 500;
		int height = 350;
		ChartUtilities.writeChartAsPNG(outputStream, chart, width, height);
	}

	/**
	 * Method that returns an image of pie chart. This pie chart show the percentage
	 * of operating system usage. At the first place is linux, with 70% of users.
	 * Then Mac follows with 20% of users. At the third place if windows with 9% of
	 * users. An remaining 1% is for other operating systems.
	 * 
	 * @return Image of pie chart.
	 */
	public JFreeChart getChart() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Linux", 70);
		dataset.setValue("Windows", 9);
		dataset.setValue("Max", 20);
		dataset.setValue("Other", 1);

		boolean legend = true;
		boolean tooltips = false;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createPieChart("OS usage", dataset, legend, tooltips, urls);

		chart.setBorderPaint(Color.GREEN);
		chart.setBorderStroke(new BasicStroke(5.0f));
		chart.setBorderVisible(true);

		return chart;
	}
}
