package hr.fer.zemris.java.gui.layouts;

/**
 * Class used as constraint Object in custom layout manager called CalcLayout.
 * 
 * @author david
 *
 */
public class RCPosition {
	/**
	 * Row of the component.
	 */
	private int row;

	/**
	 * Column of the component.
	 */
	private int col;

	/**
	 * Initialize row and column.
	 * 
	 * @param row row of the component.
	 * @param col column of the component.
	 */
	public RCPosition(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 * Returns row.
	 * 
	 * @return row.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns column.
	 * 
	 * @return column.
	 */
	public int getCol() {
		return col;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
}
