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
package org.jlinalg.rationalFunction;

import java.util.Hashtable;

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;

/**
 * Cash for instances of rational function factories. In order to obtain a
 * factory for
 * a rational function of a given base type use the method {@link Hashtable
 * <IRingElementFactory<?>, RationalFunctionFactory<?>>#get(Object)} with an
 * instance
 * of {@link org.jlinalg.IRingElement} as argument.
 * 
 * @author Andreas Keilhauer (2014)
 */
@SuppressWarnings("serial")
public class RationalFunctionFactoryMap<RE extends IRingElement<RE>>
		extends Hashtable<IRingElementFactory<RE>, RationalFunctionFactory<RE>>
{

	/*
	 * this is a singleton class
	 */
	public final static RationalFunctionFactoryMap<?> INSTANCE = new RationalFunctionFactoryMap();

	/**
	 * No second instance of this class should be created
	 */
	private RationalFunctionFactoryMap()
	{
		super();
	}

	@Override
	public RationalFunctionFactory<RE> put(IRingElementFactory<RE> key,
			RationalFunctionFactory<RE> value)
	{
		return super.put(key, value);
	}
}
