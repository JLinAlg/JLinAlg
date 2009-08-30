package org.jlinalg.polynomial;

import java.util.Hashtable;

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
public class PolynomialFactoryMap
		extends Hashtable<IRingElementFactory<?>, PolynomialFactory<?>>
{
	/*
	 * this is a singleton class
	 */
	public final static PolynomialFactoryMap INSTANCE = new PolynomialFactoryMap();

	/**
	 * No second instance of this class should be created
	 */
	private PolynomialFactoryMap()
	{
		super();
	}

}
