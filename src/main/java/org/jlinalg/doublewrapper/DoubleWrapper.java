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
package org.jlinalg.doublewrapper;

import java.util.regex.Pattern;

import org.jlinalg.DivisionByZeroException;
import org.jlinalg.FieldElement;
import org.jlinalg.IRingElement;
import org.jlinalg.InvalidOperationException;

/**
 * This class wraps a double value and performs all FieldElement operations on
 * that double. Fast but not without rounding errors.
 * 
 * @author Andreas Keilhauer, Georg Thimm
 */

public class DoubleWrapper
		extends
		FieldElement<DoubleWrapper>
		implements
		IRingElement<DoubleWrapper>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @return the hashCode of the encapsulated value.
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		long l = Double.doubleToLongBits(value);
		return (int) ((l >> 32) ^ l);
	}

	/**
	 * The encapsulated value.
	 */
	protected double value;

	/**
	 * Builds an element from a double-precision floating-point value.
	 * 
	 * @param value
	 *            a double value to be wrapped
	 */
	DoubleWrapper(double value)
	{
		this.value = value;
	}

	/**
	 * @return the double value this instance wraps.
	 */
	public double getValue()
	{
		return value;
	}

	/**
	 * Calculates the sum of this element and another one.
	 * 
	 * @param val
	 * @return sum
	 */
	@Override
	public DoubleWrapper add(DoubleWrapper val)
	{
		return new DoubleWrapper(this.value + (val).value);
	}

	/**
	 * Calculates the product of this element and another one.
	 * 
	 * @param val
	 * @return product
	 */
	@Override
	public DoubleWrapper subtract(DoubleWrapper val)
	{
		return new DoubleWrapper(this.value - (val).value);
	}

	/**
	 * Calculates the product of this element and another one.
	 * 
	 * @param val
	 * @return product
	 */
	@Override
	public DoubleWrapper multiply(DoubleWrapper val)
	{
		return new DoubleWrapper(this.value * (val).value);
	}

	/**
	 * Calculates the quotient of this element and another one.
	 * 
	 * @param val
	 * @return this / val
	 */
	@Override
	public DoubleWrapper divide(DoubleWrapper val)
			throws DivisionByZeroException
	{
		if (val.isZero()) {
			throw new DivisionByZeroException(
					"Tried to divide " + this + "by" + val + ".");
		}
		return new DoubleWrapper(this.value / (val).value);
	}

	/**
	 * Calculates the inverse element of addition for this element.
	 * 
	 * @return negated (-value)
	 */
	@Override
	public DoubleWrapper negate()
	{
		return FACTORY.get(-this.value);
	}

	/**
	 * Calculates the inverse element of multiplication for this element.
	 * 
	 * @return inverted (1/value)
	 * @throws InvalidOperationException
	 *             if original value is zero
	 */
	@Override
	public DoubleWrapper invert() throws DivisionByZeroException
	{
		if (this.isZero()) {
			throw new DivisionByZeroException("Tried to invert zero.");
		}
		return new DoubleWrapper(1.0 / this.value);
	}

	/**
	 * Checks two FieldElements for equality.
	 * 
	 * @param obj
	 * @return true if the two FieldElements are mathematically equal.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) return false;
		DoubleWrapper comp = (DoubleWrapper) obj;
		return this.value == comp.value;
	}

	/**
	 * Returns a String representation of this element.
	 * 
	 * @return String representation
	 */
	@Override
	public String toString()
	{
		return "" + this.value;
	}

	/**
	 * Returns the double-precision floating-point value of this element.
	 * 
	 * @return value
	 */
	public double doubleValue()
	{
		return this.value;
	}

	/**
	 * Implements Comparable.compareTo(Object).
	 * 
	 * @param o
	 *            the object
	 * @return {-,+,0} if this object is less than, equal to, or greater than
	 *         the
	 *         specified object.
	 */
	@Override
	public int compareTo(DoubleWrapper o)
	{
		DoubleWrapper comp = o;
		return this.value < comp.value ? -1 : (this.value > comp.value ? 1 : 0);
	}

	/**
	 * @return the square root of this value.
	 */
	public DoubleWrapper sqrt()
	{
		return FACTORY.get(Math.sqrt(this.value));
	}

	/**
	 * @return the singleton factory for this type.
	 */
	@Override
	public DoubleWrapperFactory getFactory()
	{
		return FACTORY;
	}

	/**
	 * The singleton factory for DoubleWrapper
	 */
	public static final DoubleWrapperFactory FACTORY = DoubleWrapperFactory.INSTANCE;

	/**
	 * used in {@link DoubleWrapperFactory#get(Object)} to parse fractions.
	 */
	static final Pattern fractionPattern = Pattern
			.compile("([-+]{0,1}\\d+)/([-+]{0,1}\\d+)");

	/**
	 * @see org.jlinalg.IRingElement#abs()
	 */
	@Override
	public DoubleWrapper abs()
	{
		return (value >= 0) ? this : FACTORY.get(-value);
	}

	@Override
	public DoubleWrapper floor()
	{
		return FACTORY.get(Math.floor(value));
	}

}
