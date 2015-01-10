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
package org.jlinalg.testutil;

import java.util.ArrayList;

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;

/**
 * A list of random numbers for the convenience of the tests.
 * 
 * @author Georg Thimm
 * @param <E>
 *            The type of which random elements are created.
 */
@SuppressWarnings("serial")
public class RandomNumberList<RE extends IRingElement<RE>>
		extends ArrayList<RE>
{
	/**
	 * @param fac
	 *            the factory for elements of type E
	 * @param n
	 *            the number of elements.
	 */
	public RandomNumberList(IRingElementFactory<RE> fac, int n)
	{
		for (int i = 0; i < n; i++) {
			this.add(fac.randomValue());
		}
	}

	/**
	 * @param factory
	 * @param n
	 *            the number of random values
	 * @param min
	 *            the minimal value
	 * @param max
	 *            the maximal value
	 */
	public RandomNumberList(IRingElementFactory<RE> factory, int n, RE min,
			RE max)
	{
		for (int i = 0; i < n; i++) {
			this.add(factory.randomValue(min, max));
		}
	}
}
