package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Stores values.
 * 
 * @author david
 *
 */
public class ValueWrapper {
	/**
	 * Value stored.
	 */
	private Object value;

	/**
	 * Constructor to initialize value.
	 * 
	 * @param value new value.
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Method to check types of the value for arithmetic operations.
	 * 
	 * @param secondValue Second value passed as an argument for arithmetic
	 *                    operations.
	 * @return True if both values are of type String, Integer, Double or null.
	 */
	private boolean checkTypes(Object secondValue) {
		return (value instanceof String || value instanceof Integer || value instanceof Double || value == null)
				&& (secondValue instanceof Integer || secondValue instanceof Double || secondValue instanceof String
						|| secondValue == null);
	}

	/**
	 * Returns double or integer representing Number value of given String. If
	 * cannot cast to integer not double RuntimeException is thrown.
	 * 
	 * @param value String value.
	 * @return Double or integer representing Number value of given String.
	 * 
	 */
	private Object getValueFromString(Object value) {
		try {
			value = Integer.parseInt((String) value);
		} catch (RuntimeException e) {
			try {
				value = Double.parseDouble((String) value);
			} catch (RuntimeException e1) {
				throw new RuntimeException();
			}
		}
		return value;
	}

	/**
	 * Returns double or integer value of given object. If cannot be casted to one
	 * of those two RuntimeException is thrown.
	 * 
	 * @param value Given object value.
	 * @return Double or Integer value of given object value.
	 * @throws RuntimeException if cannot case to integer nor double.
	 */
	private Object getValue(Object value) {
		try {
			if (value == null) {
				value = (Integer) 0;
			} else if (value instanceof String) {
				value = getValueFromString(value);
			}
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
		return value;
	}

	/**
	 * 
	 * Performs specified operation under the doubles value1 and value2.
	 * 
	 * @param operation String represents the operation to be performed.
	 * @param value1    Double value1.
	 * @param value2    Double value2.
	 * @return Result of the specified operation as Double.
	 * @throws ArithmeticException if dividing by zero.
	 */
	private Double performOperation(String operation, double value1, double value2) {
		switch (operation) {
		case "add":
			return value1 + value2;
		case "subtract":
			return value1 - value2;
		case "multiply":
			return value1 * value2;
		case "divide":
			if (value2 == 0) {
				throw new ArithmeticException("Divide by zero");
			}
			return value1 / value2;
		}
		return null;
	}

	/**
	 * Performs specified operation under the integers value1 and value2.
	 * 
	 * @param operation String represents the operation to be performed.
	 * @param value1    Integer value1.
	 * @param value2    Integer value2.
	 * @return Result of the specified operation as Integer.
	 * @throws ArithmeticException if dividing by zero.
	 */
	private Integer performOperation(String operation, int value1, int value2) {
		switch (operation) {
		case "add":
			return value1 + value2;
		case "subtract":
			return value1 - value2;
		case "multiply":
			return value1 * value2;
		case "divide":
			if (value2 == 0) {
				throw new ArithmeticException("Divide by zero");
			}
			return value1 / value2;
		}
		return null;
	}

	/**
	 * Performs arithmetic operation. First it checks if value and provided argument
	 * are null or instances of String, Integer or Double. If this condition is not
	 * satisfied RuntimeException is thrown. Then we get double or int value from
	 * getValue method and then perform operation based on that.
	 * 
	 * @param operation String represents the operation to be performed.
	 * @param argument  Argument of arithmetic operations.
	 * @throws RuntimeException if arguments are not null nor String, Double or
	 *                          Integer.
	 */
	private void performOperation(String operation, Object argument) {
		if (!checkTypes(argument)) {
			throw new RuntimeException("Value must be null or instance of String, Integer or Double.");
		}

		try {
			value = getValue(value);
			argument = getValue(argument);

			if (value instanceof Double && argument instanceof Double) {
				value = performOperation(operation, (Double) value, (Double) argument);
			} else if (value instanceof Double) {
				int temp = (Integer) argument;
				value = performOperation(operation, (Double) value, (double) temp);
			} else if (argument instanceof Double) {
				int temp = (Integer) value;
				value = performOperation(operation, (double) temp, (Double) argument);
			} else {
				value = performOperation(operation, (Integer) value, (Integer) argument);
			}

		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Method to add two objects.
	 * 
	 * @param incValue Value to be added with value that this object stores.
	 * @throws RuntimeException if values are not of type Intger or String or Double
	 *                          or null.
	 */
	public void add(Object incValue) {
		try {
			performOperation("add", incValue);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Method to subtract two objects.
	 * 
	 * @param decValue Decrement value.
	 * @throws RuntimeException if values are not of type Intger or String or Double
	 *                          or null.
	 */
	public void subtract(Object decValue) {
		try {
			performOperation("subtract", decValue);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Method to multiply two objects.
	 * 
	 * @param mulValue Multiply value.
	 * @throws RuntimeException if values are not of type Intger or String or Double
	 *                          or null.
	 */
	public void multiply(Object mulValue) {
		try {
			performOperation("multiply", mulValue);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Method to divide two objects.
	 * 
	 * @param divValue Divide value.
	 * @throws RuntimeException if values are not of type Intger or String or Double
	 *                          or null.
	 */
	public void divide(Object divValue) {
		try {
			performOperation("divide", divValue);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Method to compare given value with value that this object stores.
	 * 
	 * @param withValue Value to compare this.value with.
	 * @return True if given objects are equals.
	 * @throws RuntimeException if value of withValue cannot be converted to double
	 *                          or integer.
	 */
	public int numCompare(Object withValue) {
		if (!checkTypes(withValue)) {
			throw new RuntimeException();
		}

		try {
			Object value1 = getValue(value);
			Object value2 = getValue(withValue);

			double num1 = 0, num2 = 0;

			if (value1 instanceof Double) {
				num1 = (Double) value1;
			} else if (value1 instanceof Integer) {
				num1 = (double) (Integer) value1;
			}

			if (value2 instanceof Double) {
				num2 = (Double) value2;
			} else if (value2 instanceof Integer) {
				num2 = (double) (Integer) value2;
			}

			return Double.compare(num1, num2);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Sets new value.
	 * 
	 * @param value New value.
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Returns value.
	 * 
	 * @return value.
	 */
	public Object getValue() {
		return value;
	}
}
