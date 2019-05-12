package hr.fer.zemris.java.raytracer.model;

/**
 * Represents sphere object. Sphere is determined by center of the sphere and
 * radius of the sphere.
 * 
 * @author david
 *
 */
public class Sphere extends GraphicalObject {
	/**
	 * Center of the sphere.
	 */
	private Point3D center;

	/**
	 * Radius of the sphere.
	 */
	private double radius;

	/**
	 * Diffuse components.
	 */
	private double kdr;
	private double kdg;
	private double kdb;

	/**
	 * Reflective components.
	 */
	private double krr;
	private double krg;
	private double krb;

	/**
	 * Shininess factor.
	 */
	private double krn;

	/**
	 * Initialize private fields.
	 * 
	 * @param center Center of the sphere.
	 * @param radius Radius of the sphere.
	 * @param kdr    Diffuse component.
	 * @param kdg    Diffuse component.
	 * @param kdb    Diffuse component.
	 * @param krr    Reflective component.
	 * @param krg    Reflective component.
	 * @param krb    Reflective component.
	 * @param krn    Shininess factor.
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		double d1 = getDistance(ray.direction, ray.start, true);
		double d2 = getDistance(ray.direction, ray.start, false);

		// No intersection.
		if (d1 < 0 && d2 < 0) {
			return null;
		}
		
		Point3D intersectionPoint;

		// Intersection in one point.
		if (d1 == d2) {
			intersectionPoint = getIntersectionPoint(d1, ray.direction, ray.start);
			return new MyRayIntersection(intersectionPoint, d1, isOuter(ray.start, d1), getNormal(intersectionPoint),
					kdr, kdg, kdb, krr, krg, krb, krn);
		}

		// Intersection in two points. Find closest one.
		double distance = findMinNonNegativeDistance(d1, d2);

		if (distance == -1) {
			return null;
		}

		intersectionPoint = getIntersectionPoint(distance, ray.direction, ray.start);
		return new MyRayIntersection(intersectionPoint, distance, isOuter(ray.start, distance),
				getNormal(intersectionPoint), kdr, kdg, kdb, krr, krg, krb, krn);
	}

	/**
	 * Returns minimum non-negative distance. One ray can intersect sphere in two
	 * points. This method will return closest one that is in front of the ray. If
	 * both points are behind the ray this method will return -1.
	 * 
	 * @param d1 Distance1
	 * @param d2 Distance2.
	 * @return Minimum non-negative distance.
	 */
	private double findMinNonNegativeDistance(double d1, double d2) {
		if (d1 < 0 && d2 < 0) {
			return -1;
		} else if (d1 < 0) {
			return d2;
		} else if (d2 < 0) {
			return d1;
		}
		return Math.min(d1, d2);
	}

	/**
	 * Checks if ray start is inside or outside the sphere.
	 * 
	 * @param start    Start point of the ray.
	 * @param distance Distance between start of the ray and intersection.
	 * @return True if point is outer, otherwise returns false.
	 */
	private boolean isOuter(Point3D start, double distance) {
		return center.sub(start).norm() > distance;
	}

	/**
	 * Returns unit vector that represents normal to this sphere in intersection
	 * point.
	 * 
	 * @param point Intersection point.
	 * @return Unit vector that represents normal to this sphere in intersection
	 *         point.
	 */
	private Point3D getNormal(Point3D point) {
		return new Point3D(point.x - center.x, point.y - center.y, point.z - center.z).normalize();
	}

	/**
	 * Returns intersection point between ray and this sphere.
	 * 
	 * @param distance  Distance between start of the ray and intersection point.
	 * @param direction Direction of the ray.
	 * @param start     Start point of the ray.
	 * @return Intersection point between ray and this sphere.
	 */
	private Point3D getIntersectionPoint(double distance, Point3D direction, Point3D start) {
		Point3D intersection = start.add(direction.scalarMultiply(distance));
		return intersection;
	}

