package hr.fer.zemris.java.hw06.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class that allows user to encrypt/decrypt given file using the AES
 * cryptoalgorithm and the 128-bit encryption key or calculate and check the
 * SHA-256 file digest.
 * 
 * @author david
 *
 */
public class Crypto {
	/**
	 * Constant representing one KB.
	 */
	private static final int KB = 1024;

	/**
	 * Method invoked when running the program. Method allow user to encrypt/decrypt
	 * given file using AES cryptoalgorithm and the 128-bit encryption key or
	 * calculate and check the SHA-256 file digest.
	 * 
	 * @param args Arguments provided via command line. First argument must be one
	 *             of this three words : checksha, encrypt, decrypt. If checksha
	 *             word is first argument, only one additional argument is expected:
	 *             file to check if has correct key. If decrypt or encrypt is
	 *             provided as first argument, then two additional arguments are
	 *             expected. First is source file and second is destination file.
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Wrong number of arguments.");
			return;
		}

		Scanner scanner = new Scanner(System.in);

		if (args[0].equals("checksha")) {

			if (args.length != 2) {
				System.out.println("Wrong number of arguments.");
				scanner.close();
				return;
			}

			checkSha(args[1], scanner);
		} else if (args[0].equals("encrypt") || args[0].equals("decrypt")) {

			if (args.length != 3) {
				System.out.println("Wrong number of arguments.");
				scanner.close();
				return;
			}

			encryptDecrypt(args[0], scanner, args[1], args[2]);
		} else {
			System.out.println("Unrecognized string in input.");
		}
	}

	/**
	 * Calculates and checks SHA-256 file digest.
	 * 
	 * @param filename File to check for.
	 * @param scanner  Scanner to get input from user.
	 */
	private static void checkSha(String filename, Scanner scanner) {
		System.out.println("Please provide expected sha-256 digest for " + filename + ":");
		System.out.print("> ");

		String input = scanner.next();

		String expected = input2(filename);

		if (input.trim().equals(expected)) {
			System.out.println("Digesting completed. Digest of " + filename + " matches expected digest.");
		} else {
			System.out.println("Digesting completed. Digest of " + filename
					+ " does not match the expected digest. Digest\n" + "was: " + expected);
		}
	}

	/**
	 * Method to get input from file.
	 * 
	 * @param filename File to get input from.
	 * @return String
	 */
	private static String input2(String filename) {
		try (InputStream inputStream = Files.newInputStream(Paths.get(filename))) {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");

			byte[] array;

			array = inputStream.readNBytes(4 * KB);
			while (array.length != 0) {
				if (array != null) {
					sha.update(array);
				}
				array = inputStream.readNBytes(4 * KB);
			}
			byte[] hash = sha.digest(array);

			return Util.bytetohex(hash).trim();
		} catch (IOException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Does encryption or decryption of the inputFile.
	 * 
	 * @param action     String representing the action. If action is "encrypt" this
	 *                   method will do encryption. If action is "decrypt" this
	 *                   method will do decryption.
	 * @param scanner    Scanner to get input from user.
	 * @param inputFile  Source file.
	 * @param outputFile Destination file.
	 */
	private static void encryptDecrypt(String action, Scanner scanner, String inputFile, String outputFile) {
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		System.out.print("> ");
		String keyText = scanner.next();

		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		System.out.print("> ");
		String ivText = scanner.next();

		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText.trim()), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText.trim()));
		Cipher cipher = null;

		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(action.equals("encrypt") ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException e1) {
			e1.printStackTrace();
		}

		input(cipher, inputFile, outputFile);
		System.out.println("Decryption completed. Generated file " + outputFile + " based on file " + inputFile + ".");
	}

	/**
	 * Method to get input from file.
	 * 
	 * @param cipher     Instance of Cipher object.
	 * @param inputFile  Source file.
	 * @param outputFile Destination file.
	 */
	private static void input(Cipher cipher, String inputFile, String outputFile) {
		Path output = Paths.get(outputFile);
		
		if (!Files.exists(output)) {
			try {
				Files.createFile(output);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try (InputStream inputStream = Files.newInputStream(Paths.get(inputFile));
				OutputStream outputStream = Files.newOutputStream(output)) {
			byte[] array;

			array = inputStream.readNBytes(4 * KB);

			while (array.length != 0) {

				byte[] result = cipher.update(array);
				if (result != null) {
					outputStream.write(result);
				}
				array = inputStream.readNBytes(4 * KB);
			}

			outputStream.write(cipher.doFinal());

		} catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
	}
}
