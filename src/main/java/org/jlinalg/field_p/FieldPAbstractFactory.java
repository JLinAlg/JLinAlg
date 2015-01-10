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

import org.jlinalg.JLinAlgTypeProperties;
import org.jlinalg.RingElementFactory;

/**
 * This class defines the abstract class for actual implementations of FieldP.
 * IMPORTANT: This class and its subclasses make use of the concept of
 * immutability of objects. If you make changes or subclass these classes,
 * ensure immutability or overwrite the methods affected.
 * 
 * @author Andreas Lochbihler, Georg Thimm
 */
@JLinAlgTypeProperties(isExact = true, isDiscreet = true, hasNegativeValues = false)
public abstract class FieldPAbstractFactory<RE extends FieldP<RE>>
		extends RingElementFactory<RE>
{
	@SuppressWarnings("unchecked")
	@Override
	public RE[][] getArray(int rows, int columns)
	{
		return (RE[][]) new FieldP[rows][columns];
	}

	@SuppressWarnings("unchecked")
	@Override
	public RE[] getArray(int size)
	{
		return (RE[]) new FieldP[size];
	}

}
