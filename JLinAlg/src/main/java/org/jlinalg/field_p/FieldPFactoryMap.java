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
import java.util.TreeMap;

/**
 * Cash for instances of polynomial factories. In order to obtain a factory for
 * a p-field of a given base type use the method {@link #get(Object)} with an
 * instance of {@link Long} as argument.
 * 
 * @author Georg THimm (2008)
 */
public class FieldPFactoryMap
		extends TreeMap<Object, FieldPAbstractFactory<?>>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * this is a singleton class
	 */
	public final static FieldPFactoryMap FACTORY_MAP = new FieldPFactoryMap();

	/**
	 * No second instance of this class should be created
	 */
	@SuppressWarnings("unchecked")
	private FieldPFactoryMap()
	{
		super(new FieldPFactoryComparator());
	}

	/**
	 * for all primes p less than PRIME_SEPARATION_BOUNDARY long variables are
	 * sufficient for computation whereas above BigInteger must be used.
	 */
	static final long PRIME_SEPARATION_BOUNDARY = 3037000500l;

	/**
	 * The same valuse as {@link #PRIME_SEPARATION_BOUNDARY}
	 */
	public static final BigInteger PRIME_SEPARATION_BOUNDARY_BIGINT = BigInteger
			.valueOf(PRIME_SEPARATION_BOUNDARY);

	private static final int PRIME_CERTANITY = 10;
	/**
	 * for all Fp with p &lt;= inversesLookupTableBoundary inverses are stored
	 * in a lookup table of size p , for bigger fields inverses are stored with
	 * the element. It is assumed that inversesLookupTableBoundary <
	 * PRIME_SEPARATION_BOUNDARY
	 */
	private static long inversesLookupTableBoundary = 65521;

	/**
	 * Checks for primality of p. Intended to be used for checking the primality
	 * requirement of p in a Fp field. Not implemented yet, it is left up to the
	 * user to ensure the primality of p.
	 * 
	 * @param p
	 *            The number to check for primality
	 * @return For the time being, 2 and all odd numbers are considered prime
	 */
	static boolean isPrime(long p)
	{
		return (p == 2) || (p % 2 == 1);
	}

	/**
	 * Give access to a factory of elements of type Fp (see {@link FieldP}). For
	 * a given value of p, only one factory is created (and then cached in
	 * {@link #FACTORY_MAP}).
	 * 
	 * @param p
	 *            the size of the field
	 * @return a factory that is unique for p.
	 */
	public static FieldPAbstractFactory<?> getFactory(Long p)
	{
		FieldPAbstractFactory<?> factory = FACTORY_MAP.get(p);
		if (factory != null) {
			return factory;
		}
		if (!isPrime(p.longValue())) {
			throw new IllegalArgumentException("p = " + p + " is not a prime.");
		}

		if (p.longValue() < PRIME_SEPARATION_BOUNDARY) {
			factory = new FieldPLongFactory(p.longValue());
		}
		else {
			factory = new FieldPBigFactory(BigInteger.valueOf(p.longValue()));
		}
		FACTORY_MAP.put(p, factory);
		return factory;
	}

	/**
	 * Give access to a factory of elements of type Fp (see {@link FieldP}). For
	 * a given value of p, only one factory is created (and then cached in
	 * {@link #FACTORY_MAP}). {@code p} is tested with the certainty
	 * {@link FieldPFactoryMap#PRIME_CERTANITY}
	 * 
	 * @see BigInteger#isProbablePrime(int).
	 * @param p
	 *            the size of the field
	 * @return a factory
	 */
	public static FieldPAbstractFactory<?> getFactory(String p)
	{
		BigInteger bInt = new BigInteger(p);
		// if this is a relatively small value, use Long's.
		if (bInt.compareTo(PRIME_SEPARATION_BOUNDARY_BIGINT) <= 0)
			return getFactory(new Long(bInt.longValue()));

		FieldPAbstractFactory<?> factory = FACTORY_MAP.get(bInt);
		if (factory != null) {
			return factory;
		}
		if (!bInt.isProbablePrime(PRIME_CERTANITY)) {
			throw new IllegalArgumentException("p = " + p
					+ " is not a prime number.");
		}
		factory = new FieldPBigFactory(bInt);
		FACTORY_MAP.put(bInt, factory);
		return factory;
	}

	/**
	 * Returns the number n which decides whether to store inverses in a lookup
	 * table (for fields with less or equal than n elements) or with the
	 * elements (otherwise)
	 * 
	 * @return The number inversesLookupTableBoundary
	 */
	public static long getInversesLookupTableBoundary()
	{
		return inversesLookupTableBoundary;
	}

	/**
	 * This methods sets the boundary on the elements of Fp above which all
	 * inverses are stored with the elements and therefore may be computed
	 * several times instead of storing them in a lookup table. When using a
	 * lookup table for the inverses the inverse of a element will be
	 * automatically computed at creation time and stored in the table. If you
	 * are sure you will never need division it might be faster to set the
	 * boundary lower than the number of elements in your field BEFORE creating
	 * the first element of it.
	 * WARNING: If you change this boundary AFTER you have instantiated at least
	 * one element of Fp where p is less than Integer.MAX_VALUE, all existing
	 * elements which have not used the lookup-table won't use it either
	 * afterwards. Moreover, any new elements generated from these by unary
	 * operations or by binary operations where they from the first operand
	 * won't use it either. Similarily all elements which have used the lookup
	 * table and all elements generated from them in the same way will still use
	 * the lookup table. The memory allocated to the lookup table will not be
	 * freed before all elements using the lookup table are removed. Lookup
	 * tables are separate for each Fp.
	 * 
	 * @param boundary
	 *            The number n of elements in Fp above which no lookup table
	 *            should be used.
	 */
	public static void setInversesLookupTableBoundary(long boundary)
	{
		inversesLookupTableBoundary = Math.min(Integer.MAX_VALUE, boundary);
	}

}
