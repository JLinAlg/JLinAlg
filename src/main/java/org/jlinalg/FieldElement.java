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

/**
 * This class represents an element of an arbitrary field. It has to have four
 * operations (add, subtract, multiply, divide) a neutral element of addition
 * (zero) and a neutral element of multiplication (one). Concrete number types
 * (fields) can be easily implemented by extending this class.<BR>
 * <STRONG>Note!</STRONG> Performance Issue<BR>
 * Most non abstract methods in this class were only implemented to make the
 * process of implementing a new FieldElement convenient and short. These
 * methods should be overwritten, whenever one is more interested in performance
 * than in just getting something that works.
 * <P>
 * <STRONG>Note!</STRONG> FieldElements are presumably immutable. Extensions to
 * this class should not allow a modification after instantiation!
 * <P>
 * 
 * @author Andreas Keilhauer, Simon D. Levy
 */

@SuppressWarnings("serial")
public abstract class FieldElement<RE extends IRingElement<RE>>
		extends RingElement<RE>
		implements IRingElement<RE>
{

	/**
	 * Implement subtraction as a addition of a negated value.
	 * 
	 * @param val
	 *            the value to be subtracted from this element.
	 * @return the difference
	 */
	@Override
	public RE subtract(RE val)
	{
		RE re = val.negate();
		return this.add(re);
	}

	/**
	 * Calculates the quotient of this FieldElement and another one.
	 * 
	 * @param val
	 * @return quotient
	 */
	@Override
	public RE divide(RE val) throws DivisionByZeroException
	{
		if (val.isZero()) {
			throw new DivisionByZeroException("Tried to divide " + this
					+ " by " + val + ".");
		}
		RE re = val.invert();
		return this.multiply(re);
	}

	/**
	 * @return the absolute value of this field.
	 * @see #abs()
	 */
	@Override
	public RE norm()
	{
		return this.abs();
	}
}
