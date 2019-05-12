package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Represents the content of 'FOR' tag. 'FOR' tag consists of one variable and
 * two or three additional expressions, start expression, end expression and
 * step expression, respectively. Step expression in optionally.
 * 
 * @author david
 *
 */
public class ForLoopNode extends Node {
	/**
	 * Element that represents the name of the variable.
	 */
	private ElementVariable variable;

	/**
	 * Represents the start of the expression.
	 */
	private Element startExpression;

	/**
	 * Represents the end of the expression.
	 */
	private Element endExpression;

	/**
	 * Represents the end of the expression. This is optional.
	 */
	private Element stepExpression;

	/**
	 * Constructor to initialize all members.
	 * 
	 * @param variable        The name of the variable.
	 * @param startExpression Start of the expression.
	 * @param endExpression   End of the expression.
	 * @param stepExpression  Step of the expression.
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Method to return variable.
	 * 
	 * @return variable.
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Method to return startExpression.
	 * 
	 * @return startExpression.
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Method to return endExpession.
	 * 
	 * @return endExpression.
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Method to return stepExpression.
	 * 
	 * @return stepExpression.
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
}
