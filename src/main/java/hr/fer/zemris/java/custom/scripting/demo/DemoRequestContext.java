package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Demo example to check if class RequestContext works. This program creates
 * three .txt files named: primjer1, primjer2, primjer3.
 * 
 * @author David
 *
 */
public class DemoRequestContext {

	/**
	 * Method invoked when running the program. This method creates three .txt files
	 * called : primjer1, primjer2, primjer3.
	 * 
	 * @param args Argument provided via command line. In this example they are not
	 *             used.
	 * @throws IOException If creating or writing to output stream failed.
	 */
	public static void main(String[] args) throws IOException {
		demo1("primjer1.txt", "ISO-8859-2");
		demo1("primjer2.txt", "UTF-8");
		demo2("primjer3.txt", "UTF-8");
	}

	/**
	 * Demo example 1. This private method creates one .txt file called primjer1. It
	 * sets mime type to text/plain and sets status code to 205. encoding.
	 * 
	 * @param filePath File path.
	 * @param encoding Encoding.
	 * @throws IOException If writing or opening output stream fails
	 */
	private static void demo1(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");

		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}

	/**
	 * Demo example 2. This private method creates one .txt file called primjer2.
	 * This example is similar to demo1 example, but it uses different encoding
	 * which result is different length of output files.
	 * 
	 * @param filePath File path.
	 * @param encoding Encoding. 
	 * @throws IOException If writing or opening output stream fails.
	 */
	private static void demo2(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(new RCCookie("korisnik", "perica", 3600, "127.0.0.1", "/"));
		rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/"));

		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
}