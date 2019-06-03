package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet used to get the .xls file. This page is available with /powers url.
 * In .xls file in each sheet will be presented in first column all numbers from
 * a to b and in second column the i-th power of number from first column. 'a',
 * 'b' and 'n' must be provided as parameter. 'a' and 'b' must be in range -100,
 * 100 and 'n' must be in range 1 and 5. If those ranges are not obeyed error
 * message will appear on the page.
 * 
 * @author David
 *
 */
public class Excel extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int a = getA(req);
		int b = getB(req);
		int n = getN(req);

		checkParameters(a, b, n, req, resp);

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
		try {
			HSSFWorkbook hwb = new HSSFWorkbook();

			List<HSSFSheet> sheets = new ArrayList<>();

			for (int i = 1; i <= n; i++) {
				HSSFSheet sheet = hwb.createSheet("New sheet" + String.valueOf(i));
				sheets.add(sheet);

				for (int j = a; j <= b; j++) {
					HSSFRow rowhead = sheet.createRow((short) j - a);

					String number = String.valueOf(j);
					String power = String.valueOf((int) Math.pow(j, i));
					rowhead.createCell((short) 0).setCellValue(number);
					rowhead.createCell((short) 1).setCellValue(power);
				}
			}
			hwb.write(resp.getOutputStream());
			hwb.close();
		} catch (Exception ignorable) {
		}
	}

	/**
	 * Method to check if parameter provided are correct. I.e. in valid range.
	 * 
	 * @param a    Parameter a.
	 * @param b    Parameter b.
	 * @param n    Parameter n.
	 * @param req  Request.
	 * @param resp Response
	 * @throws ServletException If error occurs.
	 * @throws IOException      If error occurs.
	 */
	private void checkParameters(int a, int b, int n, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (a < -100 || a > 100) {
			req.getSession().setAttribute("error", "Parameter 'a' must be in range: [-100, 100]");
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
		} else if (b < -100 || b > 100) {
			req.getSession().setAttribute("error", "Parameter 'b' must be in range: [-100, 100]");
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
		} else if (n < 1 || n > 5) {
			req.getSession().setAttribute("error", "Parameter 'n' must be in range: [1, 5]");
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
		}
	}

	/**
	 * Returns integer value of parameter 'a'.
	 * 
	 * @param req Request.
	 * @return Integer value of parameter 'a'.
	 */
	private int getA(HttpServletRequest req) {
		String a = req.getParameter("a");

		int varA = -200;
		try {
			varA = Integer.parseInt(a);
		} catch (NumberFormatException e) {
		}

		return varA;
	}

	/**
	 * Returns integer value of parameter 'a'.
	 * 
	 * @param req Request.
	 * @return Integer value of parameter 'b'.
	 */
	private int getB(HttpServletRequest req) {
		String b = req.getParameter("b");

		int varB = -200;
		try {
			varB = Integer.parseInt(b);
		} catch (NumberFormatException e) {
		}

		return varB;
	}

	/**
	 * Returns integer value of parameter 'n'.
	 * 
	 * @param req Request.
	 * @return Integer value of parameter 'n'.
	 */
	private int getN(HttpServletRequest req) {
		String n = req.getParameter("n");

		int varN = -200;
		try {
			varN = Integer.parseInt(n);
		} catch (NumberFormatException e) {
		}

		return varN;
	}
}
