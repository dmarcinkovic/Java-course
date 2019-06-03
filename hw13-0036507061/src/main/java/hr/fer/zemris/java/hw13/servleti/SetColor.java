package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to set background color. User can choose between white(which is
 * default), red, green and cyan. Changing color of background will not just
 * affect on this page, but on all pages from this project.
 * 
 * @author David
 *
 */
public class SetColor extends HttpServlet {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().setAttribute("pickedBgCol", req.getParameter("pickedBgCol"));
		req.getRequestDispatcher("colors.jsp").forward(req, resp);
	}

}
