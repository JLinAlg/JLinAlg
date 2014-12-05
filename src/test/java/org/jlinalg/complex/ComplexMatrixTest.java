package org.jlinalg.complex;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.testutil.MatrixTestBase;

public class ComplexMatrixTest
		extends MatrixTestBase<Complex>
{

	@Override
	public IRingElementFactory<Complex> getFactory()
	{
		return Complex.FACTORY;
	}

}
