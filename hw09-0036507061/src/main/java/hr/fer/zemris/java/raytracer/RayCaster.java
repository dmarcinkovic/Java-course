package hr.fer.zemris.java.raytracer;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program that renders an image of spheres using Ray-casting algorithm.
 * 
 * @author david
 *
 */
public class RayCaster {

	/**
	 * Method invoked when running the program. This method renders an image of
	 * spheres.
	 * 
	 * @param args Arguments provided via command line. In this program they are not
	 *             used.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}
	
	/**
	 * Return ray tracer producer.
	 * 
	 * @return Ray tracer producer.
	 */
	private static IRayTracerProducer getIRayTracerProducer() {

		return new IRayTracerProducer() {

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {

				System.out.println("Započinjem izračune...");

				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				
				RayCasterUtil rayCaster = new RayCasterUtil();
				
				Point3D OG = rayCaster.getOGVector(view, eye);
				
				Point3D yAxis = rayCaster.getYAxis(viewUp.normalize(), OG);
				Point3D xAxis = rayCaster.getXAxis(OG, yAxis);

				Point3D screenCorner = rayCaster.getScreenCorner(view, xAxis, yAxis, horizontal, vertical);
				Scene scene = RayTracerViewer.createPredefinedScene();

				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = rayCaster.getPointXY(screenCorner, x, width, horizontal, xAxis, y, height, vertical,
								yAxis);
						Ray ray = Ray.fromPoints(eye, screenPoint);

						rayCaster.tracer(scene, ray, rgb, eye);
						
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}
}
