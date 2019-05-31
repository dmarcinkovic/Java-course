package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IDispatcher;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * 
 * Worker that is used to serve index2.html file. This file creates three links:
 * one for osnovni.smscr, one for brojPoziva.smscr and one for fibonacci.smscr.
 * Also it creates links to HelloWorker and CircleWorker. At the middle of the
 * page there are two text boxes in which user can type two number to be added
 * using SumWorker. At the bottom of the page combo box is presented which
 * allows user to change background of the page.
 * 
 * @author David
 *
 */
public class Home implements IWebWorker {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bgColor = context.getPersistentParameter("bgcolor");

		if (bgColor != null) {
			context.setTemporaryParameter("background", bgColor);
			context.setPersistentParameter("bgcolor", bgColor);
		} else {
			context.setTemporaryParameter("background", "7F7F7F");
			context.setPersistentParameter("colorUpdated", "yes");
		}

		IDispatcher dispatcher = context.getDispatcher();

		dispatcher.dispatchRequest("/private/pages/home.smscr");
	}

}
