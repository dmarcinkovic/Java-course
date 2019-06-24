package hr.fer.zemris.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShowImage
 */
@WebServlet("/showImage")
public class ShowImage extends HttpServlet {
	
	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowImage() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String imageName = request.getParameter("img");
		Image img = Initialize.getImage(imageName);

		response.setContentType("text/html");

		response.getWriter().write("<h1>Naslov slike: " + img.getFilename() + "</h1><br><h2>Opis slike: " + img.getDescription() + "</h2><hr><br>");
		response.getWriter().write("<h3>Tagovi: <br>" + img.getTag1() + "</h3><h3>" + img.getTag2() + "</h3><h3>" + img.getTag3() + "</h3><hr><br>");
		response.getWriter().write("<img src=\"showFullImage?img=" + imageName + "\"/>&nbsp;");
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
