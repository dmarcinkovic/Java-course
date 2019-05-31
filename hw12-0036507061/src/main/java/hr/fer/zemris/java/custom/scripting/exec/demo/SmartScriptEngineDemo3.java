package hr.fer.zemris.java.custom.scripting.exec.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Example program to check if SmartScriptEngine program works fine. This
 * program open file from disk and reads source code of SmartScript language.
 * Then it parses given source code and executes appropriate commands.
 * 
 * @author David
 *
 */
public class SmartScriptEngineDemo3 {

	/**
	 * Method invoked when running the program. This method read content of file in
	 * which source code is presented. After that given source code is parsed and
	 * executed.
	 * 
	 * @param args Arguments provided via command line. In this program they are not
	 *             used.
	 */
	public static void main(String[] args) {
		String documentBody = readFromDisk("webroot/scripts/brojPoziva.smscr");
		
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		
		persistentParameters.put("brojPoziva", "3");
		
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters,
		cookies);
		
		new SmartScriptEngine(
		new SmartScriptParser(documentBody).getDocumentNode(), rc
		).execute();
		System.out.println("Vrijednost u mapi: "+rc.getPersistentParameter("brojPoziva"));

	}

	/**
	 * Method used to read content of file and convert it to one String.
	 * 
	 * @param path Path of file.
	 * @return String that is obtained by concatenating all lines of input file.
	 */
	private static String readFromDisk(String path) {
		Path file = Paths.get(path);

		List<String> lines = null;
		try {
			lines = Files.readAllLines(file);
		} catch (IOException e) {
			System.err.println("Cannot read from file");
			System.exit(1);
		}

		StringBuilder sb = new StringBuilder();

		for (String s : lines) {
			sb.append(s).append("\n");
		}

		return sb.toString();
	}
}
