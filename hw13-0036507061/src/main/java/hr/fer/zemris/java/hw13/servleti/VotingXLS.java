package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet that is used to create .xls file in which voting results are written
 * in table. In column 0 there are presented names of the bands. In column 1 are
 * presented scores of the bands. When link to .xls file is clicked, .xls file
 * will be downloaded.
 * 
 * @author David
 *
 */
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
		int numberOfBands = (int) req.getSession().getAttribute("numberOfBands");

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
		try {
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("Voting results");

			for (int i = 1; i <= numberOfBands; i++) {
				String bandName = (String) req.getSession().getAttribute("id" + String.valueOf(i));

				HSSFRow rowhead = sheet.createRow((short) i - 1);

				rowhead.createCell((short) 0).setCellValue(bandName);

				int score = (int) req.getSession().getAttribute(String.valueOf(i));
				rowhead.createCell((short) 1).setCellValue(String.valueOf(score));
			}

			hwb.write(resp.getOutputStream());
			hwb.close();
		} catch (Exception ignorable) {
		}
	}

}
