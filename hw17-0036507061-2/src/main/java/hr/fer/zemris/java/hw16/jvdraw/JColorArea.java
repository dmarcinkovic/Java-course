package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Component that is similar to button. This component is presented as filled
 * rectangle with size 15x15. This component is subject in Observer pattern.
 * 
 * @author David
 *
 */
public class JColorArea extends JPanel implements IColorProvider { 

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Selected color.
	 */
	private Color selectedColor;

	/**
	 * List of all registered listeners.
	 */
	private List<ColorChangeListener> listeners;

	/**
	 * Constructor used to initialize selectedColor.
	 * 
	 * @param selectedColor SelectedColor.
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;
		listeners = new ArrayList<>();
		
		setBackground(selectedColor);
		setForeground(selectedColor);
		setVisible(true);
		setBorder(BorderFactory.createLineBorder(selectedColor));
		setOpaque(true);
	}
	
	/**
	 * Setter for selectedColor.
	 * 
	 * @param selectedColor Selected color.
	 */
	public void setSelectedColor(Color selectedColor) {
		informListneres(this.selectedColor, selectedColor);
		this.selectedColor = selectedColor;
		
		setBorder(BorderFactory.createLineBorder(selectedColor));
		setBackground(selectedColor);
	}

	/**
	 * Method that informs all registered listener about the color change.
	 * 
	 * @param old      Old color.
	 * @param newColor New color.
	 */
	private void informListneres(Color old, Color newColor) {
		for (ColorChangeListener l : listeners) {
			l.newColorSelected(this, old, newColor);
		}
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}

}
