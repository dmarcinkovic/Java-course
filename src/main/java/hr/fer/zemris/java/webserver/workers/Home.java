package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IDispatcher;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class Home implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bgColor = context.getPersistentParameter("“”bgcolor”");
		
		if (bgColor != null) {
			context.setTemporaryParameter("background", bgColor);
		}else {
			context.setTemporaryParameter("background", "7F7F7F");
		}
		
		IDispatcher dispatcher = context.getDispatcher();
		
		dispatcher.dispatchRequest("/private/pages/home.smscr");
	}

}
