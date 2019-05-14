package hr.fer.zemris.java.gui.calc.model;

import java.util.List;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

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
	private List<CalcValueListener> listeners;

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
			isNegative = false;
		} else {
			isNegative = true;
		}

		value *= -1;
		digits = String.valueOf(value);
		informListeners();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!isEditable || digits.contains(".") || !digits.matches("\\d+")) {
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
			digits += String.valueOf(digit);
			informListeners();
		} catch (NumberFormatException e) {
			throw new CalculatorInputException();
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

}
