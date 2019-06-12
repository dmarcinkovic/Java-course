package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.sql.SQLConnectionProvider;

/**
 * Servlet used to list all available polls to web page. Also, by clicking to
 * the poll link user can access voting. After voting, results of voting will be
 * presented in nice GUI.
 * 
 * @author David
 *
 */
public class ListPolls extends HttpServlet {
	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = resp.getWriter();
		java.sql.Connection dbConnection = SQLConnectionProvider.getConnection();

		PreparedStatement pst = null;
		
		int numberOfPolls = 0;
		try {
			pst = dbConnection.prepareStatement("select * from polls");
			ResultSet rset = pst.executeQuery();
			while (rset.next()) {
				String title = rset.getString("title");
				Long id = rset.getLong("id");

				writer.print("<a href=\"");
				writer.print(req.getContextPath() + "/servleti/glasanje?pollID=");
				writer.print(id.toString());
				writer.print("\">");

				writer.print(title);

				writer.println("</a> <br>");
				numberOfPolls++;
			}
		} catch (SQLException e) {
		}
		
		if (numberOfPolls == 0) {
			writer.println("There are no polls currently available.");
		}
	}
}
