package org.jlinalg.field_p;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

import org.jlinalg.InvalidOperationException;

/**
 * @author Andreas Lochbihler, Georg Thimm, Andreas Keilhauer
 */
public class FieldPBigFactory
		extends FieldPAbstractFactory<FieldPBig>
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
	final BigInteger p;

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
	public FieldPBig randomValue()
	{
		BigInteger r = new BigDecimal(p)
				.multiply(new BigDecimal(Math.random())).toBigInteger();
		return new FieldPBig(r, this);
	}

	/**
	 * @deprecated use {@link #randomValue()}
	 */
	@SuppressWarnings("deprecation")
	@Override
	@Deprecated
	public FieldPBig randomValue(@SuppressWarnings("unused") Random random)
	{
		return randomValue();
	}

	/**
	 * @throws InvalidOperationException
	 *             if called
	 */
	@SuppressWarnings("deprecation")
	@Override
	@Deprecated
	public FieldPBig gaussianRandomValue(
			@SuppressWarnings("unused") Random random)
	{
		throw new InvalidOperationException(
				"Can not calculate gaussian random values for Fp.");
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
			return new FieldPBig(((BigInteger) o).mod(p), this);
		}
		if (o instanceof Number) {
			Number n = (Number) o;
			if (n.longValue() == 1) return ONE;
			if (n.longValue() == -1) return M_ONE;
			if (n.longValue() == 0) return ZERO;
			return new FieldPBig(BigInteger.valueOf(n.longValue()).mod(p), this);
		}
		if (o instanceof String) {
			if (o.equals("0")) return ZERO;
			if (o.equals("1")) return ONE;
			if (o.equals("-1")) return M_ONE;
			return new FieldPBig(new BigInteger((String) o).mod(p), this);
		}
		return get(o.toString());
	}

	/**
	 * @see org.jlinalg.RingElementFactory#get(int)
	 */
	@Override
	public FieldPBig get(int i)
	{
		return new FieldPBig(BigInteger.valueOf(i).mod(p), this);
	}

	/**
	 * @see org.jlinalg.RingElementFactory#get(double)
	 */
	@Override
	public FieldPBig get(double d)
	{
		return new FieldPBig(BigInteger.valueOf((long) d).mod(p), this);
	}

	/**
	 * @see org.jlinalg.IRingElementFactory#get(long)
	 */
	public FieldPBig get(long d)
	{
		return new FieldPBig(BigInteger.valueOf(d).mod(p), this);
	}

	/**
	 * @throws UnsupportedOperationException
	 *             if called
	 */
	@SuppressWarnings("deprecation")
	@Override
	@Deprecated
	public FieldPBig randomValue(@SuppressWarnings("unused") Random random,
			@SuppressWarnings("unused") FieldPBig min,
			@SuppressWarnings("unused") FieldPBig max)
	{
		throw new UnsupportedOperationException();
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
	 * @throws UnsupportedOperationException
	 */
	@Override
	@Deprecated
	public FieldPBig randomValue(@SuppressWarnings("unused") FieldPBig min,
			@SuppressWarnings("unused") FieldPBig max)
	{
		throw new UnsupportedOperationException();
	}

}
