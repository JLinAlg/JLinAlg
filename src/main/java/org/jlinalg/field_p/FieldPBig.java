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
import org.jlinalg.JLinAlgTypeProperties;

/**
 * This class implements an element of Fp where p can be arbitrarily big by
 * using BigIntegers.
 * 
 * @author Andreas Lochbihler, Georg Thimm
 */
@JLinAlgTypeProperties(isExact = true, hasNegativeValues = false, isDiscreet = true)
public class FieldPBig
		extends
		FieldP
{
	private static final long serialVersionUID = 1L;

	/**
	 * The smallest non-negative representative of the equivalence class in Fp.
	 */
	BigInteger value;

	@Override
	protected BigInteger getInternalValue()
	{
		return value;
	}

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

	private FieldPBigFactory internalFactory;

	/**
	 * Constructs a new element of Fp which is the equivalence class containing
	 * value.
	 * 
	 * @param value
	 *            Any instance of the desired equivalence class. Value is
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
	public FieldPBig add(FieldP val)
	{
		if (val.getFactory() == getFactory()) {
			return getFactory()
					.get(value.add(((FieldPBig) val).getInternalValue()));
		}
		throw new IllegalArgumentException(
				val + " is from a different field Fp than " + this
						+ "! You cannot add them.");
	}

	/**
	 * Computes the additive inverse
	 * 
	 * @return The additive inverse
	 */
	@Override
	public FieldPBig negate()
	{
		return getFactory().get(value.negate());
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
	public FieldPBig invert()
			throws InvalidOperationException, ArithmeticException
	{
		if (this.isZero()) {
			throw new DivisionByZeroException("Multiplicative inversion of 0");
		}
		if (this.inverse == null) {
			this.inverse = getFactory()
					.get(value.modInverse(getFactory().getFieldSize()));
			inverse.inverse = this;
		}
		return inverse;
	}

	@Override
	public FieldPBigFactory getFactory()
	{
		return internalFactory;
	}

	@Override
	protected void setFactory(FieldPAbstractFactory factory)
	{
		internalFactory = (FieldPBigFactory) factory;
	}

	@Override
	protected <N extends Number> int compareInternalValueWith(N value)
	{
		if (value instanceof BigInteger) {
			return getInternalValue().compareTo((BigInteger) value);
		}
		throw new InvalidOperationException("Mismatching types");
	}

	@Override
	public FieldPBig multiply(FieldP val) throws IllegalArgumentException
	{
		if (val.getFactory() == getFactory()) {
			return getFactory()
					.get(value.multiply(((FieldPBig) val).getInternalValue()));
		}
		throw new IllegalArgumentException(val + " is from a different Fp than "
				+ this + "! You cannot multiply them.");
	}

	@Deprecated
	@Override
	public FieldP floor()
	{
		throw new InvalidOperationException(
				"The floor operator is meaningless for FieldP");
	}
}
