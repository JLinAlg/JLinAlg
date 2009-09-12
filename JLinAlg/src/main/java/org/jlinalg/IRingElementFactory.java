package org.jlinalg;

import java.util.Random;

/**
 * The interface defining the factory for ring elements.
 * 
 * @author Georg Thimm
 * @param <RE>
 *            the type of the ring elements to be creates
 */
public interface IRingElementFactory<RE extends IRingElement>
{

	/**
	 * This is to allow the class {@link Vector} to create arrays with elements
	 * of type RE
	 * 
	 * @param size
	 *            the size of the array to be created
	 * @return a 1-d array
	 */
	public abstract RE[] getArray(int size);

	/**
	 * This is to allow the class {@link Vector} to create 2-dimensional arrays
	 * with elements of type RE
	 * 
	 * @param rows
	 * @param columns
	 * @return a 2-d array
	 */
	public abstract RE[][] getArray(int rows, int columns);

	/**
	 * Provide a one.
	 * 
	 * @return a "one" of type <code>RE</code>.
	 */
	public abstract RE one();

	/**
	 * Provide a zero.
	 * 
	 * @return a "zero" of type <code>RE</code>.
	 */
	public abstract RE zero();

	/**
	 * Provide a minus one.
	 * 
	 * @return a "-1" of type <code>RE</code>.
	 */
	public abstract RE m_one();

	/**
	 * create elements from another data type.
	 * 
	 * @param o
	 *            some object type
	 * @return the corresponding object of type <code>RE</code>
	 * @throws InvalidOperationException
	 *             if <code>o</code> cannot be converted into type <RE>
	 */
	public abstract RE get(Object o);

	/**
	 * create elements from integers
	 * 
	 * @param i
	 *            an integer
	 * @return the corresponding object of type <code>RE</code>
	 */
	public abstract RE get(int i);

	/**
	 * create elements from longs
	 * 
	 * @param d
	 *            an integer
	 * @return the corresponding object of type <code>RE</code>
	 */
	public abstract RE get(long d);

	/**
	 * create elements from doubles
	 * 
	 * @param d
	 *            a double
	 * @return the corresponding object of type <code>RE</code>
	 */
	public abstract RE get(double d);

	/**
	 * Returns a random value RE with distribution N(0, 1) <BR>
	 * <STRONG>Note!</STRONG> Normal Distribution Issue<BR>
	 * For the following RingElement types this method won't achieve the correct
	 * distribution: F2, FieldP
	 * 
	 * @param random
	 * @return random value RE
	 */
	@Deprecated
	public abstract RE gaussianRandomValue(Random random);

	/**
	 * Returns a uniformly distributed random value. Refer to the documentation
	 * of the LinAlgFactory's type for further details.
	 * 
	 * @param random
	 * @return random value RE
	 */
	@Deprecated
	public abstract RE randomValue(Random random);

	/**
	 * @param min
	 * @param max
	 * @return a uniformly distributed value in the range of [min,max]
	 */
	public abstract RE randomValue(IRingElement min, IRingElement max);

	/**
	 * Returns a random value RE with distribution N(0, 1) <BR>
	 * <STRONG>Note!</STRONG> Normal Distribution Issue<BR>
	 * For the following RingElement types this method won't achieve the correct
	 * distribution: F2, FieldP
	 * 
	 * @return random value RE
	 */
	public abstract RE gaussianRandomValue();

	/**
	 * Returns a uniformly distributed random value. Refer to the documentation
	 * of the LinAlgFactory's type for further details.
	 * 
	 * @return random value RE
	 */
	public abstract RE randomValue();

	/**
	 * @param random
	 * @param min
	 * @param max
	 * @return a uniformly distributed value in the range of [min,max]
	 */
	@Deprecated
	public abstract RE randomValue(Random random, IRingElement min,
			IRingElement max);

	/**
	 * convert vectors
	 * 
	 * @param from
	 *            the vector to be converted
	 * @return a vector of type <RE>
	 */
	public <ARG extends IRingElement> Vector<RE> convert(final Vector<ARG> from);

	/**
	 * convert matrices
	 * 
	 * @param from
	 *            the matrix to be converted
	 * @return a matrix of type <RE>
	 */
	public <ARG extends IRingElement> Matrix<RE> convert(final Matrix<ARG> from);

	/**
	 * The default random number generator for use in .
	 */
	static Random random = new Random();
}