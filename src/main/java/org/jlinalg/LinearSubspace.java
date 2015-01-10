/*
 * This file is part of JLinAlg (<http://jlinalg.sourceforge.net/>).
 * 
 * JLinAlg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * JLinAlg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with JLinALg. If not, see <http://www.gnu.org/licenses/>.
 */
package org.jlinalg;

import java.io.Serializable;

/**
 * This class represents a linear subspace.
 * 
 * @author Andreas Keilhauer
 * @param <RE>
 *            the type of the domain.
 */

public class LinearSubspace<RE extends IRingElement<RE>>
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
