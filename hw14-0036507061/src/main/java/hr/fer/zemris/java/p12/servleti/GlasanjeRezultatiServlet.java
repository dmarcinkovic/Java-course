package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.sql.SQLConnectionProvider;

/**
 * Servlet used to store result of voting to glasanje-rezultati, so that those
 * results can be accessed in .jsp files.
 * 
 * @author David
 *
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Stores name's of the options with the highest number of votes.
	 */
	private List<String> bestNames = new ArrayList<>();

	/**
	 * Stores links's of the options with the highest number of votes.
	 */
	private List<String> bestLinks = new ArrayList<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = resp.getWriter();

		setHeader(writer);

		String pollId = req.getParameter("id");
		
		if (pollId == null) {
			req.getSession().setAttribute("error", "Please provide one parameter.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		java.sql.Connection dbConnection = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;

		try {
			pst = dbConnection.prepareStatement("select * from polloptions where pollId = ?");
			pst.setLong(1, Long.parseLong(pollId));

			ResultSet rset = pst.executeQuery();

			while (rset.next()) {
				String title = rset.getString("optionTitle");
				Long votesCount = rset.getLong("votesCount");

				writer.print("<tr><td>");
				writer.print(title);
				writer.print("</td><td>");
				writer.print(votesCount);
				writer.println("</td></tr>");
			}
		} catch (SQLException e) {
		} catch(NumberFormatException e) {
			req.getSession().setAttribute("error", "Please provide parameters that can be converted to number.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		setFooter(writer, req, pollId);

		getBest(pollId);

		for (int i = 0; i < bestNames.size(); i++) {
			String name = bestNames.get(i);
			String l = "\"" + bestLinks.get(i) + "\"";
			writer.print("<li><a href=");
			writer.print(l);
			writer.print(" target=\"_blank\">");
			writer.print(name);
			writer.println("</a></li>");
		}

		writer.println("</ul>");
	}

	/**
	 * Sets footer of this page.
	 * 
	 * @param writer Writer
	 * @param req    HttpServletRequest.
	 * @param pollId PollId.
	 */
	private void setFooter(PrintWriter writer, HttpServletRequest req, String pollId) {
		writer.println("</tbody>");
		writer.println("</table>");
		writer.println("<h2>Grafički prikaz rezultata</h2>");
		writer.print("<img alt=\"Pie-chart\" src=\"");
		writer.print(req.getContextPath() + "/servleti/glasanje-grafika?id=");
		writer.print(pollId.toString());
		writer.println("\" width=\"400\" height=\"400\" />");
		writer.println("<h2>Rezultati u XLS formatu</h2>");

		writer.print("<p>Rezultati u XLS formatu dostupni su <a href=\"");
		writer.print(req.getContextPath() + "/servleti/glasanje-xls?id=");
		writer.println(pollId.toString() + "\">ovdje</a></p>");
		writer.println("<h2>Razno</h2>");
		writer.println("<p>Linkovi najglasanije opcije:</p><ul>");
	}

	/**
	 * Sets header of this page.
	 * 
	 * @param writer Writer.
	 */
	private void setHeader(PrintWriter writer) {
		writer.println("<h1>Rezultati glasanja</h1>");
		writer.println("<p>Ovo su rezultati glasanja.</p>");
		writer.println("<table border=\"1\" class=\"rez\">");
		writer.println("<thead><tr><th>Naziv</th><th>Broj glasova</th></tr></thead>");
		writer.println("<tbody>");
	}

	/**
	 * Method to find the rows with the highest number of votes.
	 * 
	 * @param pollId PollID.
	 */
	private void getBest(String pollId) {
		Long maxScore = 0L;
		java.sql.Connection dbConnection = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;

		try {
			pst = dbConnection.prepareStatement("select * from polloptions where pollId = ?");
			pst.setLong(1, Long.parseLong(pollId));

			ResultSet rset = pst.executeQuery();

			while (rset.next()) {
				String title = rset.getString("optionTitle");
				Long votesCount = rset.getLong("votesCount");
				String link = rset.getString("optionLink");

				if (votesCount > maxScore) {
					bestLinks.clear();
					bestNames.clear();
					maxScore = votesCount;
				}

				if (votesCount == maxScore) {
					bestLinks.add(link);
					bestNames.add(title);
				}
			}
		} catch (SQLException e) {
		}
	}
}
