package hr.fer.zemris.java.webserver;

/**
 * Interface that represents class able to process request. Every worker has
 * some specific task to be executed.
 * 
 * @author David
 *
 */
public interface IWebWorker {

	/**
	 * Method used to process request.
	 * 
	 * @param context Reference to RequestContext.
	 * @throws Exception when error during serving file occurs.
	 */
	public void processRequest(RequestContext context) throws Exception;
}
