package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents hexdump command. This command converts file given as an argument
 * to hex record. This command is similar to xxd command in linux.
 * 
 * @author david
 *
 */
public class HexdumpShellCommand extends ShellCommandUtil implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] allArguments = getArguments(arguments);

		if (allArguments.length != 1 || arguments.trim().isEmpty()) {
			return writeErrorMessage(env, 0);
		}

		return hexdump(env, allArguments[0]);
	}

	/**
	 * Converts content of the file to hexadecimal format.
	 * 
	 * @param env      Shell environment.
	 * @param filename File which content will be converted to hexadecimal format.
	 * @return ShellStatus.
	 */
	private ShellStatus hexdump(Environment env, String filename) {
		Path file = null;

		try {
			file = Paths.get(filename);
			
			file = env.getCurrentDirectory().resolve(file);
		} catch (InvalidPathException e) {
			return writeErrorMessage(env, 1);
		}

		if (Files.isDirectory(file)) {
			return writeErrorMessage(env, 2);
		}

		return readFileAndConvertToHex(env, file);
	}

	/**
	 * Method that reads the file and convert the content of this file to
	 * hexadecimal format.
	 * 
	 * @param env  Shell environment.
	 * @param file File to read from.
	 * @return ShellStatus.
	 */
	private ShellStatus readFileAndConvertToHex(Environment env, Path file) {
		try (InputStream is = Files.newInputStream(file)) {

			byte[] array;

			array = is.readNBytes(16);
			int offset = 0;

			while (array.length != 0) {
				if (printInNiceFormat(env, array, offset).equals(ShellStatus.TERMINATE)) {
					return ShellStatus.TERMINATE;
				}

				offset += 16;
				array = is.readNBytes(16);
			}

		} catch (IOException e) {
			return writeErrorMessage(env, 3);
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Method that prints hexadecimal representation of the file in nice format.
	 * 
	 * @param env    Shell environment.
	 * @param array  byte array.
	 * @param offset Current position in the file.
	 * @return ShellStatus.
	 */
	private ShellStatus printInNiceFormat(Environment env, byte[] array, int offset) {
		try {
			env.write(String.format("%08X:", offset));

			String bytes = bytetohex(array);
			if (printBytes(env, bytes).equals(ShellStatus.TERMINATE)) {
				return ShellStatus.TERMINATE;
			}

			env.write(" ");
			for (int i = 0; i < array.length; i++) {
				if (array[i] < 32 || array[i] > 127) {
					array[i] = '.';
				}
				String str = String.valueOf((char) array[i]);
				env.write(str);
			}

			env.writeln("");

		} catch (ShellIOException e) {
			return ShellStatus.TERMINATE;
		}
		return ShellStatus.CONTINUE;
	}
	
	
	/**
	 * Method that converts hexadecimal byte array to String.
	 * 
	 * @param byteArray byte array to be converted.
	 * @return Hexadecimal representation of given byte array.
	 * @throws NullPointerException if byteArray is null.
	 */
	private String bytetohex(byte[] byteArray) {
		if (byteArray == null) {
			throw new NullPointerException();
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < byteArray.length; i++) {
			String hex = Integer.toHexString(0xff & byteArray[i]);

			if (hex.length() == 1) {
				sb.append("0");
			}
			sb.append(hex);
		}

		return sb.toString();
	}

	/**
	 * Method that prints bytes in nice format.
	 * 
	 * @param env   Shell environment.
	 * @param bytes String representing bytes to be printed.
	 * @return ShellStatus.
	 */
	private ShellStatus printBytes(Environment env, String bytes) {
		try {
			char[] array = bytes.toCharArray();
			int charactersPrinted = 0;

			for (int i = 0, n = 16 > array.length ? array.length : 16; i < n; i += 2) {
				env.write(" " + String.valueOf(array[i]).toUpperCase() + String.valueOf(array[i + 1]).toUpperCase());
				charactersPrinted += 3;
			}

			if (array.length < 16) {
				env.write(" ".repeat(24 - charactersPrinted) + "|");
			} else {
				env.write("|");
			}

			charactersPrinted = 0;
			for (int i = 16; i < array.length; i += 2) {
				env.write(String.valueOf(array[i]).toUpperCase() + String.valueOf(array[i + 1]).toUpperCase() + " ");
				charactersPrinted += 3;
			}

			if (array.length < 32) {
				env.write(" ".repeat(24 - charactersPrinted) + "|");
			} else {
				env.write("|");
			}

		} catch (ShellIOException e) {
			return ShellStatus.TERMINATE;
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Prints error message to the console.
	 * 
	 * @param env       Shell environment.
	 * @param errorCode error code.
	 * @return ShellStatus. This method will return ShellStatus.CONTINUE if writing
	 *         to the shell executes successfully, otherwise returns
	 *         ShellStatus.TERMINATE.
	 */
	private ShellStatus writeErrorMessage(Environment env, int errorCode) {
		try {
			switch (errorCode) {
			case 0:
				env.writeln("Wrong number of arguments.");
				break;
			case 1:
				env.writeln("Could not make hexdump.");
				break;
			case 2:
				env.writeln("Given path is directory.");
				break;
			case 3:
				env.writeln("Could read from file.");
				break;
			}
		} catch (ShellIOException e) {
			return ShellStatus.TERMINATE;
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "hexdump";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();

		commandDescription.add("Produces hex-output of the specified file.");
		commandDescription.add("Command expects only one argument : path to the ");
		commandDescription.add("file that will be converted to hex.");

		commandDescription = Collections.unmodifiableList(commandDescription);

		return commandDescription;
	}

}
