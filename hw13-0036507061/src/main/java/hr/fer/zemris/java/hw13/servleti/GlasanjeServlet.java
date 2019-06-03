package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to read glasanje-definicija.txt file and store information in
 * session map so that those informations can be accessed in .jsp files. It
 * stores informations about the names, links and id numbers of each band.
 * 
 * @author David
 *
 */
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
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

		List<String> lines = Files.readAllLines(Paths.get(fileName));

		for (String line : lines) {
			String[] args = line.split("\t");
			String id = args[0];
			String bandName = args[1];
			String link = args[2];

			req.getSession().setAttribute(bandName, id);
			req.getSession().setAttribute("id" + id, bandName);
			req.getSession().setAttribute("link" + id, link);
		}

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
