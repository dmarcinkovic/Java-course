package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.sql.SQLConnectionProvider;

/**
 * Servlet used to read glasanje-definicija.txt file and store information in
 * session map so that those informations can be accessed in .jsp files. It
 * stores informations about the names, links and id numbers of each band.
 * 
 * @author David
 *
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = resp.getWriter();

		String id = req.getParameter("pollID");

		if (id == null) {
			req.getSession().setAttribute("error", "Please provide one parameter.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		Long idValue = null;
		try {
			idValue = Long.parseLong(id);
		} catch (NumberFormatException e) {
			req.getSession().setAttribute("error", "Please provide one parameter that is number.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		java.sql.Connection dbConnection = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;

		try {
			req.getSession().setAttribute("pollIDValue", idValue);

			pst = dbConnection.prepareStatement("select * from polls where id = ?");
			pst.setLong(1, idValue);

			ResultSet rset = pst.executeQuery();
			rset.next();
			writer.println("<h1>" + rset.getString("title") + "</h1>");
			writer.println("<p>" + rset.getString("message") + "</p>");
			writer.println("<ol>");

			pst = dbConnection.prepareStatement("select * from polloptions where pollID = ?");

			pst.setLong(1, idValue);
			rset = pst.executeQuery();

			while (rset.next()) {
				String title = rset.getString("optionTitle");
				Long pollOptionsId = rset.getLong("id");

				printRow(req, pollOptionsId, title, id, writer);
			}

			writer.println("</ol>");

		} catch (SQLException e) {
		}
	}

	/**
	 * Method that prints one row of table.
	 * 
	 * @param req           HttpServletRequest.
	 * @param pollOptionsId Id of PollOptions table.
	 * @param title         poll title.
	 * @param id            Poll id.
	 * @param writer        Writer used to writer the web page.
	 */
	private void printRow(HttpServletRequest req, Long pollOptionsId, String title, String id, PrintWriter writer) {
		writer.print("<li><a href=\"");
		writer.print(req.getContextPath() + "/servleti/glasanje-glasaj?id1=");
		writer.print(pollOptionsId.toString());
		writer.print("&id2=");
		writer.print(id);
		writer.print("\">");
		writer.print(title);
		writer.println("</a></li>");
	}
}
