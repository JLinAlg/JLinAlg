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

package org.jlinalg;

import org.jlinalg.IEuclideanRingElement.DivisionResultWithRest;

public class EuclideanAlgorithm
{
	/**
	 * @param another
	 *            a polynomial
	 * @return the GCD of this and <code>another</code> polynomial
	 */
	public static <RE extends IEuclideanRingElement<RE>> RE gcd(final RE first,
			final RE second)
	{
		if (second.isZero()) {
			return first;
		}
		if (second.gt(first)) {
			return gcd(second, first);
		}

		final DivisionResultWithRest<RE> divisionResult = first
				.euclideanDivision(second);
		final RE remainder = divisionResult.getRemainder();

		return gcd(second, remainder);
	}

}
