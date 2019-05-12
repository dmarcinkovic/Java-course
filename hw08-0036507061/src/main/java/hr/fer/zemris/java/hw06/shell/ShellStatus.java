package hr.fer.zemris.java.hw06.shell;

/**
 * Represents status of the shell. Status can be CONTINUE and TERMINATE. Status
 * CONTINUE is one that indicates that last executed command is executed
 * successfully. Otherwise the status of the shell is TERMINATE.
 * 
 * @author david
 *
 */
public enum ShellStatus {
	/**
	 * Status that indicate that last executes command is executed successfully.
	 */
	CONTINUE,

	/**
	 * Status that indicates that last executes command is not executed
	 * successfully.
	 */
	TERMINATE
}
