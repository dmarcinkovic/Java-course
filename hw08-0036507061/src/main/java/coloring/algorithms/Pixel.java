package coloring.algorithms;

/**
 * Class that represents one pixel.
 * 
 * @author david
 *
 */
public class Pixel {

	/**
	 * X coordinate of the pixel.
	 */
	public int x;

	/**
	 * Y coordinate of the pixel.
	 */
	public int y;

	/**
	 * Constructor to initialize x and y coordinates of pixel.
	 * 
	 * @param x X coordinate of the pixel.
	 * @param y Y coordinate of the pixel.
	 */
	public Pixel(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pixel other = (Pixel) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

}
