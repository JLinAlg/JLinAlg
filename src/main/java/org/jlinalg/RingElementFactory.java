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
import java.lang.reflect.InvocationTargetException;

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
		implements
		IRingElementFactory<RE>,
		Serializable
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

	transient private DyadicOperator<RE> subtractOperator;
	transient private AndOperator<RE> andOperator;
	transient private DivideOperator<RE> divideOperator;
	transient private NotOperator<RE> notOperator;
	transient private AddOperator<RE> addOperator;
	transient private FEComparator<RE> gteOperator;
	transient private FEComparator<RE> ltOperator;
	transient private FEComparator<RE> lteOperator;
	transient private FEComparator<RE> neOperator;
	transient private FEComparator<RE> gtOperator;
	transient private MonadicOperator<RE> absOperator;
	transient private MonadicOperator<RE> sqrOperator;
	transient private DyadicOperator<RE> multiplyOperator;
	transient private FEComparator<RE> eqOperator;
	transient private DyadicOperator<RE> orOperator;
	transient private Reduction<RE> maxOperator;
	transient private Reduction<RE> sumOperator;
	transient private Reduction<RE> minOperator;

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

		throw new InvalidOperationException("cannot convert " + o
				+ " in factory " + getClass().getCanonicalName());
	}

	@Override
	public abstract RE get(int i);

	@Override
	public abstract RE get(double d);

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
	 * convert matrices
	 * 
	 * @param from
	 *            the matrix to be converted
	 * @return a matrix of type <RE>
	 */
	@Override
	public Matrix<RE> convert(final Matrix<? extends IRingElement<?>> from)
	{
		Matrix<RE> to = new Matrix<>(from.getRows(), from.getCols(), this);
		for (int row = 0; row < from.getRows(); row++) {
			for (int col = 0; col < from.getCols(); col++) {
				to.entries[row][col] = this.get(from.entries[row][col]);
			}
		}
		return to;
	}

	/**
	 * @return a matrix of the given elements converted into type RE. First
	 *         index is the row.
	 */
	@Override
	public Matrix<RE> convert(final String[][] from)
	{
		Matrix<RE> to = new Matrix<>(from.length, from[0].length, this);
		for (int row = 0; row < from[0].length; row++) {
			for (int col = 0; col < from.length; col++) {
				to.entries[col][row] = this.get(from[col][row]);
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
		Vector<RE> to = new Vector<>(from.length(), this);
		for (int row = 0; row < from.length(); row++) {
			to.entries[row] = this.get(from.entries[row]);
		}
		return to;
	}

	@Override
	public DyadicOperator<RE> getAddOperator()
	{
		if (addOperator == null) {
			addOperator = new AddOperator<>();
		}
		return addOperator;
	}

	@Override
	public DyadicOperator<RE> getSubtractOperator()
	{
		if (subtractOperator == null) {
			subtractOperator = new SubtractOperator<>();
		}
		return subtractOperator;
	}

	@Override
	public DyadicOperator<RE> getAndOperator()
	{
		if (andOperator == null) {
			andOperator = new AndOperator<>();
		}
		return andOperator;
	}

	@Override
	public DyadicOperator<RE> getOrOperator()
	{
		if (orOperator == null) {
			orOperator = new OrOperator<>();
		}
		return orOperator;
	}

	@Override
	public DyadicOperator<RE> getDivideOperator()
	{
		if (divideOperator == null) {
			divideOperator = new DivideOperator<>();
		}
		return divideOperator;
	}

	@Override
	public FEComparator<RE> getGreaterThanOrEqualToComparator()
	{
		if (gteOperator == null) {
			gteOperator = new GreaterThanOrEqualToComparator<>();
		}
		return gteOperator;
	}

	@Override
	public FEComparator<RE> getLessThanComparator()
	{
		if (ltOperator == null) {
			ltOperator = new LessThanComparator<>();
		}
		return ltOperator;
	}

	@Override
	public FEComparator<RE> getLessThanOrEqualToComparator()
	{
		if (lteOperator == null) {
			lteOperator = new LessThanOrEqualToComparator<>();
		}
		return lteOperator;
	}

	@Override
	public FEComparator<RE> getNotEqualToComparator()
	{
		if (neOperator == null) {
			neOperator = new NotEqualToComparator<>();
		}
		return neOperator;
	}

	@Override
	public FEComparator<RE> getGreaterThanComparator()
	{
		if (gtOperator == null) {
			gtOperator = new GreaterThanComparator<>();
		}
		return gtOperator;
	}

	@Override
	public MonadicOperator<RE> getNotOperator()
	{
		if (notOperator == null) {
			notOperator = new NotOperator<>();
		}
		return notOperator;
	}

	@Override
	public MonadicOperator<RE> getAbsOperator()
	{
		if (absOperator == null) {
			absOperator = new AbsOperator<>();
		}
		return absOperator;
	}

	@Override
	public MonadicOperator<RE> getSquareOperator()
	{
		if (sqrOperator == null) {
			sqrOperator = new SquareOperator<>();
		}
		return sqrOperator;
	}

	@Override
	public DyadicOperator<RE> getMultiplyOperator()
	{
		if (multiplyOperator == null) {
			multiplyOperator = new MultiplyOperator<>();
		}
		return multiplyOperator;
	}

	@Override
	public FEComparator<RE> getEqualToComparator()
	{
		if (eqOperator == null) {
			eqOperator = new EqualToComparator<>();
		}
		return eqOperator;
	}

	@Override
	public Reduction<RE> getMaxOperator()
	{
		if (maxOperator == null) {
			maxOperator = new MaxReduction<>();
		}
		return maxOperator;
	}

	@Override
	public Reduction<RE> getMinOperator()
	{
		if (minOperator == null) {
			minOperator = new MinReduction<>();
		}
		return minOperator;
	}

	@Override
	public Reduction<RE> getSumOperator()
	{
		if (sumOperator == null) {
			sumOperator = new SumReduction<>();
		}
		return sumOperator;
	}
}
