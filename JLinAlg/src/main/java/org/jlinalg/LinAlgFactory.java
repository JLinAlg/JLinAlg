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

/**
 * This class provides a set of methods for generating various common types of
 * matrices and vectors for an arbitrary RingElement type.
 * 
 * @author Simon D. Levy, Andreas Keilhauer, Georg Thimm
 * @param <RE>
 *            the type of the elements in the vectors, matrices to be created.
 */

public class LinAlgFactory<RE extends IRingElement<RE>>
		implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The factory used to create elements.
	 */
	private IRingElementFactory<RE> factory;

	/**
	 * Creates a LinAlgFactory for a certain RingElement type.
	 * 
	 * @param iRingElementFactory
	 *            the factory for elements this LinAlgFactory creates matrices,
	 *            vectors, etc. for.
	 */
	public LinAlgFactory(IRingElementFactory<RE> iRingElementFactory)
	{
		this.factory = iRingElementFactory;
	}

	/**
	 * Returns a Matrix of all ones.
	 * 
	 * @param numberOfRows
	 * @param numberOfCols
	 * @return matrix of ones
	 */
	public Matrix<RE> ones(int numberOfRows, int numberOfCols)
	{
		return block_matrix(numberOfRows, numberOfCols, factory.one());
	}

	/**
	 * Returns a Matrix of all zeros.
	 * 
	 * @param numberOfRows
	 * @param numberOfCols
	 * @return matrix of zeros
	 */
	public Matrix<RE> zeros(int numberOfRows, int numberOfCols)
	{
		return block_matrix(numberOfRows, numberOfCols, factory.zero());
	}

	/**
	 * @deprecated this is a legacy front-end to {@link #uniformNoise(int,int)}
	 *             ( <code>random</code> is ignored).
	 */
	@Deprecated
	public Matrix<RE> uniformNoise(int numberOfRows, int numberOfCols,
			@SuppressWarnings("unused") Random random)
	{
		return uniformNoise(numberOfRows, numberOfCols);
	}

	/**
	 * Returns a Matrix of uniformly distributed random values. The kind of
	 * random values you get depends on the RingElement you use to initialize
	 * the LinAlgFactory
	 * 
	 * @param numberOfRows
	 * @param numberOfCols
	 * @return matrix of uniformly distributed random values
	 */
	public Matrix<RE> uniformNoise(int numberOfRows, int numberOfCols)
	{
		Matrix<RE> a = new Matrix<RE>(numberOfRows, numberOfCols, factory);
		for (int i = 1; i <= numberOfRows; ++i) {
			for (int j = 1; j <= numberOfCols; ++j) {
				a.set(i, j, factory.randomValue());
			}
		}
		return a;
	}

	/**
	 * @deprecated this is a legacy front-end to {@link #gaussianNoise(int,int)}
	 *             ( <code>random</code> is ignored).
	 */
	@Deprecated
	public Matrix<RE> gaussianNoise(int numberOfRows, int numberOfCols,
			@SuppressWarnings("unused") Random random)
	{
		return gaussianNoise(numberOfRows, numberOfCols);
	}

	/**
	 * Returns a Matrix of normally distributed random values. Values are taken
	 * from a Gaussian distribution with zero mean and standard deviation one.<BR>
	 * <STRONG>Note!</STRONG> Normal Distribution Issue<BR>
	 * For the following RingElement types this method won't achieve values of
	 * the correct distribution: F2, FieldP
	 * 
	 * @param numberOfRows
	 * @param numberOfCols
	 * @return Matrix of normally distributed random values
	 */
	public Matrix<RE> gaussianNoise(int numberOfRows, int numberOfCols)
	{
		Matrix<RE> a = new Matrix<RE>(numberOfRows, numberOfCols, factory);
		for (int i = 1; i <= numberOfRows; ++i) {
			for (int j = 1; j <= numberOfCols; ++j) {
				a.set(i, j, factory.gaussianRandomValue());
			}
		}
		return a;
	}

	/**
	 * Returns a Vector of all ones.
	 * 
	 * @param length
	 *            vector length
	 * @return vector of ones
	 */
	public Vector<RE> ones(int length)
	{
		return block_vector(length, factory.one());
	}

	/**
	 * Returns a Vector of all zeros.
	 * 
	 * @param length
	 *            vector length
	 * @return vector of zeros
	 */
	public Vector<RE> zeros(int length)
	{
		return block_vector(length, factory.zero());
	}

	/**
	 * @deprecated this is a legacy front-end to {@link #uniformNoise(int)} (
	 *             <code>random</code> is ignored).
	 */
	@Deprecated
	public Vector<RE> uniformNoise(int length,
			@SuppressWarnings("unused") Random random)
	{
		return uniformNoise(length);
	}

	/**
	 * Returns a Vector of uniformly distributed random values.
	 * 
	 * @param length
	 *            vector length
	 * @return vector of uniformly distributed random values
	 */
	public Vector<RE> uniformNoise(int length)
	{
		Vector<RE> v = new Vector<RE>(length, factory);
		for (int i = 1; i <= length; ++i) {
			v.set(i, factory.randomValue());
		}
		return v;
	}

	/**
	 * Returns a Vector of uniformly distributed random values.<BR>
	 * <STRONG>Note!</STRONG> Normal Distribution Issue<BR>
	 * For the following RingElement types this method won't achieve values of
	 * correct distribution: F2, FieldP
	 * 
	 * @param length
	 *            vector length
	 * @return vector of uniformly distributed random values
	 */
	public Vector<RE> gaussianNoise(int length)
	{
		Vector<RE> v = new Vector<RE>(length, factory);
		for (int i = 1; i <= length; ++i) {
			v.set(i, factory.gaussianRandomValue());
		}
		return v;
	}

	/**
	 * @deprecated this is a legacy front-end to {@link #gaussianNoise(int)} (
	 *             <code>random</code> is ignored).
	 */
	@Deprecated
	public Vector<RE> gaussianNoise(int length,
			@SuppressWarnings("unused") Random random)
	{
		return gaussianNoise(length);
	}

	/**
	 * Returns the identity Matrix. This is a square matrix with ones on its
	 * diagonal and zeros elsewhere.
	 * 
	 * @param size
	 *            number of rows (= number of columns)
	 * @return identity matrix
	 */
	public Matrix<RE> identity(int size)
	{
		Matrix<RE> a = zeros(size, size);
		for (int i = 1; i <= size; ++i) {
			a.set(i, i, factory.one());
		}
		return a;
	}

	/**
	 * @param length
	 * @param value
	 * @return a Vector with <code>length</code> elements of <code>value</code>
	 */
	private Vector<RE> block_vector(int length, RE value)
	{
		Vector<RE> v = new Vector<RE>(length, factory);
		for (int i = 1; i <= length; ++i) {
			v.set(i, value);
		}
		return v;
	}

	/**
	 * @param numberOfRows
	 * @param numberOfCols
	 * @param value
	 * @return a {@link Matrix} with elements of <code>value</code>
	 */
	private Matrix<RE> block_matrix(int numberOfRows, int numberOfCols, RE value)
	{
		Matrix<RE> a = new Matrix<RE>(numberOfRows, numberOfCols, factory);
		for (int i = 1; i <= numberOfRows; ++i) {
			for (int j = 1; j <= numberOfCols; ++j) {
				a.set(i, j, value);
			}
		}
		return a;
	}

	/**
	 * Returns a Matrix built from an array of double-precision floating-point
	 * values.
	 * 
	 * @param theValues
	 * @return Matrix built from theValues
	 * @throws InvalidOperationException
	 * @deprecated use {@link RingElementFactory#convert(Matrix)} or an
	 *             appropriate constructor of {@link Matrix}
	 */
	@Deprecated
	public Matrix<RE> buildMatrix(double[][] theValues)
			throws InvalidOperationException
	{
		return new Matrix<RE>(wrap(theValues));
	}

	/**
	 * Returns a Vector built from an array of double-precision floating-point
	 * values.
	 * 
	 * @param theValues
	 * @return Vector built from theValues
	 * @throws InvalidOperationException
	 * @deprecated use {@link RingElementFactory#convert(Vector)} or an
	 *             appropriate constructor of {@link Vector}
	 */
	@Deprecated
	public Vector<RE> buildVector(double[] theValues)
			throws InvalidOperationException
	{
		return new Vector<RE>(wrap(theValues));
	}

	/**
	 * create a array with elements of type <code>RE</code> from a double vector
	 * 
	 * @param x
	 * @return an RE-array
	 * @deprecated use {@link RingElementFactory#convert(Vector)}
	 */
	@Deprecated
	public RE[] wrap(double[] x)
	{
		RE[] d = factory.getArray(x.length);
		for (int i = 0; i < x.length; ++i) {
			d[i] = factory.get(x[i]);
		}
		return d;
	}

	/**
	 * create a 2-d array with elements of type <code>RE</code> from a double
	 * array
	 * <P>
	 * use a constructor of {@link Matrix} instead.
	 * 
	 * @param x
	 * @return an RE-array
	 * @deprecated use {@link RingElementFactory#convert(Matrix)}
	 */
	@Deprecated
	public RE[][] wrap(double[][] x)
	{
		int rows = x.length, cols = x[0].length;
		RE[][] d = factory.getArray(rows, cols);
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < cols; ++j) {
				d[i][j] = factory.get(x[i][j]);
			}
		}
		return d;
	}
}
