package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

/**
 * Implementation of one calculator model.
 * 
 * @author david
 *
 */
public class CalcModelImpl implements CalcModel {

	/**
	 * Flag to indicate if number is editable or not.
	 */
	private boolean isEditable = true;

	/**
	 * Flag to indicate if number is negative or positive;
	 */
	private boolean isNegative;

	/**
	 * Stores digits from input.
	 */
	private String digits = "";

	/**
	 * Numerical value of number stored in digit String.
	 */
	private double value;

	/**
	 * Active operand. For example, if used entered number 58 and then operation '+'
	 * then active operand will be 58.
	 */
	private Double activeOperand;

	/**
	 * Current pending operation.
	 */
	private DoubleBinaryOperator pendingOperation;

	/**
	 * Listeners.
	 */
	private List<CalcValueListener> listeners = new ArrayList<>();

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if l is null.
	 */
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if l is null.
	 */
	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(Objects.requireNonNull(l));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getValue() {
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(double value) {
		this.value = value;

		digits = String.valueOf(value);
		isEditable = false;
		informListeners();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEditable() {
		return isEditable;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		isEditable = true;
		value = 0;
		digits = "";
		isNegative = false;
		informListeners();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearAll() {
		clear();
		clearActiveOperand();
		pendingOperation = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void swapSign() throws CalculatorInputException {
		if (!isEditable) {
			throw new CalculatorInputException();
		}

		if (isNegative) {
			if (digits.isEmpty()) {
				digits = "0";
			}
			digits = digits.substring(1);
			isNegative = false;
		} else {
			if (!digits.isEmpty()) {
				digits = "-" + digits;
			}
			isNegative = true;
		}

		value *= -1;
		informListeners();
	}

	/**
	 * Used to show result in display and keep that value stored.
	 * 
	 * @param value Result value.
	 */
	public void setResult(double value) {
		this.value = value;
		digits = String.valueOf(value);
		informListeners();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!isEditable || digits.contains(".") || !digits.matches("-?\\d+\\.?\\d*") || digits.trim().isEmpty()
				|| digits.equals("-")) {
			throw new CalculatorInputException();
		}
		digits += ".";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!isEditable) {
			throw new CalculatorInputException();
		} else if (digit < 0 || digit > 9) {
			throw new IllegalArgumentException();
		}

		try {
			value = Double.parseDouble(digits + String.valueOf(digit));

			if (value > Double.MAX_VALUE) {
				throw new CalculatorInputException();
			}

			digits += String.valueOf(digit);

			removeLeadingZeros();

			informListeners();
		} catch (NumberFormatException e) {
			throw new CalculatorInputException();
		}
	}

	/**
	 * Method that removes reading zeros from String representation of number.
	 * 
	 */
	private void removeLeadingZeros() {
		int index = 0;

		if (digits.startsWith("-")) {
			index++;
		}

		while (index < digits.length() && digits.charAt(index) == '0' && digits.charAt(index) != '.') {
			if (index + 1 < digits.length() && digits.charAt(index + 1) == '.') {
				break;
			} else if (index + 1 == digits.length()) {
				break;
			}
			index++;
		}

		if (index < digits.length()) {
			digits = digits.substring(index);
		}

		if (isNegative) {
			digits = "-" + digits;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isActiveOperandSet()) {
			throw new IllegalStateException();
		}
		return activeOperand;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		digits = "";
		value = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
	}

	/**
	 * Method used to inform all listeners that value has changed.
	 */
	private void informListeners() {
		for (CalcValueListener listener : listeners) {
			listener.valueChanged(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (digits.isEmpty()) {
			return isNegative ? "-0" : "0";
		}
		return getCorrectStringValue();
	}

	/**
	 * Returns correct String value of number presented in calculator. For example,
	 * if number does not fit in double, its String value must be +Infinity.
	 * 
	 * @return Correct String value of number presented in calculator.
	 */
	private String getCorrectStringValue() {
		if (value == Double.POSITIVE_INFINITY) {
			return "Infinity";
		} else if (value == Double.NaN) {
			return "Nan";
		} else if (value == Double.NEGATIVE_INFINITY) {
			return "-Infinity";
		}
		return digits;
	}

	/**
	 * Set value equal to 0 without informing listener about that.
	 */
	public void clearWithoutInforming() {
		isEditable = true;
		value = 0;
		digits = "";
		isNegative = false;
		clearActiveOperand();
		pendingOperation = null;
	}

	/**
	 * Prints error message to calculator display.
	 * 
	 * @param message Error message.
	 */
	public void printErrorMessage(String message) {
		digits = message;
		informListeners();
	}
}
