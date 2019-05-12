package hr.fer.zemris.java.raytracer;

import java.util.List;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;

public class RayCasterUtil {

	/**
	 * 
	 * @param scene Instance of class that represents scene. This object hold
	 *              information about the Graphical objects (spheres) presented in
	 *              scene.
	 * @param ray   Light ray.
	 * @param rgb   Array representing rgb color. Index zero represents red color.
	 *              Index 1 represents green color. Index 2 represents blue color.
	 * @param eye   Point of view.
	 */
	public void tracer(Scene scene, Ray ray, short[] rgb, Point3D eye) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		RayIntersection closest = findClosestIntersection(scene, ray);

		if (closest == null) {
			return;
		}

		determineColorFor(closest, scene.getLights(), scene, rgb, eye);
	}

	/**
	 * Method used to determine the color one pixel based on LightSources and
	 * intersections of rays and spheres.
	 * 
	 * @param S       Instance of class that represents intersection between sphere
	 *                and ray. It contains information about the point of
	 *                intersection.
	 * @param sources List of all light sources.
	 * @param scene   Instance of class that represents scene. This object hold
	 *                information about the Graphical objects (spheres) presented in
	 *                scene.
	 * @param rgb     Array representing rgb color. Index zero represents red color.
	 *                Index 1 represents green color. Index 2 represents blue color.
	 * @param eye     Point of view.
	 */
	private void determineColorFor(RayIntersection S, List<LightSource> sources, Scene scene, short[] rgb,
			Point3D eye) {
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;

		for (LightSource source : sources) {
			Ray newRay = Ray.fromPoints(source.getPoint(), S.getPoint());
			RayIntersection newS = findClosestIntersection(scene, newRay);

			double distance1 = S.getPoint().sub(source.getPoint()).norm();

			if (newS != null) {
				double distance2 = newS.getPoint().sub(source.getPoint()).norm();

				if (distance2 < distance1 - 1e-5) {
					continue;
				}
			}

			updateRgb(source, S, eye, rgb);
		}
	}

	/**
	 * Method used to update rgb array based on intersections.
	 * 
	 * @param source Light source.
	 * @param S      Instance of class that represents intersection between sphere
	 *               and ray. It contains information about the point of
	 *               intersection.
	 * @param eye    Point of view.
	 * @param rgb    Array representing rgb color. Index zero represents red color.
	 *               Index 1 represents green color. Index 2 represents blue color.
	 */
	private void updateRgb(LightSource source, RayIntersection S, Point3D eye, short[] rgb) {
		rgb[0] += getDiffuseComponent(source.getR(), S.getKdr(), S.getNormal(), S.getPoint(), source.getPoint())
				+ getReflectiveComponent(source.getR(), S.getKrr(), S.getKrn(), source.getPoint(), S.getPoint(),
						S.getNormal(), eye);
		rgb[1] += getDiffuseComponent(source.getG(), S.getKdg(), S.getNormal(), S.getPoint(), source.getPoint())
				+ getReflectiveComponent(source.getG(), S.getKrg(), S.getKrn(), source.getPoint(), S.getPoint(),
						S.getNormal(), eye);
		rgb[2] += getDiffuseComponent(source.getB(), S.getKdb(), S.getNormal(), S.getPoint(), source.getPoint())
				+ getReflectiveComponent(source.getB(), S.getKrb(), S.getKrn(), source.getPoint(), S.getPoint(),
						S.getNormal(), eye);
	}

	/**
	 * Method that returns diffuse component of color. It is calculated by formula:
	 * I * K * cos(angle), where I is intensity, K is diffuse coefficient and angle
	 * is angle between normal to the sphere in intersection point and vector that
	 * connects source of the light and observed point. Observed point in this
	 * example is intersection point.
	 * 
	 * @param intensity      Intensity of the color.
	 * @param diffuseCoeff   Diffuse coefficient.
	 * @param normal         Normal to the sphere in intersection point.
	 * @param observingPoint Observing point. In this example this is intersection
	 *                       point.
	 * @param source         Light source.
	 * @return Diffuse component.
	 */
	private double getDiffuseComponent(double intensity, double diffuseCoeff, Point3D normal,
			Point3D observingPoint, Point3D source) {
		Point3D fromObservingPointToSource =  source.sub(observingPoint);
		double cosAngle = normal.scalarProduct(fromObservingPointToSource.normalize());

		if (cosAngle < 0) {
			return 0;
		}

		return intensity * diffuseCoeff * cosAngle;
	}

	/**
	 * Method that returns diffuse component of color. It is calculated by formula:
	 * I * K * cos(angle) ^ n, where I is intensity, K is diffuse coefficient and
	 * angle is angle between reflected ray and ray that goes to observer. Reflected
	 * ray can easily be calculate when normal and original rays are known. It can
	 * be calculate by the formula: l - 2 * (l*n)*n, where l is original ray, n is
	 * normal vector and l*n is scalar product.
	 * 
	 * @param intensity       Intensity of the color.
	 * @param reflectionCoeff Reflection coefficient.
	 * @param n               Shininess factor.
	 * @param source          Light source.
	 * @param intersection    Intersection point.
	 * @param normal          Normal to the sphere in intersection point.
	 * @param eye             Point of view.
	 * @return
	 */
	private static double getReflectiveComponent(double intensity, double reflectionCoeff, double n, Point3D source,
			Point3D intersection, Point3D normal, Point3D eye) {
		
		Point3D l = intersection.sub(source).normalize(); 
		double scalarProduct = l.scalarProduct(normal);
		Point3D r = l.sub(normal.scalarMultiply(2 * scalarProduct));
		Point3D v = new Point3D(eye.x - intersection.x, eye.y - intersection.y, eye.z - intersection.z);

		double cosAngle = r.normalize().scalarProduct(v.normalize());

		if (cosAngle < 0) {
			return 0;
		}

		return intensity * reflectionCoeff * Math.pow(cosAngle, n);
	}

	/**
	 * Method finds closest intersection between sphere and ray. If there are no
	 * intersections, or intersections are behind the observer this method will
	 * return null.
	 * 
	 * @param scene Instance of class that represents scene. This object hold
	 *              information about the Graphical objects (spheres) presented in
	 *              scene.
	 * @param ray   Ray.
	 * @return Closest intersection between sphere and ray. If intersection does not
	 *         exists null will be returned.
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		List<GraphicalObject> graphicalObjects = scene.getObjects();

		double distance = Double.MAX_VALUE;
		RayIntersection closestRayIntersection = null;

		for (GraphicalObject object : graphicalObjects) {
			RayIntersection intersection = object.findClosestRayIntersection(ray);

			if (intersection == null) {
				continue;
			}

			double d = intersection.getDistance();

			if (d < distance) {
				distance = d;
				closestRayIntersection = intersection;
			}
		}

		return closestRayIntersection;
	}

	/**
	 * Method returns OG vector. This vector connects view point and eye position.
	 * 
	 * @param G View point.
	 * @param O Eye position.
	 * @return OG vector.
	 */
	public Point3D getOGVector(Point3D G, Point3D O) {
		Point3D OG = G.sub(O).normalize();
		return OG;
	}

	/**
	 * Returns coordinate of the y-axis.
	 * 
	 * @param VUV Normalized view up vector.
	 * @param OG  Vector that connects view point and eye position.
	 * @return Coordinate of the y-axis.
	 */
	public Point3D getYAxis(Point3D VUV, Point3D OG) {
		double scalar = OG.scalarProduct(VUV);
		Point3D yAxis = VUV.sub(OG.scalarMultiply(scalar));

		return yAxis;
	}

	/**
	 * Returns coordinate of the x-axis.
	 * 
	 * @param OG     Vector that connects view point and eye position.
	 * @param yAxsis y-axis.
	 * @return Coordinate of the x-axis.
	 */
	public Point3D getXAxis(Point3D OG, Point3D yAxsis) {
		return OG.vectorProduct(yAxsis).normalize();
	}

	/**
	 * Returns coordinate of the screen corner.
	 * 
	 * @param G          View point.
	 * @param xAxis      x-axis.
	 * @param yAxis      y-axis.
	 * @param horizontal Horizontal.
	 * @param vertical   Vertical.
	 * @return Coordinate of the screen corner.
	 */
	public Point3D getScreenCorner(Point3D G, Point3D xAxis, Point3D yAxis, double horizontal,
			double vertical) {
		Point3D scaledXAxis = xAxis.scalarMultiply(horizontal / 2);
		Point3D scalexYAxis = yAxis.scalarMultiply(vertical / 2);
		Point3D corner = G.sub(scaledXAxis).add(scalexYAxis);

		return corner;
	}

	/**
	 * Returns coordinate of current pixel.
	 * 
	 * @param corner     Screen corner.
	 * @param x          X coordinate of the pixel.
	 * @param width      Width of the window.
	 * @param horizontal Horizontal.
	 * @param xAxis      x-axis.
	 * @param y          y-axis.
	 * @param height     Height of the window.
	 * @param vertical   Vertical.
	 * @param yAxis      y-axis.
	 * @return Coordinate of current pixel.
	 */
	public Point3D getPointXY(Point3D corner, double x, double width, double horizontal, Point3D xAxis,
			double y, double height, double vertical, Point3D yAxis) {
		double scale1 = x / (width - 1) * horizontal;
		Point3D scaledXAxis = xAxis.scalarMultiply(scale1);

		double scale2 = y / (height - 1) * vertical;
		Point3D scaledYAxis = yAxis.scalarMultiply(scale2);

		return corner.add(scaledXAxis).sub(scaledYAxis);
	}
}
