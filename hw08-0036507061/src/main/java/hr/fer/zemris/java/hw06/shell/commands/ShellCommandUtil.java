package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that is utility class for shell command. It is used to extract
 * arguments that are paths.
 * 
 * @author david
 *
 */
public abstract class ShellCommandUtil {

	/**
	 * Represents the current state while parsing arguments. It can be TEXT and
	 * STRING. STRING is everything inside the quotes. It is used to support paths
	 * that contain spaces and special characters.
	 * 
	 * @author david
	 *
	 */
	private enum State {
		TEXT, STRING
	}

	/**
	 * Method that returns arguments of the shell.
	 * 
	 * @param arguments Arguments as one string.
	 * @return Splitted arguments.
	 */
	protected String[] getArguments(String arguments) {
		List<String> list = getListOfStrings(arguments);

		if (list == null) {
			return null;
		}

		return convertListToArray(list);
	}

	/**
	 * Method that extracts arguments from string and returns those argumetns as
	 * list of Strings.
	 * 
	 * @param arguments Arguments to be splitted.
	 * @return List of Strings.
	 */
	private List<String> getListOfStrings(String arguments) {
		List<String> list = new ArrayList<>();

		char[] data = arguments.toCharArray();
		State state = State.TEXT;

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			if (data[i] == '"') {
				state = toggleState(state);
			} else if (isEscapingSequence(data, i) && state.equals(State.STRING)) {
				sb.append(data[i + 1]);
				i++;
			} else if (Character.isWhitespace(data[i]) && state.equals(State.TEXT)) {
				if (!sb.toString().isEmpty()) {
					list.add(sb.toString());
					sb = new StringBuilder();
				}
			} else {
				sb.append(data[i]);
			}
		}

		if (state.equals(State.STRING)) {
			return null;
		}

		if (!sb.toString().isEmpty()) {
			list.add(sb.toString());
		}

		return list;
	}

	/**
	 * Method to toggle state. If state was State.STRING this method will return
	 * State.TEXT and vice versa.
	 * 
	 * @param state Current state.
	 * @return Toggled state.
	 */
	private State toggleState(State state) {
		if (state.equals(State.STRING)) {
			return State.TEXT;
		}
		return State.STRING;
	}

	/**
	 * Method that converts list of strings to array of strings.
	 * 
	 * @param list List to be converted to array.
	 * @return Array of Strings.
	 */
	private String[] convertListToArray(List<String> list) {
		String[] allArguments = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			allArguments[i] = list.get(i);
		}

		return allArguments;
	}

	/**
	 * Method that checks if current character is start of the escaping sequence.
	 * Escaping sequence if one that starts with '\' after which follows '"' or '\'.
	 * Any other sequence that starts with '\' will be treated as it is.
	 * 
	 * @param array Character array.
	 * @param i     Index of the current character.
	 * @return True if current character is start of the escaping sequence.
	 */
	private boolean isEscapingSequence(char[] array, int i) {
		return array[i] == '\\' && i + 1 < array.length && (array[i + 1] == '\\' || array[i + 1] == '\"');
	}
}
