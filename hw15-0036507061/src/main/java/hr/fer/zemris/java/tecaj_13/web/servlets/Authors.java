package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.ArrayList;
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
	
	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Boolean flag used to check permits to this servlet.
	 */
	private boolean permits = false;

	/**
	 * Blog entry.
	 */
	private BlogEntry commentEntry;

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

	/**
	 * Method called when path does not have anything after the
	 * /servleti/author/userName. In that case if logged user is 'userName' than
	 * list of all blog entries is listed and link to adding and editing blog
	 * entries is presented. Other no link will be presented.
	 * 
	 * @param request  HttpServletRequest.
	 * @param response HttpServletResponse.
	 * @throws ServletException If error occur.
	 * @throws IOException      If error occur.
	 */
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

	/**
	 * Method called when path is /servlet/author/username/id. Check if 'username'
	 * is indeed owner of blog entry with 'id'. It lists all comments posted to this
	 * blog entry and allows user to add new comments. Also, if logged user is
	 * 'username' then link to editing blog entry is presented.
	 * 
	 * @param request  HttpServletRequest.
	 * @param response HttpServletResponse.
	 * @throws ServletException If error occur.
	 * @throws IOException      If error occur.
	 */
	private void blogEntryPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		Long id = Long.parseLong(pathInfo.substring(pathInfo.lastIndexOf('/') + 1));

		String nick = getNick(pathInfo);
		DAO dao = DAOProvider.getDAO();

		BlogEntry entry = dao.getBlogEntryForID(id);
		commentEntry = entry;

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

		request.getRequestDispatcher("/WEB-INF/pages/BlogEntries.jsp").forward(request, response);
	}

	/**
	 * Method called when url link is /servleti/author/username/edit.This method
	 * checks permits and calls jsp file that shows form for editing blog entries.
	 * 
	 * @param request  HttpServletRequest.
	 * @param response HttpServletResponse.
	 * @throws ServletException If error occur.
	 * @throws IOException      If error occur.
	 */
	private void editMethod(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!permits) {
			request.getSession().setAttribute("error", "Forbidden");
			request.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(request, response);
			return;
		}

		if (request.getParameter("title") != null) {
			editBlog(request, response);
			return;
		}

		String pathInfo = request.getPathInfo();
		String nick = getNick(pathInfo);

		DAO dao = DAOProvider.getDAO();
		BlogUser user = dao.getUser(nick);

		if (user == null) {
			request.getSession().setAttribute("error", "User does not exists.");
			request.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(request, response);
			return;
		}

		if (request.getParameter("blogId") != null) {
			callEditBlogEntry(request, response);
			return;
		}

		List<BlogEntryForm> entries = getList(dao.getBlogEntryForUser(user));

		request.setAttribute("blogEntries", entries);

		request.getRequestDispatcher("/WEB-INF/pages/Edit.jsp").forward(request, response);
	}

	/**
	 * Method that checks if editing blog entry is valid. I.e., this method check if
	 * new title and new text are empty.
	 * 
	 * @param request  HttpServletRequest.
	 * @param response HttpServletResponse.
	 * @throws ServletException If error occur.
	 * @throws IOException      If error occur.
	 */
	private void editBlog(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BlogEntryForm form = new BlogEntryForm();
		form.popuniIzHttpRequesta(request);
		form.validateEntry();

		if (form.hasErrors()) {
			request.setAttribute("form", form);
			request.getRequestDispatcher("/WEB-INF/pages/EditBlogEntry.jsp").forward(request, response);
			return;
		}

		Long id = Long.parseLong(request.getParameter("ID"));

		DAO dao = DAOProvider.getDAO();
		BlogEntry entry = dao.getBlogEntryForID(id);
		entry.setTitle(form.getTitle());
		entry.setText(form.getText());

		dao.persistEntry(entry);

		String nick = getNick(request.getPathInfo());
		response.sendRedirect(request.getServletContext().getContextPath() + "/servleti/author/" + nick);
	}

	/**
	 * Method used to call EditBlogEntry.jsp file.
	 * 
	 * @param request  HttpServletRequest.
	 * @param response HttpServletResponse.
	 * @throws ServletException If error occur.
	 * @throws IOException      If error occur.
	 */
	private void callEditBlogEntry(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String blogId = request.getParameter("blogId");

		DAO dao = DAOProvider.getDAO();

		BlogEntry be = dao.getBlogEntryForID(Long.parseLong(blogId));
		BlogEntryForm form = new BlogEntryForm();
		form.popuniIzRecorda(be);
		form.setId(be.getId().toString());

		request.setAttribute("form", form);

		request.getRequestDispatcher("/WEB-INF/pages/EditBlogEntry.jsp").forward(request, response);
	}

	/**
	 * Method called when url is /servleti/author/username/new. This is used to add
	 * new entries. It checks if logged user is 'username', if not, then error is
	 * presented.
	 * 
	 * @param request  HttpServletRequest.
	 * @param response HttpServletResponse.
	 * @throws ServletException If error occur.
	 * @throws IOException      If error occur.
	 */
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

	/**
	 * Method used to validate the input from user. It check if blog entry title and
	 * blog entry text are empty. If so, it calls New.jsp again and shows user that
	 * title and text fields are empty.
	 * 
	 * @param request  HttpServletRequest.
	 * @param response HttpServletResponse.
	 * @throws IOException      If error occur.
	 * @throws ServletException If error occur.
	 */
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

	/**
	 * Method to add comment to blog and checks if comment is empty or not. If
	 * empty, then error is presented.
	 * 
	 * @param request  HttpServletRequest.
	 * @param response HttpServletResponse.
	 * @throws ServletException If error occur.
	 * @throws IOException      If error occur.
	 */
	private void addComment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BlogCommentForm form = new BlogCommentForm();
		form.popuniIzHttpRequesta(request);
		form.validateComment();
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
		blogComment.setBlogEntry(commentEntry);

		dao.persistComment(blogComment);
		form.setId(blogComment.getId().toString());
		response.sendRedirect(request.getRequestURL().toString());
	}

	/**
	 * Returns nick presented in url.
	 * 
	 * @param pathInfo The url after /servlet/author.
	 * @return Nick presented in url.
	 */
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
	 * Converts list of BlogEntry objects to list of BlogEntryForm objects.
	 * 
	 * @param list List of BlogEntry objects.
	 * @return List of converted BlogEntryForm object.
	 */
	private List<BlogEntryForm> getList(List<BlogEntry> list) {
		List<BlogEntryForm> result = new ArrayList<>();

		for (BlogEntry blogEntry : list) {
			BlogEntryForm form = new BlogEntryForm();
			form.popuniIzRecorda(blogEntry);
			form.setId(blogEntry.getId().toString());
			result.add(form);
		}

		return result;
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
