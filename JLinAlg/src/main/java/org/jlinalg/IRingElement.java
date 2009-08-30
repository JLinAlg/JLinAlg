/**
 * 
 */
package org.jlinalg;

/**
 * @author Georg Thimm
 */
public interface IRingElement
{
	/**
	 * Tests if this RingElement is the neutral element of addition (zero).
	 * 
	 * @return true if zero
	 */
	public boolean isZero();

	/**
	 * Calculates the sum of this RingElement and another one.
	 * 
	 * @param other
	 * @return sum
	 */
	public IRingElement add(IRingElement other);

	/**
	 * Calculates the inverse element of addition for this IRingElement.
	 * 
	 * @return negated
	 */
	public IRingElement negate();

	/**
	 * Calculates the difference of this IRingElement and another one.
	 * 
	 * @param val
	 * @return difference
	 */
	public IRingElement subtract(IRingElement val);

	/**
	 * Tests if this IRingElement is the neutral element of multiplication
	 * (one).
	 * 
	 * @return true if one
	 */
	public boolean isOne();

	/**
	 * Calculates the product of this IRingElement and another one.
	 * 
	 * @param other
	 * @return product
	 */
	public IRingElement multiply(IRingElement other);

	/**
	 * Determines whether or not two IRingElements are equal.
	 * 
	 * @param obj
	 * @return true if the two IRingElements are mathematically equal.
	 */

	public boolean equals(Object obj);

	/**
	 * @param o
	 *            the object
	 * @return -,0,+} as this object is less than, equal to, or greater than the
	 *         specified object.
	 */
	public int compareTo(IRingElement o);

	/**
	 * Returns the result of applying a specified function to this
	 * FielkdElement. New functions can be applied to a IRingElement by
	 * subclassing the abstract <tt>MonadicOperator</tt> class.
	 * 
	 * @param fun
	 *            the function to apply
	 * @return result of applying <tt>fun</tt> to this {@link IRingElement}
	 */
	public <ARG extends IRingElement> IRingElement apply(
			MonadicOperator<ARG> fun);

	/**
	 * Returns absolute value of this element
	 * 
	 * @return the absolute value.
	 */
	public IRingElement abs();

	/**
	 * @return the norm of this element.
	 */
	public IRingElement norm();

	/**
	 * Checks whether this IRingElement is mathematically less than another.
	 * 
	 * @param val
	 * @return true if this IRingElement is less than val, false otherwise
	 */
	public boolean lt(IRingElement val);

	/**
	 * Checks whether this IRingElement is mathematically greater than another.
	 * 
	 * @param val
	 * @return true if this IRingElement is greater than val, false otherwise
	 */
	public boolean gt(IRingElement val);

	/**
	 * Checks whether this IRingElement is mathematically less than or equal to
	 * another.
	 * 
	 * @param val
	 * @return true if this IRingElement is less than or equal to val, false
	 *         otherwise
	 */
	public boolean le(IRingElement val);

	/**
	 * Checks whether this IRingElement is mathematically greater than or equal
	 * to another.
	 * 
	 * @param val
	 * @return true if this IRingElement is greater than or equal to val, false
	 *         otherwise
	 */
	public boolean ge(IRingElement val);

	/**
	 * Inverts this ring element (divides one by it)
	 * 
	 * @param <T>
	 *            the type of an inverse. This may be different to the type of
	 *            the inverted type.
	 * @return the inverse of this ring element
	 * @throws DivisionByZeroException
	 *             if this is equal to the zero element.
	 */
	public <T extends IRingElement> T invert() throws DivisionByZeroException;

	/**
	 * @param val
	 * @return this divided by val
	 * @throws DivisionByZeroException
	 */
	public IRingElement divide(IRingElement val) throws DivisionByZeroException;

	/**
	 * Give access to the factory for this type. This is to replace in a
	 * consistent manner the constructors of
	 * 
	 * @return a factory for creating instances of classes implementing
	 *         IRingElement
	 */
	public IRingElementFactory<? extends IRingElement> getFactory();

}
