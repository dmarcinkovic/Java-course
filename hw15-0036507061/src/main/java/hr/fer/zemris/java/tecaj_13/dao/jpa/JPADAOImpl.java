package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Implementation of DAO interface.
 * 
 * @author David
 *
 */
public class JPADAOImpl implements DAO {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogUser getUser(String nick) throws DAOException {
		@SuppressWarnings("unchecked")
		List<BlogUser> user = JPAEMProvider.getEntityManager()
				.createQuery("SELECT b from BlogUser as " + "b where b" + ".nick=:n").setParameter("n", nick)
				.getResultList();

		if (user.isEmpty()) {
			return null;
		}
		return user.get(0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BlogEntry> getBlogEntryForUser(BlogUser user) throws DAOException {
		@SuppressWarnings("unchecked")
		List<BlogEntry> entries = JPAEMProvider.getEntityManager()
				.createQuery("SELECT b FROM BlogEntry as b where b.creator=:n").setParameter("n", user).getResultList();

		return entries;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BlogUser> getUsers() throws DAOException {
		@SuppressWarnings("unchecked")
		List<BlogUser> users = JPAEMProvider.getEntityManager().createQuery("SELECT b FROM BlogUser as b ")
				.getResultList();
		return users;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void persistUser(BlogUser user) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		if (user.getId() == null) {
			em.persist(user);
			return;
		}
		em.merge(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void persistComment(BlogComment comment) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		if (comment.getId() == null) {
			em.persist(comment);
			return;
		}
		em.merge(comment);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void persistEntry(BlogEntry entry) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		if (entry.getId() == null) {
			em.persist(entry);
			return;
		}
		em.merge(entry);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogEntry getBlogEntryForID(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

}