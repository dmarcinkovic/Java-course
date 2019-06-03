package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to store result of voting to glasanje-rezultati, so that those
 * results can be accessed in .jsp files.
 * 
 * @author David
 *
 */
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
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path path = Paths.get(fileName);

		if (!Files.exists(path)) {
			Files.createFile(path);
		}
		List<String> lines = Files.readAllLines(path);

		for (String line : lines) {
			String[] args = line.split("\t");
			String id = args[0];
			int score = Integer.parseInt(args[1]);
			req.getSession().setAttribute(id, score);
		}

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
