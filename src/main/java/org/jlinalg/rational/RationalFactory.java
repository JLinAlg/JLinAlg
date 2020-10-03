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

package org.jlinalg.rational;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jlinalg.InvalidOperationException;
import org.jlinalg.JLinAlgTypeProperties;
import org.jlinalg.RingElementFactory;
import org.jlinalg.doublewrapper.DoubleWrapper;

/**
 * The factory for rationals
 * 
 * @author Georg Thimm
 */
@JLinAlgTypeProperties(isExact = true)
public class RationalFactory
		extends
		RingElementFactory<Rational>
{

	private static final long serialVersionUID = 1L;

	/**
	 * used in {@link #get(String)} to parse numbers in exponential
	 * denotation.
	 */
	private final static Pattern expPattern = Pattern
			.compile("([+-]?\\d+)\\.?(\\d*)([eE]([-+]?\\d+))?");

	/**
	 * used in {@link #get(String)} to parse numbers in fractional
	 * denotation.
	 */
	private final static Pattern fracPattern = Pattern
			.compile("([+-]?\\d+)/([-+]?\\d+)");

	/**
	 * this should only be used by {@link Rational} to create a single
	 * instance.
	 */
	RationalFactory()
	{
		super();
	}

	@Override
	public Rational[] getArray(int size)
	{
		return new Rational[size];
	}

	/**
	 * @return the singleton factory for rational numbers.
	 */
	public static RationalFactory getFactory()
	{
		return Rational.FACTORY;
	}

	/**
	 * Translate other objects into rationals. Objects in the following
	 * classes are permissible:
	 * <UL>
	 * <li>{@link Double}
	 * <li>{@link Integer}
	 * <li>{@link BigInteger}
	 * <li>{@link Rational}
	 * <li>{@link String} in the form of integers, exponential or fractional
	 * representation.
	 * </UL>
	 * If the argument is a rational, it is returned.
	 * 
	 * @exception InvalidOperationException
	 *                if the object is a String in an unknown format.
	 */
	@Override
	public Rational get(Object o)
	{
		if (o instanceof Double) {
			return get(((Double) o).doubleValue());
		}
		if (o instanceof DoubleWrapper) {
			return get(((DoubleWrapper) o).doubleValue());
		}
		if (o instanceof Integer) {
			return get(((Integer) o).intValue());
		}
		if (o instanceof BigInteger) {
			if (BigInteger.ONE.equals(o)) {
				return Rational.ONE;
			}
			if (BigInteger.ZERO.equals(o)) {
				return Rational.ZERO;
			}
			if (Rational.bigIntMOne.equals(o)) {
				return Rational.M_ONE;
			}
			return new Rational((BigInteger) o);
		}
		if (o instanceof String) {
			try {
				String number = (String) o;
				Matcher m = expPattern.matcher(number);
				BigInteger numerator;
				BigInteger denominator;
				if (m.matches()) {
					;
					// parse exponential writing.
					int offset = m.group(2).length();
					int power = 0;
					if (m.group(3) != null)
						power = Integer.parseInt(m.group(4)) - offset;
					BigInteger mantissa = new BigInteger(
							m.group(1).concat(m.group(2)));
					if (power > 0) {
						// exponent is positive
						numerator = mantissa
								.multiply(BigInteger.TEN.pow(power));
						denominator = BigInteger.ONE;
						return get(numerator, denominator, false);
					}
					// exponent is negative.
					numerator = mantissa;
					denominator = BigInteger.TEN.pow(-power);
					return get(numerator, denominator, true);

				}
				// consider fractional notation
				m = fracPattern.matcher(number);
				if (m.matches()) {
					numerator = new BigInteger(m.group(1));
					denominator = new BigInteger(m.group(2));
					return get(numerator, denominator, true);
				}
				// at last, try whether this is a plain integer
				BigInteger i = new BigInteger(number);
				numerator = i;
				denominator = BigInteger.ONE;
			} catch (NumberFormatException e) {
				throw new InvalidOperationException(o
						+ " does not represent a rational number (exception was: "
						+ e.getMessage() + ")");
			}
		}
		// the dummy-case...
		if (o instanceof Rational) {
			return (Rational) o;
		}
		// and the last-resort try
		return get(o.toString());
	}

	/**
	 * Returns the zero element for this field.
	 * 
	 * @return zero
	 */
	@Override
	public Rational zero()
	{
		return Rational.ZERO;
	}

	/**
	 * Returns the one element for this field: 1/1
	 * 
	 * @return one
	 */
	@Override
	public Rational one()
	{
		return Rational.ONE;
	}

	/**
	 * Returns the minus one for this field: -1/1
	 */
	@Override
	public Rational m_one()
	{
		return Rational.M_ONE;
	}

	/**
	 * create rationals from integers.
	 */
	@Override
	public Rational get(int i)
	{
		if (i == 0) return Rational.ZERO;
		if (i == 1) return Rational.ONE;
		if (i == -1) return Rational.M_ONE;
		return new Rational(i);
	}

	@Override
	public Rational get(double d)
	{
		if (d == 0) return Rational.ZERO;
		if (d == 1.0) return Rational.ONE;
		if (d == -1.0) return Rational.M_ONE;
		return new Rational(d);
	}

	@Override
	public Rational[][] getArray(int rows, int columns)
	{
		return new Rational[rows][columns];
	}

	@Override
	public Rational get(long i)
	{
		if (i == 0) return Rational.ZERO;
		if (i == 1) return Rational.ONE;
		if (i == -1) return Rational.M_ONE;
		return new Rational(i);
	}

	/**
	 * @param n
	 * @param d
	 * @return a rational with value (n/d)
	 * @see org.jlinalg.IRingElementFactory#get(long)
	 */
	public Rational get(long n, long d)
	{
		if (n == 0) return Rational.ZERO;
		if (n == 1 && d == 1) return Rational.ONE;
		if (n == -1 && d == 1) return Rational.M_ONE;
		return new Rational(n, d);
	}

	/**
	 * @param n
	 *            the nominator
	 * @param d
	 *            the denominator
	 * @param b
	 *            if true, cancel the fraction
	 * @return a rational number
	 */
	public Rational get(BigInteger n, BigInteger d, boolean b)
	{
		if (n.equals(BigInteger.ZERO)) return Rational.ZERO;
		if (n.equals(BigInteger.ONE) && d.equals(BigInteger.ONE))
			return Rational.ONE;
		if (n.equals(BigInteger.ONE) && d.equals(Rational.bigIntMOne))
			return Rational.M_ONE;
		return new Rational(n, d, b);
	}

	/**
	 * @see org.jlinalg.IRingElementFactory#gaussianRandomValue()
	 */
	@Override
	public Rational gaussianRandomValue()
	{
		return get(random.nextGaussian());
	}

	@Override
	public Rational randomValue(Rational min, Rational max)
	{
		return get(randomValue().multiply(max.subtract(min))).add(min);
	}

	@Override
	public Rational randomValue()
	{
		return get(random.nextDouble());
	}
}