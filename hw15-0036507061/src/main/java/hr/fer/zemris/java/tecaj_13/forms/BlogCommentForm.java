package hr.fer.zemris.java.tecaj_13.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;

public class BlogCommentForm {
	private String id;
	private String usersEMail;
	private String message;
	private String postedOn;
	Map<String, String> errors = new HashMap<>();
	
	public void popuniIzHttpRequesta(HttpServletRequest req) {
		message = prepare(req.getParameter("comment.message"));
		usersEMail = prepare(req.getParameter("usersEMail"));
	}
	
	private String prepare(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}

	public String getError(String error) {
		return errors.get(error);
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	public boolean hasError(String firstName) {
		return errors.containsKey(firstName);
	}

	@Override
	public String toString() {
		return "BlogComment [id=" + id + ", usersEMail=" + usersEMail + ", message=" + message + ", postedOn="
				+ postedOn + "]";
	}

	public void popuniIzRecorda(BlogComment comment) {
		this.message = comment.getMessage();
		this.usersEMail = comment.getUsersEMail();
	}
	
	public void popuniURecord(BlogComment comment) {
		comment.setMessage(message);
		comment.setUsersEMail(usersEMail);
	}
	
	public void validateComment() {
		errors.clear();
		if (message.isEmpty()) {
			errors.put("message", "Comment should not be empty.");
		}
		
		if(usersEMail.isEmpty()) {
			errors.put("usersEMail", "EMail is required!");
		} else {
			int l = usersEMail.length();
			int p = usersEMail.indexOf('@');
			if(l<3 || p==-1 || p==0 || p==l-1) {
				errors.put("usersEMail", "EMail is not in the correct format.");
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsersEMail() {
		return usersEMail;
	}

	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(String postedOn) {
		this.postedOn = postedOn;
	}
}
