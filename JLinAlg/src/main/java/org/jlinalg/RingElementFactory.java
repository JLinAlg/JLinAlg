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
public abstract class RingElementFactory<RE extends RingElement>
		implements IRingElementFactory<RE>
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.IRingElementFactory#getArray(int)
	 */
	public abstract RE[] getArray(int size);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.IRingElementFactory#getArray(int, int)
	 */
	public abstract RE[][] getArray(int rows, int columns);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.IRingElementFactory#one()
	 */
	public abstract RE one();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.IRingElementFactory#zero()
	 */
	public abstract RE zero();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.IRingElementFactory#m_one()
	 */
	public abstract RE m_one();

	/**
	 * Try to use the string representation of the object <code>o</code> to
	 * create a object of type <RE>. This calls again {@link #get(Object)}).
	 * 
	 * @see org.jlinalg.IRingElementFactory#get(java.lang.Object)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.IRingElementFactory#get(int)
	 */
	public abstract RE get(int i);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.IRingElementFactory#get(double)
	 */
	public abstract RE get(double d);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jlinalg.IRingElementFactory#gaussianRandomValue(java.util.Random)
	 */
	public abstract RE gaussianRandomValue(Random random);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.IRingElementFactory#randomValue(java.util.Random)
	 */
	public abstract RE randomValue(Random random);

	/**
	 * @param random
	 *            a random number generator
	 * @param min
	 *            the minimum value to be generated
	 * @param max
	 *            the minimum value to be generated
	 * @return a random value inclusive of min, and exclusive of max for
	 *         continous domains and inclusive for others.
	 */
	public abstract RE randomValue(Random random, IRingElement min,
			IRingElement max);

	/**
	 * convert matrices
	 * 
	 * @param from
	 *            the matrix to be converted
	 * @return a matrix of type <RE>
	 */
	public <ARG extends IRingElement> Matrix<RE> convert(final Matrix<ARG> from)
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
	public <ARG extends IRingElement> Vector<RE> convert(final Vector<ARG> from)
	{
		Vector<RE> to = new Vector<RE>(from.length(), this);
		for (int row = 0; row < from.length(); row++) {
			to.entries[row] = this.get(from.entries[row]);
		}
		return to;
	}
}
