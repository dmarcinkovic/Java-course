package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.sql.SQLConnectionProvider;

/**
 * Servlet used to process informations about the voting and store them in
 * session map so that they can be accessed in .jsp files. Also, it stores
 * information about the number of bands to this map.
 * 
 * @author David
 *
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pollId = req.getParameter("id2");
		String pollOptionsId = req.getParameter("id1");

		java.sql.Connection dbConnection = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;

		try {
			pst = dbConnection
					.prepareStatement("update polloptions set votesCount = votesCount + 1 where id = ? and pollID = ?");
			pst.setLong(1, Long.parseLong(pollOptionsId));
			pst.setLong(2, Long.parseLong(pollId));
			pst.executeUpdate();
		} catch (SQLException e) {
		}

		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?id=" + pollId);
	}
}
