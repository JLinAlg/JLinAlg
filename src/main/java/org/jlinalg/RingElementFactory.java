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
package org.jlinalg;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

/**
 * This defines the interface for the factories used to create instances (and
 * possibly hold a cache of) IRingElements and to create arrays of such
 * elements. The later is in particular necessary, as generic classes cannot
 * create arrays of the elements they are used for.
 * <p>
 * Most IRingElements would hold an instance of a factory in a static field and
 * use this reference.
 * <p>
 * 
 * @author Georg Thimm (2008)
 * @param <RE>
 *            The type of the values the factory produces.
 */
public abstract class RingElementFactory<RE extends RingElement<RE>>
		implements IRingElementFactory<RE>
{

	private static final long serialVersionUID = 1L;

	/**
	 * @return a description of the factory
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Factory: " + getClass().getName();
	}

	@Override
	public abstract RE[] getArray(int size);

	@Override
	public abstract RE[][] getArray(int rows, int columns);

	@Override
	public abstract RE one();

	@Override
	public abstract RE zero();

	@Override
	public abstract RE m_one();

	/**
	 * Try to use the string representation of the object <code>o</code> to
	 * create a object of type <RE>. This calls again {@link #get(Object)}).
	 * 
	 * @see org.jlinalg.IRingElementFactory#get(java.lang.Object)
	 */
	@Override
	public RE get(Object o)
	{
		if (!(o instanceof String)) {
			try {
				java.lang.reflect.Method method = o.getClass()
						.getDeclaredMethod("toString");
				if (method.getReturnType().equals(String.class)) {
					String s = (String) method.invoke(o);
					return get(s);
				}
			} catch (SecurityException e1) {
				throw new InternalError("SecurityException " + e1.getMessage());
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

		throw new InvalidOperationException("cannot convert " + o
				+ " in factory " + getClass().getCanonicalName());
	}

	@Override
	public abstract RE get(int i);

	@Override
	public abstract RE get(double d);

	/**
	 * @deprecated
	 * @deprecated use {@link #gaussianRandomValue()}
	 */
	@Override
	@SuppressWarnings("deprecation")
	@Deprecated
	public abstract RE gaussianRandomValue(Random random);

	/**
	 * @deprecated use {@link #randomValue()}
	 */
	@Override
	@SuppressWarnings("deprecation")
	@Deprecated
	public abstract RE randomValue(Random random);

	/**
	 * @param min
	 *            the minimum value to be generated
	 * @param max
	 *            the minimum value to be generated
	 * @return a random value inclusive of min, and exclusive of max for
	 *         continous domains and inclusive for others.
	 */
	@Override
	public abstract RE randomValue(RE min, RE max);

	/**
	 * @deprecated use {@link #randomValue(IRingElement, IRingElement)}
	 */
	@SuppressWarnings("deprecation")
	@Deprecated
	@Override
	public abstract RE randomValue(Random random, RE min, RE max);

	/**
	 * convert matrices
	 * 
	 * @param from
	 *            the matrix to be converted
	 * @return a matrix of type <RE>
	 */
	@Override
	public Matrix<RE> convert(final Matrix<? extends IRingElement<?>> from)
	{
		Matrix<RE> to = new Matrix<RE>(from.getRows(), from.getCols(), this);
		for (int row = 0; row < from.getRows(); row++) {
			for (int col = 0; col < from.getCols(); col++) {
				to.entries[row][col] = this.get(from.entries[row][col]);
			}
		}
		return to;
	}

	/**
	 * convert vectors
	 * 
	 * @param from
	 *            the vector to be converted
	 * @return a vector of type <RE>
	 */
	@Override
	public Vector<RE> convert(final Vector<? extends IRingElement<?>> from)
	{
		Vector<RE> to = new Vector<RE>(from.length(), this);
		for (int row = 0; row < from.length(); row++) {
			to.entries[row] = this.get(from.entries[row]);
		}
		return to;
	}
}
