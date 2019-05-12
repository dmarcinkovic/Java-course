package hr.fer.zemris.java.raytracer;

import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;

/**
 * Represents one task. This class extends RecursiveAction. This means that
 * tasks are splitted recursively until they are small enough to be computed
 * directly.
 * 
 * @author david
 *
 */
public class Worker extends RecursiveAction {
	/**
	 * Default serial version uid.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Width and height of the window.
	 */
	private int width;
	private int height;

	/**
	 * Horizontal and vertical.
	 */
	private double horizontal;
	private double vertical;

	/**
	 * Corner of the screen.
	 */
	private Point3D screenCorner;

	/**
	 * Point of view.
	 */
	private Point3D eye;

	/**
	 * Y-axis and x-axis coordinates.
	 */
	private Point3D yAxis;
	private Point3D xAxis;

	/**
	 * Instance of class that represents scene. This object hold information about
	 * the Graphical objects (spheres) presented in scene.
	 */
	private Scene scene;

	/**
	 * Red, green and blue colors.
	 */
	private short[] red;
	private short[] green;
	private short[] blue;

	/**
	 * Current index in red, green and blue arrays.
	 */
	private int offset;

	/**
	 * Y range.
	 */
	private int yMin;
	private int yMax;

	/**
	 * Reference to RayCasterUtil class.
	 */
	private RayCasterUtil rayCaster;

	/**
	 * Constructor to initialize private field.
	 * 
	 * @param width        Width of the window.
	 * @param height       Height of the window.
	 * @param horizontal   Horizontal.
	 * @param vertical     Vertical.
	 * @param screenCorner The corner of the screen.
	 * @param eye          Point of view.
	 * @param yAxis        y-axis.
	 * @param xAxis        x-axis.
	 * @param scene        Instance of class that represents scene. This object hold
	 *                     information about the Graphical objects (spheres)
	 *                     presented in scene.
	 * @param red          Array in which is stored value of red color.
	 * @param green        Array in which is stored value of green color.
	 * @param blue         Array in which is stored value of blue color.
	 * @param offset       Offset in red, green and blue arrays.
	 * @param yMin         Start y.
	 * @param yMax         End y.
	 * @param rayCaster    Reference to RayCasterUtil.
	 */
	public Worker(int width, int height, double horizontal, double vertical, Point3D screenCorner, Point3D eye,
			Point3D yAxis, Point3D xAxis, Scene scene, short[] red, short[] green, short[] blue, int offset, int yMin,
			int yMax, RayCasterUtil rayCaster) {
		this.width = width;
		this.height = height;
		this.horizontal = horizontal;
		this.vertical = vertical;
		this.screenCorner = screenCorner;
		this.eye = eye;
		this.yAxis = yAxis;
		this.xAxis = xAxis;
		this.scene = scene;
		this.offset = offset;
		this.yMin = yMin;
		this.yMax = yMax;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.rayCaster = rayCaster;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void compute() {
		if (yMax - yMin + 1 <= 16) {
			computeDirect();
			return;
		}

		int newOffset = (yMin + (yMax - yMin) / 2 + 1) * width;

		invokeAll(
				new Worker(width, height, horizontal, vertical, screenCorner, eye, yAxis, xAxis, scene, red, green,
						blue, offset, yMin, yMin + (yMax - yMin) / 2, rayCaster),
				new Worker(width, height, horizontal, vertical, screenCorner, eye, yAxis, xAxis, scene, red, green,
						blue, newOffset, yMin + (yMax - yMin) / 2 + 1, yMax, rayCaster));
	}

	/**
	 * When task is small enough compute it. This method go through pixels from yMin
	 * to yMax and for every pixel calculates its screen point and color.
	 */
	public void computeDirect() {
		short[] rgb = new short[3];
		for (int y = yMin; y <= yMax; y++) {
			for (int x = 0; x < width; x++) {
				Point3D screenPoint = rayCaster.getPointXY(screenCorner, x, width, horizontal, xAxis, y, height,
						vertical, yAxis);
				Ray ray = Ray.fromPoints(eye, screenPoint);

				rayCaster.tracer(scene, ray, rgb, eye);
				red[offset] = rgb[0] > 255 ? 255 : rgb[0];
				green[offset] = rgb[1] > 255 ? 255 : rgb[1];
				blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
				offset++;
			}
		}
	}

}