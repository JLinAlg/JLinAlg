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
package org.jlinalg.bigdecimalwrapper;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.regex.Matcher;

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.JLinAlgTypeProperties;
import org.jlinalg.RingElementFactory;
import org.jlinalg.rational.Rational;

/**
 * The factory for instances of class {@link BigDecimalWrapper}
 * 
 * @author Georg Thimm
 */
@JLinAlgTypeProperties(isExact = false, isDiscreet = false)
public class BigDecimalWrapperFactory
		extends
		RingElementFactory<BigDecimalWrapper>
		implements
		IRingElementFactory<BigDecimalWrapper>
{
	// ===============================================================
	// Constants for BigDecimalWrapperFactory

	private static final long serialVersionUID = 1L;

	/**
	 * the constant -1
	 */
	final BigDecimalWrapper M_ONE;

	/**
	 * the constant 1
	 */
	final BigDecimalWrapper ONE;

	/**
	 * the constant 0
	 */
	final BigDecimalWrapper ZERO;

	/**
	 * The MathContext use to create new instances of {@link BigDecimalWrapper}
	 */
	final MathContext mathContext;

	/**
	 * only used (once per precision to create a java.math.MathContext, see also
	 * java.math.BigDecimal) to instantiate {@link BigDecimalWrapper#FACTORY}
	 * 
	 * @param precision
	 *            the number of digits used.
	 */
	@JLinAlgTypeProperties(hasNegativeValues = true, isDiscreet = false, isExact = false)
	public BigDecimalWrapperFactory(int precision)
	{
		super();
		mathContext = new MathContext(precision);
		ZERO = new BigDecimalWrapper(new BigDecimal(0, mathContext), this);
		ONE = new BigDecimalWrapper(new BigDecimal(1, mathContext), this);
		M_ONE = new BigDecimalWrapper(new BigDecimal(-1, mathContext), this);
	}

	@Override
	public BigDecimalWrapper get(double d)
	{
		return new BigDecimalWrapper(BigDecimal.valueOf(d).round(mathContext),
				this);
	}

	@Override
	public BigDecimalWrapper get(int i)
	{
		return new BigDecimalWrapper(new BigDecimal(i, mathContext), this);
	}

	@Override
	public BigDecimalWrapper get(Object o)
	{
		if (o instanceof BigDecimalWrapper) {
			BigDecimalWrapper d = (BigDecimalWrapper) o;
			if (d.getFactory() == this) return d;
			if (d.value.precision() > mathContext.getPrecision())
				return new BigDecimalWrapper(d.value.round(mathContext), this);
			return new BigDecimalWrapper(d.value, this);
		}
		if (o instanceof String) {
			try {
				String s = (String) o;
				Matcher fracMatch = BigDecimalWrapper.fractionPattern
						.matcher(s);
				if (fracMatch.matches()) {
					return get(new BigDecimal(fracMatch.group(1)).divide(
							new BigDecimal(fracMatch.group(2)), mathContext));
				}
				return new BigDecimalWrapper(new BigDecimal(s, mathContext),
						this);
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
						.equals(BigDecimal.class))
				{
					BigDecimal d = (BigDecimal) method.invoke(e);
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
	public BigDecimalWrapper[][] getArray(int rows, int columns)
	{
		return new BigDecimalWrapper[rows][columns];
	}

	@Override
	public BigDecimalWrapper m_one()
	{
		return M_ONE;
	}

	@Override
	public BigDecimalWrapper one()
	{
		return ONE;
	}

	@Override
	public BigDecimalWrapper zero()
	{
		return ZERO;
	}

	@Override
	public BigDecimalWrapper[] getArray(int size)
	{
		return new BigDecimalWrapper[size];
	}

	@Override
	public BigDecimalWrapper get(long d)
	{
		return new BigDecimalWrapper(new BigDecimal(d, mathContext), this);
	}

	@Override
	public BigDecimalWrapper gaussianRandomValue()
	{
		return get(random.nextGaussian());
	}

	@Override
	public BigDecimalWrapper randomValue(BigDecimalWrapper min,
			BigDecimalWrapper max)
	{
		BigDecimalWrapper rand = get(random.nextDouble());
		BigDecimalWrapper interval = get(max.subtract(min));
		return interval.multiply(rand).add(min);
	}

	/**
	 * create a random value from {@link java.util.Random#nextDouble()}.
	 * 
	 * @see org.jlinalg.IRingElementFactory#randomValue()
	 */
	@Override
	public BigDecimalWrapper randomValue()
	{
		double d = random.nextDouble();
		return get(new BigDecimal(d));
	}

	/**
	 * @return the {@link MathContext} used to produce numbers in this factory.
	 */
	public MathContext getMathContext()
	{
		return mathContext;
	}

	/**
	 * @return a description of the factory
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Factory: " + getClass().getName() + " MathContext="
				+ mathContext.toString();
	}
}