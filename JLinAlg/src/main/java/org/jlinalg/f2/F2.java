package org.jlinalg.f2;

import java.util.Random;

import org.jlinalg.DivisionByZeroException;
import org.jlinalg.FieldElement;
import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.JLinAlgTypeProperties;
import org.jlinalg.RingElementFactory;

/**
 * This class represents an element of the modulo 2 field F2. F2 is a field,
 * just like the rational numbers are. At first glance it seems to be a rather
 * academic example, but prime fields (especially F2) have numerous applications
 * (e.g.: error correcting codes).
 * 
 * @author Andreas Keilhauer, Georg Thimm
 */
public class F2
		extends FieldElement<F2>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Builds an element in F2.
	 * <P>
	 * This should exclusively be used to create the instances {@link #ONE} and
	 * {@link #ZERO}. No internal value is needed as only these two instances
	 * are created and used.
	 */
	private F2()
	{
	}

	/**
	 * Calculates the sum of this element and another one.
	 * 
	 * @param val
	 *            a F2-value
	 * @return sum <=> logical XOR
	 * @exception InvalidOperationException
	 *                if val is not in {{@link #ONE},{@link #ZERO} .
	 */
	@Override
	public F2 add(F2 val)
	{
		if (!(val == ZERO || val == ONE))
			throw new InvalidOperationException("illegal argument " + val + ".");
		if (val == this) return ZERO;
		return ONE;
	}

	/**
	 * Calculates the difference between this element and another one.
	 * 
	 * @param val
	 *            a F2-value
	 * @return difference <=> logical XOR
	 * @exception InvalidOperationException
	 *                if val is not in {{@link #ONE},{@link #ZERO} .
	 */
	@Override
	public F2 subtract(F2 val)
	{
		return add(val);
	}

	/**
	 * Calculates the product of this element and another one.
	 * 
	 * @param val
	 * @return product <=> logical AND
	 * @exception InvalidOperationException
	 *                if val is not in {{@link #ONE},{@link #ZERO} .
	 */
	@Override
	public F2 multiply(F2 val)
	{
		if (val == ONE && this == ONE) return ONE;
		return ZERO;
	}

	/**
	 * Calculates the quotient of this FieldElement and another one.
	 * 
	 * @param val
	 * @return quotient <=> this value if val = 1m2 and undefined (Exception)
	 *         otherwise
	 * @exception InvalidOperationException
	 *                if val == 0m2
	 * @exception InvalidOperationException
	 *                if val is not in {{@link #ONE},{@link #ZERO} .
	 */
	@Override
	public F2 divide(F2 val)
	{
		if (!(val == ZERO || val == ONE))
			throw new InvalidOperationException("illegal argument " + val + ".");
		if (val == ONE) {
			return this;
		}
		throw new InvalidOperationException("Division by 0m2");
	}

	/**
	 * Calculates the inverse element of addition for this element. This is
	 * incidentally for F2 <code>this</code>.
	 * 
	 * @return negated <=> this value
	 */
	@Override
	public F2 negate()
	{
		return this;
	}

	/**
	 * Calculates the inverse element of multiplication for this element.
	 * 
	 * @return ONE if <code>this=={@link #ONE}</code> .
	 * @throws DivisionByZeroException
	 *             if <code>this=={@link #ZERO}</code> .
	 */
	@Override
	public F2 invert() throws DivisionByZeroException
	{
		if (this == ZERO) {
			throw new DivisionByZeroException("Tried to invert zero.");
		}
		return ONE;
	}

	/**
	 * Checks two elements for equality.
	 * 
	 * @param val
	 * @return true if the two FieldElements are mathematically equal.
	 */
	@Override
	public boolean equals(Object val)
	{
		// it is sufficient to examine the objects for identity as only instance
		// for each value can exists
		return this == val;
	}

	/**
	 * Returns a String representation of this element.
	 * 
	 * @return <code>0m2</code> or <code>1m2</code>.
	 */
	@Override
	public String toString()
	{
		if (this == ZERO) return "0m2";
		return "1m2";
	}

	/**
	 * Implements Comparable.compareTo(Object).
	 * 
	 * @param val
	 *            the object
	 * @return one of {-1,+,0} as this object is less than, equal to, or greater
	 *         than the specified object.
	 * @exception InvalidOperationException
	 *                if val is not in {{@link #ONE},{@link #ZERO}.
	 */
	@Override
	public int compareTo(F2 val)
	{
		if (!(val == ZERO || val == ONE))
			throw new InvalidOperationException("illegal argument " + val + ".");
		if (this == val) return 0;
		if (this == ZERO) return -1;
		return 1;
	}

	/**
	 * @return 0 or 1.
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return this == ZERO ? 0 : 1;
	}

	/**
	 * @return a reference to the singleton factory for this type.
	 */
	@Override
	public IRingElementFactory<F2> getFactory()
	{
		return FACTORY;
	}

	/**
	 * the zero-instance for F2
	 */
	public static final F2 ZERO = new F2();

	/**
	 * the one-instance for F2
	 */
	public static final F2 ONE = new F2();

	/**
	 * The instance of the singleton factory.
	 */
	public static F2Factory FACTORY = ZERO.new F2Factory();

	/**
	 * The factory class for the F2 data type. Only one instance of this factory
	 * should ever exist.
	 */
	@JLinAlgTypeProperties(isExact = true, isDiscreet = true, hasNegativeValues = false)
	public class F2Factory
			extends RingElementFactory<F2>
	{
		/**
		 * create an array to hold <code>size</code> F2-instances.
		 */
		@Override
		public F2[] getArray(int size)
		{
			return new F2[size];
		}

		/**
		 * @exception InvalidOperationException
		 *                F2-elements can only be obtained for <code>int</code>
		 *                or <code>long</code> values.
		 */
		@Override
		public F2 get(Object o)
		{
			if (o instanceof String) {
				String s = (String) o;
				if ("0m2".equalsIgnoreCase(s)) return ZERO;
				if ("1m2".equalsIgnoreCase(s)) return ONE;
				try {
					int i = Integer.parseInt(s);
					return get(i);
				} catch (NumberFormatException e) {
					throw new InvalidOperationException(s
							+ " can not be translated into a F2 element.");
				}
			}
			if (o instanceof Integer) {
				return get(((Integer) o).intValue() & 1);
			}
			if (o instanceof Long) {
				return get(((Long) o).intValue() & 1);
			}
			// last resort...
			return get(o.toString());
		}

		/**
		 * @return {@link #ZERO} if <code>i%2==0</code> and {@link #ONE}
		 *         otherwise
		 */
		@Override
		public F2 get(int i)
		{
			if (i % 2 == 0) return ZERO;
			return ONE;
		}

		/**
		 * @exception InvalidOperationException
		 *                F2-elements can only be obtained for <code>int</code>
		 *                or <code>long</code> values.
		 */
		@Override
		public F2 get(double d)
		{
			throw new InvalidOperationException(
					"Cannot instanciate an F2--element from " + d);
		}

		/**
		 * @return {@link #ONE} as -1 and 1 are identical.
		 */
		@Override
		public F2 m_one()
		{
			return ONE;
		}

		/**
		 * @return {@link #ONE}
		 */
		@Override
		public F2 one()
		{
			return ONE;
		}

		/**
		 * @return {@link #ZERO} as -1 and 1 are identical.
		 */
		@Override
		public F2 zero()
		{
			return ZERO;
		}

		/**
		 * @return the same as {@link #randomValue()}
		 */
		@SuppressWarnings("deprecation")
		@Override
		@Deprecated
		public F2 gaussianRandomValue(@SuppressWarnings("unused") Random random)
		{
			return randomValue();
		}

		/**
		 * create an array to hold <code>rows</code> time <code>columns</code>
		 * F2-instances.
		 */
		@Override
		public F2[][] getArray(int rows, int columns)
		{
			return new F2[rows][columns];
		}

		/**
		 * @return the same as {@link #randomValue()}
		 */
		@SuppressWarnings("deprecation")
		@Override
		@Deprecated
		public F2 randomValue(@SuppressWarnings("unused") Random random)
		{
			return randomValue();
		}

		/**
		 * @return {@link #ZERO} if <code>i%2==0</code> and {@link #ONE}
		 *         otherwise
		 * @see org.jlinalg.IRingElementFactory#get(long)
		 */
		@Override
		public F2 get(long d)
		{
			return (d % 2L) == 0 ? ZERO : ONE;
		}

		/**
		 * All parameters are ignored.
		 * 
		 * @see org.jlinalg.IRingElementFactory#randomValue(Random,
		 *      IRingElement, IRingElement)
		 */
		@SuppressWarnings("deprecation")
		@Override
		@Deprecated
		public F2 randomValue(@SuppressWarnings("unused") Random random,
				@SuppressWarnings("unused") F2 min,
				@SuppressWarnings("unused") F2 max)
		{
			return randomValue();
		}

		/**
		 * @return the same as {@link #randomValue()}
		 */
		@Override
		@Deprecated
		public F2 gaussianRandomValue()
		{
			return randomValue();
		}

		/**
		 * @return the same as {@link #randomValue()}. All parameters are
		 *         ignored.
		 */
		@Override
		@Deprecated
		public F2 randomValue(@SuppressWarnings("unused") F2 min,
				@SuppressWarnings("unused") F2 max)
		{
			return randomValue();
		}

		/**
		 * @return 0 or 1 with equal probability.
		 * @see org.jlinalg.IRingElementFactory#randomValue()
		 */
		@Override
		public F2 randomValue()
		{
			return random.nextBoolean() ? ONE : ZERO;
		}

	}

	/**
	 * @return {@code this}
	 * @see org.jlinalg.IRingElement#abs()
	 */
	@Override
	public F2 abs()
	{
		return this;
	}

}
