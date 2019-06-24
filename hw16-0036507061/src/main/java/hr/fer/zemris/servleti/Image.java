package hr.fer.zemris.servleti;

import java.awt.image.BufferedImage;

/**
 * 
 * Class that represents one image that is presented in gallery. Image have its
 * name, description and three tags.
 * 
 * @author David
 *
 */
public class Image {

	/**
	 * Image name.
	 */
	private String filename;

	/**
	 * Image description.
	 */
	private String description;

	/**
	 * First image tag.
	 */
	private String tag1;

	/**
	 * Second image tag.
	 */
	private String tag2;

	/**
	 * Third image tag.
	 */
	private String tag3;

	/**
	 * Image.
	 */
	private BufferedImage image;

	/**
	 * Constructor used to initialize image name, description and tags.
	 * 
	 * @param filename    Image name.
	 * @param description Image description.
	 * @param tag1        First image tag.
	 * @param tag2        Second image tag.
	 * @param tag3        Third image tag.
	 */
	public Image(String filename, String description, String tag1, String tag2, String tag3, BufferedImage image) {
		super();
		this.filename = filename;
		this.description = description;
		this.tag1 = tag1;
		this.tag2 = tag2;
		this.tag3 = tag3;
		this.image = image;
	}

	/**
	 * Getter for filename.
	 * 
	 * @return Image filename.
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Setter for filename.
	 * 
	 * @param filename Image filename.
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * Getter for description.
	 * 
	 * @return Image description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter for image description.
	 * 
	 * @param description Image description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter for first image tag.
	 * 
	 * @return First image tag.
	 */
	public String getTag1() {
		return tag1;
	}

	/**
	 * Setter for second image tag.
	 * 
	 * @param tag1 Second image tag.
	 */
	public void setTag1(String tag1) {
		this.tag1 = tag1;
	}

	/**
	 * Getter for second image tag.
	 * 
	 * @return Second image tag.
	 */
	public String getTag2() {
		return tag2;
	}

	/**
	 * Setter for second image tag.
	 * 
	 * @param tag2 Second image tag.
	 */
	public void setTag2(String tag2) {
		this.tag2 = tag2;
	}

	/**
	 * Getter for third image tag.
	 * 
	 * @return Third image tag.
	 */
	public String getTag3() {
		return tag3;
	}

	/**
	 * Setter for third image tag.
	 * 
	 * @param tag3 Third image tag.
	 */
	public void setTag3(String tag3) {
		this.tag3 = tag3;
	}

	/**
	 * Getter for image.
	 * 
	 * @return Image.
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * Setter for image.
	 * 
	 * @param image Image.
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
