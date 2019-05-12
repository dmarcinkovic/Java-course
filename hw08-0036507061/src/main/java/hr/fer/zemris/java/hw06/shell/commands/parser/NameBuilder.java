package hr.fer.zemris.java.hw06.shell.commands.parser;

import hr.fer.zemris.java.hw06.shell.commands.FilterResult;

/**
 * This interface is used to generate new name for the file. It writes new name
 * to given StringBuilder.
 * 
 * @author david
 *
 */
public interface NameBuilder {

	/**
	 * This method is used to generate new name for the file. It reads all necessary
	 * informations from given instance of result and creates new name for the file.
	 * 
	 * @param result Given instance of FilterResult. It is used to get current name
	 *               of the file and get groups from the mask.
	 * @param sb     StringBuilder object that stores new name.
	 */
	void execute(FilterResult result, StringBuilder sb);
}
