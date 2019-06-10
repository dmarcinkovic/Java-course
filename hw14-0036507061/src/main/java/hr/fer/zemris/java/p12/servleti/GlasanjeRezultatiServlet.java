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
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = resp.getWriter();
		
		writer.println("<h1>Rezultati glasanja</h1>");
		writer.println("<p>Ovo su rezultati glasanja.</p>");
		writer.println("<table border=\"1\" class=\"rez\">");
		
		String pollId = req.getParameter("id");
		java.sql.Connection dbConnection = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null; 
		
		try {
			pst = dbConnection.prepareStatement("select * from polloptions where pollId = ?");
			pst.setLong(1, Long.parseLong(pollId));
			
			ResultSet rset = pst.executeQuery();
			
			while (rset.next()) {
				String title = rset.getString("optionTitle");
				String link = rset.getString("optionLink");
				Long id = rset.getLong("pollID");
				Long votesCount = rset.getLong("votesCount");
				
				System.out.println(title + " " + link + " " + id + " " + votesCount);
			}
		} catch (SQLException e) {
			
		}
	}
}
