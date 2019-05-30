package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IDispatcher;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class BgColorWorker implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bgColor = context.getParameter("bgcolor");
		
		if (bgColor != null && bgColor.length() == 6) {
			context.setPersistentParameter("bgcolor", bgColor);
			context.setTemporaryParameter("colorUpdated", "yes");
		}else {
			context.setTemporaryParameter("colorUpdated", "no");
		}
		
		IDispatcher dispatcher = context.getDispatcher();
	
		dispatcher.dispatchRequest("/index2.html");
	}

}
