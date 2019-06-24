package hr.fer.zemris.servleti;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web listener that loads all images from disk when server runs up.
 * 
 * @author David
 *
 */
@WebListener
public class Initialize implements ServletContextListener {

	/**
	 * List of all images that will be presented in this gallery.
	 */
	private static List<Image> images = new ArrayList<Image>();

	/**
	 * {@index}
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String fileName = sce.getServletContext().getRealPath("/WEB-INF/opisnik.txt");
		Path path = Paths.get(fileName);
		if (!Files.exists(path)) {
			throw new IllegalStateException("Missing opisnik.txt file");
		}

		List<String> lines = null;
		try {
			lines = Files.readAllLines(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < lines.size(); i += 3) {
			String name = lines.get(i);
			String description = lines.get(i + 1);
			String[] tags = lines.get(i + 2).split(", ");
			String imagePath = sce.getServletContext().getRealPath("/WEB-INF/slike/" + name);

			BufferedImage image = null;
			try {
				image = ImageIO.read(new File(imagePath));
			} catch (IOException e) {
				e.printStackTrace();
			}

			images.add(new Image(name, description, tags[0], tags[1], tags[2], image));
		}
	}

	/**
	 * {@index}
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

	/**
	 * Method that returns set of all tags.
	 * 
	 * @return Set of all tags.
	 */
	public static Set<String> getTags() {
		Set<String> tags = new HashSet<>();

		for (Image image : images) {
			tags.add(image.getTag1());
			tags.add(image.getTag2());
			tags.add(image.getTag3());
		}

		return tags;
	}

	/**
	 * Method that returns List of image objects that have tag1 or tag2 or tag3
	 * equal to given method argument 'tag'.
	 * 
	 * @param tag Tag.
	 * @return List of image object that have tag1 or tag2 or tag3 equal to given
	 *         method argument 'tag'.
	 */
	public static List<Image> getImagesForTag(String tag) {
		List<Image> result = new ArrayList<>();

		for (Image image : images) {
			if (image.getTag1().equals(tag) || image.getTag2().equals(tag) || image.getTag3().equals(tag)) {
				result.add(image);
			}
		}

		return result;
	}

	/**
	 * Method that returns Image object who has image name equal to 'name'.
	 * 
	 * @param name Image name.
	 * @return Image object who has image name equal to 'name'.
	 */
	public static Image getImage(String name) {
		for (Image image : images) {
			if (image.getFilename().equals(name)) {
				return image;
			}
		}
		return null;
	}
}
