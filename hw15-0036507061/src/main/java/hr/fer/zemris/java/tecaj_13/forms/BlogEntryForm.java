package hr.fer.zemris.java.tecaj_13.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * Class that represents blog entry. Adding blog entries and editing them can
 * only do registered users.
 * 
 * @author David
 *
 */
public class BlogEntryForm {

	/**
	 * Blog entry id.
	 */
	private String id;

	/**
	 * The date when this blog entry is created.
	 */
	private String createdAt;

	/**
	 * The date of last modification.
	 */
	private String lastModifiedAt;

	/**
	 * Title of blog entry.
	 */
	private String title;

	/**
	 * Text of blog entry.
	 */
	private String text;

	/**
	 * Map of all errors.
	 */
	Map<String, String> errors = new HashMap<>();

	/**
	 * Method used to fill this BlogCommentForm from HttpRequest.
	 * 
	 * @param req HttpRequest.
	 */
	public void fillFromHttpRequest(HttpServletRequest req) {
		createdAt = prepare(req.getParameter("createdAt"));
		lastModifiedAt = prepare(req.getParameter("lastModifiedAt"));
		title = prepare(req.getParameter("title"));
		text = prepare(req.getParameter("text"));
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
		return "BlogEntryForm [title=" + title + ", text=" + text + "]";
	}

	/**
	 * Fills this Form from given BlogEntry.
	 * 
	 * @param entry Given BlogEntry.
	 */
	public void fillFromRecord(BlogEntry entry) {
		title = entry.getTitle();
		text = entry.getText();
	}

	/**
	 * Fills given BlogEntry from this form.
	 * 
	 * @param entry Given BlogEntry.
	 */
	public void fillToRecord(BlogEntry entry) {
		entry.setTitle(title);
		entry.setText(text);
	}

	/**
	 * Method to validate Entry. Entry is valid if title and text are not empty.
	 */
	public void validateEntry() {
		errors.clear();
		if (title.isEmpty()) {
			errors.put("title", "Title should not be empty.");
		}

		if (text.isEmpty()) {
			errors.put("text", "Text should not be empty.");
		}
	}

	/**
	 * Returns id.
	 * 
	 * @return id.
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
	 * Returns the date when this blog entry is created.
	 * 
	 * @return The date when this blog etnry is created.
	 */
	public String getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the date when this blog entry is created.
	 * 
	 * @param createdAt Date when this blog entry is created.
	 */
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Returns the date of last modification of this blog entry.
	 * 
	 * @return Date of last modification of this blog entry.
	 */
	public String getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Sets the date of last modification of this blog entry.
	 * 
	 * @param lastModifiedAt Date of last modification of this blog entry.
	 */
	public void setLastModifiedAt(String lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Returns title.
	 * 
	 * @return Title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets title.
	 * 
	 * @param title Title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns text.
	 * 
	 * @return Text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets text.
	 * 
	 * @param text Text.
	 */
	public void setText(String text) {
		this.text = text;
	}

}
