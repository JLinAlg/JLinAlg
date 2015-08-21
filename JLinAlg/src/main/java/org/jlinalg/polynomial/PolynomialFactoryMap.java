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
public class PolynomialFactoryMap<RE extends IRingElement<RE>>
		extends Hashtable<IRingElementFactory<RE>, PolynomialFactory<RE>>
{
	/*
	 * this is a singleton class
	 */
	public final static PolynomialFactoryMap<?> INSTANCE = new PolynomialFactoryMap();

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
