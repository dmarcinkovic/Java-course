package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity class that represents blog comment. Blog comment have information
 * about the user's email and stores comment message. Blog comments can add all
 * users, logged and not logged.
 * 
 * @author David
 *
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

	/**
	 * Blog comment id.
	 */
	private Long id;

	/**
	 * Reference to blog entry in which this comment is posted.
	 */
	private BlogEntry blogEntry;

	/**
	 * Mail of user who posted this comment.
	 */
	private String usersEMail;

	/**
	 * Comment message.
	 */
	private String message;

	/**
	 * Date when this comment is posted.
	 */
	private Date postedOn;

	/**
	 * Returns id.
	 * 
	 * @return blog comment id.
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Sets blog comment id.
	 * 
	 * @param id Comment id.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns reference to blog entry.
	 * 
	 * @return Reference to blog entry.
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Sets reference to blog entry.
	 * 
	 * @param blogEntry Reference to blog entry.
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Returns user's email.
	 * 
	 * @return User's email.
	 */
	@Column(length = 100, nullable = false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Sets user's email.
	 * 
	 * @param usersEMail User's email.
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Returns message comment.
	 * 
	 * @return Message comment.
	 */
	@Column(length = 4096, nullable = false)
	public String getMessage() {
		return message;
	}

	/**
	 * Sets message commnet.
	 * 
	 * @param message Message comment.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Returns the date when this comment is posted.
	 * 
	 * @return The date when this comment is posted.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Sets the date when this comment is posted.
	 * 
	 * @param postedOn The date when this commnet is posted.
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}