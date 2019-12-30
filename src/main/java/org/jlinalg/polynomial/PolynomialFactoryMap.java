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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

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
		implements
		Map<IRingElementFactory<RE>, PolynomialFactory<RE>>
{
	/*
	 * this is a singleton class
	 */
	private final static Hashtable<IRingElementFactory<?>, PolynomialFactory<?>> INSTANCE = new Hashtable<>();

	/**
	 * No second instance of this class should be created
	 */
	private PolynomialFactoryMap()
	{
		super();
	}

	@Override
	public int size()
	{
		return INSTANCE.size();
	}

	@Override
	public boolean isEmpty()
	{

		return INSTANCE.isEmpty();
	}

	@Override
	public boolean containsKey(Object key)
	{
		return INSTANCE.contains(key);
	}

	@Override
	public boolean containsValue(Object value)
	{
		return INSTANCE.containsValue(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PolynomialFactory<RE> get(Object key)
	{
		return (PolynomialFactory<RE>) INSTANCE.get(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PolynomialFactory<RE> remove(Object key)
	{
		return (PolynomialFactory<RE>) INSTANCE.remove(key);
	}

	@Override
	public void putAll(
			Map<? extends IRingElementFactory<RE>, ? extends PolynomialFactory<RE>> map)
	{
		INSTANCE.putAll(map);
	}

	@Override
	public void clear()
	{
		INSTANCE.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IRingElementFactory<RE>> keySet()
	{
		Set<IRingElementFactory<RE>> result = new HashSet<>();
		INSTANCE.forEach((k, v) -> result.add((IRingElementFactory<RE>) k));
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PolynomialFactory<RE>> values()
	{
		Collection<PolynomialFactory<RE>> result = new ArrayList<>();
		INSTANCE.forEach((k, v) -> result.add((PolynomialFactory<RE>) k));
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Entry<IRingElementFactory<RE>, PolynomialFactory<RE>>> entrySet()
	{
		Map<IRingElementFactory<RE>, PolynomialFactory<RE>> result = new HashMap<>();
		INSTANCE.entrySet()
				.forEach(e -> result.put((IRingElementFactory<RE>) e.getKey(),
						(PolynomialFactory<RE>) e.getValue()));
		return result.entrySet();
	}

	@Override
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
		PolynomialFactory<BASE> polynomialFactory = (PolynomialFactory<BASE>) PolynomialFactoryMap.INSTANCE
				.get(factory);
		if (polynomialFactory == null) {
			polynomialFactory = new PolynomialFactory<>(factory);
			PolynomialFactoryMap.INSTANCE.put(factory, polynomialFactory);
		}
		return polynomialFactory;
	}

	public static <BASE extends IRingElement<BASE>> PolynomialFactory<BASE> getFactory(
			IRingElementFactory<BASE> baseFactory)
	{
		return getFactory(baseFactory.one());
	}

}
