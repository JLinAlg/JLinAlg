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

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.JLinAlgTypeProperties;
import org.jlinalg.RingElementFactory;
import org.jlinalg.rational.Rational;

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
	public static final DoubleWrapperFactory INSTANCE = new DoubleWrapperFactory();
	private static final long serialVersionUID = 1L;

	/**
	 * the constant -1
	 */
	static final DoubleWrapper M_ONE = new DoubleWrapper(-1);

	/**
	 * the constant 1
	 */
	static final DoubleWrapper ONE = new DoubleWrapper(1);

	/**
	 * the constant 0
	 */
	static final DoubleWrapper ZERO = new DoubleWrapper(0);

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
				Matcher m = DoubleWrapper.fractionPattern.matcher(s);

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
				if (method.getReturnType().getClass().equals(Double.class)) {
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