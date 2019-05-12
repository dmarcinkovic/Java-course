package searching.slagalica;

import java.util.Arrays;

/**
 * Represents one puzzle configuration.
 * 
 * @author david
 *
 */
public class KonfiguracijaSlagalice {
	/**
	 * Stores puzzle configuration.
	 */
	private int[] configuration;

	/**
	 * Initialize configuration.
	 * 
	 * @param configuration Configuration.
	 */
	public KonfiguracijaSlagalice(int[] configuration) {
		this.configuration = configuration;
	}

	/**
	 * Returns configuration.
	 * 
	 * @return Configuration.
	 */
	public int[] getPolje() {
		return Arrays.copyOf(configuration, configuration.length);
	}

	/**
	 * Returns index of space.
	 * 
	 * @return Index of space.
	 */
	public int indexOfSpace() {
		for (int i = 0; i < configuration.length; i++) {
			if (configuration[i] == 0) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int index = i * 3 + j;
				if (configuration[index] == 0) {
					sb.append("* ");
				} else {
					sb.append(configuration[index]).append(" ");
				}
			}
			if (i != 2) {
				sb.append("\n");
			}
		}

		return sb.toString();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(configuration);
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
		KonfiguracijaSlagalice other = (KonfiguracijaSlagalice) obj;
		if (!Arrays.equals(configuration, other.configuration))
			return false;
		return true;
	}
}
