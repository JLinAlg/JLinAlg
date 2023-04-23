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

import org.jlinalg.operator.MonadicOperator;

/**
 * @author Georg Thimm
 */
public interface IRingElement<RE extends IRingElement<RE>>
		extends
		Comparable<RE>
{
	/**
	 * Tests if this RingElement is the neutral element of addition (zero).
	 * 
	 * @return true if zero
	 */
	public boolean isZero();

	/**
	 * Calculates the sum of this RingElement and another one.
	 * 
	 * @param other
	 * @return sum
	 */
	public RE add(RE other);

	/**
	 * Calculates the difference of this IRingElement and another one.
	 * 
	 * @param val
	 * @return difference
	 */
	public RE subtract(RE val);

	/**
	 * Calculates the inverse element of addition for this IRingElement.
	 * 
	 * @return negated
	 */
	public RE negate();

	/**
	 * Tests if this IRingElement is the neutral element of multiplication
	 * (one).
	 * 
	 * @return true if one
	 */
	public boolean isOne();

	/**
	 * Calculates the product of this IRingElement and another one.
	 * 
	 * @param other
	 * @return product
	 */
	public RE multiply(RE other);

	/**
	 * Determines whether or not two IRingElements are equal.
	 * 
	 * @param obj
	 * @return true if the two IRingElements are mathematically equal.
	 */

	@Override
	public boolean equals(Object obj);

	/**
	 * Returns the result of applying a specified function to this
	 * FielkdElement. New functions can be applied to a IRingElement by
	 * sub-classing the abstract <tt>MonadicOperator</tt> class.
	 * 
	 * @param fun
	 *            the function to apply
	 * @return result of applying <tt>fun</tt> to this {@link IRingElement}
	 */
	public RE apply(MonadicOperator<RE> fun);

	/**
	 * Returns absolute value of this element
	 * 
	 * @return the absolute value.
	 */
	public RE abs();

	/**
	 * @return the norm of this element.
	 */
	public RE norm();

	/**
	 * Checks whether this IRingElement is mathematically less than another.
	 * 
	 * @param val
	 * @return true if this IRingElement is less than val, false otherwise
	 */
	public boolean lt(RE val);

	/**
	 * Checks whether this IRingElement is mathematically greater than another.
	 * 
	 * @param val
	 * @return true if this IRingElement is greater than val, false otherwise
	 */
	public boolean gt(RE val);

	/**
	 * Checks whether this IRingElement is mathematically less than or equal to
	 * another.
	 * 
	 * @param val
	 * @return true if this IRingElement is less than or equal to val, false
	 *         otherwise
	 */
	public boolean le(RE val);

	/**
	 * Checks whether this IRingElement is mathematically greater than or equal
	 * to another.
	 * 
	 * @param val
	 * @return true if this IRingElement is greater than or equal to val, false
	 *         otherwise
	 */
	public boolean ge(RE val);

	/**
	 * Inverts this ring element (divides one by it)
	 * 
	 * @return the inverse of this ring element
	 * @throws DivisionByZeroException
	 *             if this is equal to the zero element.
	 */
	public RE invert() throws DivisionByZeroException;

	/**
	 * @param val
	 * @return this divided by val
	 * @throws DivisionByZeroException
	 */
	public RE divide(RE val) throws DivisionByZeroException;

	/**
	 * Give access to the factory for this type. This is to replace in a
	 * consistent manner the constructors of
	 * 
	 * @return a factory for creating instances of classes implementing
	 *         IRingElement
	 */
	public IRingElementFactory<RE> getFactory();

	public RE floor();

}
