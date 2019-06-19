package hr.fer.zemris.java.tecaj_13.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

public class BlogEntryForm {
	private String id;
	private String createdAt;
	private String lastModifiedAt;
	private String title;
	private String text;
	Map<String, String> errors = new HashMap<>();

	public void popuniIzHttpRequesta(HttpServletRequest req) {
		createdAt = prepare(req.getParameter("createdAt"));
		lastModifiedAt = prepare(req.getParameter("lastModifiedAt"));
		title = prepare(req.getParameter("title"));
		text = prepare(req.getParameter("text"));
	}
	
	private String prepare(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
	
	public String getError(String error) {
		System.out.println(errors.get(error));
		return errors.get(error);
	}

	public boolean hasErrors() {
		System.out.println(!errors.isEmpty());
		return !errors.isEmpty();
	}

	public boolean hasError(String firstName) {
		System.out.println(errors.containsKey(firstName));
		return errors.containsKey(firstName);
	}
	
	@Override
	public String toString() {
		return "BlogEntryForm [title=" + title + ", text=" + text + "]";
	}

	public void popuniIzRecorda(BlogEntry entry) {
		title = entry.getTitle();
		text = entry.getText();
	}

	public void popuniURecord(BlogEntry entry) {
		entry.setTitle(title);
		entry.setText(text);
	}
	
	public void validateEntry() {
		errors.clear();
		if (title.isEmpty()) {
			errors.put("title", "Title should not be empty.");
		}
		
		if (text.isEmpty()) {
			errors.put("text", "Text should not be empty.");
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getLastModifiedAt() {
		return lastModifiedAt;
	}

	public void setLastModifiedAt(String lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
