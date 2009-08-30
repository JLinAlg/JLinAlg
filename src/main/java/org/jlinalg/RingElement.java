package org.jlinalg;

import java.io.Serializable;

/**
 * The basic implementation of some methods for most ring elements.
 * 
 * @author Andreas, Georg Thimm
 */
public abstract class RingElement
		implements IRingElement, Comparable<IRingElement>, Serializable
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

	public RingElement subtract(IRingElement val)
	{
		return (RingElement) this.add(val.negate());
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
	@Override
	public boolean equals(Object obj)
	{
		return this.compareTo((RingElement) obj) == 0;
	}

	/**
	 * Returns the result of applying a specified function to this
	 * FielkdElement. New functions can be applied to a RingElement by
	 * subclassing the abstract <tt>MonadicOperator</tt> class.
	 * 
	 * @param fun
	 *            the function to apply
	 * @return result of applying <tt>fun</tt> to this {@link RingElement}
	 */
	public <ARG extends IRingElement> RingElement apply(MonadicOperator<ARG> fun)
	{
		return (RingElement) fun.apply(this);
	}

	/**
	 * Returns absolute value of this element
	 * 
	 * @return the absolute value
	 */
	public RingElement abs()
	{
		return apply(AbsOperator.getInstance());
	}

	/**
	 * @return the absolute value of this fiels.
	 */
	public RingElement norm()
	{
		return this.abs();
	}

	/**
	 * Checks whether this RingElement is mathematically less than another.
	 * 
	 * @param val
	 * @return true if this RingElement is less than val, false otherwise
	 */
	public boolean lt(IRingElement val)
	{
		return compareTo(val) < 0;
	}

	/**
	 * Checks whether this RingElement is mathematically greater than another.
	 * 
	 * @param val
	 * @return true if this RingElement is greater than val, false otherwise
	 */
	public boolean gt(IRingElement val)
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
	public boolean le(IRingElement val)
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
	public boolean ge(IRingElement val)
	{
		return compareTo(val) >= 0;
	}

	public RingElement divide(IRingElement val) throws DivisionByZeroException
	{
		throw new InvalidOperationException("RingElements  of type "
				+ this.getClass().getCanonicalName() + " cannot be divided!");
	}

	@SuppressWarnings("unchecked")
	public RingElement invert() throws DivisionByZeroException
	{
		throw new InvalidOperationException("RingElements of type "
				+ this.getClass().getCanonicalName() + " cannot be inverted! "
				+ this.getClass() + " objects cannot be inverted.");
	}

}
