package hr.fer.zemris.java.hw13.servleti;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener used to listen when the server is started. When server is started,
 * in session map with key equal to 'time' will be remembered current time. This
 * information is then used to display the time this server is running.
 * 
 * @author David
 *
 */
@WebListener
public class AppInfo implements ServletContextListener {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		long currentMilis = System.currentTimeMillis();
		sce.getServletContext().setAttribute("time", currentMilis);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
