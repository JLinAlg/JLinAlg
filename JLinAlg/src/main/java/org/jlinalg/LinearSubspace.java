package org.jlinalg;

import java.io.Serializable;

/**
 * This class represents a linear subspace.
 * 
 * @author Andreas Keilhauer
 * @param <RE>
 *            the type of the domain.
 */

public class LinearSubspace<RE extends IRingElement>
		extends AffineLinearSubspace<RE>
		implements Serializable
{

	private static final long serialVersionUID = 1L;

	/**
	 * This constructs a linear subspace with the given generating System.
	 * 
	 * @param generatingSystem
	 */

	public LinearSubspace(Vector<RE>[] generatingSystem)
	{
		super(null, generatingSystem);
	}

	/**
	 * This constructs a linear subspace with the given generating System. The
	 * normalized flag, which is usually set after normalize is executed is also
	 * set.
	 * 
	 * @param generatingSystem
	 * @param normalized
	 */

	public LinearSubspace(Vector<RE>[] generatingSystem, boolean normalized)
	{
		super(null, generatingSystem, normalized);
	}

	/**
	 * Returns a String representation of this linear subspace.
	 * 
	 * @return String representation
	 */
	@Override
	public String toString()
	{
		StringBuffer tmp = new StringBuffer("< {");
		for (int i = 0; i < this.generatingSystem.length; i++) {
			if (i > 0) tmp.append(" ,");
			tmp.append(this.generatingSystem[i].toString());
		}
		tmp.append("} >");
		return tmp.toString();
	}
}
