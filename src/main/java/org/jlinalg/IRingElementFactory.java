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

import java.io.Serializable;
import java.util.Random;

import org.jlinalg.operator.DyadicOperator;
import org.jlinalg.operator.FEComparator;
import org.jlinalg.operator.MonadicOperator;

/**
 * The interface defining the factory for ring elements.
 * 
 * @author Georg Thimm
 * @param <RE>
 *            the type of the ring elements to be creates
 */
@JLinAlgTypeProperties(isExact = false)
public interface IRingElementFactory<RE extends IRingElement<RE>>
		extends
		Serializable
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
	 * @param min
	 * @param max
	 * @return a uniformly distributed value in the range of [min,max]
	 */
	public abstract RE randomValue(RE min, RE max);

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
	 * convert vectors to the type represented by this factory.
	 * 
	 * @param from
	 *            the vector to be converted
	 * @return a vector of type <RE>
	 */
	public Vector<RE> convert(final Vector<? extends IRingElement<?>> from);

	/**
	 * convert matrices
	 * 
	 * @param from
	 *            the matrix to be converted
	 * @return a matrix of type <RE>
	 */
	public Matrix<RE> convert(final Matrix<? extends IRingElement<?>> from);

	/**
	 * The default random number generator for use in .
	 */
	static Random random = new Random();

	public abstract DyadicOperator<RE> getSubtractOperator();

	public abstract DyadicOperator<RE> getAndOperator();

	public abstract DyadicOperator<RE> getDivideOperator();

	public abstract MonadicOperator<RE> getNotOperator();

	public abstract DyadicOperator<RE> getAddOperator();

	public abstract FEComparator<RE> getGreaterThanOrEqualToComparator();

	public abstract FEComparator<RE> getLessThanComparator();

	public abstract FEComparator<RE> getLessThanOrEqualToComparator();

	public abstract FEComparator<RE> getNotEqualToComparator();

	public abstract FEComparator<RE> getGreaterThanComparator();

	public abstract MonadicOperator<RE> getAbsOperator();

	public abstract MonadicOperator<RE> getSquareOperator();

	public abstract DyadicOperator<RE> getMultiplyOperator();

	public abstract FEComparator<RE> getEqualToComparator();

	public abstract DyadicOperator<RE> getOrOperator();
}
