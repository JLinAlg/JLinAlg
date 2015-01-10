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

import java.math.BigInteger;

import org.jlinalg.DivisionByZeroException;
import org.jlinalg.InvalidOperationException;

/**
 * This class implements an element of Fp where p can be arbitrarily big by
 * using BigIntegers.
 * 
 * @author Andreas Lochbihler, Georg Thimm
 */
class FieldPBig
		extends FieldP<FieldPBig>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The smallest non-negative representative of the equivalence class in Fp.
	 */
	BigInteger value;

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
	FieldPBig(BigInteger value, FieldPAbstractFactory<FieldPBig> factory)
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
	public FieldPBig add(FieldPBig val)
	{
		if (val.factory == factory) {
			return factory.get(value.add(val.value));
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
	public FieldPBig multiply(FieldPBig val)
	{
		if (val.factory == factory) {
			return factory.get(this.value.multiply(val.value));
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
		return factory.get(value.negate());
	}

	/**
	 * Computes the multiplicative inverse
	 * 
	 * @return The multiplicative inverse
	 * @throws DivisionByZeroException
	 *             Thrown if trying to invert 0.
	 * @throws ArithmeticException
	 *             Thrown if value is is not relatively prime to p.
	 */
	@Override
	public FieldPBig invert() throws InvalidOperationException,
			ArithmeticException
	{
		if (this.isZero()) {
			throw new DivisionByZeroException("Multiplicative inversion of 0");
		}
		if (this.inverse == null) {
			this.inverse = factory.get(value
					.modInverse(((FieldPBigFactory) factory).p));
			inverse.inverse = this;
		}
		return inverse;
	}

	/**
	 * Compares this element with another element of the same field Fp. Note:
	 * This order does not respect addition or multiplication!
	 * 
	 * @param par
	 *            The element to compare to
	 * @return -1, if this is less, 0, if they are equal, 1, if this is bigger
	 */
	@Override
	public int compareTo(FieldPBig par)
	{
		if (par.factory == factory) {
			return value.compareTo(par.value);
		}
		throw new IllegalArgumentException(par
				+ " is from a differend field than " + this
				+ "! You cannot compare them");
	}

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

	/**
	 * return the String representation of the encapsulated value.
	 */
	@Override
	public String toString()
	{
		return value.toString();
	}

}
