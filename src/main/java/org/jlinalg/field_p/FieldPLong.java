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

import org.jlinalg.InvalidOperationException;

/**
 * This class implements the operations in Fp where p &lt;
 * PRIME_SEPARATION_BOUNDARY. All computations can be done using long variables.
 * IMPORTANT: This class and its subclasses make use of the concept of
 * immutability of objects. If you make changes or subclass these classes,
 * ensure immutability or overwrite the methods affected.
 * 
 * @author Lochbihler Andreas, Georg Thimm
 */
public class FieldPLong
		extends
		FieldP
{
	private static final long serialVersionUID = 1L;

	/** The least non-negative instance of the equivalence class */
	protected long value;

	private FieldPLongFactory internalFactory;

	/**
	 * a cache for the inverse.
	 */
	private FieldPLong inverse;

	/**
	 * Returns a new element of the field Fp. The element is the equivalence
	 * class containing value. This method should only be called by the factory
	 * {@link FieldPAbstractFactory}
	 * 
	 * @param value
	 *            The new element is the equivalence class of value. This value
	 *            is assumed to be in the range of 0..(p-1).
	 * @param factory
	 *            the factory producing elements in Fp (typically the caller of
	 *            this constructor).
	 */
	FieldPLong(long value, FieldPAbstractFactory factory)
	{
		super(factory);
		this.value = value;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return (int) (value ^ (value << (Long.SIZE / 2)));
	}

	/**
	 * Computes the additive inverse
	 * 
	 * @return The additive inverse
	 */
	@Override
	public FieldP negate()
	{
		return getFactory().get(-value);
	}

	/**
	 * Calculates the sum of this element and another one from the same field.
	 * 
	 * @param val
	 *            The other element to add
	 * @return The sum of both elements
	 * @throws IllegalArgumentException
	 *             Thrown if you are trying to add elements from different
	 *             fields Fp.
	 */
	@Override
	public FieldP add(FieldP val) throws IllegalArgumentException
	{
		if (val.getFactory() == this.getFactory()) {
			return getFactory().get(getInternalValue().longValue()
					+ val.getInternalValue().longValue());
		}

		throw new IllegalArgumentException(val + " is from a different Fp than "
				+ this + "! You cannot add them.");
	}

	/**
	 * Calculates the product of this element and another one from the same
	 * field.
	 * 
	 * @param val
	 *            The other element to multiply
	 * @return The product of both elements
	 * @throws IllegalArgumentException
	 *             Thrown if you are trying to multiply elements from different
	 *             fields Fp.
	 */
	@Override
	public FieldP multiply(FieldP val) throws IllegalArgumentException
	{
		if (val.getFactory() == getFactory()) {
			return getFactory()
					.get(this.value * ((FieldPLong) val).getInternalValue());
		}
		throw new IllegalArgumentException(val + " is from a different Fp than "
				+ this + "! You cannot multiply them.");
	}

	@Override
	protected Long getInternalValue()
	{
		return value;
	}

	@Override
	public FieldPLong invert()
	{
		if (inverse == null) {
			inverse = getFactory().computeInverse(value);
			inverse.inverse = this;
		}
		return inverse;
	}

	@Override
	protected void setFactory(FieldPAbstractFactory factory)
	{
		internalFactory = (FieldPLongFactory) factory;
	}

	@Override
	protected <N extends Number> int compareInternalValueWith(N number)
	{
		return Long.compare(this.value, number.longValue());
	}

	@Override
	public FieldPLongFactory getFactory()
	{
		return internalFactory;
	}

	@Deprecated
	@Override
	public FieldP floor()
	{
		throw new InvalidOperationException(
				"The floor operator is meaningless for FieldPLong");
	}

}
