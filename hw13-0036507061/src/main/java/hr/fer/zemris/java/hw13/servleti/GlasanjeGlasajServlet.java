package hr.fer.zemris.java.hw13.servleti;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to process informations about the voting and store them in
 * session map so that they can be accessed in .jsp files. Also, it stores
 * information about the number of bands to this map.
 * 
 * @author David
 *
 */
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Stores the total number of bands.
	 */
	private final int NUMBER_OF_BANDS = 7;

	/**
	 * Stores the score
	 */
	private Map<Integer, Integer> score = new HashMap<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");

		req.getSession().setAttribute("numberOfBands", NUMBER_OF_BANDS);

		int id = Integer.parseInt(req.getParameter("id"));
		Integer currentScore = score.get(id - 1);

		if (currentScore == null) {
			score.put(id - 1, 1);
		} else {
			score.put(id - 1, currentScore + 1);
		}

		Path path = Paths.get(fileName);

		if (!Files.exists(path)) {
			Files.createFile(path);
		}

		try (FileOutputStream os = new FileOutputStream(path.toFile())) {
			for (int i = 1; i <= NUMBER_OF_BANDS; i++) {
				Integer value = score.get(i - 1);
				if (value == null) {
					value = 0;
				}
				String line = String.valueOf(i) + "\t" + String.valueOf(value) + "\n";
				os.write(line.getBytes());
			}
		}
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}
