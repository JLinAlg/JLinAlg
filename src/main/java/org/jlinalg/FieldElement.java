package org.jlinalg;

/**
 * This class represents an element of an arbitrary field. It has to have four
 * operations (add, subtract, multiply, divide) a neutral element of addition
 * (zero) and a neutral element of multiplication (one). Concrete number types
 * (fields) can be easily implemented by extending this class.<BR>
 * <STRONG>Note!</STRONG> Performance Issue<BR>
 * Most non abstract methods in this class were only implemented to make the
 * process of implementing a new FieldElement convenient and short. These
 * methods should be overwritten, whenever one is more interested in performance
 * than in just getting something that works.
 * <P>
 * <STRONG>Note!</STRONG> FieldElements are presumably immutable. Extensions to
 * this class should not allow a modification after instantiation!
 * <P>
 * 
 * @author Andreas Keilhauer, Simon D. Levy
 */

@SuppressWarnings("serial")
public abstract class FieldElement
		extends RingElement
		implements IRingElement
{

	/*
	 * @param val the value to be subtracted from this element.
	 * 
	 * @return difference
	 */
	@Override
	public FieldElement subtract(IRingElement val)
	{
		return (FieldElement) this.add(val.negate());
	}

	/**
	 * Calculates the quotient of this FieldElement and another one.
	 * 
	 * @param val
	 * @return quotient
	 */
	@Override
	public FieldElement divide(IRingElement val) throws DivisionByZeroException
	{
		if (val.isZero()) {
			throw new DivisionByZeroException("Tried to divide " + this
					+ " by " + val + ".");
		}
		return (FieldElement) this.multiply(val.invert());
	}

	/**
	 * @return the absolute value
	 */
	@Override
	public FieldElement abs()
	{
		return (FieldElement) this.apply(AbsOperator.getInstance());
	}

	/**
	 * @return the absolute value of this field.
	 * @see #abs()
	 */
	@Override
	public FieldElement norm()
	{
		return this.abs();
	}
}
