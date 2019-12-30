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
package org.jlinalg.fastrational;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jlinalg.DivisionByZeroException;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.JLinAlgTypeProperties;
import org.jlinalg.RingElementFactory;
import org.jlinalg.doublewrapper.DoubleWrapper;
import org.jlinalg.rational.Rational;

/**
 * An implementation of rational numbers based on long numbers in order to
 * improve the memory footprint and calculation time as compared to
 * {@link org.jlinalg.rational.Rational}.
 * <P>
 * This, however, comes at a price: if the numerator or denominator leaves the
 * domain of long-numbers, results will be wrong (no test for overflows is
 * performed).
 * 
 * @author Georg Thimm
 */
@JLinAlgTypeProperties(hasNegativeValues = true, isCompound = false, isDiscreet = false, isExact = true)
public class FastRationalFactory
		extends
		RingElementFactory<FastRational>
{

	private static final long serialVersionUID = 1L;

	final public static FastRational M_ONE = new FastRational(-1L, 1L, false);

	final public static FastRational ONE = new FastRational(1L, 1L, false);

	final public static FastRational ZERO = new FastRational(0L, 1L, false);

	final public static FastRational UNKNOWN = new FastRational(1);

	final public static FastRational NOTANUMBER = new FastRational(2);

	@Override
	public FastRational get(final long n)
	{
		if (n == 0L) return ZERO;
		if (n == 1L) return ONE;
		if (n == -1L) return M_ONE;
		return new FastRational(n, 1, false);
	}

	public final static FastRationalFactory INSTANCE = new FastRationalFactory();

	public FastRational get(final long n, final long d)
	{
		return get(n, d, true);
	}

	public FastRational get(long numerator, long denominator, boolean cancel)
	{
		if (denominator == 0) throw new DivisionByZeroException(
				"illegal denominator " + numerator + "/" + denominator);
		if (numerator == 0) return ZERO;
		if (cancel) {
			long cancelledBy = FastRational.gcd(denominator, numerator);
			denominator = denominator / cancelledBy;
			numerator = numerator / cancelledBy;
		}
		if (denominator < 0L) {
			denominator = -denominator;
			numerator = -numerator;
		}
		if (denominator == 1L) {
			if (numerator == 1L) return ONE;
			if (numerator == -1) return M_ONE;
		}
		return new FastRational(numerator, denominator, false);
	}

	/**
	 * regular expression for fractions. Used in {@link #get(Object)}.
	 */
	Pattern fraction = Pattern
			.compile("\\A(-?\\d{1,8})(?:/([-+]?\\d{1,8}))?\\z");

	Pattern doublePattern = Pattern
			.compile("([+-]?\\d+)\\.?(\\d*)([eE]([-+]?\\d+))?");

	@Override
	public FastRational get(int i)
	{
		if (i == 0) return ZERO;
		if (i == 1) return ONE;
		if (i == -1) return M_ONE;
		return new FastRational(i, 1, false);
	}

	@Override
	public FastRational get(double d)
	{
		if (d == 0.0) return ZERO;
		if (d == 1.0) return ONE;
		if (d == -1.0) return M_ONE;
		return new FastRational(d);
	}

	@Override
	public FastRational[] getArray(int size)
	{
		return new FastRational[size];
	}

	@Override
	public FastRational[][] getArray(int rows, int columns)
	{
		return new FastRational[rows][columns];
	}

	@Override
	public FastRational m_one()
	{
		return M_ONE;
	}

	@Override
	public FastRational one()
	{
		return ONE;
	}

	/**
	 * @exception InvalidOperationException
	 * @deprecated as unimplemented
	 */
	@Deprecated
	@Override
	public FastRational randomValue(
			@SuppressWarnings("unused") FastRational min,
			@SuppressWarnings("unused") FastRational max)
	{
		throw new InvalidOperationException("Not implemented");
	}

	@Override
	public FastRational zero()
	{
		return ZERO;
	}

	/**
	 * Not implemented.
	 * 
	 * @exception InvalidOperationException
	 */
	@Deprecated
	@Override
	public FastRational gaussianRandomValue()
	{
		throw new InvalidOperationException("Not implemented");
	}

	/**
	 * @return a fraction with random numerator and denominator
	 */
	@Override
	public FastRational randomValue()
	{
		long denominator = random.nextLong();
		while (denominator == 0L)
			denominator = random.nextLong();
		return get(random.nextLong(), denominator, true);
	}

	/**
	 * Translate other objects into {@code FastRational}s. Objects in the
	 * following
	 * classes are permissible:
	 * <UL>
	 * <li>those extending on {@link java.lang.Number},
	 * <li>{@link Rational},
	 * <li>{@link DoubleWrapper}
	 * <li>{@link String} in the form of integers, exponential or fractional
	 * representation.
	 * </UL>
	 * If the argument is a FastRational, it is returned. Should the given
	 * object exceed the range of long numbers, the result is silently
	 * truncated.
	 * 
	 * @param object
	 * @exception InvalidOperationException
	 *                if {@code object} is not in one of the given classes and
	 *                {@code object.toString()} is in an unknown format.
	 */
	@Override
	public FastRational get(Object object)
	{
		if (object instanceof FastRational) {
			return (FastRational) object;
		}
		if (object instanceof Double) {
			return get(((Double) object).doubleValue());
		}
		if (object instanceof DoubleWrapper) {
			return get(((DoubleWrapper) object).doubleValue());
		}
		if (object instanceof Number) {
			return get(((Number) object).longValue());
		}
		if (object instanceof Rational) {
			Rational r = (Rational) object;
			return new FastRational(r.getNumerator().longValue(),
					r.getDenominator().longValue(), false);
		}
		if (object instanceof CharSequence) {
			if ("".equals(object)) return null;
			Matcher m = fraction.matcher((CharSequence) object);
			if (m.matches()) {
				if (m.group(2) == null || m.group(2).length() == 0)
					return FastRational.FACTORY.get(Long.parseLong(m.group(1)),
							1L, false);
				return FastRational.FACTORY.get(Long.parseLong(m.group(1)),
						Long.parseLong(m.group(2)), true);
			}
			m = doublePattern.matcher((CharSequence) object);
			if (m.matches()) return FastRationalFactory.INSTANCE
					.get(Double.parseDouble((String) object));

			throw new InvalidOperationException("String " + object
					+ " does not represent a valid fraction.");
		}
		// the dummy-case...
		if (object instanceof FastRational) {
			return (FastRational) object;
		}
		// and the last-resort try
		return get(object.toString());
	}

	/**
	 * @param power
	 * @return 10^power (it is assumed that power >=0);
	 */
	static long long10toPower(int power)
	{
		assert power >= 0;
		long res = 1L;
		while (power-- > 0) {
			res *= 10L;
		}
		return res;
	}

}
