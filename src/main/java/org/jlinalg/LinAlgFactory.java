package org.jlinalg;

import java.io.Serializable;
import java.util.Random;

/**
 * This class provides a set of methods for generating various common types of
 * matrices and vectors for an arbitrary RingElement type.
 * 
 * @author Simon D. Levy, Andreas Keilhauer
 * @param <RE>
 *            the type of the elements in the vectors, matrices to be created.
 */

public class LinAlgFactory<RE extends IRingElement>
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
	 * @param factory
	 *            the factory for elements this LinAlgFactory creates matices,
	 *            vectors, etc. for.
	 */
	public LinAlgFactory(IRingElementFactory<RE> factory)
	{
		this.factory = factory;
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
	 * Returns a Matrix of uniformly distributed random values. The kind of
	 * random values you get depends on the RingElement you use to initialise
	 * the LinAlgFactory
	 * 
	 * @param numberOfRows
	 * @param numberOfCols
	 * @param random
	 *            random-number generator
	 * @return matrix of uniformly distributed random values
	 */
	public Matrix<RE> uniformNoise(int numberOfRows, int numberOfCols,
			Random random)
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
	 * Returns a Matrix of normally distributed random values. Values are taken
	 * from a Gaussian distribution with zero mean and standard deviation one.<BR>
	 * <STRONG>Note!</STRONG> Normal Distribution Issue<BR>
	 * For the following RingElement types this method won't achieve values of
	 * the correct distribution: F2, FieldP
	 * 
	 * @param numberOfRows
	 * @param numberOfCols
	 * @param random
	 *            random-number generator
	 * @return Matrix of normally distributed random values
	 */
	public Matrix<RE> gaussianNoise(int numberOfRows, int numberOfCols,
			Random random)
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
	 * Returns a Vector of uniformly distributed random values.
	 * 
	 * @param length
	 *            vector length
	 * @param random
	 *            a random number generator
	 * @return vector of uniformly distributed random values
	 */
	public Vector<RE> uniformNoise(int length, Random random)
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
	 * @param random
	 *            a random number generator
	 * @return vector of uniformly distributed random values
	 */
	public Vector<RE> gaussianNoise(int length, Random random)
	{
		Vector<RE> v = new Vector<RE>(length, factory);
		for (int i = 1; i <= length; ++i) {
			v.set(i, factory.gaussianRandomValue());
		}
		return v;
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
	 */
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
	 */
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
	 */
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
	 * 
	 * @param x
	 * @return an RE-array
	 */
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
