package hr.fer.zemris.servleti;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShowThumbnails
 */
@WebServlet("/showThumbnail")
public class ShowThumbnails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowThumbnails() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpg");
		
		OutputStream outputStream = response.getOutputStream();
		String imageName = request.getParameter("img");
		String filename = request.getServletContext().getRealPath("/WEB-INF/thumbnails") + File.separator + imageName;
		Path file = Paths.get(filename);
		
		BufferedImage image = ImageIO.read(file.toFile());
		
		ImageIO.write(image, "jpg", outputStream);
		outputStream.flush();
		outputStream.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
