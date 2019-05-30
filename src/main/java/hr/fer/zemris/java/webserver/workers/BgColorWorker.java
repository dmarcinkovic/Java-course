package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IDispatcher;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker used to change the background color if correct color is provided.
 * Otherwise it will cancel the request for color change and inform user that
 * color is not modified.
 * 
 * @author David
 *
 */
public class BgColorWorker implements IWebWorker {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bgColor = context.getParameter("bgcolor");

		if (bgColor != null && bgColor.length() == 6) {
			context.setPersistentParameter("bgcolor", bgColor);
			context.setTemporaryParameter("colorUpdated", "yes");
		} else {
			context.setTemporaryParameter("colorUpdated", "no");
		}

		IDispatcher dispatcher = context.getDispatcher();

		dispatcher.dispatchRequest("/index2.html");
	}

}
