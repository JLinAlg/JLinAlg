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

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jlinalg.DivisionByZeroException;
import org.jlinalg.FieldElement;
import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.JLinAlgTypeProperties;
import org.jlinalg.RingElementFactory;
import org.jlinalg.rational.Rational;

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
	public DoubleWrapper(double value)
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

	// ===============================================================
	// Constants for DoubleWrapperFactory

	/**
	 * the constant -1
	 */
	private static final DoubleWrapper M_ONE = new DoubleWrapper(-1);

	/**
	 * the constant 1
	 */
	private static final DoubleWrapper ONE = new DoubleWrapper(1);

	/**
	 * the constant 0
	 */
	private static final DoubleWrapper ZERO = new DoubleWrapper(0);

	/**
	 * The singleton factory for DoubleWrapper
	 */
	public static final DoubleWrapperFactory FACTORY = ZERO.new DoubleWrapperFactory();

	/**
	 * used in {@link DoubleWrapperFactory#get(Object)} to parse fractions.
	 */
	static final Pattern fractionPattern = Pattern
			.compile("([-+]{0,1}\\d+)/([-+]{0,1}\\d+)");

	/**
	 * The factory for instances of class {@link DoubleWrapper#FACTORY}
	 */
	@JLinAlgTypeProperties(isExact = false, isDiscreet = false)
	public class DoubleWrapperFactory
			extends
			RingElementFactory<DoubleWrapper>
			implements
			IRingElementFactory<DoubleWrapper>
	{
		private static final long serialVersionUID = 1L;

		/**
		 * only used (once) by {@link DoubleWrapper} to instantiate
		 * {@link DoubleWrapper#FACTORY}
		 */
		private DoubleWrapperFactory()
		{
			super();
		}

		@Override
		public DoubleWrapper get(double d)
		{

			return new DoubleWrapper(d);
		}

		@Override
		public DoubleWrapper get(int i)
		{
			return new DoubleWrapper(i);
		}

		@Override
		public DoubleWrapper get(Object o)
		{
			if (o instanceof DoubleWrapper) return (DoubleWrapper) o;
			if (o instanceof String) {
				try {
					String s = (String) o;
					Matcher m = fractionPattern.matcher(s);

					if (m.matches()) {
						return get(Double.parseDouble(m.group(1))
								/ Double.parseDouble(m.group(2)));
					}
					return new DoubleWrapper(Double.parseDouble(s));
				} catch (NumberFormatException e) {
					throw new InvalidOperationException(
							"String " + o + " does not represent a double.");
				}
			}
			if (o instanceof Rational) {
				return get(((Rational) o).doubleValue());
			}
			if (o instanceof IRingElement<?>) {
				IRingElement<?> e = (IRingElement<?>) o;
				// try the doubleValue method.
				try {
					java.lang.reflect.Method method = e.getFactory().getClass()
							.getDeclaredMethod("doubleValue");
					if (method.getReturnType().getClass()
							.equals(Double.class))
					{
						Double d = (Double) method.invoke(e);
						return get(d.doubleValue());
					}
				} catch (SecurityException e1) {
					throw new InvalidOperationException(
							"SecurityException " + e1.getMessage());
				} catch (NoSuchMethodException e1) {
					// bad luck - try something else...
				} catch (IllegalArgumentException e1) {
					// this should not happen.
					throw new InternalError(
							"IllegalArgumentException " + e1.getMessage());
				} catch (IllegalAccessException e1) {
					// this should not happen.
					throw new InternalError(
							"IllegalAccessException " + e1.getMessage());
				} catch (InvocationTargetException e1) {
					// this should not happen.
					throw new InternalError(
							"InvocationTargetException " + e1.getMessage());
				}
			}
			return super.get(o);
		}

		@Override
		public DoubleWrapper[][] getArray(int rows, int columns)
		{
			return new DoubleWrapper[rows][columns];
		}

		@Override
		public DoubleWrapper m_one()
		{
			return M_ONE;
		}

		@Override
		public DoubleWrapper one()
		{
			return ONE;
		}

		@Override
		public DoubleWrapper zero()
		{
			return ZERO;
		}

		@Override
		public DoubleWrapper[] getArray(int size)
		{
			return new DoubleWrapper[size];
		}

		@Override
		public DoubleWrapper get(long d)
		{
			return new DoubleWrapper(d);
		}

		@Override
		public DoubleWrapper gaussianRandomValue()
		{
			return new DoubleWrapper(random.nextGaussian());
		}

		@Override
		public DoubleWrapper randomValue(DoubleWrapper min, DoubleWrapper max)
		{
			double min_ = min.value;
			double max_ = max.value;
			return get(random.nextDouble() * (max_ - min_) + min_);
		}

		@Override
		public DoubleWrapper randomValue()
		{
			return new DoubleWrapper(random.nextDouble());
		}
	}

	/**
	 * @see org.jlinalg.IRingElement#abs()
	 */
	@Override
	public DoubleWrapper abs()
	{
		return (value >= 0) ? this : FACTORY.get(-value);
	}

}
