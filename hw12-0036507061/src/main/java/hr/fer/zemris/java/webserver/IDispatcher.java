package hr.fer.zemris.java.webserver;

/**
 * Interface representing Dispatcher. Implementations of this interface are able
 * to dispatch request.
 * 
 * @author David
 *
 */
public interface IDispatcher {

	/**
	 * Method used to serve the file with specified url path.
	 * 
	 * @param urlPath Url path that determines which file is served and represented
	 *                to the page.
	 * @throws Exception When error during serving file occurs.
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
