package org.jlinalg.bigdecimalwrapper;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.testutil.MatrixTestBase;

public class BigDecimalWrapperMatrixTest
		extends MatrixTestBase<BigDecimalWrapper>
{
	private IRingElementFactory<BigDecimalWrapper> factory = new BigDecimalWrapperFactory(
			50);

	@Override
	public IRingElementFactory<BigDecimalWrapper> getFactory()
	{
		return factory;
	}

}
