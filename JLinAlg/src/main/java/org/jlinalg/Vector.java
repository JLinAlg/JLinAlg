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
import java.lang.reflect.Method;

import org.jlinalg.operator.AbsOperator;
import org.jlinalg.operator.AddOperator;
import org.jlinalg.operator.AndOperator;
import org.jlinalg.operator.DivideOperator;
import org.jlinalg.operator.DyadicOperator;
import org.jlinalg.operator.EqualToComparator;
import org.jlinalg.operator.FEComparator;
import org.jlinalg.operator.GreaterThanComparator;
import org.jlinalg.operator.GreaterThanOrEqualToComparator;
import org.jlinalg.operator.LessThanComparator;
import org.jlinalg.operator.LessThanOrEqualToComparator;
import org.jlinalg.operator.MaxReduction;
import org.jlinalg.operator.MinReduction;
import org.jlinalg.operator.MonadicOperator;
import org.jlinalg.operator.MultiplyOperator;
import org.jlinalg.operator.NotEqualToComparator;
import org.jlinalg.operator.NotOperator;
import org.jlinalg.operator.OrOperator;
import org.jlinalg.operator.Reduction;
import org.jlinalg.operator.SquareOperator;
import org.jlinalg.operator.SubtractOperator;
import org.jlinalg.operator.SumReduction;

/**
 * This class represents a mathematical vector.
 * 
 * @author Andreas Keilhauer, Simon D. Levy, Georg Thimm
 * @param <RE>
 *            the type of the elements in the vector.
 */
