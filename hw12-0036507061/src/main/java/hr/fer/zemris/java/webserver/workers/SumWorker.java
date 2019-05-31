package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IDispatcher;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that gets two numbers as parameters and sums them if they are
 * integers. It created page result of addition is presented, as well as image
 * at the bottom of the page. If result of addition if even then heart will be
 * presented, otherwise Simpson will be presented as gif image.
 * 
 * @author David
 *
 */
public class SumWorker implements IWebWorker {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		String firstParameter = context.getParameter("a");
		String secondParameter = context.getParameter("b");

		int a;
		int b;

		if (firstParameter == null) {
			a = 1;
		} else {
			a = Integer.parseInt(firstParameter);
		}

		if (secondParameter == null) {
			b = 2;
		} else {
			b = Integer.parseInt(secondParameter);
		}

		String result = String.valueOf(a + b);
		context.setTemporaryParameter("zbroj", result);
		context.setTemporaryParameter("varA", String.valueOf(a));
		context.setTemporaryParameter("varB", String.valueOf(b));

		if ((a + b) % 2 == 0) {
			context.setTemporaryParameter("imgName", "images/image1.gif");
		} else {
			context.setTemporaryParameter("imgName", "images/image2.gif");
		}
		IDispatcher dispatcher = context.getDispatcher();

		dispatcher.dispatchRequest("/private/pages/calc.smscr");
	}

}
