package org.jlinalg.polynomial;

import java.util.Hashtable;

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;

/**
 * Cash for instances of polynomial factories. In order to obtain a factory for
 * a polynomial of a given base type use the method {@link Hashtable
 * <IRingElementFactory<?>, PolynomialFactory<?>>#get(Object)} with an instance
 * of {@link org.jlinalg.IRingElement} as argument.
 * 
 * @author Georg THimm (2008)
 */
@SuppressWarnings("serial")
class PolynomialFactoryMap<RE extends IRingElement<RE>>
		extends Hashtable<IRingElementFactory<RE>, PolynomialFactory<RE>>
{
	/*
	 * this is a singleton class
	 */
	public final static PolynomialFactoryMap<?> INSTANCE = new PolynomialFactoryMap<>();

	/**
	 * No second instance of this class should be created
	 */
	private PolynomialFactoryMap()
	{
		super();
	}

	@Override
	public PolynomialFactory<RE> put(IRingElementFactory<RE> key,
			PolynomialFactory<RE> value)
	{
		return super.put(key, value);
	}

}
