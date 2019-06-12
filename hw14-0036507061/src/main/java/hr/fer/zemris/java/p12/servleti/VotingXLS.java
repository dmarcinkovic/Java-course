package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.dao.sql.SQLConnectionProvider;

/**
 * Servlet that is used to create .xls file in which voting results are written
 * in table. In column 0 there are presented names of the bands. In column 1 are
 * presented scores of the bands. When link to .xls file is clicked, .xls file
 * will be download.
 * 
 * @author David
 *
 */
@WebServlet("/servleti/glasanje-xls")
public class VotingXLS extends HttpServlet {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String parameter = req.getParameter("id");
		
		if (parameter == null) {
			req.getSession().setAttribute("error", "Please provide one parameter.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		Long id = null;
		
		try {
			id = Long.parseLong(parameter);
		}catch(NumberFormatException e) {
			req.getSession().setAttribute("error", "Please provide one parameter that is number.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		java.sql.Connection dbConnection = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null; 
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
		
		try {
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("Voting results");
			
			pst = dbConnection.prepareStatement("select * from polloptions where pollID = ?");
			pst.setLong(1, id);
			ResultSet rset = pst.executeQuery();
			
			int index = 0;
			while (rset.next()) {
				Long score = rset.getLong("votesCount");
				String title = rset.getString("optionTitle");
				
				HSSFRow rowhead = sheet.createRow((short) index);

				rowhead.createCell((short) 0).setCellValue(title);
				rowhead.createCell((short) 1).setCellValue(String.valueOf(score));
				
				index++;
			}
			hwb.write(resp.getOutputStream());
			hwb.close();
		} catch (SQLException e) {
		}
	}

}
