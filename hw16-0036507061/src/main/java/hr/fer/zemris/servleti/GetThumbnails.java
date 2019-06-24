package hr.fer.zemris.servleti;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * NE KORISTI SE. KORIŠTENO SAMO KAO PROBA. SADA SE KORISTI IMAGEJSON.JAVA GDJE
 * SE KORISTI JERSEY. Servlet implementation class GetThumbnails
 */
@WebServlet("/getThumbnails")
public class GetThumbnails extends HttpServlet {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetThumbnails() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		List<Image> images = Initialize.getImagesForTag(request.getParameter("tag"));

		String dir = request.getServletContext().getRealPath("/WEB-INF/thumbnails");
		Path directory = Paths.get(dir);

		if (!Files.exists(directory)) {
			Files.createDirectory(directory);
		}

		response.getWriter().write('[');
		boolean first = true;
		for (Image image : images) {
			if (first) {
				first = false;
			} else {
				response.getWriter().write(',');
			}

			String filename = request.getServletContext().getRealPath("/WEB-INF/thumbnails/" + image.getFilename());

			Path file = Paths.get(filename);

			if (!Files.exists(file)) {
				createThumnail(image, filename);
			}

			response.getWriter().write("\"" + image.getFilename() + "\"");
		}

		response.getWriter().write(']');
		response.getWriter().flush();
	}

	/**
	 * Method that creates image of size 150x150. Those small images are called
	 * thumbnails. Those pictures are presented when button with tag is clicked.
	 * Then user can click on thumbnail and larger picture with picture name and
	 * description will be presented at web page.
	 * 
	 * @param image    Object that stores informations about picture.
	 * @param filename Picture name.
	 */
	private void createThumnail(Image image, String filename) {
		try {
			resize(image.getImage(), filename, 150, 150);
		} catch (IOException e) {
		}
	}

	/**
	 * Method that resizes given BufferedImage.
	 * 
	 * @param inputImage      Given BufferedImage.
	 * @param outputImagePath Path where resized image will be placed.
	 * @param scaledWidth     New width.
	 * @param scaledHeight    New height.
	 * @throws IOException When error while reading or writing occurs.
	 */
	private void resize(BufferedImage inputImage, String outputImagePath, int scaledWidth, int scaledHeight)
			throws IOException {

		BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());

		Graphics2D g2d = outputImage.createGraphics();
		g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
		g2d.dispose();

		String formatName = outputImagePath.substring(outputImagePath.lastIndexOf(".") + 1);

		ImageIO.write(outputImage, formatName, new File(outputImagePath));
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
