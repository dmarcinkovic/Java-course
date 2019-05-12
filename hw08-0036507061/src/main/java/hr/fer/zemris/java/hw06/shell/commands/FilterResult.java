package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Stores file and informations about groups found in regex pattern.
 * 
 * @author david
 *
 */
public class FilterResult {
	/**
	 * File.
	 */
	private Path file;
	
	/**
	 * All groups.
	 */
	List<String> groups;

	/**
	 * Constructor to initialize private fields.
	 * 
	 * @param file    Filename.
	 * @param pattern Patter to match.
	 */
	public FilterResult(Path file, String pattern) {
		this.file = file;
		
		groups = new ArrayList<>();
		
		Pattern p = null;
		try {
			p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE & Pattern.UNICODE_CASE);
		} catch (PatternSyntaxException e) {

		}
		Matcher m = p.matcher(file.getFileName().toString());
		
		if (m.matches()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				String group = m.group(i);
				groups.add(group);
			}
		}
	}

	/**
	 * Returns the filename.
	 * 
	 * @return Filename.
	 */
	public String toString() {
		return file.getFileName().toString();
	}

	/**
	 * Returns the number of groups.
	 * 
	 * @return Number of groups.
	 */
	public int numberOfGroups() {
		return groups.size() - 1;
	}

	/**
	 * Returns group with given index.
	 * 
	 * @param index Index of group.
	 * @return String representing group with the given index.
	 * @throws IllegalArgumentException if index is out of range.
	 */
	public String group(int index) {
		if (index < 0 || index > numberOfGroups()) {
			throw new IllegalArgumentException();
		}
		
		return groups.get(index);
	}

}
