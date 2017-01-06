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
package org.jlinalg.polynomial;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.JLinAlgTypeProperties;
import org.jlinalg.RingElementFactory;

/**
 * The factory for polynomials. The factory has to be created for a particular
 * sub-type of {@link IRingElement}, that is the constructors of single elements
 * have to be provided with a factory of this type.
 * <P>
 * Note: if the factory of the base type is not a singelton factory, it must
 * implement {@code java.lang.Object.equal(Object o)} such that if the base type
 * are considered equal, the factories are equal.
 * 
 * @author Georg THimm
 * @param <BASE>
 *            the type for the elements in the polynomial
 */
@JLinAlgTypeProperties(isCompound = true)
public class PolynomialFactory<BASE extends IRingElement<BASE>>
		extends RingElementFactory<Polynomial<BASE>>
{

	private static final long serialVersionUID = 1L;

	/**
	 * the basic empty polynomial.
	 */
	private final Polynomial<BASE> ZERO;

	/**
	 * the polynomial "1".
	 */
	private final Polynomial<BASE> ONE;

	/**
	 * the polynomial "-1".
	 */
	private final Polynomial<BASE> M_ONE;

	/**
	 * obtain a factory for a given base type. This function can be called again
	 * for the same base type, but only one factory will be created @see
	 * {@link PolynomialFactoryMap}
	 * 
	 * @param <RE>
	 *            the base type for the factory
	 * @param baseFactory
	 * @return a factory for polynomials.
	 */
	@SuppressWarnings("unchecked")
	public static <RE extends IRingElement<RE>> PolynomialFactory<RE> getFactory(
			IRingElementFactory<RE> baseFactory)
	{
		PolynomialFactory<RE> factory = (PolynomialFactory<RE>) PolynomialFactoryMap.INSTANCE
				.get(baseFactory);
		if (factory == null) {
			factory = new PolynomialFactory<RE>(baseFactory);
			PolynomialFactoryMap.INSTANCE.put(
					(IRingElementFactory) baseFactory,
					(PolynomialFactory) factory);
		}
		return factory;
	}

	/**
	 * Create a new Factory for a given base type.
	 * 
	 * @param baseFactory
	 *            the factory for the base elements.
	 * @exception InternalError
	 *                if it is attempted to create another factory for the same
	 *                base type.
	 */
	@SuppressWarnings({
			"boxing", "unchecked"
	})
	private PolynomialFactory(IRingElementFactory<BASE> baseFactory)
	{
		super();
		if (PolynomialFactoryMap.INSTANCE.containsKey(baseFactory))
			throw new InternalError("Try to recreate the same factory again.");
		BASEFACTORY = baseFactory;
		PolynomialFactoryMap.INSTANCE.put((IRingElementFactory) BASEFACTORY,
				(PolynomialFactory) this);

		Map<Integer, BASE> coefficientForZero = new HashMap<Integer, BASE>();
		coefficientForZero.put(0, baseFactory.zero());
		ZERO = new Polynomial<BASE>(coefficientForZero, baseFactory);
		Map<Integer, BASE> coefficientForOne = new HashMap<Integer, BASE>();
		coefficientForOne.put(0, baseFactory.one());
		ONE = new Polynomial<BASE>(coefficientForOne, baseFactory);
		M_ONE = ONE.negate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (!(obj instanceof PolynomialFactory)) return false;
		return BASEFACTORY.equals(((PolynomialFactory<BASE>) obj)
				.getBaseFactory());
	}

	/**
	 * the factory for the base element of the type of polynomials this factory
	 * produces.
	 */
	final IRingElementFactory<BASE> BASEFACTORY;

	/**
	 * The getter for the factory for the base element of the type of
	 * polynomials this factory produces.
	 * 
	 * @return the factory for the base element
	 */
	public IRingElementFactory<BASE> getBaseFactory()
	{
		return BASEFACTORY;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Polynomial<BASE>[] getArray(int size)
	{
		return new Polynomial[size];
	}

	@Override
	public Polynomial<BASE> m_one()
	{
		return M_ONE;
	}

	@Override
	public Polynomial<BASE> one()
	{
		return ONE;
	}

	@Override
	public Polynomial<BASE> zero()
	{
		return ZERO;
	}

	/**
	 * @throws InvalidOperationException
	 *             if called.
	 */
	@SuppressWarnings("deprecation")
	@Override
	@Deprecated
	public Polynomial<BASE> gaussianRandomValue(
			@SuppressWarnings("unused") Random random)
	{
		throw new InvalidOperationException("Cannot create random polynomials.");
	}

	/**
	 * Create a polynomial from a {@link Map}<Integer,? extends
	 * {@link IRingElement}> or an instance of {@link IRingElement}. <BR>
	 * Preferred method: {@link #get(Map, IRingElementFactory)}
	 * 
	 * @param o
	 *            an object which can be translated into a coefficient for this
	 *            polynomial.
	 * @return a polynomial consisting of a single constant: the value of
	 *         <code>o</code>
	 */
	@Override
	public Polynomial<BASE> get(Object o)
	{
		// create a polynomial consisting of a single constant.
		if (o instanceof IRingElement<?>) {
			BASE e = (BASE) o;
			Map<Integer, BASE> coefficientForOne = new HashMap<Integer, BASE>();
			coefficientForOne.put(new Integer(0), e);
			return new Polynomial<BASE>(coefficientForOne, e.getFactory());
		}

		if (o instanceof Map<?, ?>) {
			if (((Map<?, ?>) o).isEmpty()) {
				throw new InvalidOperationException(
						"can not create a polynomial from an empty map");
			}
			BASE baseElement = (BASE) ((Map<?, ?>) o).values().toArray()[0];

			return new Polynomial<BASE>((Map<Integer, BASE>) o,
					baseElement.getFactory());
		}
		return get(BASEFACTORY.get(o));
	}

	/**
	 * Create a polynomial from a map with coefficients and a basefactory
	 * 
	 * @param coefficients
	 * @param basefactory
	 * @return the polynomial created from the map.
	 */
	public Polynomial<BASE> get(Map<Integer, BASE> coefficients,
			IRingElementFactory<BASE> basefactory)
	{
		return new Polynomial<BASE>(coefficients, basefactory);
	}

	@Override
	public Polynomial<BASE> get(int i)
	{
		return get(BASEFACTORY.get(i));
	}

	@Override
	public Polynomial<BASE> get(double d)
	{
		return get(BASEFACTORY.get(d));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Polynomial<BASE>[][] getArray(int rows, int columns)
	{
		return new Polynomial[rows][columns];
	}

	/**
	 * @throws InvalidOperationException
	 *             if called.
	 */
	@SuppressWarnings("deprecation")
	@Override
	@Deprecated
	public Polynomial<BASE> randomValue(
			@SuppressWarnings("unused") final Random random)
	{
		throw new InvalidOperationException("Cannot create random polynomials.");
	}

	/**
	 * @see org.jlinalg.IRingElementFactory#get(long)
	 */
	@Override
	public Polynomial<BASE> get(long d)
	{
		return get(BASEFACTORY.get(d));
	}

	/**
	 * @throws InvalidOperationException
	 */
	@SuppressWarnings("deprecation")
	@Deprecated
	@Override
	public Polynomial<BASE> randomValue(
			@SuppressWarnings("unused") Random random,
			@SuppressWarnings("unused") Polynomial<BASE> min,
			@SuppressWarnings("unused") Polynomial<BASE> max)
	{
		throw new InvalidOperationException("Cannot create random polynomials.");
	}

	/**
	 * @throws InvalidOperationException
	 *             if called.
	 */
	@Override
	@Deprecated
	public Polynomial<BASE> gaussianRandomValue()
	{
		throw new InvalidOperationException("Cannot create random polynomials.");
	}

	/**
	 * @throws InvalidOperationException
	 *             if called.
	 */
	@Override
	@Deprecated
	public Polynomial<BASE> randomValue(
			@SuppressWarnings("unused") Polynomial<BASE> min,
			@SuppressWarnings("unused") Polynomial<BASE> max)
	{
		throw new InvalidOperationException("Cannot create random polynomials.");
	}

	/**
	 * @throws InvalidOperationException
	 *             if called.
	 */
	@Override
	@Deprecated
	public Polynomial<BASE> randomValue()
	{
		throw new InvalidOperationException("Cannot create random polynomials.");
	}

	/**
	 * @return a description of the factory
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Factory: " + getClass().getName() + "[" + getBaseFactory()
				+ "]";
	}
}
