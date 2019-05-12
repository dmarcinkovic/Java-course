package coloring.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;

/**
 * Class that implements all required method to implement coloring algorithm.
 * 
 * @author david
 *
 */
public class Coloring implements Consumer<Pixel>, Predicate<Pixel>, Supplier<Pixel>, Function<Pixel, List<Pixel>> {
	/**
	 * Reference to Pixel. 
	 */
	private Pixel reference;
	
	/**
	 * Reference to picture.
	 */
	private Picture picture;
	
	/**
	 * Fill color. 
	 */
	private int fillColor;
	
	/**
	 * Ref color.
	 */
	private int refColor;

	/**
	 * Constructor to initialize private fields.
	 * 
	 * @param reference Reference to pixel.
	 * @param picture   Reference to picture.
	 * @param fillColor Fill color.
	 */
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		this.reference = reference;
		this.picture = picture;
		this.fillColor = fillColor;

		refColor = picture.getPixelColor(reference.x, reference.y);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(Pixel t) {
		picture.setPixelColor(t.x, t.y, fillColor);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean test(Pixel t) {
		return picture.getPixelColor(t.x, t.y) == refColor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Pixel get() {
		return reference;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Pixel> apply(Pixel t) {
		List<Pixel> pixels = new LinkedList<>();

		pixels.add(new Pixel(t.x - 1, t.y));
		pixels.add(new Pixel(t.x, t.y + 1));
		pixels.add(new Pixel(t.x, t.y - 1));
		pixels.add(new Pixel(t.x + 1, t.y));

		return pixels;
	}

}
