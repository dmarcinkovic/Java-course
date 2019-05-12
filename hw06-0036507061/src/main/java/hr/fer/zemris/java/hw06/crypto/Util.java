package hr.fer.zemris.java.hw06.crypto;

/**
 * Class that implements two static methods for converting bytes to hexadecimal
 * and vice versa.
 * 
 * @author david
 *
 */
public class Util {

	/**
	 * Method that converts String to hexadecimal bytes.
	 * 
	 * @param keyText Text to be converted.
	 * @return hexadecimal bytes.
	 * @throws IllegalArgumentException if keyText is odd-sized or has invalid
	 *                                  characters.
	 * @throws NullPointerException     if keyText is null.
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText == null) {
			throw new NullPointerException();
		}

		if (keyText.length() % 2 == 1) {
			throw new IllegalArgumentException();
		}

		byte[] result = new byte[keyText.length() / 2];
		for (int i = 0; i < keyText.length(); i += 2) {
			try {
				result[i / 2] = (byte) Integer.parseInt(keyText.substring(i, i + 2), 16);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException();
			}
		}

		return result;
	}

	/**
	 * Method that converts hexadecimal byte array to String.
	 * 
	 * @param byteArray byte array to be converted.
	 * @return Hexadecimal representation of given byte array.
	 * @throws NullPointerException if byteArray is null.
	 */
	public static String bytetohex(byte[] byteArray) {
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

}
