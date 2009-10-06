package org.jlinalg.fastrational;

import java.util.Random;
import java.util.regex.Matcher;

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
		extends RingElementFactory<FastRational>
{
	final public static FastRational M_ONE = new FastRational(-1L, 1L, false);

	final public static FastRational ONE = new FastRational(1L, 1L, false);

	final public static FastRational ZERO = new FastRational(0L, 1L, false);

	public FastRational get(final long n)
	{
		if (n == 0L) return ZERO;
		if (n == 1L) return ONE;
		if (n == -1L) return M_ONE;
		return new FastRational(n, 1, false);
	}

	public FastRational get(final long n, final long d)
	{
		return get(n, d, true);
	}

	public FastRational get(long numerator, long denominator, boolean cancel)
	{
		if (denominator == 0)
			throw new DivisionByZeroException("illegal denominator "
					+ numerator + "/" + denominator);
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
	 * @exception InvalidOperationException
	 */
	@SuppressWarnings("deprecation")
	@Deprecated
	@Override
	public FastRational gaussianRandomValue(
			@SuppressWarnings("unused") Random random)
	{
		throw new InvalidOperationException("Not implemented");
	}

	@Override
	public FastRational get(int i)
	{
		if (i == 0) return ZERO;
		if (i == 1) return ONE;
		if (i == -1) return M_ONE;
		return new FastRational(i);
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
	 */
	@SuppressWarnings("deprecation")
	@Deprecated
	@Override
	public FastRational randomValue(@SuppressWarnings("unused") Random random)
	{
		throw new InvalidOperationException("Not implemented");
	}

	/**
	 * @exception InvalidOperationException
	 */
	@Deprecated
	@Override
	public FastRational randomValue(
			@SuppressWarnings("unused") FastRational min,
			@SuppressWarnings("unused") FastRational max)
	{
		throw new InvalidOperationException("Not implemented");
	}

	/**
	 * @exception InvalidOperationException
	 */
	@SuppressWarnings("deprecation")
	@Deprecated
	@Override
	public FastRational randomValue(@SuppressWarnings("unused") Random random,
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
	 * <li> {@link Rational},
	 * <li> {@link DoubleWrapper}
	 * <li> {@link String} in the form of integers, exponential or fractional
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
			return new FastRational(r.getNumerator().longValue(), r
					.getDenominator().longValue(), false);
		}
		if (object instanceof String) {
			try {
				String number = (String) object;
				Matcher m = Rational.expPattern.matcher(number);
				long numerator;
				long denominator;
				if (m.matches()) {
					// parse exponential writing.
					int offset = m.group(2).length();
					int power = 0;
					if (m.group(3) != null)
						power = Integer.parseInt(m.group(4)) - offset;
					long mantissa = Long.parseLong(m.group(1)
							.concat(m.group(2)));
					if (power > 0) {
						// exponent is positive
						numerator = mantissa * long10toPower(power);
						denominator = 1L;
						return get(numerator, denominator, false);
					}
					// exponent is negative.
					numerator = mantissa;
					denominator = long10toPower(-power);
					return get(numerator, denominator, true);

				}
				// consider fractional notation
				m = Rational.fracPattern.matcher(number);
				if (m.matches()) {
					numerator = Long.parseLong(m.group(1));
					denominator = Long.parseLong(m.group(2));
					return get(numerator, denominator, true);
				}
				// at last, try whether this is a plain integer

				numerator = Long.parseLong(number);
				denominator = 1L;
			} catch (NumberFormatException e) {
				throw new InvalidOperationException(
						object
								+ " does not represent a rational number (exception was: "
								+ e.getMessage() + ")");
			}
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
