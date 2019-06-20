package hr.fer.zemris.java.tecaj_13.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;

/**
 * Entity class that represents blog comment. Blog comment have information
 * about the user's email and stores comment message. Blog comments can add all
 * users, logged and not logged.
 * 
 * @author David
 *
 */
public class BlogCommentForm {

	/**
	 * Blog comment id.
	 */
	private String id;

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
	private String postedOn;

	/**
	 * Map of all errors.
	 */
	Map<String, String> errors = new HashMap<>();

	/**
	 * Method used to fill this BlogCommentForm from HttpRequest.
	 * 
	 * @param req HttpRequest.
	 */
	public void popuniIzHttpRequesta(HttpServletRequest req) {
		message = prepare(req.getParameter("comment.message"));
		usersEMail = prepare(req.getParameter("usersEMail"));
	}

	/**
	 * Returns empty string if null is provided, otherwise it returns String itself.
	 * 
	 * @param s String provided.
	 * @return Empty string if null is provided, otherwise it returns String itself.
	 */
	private String prepare(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}

	/**
	 * Returns value from errors map.
	 * 
	 * @param error Key.
	 * @return Value from errors map.
	 */
	public String getError(String error) {
		return errors.get(error);
	}

	/**
	 * Checks if any error has occur.
	 * 
	 * @return True if error has occur, otherwise returns false.
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	/**
	 * Checks if there is error with specified name.
	 * 
	 * @param errorName Error name.
	 * @return True if there is error with specified name.
	 */
	public boolean hasError(String errorName) {
		return errors.containsKey(errorName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "BlogComment [id=" + id + ", usersEMail=" + usersEMail + ", message=" + message + ", postedOn="
				+ postedOn + "]";
	}

	/**
	 * Fills this Form from given BlogComment.
	 * 
	 * @param comment Given BlogComment.
	 */
	public void popuniIzRecorda(BlogComment comment) {
		this.message = comment.getMessage();
		this.usersEMail = comment.getUsersEMail();
	}

	/**
	 * Fills given BlogComment from this form.
	 * 
	 * @param comment Given BlogComment.
	 */
	public void popuniURecord(BlogComment comment) {
		comment.setMessage(message);
		comment.setUsersEMail(usersEMail);
	}

	/**
	 * Method to validate comment. Comment is valid if is not empty.
	 */
	public void validateComment() {
		errors.clear();
		if (message.isEmpty()) {
			errors.put("message", "Comment should not be empty.");
		}

		if (usersEMail.isEmpty()) {
			errors.put("usersEMail", "EMail is required!");
		} else {
			int l = usersEMail.length();
			int p = usersEMail.indexOf('@');
			if (l < 3 || p == -1 || p == 0 || p == l - 1) {
				errors.put("usersEMail", "EMail is not in the correct format.");
			}
		}
	}

	/**
	 * Returns id.
	 * 
	 * @return ID.
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
	 * Returns user's email.
	 * 
	 * @return User's email.
	 */
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
	 * Return comment message.
	 * 
	 * @return Comment message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets comment message.
	 * 
	 * @param message Comment message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Returns the date when this comment is posted as String.
	 * 
	 * @return The date when this comment is posted as String.
	 */
	public String getPostedOn() {
		return postedOn;
	}

	/**
	 * Sets the date when this comment is posted as String.
	 * 
	 * @param postedOn The date this comment is posted as String.
	 */
	public void setPostedOn(String postedOn) {
		this.postedOn = postedOn;
	}
}
