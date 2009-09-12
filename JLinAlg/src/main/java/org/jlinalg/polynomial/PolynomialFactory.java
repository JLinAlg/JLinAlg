package org.jlinalg.polynomial;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.InvalidOperationException;
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
public class PolynomialFactory<BASE extends IRingElement>
		extends RingElementFactory<Polynomial<BASE>>
{
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
	public static <RE extends IRingElement> PolynomialFactory<RE> getFactory(
			IRingElementFactory<RE> baseFactory)
	{
		PolynomialFactory<RE> factory = (PolynomialFactory<RE>) PolynomialFactoryMap.INSTANCE
				.get(baseFactory);
		if (factory == null) {
			factory = new PolynomialFactory<RE>(baseFactory);
			PolynomialFactoryMap.INSTANCE.put(baseFactory, factory);
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
	private PolynomialFactory(IRingElementFactory<BASE> baseFactory)
	{
		super();
		if (PolynomialFactoryMap.INSTANCE.containsKey(baseFactory))
			throw new InternalError("Try to recreate the same factory again.");
		BASEFACTORY = baseFactory;
		PolynomialFactoryMap.INSTANCE.put(BASEFACTORY, this);

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
		return BASEFACTORY.equals(((PolynomialFactory<IRingElement>) obj)
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
	@Override
	@Deprecated
	public Polynomial<BASE> gaussianRandomValue(Random random)
	{
		throw new InvalidOperationException("Cannot create random polynomials.");
	}

	/**
	 * Create a polynomial from a {@link Map}<Integer,? extends
	 * {@link IRingElement}> or an instance of {@link IRingElement}. <BR>
	 * Preferred method: {@link #get(Map, IRingElementFactory)}
	 */
	@Override
	public Polynomial<BASE> get(Object o)
	{
		// create a polynomial consisting of a single constant.
		if (o instanceof IRingElement) {
			IRingElement e = (IRingElement) o;
			Map<Integer, IRingElement> coefficientForOne = new HashMap<Integer, IRingElement>();
			coefficientForOne.put(0, e);
			return new Polynomial<BASE>((Map<Integer, BASE>) coefficientForOne,
					(IRingElementFactory<BASE>) e.getFactory());
		}

		if (o instanceof Map) {
			if (((Map<Integer, BASE>) o).isEmpty()) {
				throw new InvalidOperationException(
						"can not create a polynomial from an empty map");
			}
			BASE baseElement = (BASE) ((Map<Integer, BASE>) o).values()
					.toArray()[0];

			return new Polynomial<BASE>((Map<Integer, BASE>) o,
					(IRingElementFactory<BASE>) baseElement.getFactory());
		}

		throw new InvalidOperationException("cannot create a polynomial for "
				+ o);
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

	@Override
	public Polynomial<BASE>[][] getArray(int rows, int columns)
	{
		return new Polynomial[rows][columns];
	}

	/**
	 * @throws InvalidOperationException
	 *             if called.
	 */
	@Override
	@Deprecated
	public Polynomial<BASE> randomValue(final Random random)
	{
		throw new InvalidOperationException("Cannot create random polynomials.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.IRingElementFactory#get(long)
	 */
	public Polynomial<BASE> get(long d)
	{
		return get(BASEFACTORY.get(d));
	}

	/**
	 * @throws InvalidOperationException
	 * @see org.jlinalg.RingElementFactory#randomValue(java.util.Random,
	 *      org.jlinalg.IRingElement, org.jlinalg.IRingElement)
	 */
	@Override
	@Deprecated
	public Polynomial<BASE> randomValue(Random random, IRingElement min,
			IRingElement max)
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
	public Polynomial<BASE> randomValue(IRingElement min, IRingElement max)
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

}