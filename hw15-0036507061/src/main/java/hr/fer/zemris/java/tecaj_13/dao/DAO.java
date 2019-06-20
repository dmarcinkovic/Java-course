package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Interface which implementations will communicate with database and store and
 * get data from database.
 * 
 * @author David
 *
 */
public interface DAO {

	/**
	 * Retrieves entry with provided id. If such a entry does not exists returns
	 * null.
	 * 
	 * @param id Primary key.
	 * @return entry of null if such a entry does not exists.
	 * @throws DAOException If error occurs while retrieving data.
	 */
	public BlogEntry getBlogEntryForID(Long id) throws DAOException;

	/**
	 * Retrieves list of blog entries with provided creator. Returns null if creator
	 * did not create blog entries.
	 * 
	 * @param user Creator of blog entries.
	 * @return List of blog entries with provided creator.
	 */
	List<BlogEntry> getBlogEntryForUser(BlogUser user);

	/**
	 * Returns user with nick provided.
	 * 
	 * @param nick Nick provided.
	 * @return User with nick provided.
	 */
	BlogUser getUser(String nick);

	/**
	 * Retrieves list of BlogUser-s.
	 * 
	 * @return List of BlogUser-s.
	 */
	List<BlogUser> getUsers();

	/**
	 * Method used to persist BlogUser in database.
	 * 
	 * @param user User that will be persisted.
	 */
	void persistUser(BlogUser user);

	/**
	 * Method used to persist BlogEntry in database.
	 * 
	 * @param entry Entry that will be persisted.
	 */
	void persistEntry(BlogEntry entry);

	/**
	 * Method used to persist BlogComment in database.
	 * 
	 * @param comment Blog Comment that will be persisted.
	 */
	void persistComment(BlogComment comment);

}