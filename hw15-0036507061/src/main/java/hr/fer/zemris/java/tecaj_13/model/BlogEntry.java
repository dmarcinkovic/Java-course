package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class that represents blog entry. Adding blog entries and editing them can
 * only do registered users.
 * 
 * @author David
 *
 */
@NamedQueries({
		@NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when") })
@Entity
@Table(name = "blog_entries")
public class BlogEntry {

	/**
	 * Blog entry id.
	 */
	private Long id;

	/**
	 * List of comments this blog entry contains.
	 */
	private List<BlogComment> comments = new ArrayList<>();

	/**
	 * The date when this blog entry is created.
	 */
	private Date createdAt;

	/**
	 * The date of last modification.
	 */
	private Date lastModifiedAt;

	/**
	 * Title of blog entry.
	 */
	private String title;

	/**
	 * Text of blog entry.
	 */
	private String text;

	/**
	 * Reference to creator of this blog entry.
	 */
	private BlogUser creator;

	/**
	 * Returns reference to creator of this blog entry.
	 * 
	 * @return Reference to creator of this blog entry.
	 */
	@ManyToOne
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * Sets creator of this blog entry.
	 * 
	 * @param creator Creator of this blog entry.
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}

	/**
	 * Returns id.
	 * 
	 * @return Id.
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Sets id.
	 * 
	 * @param id ID.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns comments posted to this blog entry.
	 * 
	 * @return Comments posted to this blog entry.
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Sets comment posted to this blog entry.
	 * 
	 * @param comments Comments posted to this blog entry.
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Returns the date this blog entry is created.
	 * 
	 * @return Date this blog entry is created.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the date this blog entry is created.
	 * 
	 * @param createdAt Date this blog entry is created.
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Returns the date of last modification.
	 * 
	 * @return Date of last modification.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Sets the date of last modification.
	 * 
	 * @param lastModifiedAt Date of last modification.
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Title of this blog entry.
	 * 
	 * @return Title of this blog entry.
	 */
	@Column(length = 200, nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * Sets title of this blog entry.
	 * 
	 * @param title Title of this blog entry.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the text of this blog entry.
	 * 
	 * @return text of this blog entry.
	 */
	@Column(length = 4096, nullable = false)
	public String getText() {
		return text;
	}

	/**
	 * Sets text of this blog entry.
	 * 
	 * @param text Text of this blog entry.
	 */
	public void setText(String text) {
		this.text = text;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}