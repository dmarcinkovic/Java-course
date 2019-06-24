package hr.fer.zemris.servleti;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * NE KORISTI SE. KORIŠTENO SAMO KAO PROBA. SADA SE KORISTI IMAGEJSON.JAVA GDJE
 * SE KORISTI JERSEY. Servlet implementation class LoadTags
 */
@WebServlet("/loadTags")
public class LoadTags extends HttpServlet {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoadTags() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Set<String> tags = Initialize.getTags();
		response.setContentType("application/json;charset=UTF-8");

		response.getWriter().write('[');
		boolean first = true;
		for (String tag : tags) {
			if (first) {
				first = false;
			} else {
				response.getWriter().write(',');
			}

			response.getWriter().write("\"" + tag + "\"");
		}

		response.getWriter().write(']');
		response.getWriter().flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
