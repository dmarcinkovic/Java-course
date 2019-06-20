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
 * Servlet implementation class Login
 */
@WebServlet("/servleti/login")
public class Login extends HttpServlet {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		obradi(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		obradi(request, response);
	}

	/**
	 * Method to process the request.
	 * 
	 * @param req  HttpServletReques.
	 * @param resp HttpServletResponse.
	 * @throws ServletException When error occurs.
	 * @throws IOException      When error occurs.
	 */
	protected void obradi(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		DAO dao = DAOProvider.getDAO();
		List<BlogUser> users = dao.getUsers();

		req.setAttribute("zapisi", users);

		BlogUserForm form = new BlogUserForm();
		form.popuniIzHttpRequesta(req);

		form.validateLogin();

		if (form.imaPogresaka()) {
			form.setPasswordHash("");
			req.setAttribute("zapis", form);
			req.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(req, resp);
			return;
		}

		BlogUser user = dao.getUser(form.getNick());

		if (user != null && user.getPasswordHash().equals(form.getPasswordHash())) {
			req.getSession().setAttribute("current.user.id", user.getId().toString());
			req.getSession().setAttribute("current.user.fn", user.getFirstName());
			req.getSession().setAttribute("current.user.ln", user.getLastName());
			req.getSession().setAttribute("current.user.nick", user.getNick());
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + user.getNick());
		} else {
			form.setPasswordHash("");
			req.setAttribute("zapis", form);
			form.setGreske("user", "Wrong nick name or password.");
			req.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(req, resp);
		}
	}
}
