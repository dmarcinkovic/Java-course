package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker used to draw red circle with white background. That image is presented
 * at the center of the page and is 200x200 large. This page can be accessed by
 * typing /ext/CircleWorker or /cw.
 * 
 * @author David
 *
 */
public class CircleWorker implements IWebWorker {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = bim.createGraphics();

		// Those four lines are copied from out last lesson. In class HTTPServer.
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, bim.getWidth(), bim.getHeight());
		g2d.setColor(Color.RED);
		g2d.fillOval(0, 0, bim.getWidth(), bim.getHeight());

		g2d.dispose();

		context.setMimeType("image/png");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bim, "png", bos);
			context.write(bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
