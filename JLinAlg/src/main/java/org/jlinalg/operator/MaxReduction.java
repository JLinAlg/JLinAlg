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

import org.jlinalg.IRingElement;

/**
 * computes maximum over elements of Vector or Matrix
 * 
 * @author Simon Levy, Andreas Keilhauer
 * @param <RE>
 *            the type of the elements in the vectors to be reduced.
 */
public class MaxReduction<RE extends IRingElement<RE>>
		extends Reduction<RE>
{

	@Override
	public void init(RE firstValue)
	{
		reducedValue = firstValue.getFactory().zero();
	}

	@Override
	public void track(RE currValue)
	{
		if (currValue.gt(reducedValue)) {
			reducedValue = currValue;
		}
	}
}
