package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.forms.BlogCommentForm;
import hr.fer.zemris.java.tecaj_13.forms.BlogEntryForm;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet implementation class Authors
 */
@WebServlet("/servleti/author/*")
public class Authors extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private boolean permits = false;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Authors() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();

		if (pathInfo == null) {
			return;
		}

		if (!pathInfo.matches("/.*")) {
			return;
		}

		String nick = getNick(pathInfo);
		String currentUser = (String) request.getSession().getAttribute("current.user.nick");

		if (nick.equals(currentUser)) {
			permits = true;
		} else {
			permits = false;
		}

		request.setAttribute("permits", permits);
		if (pathInfo.matches("/\\w+/new")) {
			newMethod(request, response);
			return;
		} else if (pathInfo.matches("/\\w+/edit")) {
			editMethod(request, response);
			return;
		} else if (pathInfo.matches("/\\w+/\\d+")) {
			blogEntryPage(request, response);
			return;
		} else if (pathInfo.matches("/\\w+")) {
			author(request, response);
			return;
		}

		request.getSession().setAttribute("error", "Wrong path.");
		request.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(request, response);
	}

	private void author(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		String nick = pathInfo.substring(1);

		request.getSession().setAttribute("nick", nick);

		DAO dao = DAOProvider.getDAO();
		BlogUser user = dao.getUser(nick);

		if (user == null) {
			request.getSession().setAttribute("error", "User does not exists.");
			request.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(request, response);
			return;
		}

		List<BlogEntry> entries = dao.getBlogEntryForUser(user);	

		request.setAttribute("blogEntries", entries);
		request.getRequestDispatcher("/WEB-INF/pages/Authors.jsp").forward(request, response);
	}

	private void blogEntryPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		Long id = Long.parseLong(pathInfo.substring(pathInfo.lastIndexOf('/') + 1));

		String nick = getNick(pathInfo);
		DAO dao = DAOProvider.getDAO();
		
		BlogEntry entry = dao.getBlogEntry(id);

		if (!entry.getCreator().getNick().equals(nick)) {
			request.getSession().setAttribute("error", "Error. User is not owner of blog entry with given id.");
			request.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(request, response);
			return;
		}
		
		BlogEntryForm form = new BlogEntryForm();
		form.popuniIzRecorda(entry);

		request.setAttribute("zapis", entry);
		List<BlogComment> comments = entry.getComments();
		request.setAttribute("comments", comments);
		
		request.setAttribute("nick", nick);
		request.setAttribute("id", id.toString());
		
		if (request.getParameter("comment.message") != null) {
			addComment(request, response);
			return;
		}
		
		BlogCommentForm commentForm = new BlogCommentForm();
		request.setAttribute("commentForm", commentForm);
		request.setAttribute("entryComment", entry);
		
		request.getRequestDispatcher("/WEB-INF/pages/BlogEntries.jsp").forward(request, response);
	}

	private void editMethod(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		String nick = getNick(pathInfo);

		DAO dao = DAOProvider.getDAO();
		BlogUser user = dao.getUser(nick);

		if (user == null) {
			request.getSession().setAttribute("error", "User does not exists.");
			request.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(request, response);
			return;
		}

		List<BlogEntry> entries = dao.getBlogEntryForUser(user);
		request.setAttribute("blogEntries", entries);

		request.getRequestDispatcher("/WEB-INF/pages/Edit.jsp").forward(request, response);
	}

	private void newMethod(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!permits) {
			request.getSession().setAttribute("error", "Forbidden");
			request.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(request, response);
			return;
		}

		if (request.getParameter("title") != null) {
			addNewBlog(request, response);
			return;
		}

		BlogEntry blogEntry = new BlogEntry();
		BlogEntryForm form = new BlogEntryForm();
		form.popuniIzRecorda(blogEntry);

		request.setAttribute("zapis", form);

		request.getRequestDispatcher("/WEB-INF/pages/New.jsp").forward(request, response);
	}

	private void addNewBlog(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		BlogEntryForm form = new BlogEntryForm();
		form.popuniIzHttpRequesta(request);
		form.validateEntry();

		if (form.hasErrors()) {
			request.setAttribute("zapis", form);
			request.getRequestDispatcher("/WEB-INF/pages/New.jsp").forward(request, response);
			return;
		}

		BlogEntry entry = new BlogEntry();
		form.popuniURecord(entry);

		DAO dao = DAOProvider.getDAO();

		String nick = (String) request.getSession().getAttribute("current.user.nick");
		BlogUser user = dao.getUser(nick);

		entry.setCreator(user);
		entry.setCreatedAt(new Date());

		dao.persistEntry(entry);
		form.setId(entry.getId().toString());

		response.sendRedirect(request.getServletContext().getContextPath() + "/servleti/author/" + nick);
	}
	
	private void addComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BlogCommentForm form = new BlogCommentForm();
		form.popuniIzHttpRequesta(request);
		form.validateComment();
		System.out.println(form);
		request.setAttribute("commentForm", form);
		
		if (form.hasErrors()) {
			request.getRequestDispatcher("/WEB-INF/pages/BlogEntries.jsp").forward(request, response);
			return;
		}
		
		DAO dao = DAOProvider.getDAO();
		
		BlogComment blogComment = new BlogComment();
		blogComment.setMessage(form.getMessage());
		blogComment.setPostedOn(new Date());
		blogComment.setUsersEMail(form.getUsersEMail());
		blogComment.setBlogEntry((BlogEntry) request.getAttribute("entryComment"));
			
		dao.persistComment(blogComment);
		request.getRequestDispatcher("/WEB-INF/pages/BlogEntries.jsp").forward(request, response);
	}

	private String getNick(String pathInfo) {
		int index = pathInfo.substring(1).indexOf("/");

		if (index == -1) {
			index = pathInfo.length();
		} else {
			index++;
		}

		String nick = pathInfo.substring(1, index);
		return nick;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
