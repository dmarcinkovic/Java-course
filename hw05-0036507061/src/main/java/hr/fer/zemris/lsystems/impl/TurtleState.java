package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Class represents the state of the turtle. Turtle state is defined with its
 * position, direction, shift length and color with which the turtle draw the
 * lines.
 * 
 * @author david
 *
 */
public class TurtleState {
	/**
	 * Radius-vector that stores the position of the turtle.
	 */
	private Vector2D position;

	/**
	 * Vector which length is 1. Vector stores direction of the turtle.
	 */
	private Vector2D direction;

	/**
	 * The color with which turtle draw the lines.
	 */
	private Color color;

	/**
	 * The shift length.
	 */
	private double length;

	/**
	 * Constructor used to initialize Turtle state.
	 * 
	 * @param position  new Turtle position.
	 * @param direction Direction of the turtle-
	 * @param color     Color with which turtle draw the lines-
	 * @param length    The shift length.
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double length) {
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.length = length;
	}

	/**
	 * Creates object of type TurtleState with the same fields as this one.
	 * 
	 * @return Copy of this object.
	 */
	public TurtleState copy() {
		Color newColor = new Color(color.getRGB());
		
		return new TurtleState(position.copy(), direction.copy(), newColor, length);
	}

	/**
	 * Returns direction of the turtle.
	 * 
	 * @return Direction of the turtle.
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Sets direction to new value.
	 * 
	 * @param direction New direction.
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * Returns position of the turtle.
	 * 
	 * @return Position of the turtle.
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Sets position to new value.
	 * 
	 * @param position New position.
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * Sets color to new color.
	 * 
	 * @param color New color.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Returns color.
	 * 
	 * @return color.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Returns length.
	 * 
	 * @return length.
	 */
	public double getLength() {
		return length;
	}
}
