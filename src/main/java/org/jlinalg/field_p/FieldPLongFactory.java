package org.jlinalg.field_p;

import java.util.Random;

import org.jlinalg.IRingElement;
import org.jlinalg.InvalidOperationException;

/**
 * An implementation of a factory for FieldP's using "long"s as internal number
 * type.
 * 
 * @author Andreas Lochbihler, Georg Thimm, Andreas Keilhauer
 */
public class FieldPLongFactory
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
		if (!(obj instanceof FieldPLongFactory)) return false;
		return p == ((FieldPLongFactory) obj).p;
	}

	/**
	 * the internal value for an instance
	 */
	final long p;

	/**
	 * the constant 0
	 */
	private final FieldPLong ZERO;

	/**
	 * the constant 1
	 */
	private final FieldPLong ONE;

	/**
	 * the constant -1
	 */
	private final FieldPLong M_ONE;

	/**
	 * Creates a new element of the field Fp.
	 * 
	 * @param p
	 *            The number of elements in the Field. p must be prime. It is up
	 *            to the user to ensure that p^2<Long.MAX_VALUE otherwise
	 *            overflow problems may occur. p is presumed to be prime
	 */
	protected FieldPLongFactory(final long p)
	{
		this.p = p;
		ZERO = new FieldPLong(0L, this);
		ONE = new FieldPLong(1L, this);
		M_ONE = ONE.negate();
	}

	/**
	 * Returns the zero element of the field Fp
	 * 
	 * @return The zero element of the field Fp
	 */
	@Override
	public FieldPLong zero()
	{
		return ZERO;
	}

	/**
	 * Returns the one element of the field Fp
	 * 
	 * @return The one element of the field Fp
	 */
	@Override
	public FieldPLong one()
	{
		return ONE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see JLinAlg.FieldElement#m_one()
	 */
	@Override
	public FieldPLong m_one()
	{
		return M_ONE;
	}

	/**
	 * @throws InvalidOperationException
	 *             if called
	 */
	@Override
	@Deprecated
	public FieldPLong gaussianRandomValue(Random random)
	{
		throw new InternalError("not implemented");
	}

	@Override
	public FieldPLong get(double d)
	{
		return new FieldPLong(normalize((long) d, p), this);
	}

	@Override
	public FieldPLong get(int i)
	{
		return new FieldPLong(normalize(i, p), this);
	}

	@Override
	public FieldPLong get(Object o)
	{
		if (o instanceof Number) {
			return new FieldPLong(normalize(((Number) o).longValue(), p), this);
		}
		throw new InvalidOperationException("Cannot convert " + o + "in class "
				+ o.getClass() + " to type FieldPLong");
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
	public FieldPLong randomValue(Random random)
	{
		return new FieldPLong((long) (p * random.nextDouble()), this);
	}

	/**
	 * Normalizes val with respect to p. This means, it computes 0<=r
	 * <p
	 * such that p divides v-r
	 * 
	 * @param val
	 *            The value to normalize
	 * @param p
	 *            The number of elements in Fp
	 * @return The normalized value of val in Fp.
	 */
	protected static long normalize(long val, long p)
	{
		val = val % p;
		while (val < 0) {
			val += p;
		}
		return val;
	}

	/**
	 * Computes the inverse in Fp.
	 * 
	 * @param val
	 *            The value whose inverse is to be computed
	 * @param p
	 *            The number of elements in the field.
	 * @return The inverse of val in Fp.
	 */
	protected static long computeInverse(long val, long p)
	{
		long a = p;
		long b = val;

		long w = 1;
		long x = 1;
		long y = 0;
		long z = 0;

		long r = 0;
		long q = 0;

		long new_z, new_w;

		// The invariant is x*p + y*value = a and z*p + w*value = b
		while (b != 0) {
			r = a % b;
			q = a / b;

			// swap variables
			a = b;
			b = r;

			new_z = x - q * z;
			new_w = y - q * w;
			x = z;
			y = w;
			z = new_z;
			w = new_w;
		}

		if (a != 1) {
			throw new InvalidOperationException(val + " is not invertible in F"
					+ p);
		} // else {
		// normalize y
		while (y < 0) {
			y += p;
		}
		return y;
		// }
	}

	/**
	 * Determine the inverse for this value. Used by {@link FieldPLong#invert()}
	 * 
	 * @param value
	 * @return the inverse of value
	 */
	protected FieldPLong computeInverse(long value)
	{
		long inverse = computeInverse(value, p);
		return new FieldPLong(inverse, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.IRingElementFactory#get(long)
	 */
	public FieldPLong get(long d)
	{
		d = normalize(d, p);
		return new FieldPLong(d, this);
	}

	/**
	 * @return a random value inclusive of min and max.
	 * @see org.jlinalg.IRingElementFactory#randomValue(Random, IRingElement,
	 *      IRingElement)
	 */
	@Override
	@Deprecated
	public FieldPLong randomValue(Random random, IRingElement min_,
			IRingElement max_)
	{
		long min = ((FieldPLong) min_).value;
		long max = ((FieldPLong) max_).value;
		long r = random.nextLong();
		if (r < 0L) r = -r;
		long l = (r % (max - min + 1)) + min;
		return get(l);
	}

	/**
	 * @throws InvalidOperationException
	 *             if called
	 */
	@Override
	@Deprecated
	public FieldP gaussianRandomValue()
	{
		throw new InternalError("not implemented");
	}

	/**
	 * @return a random value inclusive of min and max.
	 * @see org.jlinalg.IRingElementFactory#randomValue(Random, IRingElement,
	 *      IRingElement)
	 */
	@Override
	public FieldP randomValue(IRingElement min_, IRingElement max_)
	{
		long min = ((FieldPLong) min_).value;
		long max = ((FieldPLong) max_).value;
		long r = random.nextLong();
		if (r < 0L) r = -r;
		long l = (r % (max - min + 1)) + min;
		return get(l);
	}

	/**
	 * Creates a random value (by mapping a random double value 0<=dval<1 to a
	 * element of FieldP)
	 * 
	 * @return a random element.
	 */
	@Override
	public FieldP randomValue()
	{
		return new FieldPLong((long) (p * random.nextDouble()), this);
	}

}
