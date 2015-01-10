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
package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * abstract class to compare two <code>RE</code>'s.
 * 
 * @param <RE>
 *            a specialisation of {@link IRingElement}
 */
public abstract class FEComparator<RE extends IRingElement<RE>>
{

	/**
	 * @param x
	 *            an instance of {@link IRingElement}
	 * @param y
	 *            an instance of {@link IRingElement}
	 * @return true if a and b have the same value.
	 */
	public abstract boolean compare(RE x, RE y);
}
