package hr.fer.zemris.java.tecaj_13.web.init;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMFProvider;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 
 * Web listener used to initialize entity manager factory and destroy it when
 * server stops. In the initialization of entity manager this listener connects
 * to baza.podataka.za.blog.
 * 
 * @author David
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("baza.podataka.za.blog");
		sce.getServletContext().setAttribute("my.application.emf", emf);
		JPAEMFProvider.setEmf(emf);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		JPAEMFProvider.setEmf(null);
		EntityManagerFactory emf = (EntityManagerFactory) sce.getServletContext().getAttribute("my.application.emf");
		if (emf != null) {
			emf.close();
		}
	}
}