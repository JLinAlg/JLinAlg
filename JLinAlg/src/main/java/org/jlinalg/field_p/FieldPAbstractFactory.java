package org.jlinalg.field_p;

import org.jlinalg.RingElementFactory;

/**
 * This class defines the abstract class for actual implementations of FieldP.
 * IMPORTANT: This class and its subclasses make use of the concept of
 * immutability of objects. If you make changes or subclass these classes,
 * ensure immutability or overwrite the methods affected.
 * 
 * @author Andreas Lochbihler, Georg Thimm
 */
public abstract class FieldPAbstractFactory
		extends RingElementFactory<FieldP>
{
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

}
