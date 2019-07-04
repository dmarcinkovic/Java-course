package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.hw16.jvdraw.listeners.ColorChangeListener;

/**
 * Class that represents Status Bar component. It have text aligned to the
 * center of status bar. It has black line border whose stroke is one.
 * 
 * @author David
 *
 */
public class JStatusBar extends JLabel implements ColorChangeListener {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Reference to foreground color provider.
	 */
	private IColorProvider fgColorProvider;

	/**
	 * Reference to background color provider.
	 */
	private IColorProvider bgColorProvider;

	/**
	 * Construct that allows user to initialize text that will be presented at
	 * center of status bar.
	 * 
	 * @param fgColorProvider
	 * @param bgColorProvider
	 */
	public JStatusBar(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;

		setBorder(BorderFactory.createLineBorder(Color.black));
		setHorizontalAlignment(SwingConstants.CENTER);

		showStausBarInfo();
		
		this.fgColorProvider.addColorChangeListener(this);
		this.bgColorProvider.addColorChangeListener(this);
	}

	/**
	 * Method that show info on current foreground and background color. It show
	 * red, green and blue component of those colors.
	 */
	private void showStausBarInfo() {
		Color fg = fgColorProvider.getCurrentColor();
		Color bg = bgColorProvider.getCurrentColor();
		setText("Foreground color: (" + fg.getRed() + ", " + fg.getGreen() + ", " + fg.getBlue()
				+ "), background color: (" + bg.getRed() + ", " + bg.getGreen() + ", " + bg.getBlue() + ").");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		Color fg = null; 
		Color bg = null;
		if (source == fgColorProvider) {
			fg = newColor;
			bg = bgColorProvider.getCurrentColor();
		}else {
			bg = newColor;
			fg = fgColorProvider.getCurrentColor();
		}
		
		setText("Foreground color: (" + fg.getRed() + ", " + fg.getGreen() + ", " + fg.getBlue()
			+ "), background color: (" + bg.getRed() + ", " + bg.getGreen() + ", " + bg.getBlue() + ").");
	}
	
	
}
