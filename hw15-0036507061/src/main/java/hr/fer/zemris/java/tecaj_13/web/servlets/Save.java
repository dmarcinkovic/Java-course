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
 * Servlet koji obavlja akciju /save. Pogledajte slideove za opis: tamo odgovara
 * akciji /update.
 * 
 * @author marcupic
 */
@WebServlet("/servleti/save")
public class Save extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		obradi(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		obradi(req, resp);
	}

	protected void obradi(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		BlogUserForm form = new BlogUserForm();

		form.popuniIzHttpRequesta(req);
		form.validateRegistration();

		if (form.imaPogresaka()) {
			req.setAttribute("zapis", form);
			req.getRequestDispatcher("/WEB-INF/pages/SingIn.jsp").forward(req, resp);
			return;
		}

		BlogUser blogUser = new BlogUser();
		form.popuniURecord(blogUser);

		DAO dao = DAOProvider.getDAO();

		BlogUser user = dao.getUser(form.getNick());
		if (user != null) {
			req.setAttribute("zapis", form);
			form.setGreske("nickAlreadyExists", "User with nick " + form.getNick() + " already exists.");
			req.getRequestDispatcher("/WEB-INF/pages/SingIn.jsp").forward(req, resp);
			return;
		}

		dao.persistUser(blogUser);
		form.setId(blogUser.getId().toString());

		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
}
