package hr.fer.zemris.java.tecaj_13.forms;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Class that represents blog user. Blog user can add and edit blog entries.
 * 
 * @author David
 *
 */
public class BlogUserForm {

	/**
	 * Id of blog user. This is primary key.
	 */
	private String id;

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
	 * Map with errors.
	 */
	Map<String, String> errors = new HashMap<>();

	/**
	 * Constructor.
	 */
	public BlogUserForm() {
	}

	/**
	 * Sets value to errors map.
	 * 
	 * @param errorName Key.
	 * @param error     Value.
	 */
	public void setGreske(String errorName, String error) {
		errors.put(errorName, error);
	}

	/**
	 * Dohvaća poruku pogreške za traženo svojstvo.
	 * 
	 * @param ime naziv svojstva za koje se traži poruka pogreške
	 * @return poruku pogreške ili <code>null</code> ako svojstvo nema pridruženu
	 *         pogrešku
	 */
	public String dohvatiPogresku(String ime) {
		return errors.get(ime);
	}

	/**
	 * Provjera ima li barem jedno od svojstava pridruženu pogrešku.
	 * 
	 * @return <code>true</code> ako ima, <code>false</code> inače.
	 */
	public boolean imaPogresaka() {
		return !errors.isEmpty();
	}

	/**
	 * Provjerava ima li traženo svojstvo pridruženu pogrešku.
	 * 
	 * @param ime naziv svojstva za koje se ispituje postojanje pogreške
	 * @return <code>true</code> ako ima, <code>false</code> inače.
	 */
	public boolean imaPogresku(String firstName) {
		return errors.containsKey(firstName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "BlogUserForm [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", nick=" + nick
				+ ", email=" + email + ", passwordHash=" + passwordHash + "]";
	}

	/**
	 * Method used to fill this BlogUserForm from HttpRequest.
	 * 
	 * @param req HttpRequest.
	 */
	public void popuniIzHttpRequesta(HttpServletRequest req) {
		this.firstName = pripremi(req.getParameter("firstName"));
		this.lastName = pripremi(req.getParameter("lastName"));
		this.email = pripremi(req.getParameter("email"));

		this.passwordHash = getHashedPassword(req.getParameter("passwordHash"));
		this.nick = pripremi(req.getParameter("nick"));
	}

	/**
	 * Returns hashed password.
	 * 
	 * @param password Plain password.
	 * @return Hashed password.
	 */
	private String getHashedPassword(String password) {
		if (password == null || password.isEmpty()) {
			return "";
		}
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			byte[] array = password.getBytes();
			byte[] hash = sha.digest(array);

			return bytetohex(hash).trim();
		} catch (NoSuchAlgorithmException e) {
		}
		return null;
	}

	/**
	 * Method that converts hexadecimal byte array to String.
	 * 
	 * @param byteArray byte array to be converted.
	 * @return Hexadecimal representation of given byte array.
	 * @throws NullPointerException if byteArray is null.
	 */
	private String bytetohex(byte[] byteArray) {
		if (byteArray == null) {
			throw new NullPointerException();
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < byteArray.length; i++) {
			String hex = Integer.toHexString(0xff & byteArray[i]);

			if (hex.length() == 1) {
				sb.append("0");
			}
			sb.append(hex);
		}

		return sb.toString();
	}

	/**
	 * Fills this Form from given BlogUser.
	 * 
	 * @param blogUser Given BloUser.
	 */
	public void popuniIzRecorda(BlogUser blogUser) {
		this.firstName = blogUser.getFirstName();
		this.lastName = blogUser.getLastName();
		this.email = blogUser.getEmail();
		this.nick = blogUser.getNick();
		this.passwordHash = blogUser.getPasswordHash();
	}

	/**
	 * Fills given BlogUser from this form.
	 * 
	 * @param blogUser Given BlogUser.
	 */
	public void popuniURecord(BlogUser blogUser) {
		blogUser.setFirstName(this.firstName);
		blogUser.setLastName(this.lastName);
		blogUser.setEmail(this.email);
		blogUser.setPasswordHash(this.passwordHash);
		blogUser.setNick(this.nick);
	}

	/**
	 * Method to validate Login. It checks if passwordHash and nick are empty.If
	 * they are empty then we put error in errors map.
	 */
	public void validateLogin() {
		errors.clear();
		if (this.passwordHash.isEmpty()) {
			errors.put("passwordHash", "Password is required!");
		}

		if (this.nick.isEmpty()) {
			errors.put("nick", "Nick is required");
		}
	}

	/**
	 * Method to validate registration. It check if fields are empty. If they are
	 * empty then we put error in errors map.
	 */
	public void validateRegistration() {
		validateLogin();

		if (this.firstName.isEmpty()) {
			errors.put("firstName", "First name is required!");
		}

		if (this.lastName.isEmpty()) {
			errors.put("lastName", "Last name is required!");
		}

		if (this.email.isEmpty()) {
			errors.put("email", "EMail is required!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if (l < 3 || p == -1 || p == 0 || p == l - 1) {
				errors.put("email", "EMail is not in the correct format.");
			}
		}
	}

	/**
	 * Returns empty string if null is provided, otherwise it returns String itself.
	 * 
	 * @param s String provided.
	 * @return Empty string if null is provided, otherwise it returns String itself.
	 */
	private String pripremi(String s) {
		if (s == null)
			return "";
		return s.trim();
	}

	/**
	 * Returns id.
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets id.
	 * 
	 * @param id Id.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns first name.
	 * 
	 * @return First name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets first name.
	 * 
	 * @param firstName First name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns last name.
	 * 
	 * @return Last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Set the last name.
	 * 
	 * @param lastName Last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns nick.
	 * 
	 * @return Nick.
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Sets nick.
	 * 
	 * @param nick Nick.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Returns email.
	 * 
	 * @return Email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets email.
	 * 
	 * @param email Email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns password hash.
	 * 
	 * @return Password hash.
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Sets password hash.
	 * 
	 * @param passwordHash Password hash.
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
}