public class Vector<RE extends IRingElement<RE>>
		implements Serializable, Comparable<Vector<RE>>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	/**
	 * a reference to the singleton factory
	 */
	final IRingElementFactory<RE> FACTORY;

	/**
	 * the elements in this vector.
	 */
	protected RE[] entries;

	/**
	 * Create a Vector with elements of type {@code RE} from a 1-dimensional
	 * array containing any type of objects the factory of {@code RE}
	 * understands.
	 * 
	 * @param theEntries
	 *            the array of objects to be converted and inserted into the
	 *            vector.
	 * @param factory
	 *            the factory of the base type for the vector to be created.
	 */
	public Vector(Object[] theEntries, IRingElementFactory<RE> factory)
			throws InvalidOperationException
	{
		if (theEntries == null) {
			throw new InvalidOperationException(
					"Tried to construct vector but entry array was null");
		}
		if (factory == null) {
			throw new InvalidOperationException("The factory is is null ");
		}
		entries = factory.getArray(theEntries.length);
		FACTORY = factory;
		for (int row = 0; row < theEntries.length; row++) {
			entries[row] = FACTORY.get(theEntries[row]);
		}
	}

	/**
	 * A constructor that creates a Vector of a given length with no entries.
	 * 
	 * @param length
	 * @param factory
	 *            the factory to be used to create elements for this vector.
	 */
	public Vector(int length, IRingElementFactory<RE> factory)
	{
		FACTORY = factory;
		entries = factory.getArray(length);
	}

	/**
	 * A constructor that puts an Array of RingElements into a Vector.
	 * 
	 * @param theEntries
	 *            as an array of field elements
	 * @throws InvalidOperationException
	 *             if the array is null
	 */
	public Vector(RE[] theEntries) throws InvalidOperationException
	{
		if (theEntries == null) {
			String err = "Tried to construct Vector but entry array was null.";
			throw new InvalidOperationException(err);
		}
		FACTORY = theEntries[0].getFactory();
		entries = theEntries;
	}

	/**
	 * Create a new vector with elements of another type.
	 * 
	 * @param vector
	 *            The vector to be converted
	 * @param factory
	 *            The factory that will be used to create the elements of the
	 *            new vector.
	 */
	public Vector(Vector<?> vector, IRingElementFactory<RE> factory)
	{
		this(vector.entries, factory);
	}

	/**
	 * Returns the length of the Vector.
	 * 
	 * @return the length
	 */

	public int length()
	{
		return entries.length;
	}

	/**
	 * Returns the L1 norm of this Vector.
	 * 
	 * @return L1 norm of this Vector
	 */
	@SuppressWarnings("unchecked")
	public RE L1Norm()
	{
		Vector<RE> abs = this.apply((MonadicOperator<RE>) AbsOperator
				.getInstance());
		return abs.sum();
	}

	/**
	 * @return the L2 or Euclidean norm of this Vector.
	 * @throws InvalidOperationException
	 *             if the <code>RE</code> does not implement the method sqrt()
	 *             (or the reflective access to class <code>RE</code> fails for
	 *             some other reason).
	 */
	@SuppressWarnings("unchecked")
	public RE L2Norm()
	{
		RE sum = this.apply((MonadicOperator<RE>) SquareOperator.getInstance())
				.sum();
		Method sqrt;
		try {
			sqrt = sum.getClass().getMethod("sqrt");
			return (RE) sqrt.invoke(sum);
		} catch (Exception e) {
			throw new InvalidOperationException(
					"L2Norm can not be calculated for "
							+ sum.getClass().getCanonicalName() + " "
							+ e.getMessage());

		}
	}

	/**
	 * Returns the Manhattan distance (L1 norm of differences) between this
	 * Vector and another.
	 * 
	 * @param anotherVector
	 * @return Manhattan distance between this and anotherVector
	 * @throws InvalidOperationException
	 *             if Vectors have unequal lengths
	 */
	public RE nycDist(Vector<RE> anotherVector)
			throws InvalidOperationException
	{
		return subtract(anotherVector).L1Norm();
	}

	/**
	 * Returns the Squared Euclidean distance between this
	 * Vector and another. This is not a metric as it does not satisfy the
	 * triangle equation, but it is very useful (e.g. for optimisation
	 * problems).
	 * 
	 * @param anotherVector
	 * @return MSquared Euclidean distance between this and anotherVector
	 */
	public RE squaredDistance(Vector<RE> anotherVector)
	{
		RE sum = this.subtract(anotherVector)
				.apply(((MonadicOperator<RE>) SquareOperator.getInstance()))
				.sum();
		return sum;
	}

	/**
	 * Returns the Euclidean distance (L2 norm of differences) between this
	 * Vector and another.
	 * 
	 * @param anotherVector
	 * @return Euclidean distance between this and anotherVector
	 * @throws InvalidOperationException
	 *             if Vectors have unequal lengths or RE does not implement the
	 *             method sqrt()
	 */
	public RE distance(Vector<RE> anotherVector)
	{
		return subtract(anotherVector).L2Norm();
	}

	/**
	 * Returns the cosine between this Vector and another. Cosine is the dot
	 * product of the vectors, divided by the product of their lengths.
	 * N.B.: In General this operation will fail, if not all entries are
	 * FieldElements.
	 * 
	 * @param anotherVector
	 * @return cosine between this and anotherVector
	 * @throws InvalidOperationException
	 *             if Vectors have unequal lengths
	 */
	public RE cosine(Vector<RE> anotherVector) throws InvalidOperationException
	{
		check_lengths(anotherVector, "cosine");
		return multiply(anotherVector).divide(L2Norm()).divide(
				anotherVector.L2Norm());
	}

	/**
	 * Gets the entry of a certain index. First index is 1.
	 * 
	 * @param index
	 *            an index of a vector element
	 * @return the entry
	 * @throws InvalidOperationException
	 *             if the index is out of bounds
	 */

	public RE getEntry(int index) throws InvalidOperationException
	{
		try {
			return entries[index - 1];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InvalidOperationException("Invalid index: " + index
					+ "\n Valid only between 1 and " + entries.length);
		}
	}

	/**
	 * Sets an entry to a IRingElement at a certain index.
	 * 
	 * @param index
	 * @param entry
	 * @throws InvalidOperationException
	 *             if the index is invalid
	 */
	public void set(int index, RE entry) throws InvalidOperationException
	{
		try {
			entries[index - 1] = entry;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InvalidOperationException("Invalid index: " + index
					+ "\n Valid only between 1 and " + entries.length);
		}
	}

	/**
	 * Returns a new 1xN Matrix made from the N elements of this Vector.
	 * 
	 * @return a Matrix with a single column having the elements of this vector.
	 */
	public Matrix<RE> toMatrix()
	{
		Matrix<RE> tmp = new Matrix<RE>(this.length(), 1, FACTORY);
		tmp.setCol(1, this);
		return tmp;
	}

	/**
	 * Returns this Vector divided by a scalar.
	 * 
	 * @param scalar
	 * @return the divided Vector
	 */
	@SuppressWarnings("unchecked")
	public Vector<RE> divide(RE scalar)
	{
		return operate(scalar,
				(DyadicOperator<RE>) DivideOperator.getInstance());
	}

	/**
	 * Divides this Vector by a scalar.
	 * 
	 * @param scalar
	 */
	@SuppressWarnings("unchecked")
	public void divideReplace(RE scalar)
	{
		operateReplace(scalar,
				(DyadicOperator<RE>) DivideOperator.getInstance());
	}

	/**
	 * Returns the product of this Vector and a scalar.
	 * 
	 * @param scalar
	 * @return the multiplied Vector
	 */
	@SuppressWarnings("unchecked")
	public Vector<RE> multiply(RE scalar)
	{
		return operate(scalar,
				(DyadicOperator<RE>) MultiplyOperator.getInstance());
	}

	/**
	 * Returns the element-wise product of this Vector and another.
	 * 
	 * @param anotherVector
	 * @return this .* anotherVector
	 * @throws InvalidOperationException
	 *             if the vectors have unequal lengths
	 */
	@SuppressWarnings("unchecked")
	public Vector<RE> arrayMultiply(Vector<RE> anotherVector)
			throws InvalidOperationException
	{
		return operate(anotherVector,
				(DyadicOperator<RE>) MultiplyOperator.getInstance(),
				"arrayMultiply");
	}

	/**
	 * Multiplies this Vector by a scalar.
	 * 
	 * @param scalar
	 */

	@SuppressWarnings("unchecked")
	public void multiplyReplace(RE scalar)
	{
		operateReplace(scalar,
				(DyadicOperator<RE>) MultiplyOperator.getInstance());
	}

	/**
	 * Multiplies this Vector element-wise by another.
	 * 
	 * @param anotherVector
	 * @throws InvalidOperationException
	 *             if the vectors have unequal lengths
	 */
	@SuppressWarnings("unchecked")
	public void multiplyReplace(Vector<RE> anotherVector)
	{
		operateReplace(anotherVector,
				(DyadicOperator<RE>) MultiplyOperator.getInstance(),
				"multiplyReplace");
	}

	/**
	 * Returns the sum of this Vector and a scalar.
	 * 
	 * @param scalar
	 * @return the add Vector
	 */
	@SuppressWarnings("unchecked")
	public Vector<RE> add(RE scalar)
	{
		return operate(scalar, (DyadicOperator<RE>) AddOperator.getInstance());
	}

	/**
	 * Returns the sum of this Vector and another.
	 * 
	 * @param anotherVector
	 * @return this + anotherVector
	 * @throws InvalidOperationException
	 *             if the vectors have unequal lengths
	 */

	@SuppressWarnings("unchecked")
	public Vector<RE> add(Vector<RE> anotherVector)
			throws InvalidOperationException
	{
		return operate(anotherVector,
				(DyadicOperator<RE>) AddOperator.getInstance(), "add");
	}

	/**
	 * Adds a scalar to this Vector.
	 * 
	 * @param scalar
	 */
	@SuppressWarnings("unchecked")
	public void addReplace(RE scalar)
	{
		operateReplace(scalar, (DyadicOperator<RE>) AddOperator.getInstance());
	}

	/**
	 * Adds another vector to this Vector.
	 * 
	 * @param anotherVector
	 * @throws InvalidOperationException
	 *             if the vectors have unequal lengths
	 */
	@SuppressWarnings("unchecked")
	public void addReplace(Vector<RE> anotherVector)
	{
		operateReplace(anotherVector,
				(DyadicOperator<RE>) AddOperator.getInstance(), "add");
	}

	/**
	 * Returns this Vector subtracted by a scalar.
	 * 
	 * @param scalar
	 * @return the subtractd Vector
	 */
	@SuppressWarnings("unchecked")
	public Vector<RE> subtract(RE scalar)
	{
		return operate(scalar,
				(DyadicOperator<RE>) SubtractOperator.getInstance());
	}

	/**
	 * Returns the result of subtracting another vector from this.
	 * 
	 * @param anotherVector
	 * @return this - anotherVector
	 * @throws InvalidOperationException
	 *             if the vectors have unequal lengths
	 */
	@SuppressWarnings("unchecked")
	public Vector<RE> subtract(Vector<RE> anotherVector)
			throws InvalidOperationException
	{
		return operate(anotherVector,
				(DyadicOperator<RE>) SubtractOperator.getInstance(), "subtract");
	}

	/**
	 * Subtracts a scalar from this Vector.
	 * 
	 * @param scalar
	 */
	@SuppressWarnings("unchecked")
	public void subtractReplace(RE scalar)
	{
		operateReplace(scalar,
				(DyadicOperator<RE>) SubtractOperator.getInstance());
	}

	/**
	 * Subtracts another Vector from this.
	 * 
	 * @param anotherVector
	 * @throws InvalidOperationException
	 *             if the vectors have unequal lengths
	 */
	@SuppressWarnings("unchecked")
	public void subtractReplace(Vector<RE> anotherVector)
	{
		operateReplace(anotherVector,
				(DyadicOperator<RE>) SubtractOperator.getInstance(), "subtract");
	}

	/**
	 * Returns the scalar product of this Vector and another.
	 * 
	 * @return The scalar product of this Vector and another one.
	 * @param anotherVector
	 * @throws InvalidOperationException
	 *             if the other vector is null
	 * @throws InvalidOperationException
	 *             if the vectors have unequal lengths
	 */
	public RE multiply(Vector<RE> anotherVector)
			throws InvalidOperationException
	{
		if (anotherVector == null) {
			throw new InvalidOperationException("Tried to " + "multiply \n"
					+ this + " and \n" + anotherVector
					+ "Second Vector is null!");
		}

		check_lengths(anotherVector, "multiply");

		RE result = FACTORY.zero();
		for (int i = 1; i <= entries.length; i++) {
			result = result.add(entries[i - 1].multiply(anotherVector
					.getEntry(i)));
		}

		return result;
	}

	/**
	 * Tests two Vectors for equality.
	 * 
	 * @return true if and only if the two Vectors equal in all entries.
	 * @param anotherVector
	 */
	@Override
	public boolean equals(Object anotherVector)
	{
		Vector<?> v = (Vector<?>) anotherVector;
		if (this.length() == v.length()) {
			for (int i = 1; i <= this.length(); i++) {
				if (!this.getEntry(i).equals(v.getEntry(i))) {
					return false;
				}
			}
		}
		else {
			return false;
		}
		return true;
	}

	/**
	 * Swaps two Entries of a Vector.
	 * 
	 * @param index1
	 *            1st swap partner
	 * @param index2
	 *            2nd seap partner
	 */
	public void swapEntries(int index1, int index2)
	{
		RE tmp = this.getEntry(index1);
		this.set(index1, this.getEntry(index2));
		this.set(index2, tmp);
	}

	/**
	 * Returns a String representation of this Vector
	 */
	@Override
	public String toString()
	{
		if (entries.length == 0) {
			return "( )";
		}
		String tempString = "(";
		for (int i = 1; i < entries.length; i++) {
			tempString += getEntry(i) + ", ";
		}
		tempString += getEntry(entries.length) + ")";
		return tempString;
	}

	/**
	 * Returns the logical AND of this Vector with another. Elements of the
	 * result are 1 where both vectors are non-zero, and zero elsewhere.
	 * 
	 * @param anotherVector
	 * @return Vector of 1's and 0's
	 * @throws InvalidOperationException
	 *             if the vectors have unequal lengths
	 */

	@SuppressWarnings("unchecked")
	public Vector<RE> and(Vector<RE> anotherVector)
	{
		return operate(anotherVector,
				(DyadicOperator<RE>) AndOperator.getInstance(), "AND");
	}

	/**
	 * Returns the logical OR of this Vector with another. Elements of the
	 * result are 1 where both vectors are non-zero, and zero elsewhere.
	 * 
	 * @param anotherVector
	 * @return Vector of 1's and 0's
	 * @throws InvalidOperationException
	 *             if the vectors have unequal lengths
	 */
	@SuppressWarnings("unchecked")
	public Vector<RE> or(Vector<RE> anotherVector)
	{
		return operate(anotherVector,
				(DyadicOperator<RE>) OrOperator.getInstance(), "OR");
	}

	/**
	 * Returns the logical negation of this Vector. Elements of the result are 1
	 * where the vector is zero, and one elsewhere.
	 * 
	 * @return Vector of 1's and 0's
	 */
	@SuppressWarnings("unchecked")
	public Vector<RE> not()
	{
		return this.apply((MonadicOperator<RE>) NotOperator.getInstance());
	}

	/**
	 * Sets this Vector to the result of applying a specified function to every
	 * element of this Vector. New functions can be applied to a Vector by
	 * subclassing the abstract <tt>MonadicOperator</tt> class.
	 * 
	 * @param fun
	 *            the function to apply
	 */
	public void applyReplace(MonadicOperator<RE> fun)
	{
		for (int i = 1; i <= this.length(); i++) {
			this.set(i, fun.apply(this.getEntry(i)));
		}
	}

	/**
	 * Returns the result of applying a specified function to every element of
	 * this Vector. New functions can be applied to a Vector by subclassing the
	 * abstract <tt>MonadicOperator</tt> class.
	 * 
	 * @param fun
	 *            the function to apply
	 * @return result of applying <tt>fun</tt> to this Vector
	 */
	public Vector<RE> apply(MonadicOperator<RE> fun)
	{
		Vector<RE> vector = new Vector<RE>(this.length(), FACTORY);

		for (int i = 1; i <= vector.length(); i++) {
			vector.set(i, fun.apply(this.getEntry(i)));
		}
		return vector;
	}

	/**
	 * Sets this Vector to the result of applying a specified function to
	 * elements of this Vector and another's. New functions can be applied to a
	 * Vector by subclassing the abstract <tt>DyadicOperator</tt> class.
	 * 
	 * @param anotherVector
	 * @param fun
	 *            the function to apply
	 */
	public void applyReplace(Vector<RE> anotherVector, DyadicOperator<RE> fun)
	{
		check_lengths(anotherVector, fun.getClass().getName());

		for (int i = 1; i <= this.length(); i++) {
			this.set(i, fun.apply(this.getEntry(i), anotherVector.getEntry(i)));
		}
	}

	/**
	 * Returns the result of applying a specified function to the elements of
	 * this Vector and another. New functions can be applied to a Vector by
	 * subclassing the abstract <tt>DyadicOperator</tt> class.
	 * 
	 * @param anotherVector
	 * @param fun
	 *            the function to apply
	 * @return result of applying <tt>fun</tt> to the two Vectors
	 */
	public Vector<RE> apply(Vector<RE> anotherVector, DyadicOperator<RE> fun)
	{

		check_lengths(anotherVector, fun.getClass().getName());

		Vector<RE> vector = new Vector<RE>(this.length(), FACTORY);

		for (int i = 1; i <= vector.length(); i++) {
			vector.set(i,
					fun.apply(this.getEntry(i), anotherVector.getEntry(i)));
		}

		return vector;
	}

	/**
	 * Sets this Vector to the result of applying a specified function to
	 * elements of this Vector and a scalar. New functions can be applied to a
	 * Vector by subclassing the abstract <tt>DyadicOperator</tt> class.
	 * 
	 * @param scalar
	 * @param fun
	 *            the function to apply
	 */
	public void applyReplace(RE scalar, DyadicOperator<RE> fun)
	{

		for (int i = 1; i <= this.length(); i++) {
			this.set(i, fun.apply(this.getEntry(i), scalar));
		}
	}

	/**
	 * Returns the result of applying a specified function to the elements of a
	 * this Vector a scalar. New functions can be applied to a Vector by
	 * subclassing the abstract <tt>DyadicOperator</tt> class.
	 * 
	 * @param scalar
	 * @param fun
	 *            the function to apply
	 * @return result of applying <tt>fun</tt> to the Vector and scalar
	 */
	public Vector<RE> apply(RE scalar, DyadicOperator<RE> fun)
	{

		Vector<RE> vector = new Vector<RE>(this.length(), FACTORY);

		for (int i = 1; i <= vector.length(); i++) {
			vector.set(i, fun.apply(this.getEntry(i), scalar));
		}

		return vector;
	}

	/**
	 * Returns a deep copy of this Vector.
	 * 
	 * @return Vector copy
	 */
	public Vector<RE> copy()
	{
		if (entries.length == 0) {
			throw new InvalidOperationException("Encountered empty vector.");
		}
		Vector<RE> tmp = new Vector<RE>(this.length(), entries[0].getFactory());
		for (int i = 1; i <= this.length(); i++) {
			tmp.set(i, this.getEntry(i));
		}
		return tmp;
	}

	/**
	 * Returns a Vector containing ones where this Vector's elements are less
	 * than those of another Vectors, and zeros elsewhere.
	 * 
	 * @param anotherVector
	 * @return Vector of ones and zeros
	 * @throws InvalidOperationException
	 *             if the vectors have unequal lengths
	 */
	@SuppressWarnings("unchecked")
	public Vector<RE> lt(Vector<RE> anotherVector)
	{
		return comparison(anotherVector,
				(FEComparator<RE>) LessThanComparator.getInstance(), "LT");
	}

	/**
	 * Returns a Vector containing ones where this Vector's elements are less
	 * than a scalar, and zeros elsewhere.
	 * 
	 * @param scalar
	 * @return Vector of ones and zeros
	 */
	@SuppressWarnings("unchecked")
	public Vector<RE> lt(RE scalar)
	{
		return comparison(scalar,
				(FEComparator<RE>) LessThanComparator.getInstance());
	}

	/**
	 * Returns a Vector containing ones where this Vector's elements are less
	 * than or equal to those of another Vectors, and zeros elsewhere.
	 * 
	 * @param anotherVector
	 * @return Vector of ones and zeros
	 * @throws InvalidOperationException
	 *             if the vectors have unequal lengths
	 */
	@SuppressWarnings("unchecked")
	public Vector<RE> le(Vector<RE> anotherVector)
	{
		return comparison(anotherVector,
				(FEComparator<RE>) LessThanOrEqualToComparator.getInstance(),
				"LE");
	}

	/**
	 * Returns a Vector containing ones where this Vector's elements are less
	 * than or equal to a scalar, and zeros elsewhere.
	 * 
	 * @param scalar
	 * @return Vector of ones and zeros
	 */
	@SuppressWarnings("unchecked")
	public Vector<RE> le(RE scalar)
	{
		return comparison(scalar,
				(FEComparator<RE>) LessThanOrEqualToComparator.getInstance());
	}

	/**
	 * Returns a Vector containing ones where this Vector's elements are greater
	 * than those of another Vectors, and zeros elsewhere.
	 * 
	 * @param anotherVector
	 * @return Vector of ones and zeros
	 * @throws InvalidOperationException
	 *             if the vectors have unequal lengths
	 */
	@SuppressWarnings("unchecked")
	public Vector<RE> gt(Vector<RE> anotherVector)
	{
		return comparison(anotherVector,
				(FEComparator<RE>) GreaterThanComparator.getInstance(), "GT");
	}

	/**
	 * Returns a Vector containing ones where this Vector's elements are greater
	 * than a scalar, and zeros elsewhere.
	 * 
	 * @param scalar
	 * @return Vector of ones and zeros
	 */
	@SuppressWarnings("unchecked")
	public Vector<RE> gt(RE scalar)
	{
		return comparison(scalar,
				(FEComparator<RE>) GreaterThanComparator.getInstance());
	}

	/**
	 * Returns a Vector containing ones where this Vector's elements are greater
	 * than or equal to those of another Vectors, and zeros elsewhere.
	 * 
	 * @param anotherVector
	 * @return Vector of ones and zeros
	 * @throws InvalidOperationException
	 *             if the vectors have unequal lengths
	 */
	@SuppressWarnings("unchecked")
	public Vector<RE> ge(Vector<RE> anotherVector)
	{
		return comparison(
				anotherVector,
				(FEComparator<RE>) GreaterThanOrEqualToComparator.getInstance(),
				"GE");
	}

	/**
	 * Returns a Vector containing ones where this Vector's elements are greater
	 * than or equal to a scalar, and zeros elsewhere.
	 * 
	 * @param scalar
	 * @return Vector of ones and zeros
	 */
	@SuppressWarnings("unchecked")
	public Vector<RE> ge(RE scalar)
	{
		return comparison(scalar,
				(FEComparator<RE>) GreaterThanOrEqualToComparator.getInstance());
	}

	/**
	 * Returns a Vector containing ones where this Vector's elements are equal
	 * to those of another Vectors, and zeros elsewhere.
	 * 
	 * @param anotherVector
	 * @return Vector of ones and zeros
	 * @throws InvalidOperationException
	 *             if the vectors have unequal lengths
	 */
	@SuppressWarnings("unchecked")
	public Vector<RE> eq(Vector<RE> anotherVector)
	{
		return comparison(anotherVector,
				(FEComparator<RE>) EqualToComparator.getInstance(), "EQ");
	}

	/**
	 * Returns a Vector containing ones where this Vector's elements are equal
	 * to a scalar, and zeros elsewhere.
	 * 
	 * @param scalar
	 * @return Vector of ones and zeros
	 */
	@SuppressWarnings("unchecked")
	public Vector<RE> eq(RE scalar)
	{
		return comparison(scalar,
				(FEComparator<RE>) EqualToComparator.getInstance());
	}

	/**
	 * Returns a Vector containing ones where this Vector's elements are not
	 * equal to those of another Vector, and zeros elsewhere.
	 * 
	 * @param anotherVector
	 * @return Vector of ones and zeros
	 * @throws InvalidOperationException
	 *             if the vectors have unequal lengths
	 */
	@SuppressWarnings("unchecked")
	public Vector<RE> ne(Vector<RE> anotherVector)
	{
		return comparison(anotherVector,
				(FEComparator<RE>) NotEqualToComparator.getInstance(), "NE");
	}

	/**
	 * Returns a Vector containing ones where this Vector's elements are not
	 * equal to a scalar, and zeros elsewhere.
	 * 
	 * @param scalar
	 * @return Vector of ones and zeros
	 */
	@SuppressWarnings("unchecked")
	public Vector<RE> ne(RE scalar)
	{
		return comparison(scalar,
				(FEComparator<RE>) NotEqualToComparator.getInstance());
	}

	/**
	 * Computes the sum over the elements of this Vector.
	 * 
	 * @return the sum
	 */
	public RE sum()
	{
		return reduce(new SumReduction<RE>());
	}

	/**
	 * Computes the smallest value of any element in this Vector.
	 * 
	 * @return the smallest value
	 */
	public RE min()
	{
		return reduce(new MinReduction<RE>());
	}

	/**
	 * Computes the largest value of any element in this Vector.
	 * 
	 * @return the largest value
	 */
	public RE max()
	{
		return reduce(new MaxReduction<RE>());
	}

	/**
	 * Computes the mean over the elements of this Vector.
	 * N.B.: In General this operation will fail, if not all entries are
	 * FieldElements.
	 * 
	 * @return the mean
	 */
	public RE mean()
	{
		return sum().multiply((FACTORY.get(length())).invert());
	}

	/**
	 * Returns indices where vector equals scalar argument.
	 * 
	 * @param scalar
	 * @return indices where vector equals scalar
	 */
	public int[] find(RE scalar)
	{
		int[] equals = new int[this.length()];
		int n = 0;
		for (int i = 1; i <= this.length(); ++i) {
			RE val = this.getEntry(i);
			equals[i - 1] = val.equals(scalar) ? i : -1;
			if (val.equals(scalar)) n++;
		}
		int[] indices = new int[n];
		int j = 0;
		for (int i = 0; i < this.length(); ++i) {
			if (equals[i] > -1) {
				indices[j++] = equals[i];
			}
		}
		return indices;
	}

	/**
	 * Sets all entries to a IRingElement.
	 * 
	 * @param newEntry
	 *            the IRingElement
	 */

	public void setAll(RE newEntry)
	{
		for (int i = 1; i <= this.length(); ++i) {
			this.set(i, newEntry);
		}
	}

	/**
	 * Replicates this Vector as a Matrix. Returns an <i>m</i>-by-<i>n</i>
	 * Matrix for a length-<i>n</i> Vector replicated <i>m</i> times.
	 * 
	 * @param m
	 *            number of times to replicate
	 * @return an <i>m</i>-by-<i>n</i> Matrix
	 */
	public Matrix<RE> repmat(int m)
	{
		Matrix<RE> a = new Matrix<RE>(m, this.length(), FACTORY);
		for (int i = 1; i <= m; ++i) {
			a.setRow(i, this);
		}
		return a;
	}

	/**
	 * Returns a copy of this Vector, sorted in ascending order.
	 * 
	 * @return sorted copy
	 */
	public Vector<RE> sort()
	{
		Vector<RE> sorted = this.copy();
		java.util.Arrays.sort(sorted.entries);
		return sorted;
	}

	/**
	 * Returns the product of this Vector and a Matrix.
	 * 
	 * @param theMatrix
	 * @return this * theMatrix
	 * @throws InvalidOperationException
	 *             if the matrix is null
	 * @throws InvalidOperationException
	 *             if the inner dimensions mismatch
	 */
	public Vector<RE> multiply(Matrix<RE> theMatrix)
			throws InvalidOperationException
	{

		if (theMatrix == null) {
			throw new InvalidOperationException("Tried to " + "multiply \n"
					+ this + " and \n" + theMatrix + "Matrix is null!");
		}

		if (this.length() != theMatrix.getRows()) {
			throw new InvalidOperationException("Tried to " + "multiply \n"
					+ this + " and \n" + theMatrix
					+ "Inner dimensions do not match!");
		}

		Vector<RE> result = new Vector<RE>(theMatrix.getCols(),
				theMatrix.getFactory());

		for (int i = 1; i <= theMatrix.getCols(); ++i) {
			result.set(i, this.multiply(theMatrix.getCol(i)));
		}

		return result;
	}

	/**
	 * @param vec
	 *            a vector
	 * @return the cross-product of this Vector and vector {@code vec}.
	 * @throws InvalidOperationException
	 *             if the other Vector is null or not in the three-dimensional
	 *             space
	 */

	public Vector<RE> cross(Vector<RE> vec) throws InvalidOperationException
	{
		Vector<RE> res = new Vector<RE>(3, FACTORY);
		res.entries[0] = entries[1].multiply(vec.entries[2]).subtract(
				entries[2].multiply(vec.entries[1]));
		res.entries[1] = entries[2].multiply(vec.entries[0]).subtract(
				entries[0].multiply(vec.entries[2]));
		res.entries[2] = entries[0].multiply(vec.entries[1]).subtract(
				entries[1].multiply(vec.entries[0]));
		return res;
	}

	/**
	 * @param anotherVector
	 * @return the matrix obtained from multiplying the transposed of this
	 *         Vector and another.
	 * @throws InvalidOperationException
	 *             if the other Vector is null
	 */
	public Matrix<RE> transposeAndMultiply(Vector<RE> anotherVector)
			throws InvalidOperationException
	{
		if (anotherVector == null) {
			throw new InvalidOperationException("Tried to " + "cross \n" + this
					+ " and \n" + anotherVector + "Other vector is null!");
		}

		Matrix<RE> result = new Matrix<RE>(this.length(),
				anotherVector.length(), FACTORY);

		for (int i = 1; i <= this.length(); ++i) {
			for (int j = 1; j <= anotherVector.length(); ++j) {
				result.set(i, j,
						entries[i - 1].multiply(anotherVector.getEntry(j)));
			}
		}

		return result;
	}

	/**
	 * Tests whether this is the zero-vector.
	 * 
	 * @return true iff this is the zero vector
	 */

	public boolean isZero()
	{
		for (int i = 0; i < this.length(); i++) {
			if (!this.entries[i].isZero()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Element-wise vector subtraction with error check. Depreciated as
	 * substract does the check anyhow.
	 * 
	 * @param anotherVector
	 * @param method
	 * @return the result of {@link #subtract(Vector)}
	 * @throws InvalidOperationException
	 *             is the vectors have different lengths
	 */
	@Deprecated
	protected Vector<RE> safe_diff(Vector<RE> anotherVector, String method)
			throws InvalidOperationException
	{
		check_lengths(anotherVector, method);
		return this.subtract(anotherVector);
	}

	/**
	 * @param anotherVector
	 * @param feComparator
	 * @param compName
	 * @return the result of comparing this Vector with another (ones where
	 *         comparison succeeds, zeros where it fails)
	 */
	private Vector<RE> comparison(Vector<RE> anotherVector,
			FEComparator<RE> feComparator, String compName)
	{
		check_lengths(anotherVector, compName);

		Vector<RE> v = new Vector<RE>(this.length(), FACTORY);

		for (int i = 1; i <= this.length(); ++i) {
			RE entry = this.getEntry(i);
			boolean success = feComparator.compare(entry,
					anotherVector.getEntry(i));
			RE result = success ? FACTORY.one() : FACTORY.zero();
			v.set(i, result);
		}
		return v;
	}

	/**
	 * @param scalar
	 * @param feComparator
	 *            the comparator used for the comparison.
	 * @return the result of comparing this Vector with a scalar (ones where
	 *         comparison succeeds, zeros where it fails)
	 */
	private Vector<RE> comparison(RE scalar, FEComparator<RE> feComparator)
	{
		Vector<RE> v = new Vector<RE>(this.length(), FACTORY);

		for (int i = 1; i <= this.length(); ++i) {
			RE entry = this.getEntry(i);
			boolean success = feComparator.compare(entry, scalar);
			RE result = success ? FACTORY.one() : FACTORY.zero();
			v.set(i, result);
		}
		return v;
	}

	/**
	 * Operate on two vectors to create a new vector
	 * 
	 * @param vector2
	 * @param dyadicOperator
	 *            an operator
	 * @param funName
	 * @return Vector resulting from operation on two others
	 */
	private Vector<RE> operate(Vector<RE> vector2,
			DyadicOperator<RE> dyadicOperator, String funName)
	{
		check_lengths(vector2, funName);
		Vector<RE> vector3 = copy();
		for (int i = 1; i <= length(); ++i) {
			vector3.set(i,
					dyadicOperator.apply(getEntry(i), vector2.getEntry(i)));
		}
		return vector3;
	}

	/**
	 * @param scalar
	 * @param dyadicOperator
	 * @return Vector resulting from operation on Vector and scalar
	 */
	private Vector<RE> operate(RE scalar, DyadicOperator<RE> dyadicOperator)
	{
		Vector<RE> vector2 = copy();
		for (int i = 1; i <= length(); ++i) {
			vector2.set(i, dyadicOperator.apply(getEntry(i), scalar));
		}
		return vector2;
	}

	/**
	 * Set elements of this Vector to result of operation on them and another's
	 * 
	 * @param vector
	 * @param dyadicOperator
	 * @param funName
	 */
	private void operateReplace(Vector<RE> vector,
			DyadicOperator<RE> dyadicOperator, String funName)
	{
		check_lengths(vector, funName);

		for (int i = 1; i <= this.length(); ++i) {
			this.set(i,
					dyadicOperator.apply(this.getEntry(i), vector.getEntry(i)));
		}
	}

	/**
	 * Set elements of this Vector to result of operation on them and another's
	 * 
	 * @param scalar
	 * @param dyadicOperator
	 */
	private void operateReplace(RE scalar, DyadicOperator<RE> dyadicOperator)
	{
		for (int i = 1; i <= this.length(); ++i) {
			this.set(i, dyadicOperator.apply(this.getEntry(i), scalar));
		}
	}

	/**
	 * Generic method for sum, min, max
	 * 
	 * @param r
	 *            the reduction
	 * @return the result of the reduction
	 */
	public RE reduce(Reduction<RE> r)
	{
		r.init(this.getEntry(1));
		for (int i = 2; i <= this.length(); i++) {
			r.track(this.getEntry(i));
		}
		return r.reducedValue;
	}

	/**
	 * Check vectors for equal length, throwing exception on failure
	 * 
	 * @param vector
	 * @param op
	 *            string indication from where this method was called.
	 * @throws InvalidOperationException
	 *             if the lengths of this and y don't match
	 */
	private void check_lengths(Vector<RE> vector, String op)
			throws InvalidOperationException
	{
		if (length() != vector.length()) {
			String err = "Tried to calculate\n" + this + " " + op + "\n"
					+ vector + " of different lengths";
			throw new InvalidOperationException(err);
		}
	}

	/**
	 * Calculate the hashcode from the first three elements in the vector (or
	 * less).
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		if (entries == null) return 0;
		int hash = entries.length;
		for (int i = 0; i < entries.length; i++)
			hash ^= entries[i].hashCode() << i;
		return hash;
	}

	/**
	 * Calculate the product over all elements in this vector
	 * 
	 * @return the product
	 * @exception InvalidOperationException
	 *                if the vector contains no element.
	 */
	public RE elementProduct()
	{
		if (entries.length == 0)
			throw new InvalidOperationException(
					"Can not calculate the product for an empty vector.");
		RE product = FACTORY.one();
		for (int i = 0; i < entries.length; i++)
			product = product.multiply(entries[i]);
		return product;
	}

	/**
	 * Establish a lexicographical order between vectors. The order follows the
	 * following rules:
	 * <UL>
	 * <LI>Comparison starts with the first vector element.
	 * <LI>The first element that differs defines the order according to their
	 * numerical value.
	 * <LI>if a null-value is found, the type of the fields is different, or the
	 * lengths of the vectors disagree, an exception is thrown
	 * <UL>
	 * 
	 * @param vector
	 * @return one of {<-1,0,>1} according to whether this vector is
	 *         lexigrographically smaller, equal or bigger than the argument
	 */
	@Override
	public int compareTo(Vector<RE> vector) throws InvalidOperationException
	{
		/*
		 * if (!(vector instanceof Vector)) throw new InvalidOperationException(
		 * "can not compare a Vector with on object of class " +
		 * vector.getClass().getCanonicalName() + ".");
		 */
		if (vector.length() != length())
			throw new InvalidOperationException(
					"Comparison of vectors with distinct lengths is not permitted.");
		RE[] entries_b = vector.entries;
		for (int i = 1; i <= length(); i++) {
			RE a = entries[i - 1];
			if (a == null)
				throw new InvalidOperationException(
						"Can not compare vectors with null entries.");
			RE b = entries_b[i - 1];
			if (b == null)
				throw new InvalidOperationException(
						"Can not compare vectors with null entries.");
			int c = a.compareTo(b);
			if (c != 0) return c;
		}
		return 0;
	}

	/**
	 * set the contents of <code>vector</code> into this vector.
	 * 
	 * @param vector
	 * @throws InvalidOperationException
	 *             if the elements were created by distinct factories
	 * @throws InvalidOperationException
	 *             is the lengths of the vectors are incompatible.
	 */
	public void set(Vector<RE> vector)
	{
		if (vector.FACTORY != FACTORY) {
			throw new InvalidOperationException(this.toString() + " and "
					+ vector.toString() + " have incompatible factories "
					+ FACTORY.toString() + " and " + vector.FACTORY.toString());
		}
		if (this.length() != vector.length())
			throw new InvalidOperationException(this.toString() + " and "
					+ vector.toString() + " have incompatible lengths "
					+ length() + " and " + vector.length());
		for (int i = 0; i < entries.length; i++) {
			entries[i] = vector.entries[i];
		}
	}

}
