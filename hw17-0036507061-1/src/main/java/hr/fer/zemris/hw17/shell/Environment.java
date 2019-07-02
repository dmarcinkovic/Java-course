package hr.fer.zemris.hw17.shell;

import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.hw17.article.Article;
import hr.fer.zemris.hw17.commands.ShellCommand;
import hr.fer.zemris.hw17.vocabulary.Vocabulary;

/**
 * Interface representing all commands available in console.
 * 
 * @author David
 *
 */
public interface Environment {
	/**
	 * This command is used to read line from the console.
	 * 
	 * @return Read line.
	 * @throws ShellIOException If reading from the console fails.
	 */
	String readLine() throws ShellIOException;

	/**
	 * This command is used to write to the console.
	 * 
	 * @param text Text to be written to the console.
	 * @throws ShellIOException If writing to the console fails.
	 */
	void write(String text) throws ShellIOException;

	/**
	 * This command is used to write to the console and additionally write newline
	 * symbol. '\n'.
	 * 
	 * @param text Text to be written to the console.
	 * @throws ShellIOException If writing to the console fails.
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * 
	 * Returns all commands available.
	 * 
	 * @return All Commands available.
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Returns character that represents multiline symbol. Multiline is symbol that
	 * shell uses to indicate that command is written in more than one line.
	 * 
	 * @return Character that represents multiline symbol.
	 */
	Character getMultilineSymbol();

	/**
	 * Returns the character that represents prompt symbol.
	 * 
	 * @return Character that represents prompt symbol.
	 */
	Character getPromptSymbol();

	/**
	 * Method that returns the symbol that is used to indicate that command that is
	 * written to the console will be in more lines.
	 * 
	 * @return Symbol that is used to indicate that command that is written to the
	 *         console will be in more lines.
	 */
	Character getMorelinesSymbol();

	/**
	 * Method used to set the result of previously executed command.
	 * 
	 * @param commands List of strings that represents the result of previously
	 *                 executed command.
	 */
	void setResultOfPreviousCommand(List<String> commands);

	/**
	 * Method that returns the list of strings that represents the result of
	 * previously executed command.
	 * 
	 * @return Result of previously executed command.
	 */
	List<String> getResultOfPreviousCommand();

	/**
	 * Method that returns the article that is stored in given path.
	 * 
	 * @param path Path in which the article is stored.
	 * @return Article that is stored in given path.
	 */
	Article getDocument(String path);

	/**
	 * Returns vocabulary.
	 * 
	 * @return vocabulary.
	 */
	Vocabulary getVocabulay();

}
