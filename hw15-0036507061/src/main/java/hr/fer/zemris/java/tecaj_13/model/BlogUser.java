package hr.fer.zemris.java.tecaj_13.model;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Class that represents blog user. Blog user can add and edit blog entries.
 * 
 * @author David
 *
 */
@Entity
@Table(name = "blog_users")
public class BlogUser {

	/**
	 * Id of blog user. This is primary key.
	 */
	private Long id;

	/**
	 * First name of blog user.
	 */
	private String firstName;

	/**
	 * Last name of blog user.
	 */
	private String lastName;

	/**
	 * Nick of blog user. This is unique.
	 */
	private String nick;

	/**
	 * Email of blog user.
	 */
	private String email;

	/**
	 * Password stored as hash using secure hash algorithm.
	 */
	private String passwordHash;

	/**
	 * Collection of blog entries this user added.
	 */
	private Collection<BlogEntry> entries = new HashSet<>();

	/**
	 * Primary key.
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
	 * Returns user's first name.
	 * 
	 * @return User's first name.
	 */
	@Column(nullable = false)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets user's first name.
	 * 
	 * @param firstName User's first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns user's last name.
	 * 
	 * @return User's last name.
	 */
	@Column(nullable = false)
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets user's last name.
	 * 
	 * @param lastName User's last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns unique user's nick.
	 * 
	 * @return User's nick.
	 */
	@Column(unique = true, nullable = false)
	public String getNick() {
		return nick;
	}

	/**
	 * Sets user's nick.
	 * 
	 * @param nick User's nick.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Returns user's email.
	 * 
	 * @return User's email.
	 */
	@Column(nullable = false)
	public String getEmail() {
		return email;
	}

	/**
	 * Sets user's email.
	 * 
	 * @param email User's email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns password.
	 * 
	 * @return Password.
	 */
	@Column(nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Sets password.
	 * 
	 * @param passwordHash Password stored using secure hash algorithm.
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Returns entries this user added.
	 * 
	 * @return Entries this user added.
	 */
	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	public Collection<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * Sets entries.
	 * 
	 * @param entries Entries.
	 */
	public void setEntries(Collection<BlogEntry> entries) {
		this.entries = entries;
	}
}
