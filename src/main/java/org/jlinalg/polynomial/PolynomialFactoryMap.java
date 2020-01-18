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

import java.util.Hashtable;
import java.util.Map;

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;

/**
 * Cash for instances of polynomial factories. In order to obtain a factory for
 * a polynomial of a given base type use the method {@link Hashtable
 * <IRingElementFactory<?>, PolynomialFactory<?>>#get(Object)} with an instance
 * of {@link org.jlinalg.IRingElement} as argument.
 * 
 * @author Georg Thimm (2008)
 */
public final class PolynomialFactoryMap<RE extends IRingElement<RE>>
{
	/*
	 * this is a singleton class
	 */
	private final static Map<IRingElementFactory<?>, PolynomialFactory<?>> factories = new Hashtable<>();

	/**
	 * No second instance of this class should be created
	 */
	private PolynomialFactoryMap()
	{
		super();
	}

	public PolynomialFactory<RE> put(IRingElementFactory<RE> key,
			PolynomialFactory<RE> value)
	{
		return put(key, value);
	}

	@SuppressWarnings("unchecked")
	public static <BASE extends IRingElement<BASE>> PolynomialFactory<BASE> getFactory(
			BASE value)
	{
		IRingElementFactory<BASE> factory = value.getFactory();
		PolynomialFactory<BASE> polynomialFactory = (PolynomialFactory<BASE>) factories
				.get(factory);
		if (polynomialFactory == null) {
			polynomialFactory = new PolynomialFactory<>(factory);
			PolynomialFactoryMap.factories.put(factory, polynomialFactory);
		}
		return polynomialFactory;
	}

	public static <BASE extends IRingElement<BASE>> PolynomialFactory<BASE> getFactory(
			IRingElementFactory<BASE> baseFactory)
	{
		return getFactory(baseFactory.one());
	}

}
