package org.jlinalg;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class wraps a double value and performs all FieldElement operations on
 * that double. Fast but not without rounding errors.
 * 
 * @author Andreas Keilhauer, Georg Thimm
 */

public class DoubleWrapper
		extends FieldElement
		implements IRingElement
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
		return (int) Double.doubleToLongBits(value);
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
	public DoubleWrapper add(IRingElement val)
	{
		return new DoubleWrapper(this.value + ((DoubleWrapper) val).value);
	}

	/**
	 * Calculates the product of this element and another one.
	 * 
	 * @param val
	 * @return product
	 */
	@Override
	public DoubleWrapper subtract(IRingElement val)
	{
		return new DoubleWrapper(this.value - ((DoubleWrapper) val).value);
	}

	/**
	 * Calculates the product of this element and another one.
	 * 
	 * @param val
	 * @return product
	 */
	public DoubleWrapper multiply(IRingElement val)
	{
		return new DoubleWrapper(this.value * ((DoubleWrapper) val).value);
	}

	/**
	 * Calculates the quotient of this element and another one.
	 * 
	 * @param val
	 * @return this / val
	 */
	@Override
	public DoubleWrapper divide(IRingElement val)
			throws DivisionByZeroException
	{
		if (val.isZero()) {
			throw new DivisionByZeroException("Tried to divide " + this + "by"
					+ val + ".");
		}
		return new DoubleWrapper(this.value / ((DoubleWrapper) val).value);
	}

	/**
	 * Calculates the inverse element of addition for this element.
	 * 
	 * @return negated (-value)
	 */
	public DoubleWrapper negate()
	{
		return new DoubleWrapper(-this.value);
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
	 * @return -,+,0} as this object is less than, equal to, or greater than the
	 *         specified object.
	 */
	public int compareTo(IRingElement o)
	{
		DoubleWrapper comp = (DoubleWrapper) o;
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
	 * The singelton factory for DoubleWrapper
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
	public class DoubleWrapperFactory
			extends RingElementFactory<DoubleWrapper>
	{
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
					throw new InvalidOperationException("String " + o
							+ " does not represent a double.");
				}
			}
			if (o instanceof Rational) {
				return get(((Rational) o).doubleValue());
			}
			if (o instanceof IRingElement) {
				IRingElement e = (IRingElement) o;
				// try the doubleValue method.
				try {
					java.lang.reflect.Method method = e.getFactory().getClass()
							.getDeclaredMethod("doubleValue");
					if (method.getReturnType().getClass().equals(Double.class))
					{
						Double d = (Double) method.invoke(e);
						return get(d.doubleValue());
					}
				} catch (SecurityException e1) {
					throw new InvalidOperationException("SecurityException "
							+ e1.getMessage());
				} catch (NoSuchMethodException e1) {
					// bad luck - try something else...
				} catch (IllegalArgumentException e1) {
					// this should not happen.
					throw new InternalError("IllegalArgumentException "
							+ e1.getMessage());
				} catch (IllegalAccessException e1) {
					// this should not happen.
					throw new InternalError("IllegalAccessException "
							+ e1.getMessage());
				} catch (InvocationTargetException e1) {
					// this should not happen.
					throw new InternalError("InvocationTargetException "
							+ e1.getMessage());
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
		@Deprecated
		public DoubleWrapper randomValue(Random random)
		{
			return new DoubleWrapper(random.nextDouble());
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
		@Deprecated
		public DoubleWrapper gaussianRandomValue(Random random)
		{
			return new DoubleWrapper(random.nextGaussian());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jlinalg.IRingElementFactory#get(long)
		 */
		public DoubleWrapper get(long d)
		{
			return new DoubleWrapper(d);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jlinalg.IRingElementFactory#randomValue(java.util.Random,
		 * java.lang.Object, java.lang.Object)
		 */
		@Override
		@Deprecated
		public DoubleWrapper randomValue(Random random, IRingElement min_,
				IRingElement max_)
		{
			double min = ((DoubleWrapper) min_).value;
			double max = ((DoubleWrapper) max_).value;
			return get(random.nextDouble() * (max - min) + min);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jlinalg.IRingElementFactory#gaussianRandomValue()
		 */
		@Override
		public DoubleWrapper gaussianRandomValue()
		{
			return new DoubleWrapper(random.nextGaussian());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.jlinalg.IRingElementFactory#randomValue(org.jlinalg.IRingElement,
		 * org.jlinalg.IRingElement)
		 */
		@Override
		public DoubleWrapper randomValue(IRingElement min, IRingElement max)
		{
			double min_ = ((DoubleWrapper) min).value;
			double max_ = ((DoubleWrapper) max).value;
			return get(random.nextDouble() * (max_ - min_) + min_);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jlinalg.IRingElementFactory#randomValue()
		 */
		@Override
		public DoubleWrapper randomValue()
		{
			return new DoubleWrapper(random.nextDouble());
		}

	}

	/**
	 * @param d
	 *            a double value
	 * @return the value of <code>this - d</code>.
	 */
	public DoubleWrapper subtract(DoubleWrapper d)
	{
		return (DoubleWrapper) super.subtract(d);
	}
}
