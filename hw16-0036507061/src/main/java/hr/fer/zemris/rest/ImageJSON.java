package hr.fer.zemris.rest;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import hr.fer.zemris.servleti.Image;
import hr.fer.zemris.servleti.Initialize;

/**
 * Class that contains two methods that are served using REST.
 * 
 * @author David
 *
 */
@Path("/image")
public class ImageJSON {

	/**
	 * Servlet context used to get the location of images at the disk.
	 */
	@Context
	ServletContext servletContext;

	/**
	 * Method that returns all tags from images in JSON format.
	 * 
	 * @return Response.
	 */
	@GET
	@Produces("application/json")
	public Response loadTags() {
		Set<String> tags = Initialize.getTags();

		List<String> list = new ArrayList<>();
		for (String tag : tags) {
			list.add(tag);
		}

		JSONObject result = new JSONObject();
		result.put("name", new JSONArray(list));

		return Response.status(Status.OK).entity(result.toString()).build();
	}

	/**
	 * Method that can be accessed with /rest/image/tag. This method returns JSON
	 * representation of array of strings. Each string is name of the file that has
	 * given tag.
	 * 
	 * @param tag Tag.
	 * @return Response.
	 */
	@Path("{tag}")
	@GET
	@Produces("application/json")
	public Response getThumbnails(@PathParam("tag") String tag) {
		List<Image> images = Initialize.getImagesForTag(tag);
		String dir = servletContext.getRealPath("/WEB-INF/thumbnails");
		java.nio.file.Path directory = Paths.get(dir);

		if (!Files.exists(directory)) {
			try {
				Files.createDirectory(directory);
			} catch (IOException e) {
			}
		}

		List<String> list = new ArrayList<>();
		for (Image image : images) {
			String filename = servletContext.getRealPath("/WEB-INF/thumbnails/" + image.getFilename());

			java.nio.file.Path file = Paths.get(filename);

			if (!Files.exists(file)) {
				createThumnail(image, filename);
			}

			list.add(image.getFilename());
		}

		JSONObject result = new JSONObject();
		result.put("name", new JSONArray(list));

		return Response.status(Status.OK).entity(result.toString()).build();
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

}
