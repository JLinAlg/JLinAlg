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

import org.jlinalg.FieldElementFactory;
import org.jlinalg.IFieldElementFactory;
import org.jlinalg.JLinAlgTypeProperties;

/**
 * This class defines the abstract class for actual implementations of FieldP.
 * IMPORTANT: This class and its subclasses make use of the concept of
 * immutability of objects. If you make changes or subclass these classes,
 * ensure immutability or overwrite the methods affected.
 * 
 * @author Andreas Lochbihler, Georg Thimm
 */
@JLinAlgTypeProperties(isExact = true, isDiscreet = true, hasNegativeValues = false, isCompound = false)
public abstract class FieldPAbstractFactory
		extends
		FieldElementFactory<FieldP>
		implements
		IFieldElementFactory<FieldP>
{
	private static final long serialVersionUID = 1L;

	@Override
	public FieldP[][] getArray(int rows, int columns)
	{
		return new FieldP[rows][columns];
	}

	@Override
	public FieldP[] getArray(int size)
	{
		return new FieldP[size];
	}

	public abstract Number getFieldSize();

}
