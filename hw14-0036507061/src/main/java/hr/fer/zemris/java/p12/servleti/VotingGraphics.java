package hr.fer.zemris.java.p12.servleti;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.p12.dao.sql.SQLConnectionProvider;

/**
 * Servlet that is used to create pie chart that will graphically show the
 * result of voting. The picture of the pie chart will be presented on
 * /glasanje-rezultati link.
 * 
 * @author David
 *
 */
@WebServlet("/servleti/glasanje-grafika")
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
		if (req.getParameter("id") == null) {
			req.getSession().setAttribute("error", "Please provide one parameter.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		resp.setContentType("image/png");
		OutputStream outputStream = resp.getOutputStream();
		JFreeChart chart = getChart(req);
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
	public JFreeChart getChart(HttpServletRequest req) {
		DefaultPieDataset dataset = new DefaultPieDataset();

		Long id = Long.parseLong(req.getParameter("id"));

		java.sql.Connection dbConnection = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;

		try {
			pst = dbConnection.prepareStatement("select * from polloptions where pollID = ?");
			pst.setLong(1, id);

			ResultSet rset = pst.executeQuery();

			while (rset.next()) {
				Long score = rset.getLong("votesCount");
				String title = rset.getString("optionTitle");

				dataset.setValue(title, score);
			}

		} catch (SQLException e) {
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
