package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

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
 * Servlet implementation class Save.
 */
@WebServlet("/servleti/save")
public class Save extends HttpServlet {

	/**
	 * Default serial verion UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	/**
	 * Method to process the request.
	 * 
	 * @param req  HttpServletReques.
	 * @param resp HttpServletResponse.
	 * @throws ServletException When error occurs.
	 * @throws IOException      When error occurs.
	 */
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		BlogUserForm form = new BlogUserForm();

		form.fillFromHttpRequest(req);
		form.validateRegistration();

		if (form.hasErrors()) {
			form.setPasswordHash("");
			req.setAttribute("zapis", form);
			req.getRequestDispatcher("/WEB-INF/pages/SingIn.jsp").forward(req, resp);
			return;
		}

		BlogUser blogUser = new BlogUser();
		form.fillToRecord(blogUser);

		DAO dao = DAOProvider.getDAO();

		BlogUser user = dao.getUser(form.getNick());
		if (user != null) {
			form.setPasswordHash("");
			req.setAttribute("zapis", form);
			form.setErrors("nickAlreadyExists", "User with nick " + form.getNick() + " already exists.");
			req.getRequestDispatcher("/WEB-INF/pages/SingIn.jsp").forward(req, resp);
			return;
		}

		dao.persistUser(blogUser);
		form.setId(blogUser.getId().toString());

		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
}
