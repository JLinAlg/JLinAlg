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
