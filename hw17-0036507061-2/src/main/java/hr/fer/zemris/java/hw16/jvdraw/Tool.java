package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Interface that models tools available. There are three tools that this
 * application offers to user: drawing of lines, drawing of circles and drawing
 * of filled circles. Those object are added using mouse clicks.
 * 
 * @author David
 *
 */
public interface Tool {

	/**
	 * Method called when mouse is pressed.
	 * 
	 * @param e Mouse Event.
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * Method called when mouse is released.
	 * 
	 * @param e Mouse Event.
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * Method called when mouse is clicked. That means that mouse is pressed and
	 * then released.
	 * 
	 * @param e Mouse Event.
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * Method called when mouse is moved.
	 * 
	 * @param e Mouse Event.
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * Method called when mouse is dragged. This means that mouse is pressed and
	 * moved.
	 * 
	 * @param e Mouse Event.
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Method used to paint graphical object(lines, circles and filled circles).
	 * 
	 * @param g2d Reference to Graphics2D object.
	 */
	public void paint(Graphics2D g2d);
}
