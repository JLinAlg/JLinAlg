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

package org.jlinalg.field_p;

import java.math.BigInteger;
import java.util.Comparator;

class NumberComparator
		implements
		Comparator<Number>
{

	@Override
	public int compare(Number n1, Number n2)
	{
		if (n1 instanceof Long && n2 instanceof Long) {
			long diffLong = n1.longValue() - n2.longValue();
			return diffLong < 0 ? -1 : (diffLong == 0 ? 0 : 1);
		}
		BigInteger bigInt1;
		BigInteger bigInt2;
		if (n1 instanceof BigInteger) {
			bigInt1 = (BigInteger) n1;
		}
		else {
			if (n1 instanceof Long) {
				bigInt1 = BigInteger.valueOf(n1.longValue());
			}
			else {
				throw new Error("cannot compare " + n1.getClass());
			}
		}
		if (n2 instanceof BigInteger) {
			bigInt2 = (BigInteger) n2;
		}
		else {
			if (n2 instanceof Long) {
				bigInt2 = BigInteger.valueOf(n2.longValue());
			}
			else {
				throw new Error("cannot compare " + n2.getClass());
			}
		}
		return bigInt1.compareTo(bigInt2);
	}

}
