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

public class ListPolls extends HttpServlet{
	//// TODO do not create connection, use existing one
	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = resp.getWriter();
		java.sql.Connection dbConnection = SQLConnectionProvider.getConnection();
		
		PreparedStatement pst = null; 
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
			}
		} catch (SQLException e) {
		}
	}
}
