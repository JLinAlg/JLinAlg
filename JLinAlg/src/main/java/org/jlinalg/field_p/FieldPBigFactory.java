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

import java.math.BigDecimal;
import java.math.BigInteger;

import org.jlinalg.InvalidOperationException;

/**
 * @author Andreas Lochbihler, Georg Thimm, Andreas Keilhauer
 */
public class FieldPBigFactory
		extends
		FieldPAbstractFactory
{
	private static final long serialVersionUID = 1L;

	/**
	 * @return true if {@code obj} is of the same type and are based on the same
	 *         number.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (!(obj instanceof FieldPBigFactory)) return false;
		return fieldSize.equals(((FieldPBigFactory) obj).fieldSize);
	}

	@Override
	public int hashCode()
	{
		return fieldSize.hashCode();
	}

	/**
	 * The number of elements in Fp. Must be a prime.
	 */
	private final BigInteger fieldSize;

	/**
	 * the zero element
	 */
	private final FieldPBig ZERO;

	/**
	 * the one element
	 */
	private final FieldPBig ONE;

	/**
	 * the -1 element
	 */
	private final FieldPBig M_ONE;

	/**
	 * Creates a new element of the field Fp.
	 * 
	 * @param p
	 *            The number of elements in the Field. p must be prime. It is up
	 *            to the user to ensure that p^2&lt;Long.MAX_VALUE otherwise
	 *            overflow problems may occur. p is presumed to be prime
	 */
	public FieldPBigFactory(BigInteger p)
	{
		fieldSize = p;
		ZERO = new FieldPBig(BigInteger.ZERO, this);
		ONE = new FieldPBig(BigInteger.ONE, this);
		M_ONE = new FieldPBig(p.subtract(BigInteger.ONE), this);
	}

	/**
	 * Returns the one element of the field Fp
	 * 
	 * @return The one element of the field Fp
	 */
	@Override
	public FieldPBig one()
	{
		return ONE;
	}

	/**
	 * @return The zero element of the field Fp
	 */
	@Override
	public FieldPBig zero()
	{
		return ZERO;
	}

	/**
	 * @see org.jlinalg.IRingElementFactory#m_one()
	 */
	@Override
	public FieldPBig m_one()
	{
		return M_ONE;
	}

	/**
	 * Returns a pseudo-random element. All elements are approximately equally
	 * probable.
	 * 
	 * @return a pseudo-random element
	 */
	@Override
	public FieldPBig randomValue()
	{
		BigInteger r = new BigDecimal(fieldSize)
				.multiply(new BigDecimal(Math.random())).toBigInteger();
		return new FieldPBig(r, this);
	}

	/**
	 * @see org.jlinalg.RingElementFactory#get(java.lang.Object)
	 */
	@Override
	public FieldPBig get(Object o)
	{
		if (o instanceof BigInteger) {
			if (o.equals(BigInteger.ZERO)) return ZERO;
			if (o.equals(BigInteger.ONE)) return ONE;
			if (((BigInteger) o).equals(M_ONE.value)) return M_ONE;
			return new FieldPBig(((BigInteger) o).mod(fieldSize), this);
		}
		if (o instanceof Number) {
			Number n = (Number) o;
			if (n.longValue() == 1) return ONE;
			if (n.longValue() == -1) return M_ONE;
			if (n.longValue() == 0) return ZERO;
			return new FieldPBig(
					BigInteger.valueOf(n.longValue()).mod(fieldSize), this);
		}
		if (o instanceof String) {
			if (o.equals("0")) return ZERO;
			if (o.equals("1")) return ONE;
			if (o.equals("-1")) return M_ONE;
			return new FieldPBig(new BigInteger((String) o).mod(fieldSize),
					this);
		}
		return get(o.toString());
	}

	/**
	 * @see org.jlinalg.RingElementFactory#get(int)
	 */
	@Override
	public FieldPBig get(int i)
	{
		return new FieldPBig(BigInteger.valueOf(i).mod(fieldSize), this);
	}

	/**
	 * @see org.jlinalg.RingElementFactory#get(double)
	 */
	@Override
	public FieldPBig get(double d)
	{
		return new FieldPBig(BigInteger.valueOf((long) d).mod(fieldSize), this);
	}

	/**
	 * @see org.jlinalg.IRingElementFactory#get(long)
	 */
	@Override
	public FieldPBig get(long d)
	{
		return new FieldPBig(BigInteger.valueOf(d).mod(fieldSize), this);
	}

	/**
	 * @throws InvalidOperationException
	 *             if called
	 */
	@Override
	@Deprecated
	public FieldPBig gaussianRandomValue()
	{
		throw new InvalidOperationException(
				"Can not calculate gaussian random values for Fp.");
	}

	/**
	 * @return a description of the factory
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Factory: " + getClass().getName() + " p=" + fieldSize;
	}

	@SuppressWarnings("unused")
	@Override
	@Deprecated
	public FieldP randomValue(FieldP min, FieldP max)
	{
		throw new InvalidOperationException(
				"randomValue(IRingElement min, IRingElement max) is not implemented.");
	}

	@Override
	public BigInteger getFieldSize()
	{
		return fieldSize;
	}
}