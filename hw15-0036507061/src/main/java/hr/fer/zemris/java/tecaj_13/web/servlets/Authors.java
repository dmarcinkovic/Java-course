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
		
		if (pathInfo.matches("/author/.*/new")) {
			newMethod(request, response);
		}else if (pathInfo.matches("/author/.*/edit")) {
			editMethod(request, response);
		}else if (pathInfo.matches("/author/.*/\\d+")) {
			blogEntryPage(request, response);
		}
		
		int index = pathInfo.substring(1).indexOf("/");
		if (index == -1) {
			index = pathInfo.length();
		}
		
		String nick = pathInfo.substring(1, index);
		String currentUserNick = (String)request.getSession().getAttribute("current.user.nick");
		
		if (nick.equals(currentUserNick)) {
			request.getSession().setAttribute("permits", true);
		}else {
			request.getSession().setAttribute("permits", false);
		}
		
		request.getSession().setAttribute("nick", nick);
		
		DAO dao = DAOProvider.getDAO();
		BlogUser user = dao.getUser(nick);
		
		if (user == null) {
			/// TODO ispisi gresku u greska.jsp
			return;
		}
		
		request.getSession().setAttribute("nick_id", user.getId().toString());
		
		List<BlogEntry> entries = dao.getBlogEntryForUser(user);
		
		request.setAttribute("blogEntries", entries);
		request.getRequestDispatcher("/WEB-INF/pages/Authors.jsp").forward(request, response);
	}

	private void blogEntryPage(HttpServletRequest request, HttpServletResponse response) {
		
	}

	private void editMethod(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void newMethod(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
