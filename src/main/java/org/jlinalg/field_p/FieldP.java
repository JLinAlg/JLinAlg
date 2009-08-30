package org.jlinalg.field_p;

import org.jlinalg.FieldElement;
import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;

/**
 * This class represents an element of the modulo p field Fp (i.e. the Galois
 * field GF(p)) where p is prime. Fp is a field like any other field but there
 * is no order that respects addition.
 * 
 * @author Andreas Lochbihler, Georg Thimm, Andreas Keilhauer
 */
/*
 * This is only a wrapper class for the different implementations for
 * FieldPAbstract depending on the magnitude of p.
 */
public abstract class FieldP
		extends FieldElement
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the factory for the creation of other elements of this type.
	 */
	final FieldPAbstractFactory factory;

	/**
	 * constructor for internal use only.
	 * 
	 * @param factory
	 *            the factory to be used for the creation of subsequent
	 *            instances of specializations this class.
	 */
	FieldP(FieldPAbstractFactory factory)
	{
		this.factory = factory;
	}

	/**
	 * Adds this element to val and returns the new element of Fp.
	 * 
	 * @param val
	 *            The second operand. Must be of the same field Fp.
	 * @return The element that is the sum of this and val.
	 */
	public abstract FieldP add(IRingElement val);

	/**
	 * Multiplies this element with val and returns the new element of Fp.
	 * 
	 * @param val
	 *            The second operand. Must be of the same field Fp.
	 * @return The element that is the sum of this and val.
	 */
	public abstract FieldP multiply(IRingElement val);

	/**
	 * Returns the additive inverse of this in the field Fp.
	 * 
	 * @return The additive inverse of this in the field Fp.
	 */
	public abstract FieldP negate();

	/**
	 * Returns the multiplicative inverse of this in the field Fp.
	 * 
	 * @return The multiplicative inverse of this in the field Fp.
	 */
	@Override
	public abstract FieldP invert();

	/**
	 * Compares this element with another element. o must be a instance of
	 * FieldP and its field must have the same number of elements as this' one.
	 * A element of Fp is considered to be smaller than a other element if and
	 * only if its smallest nonnegative representative is smaller than the
	 * other's one.
	 * 
	 * @param o
	 *            The object to compare to. Must be a instance of FieldP and of
	 *            the same field Fp as this.
	 * @return &lt; 0 if this is smaller than o, = 0 if this is equal to o (in
	 *         the sense of equals), &gt; 0 if this is bigger than o
	 */
	public abstract int compareTo(IRingElement o);

	/**
	 * Returns true if and only if <code>this</code> and <code>o</code>
	 * represent the same element of the same field.
	 * 
	 * @param e
	 *            The element to compare to.
	 * @return True if and only if this and o are the same equivalence class of
	 *         the same field.
	 */

	@Override
	public abstract boolean equals(Object e);

	public IRingElementFactory<? extends IRingElement> getFactory()
	{
		return factory;
	}

}
