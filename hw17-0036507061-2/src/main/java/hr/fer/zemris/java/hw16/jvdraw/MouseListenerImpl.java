package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JColorChooser;

/**
 * Class that implements MouseListener. It is used to change the foreground and
 * background color of Graphical objects used in this application.
 * 
 * @author David
 *
 */
public class MouseListenerImpl implements MouseListener {

	/**
	 * Reference to Content pane.
	 */
	private Container cp;

	/**
	 * Constructor.
	 * 
	 * @param cp Reference to Content pane.
	 */
	public MouseListenerImpl(Container cp) {
		this.cp = cp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		JColorArea source = (JColorArea) e.getSource();
		Color c = JColorChooser.showDialog(cp, "Choose the color", source.getCurrentColor());

		if (c != null) {
			source.setSelectedColor(c);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public void mouseExited(MouseEvent e) {
	}

}
