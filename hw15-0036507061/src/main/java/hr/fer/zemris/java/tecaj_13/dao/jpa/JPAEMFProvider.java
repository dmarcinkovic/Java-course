package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * JPA EntityManagerFactory provider.
 * 
 * @author David
 *
 */
public class JPAEMFProvider {

	/**
	 * Reference to EntityManagerFactory. It is used to get ORM. In this program
	 * hibernate ORM is used.
	 */
	public static EntityManagerFactory emf;

	/**
	 * Return reference to EntityManagerFactory.
	 * 
	 * @return Reference to EntityManagerFactory.
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Sets reference to EntityManagerFactory.
	 * 
	 * @param emf Reference to EntityManagerFactory.
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}