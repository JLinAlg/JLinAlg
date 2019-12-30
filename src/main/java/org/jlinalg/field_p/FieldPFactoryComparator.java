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

import java.util.Comparator;

class FieldPFactoryComparator<T>
		implements
		Comparator<Comparable<? super T>>
{
	/**
	 * If o1 and o2 are of the same class, return the value for a normal
	 * comparison. Otherwise, assume that one object is an instance of {@code
	 * java.lang.Long}, the other of {@code java.util.BigInteger} and that
	 * instances of the later have always the larger values
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int compare(Comparable<? super T> o1, Comparable<? super T> o2)
	{
		if (o1.getClass().equals(o2.getClass())) return o1.compareTo((T) o2);
		if (o1 instanceof Long) return -1;
		return 1;
	}

}
