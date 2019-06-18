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
import hr.fer.zemris.java.tecaj_13.forms.BlogUserForm;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet implementation class Main
 */
@WebServlet("/servleti/main")
public class Main extends HttpServlet {
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BlogUser blogUser = new BlogUser();
		BlogUserForm form = new BlogUserForm();
		form.popuniIzRecorda(blogUser);
		
		request.setAttribute("zapis", form);
		
		DAO dao = DAOProvider.getDAO();
		List<BlogUser> users = dao.getUsers();
		
		request.setAttribute("zapisi", users);
		
		request.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(request, response);;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
