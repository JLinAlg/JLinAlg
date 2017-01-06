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

public interface IEuclideanRingElement<RE extends IEuclideanRingElement<RE>>
		extends IRingElement<RE>
{

	DivisionResultWithRest<RE> euclideanDivision(RE divisor);

	int getDegree(); // TODO: Notwendig???

	public class DivisionResultWithRest<RE extends IEuclideanRingElement<RE>>
	{
		private RE quotient;

		private RE remainder;

		/**
		 * @param quotient
		 * @param remainder
		 */
		public DivisionResultWithRest(RE quotient, RE remainder)
		{
			this.quotient = quotient;
			this.remainder = remainder;
		}

		public RE getQuotient()
		{
			return quotient;
		}

		public RE getRemainder()
		{
			return remainder;
		}

		@Override
		public String toString()
		{
			return this.quotient
					+ ((!remainder.isZero()) ? " rem " + remainder : "");
		}

	}
}
