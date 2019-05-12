package hr.fer.zemris.math;

/**
 * The class model 3D vector whose components are real numbers: x and y and z.
 * 
 * @author david
 *
 */
public class Vector3 {
	/**
	 * X coordinate of the vector.
	 */
	private double x;

	/**
	 * Y coordinate of the vector.
	 */
	private double y;

	/**
	 * Z coordinate of the vector.
	 */
	private double z;

	/**
	 * Constructor to initialize private members of this class.
	 * 
	 * @param x x coordinate of the vector.
	 * @param y y coordinate of the vector.
	 * @param z z coordinate of the vector.
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Returns the length of the vector.
	 * 
	 * @return The length of the vector.
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Returns unit vector with the same angle as this vector.
	 * 
	 * @return Unit vector with the same angle as this vector.
	 */
	public Vector3 normalized() {
		double norm = norm();
		return new Vector3(this.x / norm, this.y / norm, this.z / norm);
	}

	/**
	 * Adds two vectors.
	 * 
	 * @param other second vector.
	 * @return New vector that is obtained by adding this and given vector.
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}

	/**
	 * Subtract this vector with given.
	 * 
	 * @param other Given vector.
	 * @return New vector that is obtained by subtracting this vector by given one.
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}

	/**
	 * Dot product of this and given vector. This is calculated by formula: |v1| *
	 * |v2| * cos(angle).
	 * 
	 * @param other Given vector.
	 * @return Dot product of two vectors.
	 */
	public double dot(Vector3 other) {
		double x = this.x * other.x;
		double y = this.y * other.y;
		double z = this.z * other.z;
		return x + y + z;
	}

	/**
	 * Cross product of this and given vector.
	 * 
	 * @param other Given vector.
	 * @return Cross product of two vectors.
	 */
	public Vector3 cross(Vector3 other) {
		double x = this.y * other.z - this.z * other.y;
		double y = this.x * other.z - this.z * other.x;
		double z = this.x * other.y - this.y * other.x;
		return new Vector3(x, -y, z);
	}

	/**
	 * Scale vector by scale value.
	 * 
	 * @param s Scale value.
	 * @return Scaled vector.
	 */
	public Vector3 scale(double s) {
		return new Vector3(s * x, s * y, s * z);
	}

	/**
	 * Return cosine of angle between this vector and given one.
	 * 
	 * @param other Given vector.
	 * @return Cosine of angle between this vector and given one.
	 */
	public double cosAngle(Vector3 other) {
		return dot(other) / (this.norm() * other.norm());
	}

	/**
	 * Returns x coordinate of the vector.
	 * 
	 * @return x coordinate of the vector.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns y coordinate of the vector.
	 * 
	 * @return y coordinate of the vector.
	 */
	public double getY() {
		return y;
	}

	/**
	 * Returns z coordinate of the vector.
	 * 
	 * @return Z coordinate of the vector.
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Returns this vector as an array of three elements, where first element is x
	 * coordinate of this vector, second element is y coordinate of the vector and
	 * third element is z coordinate of this vector.
	 * 
	 * @return Array representing this vector.
	 */
	public double[] toArray() {
		double[] array = { x, y, z };
		return array;
	}

	/**
	 * Returns string in form (x, y, z) where x, y and z are components of this
	 * vector.
	 */
	@Override
	public String toString() {
		return String.format("(%f, %f, %f)", x, y, z);
	}
}
