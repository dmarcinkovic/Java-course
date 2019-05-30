package hr.fer.zemris.java.webserver.workers;

import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");

		Set<String> names = context.getParameterNames();

		context.write("<html>");
		context.write("<head>\r\n" + "<style>\r\n" + "table, th, td {\r\n" + "  border: 1px solid black;\r\n"
				+ "  border-collapse: collapse;\r\n" + "}\r\n" + "th, td {\r\n" + "  padding: 5px;\r\n"
				+ "  text-align: left;    \r\n" + "}\r\n" + "</style>\r\n" + "</head>");

		context.write("<body>");

		context.write(" <table style=\"width:100%\">");
		context.write("<thead>");
		context.write(" <tr><th>Key</th><th>Value</th></tr>");
		context.write(" </thead><tbody>");

		for (String name : names) {
			String value = context.getParameter(name);
			context.write("<tr><td>");
			context.write(name);
			context.write("</td><td>");
			context.write(value);
			context.write("</td></tr>");
		}

		context.write("</tbody></table></body></html>");
	}

}
