package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Trigonometric extends HttpServlet {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("a");
		String b = req.getParameter("b");

		int varA = 0;
		int varB = 360;

		try {
			if (a != null) {
				varA = Integer.parseInt(a);
			}

			if (b != null) {
				varB = Integer.parseInt(b);
			}
		} catch (NumberFormatException ignorable) {
		}

		if (varA > varB) {
			int temp = varA;
			varA = varB;
			varB = temp;
		}

		if (varB > varA + 720) {
			varB = varA + 720;
		}

		req.getSession().setAttribute("a", varA);
		req.getSession().setAttribute("b", varB);
		req.getRequestDispatcher("WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
}
