package org.jlinalg.complex;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.testutil.MatrixTestBase;

public class ComplexMatrixTest
		extends MatrixTestBase<Complex>
{

	@SuppressWarnings("unchecked")
	@Override
	public IRingElementFactory getFactory()
	{
		return Complex.FACTORY;
	}

}
