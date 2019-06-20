package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * Class used to provide other classes with DAO.
 * 
 * @author David
 *
 */
public class DAOProvider {

	/**
	 * Reference to dao implementation.
	 */
	private static DAO dao = new JPADAOImpl();

	/**
	 * Returns reference to DAO implementation.
	 * 
	 * @return Reference to DAO implementation.
	 */
	public static DAO getDAO() {
		return dao;
	}
}