	/**
	 * Returns distance between intersection point and start point of the ray. This
	 * is calculates using quadratic formula.
	 * 
	 * @param direction  Direction of the ray.
	 * @param start      Start point of the ray.
	 * @param firstPoint Boolean to indicate which point we want, because there
	 *                   possibly two points of intersection.
	 * @return Distance between intersection point and start point of the ray.
	 */
	private double getDistance(Point3D direction, Point3D start, boolean firstPoint) {
		double determinant = getDeterminant(direction, start);

		if (determinant < 0) {
			return -1;
		} else if (determinant == 0) {
			return getFirstPartOfEquation(direction, start);
		}

		if (firstPoint) {
			return getFirstPartOfEquation(direction, start) + Math.sqrt(determinant);
		}

		return getFirstPartOfEquation(direction, start) - Math.sqrt(determinant);
	}

	/**
	 * Calculate determinant of quadratic equation with formula: (direction * (start
	 * - center))^2 - (abs(start - center)^2 - radius^2).
	 * 
	 * @param direction Direction of the ray.
	 * @param start     Start point of the ray.
	 * @return Determinant of quadratic equation.
	 */
	private double getDeterminant(Point3D direction, Point3D start) {
		Point3D startMinusCenter = start.sub(center);
		double D = direction.scalarProduct(startMinusCenter);

		D *= D;
		D -= (Math.pow(startMinusCenter.norm(), 2) - radius * radius);

		return D;
	}

	/**
	 * Returns first part of quadratic equation. This is calculated by formula: -(
	 * direction * (start - center))
	 * 
	 * @param direction Direction of the ray.
	 * @param start     Start point of the ray.
	 * @return First part of the quadratic equation.
	 */
	private double getFirstPartOfEquation(Point3D direction, Point3D start) {
		Point3D startMinusCenter = start.sub(center);
		return -1 * direction.scalarProduct(startMinusCenter);
	}

	/**
	 * Implementation of RayIntersection.
	 * 
	 * @author david
	 *
	 */
	private static class MyRayIntersection extends RayIntersection {
		/**
		 * Normal to the sphere in intersection point.
		 */
		private Point3D normal;

		/**
		 * Coefficient for diffuse component for red color.
		 */
		private double kdr;

		/**
		 * Coefficient for diffuse component for green color.
		 */
		private double kdg;

		/**
		 * Coefficient for diffuse component for blue blue.
		 */
		private double kdb;

		/**
		 * Coefficient for reflective component for red color.
		 */
		private double krr;

		/**
		 * Coefficient for reflective component for green color.
		 */
		private double krg;

		/**
		 * Coefficient for reflective component for blue color.
		 */
		private double krb;

		/**
		 * Coefficient <code>n</code> for reflective component.
		 */
		private double krn;

		/**
		 * Constructor to initialize private fields.
		 * 
		 * @param point    Intersection point.
		 * @param distance Distance between intersection point and start of the ray.
		 * @param outer    Boolean flag to determine if intersection is outer or inner.
		 * @param normal   Normal to the sphere in intersection point.
		 * @param kdr      Coefficient for diffuse component for red color.
		 * @param kdg      Coefficient for diffuse component for blue blue.
		 * @param kdb      Coefficient for diffuse component for blue blue.
		 * @param krr      Coefficient for reflective component for red color.
		 * @param krg      Coefficient for reflective component for blue color.
		 * @param krb      Coefficient for reflective component for blue color.
		 * @param krn      Coefficient <code>n</code> for reflective component.
		 */
		protected MyRayIntersection(Point3D point, double distance, boolean outer, Point3D normal, double kdr,
				double kdg, double kdb, double krr, double krg, double krb, double krn) {
			super(point, distance, outer);
			this.normal = normal;
			this.kdb = kdb;
			this.kdr = kdr;
			this.kdg = kdg;
			this.krr = krr;
			this.krg = krg;
			this.krb = krb;
			this.krn = krn;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Point3D getNormal() {
			return normal;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKdr() {
			return kdr;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKdg() {
			return kdg;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKdb() {
			return kdb;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKrr() {
			return krr;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKrg() {
			return krg;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKrb() {
			return krb;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKrn() {
			return krn;
		}

	}
}
