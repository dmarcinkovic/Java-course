package hr.fer.zemris.java.hw06.shell.commands.parser;

/**
 * This class have three public method that creates three different types of
 * NameBuilder.
 * 
 * @author david
 *
 */
public class DefaultNameBuilders {

	/**
	 * Creates NameBuilder that gets String via constructor and writes it to
	 * StringBuilder.
	 * 
	 * @param t String to be written to given StringBuilder.
	 * @return NameBuilder.
	 */
	public static NameBuilder text(String t) {
		return (result, sb) -> sb.append(t);
	}

	/**
	 * Creates NameBuilder that gets index via constructor and writes group with
	 * that index to StringBuilder.
	 * 
	 * @param index Index of the group.
	 * @return NameBuilder.
	 */
	public static NameBuilder group(int index) {
		return (result, sb) -> {
			sb.append(result.group(index));
		};
	}

	/**
	 * Creates NameBuilder that gets index, padding character and minimum width via
	 * constructor and writes group with that index and with minimum width to
	 * StringBuilder.
	 * 
	 * @param index    Index of group.
	 * @param padding  Padding character.
	 * @param minWidth Minimum width.
	 * @return NameBuilder.
	 */
	public static NameBuilder group(int index, char padding, int minWidth) {
		return (result, sb) -> {
			String group = result.group(index);
			if (group.length() < minWidth) {
				sb.append(String.valueOf(padding).repeat(minWidth - group.length()));
			}
			sb.append(group);
		};
	}
}
