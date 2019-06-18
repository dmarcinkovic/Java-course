package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet implementation class Authors
 */
@WebServlet("/servleti/author/*")
public class Authors extends HttpServlet { /// TODO check if user has permitions 
	private static final long serialVersionUID = 1L;
	private boolean permits = false;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authors() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		
		if (pathInfo == null) {
			return;
		}
		
		if (!pathInfo.matches("/.*")) {
			return;
		}
		
		String nick = getNick(pathInfo);
		String currentUser = (String)request.getSession().getAttribute("current.user.nick");
		
		if (nick.equals(currentUser)) {
			permits = true;
		}else {
			permits = false;
		}
		
		request.setAttribute("permits", permits);
		if (pathInfo.matches("/.*/new")) {
			System.out.println("New");
			newMethod(request, response);
			request.getRequestDispatcher("/WEB-INF/pages/New.jsp");
			return;
		}else if (pathInfo.matches("/.*/edit")) {
			System.out.println("Edit");
			editMethod(request, response);
			request.getRequestDispatcher("/WEB-INF/pages/Edit.jsp");
			return;
		}else if (pathInfo.matches("/.*/\\d+")) {
			System.out.println("With id");
			blogEntryPage(request, response);
			request.getRequestDispatcher("/WEB-INF/pages/BlogEntry.jsp");
			return;
		}else if (pathInfo.matches("/\\w+")) {
			System.out.println("Authro");
			author(request, response);
			return;
		}
		
			// TODO error
	}

	private void author(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		String nick = pathInfo.substring(1);

		request.getSession().setAttribute("nick", nick);
		
		DAO dao = DAOProvider.getDAO();
		BlogUser user = dao.getUser(nick);
		
		if (user == null) {
			/// TODO ispisi gresku u greska.jsp
			return;
		}
		
		List<BlogEntry> entries = dao.getBlogEntryForUser(user);
		
		request.setAttribute("blogEntries", entries);
		request.getRequestDispatcher("/WEB-INF/pages/Authors.jsp").forward(request, response);
	}

	private void blogEntryPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!permits) {
			// TODO write error message
			return;
		}
		
		String pathInfo = request.getPathInfo();
		Long id = Long.parseLong(pathInfo.substring(pathInfo.lastIndexOf('/')+1));
		
		DAO dao = DAOProvider.getDAO();
		
		BlogEntry entry = dao.getBlogEntry(id);
		request.setAttribute("entry", entry);
		String result = null; 
		request.setAttribute("result", result);
		
		// TODO should send to servlet that lists blog entries
		request.getRequestDispatcher("/WEB-INF/pages/Authors.jsp").forward(request, response);
	}

	private void editMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		String nick = getNick(pathInfo); 
		
		DAO dao = DAOProvider.getDAO();
		BlogUser user = dao.getUser(nick);
		
		if (user == null) {
			// TODO writer error message
			return;
		}
		
		List<BlogEntry> entries = dao.getBlogEntryForUser(user);
		request.setAttribute("blogEntries", entries);	
		
		request.getRequestDispatcher("/WEB-INF/pages/Edit.jsp").forward(request, response);
	}

	private void newMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!permits) {
			// TODO do some error
			return;
		}
		request.getRequestDispatcher("/WEB-INF/pages/New.jsp").forward(request, response);
	}
	
	private String getNick(String pathInfo) {
		int index = pathInfo.substring(1).indexOf('/');
		
		if (index == -1) {
			index = pathInfo.length();
		}
		
		String nick = pathInfo.substring(1, index);
		return nick;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
