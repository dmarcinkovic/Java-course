package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.IRayTracerAnimator;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program that renders an image of spheres using Ray-casting algorithm. It
 * rotates around the spheres.
 * 
 * @author david
 *
 */
public class RayCasterParallel2 {
	/**
	 * Method invoked when running the program. This method renders an image of
	 * spheres.
	 * 
	 * @param args Arguments provided via command line. In this program they are not
	 *             used.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), getIRayTracerAnimator(), 30, 30);
	}

	/**
	 * Returns IRayTracerAnimator.
	 * 
	 * @return IRayTracerAnimator.
	 */
	private static IRayTracerAnimator getIRayTracerAnimator() {
		return new IRayTracerAnimator() {
			/**
			 * Time.
			 */
			long time;

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void update(long deltaTime) {
				time += deltaTime;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public Point3D getViewUp() {
				return new Point3D(0, 0, 10);
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public Point3D getView() {
				return new Point3D(-2, 0, -0.5);
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public long getTargetTimeFrameDuration() {
				return 150;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public Point3D getEye() {
				double t = (double) time / 10000 * 2 * Math.PI;
				double t2 = (double) time / 5000 * 2 * Math.PI;
				double x = 50 * Math.cos(t);
				double y = 50 * Math.sin(t);
				double z = 30 * Math.sin(t2);
				return new Point3D(x, y, z);
			}
		};
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
				Scene scene = RayTracerViewer.createPredefinedScene2();

				ForkJoinPool pool = new ForkJoinPool();
				int offset = 0;
				pool.invoke(new Worker(width, height, horizontal, vertical, screenCorner, eye, yAxis, xAxis, scene, red,
						green, blue, offset, 0, height - 1, rayCaster));
				pool.shutdown();

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}
}
