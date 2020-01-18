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

import org.jlinalg.operator.MonadicOperator;

/**
 * The basic implementation of some methods for most ring elements.
 * 
 * @author Andreas, Georg Thimm
 */
public abstract class RingElement<RE extends IRingElement<RE>>
		implements
		IRingElement<RE>,
		Comparable<RE>,
		Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Tests if this RingElement is the neutral element of addition (zero).
	 * 
	 * @return true if zero
	 */
	@Override
	public boolean isZero()
	{
		return this.equals(getFactory().zero());
	}

	/**
	 * Calculates the difference of this RingElement and another one.
	 * 
	 * @param val
	 * @return difference
	 */
	@Override
	public RE subtract(RE val)
	{
		RE re = val.negate();
		return add(re);
	}

	/**
	 * Tests if this RingElement is the neutral element of multiplication (one).
	 * 
	 * @return true if one
	 */
	@Override
	public boolean isOne()
	{
		return this.equals(getFactory().one());
	}

	/**
	 * Determines whether or not two RingElements are equal.
	 * 
	 * @param obj
	 * @return true if the two RingElements are mathematically equal.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) {
			return false;
		}
		return this.compareTo((RE) obj) == 0;
	}

	/**
	 * Returns the result of applying a specified function to this
	 * FieldElement. New functions can be applied to a RingElement by
	 * sub-classing the abstract <tt>MonadicOperator</tt> class.
	 * 
	 * @param fun
	 *            the function to apply
	 * @return result of applying <tt>fun</tt> to this {@link RingElement}
	 */

	@Override
	public RE apply(MonadicOperator<RE> fun)
	{
		return fun.apply((RE) this);
	}

	/**
	 * @return the absolute value of this field.
	 */
	@Override
	public RE norm()
	{
		return this.abs();
	}

	/**
	 * Checks whether this RingElement is mathematically less than another.
	 * 
	 * @param val
	 * @return true if this RingElement is less than {@code val}, false
	 *         otherwise
	 * @throws InvalidOperationException
	 *             if @code this} and {@code val} are not instances of the
	 *             same class
	 */
	@Override
	public boolean lt(RE val)
	{
		if (getClass() != val.getClass()) throw new InvalidOperationException(
				"cannot compare " + this + " in class" + getClass() + " and "
						+ val + " in class " + val.getClass());
		return compareTo(val) < 0;
	}

	/**
	 * Checks whether this RingElement is mathematically greater than another.
	 * 
	 * @param val
	 * @return true if this RingElement is greater than val, false otherwise
	 * @throws InvalidOperationException
	 *             if {@code this} and {@code val} are not instances of the
	 *             same class
	 */
	@Override
	public boolean gt(RE val)
	{
		if (getClass() != val.getClass()) throw new InvalidOperationException(
				"cannot compare " + this + " in class" + getClass() + " and "
						+ val + " in class " + val.getClass());
		return compareTo(val) > 0;
	}

	/**
	 * Checks whether this RingElement is mathematically less than or equal to
	 * another.
	 * 
	 * @param val
	 * @return true if this RingElement is less than or equal to {@code val},
	 *         false
	 *         otherwise
	 * @throws InvalidOperationException
	 *             if {@code this} and {@code val} are not instances of the
	 *             same class
	 */
	@Override
	public boolean le(RE val)
	{
		if (getClass() != val.getClass()) throw new InvalidOperationException(
				"cannot compare " + this + " in class" + getClass() + " and "
						+ val + " in class " + val.getClass());
		return compareTo(val) <= 0;
	}

	/**
	 * Checks whether this RingElement is mathematically greater than or equal
	 * to another.
	 * 
	 * @param val
	 * @return true if this RingElement is greater than or equal to {@code val},
	 *         false otherwise
	 * @throws InvalidOperationException
	 *             if {@code this} and {@code val} are not instances of the
	 *             same class
	 */
	@Override
	public boolean ge(RE val)
	{
		if (getClass() != val.getClass()) throw new InvalidOperationException(
				"cannot compare " + this + " in class" + getClass() + " and "
						+ val + " in class " + val.getClass());
		return compareTo(val) >= 0;
	}

	/**
	 * @throws DivisionByZeroException
	 *             if a division is possible for the type, but <code>val</code>
	 *             is
	 *             the zero element.
	 * @throws InvalidOperationException
	 *             as in the general case, an inversion is not possible.
	 */
	@Override
	public RE divide(@SuppressWarnings("unused") RE val)
			throws DivisionByZeroException, InvalidOperationException
	{
		throw new InvalidOperationException("RingElements of type "
				+ this.getClass().getCanonicalName() + " cannot be divided!");
	}

	/**
	 * @throws DivisionByZeroException
	 *             if an inversion is possible for the type, but the element is
	 *             the zero element.
	 * @throws InvalidOperationException
	 *             as in the general case, an inversion is not possible.
	 */
	@Override
	public RE invert() throws DivisionByZeroException, InvalidOperationException
	{
		throw new InvalidOperationException("RingElements of type "
				+ this.getClass().getCanonicalName() + " cannot be inverted! "
				+ this.getClass() + " objects cannot be inverted.");
	}

	@Override
	public abstract int hashCode();

}
