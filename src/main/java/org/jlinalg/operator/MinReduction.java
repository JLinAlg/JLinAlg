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
package org.jlinalg.operator;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

import org.jlinalg.IReducible;
import org.jlinalg.IRingElement;

/**
 * computes minimum over elements of Vector or Matrix
 * 
 * @author Simon Levy, Andreas Keilhauer
 */
public class MinReduction<RE extends IRingElement<RE>>
		extends
		Reduction<RE>
{
	@Override
	public RE apply(IReducible<RE> reducible)
	{
		final Iterator<RE> iterator = reducible.iterator();
		final RE firstElement = iterator.next();
		final FEComparator<RE> comparator = firstElement.getFactory()
				.getGreaterThanComparator();
		final AtomicReference<RE> result = new AtomicReference<>(firstElement);
		iterator.forEachRemaining(e -> {
			if (comparator.compare(result.get(), e)) {
				result.set(e);
			}
		});
		return result.get();
	}
}
