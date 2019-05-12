package hr.fer.zemris.math;

/**
 * The class model 2D vector whose components are real numbers: x and y.
 * 
 * @author david
 *
 */
public class Vector2D {
	/**
	 * X coordinate of the vector.
	 */
	private double x;

	/**
	 * Y coordinate of the vector.
	 */
	private double y;

	/**
	 * Constructor to initialize private members of this class.
	 * 
	 * @param x x coordinate of the vector.
	 * @param y y coordinate of the vector.
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
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
	 * @return y coordinate of the vector
	 */
	public double getY() {
		return y;
	}

	/**
	 * Method to translate this vector by given offset.
	 * 
	 * @param offset Vector for which this vector is translated.
	 * @throws NullPointerException if the offset is null.
	 */
	public void translate(Vector2D offset) {
		if (offset == null) {
			throw new NullPointerException();
		}
		x += offset.getX();
		y += offset.getY();
	}

	/**
	 * Method to translate this vector by given offset and return it.
	 * 
	 * @param offset Vector for which this vector is translated.
	 * @return translated vector.
	 * @throws NullPointerException if the offset is null.
	 */
	public Vector2D translated(Vector2D offset) {
		if (offset == null) {
			throw new NullPointerException();
		}
		Vector2D translated = new Vector2D(offset.getX() + x, offset.getY() + y);
		return translated;
	}

	/**
	 * Method to rotate this vector by given angle.
	 * 
	 * @param angle The angle for which the this vector is rotated.
	 */
	public void rotate(double angle) {
		double currentAngle = Math.atan2(y, x);
		double length = Math.sqrt(x * x + y * y);
		x = length * Math.cos(angle + currentAngle);
		y = length * Math.sin(angle + currentAngle);
	}

	/**
	 * Method to rotate this vector by given angle and return it.
	 * 
	 * @param angle The angle for which the this vector is rotated.
	 * @return rotated vector.
	 */
	public Vector2D rotated(double angle) {
		double currentAngle = Math.atan2(y, x);
		double length = Math.sqrt(x * x + y * y);
		Vector2D vector = new Vector2D(x = length * Math.cos(angle + currentAngle),
				length * Math.sin(angle + currentAngle));
		return vector;
	}

	/**
	 * Method to scale this vector by scaler value.
	 * 
	 * @param scaler value to scale vector.
	 */
	public void scale(double scaler) {
		double angle = Math.atan2(y, x);
		double length = Math.sqrt(x * x + y * y);
		x = length * scaler * Math.cos(angle);
		y = length * scaler * Math.sin(angle);
	}

	/**
	 * Method to scale this vector by scaler and return new vector.
	 * 
	 * @param scaler value to scale vector.
	 * @return scaled vector.
	 */
	public Vector2D scaled(double scaler) {
		double angle = Math.atan2(y, x);
		double length = Math.sqrt(x * x + y * y);
		return new Vector2D(length * scaler * Math.cos(angle), length * scaler * Math.sin(angle));
	}

	/**
	 * Method to copy this vector.
	 * 
	 * @return copied vector.
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
}
