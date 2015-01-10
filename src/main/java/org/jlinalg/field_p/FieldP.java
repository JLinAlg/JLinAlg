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
package org.jlinalg.field_p;

import org.jlinalg.FieldElement;
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
public abstract class FieldP<RE extends FieldP<RE>>
		extends FieldElement<RE>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the factory for the creation of other elements of this type.
	 */
	final FieldPAbstractFactory<RE> factory;

	/**
	 * constructor for internal use only.
	 * 
	 * @param factory
	 *            the factory to be used for the creation of subsequent
	 *            instances of specializations this class.
	 */
	FieldP(FieldPAbstractFactory<RE> factory)
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
	@Override
	public abstract RE add(RE val);

	/**
	 * Multiplies this element with val and returns the new element of Fp.
	 * 
	 * @param val
	 *            The second operand. Must be of the same field Fp.
	 * @return The element that is the sum of this and val.
	 */
	@Override
	public abstract RE multiply(RE val);

	/**
	 * Returns the additive inverse of this in the field Fp.
	 * 
	 * @return The additive inverse of this in the field Fp.
	 */
	@Override
	public abstract RE negate();

	/**
	 * Returns the multiplicative inverse of this in the field Fp.
	 * 
	 * @return The multiplicative inverse of this in the field Fp.
	 */
	@Override
	public abstract RE invert();

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
	@Override
	public abstract int compareTo(RE o);

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

	@Override
	public IRingElementFactory<RE> getFactory()
	{
		return factory;
	}

	/**
	 * @return {@code this}
	 * @see org.jlinalg.IRingElement#abs()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public RE abs()
	{
		return (RE) this;
	}
}
