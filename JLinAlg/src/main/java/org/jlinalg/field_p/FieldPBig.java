package org.jlinalg.field_p;

import java.math.BigInteger;

import org.jlinalg.IRingElement;
import org.jlinalg.InvalidOperationException;

/**
 * This class implements an element of Fp where p can be arbitrarily big by
 * using BigIntegers.
 * 
 * @author Andreas Lochbihler, Georg Thimm
 */
class FieldPBig
		extends FieldP
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The smallest nonnegative representant of the equivalence class in Fp.
	 */
	private BigInteger value;

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return value.hashCode();
	}

	/**
	 * If already computed, the multiplicative inverse of value. null otherwise
	 */
	private FieldPBig inverse = null;

	/**
	 * Constructs a new element of Fp which is the equivalence class containing
	 * value.
	 * 
	 * @param value
	 *            Any representant of the desired equivalence class. Value is
	 *            assumed to be in the range 1..(p-1). For internal use only.
	 * @param factory
	 *            the factory producing elements in Fp (typically the caller of
	 *            this constructor).
	 */
	FieldPBig(BigInteger value, FieldPAbstractFactory factory)
	{
		super(factory);
		this.value = value;
	}

	/**
	 * Returns the sum of this and val.
	 * 
	 * @param val
	 *            The other operand.
	 * @return The sum of this and val.
	 */
	@Override
	public FieldPBig add(IRingElement val)
	{
		FieldPBig op = (FieldPBig) val;
		if (op.factory == factory) {
			return (FieldPBig) factory.get(value.add(op.value));
		}
		throw new IllegalArgumentException(val
				+ " is from a different field Fp than " + this
				+ "! You cannot add them.");
	}

	/**
	 * Returns the product of this and val.
	 * 
	 * @param val
	 *            The other factor
	 * @return The product of this and val.
	 */
	@Override
	public FieldPBig multiply(IRingElement val)
	{
		FieldPBig op = (FieldPBig) val;
		if (op.factory == factory) {
			return (FieldPBig) factory.get(this.value.multiply(op.value));
		}
		throw new IllegalArgumentException(val
				+ " is from a different field Fp than " + this
				+ "! You cannot multiply them.");
	}

	/**
	 * Computes the additive inverse
	 * 
	 * @return The additive inverse
	 */
	@Override
	public FieldPBig negate()
	{
		return (FieldPBig) factory.get(value.negate());
	}

	/**
	 * Computes the multiplicative inverse
	 * 
	 * @return The multiplicative inverse
	 * @throws InvalidOperationException
	 *             Thrown if trying to inverst 0.
	 * @throws ArithmeticException
	 *             Thrown if value is is not relatively prime to p.
	 */
	@Override
	public FieldPBig invert() throws InvalidOperationException,
			ArithmeticException
	{
		if (this.isZero()) {
			throw new InvalidOperationException("Multiplicative inversion of 0");
		}
		if (this.inverse == null) {
			this.inverse = (FieldPBig) factory.get(value
					.modInverse(((FieldPBigFactory) factory).p));
			inverse.inverse = this;
		}
		return inverse;
	}

	/**
	 * Compares this element with another element of the same field Fp. Note:
	 * This order does not respect addition or multiplication!
	 * 
	 * @param o
	 *            The element to compare to
	 * @return -1, if this is less, 0, if they are equal, 1, if this is bigger
	 */
	@Override
	public int compareTo(IRingElement o)
	{
		FieldPBig par = (FieldPBig) o;
		if (par.factory == factory) {
			return value.compareTo(par.value);
		}
		throw new IllegalArgumentException(o
				+ " is from a differend field than " + this
				+ "! You cannot compare them");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.FieldP#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o)
	{
		FieldPBig f = (FieldPBig) o;
		if (f.factory != factory) {
			throw new IllegalArgumentException(
					"Cannot compare elements in different fields");
		}
		return f.value.equals(value);
	}

}
