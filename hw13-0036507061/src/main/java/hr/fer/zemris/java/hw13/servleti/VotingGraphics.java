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
 * Servlet that is used to create pie chart that will graphically show the
 * result of voting. The picture of the pie chart will be presented on
 * /glasanje-rezultati link.
 * 
 * @author David
 *
 */
public class VotingGraphics extends HttpServlet {

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
		int numberOfBands = (int) req.getSession().getAttribute("numberOfBands");
		JFreeChart chart = getChart(numberOfBands, req);
		int width = 500;
		int height = 350;
		ChartUtilities.writeChartAsPNG(outputStream, chart, width, height);
	}

	/**
	 * Method to get pie chart with all informations about the band names.
	 * 
	 * @param numberOfBands Number of bands for which it is possible to vote.
	 * @param req           Request.
	 * @return Returns Pie chart image.
	 */
	public JFreeChart getChart(int numberOfBands, HttpServletRequest req) {
		DefaultPieDataset dataset = new DefaultPieDataset();

		for (int i = 1; i <= numberOfBands; i++) {
			int score = (int) req.getSession().getAttribute(String.valueOf(i));
			String bandName = (String) req.getSession().getAttribute("id" + String.valueOf(i));

			dataset.setValue(bandName, score);
		}

		boolean legend = true;
		boolean tooltips = false;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createPieChart("Voting results", dataset, legend, tooltips, urls);

		chart.setBorderPaint(Color.GREEN);
		chart.setBorderStroke(new BasicStroke(5.0f));
		chart.setBorderVisible(true);

		return chart;
	}
}
