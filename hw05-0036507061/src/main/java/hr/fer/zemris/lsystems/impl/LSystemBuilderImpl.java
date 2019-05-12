
package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * Implementation of LSystemBuilder.
 * 
 * @author david
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	/**
	 * Stores production in pair Character-String. For example : 'F' -> "F++F--F".
	 */
	private Dictionary<Character, String> production;

	/**
	 * Stores commands in pair Character-Command. For example : '+' -> Rotate by
	 * some angle.
	 */
	private Dictionary<Character, Command> commands;

	/**
	 * Represents origin of the turtle.
	 */
	private Vector2D origin;

	/**
	 * Start angle of the turtle. If the angle is 0 turtle is looking to the right.
	 */
	private double angle;

	/**
	 * Length of one turtle move.
	 */
	private double unitLength;

	/**
	 * Used to keep the dimension of fractal constant.
	 */
	private double unitLengthDegreeScaler;

	/**
	 * The starting string (or character) from which the system development is
	 * moving.
	 */
	private String axiom;

	/**
	 * Constructor that initializes private fields of this class.
	 */
	public LSystemBuilderImpl() {
		production = new Dictionary<>();
		commands = new Dictionary<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LSystem build() {
		return new MyLSystems();
	}

	/**
	 * Class that implements LSystem. Class consists of two methods. First one is
	 * draw method that draws the trace that turtle leaving while moving. Second one
	 * is generate. This method generates the sequence that represents commands
	 * which turtle will execute.
	 * 
	 * @author david
	 *
	 */
	public class MyLSystems implements LSystem {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void draw(int arg0, Painter arg1) {
			Context context = new Context();
			TurtleState state = new TurtleState(origin, new Vector2D(Math.cos(angle), Math.sin(angle)), Color.BLACK,
					unitLength * Math.pow(unitLengthDegreeScaler, arg0));
			context.pushState(state);

			char[] array = generate(arg0).toCharArray();

			for (Character c : array) {
				Command command = commands.get(c);

				if (command != null) {
					command.execute(context, arg1);
				}
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String generate(int arg0) {
			if (arg0 == 0) {
				return axiom;
			}

			char[] array = generate(arg0 - 1).toCharArray();
			StringBuilder sb = new StringBuilder();

			for (Character c : array) {
				if (production.get(c) != null) {
					sb.append(production.get(c));
				} else {
					sb.append(c);
				}
			}
			return sb.toString();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LSystemBuilder configureFromText(String[] arg0) {

		parseConfigureFromText(arg0);

		return this;
	}

	/**
	 * Method that initializes origin, angle, unitLength, unitLengthDegreeScaler,
	 * axiom, production and registers command.
	 * 
	 * @param array String array in which every line represents one command. For
	 *              example origin 0 0, or command F draw 1.
	 * @throws ParserException if any error during parsing occurs.
	 */
	private void parseConfigureFromText(String[] array) {
		for (String s : array) {
			String[] arr = s.trim().split("\\s+");

			if (s.trim().isEmpty()) {
				continue;
			}

			switch (arr[0]) {
			case "origin":
				nextOrigin(arr);
				break;
			case "angle":
				nextAngle(arr);
				break;
			case "unitLength":
				nextUnitLength(arr);
				break;
			case "unitLengthDegreeScaler":
				nextUnitLengthDegreeScaler(arr);
				break;
			case "command":
				nextCommand(arr);
				break;
			case "axiom":
				nextAxiom(arr);
				break;
			case "production":
				nextProduction(arr);
				break;
			default:
				throw new ParserException();
			}
		}
	}

	/**
	 * Method that reads information about the production.
	 * 
	 * @param array String representation of information about the production.
	 * @throws ParserException if any error during parsing occurs.
	 */
	private void nextProduction(String[] array) {
		if (array.length != 3) {
			throw new ParserException();
		}

		if (array[1].length() != 1) {
			throw new ParserException();
		}

		registerProduction(array[1].charAt(0), array[2]);
	}

	/**
	 * Method that reads information about the axiom and sets axiom.
	 * 
	 * @param array String representation of information about the axiom.
	 * @throws ParserException if any error during parsing occurs.
	 */
	private void nextAxiom(String[] array) {
		if (array.length != 2) {
			throw new ParserException();
		}

		setAxiom(array[1]);
	}

	/**
	 * Method that reads information about the next command to be registered.
	 * 
	 * @param array String representation of information about the next command to
	 *              be registered.
	 * @throws ParserException if any error during parsing occurs.
	 */
	private void nextCommand(String[] array) {
		StringBuilder sb = new StringBuilder();

		if (array.length < 3) {
			throw new ParserException();
		} else if (array[1].length() != 1) {
			throw new ParserException();
		}

		for (int i = 2; i < array.length; i++) {
			sb.append(array[i]).append(" ");
		}

		try {
			Command command = parse(sb.toString());
			commands.put(array[1].charAt(0), command);
		} catch (ParserException e) {
			throw new ParserException();
		}
	}

	/**
	 * Method that reads information about the uniLengthDegreeScaler and sets
	 * unitLengthDegreeeScaler.
	 * 
	 * @param array String representation of information about the
	 *              unitLengthDegreeScaler.
	 * @throws ParserException if any error during parsing occurs.
	 */
	private void nextUnitLengthDegreeScaler(String[] array) {
		if (array.length < 2 && array.length > 4) {
			throw new ParserException();
		}

		try {
			if (array.length == 2) {
				double uniLengthDegreeScaler = Double.parseDouble(array[1]);
				setUnitLengthDegreeScaler(uniLengthDegreeScaler);
			} else {
				double number1 = 0;
				double number2 = 0;
				if (array.length == 3) {
					if (array[1].endsWith("/")) {
						number1 = Double.parseDouble(array[1].substring(0, array[1].length() - 1));
						number2 = Double.parseDouble(array[2]);
					} else if (array[2].startsWith("/")) {
						number1 = Double.parseDouble(array[1]);
						number2 = Double.parseDouble(array[2].substring(1));
					} else {
						throw new ParserException();
					}
				} else {
					if (!array[2].equals("/")) {
						throw new ParserException();
					}
					number1 = Double.parseDouble(array[1]);
					number2 = Double.parseDouble(array[3]);
				}

				if (number2 == 0) {
					throw new ParserException();
				}

				setUnitLengthDegreeScaler(number1 / number2);
			}
		} catch (NumberFormatException e) {
			throw new ParserException();
		}
	}

	/**
	 * Method that reads information about the unitLength and sets unitLength.
	 * 
	 * @param array String representation of information about the unitLength.
	 * @throws ParserException if any error during parsing occurs.
	 */
	private void nextUnitLength(String[] array) {
		if (array.length != 2) {
			throw new ParserException();
		}

		try {
			double unitLength = Double.parseDouble(array[1]);
			setUnitLength(unitLength);
		} catch (NumberFormatException e) {
			throw new ParserException();
		}
	}

	/**
	 * Method that reads information about the origin and sets origin.
	 * 
	 * @param array String representation of information about the origin.
	 * @throws ParserException if any error during parsing occurs.
	 */
	private void nextOrigin(String[] array) {
		if (array.length != 3) {
			throw new ParserException();
		}

		try {
			double x = Double.parseDouble(array[1]);
			double y = Double.parseDouble(array[2]);

			setOrigin(x, y);

		} catch (NumberFormatException e) {
			throw new ParserException();
		}
	}

	/**
	 * Method that reads information about the angle and sets angle.
	 * 
	 * @param array String representing of information about the angle.
	 * @throws ParserException if any error during parsing occurs.
	 */
	private void nextAngle(String[] array) {
		if (array.length != 2) {
			throw new ParserException();
		}

		try {
			double angle = Double.parseDouble(array[1]);
			setAngle(angle);
		} catch (NumberFormatException e) {
			throw new ParserException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {

		commands.put(arg0, parse(arg1));

		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		production.put(arg0, arg1);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LSystemBuilder setAngle(double arg0) {
		this.angle = degreeToRadians(arg0);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LSystemBuilder setAxiom(String arg0) {
		this.axiom = arg0;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		origin = new Vector2D(arg0, arg1);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		unitLength = arg0;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		unitLengthDegreeScaler = arg0;
		return this;
	}

	/**
	 * Method converts angle given in degrees to radians.
	 * 
	 * @param degree Degree to be converted to radian.
	 * @return Radian representation of given angle in degrees.
	 */
	private double degreeToRadians(double degree) {
		return degree * (Math.PI / 180);
	}

	/**
	 * Method to parse given string into commands. Supported commands are "draw s",
	 * "skip s", "scale s", "rotate a", "push", "pop", "color rrggbb".
	 * 
	 * @param input String to be parsed.
	 * @return Command obtained by parsing.
	 * @throws ParserException if any error while parsing occurs.
	 */
	private Command parse(String input) {
		String[] array = input.trim().split("\\s+");

		try {
			if (array[0].equals("draw")) {
				double number = Double.parseDouble(array[1].trim());
				return new DrawCommand(number);
			} else if (array[0].equals("skip")) {
				double number = Double.parseDouble(array[1].trim());
				return new SkipCommand(number);

			} else if (array[0].equals("scale")) {
				double number = Double.parseDouble(array[1].trim());
				return new ScaleCommand(number);

			} else if (array[0].equals("rotate")) {
				double number = Double.parseDouble(array[1].trim());
				return new RotateCommand(degreeToRadians(number));
			} else if (array[0].equals("push")) {
				if (array.length != 1) {
					throw new ParserException();
				}
				return new PushCommand();
			} else if (array[0].equals("pop")) {
				if (array.length != 1) {
					throw new ParserException();
				}
				return new PopCommand();
			} else if (array[0].equals("color")) {
				return new ColorCommand(getColor(array[1]));
			} else {
				throw new ParserException();
			}

		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			throw new ParserException();
		}
	}

	/**
	 * Method that returns new Color from given hexadecimal String.
	 * 
	 * @param color Hexadecimal String.
	 * @return new Color.
	 * @throws ParserException if cannot parse to new Color.
	 */
	private Color getColor(String color) {
		if (color.length() != 6) {
			throw new ParserException();
		}

		try {
			int r = Integer.parseInt(color.substring(0, 2), 16);
			int g = Integer.parseInt(color.substring(2, 4), 16);
			int b = Integer.parseInt(color.substring(4, 6), 16);

			return new Color(r, g, b);
		} catch (NumberFormatException e) {
			throw new ParserException();
		}
	}

}