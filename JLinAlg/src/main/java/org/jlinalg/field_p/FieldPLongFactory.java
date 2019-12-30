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

import org.jlinalg.IRingElement;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.JLinAlgTypeProperties;

/**
 * An implementation of a factory for FieldP's using "long"s as internal number
 * type.
 * 
 * @author Andreas Lochbihler, Georg Thimm, Andreas Keilhauer
 */
@JLinAlgTypeProperties(isExact = true, hasNegativeValues = false, isDiscreet = true)
public class FieldPLongFactory
		extends
		FieldPAbstractFactory<FieldPLong>
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
		if (!(obj instanceof FieldPLongFactory)) return false;
		return p == ((FieldPLongFactory) obj).p;
	}

	@Override
	public int hashCode()
	{
		return (int) (p ^ p >> 16);
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
	 *            to the user to ensure that p^2&lt;Long.MAX_VALUE otherwise
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

	@Override
	public FieldPLong m_one()
	{
		return M_ONE;
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
		if (o instanceof String) {
			try {
				long l = Long.parseLong((String) o);
				return new FieldPLong(normalize(l, p), this);
			} catch (NumberFormatException e) {
				throw new InvalidOperationException(
						o + " is not a long number");
			}
		}
		return get(o.toString());
	}

	/**
	 * Normalizes val with respect to p. This means, it computes 0&lt;=r
	 * &lt;p
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
	 * @exception InvalidOperationException
	 *                if val cannot be inverted
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
			throw new InvalidOperationException(
					val + " is not invertible in F" + p);
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

	@Override
	public FieldPLong get(long d)
	{
		d = normalize(d, p);
		return new FieldPLong(d, this);
	}

	/**
	 * @throws InvalidOperationException
	 * @Deprecated as not implemented
	 */
	@Override
	@Deprecated
	public FieldPLong gaussianRandomValue()
	{
		throw new InvalidOperationException("not implemented");
	}

	/**
	 * @return a random value inclusive of min and max.
	 * @see org.jlinalg.IRingElementFactory#randomValue(IRingElement,
	 *      IRingElement)
	 */
	@Override
	public FieldPLong randomValue(FieldPLong min_, FieldPLong max_)
	{
		long min = min_.value;
		long max = max_.value;
		long r = random.nextLong();
		if (r < 0L) r = -r;
		long l = (r % (max - min + 1)) + min;
		return get(l);
	}

	/**
	 * Creates a random value (by mapping a random double value 0&lt;=dval&lt;1
	 * to a element of FieldP)
	 * 
	 * @return a random element.
	 */
	@Override
	public FieldPLong randomValue()
	{
		return new FieldPLong((long) (p * random.nextDouble()), this);
	}

	/**
	 * @return a description of the factory
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Factory: " + getClass().getName() + " p=" + p;
	}
}
