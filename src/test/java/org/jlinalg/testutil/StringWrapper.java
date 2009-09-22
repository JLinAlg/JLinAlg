/**
 * This essentially a dummy class to store matrices as Strings. It aims at
 * easily using the same matrices of Strings to create otther types using the
 * respective matrices
 * 
 * @author Georg Thimm
 */
package org.jlinalg.testutil;

import java.util.Random;

import org.jlinalg.DivisionByZeroException;
import org.jlinalg.FieldElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.JLinAlgTypeProperties;
import org.jlinalg.RingElementFactory;

public class StringWrapper
		extends FieldElement<StringWrapper>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the value of this wrapper
	 */
	private String value;

	final public static StringWrapper ONE = new StringWrapper("1");

	final public static StringWrapper M_ONE = new StringWrapper("-1");

	final public static StringWrapper ZERO = new StringWrapper("0");

	final public static StringWrapperFactory FACTORY = ZERO.new StringWrapperFactory();

	/**
	 * @param v
	 *            the value for the new wrapper.
	 */
	private StringWrapper(String v)
	{
		value = v;
	}

	@Override
	public StringWrapper add(@SuppressWarnings("unused") StringWrapper other)
	{
		throw new InvalidOperationException("This cannot be done for strings.");
	}

	@Override
	public int compareTo(StringWrapper o)
	{
		if (o != null) {
			return value.compareTo(o.value);
		}
		throw new InvalidOperationException("cannot compare with null");
	}

	@Override
	public IRingElementFactory<StringWrapper> getFactory()
	{
		return FACTORY;
	}

	@Override
	public StringWrapper invert() throws DivisionByZeroException
	{
		throw new InvalidOperationException("This cannot be done for strings.");
	}

	@Override
	public StringWrapper multiply(
			@SuppressWarnings("unused") StringWrapper other)
	{
		throw new InvalidOperationException("This cannot be done for strings.");
	}

	@Override
	public StringWrapper negate()
	{
		throw new InvalidOperationException("This cannot be done for strings.");
	}

	@Override
	public StringWrapper abs()
	{
		throw new InvalidOperationException("This cannot be done for strings.");
	}

	/**
	 * @return the encapsulated String.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return value;
	}

	/**
	 * The factory for StringWrapper
	 * 
	 * @author Georg Thimm
	 */
	@JLinAlgTypeProperties()
	public class StringWrapperFactory
			extends RingElementFactory<StringWrapper>
	{

		@SuppressWarnings("deprecation")
		@Override
		public StringWrapper gaussianRandomValue(
				@SuppressWarnings("unused") Random random)
		{
			throw new InvalidOperationException(
					"This cannot be done for strings.");
		}

		@Override
		public StringWrapper get(Object o)
		{
			if (o instanceof String) {
				return new StringWrapper((String) o);
			}
			return new StringWrapper(o.toString());
		}

		@Override
		public StringWrapper get(int i)
		{
			return new StringWrapper(Integer.toString(i));
		}

		@Override
		public StringWrapper get(double d)
		{
			return new StringWrapper(Double.toString(d));
		}

		@Override
		public StringWrapper[] getArray(int size)
		{
			return new StringWrapper[size];
		}

		@Override
		public StringWrapper[][] getArray(int rows, int columns)
		{
			return new StringWrapper[rows][columns];
		}

		@Override
		public StringWrapper m_one()
		{
			return M_ONE;
		}

		@Override
		public StringWrapper one()
		{
			return ONE;
		}

		@SuppressWarnings("deprecation")
		@Override
		public StringWrapper randomValue(
				@SuppressWarnings("unused") Random random)
		{
			throw new InvalidOperationException(
					"This cannot be done for strings.");
		}

		@SuppressWarnings("deprecation")
		@Override
		public StringWrapper randomValue(
				@SuppressWarnings("unused") Random random,
				@SuppressWarnings("unused") StringWrapper min,
				@SuppressWarnings("unused") StringWrapper max)
		{
			throw new InvalidOperationException(
					"This cannot be done for strings.");
		}

		@Override
		public StringWrapper zero()
		{
			return ZERO;
		}

		@Override
		public StringWrapper gaussianRandomValue()
		{
			throw new InvalidOperationException(
					"This cannot be done for strings.");
		}

		@Override
		public StringWrapper get(long d)
		{
			return new StringWrapper(Long.toString(d));
		}

		@Override
		public StringWrapper randomValue(
				@SuppressWarnings("unused") StringWrapper min,
				@SuppressWarnings("unused") StringWrapper max)
		{
			throw new InvalidOperationException(
					"This cannot be done for strings.");
		}

		@Override
		public StringWrapper randomValue()
		{
			throw new InvalidOperationException(
					"This cannot be done for strings.");
		}
	}
}
