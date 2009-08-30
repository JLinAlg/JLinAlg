/**
 * 
 */
package org.jlinalg.field_p;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

import org.jlinalg.IRingElement;
import org.jlinalg.InvalidOperationException;

/**
 * @author Andreas Lochbihler, Georg Thimm, Andreas Keilhauer
 */
public class FieldPBigFactory
		extends FieldPAbstractFactory
{

	/**
	 * @return true if {@code obj} is of the same type and are based on the same
	 *         number.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (!(obj instanceof FieldPBigFactory)) return false;
		return p.equals(((FieldPBigFactory) obj).p);
	}

	/**
	 * The number of elements in Fp. Must be a prime.
	 */
	BigInteger p;

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
	 *            to the user to ensure that p^2<Long.MAX_VALUE otherwise
	 *            overflow problems may occur. p is presumed to be prime
	 */
	public FieldPBigFactory(BigInteger p)
	{
		this.p = p;
		ZERO = new FieldPBig(BigInteger.ZERO, this);
		ONE = new FieldPBig(BigInteger.ONE, this);
		M_ONE = ONE.negate();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see JLinAlg.FieldElement#m_one()
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
	public FieldPBig randomValue()
	{
		BigInteger r = new BigDecimal(p)
				.multiply(new BigDecimal(Math.random())).toBigInteger();
		return new FieldPBig(r, this);
	}

	/**
	 * Creates a random value (by mapping a random double value 0<=dval<1 to a
	 * element of FieldP)
	 * 
	 * @param random
	 *            the random number generator.
	 * @return a random element.
	 */
	@Override
	@Deprecated
	public FieldPBig randomValue(Random random)
	{
		return new FieldPBig(new BigDecimal(this.p).multiply(
				new BigDecimal(random.nextDouble())).toBigInteger(), this);
	}

	/**
	 * @throws InvalidOperationException
	 *             if called
	 */
	@Override
	@Deprecated
	public FieldPBig gaussianRandomValue(Random random)
	{
		throw new InvalidOperationException(
				"Can not calculate gaussian random values for Fp.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.RingElementFactory#get(java.lang.Object)
	 */
	@Override
	public FieldPBig get(Object o)
	{
		if (o instanceof BigInteger) {
			if (o.equals(BigInteger.ZERO)) return ZERO;
			if (o.equals(BigInteger.ONE)) return ONE;
			if (((BigInteger) o).intValue() == -1) return M_ONE;
			return new FieldPBig(((BigInteger) o).mod(p), this);
		}
		if (o instanceof Number) {
			Number n = (Number) o;
			if (n.longValue() == 1) return ONE;
			if (n.longValue() == -1) return M_ONE;
			if (n.longValue() == 0) return ZERO;
			return new FieldPBig(BigInteger.valueOf(n.longValue()).mod(p), this);
		}
		throw new InvalidOperationException("Cannot translate " + o
				+ " of class " + o.getClass() + " into a FieldPBig type.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.RingElementFactory#get(int)
	 */
	@Override
	public FieldP get(int i)
	{
		return new FieldPBig(BigInteger.valueOf(i).mod(p), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.RingElementFactory#get(double)
	 */
	@Override
	public FieldP get(double d)
	{
		return new FieldPBig(BigInteger.valueOf((long) d).mod(p), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.IRingElementFactory#get(long)
	 */
	public FieldP get(long d)
	{
		return new FieldPBig(BigInteger.valueOf(d).mod(p), this);
	}

	/**
	 * @throws InvalidOperationException
	 *             if called
	 * @see org.jlinalg.RingElementFactory#randomValue(Random, IRingElement,
	 *      IRingElement)
	 */
	@Override
	@Deprecated
	public FieldP randomValue(Random random, IRingElement min, IRingElement max)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * @throws InvalidOperationException
	 *             if called
	 */
	@Override
	@Deprecated
	public FieldP gaussianRandomValue()
	{
		throw new InvalidOperationException(
				"Can not calculate gaussian random values for Fp.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jlinalg.IRingElementFactory#randomValue(org.jlinalg.IRingElement,
	 * org.jlinalg.IRingElement)
	 */
	@Override
	@Deprecated
	public FieldP randomValue(IRingElement min, IRingElement max)
	{
		throw new UnsupportedOperationException();
	}

}
