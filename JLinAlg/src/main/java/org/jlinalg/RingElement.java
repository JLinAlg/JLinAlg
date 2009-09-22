package org.jlinalg;

import java.io.Serializable;

import org.jlinalg.operator.MonadicOperator;

/**
 * The basic implementation of some methods for most ring elements.
 * 
 * @author Andreas, Georg Thimm
 */
public abstract class RingElement<RE extends IRingElement<RE>>
		implements IRingElement<RE>, Comparable<RE>, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Tests if this RingElement is the neutral element of addition (zero).
	 * 
	 * @return true if zero
	 */
	public boolean isZero()
	{
		return this.equals(getFactory().zero());
	}

	/**
	 * Calculates the difference of this RingElement and another one.
	 * 
	 * @param val
	 * @return difference
	 */
	@Override
	public RE subtract(RE val)
	{
		RE re = val.negate();
		return add(re);
	}

	/**
	 * Tests if this RingElement is the neutral element of multiplication (one).
	 * 
	 * @return true if one
	 */
	public boolean isOne()
	{
		return this.equals(getFactory().one());
	}

	/**
	 * Determines whether or not two RingElements are equal.
	 * 
	 * @param obj
	 * @return true if the two RingElements are mathematically equal.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj)
	{
		return this.compareTo((RE) obj) == 0;
	}

	/**
	 * Returns the result of applying a specified function to this
	 * FielkdElement. New functions can be applied to a RingElement by
	 * sub-classing the abstract <tt>MonadicOperator</tt> class.
	 * 
	 * @param fun
	 *            the function to apply
	 * @return result of applying <tt>fun</tt> to this {@link RingElement}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public RE apply(MonadicOperator<RE> fun)
	{
		return fun.apply((RE) this);
	}

	// /**
	// * Returns absolute value of this element
	// *
	// * @return the absolute value
	// */
	// @SuppressWarnings("unchecked")
	// @Override
	// public RE abs()
	// {
	// return apply((MonadicOperator<RE>) AbsOperator.getInstance());
	// }

	/**
	 * @return the absolute value of this field.
	 */
	@Override
	public RE norm()
	{
		return this.abs();
	}

	/**
	 * Checks whether this RingElement is mathematically less than another.
	 * 
	 * @param val
	 * @return true if this RingElement is less than val, false otherwise
	 */
	@Override
	public boolean lt(RE val)
	{
		return compareTo(val) < 0;
	}

	/**
	 * Checks whether this RingElement is mathematically greater than another.
	 * 
	 * @param val
	 * @return true if this RingElement is greater than val, false otherwise
	 */
	@Override
	public boolean gt(RE val)
	{
		return compareTo(val) > 0;
	}

	/**
	 * Checks whether this RingElement is mathematically less than or equal to
	 * another.
	 * 
	 * @param val
	 * @return true if this RingElement is less than or equal to val, false
	 *         otherwise
	 */
	public boolean le(RE val)
	{
		return compareTo(val) <= 0;
	}

	/**
	 * Checks whether this RingElement is mathematically greater than or equal
	 * to another.
	 * 
	 * @param val
	 * @return true if this RingElement is greater than or equal to val, false
	 *         otherwise
	 */
	public boolean ge(RE val)
	{
		return compareTo(val) >= 0;
	}

	/**
	 *@throws DivisionByZeroException
	 *             if a division is possible for the type, but <code>val</code>
	 *             is
	 *             the zero element.
	 * @throws InvalidOperationException
	 *             as in the general case, an inversion is not possible.
	 */
	@Override
	public RE divide(@SuppressWarnings("unused") RE val)
			throws DivisionByZeroException, InvalidOperationException
	{
		throw new InvalidOperationException("RingElements  of type "
				+ this.getClass().getCanonicalName() + " cannot be divided!");
	}

	/**
	 *@throws DivisionByZeroException
	 *             if an inversion is possible for the type, but the element is
	 *             the zero element.
	 * @throws InvalidOperationException
	 *             as in the general case, an inversion is not possible.
	 */
	public RE invert() throws DivisionByZeroException,
			InvalidOperationException
	{
		throw new InvalidOperationException("RingElements of type "
				+ this.getClass().getCanonicalName() + " cannot be inverted! "
				+ this.getClass() + " objects cannot be inverted.");
	}

}
